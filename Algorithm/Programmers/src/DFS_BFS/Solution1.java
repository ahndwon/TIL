package DFS_BFS;

public class Solution1 {
    public int solution(int[] numbers, int target) {
        int answer = 0;

        int left = numbers.length - target;

        if (numbers.length % 2 != target % 2) {
            throw new IllegalArgumentException();
        }

        if (left < 0) {
            throw new IllegalArgumentException();
        }






        return answer;
    }

    int permutation(int n, int r) {
        int temp = r - 1;
        if (temp == 0) return n;
        return n * permutation(n - 1, temp - 1);
    }

//    int getCombination(int n, int r) {
//
//    }
//
//    int doCombination(int n) {
//
//    }
}

// 1, 1, 1, 1, 1, 1, 1, 1 8개 ,   4 , 6
