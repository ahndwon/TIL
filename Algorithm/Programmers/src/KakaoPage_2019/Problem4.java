package KakaoPage_2019;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Problem4 {
    static String[] dataArray;
    static int index = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int M = sc.nextInt();
        dataArray = new String[M];

//        ArrayList<Pair<Integer, String>> substrings = new ArrayList<>();

        sc.nextLine();
        // 데이터 넣기
        for (int i = 0; i < M; i++) {
            dataArray[i] = sc.nextLine();
        }


        for (int i = 0; i < M; i++) {
            index = 2;
            StringBuilder builder = new StringBuilder();

            char[] data = dataArray[i].toCharArray();

            parse3(builder, data, data[0] - 48);

            while (index < data.length - 1) {
                StringBuilder builder2 = new StringBuilder();
                parse3(builder2, data, data[index] - 48);
                builder.append(builder2);
            }
        }

    }

    public static StringBuilder parse3(StringBuilder builder, char[] data, int times) {

        while (true) {
            if (index > data.length - 1) return builder;

            // abcd일 때
            while (data[index] >= 97 && data[index] <= 122) {
                builder.append(data[index]);
                index++;
            }


            // 끝나면 builder에 append
            if (data[index] == ']') {
                // 숫자 만큼 반복해서 concat 하기
                String temp = builder.toString();

                for (int i = 0; i < times - 1; i++) {
                    builder.append(temp);
                }

                index++;
                return builder;
            }

            // 숫자를 만나면
            if (data[index] >= 50 && data[index] <= 57) {
                int t = data[index] - 48;

                index += 2;
                builder.append(parse3(new StringBuilder(), data, t));
            }
        }
    }
}