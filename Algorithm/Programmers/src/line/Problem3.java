package line;

import java.util.Scanner;

public class Problem3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] timeArray = new int[150];
        int max = 0;

        sc.nextLine();

        for (int i = 0; i < N; i++) {
            String nextLine = sc.nextLine();
            String[] split = nextLine.split(" ");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);

            for (int j = start; j < end; j++) {
                timeArray[j]++;
                max = timeArray[j] > max ? timeArray[j] : max;
            }
        }

        System.out.println(max);
    }
}