// https://www.acmicpc.net/problem/14502
#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

// ## 조건 ##
// 연구소 크기 N x M
// 벽 개수 : 3개
// 바깥쪽도 벽
// 남은 비오염구역의 최대값

// ## 출력 ##
// 안전 지역 크기

// ## 해결방법 ##
// 1. brute-force로 벽을 임의로 세움
// 2. 바이러스 (2)를 근원으로 바이러스 퍼뜨림 (BFS)
// 3. 0인 공간 계산
// 4. 끝까지 반복

// 남아있는 공간 출력

// 벽을 어디 세울지 판

int N;
int M;

// 상하좌우
int dir[4][2] = {{-1, 0},
                 {1,  0},
                 {0,  -1},
                 {0,  1}};

int dx[4] = {-1, 1, 0, 0};
int dy[4] = {0, 0, -1, 1};

int map[9][9];
int temp[9][9];
int cnt;
int answer = -1;

vector<pair<int, int>> blanks;

void showMap() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << map[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}

void showTemp() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << temp[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}

void bfs() {
    cnt = 0;
    bool visit[9][9] = { false };
    queue<pair<int, int>> q;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            temp[i][j] = map[i][j];
            if (map[i][j] == 2) q.push({i, j});
        }
    }

//    while (!q.empty()) {
//
//        int qs = q.size();
//        for (int i = 0; i < qs; i++) {
//
//            int x = q.front().first;
//            int y = q.front().second;
//            q.pop();
//
//            for (int d = 0; d < 4; d++) {
//                int xx = x + dx[d];
//                int yy = y + dy[d];
//
//                if (xx < 0 || xx >= N || yy < 0 || yy >= M || visit[xx][yy] || temp[xx][yy] != 0) continue;
//                visit[x][y] = 1;
//                temp[xx][yy] = 2;
//                q.push({ xx, yy });
//            }
//        }
//    }

    while (!q.empty()) {


            int fx = q.front().first;
            int fy = q.front().second;
            q.pop();

            for (int i = 0; i < 4; i++) {
                int rx = fx + dx[i];
                int ry = fy + dy[i];

                if (rx < 0 || rx >= N  || ry < 0 || ry >= M  || visit[rx][ry] || temp[rx][ry] != 0) continue;

                visit[rx][ry] = true;
                temp[rx][ry] = 2;
                q.push({rx, ry});
            }

    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if (temp[i][j] == 0) cnt++;
        }
    }

}


int main() {
    cin >> N >> M;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            int tile;
            cin >> tile;
            map[i][j] = tile;
            if (tile == 0) {
                blanks.push_back({i, j});
            }
        }
    }

    for (int i = 0; i < blanks.size() - 2; i++) {
        for (int j = i + 1; j < blanks.size() - 1; j++) {
            for (int k = j + 1; k < blanks.size(); k++) {
//                pair<int, int> first = {blanks[i].first, blanks[i].second};
//                pair<int, int> second = {blanks[j].first, blanks[j].second};
//                pair<int, int> third = {blanks[k].first, blanks[k].second};
                map[blanks[i].first][blanks[i].second] = 1;
                map[blanks[j].first][blanks[j].second] = 1;
                map[blanks[k].first][blanks[k].second] = 1;

                bfs();

//                cout << cnt << endl;
//                showTemp();
                answer = max(cnt, answer);

                map[blanks[i].first][blanks[i].second] = 0;
                map[blanks[j].first][blanks[j].second] = 0;
                map[blanks[k].first][blanks[k].second] = 0;
            }
        }
    }

    cout << answer << endl;
}