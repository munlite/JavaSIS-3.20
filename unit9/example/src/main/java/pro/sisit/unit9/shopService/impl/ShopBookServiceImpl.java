package pro.sisit.unit9.shopService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sisit.unit9.data.repository.BookRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Client;
import pro.sisit.unit9.shopService.DealService;
import pro.sisit.unit9.shopService.ShopBookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, предназначенный для покупки книг
 */
@Service
public class ShopBookServiceImpl implements ShopBookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DealService dealService;

    @Override
    public void buyBook(String title, Client client) {
        if (bookRepository.findByTitle(title)!=null){
            registrationOfTransaction(title, client.getId());
            System.out.println("Книга куплена");
        }
        else {
            System.out.println("Проверьте название книги");
        }
    }

    @Override
    public void registrationOfTransaction(String title, Long idClient) {
        dealService.saveDeal(title, idClient);

    }

    @Override
    public List<String> findAllBook() {
        return bookRepository.findAll().stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }
}
