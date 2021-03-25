package pro.sisit.unit9.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pro.sisit.unit9.data.queryRepository.DealComplexQueryRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Client;
import pro.sisit.unit9.entity.Deal;

import java.math.BigDecimal;
import java.util.List;

public interface DealRepository extends CrudRepository<Deal, Long>,
        PagingAndSortingRepository<Deal, Long>,
        JpaRepository<Deal, Long>,
        JpaSpecificationExecutor<Deal>,
        DealComplexQueryRepository {
    @Query("select d.book from "
            + "Deal d "
            + "join d.client "
            + "where d.client.lastname = ?1")
    List<Book> findByClient(String lastname);


    @Query("select d.client from " +
            "Deal d " +
            "join d.book " +
            "where d.book.title = ?1")
    List<Client> findByBook(String title);

    @Query("select SUM (d.price) from " +
            "Deal d " +
            "where d.book.title = ?1")
    BigDecimal sumByBook(String title);

    @Query("select SUM (d.price) " +
            "from Deal d " +
            "inner join AuthorOfBook aob " +
            "on d.book.id = aob.book.id " +
            "where aob.author.lastname = ?1")
    BigDecimal sumByAuthor(String lastname);

}
