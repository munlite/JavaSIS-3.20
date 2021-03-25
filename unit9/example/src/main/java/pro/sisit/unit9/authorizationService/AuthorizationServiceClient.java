package pro.sisit.unit9.authorizationService;

import pro.sisit.unit9.entity.Client;

public interface AuthorizationServiceClient {
    boolean authorizationClient(Client client);
    long authenticationClient(Client client);
    long registrationClient(Client client);

}
