package ru.sisit.javacourse.shellInterface;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sisit.javacourse.data.WeatherDataService;
import ru.sisit.javacourse.webService.WeatherService;

import java.util.stream.Collectors;

@ShellComponent
public class ShellCommands {

    private final WeatherService weatherService;
    private final WeatherDataService weatherDataService;
    private String city;
    private String temp;

    public ShellCommands(WeatherService weatherService, WeatherDataService weatherDataService) {
        this.weatherService = weatherService;
        this.weatherDataService = weatherDataService;
    }

    @ShellMethod("Введите команду temp и нужный Вам город ")
    public String temp(
            @ShellOption(defaultValue = "NERDY")
                    String city){
        this.city = city;
        temp = weatherService.getWeather(city);
        return temp;
    }

    @ShellMethod("Метод сохранения города и погоды")
    public String save(){
        if (temp == null){
            return "Сначала введите город";
        } else {
            weatherDataService.save(city, temp);
            return "Погода сохранена";
        }
    }

    @ShellMethod("Метод получения всех записей")
    public String showWeather(){
        return weatherDataService.selectAll().stream()
            .collect(Collectors.joining(System.lineSeparator()));
    }

}
