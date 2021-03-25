package pro.sisit.unit9.shellService;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pro.sisit.unit9.authorizationService.AuthorizationServiceClient;
import pro.sisit.unit9.entity.Client;
import pro.sisit.unit9.shopService.AccountinBooks;
import pro.sisit.unit9.shopService.ShopBookService;

import java.math.BigDecimal;


@ShellComponent
public class ShellUserInterfaceService {
    private final AccountinBooks accountinBooks;
    private final ShopBookService shopBookService;
    private final AuthorizationServiceClient asc;
    private Client client;

    public ShellUserInterfaceService(AccountinBooks accountinBooks,
                                     ShopBookService shopBookService,
                                     AuthorizationServiceClient asc) {
        this.accountinBooks = accountinBooks;
        this.shopBookService = shopBookService;
        this.asc = asc;
    }

    @ShellMethod("Авторизация")
    public void authorizationClient(
            @ShellOption(defaultValue = "NERDY")
                    String firstname, String lastname){
        client = Client.builder()
                .firstname(firstname)
                .lastname(lastname)
                .build();
        if (asc.authorizationClient(client))
        {
            System.out.println("Авторизация прошла успешно");
            System.out.println("Можете выбрать книгу");
        }
        else {
            System.out.println("Повторите попытку авторизации или зарегистрируйтесь");
        }
    }

    @ShellMethod("Регистрация")
    public void reg(
            @ShellOption(defaultValue = "NERDY")
                    String firstname, String lastname,
                    String street, String house, String apartment){
        client = Client.builder()
                .firstname(firstname)
                .lastname(lastname)
                .street(street)
                .house(house)
                .apartment(apartment)
                .build();
        asc.registrationClient(client);
    }

    @ShellMethod("Вывести все книги")
    public void buyBook(
            @ShellOption(defaultValue = "NERDY") String title){
    shopBookService.buyBook(title, client);
    }

    @ShellMethod("Вывести все книги")
    public void findAllBook(){
        shopBookService.findAllBook()
                .forEach(System.out::println);
    }
    @ShellMethod("Вывести общую сумму купленных книг по книге")
    public BigDecimal calcBook(
            @ShellOption(defaultValue = "NERDY") String title) {
        return accountinBooks.calculatingTheTotalCostOfSalesForBook(title);
    }

    @ShellMethod("Вывести общую сумму купленных книг по автору")
    public BigDecimal calcBookByAuthor(
            @ShellOption(defaultValue = "NERDY") String lastname) {
        return accountinBooks.calculatingTheTotalCostOfSalesForBookByAuthor(lastname);
    }
}
