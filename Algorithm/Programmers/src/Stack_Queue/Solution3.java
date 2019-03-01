package Stack_Queue;

import java.util.LinkedList;
import java.util.Queue;

public class Solution3 {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;

        Queue<Integer> truckWeightQueue = new LinkedList<>();
        Queue<Integer> trucksOnBridge = new LinkedList<>();
        Queue<Integer> truckFinished = new LinkedList<>();

        int length = truck_weights.length;

        for (int truckWeight : truck_weights) {
            truckWeightQueue.add(truckWeight);
        }

        int bridgeLeft = bridge_length;
        int currentWeight = 0;

        while (truckFinished.size() != length) {
            System.out.println("answer : " + answer);
            System.out.println("trucksOnBridge : " + trucksOnBridge);
            System.out.println("truckWeightQueue : " + truckWeightQueue);
            System.out.println("bridgeLeft: " + bridgeLeft);
            System.out.println("currentWeight: " + currentWeight);

            if (!truckWeightQueue.isEmpty() && bridgeLeft > 0 && trucksOnBridge.size() < bridge_length && currentWeight < weight) {
                if (currentWeight + truckWeightQueue.peek() <= weight) {
                    int truck = truckWeightQueue.poll();
                    currentWeight += truck;
                    trucksOnBridge.add(truck);
                }
            }

            if (trucksOnBridge.size() > 0) {
                bridgeLeft--;
            }
            if (bridgeLeft < 0) {
                System.out.println("poll");
                if (trucksOnBridge.isEmpty()) continue;
                int finishedTruck = trucksOnBridge.poll();
                currentWeight -= finishedTruck;
                truckFinished.add(finishedTruck);
                bridgeLeft = 0;
            }

            if (trucksOnBridge.size() == 0) {
                System.out.println("reset");
                bridgeLeft = bridge_length;
                currentWeight = 0;
            }

            if (trucksOnBridge.size() != 0)
                answer++;
        }


        return answer + 1;
    }
}



/**
 * truck_weights 를 기반으로 queue 생성
 * trucks_on_bridge(queue) 는 다리 위에 있는 트럭
 * bridge_left = bridge_length (다리 위 빈자리)
 * current_weight = 0 (다리를 지나는 트럭들의 무게 합)
 *
 * while queue가 비지 않았을 때
 *
 *      if bridge_left 0이 아니고 truck_count가 bridge_lenth보다 작고 current_weight 보다 작거나 같을 때
 *          truck = queue를 poll
 *          if (truck + currentWeight <= weight)
 *              trucks_on_bridge.add truck
 *          else
 *              queue.add(truck)
 *          current_weight += (truck)
 *
 *      if trucks_on_bridge.size() > 0
 *          bridge_left -= trucks_on_bridge.size()
 *
 *      if bridge_left < 0
 *          current_weight -= trucks_on_bridge.poll()
 *          bridge_left++
 *
 *      if trucks_on_bridge.size() == 0
 *          bridge_left = bridge_length
 *          current_weight = 0
 **/