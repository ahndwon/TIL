package Stack_Queue;

public class Main3 {
    public static void main(String[] args) {
//        Solution3 solution = new Solution3();
        BestSolution3 solution = new BestSolution3();

        int bridge_length = 2;
//        int bridge_length = 100;
        int weight = 10;
//        int weight = 100;
        int[] truck_weights = new int[]{7, 4, 5, 6};
//        int[] truck_weights = new int[]{10};

        System.out.println(solution.solution(bridge_length, weight, truck_weights));
    }
}
