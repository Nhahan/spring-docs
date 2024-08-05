package org.example.object;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentService {

    @SneakyThrows
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        // 환율 가져오기
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = bufferedReader.lines().collect(Collectors.joining());
        bufferedReader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        ExRate exrate = objectMapper.readValue(response, ExRate.class);

        BigDecimal exRate = exrate.getRates().get("KRW");

        // 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // 유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
