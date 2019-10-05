package line;

import java.util.Scanner;

public class Problem5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int x = sc.nextInt();
        int y = sc.nextInt();

        if (x >= n || x < 0) {
            fail();
            return;
        }
        if (y >= m || y < 0) {
            fail();
            return;
        }

        int time = x + y;
        int pathCount = calcPath(x, y);

        System.out.println(time);
        System.out.println(pathCount);
    }

    static int calcPath(int x, int y) {
        return permutation(x + y) / (permutation(x) * permutation(y));
    }

    static int permutation(int n) {
        if (n == 1) return n;

        int temp = 1;
        for (int i = n; i > 1; i--) {
            temp *= i;
        }

        return temp;
    }


    static void fail() {
        System.out.println("fail");
    }
}
