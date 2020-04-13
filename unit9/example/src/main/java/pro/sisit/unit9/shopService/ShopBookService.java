package pro.sisit.unit9.shopService;


import pro.sisit.unit9.entity.Client;

import java.util.List;

public interface ShopBookService {
    void buyBook(String title, Client client);
    void registrationOfTransaction(String title, Long idClient);
    List<String> findAllBook();

}
