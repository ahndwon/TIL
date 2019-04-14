#include <iostream>
#include <math.h>


using namespace std;

// 문제 조건
// 1. 팀을 두 팀으로 나눔 (N / 2) (N은 짝수) (4 <= N <= 20)
// 2. S[i][j]는 i와 j가 같은 팀일 떄, 팀에 더해지는 능력치
// 3. S[i][j] + S[j][i]
// 4. 능력치 차이 최소값 출력
// 5. i != j

// 해결법 (brute-force)
// 1. N / 2 만큼 두팀으로 나눔
// 2. 최저값 저장
// 3. 역대 최저값 출력

int N;
int players[20][20];
int answer = 999999;

void show(bool visit[20][20]) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cout << visit[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}

void showPlayers(int visit[20][20]) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cout << visit[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}


void solve() {
    bool teamAvisit[20][20] = {false};
//    bool teamB[20][20] = { false };

    while (true) {
        int playerCnt = N / 2;
        int teamA = 0;
        int teamB = 0;
        bool visit[20][20] = {false};

        // A team
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (!visit[i][j] && !teamAvisit[i][j] && i != j && playerCnt > 0) {
                    playerCnt--;
                    teamA += players[i][j] + players[j][i];
                    teamAvisit[i][j] = true;
                    teamAvisit[j][i] = true;
//                teamB += players[i][j] + players[j][i];

                    visit[i][j] = true;
                    visit[j][i] = true;

                }
            }
        }
        cout << "visit" << endl;
        show(visit);

        // B team
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (!visit[i][j] && i != j) {
//                playerCnt--;
                    teamB += players[i][j] + players[j][i];
                }
            }
        }

        int diff = abs(teamA - teamB);
        answer = min(diff, answer);
        playerCnt = 2 / N;

        int isOver = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (teamAvisit[i][j]) isOver++;
            }
        }
        cout << "teamA" << endl;
        show(teamAvisit);
        if (isOver == N * N - N) return;
    }
}

int main() {
    cin >> N;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> players[i][j];
        }
    }
    showPlayers(players);

    solve();
    cout << answer << endl;
}