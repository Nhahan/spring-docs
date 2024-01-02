package org.example.data;

import java.util.HashMap;
import java.util.Map;

public class ScreenData {

    private final Map<ScreenStage, String> screenDataMap = new HashMap<>();

    public ScreenData() {
        initializeData();
    }

    public String getScreenData(ScreenStage screenStage) {
        return screenDataMap.get(screenStage);
    }

    private void initializeData() {
        screenDataMap.put(ScreenStage.MAIN, "\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"\n" +
                "아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n" +
                "\n" +
                "[ SHAKESHACK MENU ]\n" +
                "1. Burgers         | 앵거스 비프 통살을 다져만든 버거\n" +
                "2. Forzen Custard  | 매장에서 신선하게 만드는 아이스크림\n" +
                "3. Drinks          | 매장에서 직접 만드는 음료\n" +
                "4. Beer            | 뉴욕 브루클린 브루어리에서 양조한 맥주\n" +
                "\n" +
                "[ ORDER MENU ]\n" +
                "5. Order       | 장바구니를 확인 후 주문합니다.\n" +
                "6. Cancel      | 진행중인 주문을 취소합니다.");
        screenDataMap.put(ScreenStage.MENU, " \"SHAKESHACK BURGER 에 오신걸 환영합니다.\"\n" +
                "아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.\n" +
                "\n" +
                "[ Burgers MENU ]\n" +
                "1. ShackBurger   | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거\n" +
                "2. SmokeShack    | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거\n" +
                "3. Shroom Burger | W 9.4 | 몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거\n" +
                "3. Cheeseburger  | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거\n" +
                "4. Hamburger     | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거");
        screenDataMap.put(ScreenStage.ORDER, "\"Hamburger     | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거\"\n" +
                "위 메뉴를 장바구니에 추가하시겠습니까?\n" +
                "1. 확인        2. 취소");
        screenDataMap.put(ScreenStage.REMENU, "Hamburger 가 장바구니에 추가되었습니다.\n" +
                "\n" +
                "\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"\n" +
                "아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n" +
                "\n" +
                "[ SHAKESHACK MENU ]\n" +
                "1. Burgers         | 앵거스 비프 통살을 다져만든 버거\n" +
                "2. Forzen Custard  | 매장에서 신선하게 만드는 아이스크림\n" +
                "3. Drinks          | 매장에서 직접 만드는 음료\n" +
                "4. Beer            | 뉴욕 브루클린 브루어리에서 양조한 맥주\n" +
                "\n" +
                "[ ORDER MENU ]\n" +
                "5. Order       | 장바구니를 확인 후 주문합니다.\n" +
                "6. Cancel      | 진행중인 주문을 취소합니다.");
        screenDataMap.put(ScreenStage.DECIDE_ORDER, "아래와 같이 주문 하시겠습니까?\n" +
                "\n" +
                "[ Orders ]\n" +
                "ShackBurger   | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거\n" +
                "SmokeShack    | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거\n" +
                "\n" +
                "[ Total ]\n" +
                "W 15.8\n" +
                "\n" +
                "1. 주문      2. 메뉴판으로");
        screenDataMap.put(ScreenStage.EXIT, "주문이 완료되었습니다!\n" +
                "\n" +
                "대기번호는 [ 1 ] 번 입니다.\n" +
                "(3초후 메뉴판으로 돌아갑니다.)");
        screenDataMap.put(ScreenStage.CANCLE, "진행하던 주문을 취소하시겠습니까?\n" +
                "1. 확인        2. 취소");
        screenDataMap.put(ScreenStage.CANCLE_EXIT, "진행하던 주문이 취소되었습니다.\n" +
                "\n" +
                "\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"\n" +
                "아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n" +
                "\n" +
                "[ SHAKESHACK MENU ]\n" +
                "1. Burgers         | 앵거스 비프 통살을 다져만든 버거\n" +
                "2. Forzen Custard  | 매장에서 신선하게 만드는 아이스크림\n" +
                "3. Drinks          | 매장에서 직접 만드는 음료\n" +
                "4. Beer            | 뉴욕 브루클린 브루어리에서 양조한 맥주\n" +
                "\n" +
                "[ ORDER MENU ]\n" +
                "5. Order       | 장바구니를 확인 후 주문합니다.\n" +
                "6. Cancel      | 진행중인 주문을 취소합니다.");
    }
}
