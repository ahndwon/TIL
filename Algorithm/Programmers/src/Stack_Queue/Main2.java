package Stack_Queue;

public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();

        int[] priorities = new int[]{1, 1, 9, 1, 1, 1};
//        int[] priorities = new int[]{2, 1, 3, 2};
        int location = 0;

        System.out.println(solution.solution(priorities, location));
    }
}
