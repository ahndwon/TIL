package NHN;

import java.util.*;

public class Problem3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Player> playerList = new ArrayList<>();

        int N = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < N; i++) {
            playerList.add(new Player(0));
        }

        int dequeueCount = 0;

        String[] commands = sc.nextLine().split(" ");

        int playerIndex = 0;
        for (int i = 0; i < commands.length; i++) {
            String command = commands[i];
//            System.out.println("command : " + command);

            switch (command) {
                case "A": {

                    Player currentPlayer = playerList.get(playerIndex);
                    currentPlayer.addCandy();
                    HashSet<Player> checkSet = new HashSet<>();

                    addFollowerCandy(currentPlayer, checkSet);

                    break;
                }

                case "J": {
                    if (playerIndex == 0) {
                        HashSet<Player> checkSet = new HashSet<>();

                        Player firstPlayer = playerList.get(playerIndex + 1);
                        firstPlayer.addCandy();

                        Player secondPlayer = playerList.get(playerList.size() - 1);
                        secondPlayer.addCandy();

                        addFollowerCandy(firstPlayer, checkSet);
                        addFollowerCandy(secondPlayer, checkSet);


                        for (Player p : checkSet) {
                            p.addCandy();
                        }

                    } else if (playerIndex == playerList.size() - 1) {
                        HashSet<Player> checkSet = new HashSet<>();
                        Player firstPlayer = playerList.get(playerIndex - 1);
                        firstPlayer.addCandy();

                        Player secondPlayer = playerList.get(0);
                        secondPlayer.addCandy();

                        addFollowerCandy(firstPlayer, checkSet);
                        addFollowerCandy(secondPlayer, checkSet);


                        for (Player p : checkSet) {
                            p.addCandy();
                        }

                    } else {

                        HashSet<Player> checkSet = new HashSet<>();
                        Player firstPlayer = playerList.get(playerIndex - 1);
                        firstPlayer.addCandy();

                        Player secondPlayer = playerList.get(playerIndex + 1);
                        secondPlayer.addCandy();

                        addFollowerCandy(firstPlayer, checkSet);
                        addFollowerCandy(secondPlayer, checkSet);


                        for (Player p : checkSet) {
                            p.addCandy();
                        }
                    }

                    break;
                }

                case "Q": {
//                    HashMap<Player>
                    for (Player p : playerList) {
                        p.addCandy();
                    }
                    break;
                }
                case "K": {
                    int follower = Integer.parseInt(commands[i + 1]);
//                    System.out.println("follower " + follower + " add to  " + playerIndex);
                    playerList.get(playerIndex).followers.add(playerList.get(follower));
                    i++;
                    break;
                }
            }

            playerIndex = (playerIndex + 1) % (playerList.size());
//            System.out.println(playerIndex);
        }

        for (int i = 0; i < playerList.size(); i++) {
            Player p = playerList.get(i);

            if (i == playerList.size() - 1)
                System.out.print(p.candyCount);
            else
                System.out.print(p.candyCount + " ");
        }
    }

    private static void addFollowerCandy(Player currentPlayer , HashSet<Player> checkSet) {


        checkSet.add(currentPlayer);

        for (Player follower : currentPlayer.followers) {
            checkSet.add(follower);
            checkSet.addAll(follower.followers);
        }

//        for (Player p : checkSet) {
//            if (checkSet.contains(p)) break;
//            checkSet.addAll(p.followers);
//        }

//        System.out.println("checkSet + " + checkSet);

    }
}

class Player {
    int candyCount;
    ArrayList<Player> followers;

    public Player(int candyCount) {
        this.candyCount = candyCount;
        this.followers = new ArrayList<>();
    }

    void addCandy() {
        candyCount++;
//        for (Player p : followers) {
//            p.addCandy();
//        }
    }

    @Override
    public String toString() {
        return "Player : " + candyCount;
    }
}
