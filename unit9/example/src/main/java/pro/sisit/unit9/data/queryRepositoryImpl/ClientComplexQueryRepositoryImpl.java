package pro.sisit.unit9.data.queryRepositoryImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import pro.sisit.unit9.data.queryRepository.ClientComplexQueryRepository;
import pro.sisit.unit9.entity.Client;

import java.util.List;

public class ClientComplexQueryRepositoryImpl implements ClientComplexQueryRepository {
    private JdbcTemplate jdbcTemplate;

    public ClientComplexQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Client> complexQueryMethod() {
        return jdbcTemplate.query("select id, last_name, first_name, street, house, apartment from client",
                (rs, rowNum) -> Client.builder()
                        .id(rs.getLong("id"))
                        .lastname(rs.getString("last_name"))
                        .firstname(rs.getString("first_name"))
                        .street(rs.getString("street"))
                        .house(rs.getString("house"))
                        .apartment(rs.getString("apartment"))
                        .build());
    }
}

