package Hash;

import java.util.HashMap;

class Solution1 {
    private static final int MAX_NUM = 1000000;

    public String solution(String[] participant, String[] completion) {
        String answer = "";

        HashMap<String, Integer> participantMap = new HashMap<>();
        HashMap<String, Integer> completionMap = new HashMap<>();

        for (String s : participant) {
            if (participantMap.containsKey(s)) {
                participantMap.replace(s, participantMap.get(s) + 1);
            } else {
                participantMap.putIfAbsent(s, 0);
            }
        }

        for (String s : completion) {
            if (completionMap.containsKey(s)) {
                completionMap.put(s, completionMap.get(s) + 1);
            } else {
                completionMap.put(s, 0);
            }
        }

        for (String s : participant) {
            if (!completionMap.containsKey(s)) {
                answer = s;
            } else if (completionMap.containsKey(s)) {
                if (completionMap.get(s) < participantMap.get(s)) {
                    answer = s;
                }
            }
        }

        return answer;
    }
}

