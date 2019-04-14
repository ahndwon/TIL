package DFS_BFS;

public class BestSolution1 {

    public int solution(int[] numbers, int target) {
        int answer = 0;
        answer = dfs(numbers, 0, 0, target);
        return answer;
    }

    int dfs(int[] numbers, int n, int sum, int target) {
        if (n == numbers.length) {
            if (sum == target) {
                return 1;
            }
            return 0;
        }

        System.out.println("n : " + n + ", sum: " + sum + ", target: "+ target);
        return dfs(numbers, n + 1, sum + numbers[n], target) + dfs(numbers, n + 1, sum - numbers[n], target);
    }

}
// n = 0, target == 3
// dfs (numbers, 1, 0 + numbers[0], 3) + dfs(numbers, 1, 0 - numbers[0], 3);
