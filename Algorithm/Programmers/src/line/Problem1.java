package line;

import java.util.ArrayList;
import java.util.Scanner;

public class Problem1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int messageSize = sc.nextInt();
        int consumerCount = sc.nextInt();

        ArrayList<Message> messages = new ArrayList<>();
        Message[] consumers = new Message[consumerCount];

        for (int i = 0; i < consumerCount; i++) {
            consumers[i] = new Message(0);
        }

        for (int i = 0; i < messageSize; i++) {
            messages.add(new Message(sc.nextInt()));
        }

//        for (int i = 0; i < messageSize; i++) {
//            System.out.println(messages.get(i).progress);
//        }

        boolean isOver = true;
        int count = 0;
        while (isOver) {
//            System.out.println("#### count " + count);

            for (int i = 0; i < consumerCount; i++) {
                if (consumers[i].progress == 0) {
                    if (!messages.isEmpty()) {
                        consumers[i] = messages.get(0);
                        messages.remove(0);
                    }
                }
            }

            if (messages.isEmpty()) {
                boolean doIsOver = true;
                for (int i = 0; i < consumerCount; i++) {
                    if (consumers[i].progress != 0) doIsOver = false;
                }
                if (doIsOver) isOver = false;
            }

//            System.out.println("progress: ");
            for (int i = 0; i < consumerCount; i++) {
                if (consumers[i].progress != 0) {
//                    System.out.print(consumers[i].progress + ", ");
                    consumers[i].progress--;
                }
            }

//            System.out.println();


            count++;
        }

        System.out.println(count - 1);
    }

}

class Message {
    int progress;

    public Message(int progress) {
        this.progress = progress;
    }
}
