package line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Problem22 {
    private static ArrayList<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String line = sc.nextLine();
        String[] split = line.split(" ");

        int size = split.length;

        int[] permutations = new int[split.length];


        for (int i = 0; i < split.length; i++) {
            permutations[i] = Integer.parseInt(split[i]);
        }

        int k = sc.nextInt();

        perm(permutations, 0);

        System.out.println(numbers);
        Collections.sort(numbers);

        System.out.println(numbers);

        String answer = numbers.get(k - 1).toString();

        if (answer.length() < size) {
            System.out.println("0" + answer);
        } else System.out.println(answer);

    }

    private static void perm(int[] arr, int pivot) {
        if (pivot == arr.length) {
            StringBuilder builder = new StringBuilder();
            for (int i : arr) {
                builder.append(i);
            }

            numbers.add(Integer.parseInt(builder.toString()));

            return; 
        }
        
        for (int i = pivot; i < arr.length; i++) {
            swap(arr, i, pivot); 
            perm(arr, pivot + 1);
            swap(arr, i, pivot); 
        } 
    } 
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
}
