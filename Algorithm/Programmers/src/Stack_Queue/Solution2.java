package Stack_Queue;
import java.util.LinkedList;


public class Solution2 {
    public int solution(int[] priorities, int location) {
        LinkedList<Integer> queue = new LinkedList<>();
        int answer = 0;

        for (int num : priorities) {
            queue.offer(num);
        }

        while (true) {
            for (int i = 0; i < queue.size(); i++) {
                int highest = getHighestPriority(queue);
                int first = queue.peekFirst();

                if (first < highest) {
                    if (queue.isEmpty()) break;
                    queue.offerLast(queue.pollFirst());
                    if (location == 0) {
                        location = queue.size() - 1;
                    } else {
                        location--;
                    }
                } else {
                    answer++;

                    if (location == 0) {
                        return answer;
                    }

                    queue.pollFirst();
                    location--;
                }
            }
        }
    }

    private int getHighestPriority(LinkedList<Integer> queue) {
        int highest = 0;
        for (Integer integer : queue) {
            if (integer > highest) {
                highest = integer;
            }
        }
        return highest;
    }
}