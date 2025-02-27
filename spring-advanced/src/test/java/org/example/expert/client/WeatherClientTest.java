package org.example.expert.client;

import org.example.expert.client.dto.WeatherDto;
import org.example.expert.domain.common.exception.ServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherClient weatherClient;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(weatherClient, "weatherApiUrl", "hello.com");
    }

    @Test
    void 오늘의_날씨를_성공적으로_가져온다() {
        // given
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        WeatherDto[] weatherDtos = { new WeatherDto(today, "Sunny") };
        ResponseEntity<WeatherDto[]> responseEntity = new ResponseEntity<>(weatherDtos, HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(WeatherDto[].class))).thenReturn(responseEntity);

        // when
        String result = weatherClient.getTodayWeather();

        // then
        assertEquals("Sunny", result);
    }

    @Test
    void 오늘에_해당하는_날씨_데이터를_찾지_못하면_ServerException_에러를_던진다() {
        // given
        String notToday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("MM-dd"));
        WeatherDto[] weatherDtos = { new WeatherDto(notToday, "Rainy") };
        ResponseEntity<WeatherDto[]> responseEntity = new ResponseEntity<>(weatherDtos, HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(WeatherDto[].class))).thenReturn(responseEntity);

        // when & then
        ServerException exception = assertThrows(ServerException.class, () -> weatherClient.getTodayWeather());
        assertEquals("오늘에 해당하는 날씨 데이터를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    void 날씨_데이터가_비어있다면_ServerException_에러를_던진다() {
        // given
        WeatherDto[] emptyWeather = new WeatherDto[0];
        ResponseEntity<WeatherDto[]> responseEntity = new ResponseEntity<>(emptyWeather, HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(WeatherDto[].class))).thenReturn(responseEntity);

        // when & then
        ServerException exception = assertThrows(ServerException.class, () -> weatherClient.getTodayWeather());
        assertEquals("날씨 데이터가 없습니다.", exception.getMessage());
    }

    @Test
    void 날씨_데이터가_null이면_ServerException_에러를_던진다() {
        // given
        ResponseEntity<WeatherDto[]> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(WeatherDto[].class))).thenReturn(responseEntity);

        // when & then
        ServerException exception = assertThrows(ServerException.class, () -> weatherClient.getTodayWeather());
        assertEquals("날씨 데이터가 없습니다.", exception.getMessage());
    }

    @Test
    void 날씨_데이터를_가져오는데_실패하면_ServerException_에러를_던진다() {
        // given
        ResponseEntity<WeatherDto[]> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        when(restTemplate.getForEntity(any(URI.class), eq(WeatherDto[].class))).thenReturn(responseEntity);

        // when & then
        ServerException exception = assertThrows(ServerException.class, () -> weatherClient.getTodayWeather());
        assertTrue(exception.getMessage().contains("날씨 데이터를 가져오는데 실패했습니다."));
    }
}
