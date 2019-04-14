// https://www.acmicpc.net/problem/7569
#include <iostream>
#include <queue>

using namespace std;

#define rangeCheck(r, c, h) 0 <= r && r < N && 0 <= c && c < M && 0 <= h && h < H
// 핵심 : 범위, bfs,

// # 조건
// 인접한 토마토가 익어감
// 위 아래 왼쪽 오른쪽 앞 뒤
// 대각선 X
// 하루가 지나면 익음

// 2 <= M, N <= 100
// 1 <= H <= 100

// 1 : 익은 토마토
// 0 : 익지 않은 토마토
// -1 : 토마토가 들어있지 않은 칸

class Point {
public:
    int row;
    int col;
    int height;
};

int N, M, H;
int map[101][101][101];
int visit[101][101][101];
queue<Point> tomatoes;

// 위 아래 왼쪽 오른쪽 앞 뒤
int dr[6] = {0, 0, 0, 0, -1, 1};
int dc[6] = {0, 0, -1, 1, 0, 0};
int dh[6] = {-1, 1, 0, 0, 0, 0};

int answer = 0;

void showMap() {
    for (int k = 0; k < H; k++) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                cout << map[k][i][j] << " ";
            }
            cout << endl;
        }
        cout << endl;
    }
    cout << endl;
}

void bfs() {

    while (!tomatoes.empty()) {
//        cout << "bfs" << endl;

        int size = tomatoes.size();
//        cout << "size : " << size << endl;

        while (size--) {
            int r = tomatoes.front().row;
            int c = tomatoes.front().col;
            int h = tomatoes.front().height;

            tomatoes.pop();
            visit[h][r][c] = true;

            for (int i = 0; i < 6; i++) {
                int ddr = r + dr[i];
                int ddc = c + dc[i];
                int ddh = h + dh[i];

                if (rangeCheck(ddr, ddc, ddh) && map[ddh][ddr][ddc] == 0 && !visit[ddh][ddr][ddc]) {
//                    cout << "bfs : (" << ddh << ", " << ddr << ", " << ddc << ")" << endl;
                    visit[ddh][ddr][ddc] = true;
                    map[ddh][ddr][ddc] = 1;
                    tomatoes.push({ddr, ddc, ddh});
                }
            }
        }
//        showMap();
        answer++;
//        cout << "answer : " << answer << endl;
    }
    answer--;

    int count = 0;
    for (int k = 0; k < H; k++) {
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                if (map[k][i][j] == 0) count++;
            }
        }
    }
    if (count > 0) answer = -1;
}

int main() {
    cin >> M >> N >> H;

    for (int k = 0; k < H; k++) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int tile;
                cin >> tile;
                map[k][i][j] = tile;
                if (map[k][i][j] == 1) tomatoes.push({i, j, k});
                else if (map[k][i][j] == 1 || map[k][i][j] == -1) visit[k][i][j] = true;
            }
        }
    }

//    showMap();

    bfs();

    cout << answer << endl;
    return 0;
}