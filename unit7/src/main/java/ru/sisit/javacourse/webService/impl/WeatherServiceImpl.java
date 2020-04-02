package ru.sisit.javacourse.webService.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sisit.javacourse.DTO.WeatherDTO;
import ru.sisit.javacourse.webService.WeatherService;



@Service
public class WeatherServiceImpl implements WeatherService {

    private final static String URL = "https://community-open-weather-map.p.rapidapi.com/weather?q=";
    private final static String METRIC = "&mode=json&units=metric";
    private HttpHeaders headers;
    private HttpEntity httpEntity;
    private final RestTemplate restTemplate;

    private void setHttpHeader(){

        headers = new HttpHeaders();
        headers.set("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com");
        headers.set("x-rapidapi-key", "cdd96ad95amsheebcca53e65aac1p136010jsn18e30e4e6350");
    }

    public WeatherServiceImpl(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    @Override
    public String getWeather(String city) {

        setHttpHeader();
        httpEntity = new HttpEntity(headers);
        ResponseEntity<WeatherDTO> responseEntity = restTemplate.exchange(URL + city + METRIC,
                HttpMethod.GET,
                httpEntity,
                WeatherDTO.class);
        return responseEntity.getBody().getMain().getTemp() + " °C";
    }
}
