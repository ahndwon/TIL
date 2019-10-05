package sort;

import java.util.ArrayList;
import java.util.Arrays;

public class BiggestNum {
    public static void main(String[] args) {
        int[] test1 = {6, 10, 2};
        int[] test2 = {3, 30, 34, 5, 9};

        System.out.println(new SolutionBiggest().solution(test2));
    }
}

class SolutionBiggest {
    public String solution(int[] numbers) {

        ArrayList<Integer> numList = new ArrayList<>();

        for (int n : numbers) {
            numList.add(n);
        }

        numList.sort((o1, o2) -> {
            char[] temp1 = o1.toString().toCharArray();
            char[] temp2 = o2.toString().toCharArray();

            int length = temp1.length >= temp2.length ? temp2.length : temp1.length;

            int i = 0;
            int j = 0;
            for (; i < length || j < length;) {
                System.out.println(temp1[i] + ", " + temp2[j]);

                if (temp1[i] != temp2[j]) {
                    System.out.println("!= " + temp1[i] + ", " + temp2[j]);
                    return Integer.compare(temp1[i], temp2[j]) * -1;
                } else {
                    if (i < temp1.length - 1) i++;
                    if (j < temp2.length - 1) j++;
                }
            }

            return 0;
        });


        StringBuilder answer = new StringBuilder();

        for (Integer num : numList) {
            answer.append(num);
        }


        return answer.toString();
    }
}
