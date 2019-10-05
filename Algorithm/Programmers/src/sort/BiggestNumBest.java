package sort;

import java.util.ArrayList;

public class BiggestNumBest {
    public static void main(String[] args) {
        int[] test1 = {6, 10, 2};
        int[] test2 = {3, 30, 34, 5, 9};
        int[] test3 = {0, 0, 0, 0};

        System.out.println(new SolutionBiggestBest().solution(test3));
    }
}

class SolutionBiggestBest {
    public String solution(int[] numbers) {

        ArrayList<Integer> numList = new ArrayList<>();

        for (int n : numbers) {
            numList.add(n);
        }

        numList.sort((o1, o2) -> {
            String a = String.valueOf(o1);
            String b = String.valueOf(o2);
            return Integer.compare(Integer.parseInt(a + b), Integer.parseInt(b + a)) * -1;
        });


        StringBuilder answer = new StringBuilder();

        for (Integer num : numList) {
            answer.append(num);
        }

        if (answer.toString().toCharArray()[0] == '0') return "0";

        return answer.toString();
    }
}
