package sort;

import java.util.*;

public class KthNumber {
    public static void main(String[] args) {
        int[] test1 = {1, 5, 2, 6, 3, 7, 4};
        int[][] test11 = {{2, 5, 3}, {4, 4, 1}, {1, 7, 3}};

        System.out.println(Arrays.toString(new Solution().solution(test1, test11)));
    }
}

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        int count = 0;
        for (int[] command : commands) {
            int i = command[0];
            int j = command[1];
            int k = command[2];

            answer[count] = getKthNumber(array, i - 1, j - 1, k - 1);
            count++;
        }

        return answer;
    }

    private int getKthNumber(int[] array, int i, int j, int k) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int a : array) {
            arrayList.add(a);
        }

        List<Integer> sublist = arrayList.subList(i, j + 1);
        sublist.sort((o1, o2) -> {
            if (o1 < o2) return -1;
            else if (o1.equals(o2)) return 0;
            else return 1;
        });

        return sublist.get(k);
    }
}
