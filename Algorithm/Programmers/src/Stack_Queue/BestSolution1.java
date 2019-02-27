package Stack_Queue;

import java.util.Stack;

public class BestSolution1 {

    public int solution(String arrangement) {
        int answer = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arrangement.length(); i++) {
            if (arrangement.charAt(i) == '(') {
                stack.push(i);
            } else if (arrangement.charAt(i) == ')') {
                if (stack.peek() + 1 == i) {
                    stack.pop();
                    answer += stack.size();
                } else {
                    stack.pop();
                    answer += 1;
                }
            }
        }

        return answer;
    }

}
