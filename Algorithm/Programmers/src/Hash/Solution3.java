package Hash;

import java.util.ArrayList;
import java.util.HashMap;

public class Solution3 {

    public int solution(String[][] clothes) {
        int answer = 0;
        HashMap<String, ArrayList<String>> clothMap = new HashMap<>();

        for (String[] cloth : clothes) {
            answer++;
            if (!clothMap.containsKey(cloth[1])) {
                ArrayList<String> category = new ArrayList<>();
                category.add(cloth[0]);
                clothMap.put(cloth[1], category);
            } else {
                clothMap.get(cloth[1]).add(cloth[0]);
            }
        }

        ArrayList<Integer> count = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>(clothMap.keySet());

        for (int i = 0; i < clothMap.keySet().size(); i++) {
            count.add(clothMap.get(keys.get(i)).size());
        }

        int temp = count.get(0);
        for (int i = 0; i < count.size(); i++) {
            if (i == 0) continue;
            else {
                temp *= count.get(i);
            }
        }

        if (temp != count.get(0)) answer += temp;

        return answer;
    }

}
