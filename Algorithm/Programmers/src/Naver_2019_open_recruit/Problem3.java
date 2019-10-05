package Naver_2019_open_recruit;

import java.util.ArrayList;
import java.util.Arrays;

public class Problem3 {
    public static void main(String[] args) {
        int[][] array = {{1, 0, 5}, {2, 2, 2}, {3, 3, 1}, {4, 4, 1}, {5, 10, 2}};

        System.out.println(new Solution33().solution(array));
    }
}

class Solution33 {
    public int[] solution(int[][] data) {
        int[] answer = new int[data.length];

        int size = data.length;

        ArrayList<Document> allDocs = new ArrayList<>();
        ArrayList<Document> waitingDocs = new ArrayList<>();
        ArrayList<Integer> finishedDocs = new ArrayList<>();
        ArrayList<Document> printingDocs = new ArrayList<>();


        for (int[] doc : data) {
            allDocs.add(new Document(doc[0], doc[1], doc[2]));
        }


        for (Document docs : allDocs) {
            System.out.println("i" + docs.num);
        }

        System.out.println("docs size : " + allDocs.size());

        System.out.println("docs : " + allDocs);
        int time = 0;

        while (finishedDocs.size() != size) {

            Document nextDoc = null;

            // add to wait
            for (Document doc : allDocs) {
                if (doc.time == time) {
                    waitingDocs.add(doc);
                }
            }

            System.out.println("waitingDocs : " + waitingDocs.size());

            // getNext
            if (printingDocs.isEmpty()) {
                for (Document doc : waitingDocs) {
                    if (doc.time == time) {
                        if (nextDoc == null)
                            nextDoc = doc;
                        else {
                            if (nextDoc.pageNum == doc.pageNum) {
                                nextDoc = nextDoc.num < doc.num ? nextDoc : doc;
                            }
                        }
                    }
                }

                printingDocs.add(nextDoc);
                waitingDocs.remove(nextDoc);
            } else {
                if (printingDocs.get(0) != null) {
                    if (printingDocs.get(0).time == 0) {
//                    finishedDocs.add(printingDocs.get(0));
                        finishedDocs.add(printingDocs.get(0).num);
                        System.out.println("finished Doc " +  printingDocs.get(0).time);

                        printingDocs.remove(0);
                        continue;
                    }

                    printingDocs.get(0).time--;
                }

            }

            time++;

            // 프린팅


        }

        for (int i = 0; i < finishedDocs.size(); i++) {
            answer[i] = finishedDocs.get(i);
        }

        System.out.println(Arrays.toString(answer));
        return answer;

    }

    class Document {
        int num;
        int time;
        int pageNum;

        public Document(int num, int time, int pageNum) {
            this.num = num;
            this.time = time;
            this.pageNum = pageNum;
        }
    }
}