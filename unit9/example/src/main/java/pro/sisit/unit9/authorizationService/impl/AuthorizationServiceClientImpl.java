package pro.sisit.unit9.authorizationService.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sisit.unit9.authorizationService.AuthorizationServiceClient;
import pro.sisit.unit9.data.repository.ClientRepository;
import pro.sisit.unit9.entity.Client;

/**
 * Класс, предназначенный для авторизации и аутентификации клиета
 */
@Service
public class AuthorizationServiceClientImpl implements AuthorizationServiceClient {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * авторизация
     * @param client
     * @return
     */
    @Override
    public boolean authorizationClient(Client client) {
        if (clientRepository.findByFirstnameAndLastname(client.getFirstname(), client.getLastname()).isPresent()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Аутетифекация
     * @param client
     * @return
     */
    @Override
    public long authenticationClient(Client client) {
        return clientRepository.findByFirstnameAndLastname(client.getFirstname(), client.getLastname()).get();
    }

    /**
     * Регистрация
     * @param client
     * @return
     */
    @Override
    public long registrationClient(Client client) {
         clientRepository.save(client);
         return authenticationClient(client);
    }
}
