package Hash;

public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        String[] participant = new String[]{"mislav", "stanko", "mislav", "ana"};
        String[] completion= new String[]{"stanko", "mislav", "ana"};


        String result = solution.solution(participant, completion);
        System.out.println("result: " + result);
    }
}