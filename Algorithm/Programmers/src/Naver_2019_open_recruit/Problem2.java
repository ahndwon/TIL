package Naver_2019_open_recruit;

import java.util.Arrays;

public class Problem2 {
    public static void main(String[] args) {
        String[] array = {"######", ">#*###", "####*#", "#<#>>#", ">#*#*<", "######"};

        System.out.println(new Solution4().solution(array));
    }
}

class Solution4 {
    public int solution(String[] drum) {
        int answer = 0;

        int N = drum.length;

        char[][] map = new char[N][N];

        for (int i = 0; i < N - 1; i++) {

            char[] row = drum[i].toCharArray();

            for (int j = 0; j < N - 1; j++) {
                map[i][j] = row[j];
            }

        }

        System.out.println(Arrays.deepToString(map));

        for (int i = 0; i < N; i++) {

            boolean isMovable = true;

            Ball ball = new Ball(0, i);
            System.out.println("#######    " + i);

            while (isMovable) {
                System.out.println("ball (" + ball.row + ", " + ball.col + ")");

                switch (map[ball.row][ball.col]) {
                    case '#': {
                        ball.move();
                        break;
                    }

                    case '>': {
                        if (ball.col < N - 1) {
                            ball.col++;
                            System.out.println(">");
                        } else {
                            System.out.println("> fail");
                            isMovable = false;
                        }
                        break;
                    }

                    case '<': {
                        if (ball.col > 0) ball.col--;
                        else isMovable = false;

                        break;
                    }

                    case '*': {
                        if (ball.isStopNext) isMovable = false;
                        else ball.isStopNext = true;

                        break;
                    }

                    default: {
                        ball.move();
                        break;
                    }
                }

                if (ball.row == N) {
                    System.out.println("isAnswer: " + i);
                    answer++;
                    break;
                }

            }


        }

        System.out.println("answer : " + answer);
        return answer;
    }

    class Ball {
        int row;
        int col;
        boolean isStopNext = false;

        public Ball(int row, int col) {
            this.row = row;
            this.col = col;
        }

        void move() {
            this.row += 1;
        }
    }
}