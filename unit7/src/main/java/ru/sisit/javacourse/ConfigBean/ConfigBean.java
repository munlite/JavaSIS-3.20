package ru.sisit.javacourse.ConfigBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import ru.sisit.javacourse.data.WeatherDataService;


import javax.annotation.PostConstruct;

@Configuration
public class ConfigBean {

    @Bean
    public RestTemplate getRestTemplate(){

        return new RestTemplate();
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @PostConstruct
    private void createWeatherTable(){

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS weathers (city text, temp text)");
    }

}
