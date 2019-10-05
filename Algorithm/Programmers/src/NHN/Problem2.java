package NHN;


import java.util.*;

public class Problem2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Integer> countMap = new HashMap<>();

        int N = sc.nextInt();
        sc.nextLine();

        int dequeueCount = 0;

        for (int i = 0; i < N; i++) {
            String[] command = sc.nextLine().split(" ");

            if (command[0].equals("enqueue")) {
                int num = Integer.parseInt(command[1]);
                if (countMap.containsKey(num)) countMap.replace(num, countMap.get(num) + 1);
                else countMap.put(num, 1);
            } else {
                dequeueCount++;
            }

        }

        Queue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
//        Queue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());

        ArrayList<Integer> keyList = new ArrayList<>(countMap.keySet());

        for (Integer key : keyList) {
            System.out.println("queue add : " + key + " , " + countMap.get(key));
            queue.add(countMap.get(key));
        }

        for (int i = 0; i < dequeueCount; i++) {
            System.out.println(queue.poll());
        }


    }


}

class Solution2 {

}
