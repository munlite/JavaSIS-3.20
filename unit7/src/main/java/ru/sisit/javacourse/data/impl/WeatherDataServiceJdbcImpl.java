package ru.sisit.javacourse.data.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.sisit.javacourse.data.WeatherDataService;
import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherDataServiceJdbcImpl implements WeatherDataService {

    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_SELECT_ALL = "SELECT * FROM weathers";
    private static final String SQL_SAVE = "INSERT INTO weathers (city, temp, date) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_CITY = "SELECT * FROM weather WHERE city=(?)";

    public WeatherDataServiceJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String city, String temp) {
        LocalDate ld = LocalDate.now();
        jdbcTemplate.update(SQL_SAVE, city, temp, ld);
    }

    @Override
    public List<String> selectAll() {

        return jdbcTemplate.query(SQL_SELECT_ALL, ((rs,rowNum) ->
                rs.getString("date")+ " " +
                rs.getString("city")
                        + " " + rs.getString("temp")));
    }
}
