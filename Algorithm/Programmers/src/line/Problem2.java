package line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Problem2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();

        ArrayList<Integer> numbers = new ArrayList<>();

        while(sc.hasNext()) {
            numbers.add(sc.nextInt());
        }

        int k = numbers.get(numbers.size() - 1);

        HashSet<Integer> numberSet = new HashSet<>();

        for (int i = 0; i < numbers.size() - 2; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < numbers.size() - 2; k++) {
                builder.append(j);
            }
        }

        System.out.println(a + b);
    }
}
