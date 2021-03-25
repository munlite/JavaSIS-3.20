package pro.sisit.unit9.data.queryRepositoryImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import pro.sisit.unit9.data.queryRepository.AuthorOfBookComplexQueryRepository;
import pro.sisit.unit9.entity.Author;
import pro.sisit.unit9.entity.AuthorOfBook;
import pro.sisit.unit9.entity.Book;

import java.util.List;

public class AuthorOfBookComplexQueryRepositoryImpl implements AuthorOfBookComplexQueryRepository {
    private JdbcTemplate jdbcTemplate;

    public AuthorOfBookComplexQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AuthorOfBook> complexQueryMethod() {
        return jdbcTemplate.query("select id, author_id, book_id from author_of_book",
                (rs, rowNum) -> AuthorOfBook.builder()
                        .id(rs.getLong("id"))
                        .author((Author) rs.getObject("author_id"))
                        .book((Book) rs.getObject("book_id"))
                        .build());
    }
}
