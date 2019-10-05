package Stack_Queue;

import java.util.Arrays;
import java.util.Stack;

public class Tower {
    public static void main(String[] args) {
        int[] test1 = {6, 9, 5, 7, 4};
        int[] test2 = {3, 9, 9, 3, 5, 7, 2};
        int[] test3 = {1, 5, 3, 6, 7, 6, 5};
        int[] test4 = {0};
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.solution(test4)));
    }
}

class Solution {
    public int[] solution(int[] heights) {
        Stack<Integer> towers = new Stack<>();

        for (int h : heights) {
            towers.push(h);
        }

        int[] answer = new int[heights.length];

        while (!towers.isEmpty()) {
            int value = towers.pop();
            int index = towers.size();


            int bigger_index = -1;
            for (int i = index - 1; i >= 0; i--) {

                if (value < heights[i]) {
                    bigger_index = i + 1;
                    break;
                }
            }

            if (bigger_index == -1) bigger_index = 0;

            answer[index] = bigger_index;
        }

        return answer;
    }
}
