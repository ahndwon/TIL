package heap;
import java.util.*;
import java.util.ArrayList;


// 테스트 케이스 한개 빼고 전부 실패 (시간 초과 많이 뜸)
// 잘못된 방법인듯 -> 시간 복잡도 O(n^2)
public class DiskControllerBestAnswer {
    public static void main(String[] args) {

        int[][] test = {{0, 3}, {1, 9}, {2, 6}};

        System.out.println(new SolutionDisk().solution(test));

    }
}


class SolutionBest {
    public static int solution(int[][] jobs) {

        Arrays.sort(jobs, (o1, o2) -> {
            // 시작 시간 기준 정렬
            if (o1[0] <= o2[0]) {
                return -1;
            }
            return 1;
        });

        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            // 작업 시간에 따른 우선순
            if (o1[1] < o2[1]) {
                return -1;
            }
            return 1;
        });

        int time = 0;
        int index = 0;
        float answer = 0;

        while (true) {
            // populate queue
            while (index < jobs.length && jobs[index][0] <= time) {
                queue.offer(jobs[index]);
                index++;
            }

            if (queue.size() == 0) {
                time = jobs[index][0];
                continue;
            }

            int[] job = queue.poll();
            time += job[1];
            answer += time - job[0];

            if (index == jobs.length && queue.size() == 0) {
                break;
            }
        }

        answer /= jobs.length;
        return (int) answer;
    }
}


class JobDisk {
    int startTime;
    int length;

    public JobDisk(int startTime, int length) {
        this.startTime = startTime;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Job (" + startTime + ", " + length + ")";
    }
}
