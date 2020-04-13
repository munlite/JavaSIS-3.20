package pro.sisit.unit9.shopService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sisit.unit9.data.repository.BookRepository;
import pro.sisit.unit9.data.repository.ClientRepository;
import pro.sisit.unit9.data.repository.DealRepository;
import pro.sisit.unit9.entity.Deal;
import pro.sisit.unit9.shopService.DealService;

/**
 * Класс, предназначенный для регистрации сделки
 */
@Service
public class DealServiceImpl implements DealService {
    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void saveDeal(String title, Long idClient) {
        Deal deal = Deal.builder()
                .client(clientRepository.findById(idClient).get())
                .book(bookRepository.findByTitle(title).get(0))
                .build();
        dealRepository.save(deal);
        System.out.println("Сделка прошла успешно");
    }
}
