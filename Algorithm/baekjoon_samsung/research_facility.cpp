// https://www.acmicpc.net/problem/14502
#include <iostream>
#include <vector>
#include <queue>

using namespace std;

// ## 조건 ##
// 연구소 크기 N x M
// 벽 개수 : 3개
// 바깥쪽도 벽
// 남은 비오염구역의 최대값

// ## 출력 ##
// 안전 지역 크기

// ## 해결방법 ##
// 바이러스 (2)를 근원으로 탐색 (BFS)
// 1. 틀어막을 수 있는 공간 생기면 벽을 세움 (최대 3개)
// 2. 바이러스 퍼뜨림
// 3. 남아있는 공간 최대값

// 남아있는 공간 출력

// 벽을 어디 세울지 판

int N;
int M;

// 상하좌우
int dir[4][2] = {{-1, 0},
                 {1,  0},
                 {0,  -1},
                 {0,  1}};

int map[8][8];
//bool visit[8][8];
bool visit[8][8][8][8][8][8];

queue<pair<int, int>> viruses;
queue<pair<int, int>> q;

void showMap() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << map[i][j] << " ";
        }
        cout << endl;
    }
}

void solve() {
    while (!viruses.empty()) {
        q.push(viruses.front());
        viruses.pop();
        int wallLeft = 3;

        while (!q.empty()) {
            int qRow = q.front().first;
            int qColumn = q.front().second;
            q.pop();

            if (map[qRow][qColumn] == 2) {

            }

            for (int i = 0; i < 4; i++) {
                int nextRow = qRow + dir[i][0];
                int nextColumn = qColumn + dir[i][1];

                int nextTile = map[nextRow][nextColumn];
                if (!visit[nextRow][nextColumn] && nextTile != 2 && nextTile != 1) {
                    visit[nextRow][nextColumn] = true;
                    q.push({nextRow, nextColumn});
                }
            }
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
            if (tile == 2) {
                q.push({i, j});
            }
        }
    }
}