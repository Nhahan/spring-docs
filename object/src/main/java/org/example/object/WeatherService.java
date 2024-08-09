package org.example.object;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponseDto getWeather() {
        String url = "https://f-api.github.io/f-api/";

        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(url, String[].class);

        WeatherResponseDto weatherResponseDto = new WeatherResponseDto();
        weatherResponseDto.setWeather(List.of(responseEntity.getBody()));

        return weatherResponseDto;
    }
}
