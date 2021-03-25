package pro.sisit.unit9.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pro.sisit.unit9.data.queryRepository.BookComplexQueryRepository;
import pro.sisit.unit9.entity.Book;

import java.math.BigDecimal;
import java.util.List;


public interface BookRepository extends CrudRepository<Book, Long>,
        PagingAndSortingRepository<Book, Long>,
        JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book>,
        BookComplexQueryRepository {

    @Query("select aob.book from "
            + "AuthorOfBook aob "
            + "join aob.author "
            + "where aob.author.lastname = ?1")
    List<Book> findByAuthor(String lastname);

    List<Book> findByTitle(String title);

    List<Book> findByYear(int year);

}
