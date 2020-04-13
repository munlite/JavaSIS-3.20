package pro.sisit.unit9.shopService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sisit.unit9.data.repository.DealRepository;
import pro.sisit.unit9.shopService.AccountinBooks;
/**
 * Класс, предназначенный для подсчета проданных книг
 */
import java.math.BigDecimal;
@Service
public class AccountinBooksImpl implements AccountinBooks {
    @Autowired
    private DealRepository dealRepository;

    @Override
    public BigDecimal calculatingTheTotalCostOfSalesForBook(String title) {
        return dealRepository.sumByBook(title);
    }

    @Override
    public BigDecimal calculatingTheTotalCostOfSalesForBookByAuthor(String lastname) {
        return dealRepository.sumByAuthor(lastname);
    }
}
