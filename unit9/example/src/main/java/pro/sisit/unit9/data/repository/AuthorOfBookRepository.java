package pro.sisit.unit9.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pro.sisit.unit9.data.queryRepository.AuthorOfBookComplexQueryRepository;
import pro.sisit.unit9.entity.AuthorOfBook;


public interface AuthorOfBookRepository extends CrudRepository<AuthorOfBook, Long> ,
        PagingAndSortingRepository<AuthorOfBook, Long>,
        JpaRepository<AuthorOfBook, Long>,
        JpaSpecificationExecutor<AuthorOfBook>,
        AuthorOfBookComplexQueryRepository {



}
