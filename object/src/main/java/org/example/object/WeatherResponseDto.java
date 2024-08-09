package org.example.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDto {
    private List<String> weather;

    // Getter and Setter
    public List<String> getWeather() {
        return weather;
    }

    public void setWeather(List<String> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "WeatherResponseDto{" +
                "weather=" + weather +
                '}';
    }
}
