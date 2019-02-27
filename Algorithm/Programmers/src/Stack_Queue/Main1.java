package Stack_Queue;

public class Main1 {
    public static void main(String[] args) {
        String test = "()(((()())(())()))(())";

        Solution1 solution = new Solution1();
        BestSolution1 best = new BestSolution1();

        for (int i = 0; i < test.length(); i++) {
            System.out.print(i + ", ");
        }
        System.out.println("");
        System.out.println(test);
        System.out.println(solution.solution(test));
        System.out.println(best.solution(test));
    }
}
