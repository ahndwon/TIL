package NHN;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Problem1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Integer> cardMap = new HashMap<>();


        boolean isBuyCard = false;
        boolean isCardBought = false;
        boolean isAllSame = true;

        int cardCount = 0;

        int N = sc.nextInt();
        sc.nextLine();

        String[] cards = sc.nextLine().split(" ");

        for (String card : cards) {
            if (cardMap.containsKey(card)) cardMap.replace(card, cardMap.get(card) + 1);
            else cardMap.put(card, 1);
        }

        ArrayList<String> keyList = new ArrayList<>(cardMap.keySet());

        int max = 0;

        // max 구하기
        for (String key : keyList) {
            Integer count = cardMap.get(key);
            max = count > max ? count : max;
            cardCount += count;
        }

        // card 사
        for (String key : keyList) {
            Integer count = cardMap.get(key);
            if (!isCardBought && count == max - 1) {
                cardCount++;
                cardMap.replace(key, cardMap.get(key) + 1);
                isCardBought = true;
            }
        }

        // 결과 확인
        for (String key : keyList) {
            Integer count = cardMap.get(key);
            if (count != max) isAllSame = false;
        }

        if (isAllSame) {
            System.out.println("Y");
            System.out.println(cardCount);
            System.out.println(keyList.size());
        } else {
            System.out.println("N");
            if (isCardBought) {
                System.out.println(cardCount - 1);
                System.out.println(keyList.size());
            }
            else {
                System.out.println(cardCount);
                System.out.println(keyList.size());
            }

        }

    }


}

class Solution1 {

}
