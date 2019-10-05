package Stack_Queue;

import java.util.LinkedList;

public class TruckBridge {
    public static void main(String[] args) {
        SolutionTruck solution = new SolutionTruck();

//        int answer = solution.solution(2, 10, new int[]{7, 4, 5, 6});
//        int answer1 = solution.solution(100, 100, new int[]{10});
        int answer2 = solution.solution(100, 100, new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10});
        System.out.println("answer " + answer2);
    }
}

class Truck {
    int weight;
    int position;

    public Truck(int weight, int position) {
        this.weight = weight;
        this.position = position;
    }
}

class SolutionTruck {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int size = truck_weights.length;

        LinkedList<Truck> waitingTrucks = new LinkedList<>();
        LinkedList<Truck> finishTrucks = new LinkedList<>();
        LinkedList<Truck> bridge = new LinkedList<>();

        for (int tw : truck_weights) {
            waitingTrucks.add(new Truck(tw, 0));
        }

        int bridge_weight = 0;

        int answer = 0;
        while (finishTrucks.size() != size) {

//            System.out.println("bridge size : " + bridge.size());
            if (bridge.size() < bridge_length) {
                if (waitingTrucks.size() != 0 && bridge_weight + waitingTrucks.peek().weight <= weight) {
                    Truck truck = waitingTrucks.poll();
                    bridge_weight += truck.weight;
                    bridge.add(truck);
                    System.out.println("bridge.add");
                }
            }

            for (Truck truck : bridge) {
                truck.position++;
                System.out.println("truck position : " + truck.position);
            }

            Truck peek = bridge.peek();
            if (peek != null)
                System.out.println("position : " + peek.position);
            if (peek != null && peek.position == bridge_length) {
                System.out.println("peek position : " + peek.position);
                finishTrucks.add(bridge.poll());
                bridge_weight -= peek.weight;
            }

            System.out.println("count : " + answer);

            answer++;
        }
        return answer + 1;
    }
}