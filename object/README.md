# 오브젝트와 의존관계

이 프로젝트는 환율을 가져오고 변환된 금액을 계산하는 간단한 결제 서비스를 구현합니다. 관심사의 분리 및 인터페이스를 사용한 유연성 확보 등의 베스트 프랙티스를 강조하여 코드를 구성했습니다.

ExRate 클래스
ExRate 클래스는 외부 API로부터 JSON 데이터를 매핑하기 위해 설계되었습니다.

java
코드 복사
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExRate {
private String result;
private Map<String, BigDecimal> rates;

    public ExRate() {
    }

    public String getResult() {
        return result;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }
}
기본 생성자: ObjectMapper가 클래스의 인스턴스를 생성하고 JSON 데이터를 필드에 매핑할 수 있도록 합니다.
@JsonIgnoreProperties(ignoreUnknown = true): 클래스에 존재하지 않는 JSON 속성이 있을 경우 이를 무시하여 오류를 방지합니다.
Payment 클래스
Payment 클래스는 주문 ID, 통화, 금액 등의 다양한 세부 정보를 포함하는 결제 엔티티를 나타냅니다.

java
코드 복사
public class Payment {
private final Long orderId;
private final String currency;
private final BigDecimal foreignCurrencyAmount;
private final BigDecimal exRate;
private final BigDecimal convertedAmount;
private final LocalDateTime validUntil;

    public Payment(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate, BigDecimal convertedAmount, LocalDateTime validUntil) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = foreignCurrencyAmount;
        this.exRate = exRate;
        this.convertedAmount = convertedAmount;
        this.validUntil = validUntil;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getForeignCurrencyAmount() {
        return foreignCurrencyAmount;
    }

    public BigDecimal getExRate() {
        return exRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", foreignCurrencyAmount=" + foreignCurrencyAmount +
                ", exRate=" + exRate +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }
}
toString() 메서드: 객체의 가독성 있는 문자열 표현을 제공하여 디버깅 및 로깅에 유용합니다.
PaymentService 클래스
PaymentService 클래스는 환율을 이용하여 Payment 객체를 준비합니다.

초기 구현
java
코드 복사
public class PaymentService {
@SneakyThrows
public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
String response = bufferedReader.lines().collect(Collectors.joining());
bufferedReader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        ExRate exrate = objectMapper.readValue(response, ExRate.class);
        BigDecimal exRate = exrate.getRates().get("KRW");

        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
리팩토링된 구현
ExRateProvider 클래스
java
코드 복사
public class ExRateProvider {
@SneakyThrows
public BigDecimal getExRate(String currency) {
URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
String response = bufferedReader.lines().collect(Collectors.joining());
bufferedReader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        ExRate exrate = objectMapper.readValue(response, ExRate.class);
        return exrate.getRates().get("KRW");
    }
}
업데이트된 PaymentService 클래스
java
코드 복사
public class PaymentService {
private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
SimpleExRateProvider 구현
java
코드 복사
public class SimpleExRateProvider {
@Override
public BigDecimal getExRate(String currency) {
if (currency.equals("USD")) {
return new BigDecimal("1000");
}

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
유연성을 위한 인터페이스 사용
IExRateProvider 인터페이스
java
코드 복사
public interface IExRateProvider {
BigDecimal getExRate(String currency);
}
인터페이스를 구현하도록 업데이트된 Provider 클래스들
java
코드 복사
public class ExRateProvider implements IExRateProvider {
@SneakyThrows
@Override
public BigDecimal getExRate(String currency) {
URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
String response = bufferedReader.lines().collect(Collectors.joining());
bufferedReader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        ExRate exrate = objectMapper.readValue(response, ExRate.class);
        return exrate.getRates().get("KRW");
    }
}
java
코드 복사
public class SimpleExRateProvider implements IExRateProvider {
@Override
public BigDecimal getExRate(String currency) {
if (currency.equals("USD")) {
return new BigDecimal("1000");
}

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
IExRateProvider를 사용하도록 업데이트된 PaymentService
java
코드 복사
public class PaymentService {
private final IExRateProvider exRateProvider;

    public PaymentService(IExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
ObjectFactory 클래스
ObjectFactory 클래스는 서비스와 프로바이더의 생성 및 구성을 처리합니다.

java
코드 복사
public class ObjectFactory {
public PaymentService paymentService() {
return new PaymentService(exRateProvider());
}

    public IExRateProvider exRateProvider() {
        return new SimpleExRateProvider();
    }
}
메인 애플리케이션
ObjectApplication 클래스는 ExRateProvider를 결정하지 않고 PaymentService를 사용하는 방법을 보여줍니다.

java
코드 복사
public class ObjectApplication {

    public static void main(String[] args) {
        ObjectFactory objectFactory = new ObjectFactory();
        PaymentService paymentService = objectFactory.paymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(55.5));
        System.out.println(payment);
    }
}
이러한 방식을 따르면 관심사의 명확한 분리로 인해 코드 유지 보수와 확장이 용이해집니다.


3/3

원래 문서의 추가 설명 같은게 다 없어졌어. 부연(추가)설명도 싹 다 넣어야돼
ChatGPT
Payment Service 구현
이 프로젝트는 환율을 가져오고 변환된 금액을 계산하는 간단한 결제 서비스를 구현합니다. 관심사의 분리 및 인터페이스를 사용한 유연성 확보 등의 베스트 프랙티스를 강조하여 코드를 구성했습니다.

ExRate 클래스
ExRate 클래스는 외부 API로부터 JSON 데이터를 매핑하기 위해 설계되었습니다.

java
코드 복사
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExRate {
private String result;
private Map<String, BigDecimal> rates;

    public ExRate() {
    }

    public String getResult() {
        return result;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }
}
<details>
<summary>추가 설명</summary>
기본 생성자: ObjectMapper가 클래스의 인스턴스를 생성하고 JSON 데이터를 필드에 매핑할 수 있도록 합니다.
@JsonIgnoreProperties(ignoreUnknown = true): 클래스에 존재하지 않는 JSON 속성이 있을 경우 이를 무시하여 오류를 방지합니다. API를 통해 반환된 데이터가 클래스와 일치하지 않으면 에러가 발생할 수 있는데, 이를 무시하기 위해 사용합니다.
</details>
Payment 클래스
Payment 클래스는 주문 ID, 통화, 금액 등의 다양한 세부 정보를 포함하는 결제 엔티티를 나타냅니다.

java
코드 복사
public class Payment {
private final Long orderId;
private final String currency;
private final BigDecimal foreignCurrencyAmount;
private final BigDecimal exRate;
private final BigDecimal convertedAmount;
private final LocalDateTime validUntil;

    public Payment(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate, BigDecimal convertedAmount, LocalDateTime validUntil) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = foreignCurrencyAmount;
        this.exRate = exRate;
        this.convertedAmount = convertedAmount;
        this.validUntil = validUntil;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getForeignCurrencyAmount() {
        return foreignCurrencyAmount;
    }

    public BigDecimal getExRate() {
        return exRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", foreignCurrencyAmount=" + foreignCurrencyAmount +
                ", exRate=" + exRate +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }
}
<details>
<summary>추가 설명</summary>
toString() 메서드: 객체의 가독성 있는 문자열 표현을 제공하여 디버깅 및 로깅에 유용합니다. 예를 들어, System.out.println(new Payment(...))을 호출할 때 toString() 메서드가 없다면 객체의 해시코드가 출력됩니다. toString()이 있으면 객체의 필드 값들이 출력되어 디버깅이 용이합니다.
</details>
PaymentService 클래스
PaymentService 클래스는 환율을 이용하여 Payment 객체를 준비합니다.

초기 구현
java
코드 복사
public class PaymentService {
@SneakyThrows
public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
String response = bufferedReader.lines().collect(Collectors.joining());
bufferedReader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        ExRate exrate = objectMapper.readValue(response, ExRate.class);
        BigDecimal exRate = exrate.getRates().get("KRW");

        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
리팩토링된 구현
ExRateProvider 클래스
java
코드 복사
public class ExRateProvider {
@SneakyThrows
public BigDecimal getExRate(String currency) {
URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
String response = bufferedReader.lines().collect(Collectors.joining());
bufferedReader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        ExRate exrate = objectMapper.readValue(response, ExRate.class);
        return exrate.getRates().get("KRW");
    }
}
업데이트된 PaymentService 클래스
java
코드 복사
public class PaymentService {
private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
<details>
<summary>추가 설명</summary>
관심사 분리: 환율을 가져오는 로직을 ExRateProvider 클래스로 분리하여 재사용성을 높이고, PaymentService는 결제 준비에 집중하도록 했습니다.
@SneakyThrows: 예외 처리를 간편하게 해주는 Lombok 어노테이션입니다. 이 어노테이션을 사용하면 명시적으로 예외를 처리하지 않아도 됩니다.
</details>
SimpleExRateProvider 구현
java
코드 복사
public class SimpleExRateProvider implements IExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
        if (currency.equals("USD")) {
            return new BigDecimal("1000");
        }

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
<details>
<summary>추가 설명</summary>
새로운 요구사항 반영: 특정 통화에 대해 간단한 환율을 반환하는 SimpleExRateProvider 클래스를 추가하여, 통화가 "USD"일 때는 환율을 무조건 1000으로 반환하도록 했습니다.
</details>
유연성을 위한 인터페이스 사용
IExRateProvider 인터페이스
java
코드 복사
public interface IExRateProvider {
    BigDecimal getExRate(String currency);
}
<details>
<summary>추가 설명</summary>
Interface 사용: 다형성을 활용하여 PaymentService가 ExRateProvider나 SimpleExRateProvider 중 어느 것을 사용할지 신경쓰지 않도록 했습니다. 인터페이스를 통해 구현체를 쉽게 교체할 수 있습니다.
@Override의 중요성: @Override 어노테이션을 사용하면 컴파일러가 해당 메서드가 상위 클래스나 인터페이스의 메서드를 오버라이드하는지 확인하고, 상위 클래스의 메서드와 시그니처가 다르면 오류를 발생시킵니다. 가독성과 유지보수 측면에서도 도움이 됩니다.
</details>
인터페이스를 구현하도록 업데이트된 Provider 클래스들
java
코드 복사
public class ExRateProvider implements IExRateProvider {
    @SneakyThrows
    @Override
    public BigDecimal getExRate(String currency) {
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = bufferedReader.lines().collect(Collectors.joining());
        bufferedReader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        ExRate exrate = objectMapper.readValue(response, ExRate.class);
        return exrate.getRates().get("KRW");
    }
}
java
코드 복사
public class SimpleExRateProvider implements IExRateProvider {
@Override
public BigDecimal getExRate(String currency) {
if (currency.equals("USD")) {
return new BigDecimal("1000");
}

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
IExRateProvider를 사용하도록 업데이트된 PaymentService
java
코드 복사
public class PaymentService {
private final IExRateProvider exRateProvider;

    public PaymentService(IExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
<details>
<summary>추가 설명</summary>
다형성 활용: PaymentService는 이제 특정 구현체에 종속되지 않고 IExRateProvider 인터페이스를 통해 다양한 환율 제공자를 사용할 수 있습니다.
</details>
ObjectFactory 클래스
ObjectFactory 클래스는 서비스와 프로바이더의 생성 및 구성을 처리합니다.

java
코드 복사
public class ObjectFactory {
public PaymentService paymentService() {
return new PaymentService(exRateProvider());
}

    public IExRateProvider exRateProvider() {
        return new SimpleExRateProvider();
    }
}
<details>
<summary>추가 설명</summary>
팩토리 패턴: ObjectFactory 클래스를 통해 객체 생성 로직을 중앙 집중화하여, 클라이언트 코드(main 메서드)가 구체적인 구현 클래스를 신경쓰지 않고도 객체를 생성할 수 있게 합니다.
</details>
메인 애플리케이션
ObjectApplication 클래스는 ExRateProvider를 결정하지 않고 PaymentService를 사용하는 방법을 보여줍니다.

java
코드 복사
public class ObjectApplication {

    public static void main(String[] args) {
        ObjectFactory objectFactory = new ObjectFactory();
        PaymentService paymentService = objectFactory.paymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(55.5));
        System.out.println(payment);
    }
}
<details>
<summary>추가 설명</summary>
메인 메서드의 간결성: main() 메서드는 이제 PaymentService를 초기화할 때 어떤 ExRateProvider를 사용할지 결정할 필요가 없습니다. 대신, ObjectFactory가 이를 처리합니다.
</details>
이러한 방식을 따르면 관심사의 명확한 분리로 인해 코드 유지 보수와 확장이 용이해집니다.
