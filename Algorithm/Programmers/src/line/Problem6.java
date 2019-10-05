package line;

import java.util.ArrayList;
import java.util.Scanner;


// 가로 크기 : n
// 세로 크기 : 2n-1

public class Problem6 {
    public static void main(String[] args) {

        ArrayList<Data> datas = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        String[] firstLine = sc.nextLine().split(" ");

        int N = Integer.parseInt(firstLine[0]);
        String align = firstLine[1];


        int maxRowSize = 0;

        for (int i = 0; i < N; i++) {
            String[] nextLine = sc.nextLine().split(" ");
            int size = Integer.parseInt(nextLine[0]);
            Data data = new Data(size, nextLine[1].toCharArray());
            datas.add(data);

            maxRowSize = maxRowSize > size ? maxRowSize : size;
        }

        Printer printer = new Printer(N, align, maxRowSize);

        for (Data data : datas) {
            printer.addNumber(data);
        }

        printer.print();
    }
}

class Printer {
    ArrayList<Character>[] canvas;
    String align;
    int maxRowSize;
    int maxHeight;

    Printer(int size, String align, int maxRowSize) {
        this.canvas = new ArrayList[size];
        this.align = align;
        this.maxRowSize = maxRowSize;
        maxHeight = 2 * maxRowSize - 1;

        for (int i = 0; i < canvas.length; i++) {
            canvas[i] = new ArrayList<Character>();
        }
    }

    private void addSpace() {
        for (ArrayList<Character> c : canvas) {
            c.add(' ');
        }
    }

    void addNumber(Data data) {
        int height = 2 * data.rowSize - 1;

        int startIndex = 0;

        if (align.equals("MIDDLE")) startIndex = maxHeight / 2 + 1;
        else if (align.equals("BOTTOM")) startIndex = (maxHeight / 2 + 1) / 2 + 1;


        for (int i = 0; i < data.numbers.length; i++) {
            char number = data.numbers[i];
            drawNumber(number, startIndex, data.rowSize);
            if (i != data.numbers.length - 1)
                addSpace();
        }

        // first index , size,
        // 숫자 높이 2*row - 1
    }

    private void drawNumber(char num, int startIndex, int rowSize) {
        int height = 2 * rowSize - 1;
        switch (num) {
            case '0': {
                for (int i = startIndex; i < height; i++) {
                    for (int j = 0; j < rowSize; j++) {
                        if (i == 0) {
                            canvas[j].add('#');
                        } else if (i == height - 1) {
                            canvas[j].add('#');
                        } else {
                            if (j == 0) canvas[j].add('#');
                            else if (j == rowSize - 1) canvas[j].add('#');
                            else canvas[j].add('.');
                        }
                    }
                }

                break;
            }
            case '1': {
                for (int i = startIndex; i < height; i++) {
                    for (int j = 0; j < rowSize; j++) {
                        if (i == rowSize - 1) {
                            canvas[j].add('#');
                        } else
                            canvas[j].add('.');
                    }
                }
                break;
            }
            case '2': {


                break;
            }
            case '3': {

                for (int i = startIndex; i < height; i++) {
                    for (int j = 0; j < rowSize; j++) {
                        if (i == 0) {
                            canvas[j].add('#');
                        } else if (i == height - 1) {
                            canvas[j].add('#');
                        } else if (i == rowSize) {
                            canvas[j].add('#');
                        } else {
                            if (j == rowSize - 1) canvas[j].add('#');
                            else canvas[j].add('.');
                        }
                    }
                }

                break;
            }
            case '4': {
                break;
            }
            case '5': {
                break;
            }
            case '6': {
                break;
            }
            case '7': {
                break;
            }
            case '8': {
                break;
            }
            case '9': {
                break;
            }
        }

        if (maxRowSize != rowSize)

            addRest(rowSize);

    }


    private void addRest(int rowSize) {
        switch (align) {
            case "TOP": {
                for (int i = maxHeight / 2 + 1; i < maxHeight; i++) {
                    for (int j = 0; j < rowSize; j++) {
                        canvas[i].add('.');
                    }
                }
                break;
            }
            case "MIDDLE": {
                for (int i = 0; i < maxHeight / 4; i++) {
                    for (int j = 0; j < rowSize; j++) {
                        canvas[i].add('.');
                    }
                }

                for (int i = maxHeight - (maxHeight / 4 + 1); i < maxHeight; i++) {
                    for (int j = 0; j < rowSize; j++) {
                        canvas[i].add('.');
                    }
                }
                break;
            }
            case "BOTTOM": {
                for (int i = 0; i < maxHeight / 2; i++) {
                    for (int j = 0; j < rowSize; j++) {
                        canvas[i].add('.');
                    }
                }
                break;
            }
        }

    }

    void print() {
        for (ArrayList<Character> list : canvas) {
            for (Character c : list) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}

class Data {
    int rowSize;
    char[] numbers;

    Data(int rowSize, char[] numbers) {
        this.rowSize = rowSize;
        this.numbers = numbers;
    }
}