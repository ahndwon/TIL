// https://www.acmicpc.net/problem/7576
#include <iostream>
#include <queue>


using namespace std;
// 4 방향으로 토마토가 익음


// 며칠이 지나면 토마토가 모두 익는지
// 2 <= M, N <= 1000

// 1 : 익은 토마토
// 0 : 익지 않은 토마토
// -1 : 토마토 X

// 모두 익는데 걸리는 시간
// 모두 익지 못하면 -1 출력


// 풀이법
// dfs ㄱㄱ

// 익은 토마토들을 벡터로 저장
// 4방향으로 토마토가 있으면 다음날 익게 함

#define rangeCheck(r, c) 0 <= r && r < N && c >= 0 && c < M

int N, M;
int days = 0;

queue<pair<int, int>> tomatoes;

int map[1001][1001];
int visit[1001][1001];

// 상하좌우
int dr[4] = {-1, 1, 0, 0};
int dc[4] = {0, 0, -1, 1};

void showMap() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << map[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}


void bfs() {
    while (!tomatoes.empty()) {
        cout << "days : " << days << endl;
        int size = tomatoes.size();
        while (size--) {
            int r = tomatoes.front().first;
            int c = tomatoes.front().second;
            tomatoes.pop();

            visit[r][c] = true;

            for (int i = 0; i < 4; i++) {
                int ddr = r + dr[i];
                int ddc = c + dc[i];
                if ((rangeCheck(ddr, ddc)) && !visit[ddr][ddc] && map[ddr][ddc] == 0) {
                    cout << "bfs : (" << ddr << ", " << ddc << ")" << endl;
                    map[ddr][ddc] = 1;
                    tomatoes.push({ddr, ddc});
//                    visit[ddr][ddc] = 1;
                }
            }
        }
        showMap();
        days++;
    }

    days--;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if (map[i][j] == 0) days = -1;
        }
    }
}

int main() {
    cin >> M >> N;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cin >> map[i][j];
            if (map[i][j] == 1) {
                tomatoes.push({i, j});
            }
            if (map[i][j] == 1 || map[i][j] == -1) {
                visit[i][j] = 1;
            }
        }
    }

    bfs();

    cout << days << endl;
}