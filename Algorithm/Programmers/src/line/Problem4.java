package line;

import java.util.Scanner;

public class Problem4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int min = 20001;

        int comparison = 0;
        boolean isFirst = true;

        for (int i = 0; i < N; i++) {
            int next = sc.nextInt();

            if (next == 1) {
                if (isFirst) {
                    comparison = i;
                    isFirst = false;
                }
                else {
                    int diff = i - comparison;
                    min = min < diff ? min : diff;
                }
            }

        }

        System.out.println(min);
    }
}
