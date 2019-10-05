package KakaoPage_2019;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Problem5 {
    static String[] words;
    static LinkedList[] patterns;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int M = sc.nextInt();
        words = new String[M];
        patterns = new LinkedList[M];

        sc.nextLine();

        // 데이터 넣기
        for (int i = 0; i < M; i++) {
            String[] line = sc.nextLine().split(" ");
            words[i] = line[0];
            LinkedList<Character> pattern = new LinkedList<>();

            for (char c : line[1].toCharArray()) {
                pattern.add(c);
            }

            patterns[i] = pattern;
        }

        System.out.println(Arrays.toString(words));
        System.out.println(Arrays.toString(patterns));

        // 매칭
        for (int i = 0; i < M; i++) {
            boolean result = match(words[i], patterns[i]);

            // 답 출력
            System.out.println(result);
        }
    }


    // 패턴 하나씩(symbol)을 단어의 char 이랑 비교
    //  -  심볼 '*' 이면 다음 심볼이랑 word 의 char 가 같을때까지,
    //       아니면 다른 심볼이 나올때까지
    //       인덱스 증가
    //  -  심볼 '?'이면 인덱스 증가
    //  -  심볼 '알파벳' 이면 비교 후 틀리면 false 반환

    static boolean match(String word, LinkedList<Character> pattern) {
        int wordIndex = 0;

        char[] wordArray = word.toCharArray();

        while (!pattern.isEmpty()) {

            if (wordIndex == wordArray.length - 1) return true;

            Character symbol = pattern.poll();

            switch (symbol) {
                case '*': {

                    if (pattern.size() > 0) {
                        Character nextSymbol = pattern.peek();

                        while (wordArray[wordIndex] != nextSymbol &&
                                wordArray[wordIndex] != '?' &&
                                wordArray[wordIndex] != '*') {
                            wordIndex++;
                        }

                    }

                    break;
                }

                case '?': {
                    if (pattern.size() > 0) wordIndex++;
                    else return false;

                    break;
                }

                default: {
                    if (symbol == wordArray[wordIndex])
                        wordIndex++;
                    else
                        return false;

                    break;
                }
            }
        }

        if (wordIndex < wordArray.length) {
            return false;
        }

        return true;
    }
}
