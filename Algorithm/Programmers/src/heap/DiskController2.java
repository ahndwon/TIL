package heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class DiskController2 {
    public static void main(String[] args) {

        int[][] test = {{0, 3}, {1, 9}, {2, 6}};

        System.out.println(new SolutionDisk2().solution(test));

    }
}

class SolutionDisk2 {

    public int solution(int[][] jobs) {
        int answer = 0;

        int length = jobs.length;

//        PriorityQueue<Job2> jobList = new PriorityQueue<>();
        LinkedList<Job2> jobList = new LinkedList<>();

        for (int[] job : jobs) {
            jobList.add(new Job2(job[0], job[1]));
        }

        int time = 0;

        while (!jobList.isEmpty()) {

            for (Job2 job : jobList) {
                job.endTime = time + job.length;
                System.out.println("endTime : " + job.endTime);
            }

            jobList.sort(Job2::compareTo);

            Job2 job = jobList.poll();
            answer += job.endTime - job.startTime;
            time += job.length;

            System.out.println("poll : " + job);
        }

        System.out.println("time :" + time);
        System.out.println("length :" + length);
        System.out.println("answer :" + answer / length);


        return answer / length;
    }
}

class Job2 implements Comparable<Job2> {
    int startTime;
    int endTime;
    int length;

    public Job2(int startTime, int length) {
        this.startTime = startTime;
        this.length = length;
        this.endTime = -1;
    }

    @Override
    public String toString() {
        return "Job (" + startTime + ", " + endTime + ", " + length + ")";
    }


    @Override
    public int compareTo(Job2 o) {
        int diff = endTime - startTime;
        int otherDiff = o.endTime - o.startTime;

        return Integer.compare(diff, otherDiff);
    }
}
