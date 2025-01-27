package com.example.extend;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 어떤 휴먼의 집이 있는 겁니다.
        List<Human> humanHouse = new ArrayList<>();
        HomoSaphiense 김선용 = new HomoSaphiense();
        HomoSaphiense 김선순 = new HomoSaphiense();
        HomoSaphiense DavidPai = new HomoSaphiense();
        humanHouse.add(김선용);
        humanHouse.add(김선순);
        humanHouse.add(DavidPai);

        // 현대 인류의 피에 네안데르탈인의 피가 흐르고 있다! (둘이 같이 살았다)

        Neandertal 착한네안데르탈인 = new Neandertal();

        humanHouse.add(착한네안데르탈인);
        // 드디어! 호모사피엔스와 착한네안데르탈인이 같이 한 지붕에서 행복하게 살았답니다!

        // 다형성!!!!!!!!!!
    }
}
