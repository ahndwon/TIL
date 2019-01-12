package Hash;

public class Solution2 {
    public boolean solution(String[] phone_book) {
        boolean answer = true;

        for (int i = 0; i < phone_book.length - 1; i++) {
            String prefix = phone_book[i];

            for (int j = 0; j < phone_book.length; j++) {
                if (j != i) {
                    String target = phone_book[j];
                    if (!check(prefix, target)) {
                        answer = false;
                    }
                }
            }
        }


        return answer;
    }

    private boolean check(String prefix, String target) {
        int length = prefix.length();

        if (length > target.length())
            return true;

        return !prefix.equals(target.substring(0, length));
    }
}
