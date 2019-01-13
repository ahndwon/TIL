package Hash;

import java.util.HashMap;

public class Solution2 {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        HashMap<String, Integer> substrings = new HashMap<>();


        for (String phoneNumber : phone_book) {
            for (int i = 0; i <= phoneNumber.length(); i++) {
                String sub = phoneNumber.substring(0, i);
                System.out.println("sub: " + sub);
                if (!substrings.containsKey(sub)) {
                    substrings.put(sub, 0);
                } else {
                    substrings.replace(sub, substrings.get(sub) + 1);
                }
            }
        }

        System.out.println(substrings);

        for (String phoneNum: phone_book) {
            if (substrings.containsKey(phoneNum) && substrings.get(phoneNum) > 0) {
                answer = false;
            }
        }

        return answer;
    }

}
