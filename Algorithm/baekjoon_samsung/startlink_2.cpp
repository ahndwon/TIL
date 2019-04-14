#include <iostream>
#include <math.h>


using namespace std;

// 문제 조건
// 1. 팀을 두 팀으로 나눔 (N / 2) (N은 짝수) (4 <= N <= 20)
// 2. S[i][j]는 i와 j가 같은 팀일 떄, 팀에 더해지는 능력치
// 3. S[i][j] + S[j][i]
// 4. 능력치 차이 최소값 출력
// 5. i != j

// 해결법 (dfs)
// 1. N / 2 만큼 두팀으로 나눔
// 2. 최저값 저장
// 3. 역대 최저값 출력

int N;
int players[20][20];
int answer = 999999;
int visit[20] = {0};


void dfs(int idx, int cnt) {
    visit[idx] = 1;

    if (cnt == (N / 2 - 1)) {
        int teamA = 0;
        int teamB = 0;

        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                if (visit[i] == 1 && visit[j] == 1) teamA += players[i][j] + players[j][i];
                if (visit[i] == 0 && visit[j] == 0) teamB += players[i][j] + players[j][i];
            }
        }

        answer = min(answer, abs(teamA - teamB));
        return;
    }

    for (int i = idx + 1; i < N; i++) {
        dfs(i, cnt + 1);
        visit[i] = 0;
    }

}

int main() {
    cin >> N;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> players[i][j];
        }
    }

    dfs(0, 0);

    cout << answer << endl;
}
