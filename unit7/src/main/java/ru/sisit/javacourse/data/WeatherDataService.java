package ru.sisit.javacourse.data;

import java.util.List;

public interface WeatherDataService {

    void save(String city, String temp);
    List<String> selectAll();
}
