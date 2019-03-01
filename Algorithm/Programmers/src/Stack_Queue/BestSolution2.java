package Stack_Queue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BestSolution2 {
    public int solution(int[] priorities, int location) {
        int answer = 0;

        Queue<Integer> que = new LinkedList<Integer>();
        for (int i : priorities) {
            que.add(i);
        }

        Arrays.sort(priorities);
        int size = priorities.length - 1;


        while (!que.isEmpty()) {
            Integer i = que.poll();
            if (i == priorities[size - answer]) {
                answer++;
                location--;
                if (location < 0)
                    break;
            } else {
                que.add(i);
                location--;
                if (location < 0)
                    location = que.size() - 1;
            }
        }

        return answer;
    }
}
