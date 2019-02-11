package Stack_Queue;

import java.util.ArrayList;

public class Solution1 {
    public int solution(String arrangement) {
        char[] arrangementArray = arrangement.toCharArray();

        int answer = 0;

        ArrayList<Bar> bars = extractBars(arrangementArray);

        for (Bar bar : bars) {
            System.out.println(bar);
        }


        return answer;
    }


    private ArrayList<Bar> extractBars(char[] arrangement) {
        ArrayList<Bar> bars = new ArrayList<>();

        for (int i = 0; i < arrangement.length; i++) {
            if (arrangement[i] == '(') {

                if (arrangement[i + 1] != ')') {
                    bars.add(extractBar(arrangement, i));
                }

            }
        }
        return bars;
    }

    private Bar extractBar(char[] arrangement, int i) {
        int count = 0;
        int start = -1;
        int end = -1;
        for (int j = i; j < arrangement.length; j++) {
            if (arrangement[j] == '(') {
                if(count==0) {
                    start = j;
                }
                count++;
            } else {
                count--;
                if (count == 0) {
                    end = j;
                    break;
                }
            }
        }
        return new Bar(start, end);
    }

    class Bar {
        int start;
        int end;

        Bar(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Bar(" + start + ", " + end + ")" ;
        }
    }

    class Laser {
        int start;
        int end;


    }
}

