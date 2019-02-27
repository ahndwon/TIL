package Stack_Queue;

import java.util.ArrayList;

public class Solution1 {
    public int solution(String arrangement) {
        char[] arrangementArray = arrangement.toCharArray();


        ArrayList<Bar> bars = extractBars(arrangementArray);
        ArrayList<Laser> lasers = extractLasers(arrangementArray);

        for (Bar bar : bars) {
            System.out.println(bar);
        }


        return getNumberOfPieces(bars, lasers);
    }

    private int getNumberOfPieces(ArrayList<Bar> bars, ArrayList<Laser> lasers) {
        int total = 0;
        for (Bar bar : bars) {
            int piece = 1;
            for (Laser laser : lasers) {
                if (bar.start < laser.start && bar.end > laser.end) {
                    piece += 1;
                }
            }
            total += piece;
        }
        return total;
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

    private ArrayList<Laser> extractLasers(char[] arrangement) {
        ArrayList<Laser> lasers = new ArrayList<>();

        for (int i = 0; i < arrangement.length; i++) {
            if (arrangement[i] == '(') {
                if (arrangement[i + 1] == ')') {
                    lasers.add(new Laser(i, i + 1));
                }
            }
        }
        return lasers;
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

        public Laser(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}

