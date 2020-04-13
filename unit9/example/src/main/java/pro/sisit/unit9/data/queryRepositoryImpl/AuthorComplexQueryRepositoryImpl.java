package pro.sisit.unit9.data.queryRepositoryImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import pro.sisit.unit9.data.queryRepository.AuthorComplexQueryRepository;
import pro.sisit.unit9.entity.Author;

import java.util.List;

public class AuthorComplexQueryRepositoryImpl implements AuthorComplexQueryRepository {
    private JdbcTemplate jdbcTemplate;

    public AuthorComplexQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> complexQueryMethod() {
        return jdbcTemplate.query("select id, first_name, last_name from author",
                (rs, rowNum) -> Author.builder()
                        .id(rs.getLong("id"))
                        .firstname(rs.getString("first_name"))
                        .lastname(rs.getString("last_name"))
                        .build());

    }
}
