package pro.sisit.unit9;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import pro.sisit.unit9.authorizationService.AuthorizationServiceClient;
import pro.sisit.unit9.data.repository.*;
import pro.sisit.unit9.entity.*;
import pro.sisit.unit9.shopService.ShopBookService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
		InteractiveShellApplicationRunner
				.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner
				.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class SpringDataApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private ShopBookService shopBookService;

	@Autowired
	private AuthorOfBookRepository authorOfBookRepository;

	@Autowired
	private DealRepository dealRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AuthorizationServiceClient authorizationServiceClient;

	@Before
	public void init() {
//		Book book = new Book();
//		book.setDescription("Увлекательные приключения Тома Сойера");
//		book.setTitle("Приключения Тома Сойера");
//		book.setYear(1876);
//		bookRepository.save(book);
//
//		Book book2 = new Book();
//		book2.setTitle("Михаил Строгов");
//		book2.setYear(1876);
//		bookRepository.save(book2);
//
//		Book book3 = new Book();
//		book3.setTitle("Приключения Гекльберри Финна");
//		book3.setYear(1884);
//		bookRepository.save(book3);
//
//		Author author = new Author();
//		author.setFirstname("Марк");
//		author.setLastname("Твен");
//		authorRepository.save(author);
//
//		AuthorOfBook authorOfBook = new AuthorOfBook();
//		authorOfBook.setAuthor(author);
//		authorOfBook.setBook(book);
//		authorOfBookRepository.save(authorOfBook);
//
//		AuthorOfBook authorOfBook2 = new AuthorOfBook();
//		authorOfBook2.setAuthor(author);
//		authorOfBook2.setBook(book3);
//		authorOfBookRepository.save(authorOfBook2);
//
//		Author author2 = new Author();
//		author2.setFirstname("Жюль");
//		author2.setLastname("Верн");
//		authorRepository.save(author2);
//
//		AuthorOfBook authorOfBook3 = new AuthorOfBook();
//		authorOfBook3.setAuthor(author2);
//		authorOfBook3.setBook(book2);
//		authorOfBookRepository.save(authorOfBook3);
//
//		Book book4 = new Book();
//		book4.setTitle("Буратино");
//		book4.setYear(1936);
//		bookRepository.save(book4);
//
//		Author author3 = new Author();
//		author3.setFirstname("Алексей");
//		author3.setLastname("Толстой");
//		authorRepository.save(author3);
//
//		AuthorOfBook authorOfBook4 = new AuthorOfBook();
//		authorOfBook4.setAuthor(author3);
//		authorOfBook4.setBook(book4);
//		authorOfBookRepository.save(authorOfBook4);
//
//		Client client1 = new Client();
//		client1.setFirstname("Даниил");
//		client1.setLastname("Хворост");
//		client1.setStreet("пр. Мира");
//		client1.setHouse("5");
//		client1.setApartment("3");
//		clientRepository.save(client1);
//
//		Client client2 = new Client();
//		client2.setFirstname("Петр");
//		client2.setLastname("Иванов");
//		client2.setStreet("ул. Гагарина");
//		client2.setHouse("9");
//		client2.setApartment("10");
//		clientRepository.save(client2);
//
//		Deal deal1 = new Deal();
//		deal1.setBook(book4);
//		deal1.setClient(client1);
//		deal1.setPrice((float) 19.19);
//		dealRepository.save(deal1);
	}

	@Test
	public void regClient(){
		Client client = Client.builder()
				.firstname("Андрей")
				.lastname("Белый")
				.street("Ангарская")
				.apartment("2")
				.house("2")
				.build();
		authorizationServiceClient.registrationClient(client);
	}



	@Test
	public void testDealFindByClient(){
		assertTrue(dealRepository.findByClient("Хворост")
				.stream().allMatch(deal -> deal.getTitle().equals("Буратино")));
	}

	@Test
	public void testDealFindByBook(){
		assertTrue(dealRepository.findByBook("Буратино")
				.stream().allMatch(deal -> deal.getLastname().equals("Хворост")));
	}

	@Test
	public void testSave() {
		boolean founded = false;
		for (Book iteratedBook : bookRepository.findAll()) {
			if (iteratedBook.getTitle().equals("Буратино")
					&& iteratedBook.getId() > 0) {
				founded = true;
				break;
			}
		}
		assertTrue(founded);
	}

	@Test
	public void testBuyBook(){
			Client client = Client.builder()
					.firstname("Кристина")
					.lastname("Рожина")
					.build();
			if (authorizationServiceClient.authorizationClient(client)){
				client.setId(authorizationServiceClient.authenticationClient(client));
				shopBookService.buyBook("Буратино", client);
			} else {
				client.setStreet("Street");
				client.setHouse("12");
				client.setApartment("222");
				authorizationServiceClient.registrationClient(client);
				shopBookService.buyBook("Буратино", client);
			}


	}

	@Test
	public void testAu(){
		Client client = Client.builder()
				.firstname("Даниил")
				.lastname("Хворост")
				.build();
		System.out.println(authorizationServiceClient.authenticationClient(client));
	}

	@Test
	public void testSumBook(){
		System.out.println(dealRepository.sumByBook("Буратино"));
	}

	@Test
	public void testSumAuthor(){
		System.out.println(dealRepository.sumByAuthor("Толстой"));
	}

	@Test
	public void testFindByYear() {
		assertEquals(2, bookRepository.findByYear(1876).size());
		assertEquals(1, bookRepository.findByYear(1884).size());
		assertEquals(0, bookRepository.findByYear(2000).size());
	}

	@Test
	public void testFindAtPage() {
		PageRequest pageRequest = PageRequest.of(1, 1, Sort.Direction.ASC, "title");
		assertTrue(bookRepository.findAll(pageRequest)
				.get().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	}

	@Test
	public void testFindSame() {
		Book book = new Book();
		book.setYear(1876);

		assertEquals(2, bookRepository.findAll(Example.of(book)).size());
	}

	@Test
	public void testFindInRange() {
//		assertEquals(3, bookRepository.findAll(
//				BookSpecifications.byYearRange(1800, 1900)).size());
//		assertEquals(0, bookRepository.findAll(
//				BookSpecifications.byYearRange(2000, 2010)).size());
	}

	@Test
	public void testFindByAuthorLastname() {
		assertTrue(bookRepository.findByAuthor("Верн")
				.stream().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	}

	@Test
	public void testComplexQueryMethod() {
//		assertEquals(4, bookRepository.complexQueryMethod().size());
	}

}
