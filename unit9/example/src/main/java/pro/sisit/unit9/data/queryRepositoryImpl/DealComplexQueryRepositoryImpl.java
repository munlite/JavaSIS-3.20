package pro.sisit.unit9.data.queryRepositoryImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import pro.sisit.unit9.data.queryRepository.DealComplexQueryRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Client;
import pro.sisit.unit9.entity.Deal;

import java.util.List;

public class DealComplexQueryRepositoryImpl implements DealComplexQueryRepository {
    private JdbcTemplate jdbcTemplate;

    public DealComplexQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Deal> complexQueryMethod() {
        return jdbcTemplate.query("select id, id_book, id_client, price from deal",
                (rs, rowNum) -> Deal.builder()
                        .id(rs.getLong("id"))
                        .client((Client) rs.getObject("id_client"))
                        .book((Book) rs.getObject("id_book"))
                        .price(rs.getBigDecimal("price"))
                        .build());
    };
}
