package heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class DoublePriorityQueue {
    public static void main(String[] args) {
        String[] test = {"I 16", "D 1"};
        String[] test2 = {"I 7", "I 5", "I -5", "D -1"};
        String[] test3 = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};

//        System.out.println(Arrays.toString(new SolutionDouble().solution(test)));
//        System.out.println(Arrays.toString(new SolutionDouble().solution(test2)));
        System.out.println(Arrays.toString(new SolutionDouble().solution(test3)));
    }


}

class SolutionDouble {
    public int[] solution(String[] operations) {
        int[] answer = {};
        PriorityQueue<Integer> minPQ = new PriorityQueue<>(Integer::compare);
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((o1, o2) -> Integer.compare(o1, o2) * -1);

        for (String operation : operations) {
            String[] op = operation.split(" ");
            int number = Integer.parseInt(op[1]);

            if (op[0].equals("I")) {
                minPQ.add(number);
                maxPQ.add(number);
            } else if (op[0].equals("D")) {
                if (minPQ.isEmpty() || maxPQ.isEmpty()) continue;

                if (number < 0) {
                    int min = minPQ.poll();
                    maxPQ.remove(min);
                } else {
                    int max = maxPQ.poll();
                    minPQ.remove(max);
                }
            }
        }


        if (maxPQ.isEmpty() || minPQ.isEmpty()) answer = new int[]{0, 0};
        else answer = new int[]{maxPQ.poll(), minPQ.poll()};

        return answer;
    }
}