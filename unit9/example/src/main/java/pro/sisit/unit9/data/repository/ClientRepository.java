package pro.sisit.unit9.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pro.sisit.unit9.data.queryRepository.ClientComplexQueryRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long>,
        PagingAndSortingRepository<Client, Long>,
        JpaRepository<Client, Long>,
        JpaSpecificationExecutor<Book>,
        ClientComplexQueryRepository{

    @Query("select c.id from " +
            "Client c " +
            "where c.firstname=?1 and c.lastname=?2")
    Optional<Long> findByFirstnameAndLastname(String firstname, String lastname);

}
