package heap;

import java.util.ArrayList;


// 테스트 케이스 한개 빼고 전부 실패 (시간 초과 많이 뜸)
// 잘못된 방법인듯 -> 시간 복잡도 O(n^2)
public class DiskController {
    public static void main(String[] args) {

        int[][] test = {{0, 3}, {1, 9}, {2, 6}};

        System.out.println(new SolutionDisk().solution(test));

    }
}

class SolutionDisk {
    int min = 99999999;

    public int solution(int[][] jobs) {
        int answer = 0;

        int length = jobs.length;

        ArrayList<Job> jobList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            jobList.add(new Job(jobs[i][0], jobs[i][1]));
        }

        doWork(jobList, new ArrayList<>(), 0);

        answer = min;

        return answer;
    }

    public void doWork(ArrayList<Job> jobList, ArrayList<Job> finishList, int time) {
        if (jobList.size() == 0) {
            int totalTime = 0;

            for (Job job : finishList) {
                totalTime += job.endTime - job.startTime;
            }

            System.out.println(finishList);
            System.out.println(totalTime / finishList.size());

            min = Math.min(totalTime / finishList.size(), min);
        }

        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            finishList.add(job);
            jobList.remove(i);
            job.endTime = time + job.length;

            doWork(jobList, finishList, time + job.length);

            finishList.remove(job);
            jobList.add(i, job);
        }
    }
}

class Job {
    int startTime;
    int endTime;
    int length;

    public Job(int startTime, int length) {
        this.startTime = startTime;
        this.length = length;
        this.endTime = -1;
    }

    @Override
    public String toString() {
        return "Job (" + startTime +", " + endTime + ", " + length + ")";
    }
}
