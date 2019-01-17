package Hash;

public class Main4 {
    public static void main(String[] args) {
        Solution4 sol4 = new Solution4();

        String[] genres = {"classic", "pop", "classic", "classic", "pop"};
        int[] plays = {500, 600, 150, 800, 2500};

        int[] answers = sol4.solution(genres, plays);
        System.out.println("answers: " + answers);
    }
}
