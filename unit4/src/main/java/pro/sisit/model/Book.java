package pro.sisit.model;

import pro.sisit.adapter.AdaptedForCSVobject;

import java.util.List;
import java.util.Objects;

public class Book implements AdaptedForCSVobject {

    private String name;
    private String author;
    private String genre;
    private String isbn;

    public Book() {
    }

    public Book(String name, String author, String genre, String isbn) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return getName().equals(book.getName()) &&
            getAuthor().equals(book.getAuthor()) &&
            getGenre().equals(book.getGenre()) &&
            getIsbn().equals(book.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getGenre(), getIsbn());
    }

    @Override
    public void fillingInAnObjectField(List<String> list) {
        name = list.get(0);
        author = list.get(1);
        genre = list.get(2);
        isbn = list.get(3);
    }
}
