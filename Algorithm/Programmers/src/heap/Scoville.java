package heap;

import java.util.PriorityQueue;

public class Scoville {
    public static void main(String[] args) {
        int[] test1 = {1, 2, 3, 9, 10, 12};
        System.out.println(new Solution().solution(test1, 7));
    }
}

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int i : scoville) {
            queue.add(i);
        }

        int count = 0;
        boolean isImpossible = false;

        while (!queue.isEmpty()) {

            if (queue.peek() > K) break;
            if (queue.size() == 1) {
                isImpossible = true;
                break;
            }

            queue.add(mix(queue));
            count++;
        }


        if (isImpossible) return -1;
        else
            return count;
    }

    int mix(PriorityQueue<Integer> queue) {
        Integer least = queue.peek() != null ? queue.poll() : 0;
        Integer secondLeast = queue.peek() != null ? queue.poll() : 0;
        return least + (secondLeast * 2);
    }
}
