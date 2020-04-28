package pro.sisit.adapter;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import pro.sisit.adapter.impl.CSVAdapter;
import pro.sisit.model.Author;
import pro.sisit.model.Book;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// TODO: 2. Описать тестовые кейсы

public class CSVAdapterTest {

    @Test
    public void createFile() {
        Path authorFilePath = Paths.get("test-author-file.csv");
        File file =new File(authorFilePath.toUri());
        file.exists();
        Author author = new Author("Глуховский", "Москва");
        CSVAdapter<Author> authorCsvAdapter =
                new CSVAdapter(Author.class, authorFilePath);
        // TODO: создать и заполнить csv-файл для сущности Author
        try {
            FileUtils.writeStringToFile(authorFilePath.toFile(), String.format(System.lineSeparator()+"%s\n%s", author.getName(), author.getBirthPlace()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("test-author-file.csv", Paths.get("test-author-file.csv"));


    }

    @After
    public void deleteFile() {

        Path authorFilePath = Paths.get("test-author-file.csv");
        FileUtils.deleteQuietly(authorFilePath.toFile());
        Path bookFilePath = Paths.get("test-book-file.csv");
        FileUtils.deleteQuietly(bookFilePath.toFile());
    }

    @Test
    public void testRead() throws IOException {

        Path bookFilePath = Paths.get("test-book-file.csv");
        CSVAdapter<Book> bookCsvAdapter =
                new CSVAdapter(Book.class, bookFilePath);

        Book book1 = bookCsvAdapter.read(1);
        assertEquals("Глуховский", book1.getAuthor());
        assertEquals("Будущее", book1.getName());
        assertEquals("978-5-17-118366-0", book1.getIsbn());
        assertEquals("Научная фантастика", book1.getGenre());

        // TODO: написать тесты для проверки сущности автора

        Path authorFilePath = Paths.get("test-author-file.csv");

        CSVAdapter<Author> authorCsvAdapter =
                new CSVAdapter(Author.class, authorFilePath);
        Author author = authorCsvAdapter.read(1);
        assertEquals("Москва", author.getBirthPlace());
        assertEquals("Пушкин", author.getName());
    }

    @Test
    public void testAppend() throws IOException {

        Path authorFilePath = Paths.get("test-author-file.csv");
        Author newAuthor = new Author("Пушкин","Москва");
        CSVAdapter<Author> authorCSVAdapter =
                new CSVAdapter(Author.class, authorFilePath);

        int authorIndex = authorCSVAdapter.append(newAuthor);
        Author authorAtIndex = authorCSVAdapter.read(authorIndex);
        assertEquals(newAuthor, authorAtIndex);

        Path bookFilePath = Paths.get("test-book-file.csv");
        CSVAdapter<Book> bookCsvAdapter =
                new CSVAdapter(Book.class, bookFilePath);

        Book newBook = new Book(
                "1",
                "2",
                "3",
                "4");

        int bookIndex = bookCsvAdapter.append(newBook);
        Book bookAtIndex = bookCsvAdapter.read(bookIndex);
        assertEquals(newBook, bookAtIndex);
    }
}
