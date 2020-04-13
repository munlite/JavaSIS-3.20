package pro.sisit.unit9.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pro.sisit.unit9.data.queryRepository.AuthorComplexQueryRepository;
import pro.sisit.unit9.entity.Author;



public interface AuthorRepository extends CrudRepository<Author, Long>,
        PagingAndSortingRepository<Author, Long>,
        JpaRepository<Author, Long>,
        JpaSpecificationExecutor<Author>,
        AuthorComplexQueryRepository {

}
