package pro.sisit.unit9.shopService;

import pro.sisit.unit9.entity.Author;
import pro.sisit.unit9.entity.Book;

import java.math.BigDecimal;

public interface AccountinBooks {
    BigDecimal calculatingTheTotalCostOfSalesForBook(String title);
    BigDecimal calculatingTheTotalCostOfSalesForBookByAuthor(String lastname);

}
