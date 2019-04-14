#include <iostream>
#include <queue>
#include <vector>
#include <math.h>
#include <algorithm>


// 16236
// https://www.acmicpc.net/problem/16236

using namespace std;

int N;
int map[20][20];
bool visit[20][20];

// 위, 왼쪽, 아래,  오른쪽
int dx[4] = {-1, 1, 0, 0};
//int dx[4] = {-1, 0, 0, 1};
int dy[4] = {0, 0, -1, 1};
//int dy[4] = {0, -1, 1, 0};

int sx, sy;

class Shark {
public:
    int row;
    int column;
    int distance;
};

class Fish {
public:
    int row;
    int column;
    int distance;
};

queue<Shark> q;

// 내가 한 실수 : visit을 pop을 하고 표시함 -> 중복된 값이 탐색할때 push됨

// 아기 상어 처음 : 2
// 1초에 한 칸
// visit[20][20]
// 조건 : 물고기가 남은게 있을때까지 탐색 반복
// 출력 이동거리 (횟수) 표시 -> 좌표 차이로 계산

// 탐색 BFS
// 위방향부터 탐색 같으면 왼쪽부터
// 물고기 없을시 종료
// 물고기 찾으면 그자리로 이동 (물고기 삭제)
// 거리만큼 점수 추가
// 다시 반복

void showMap() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cout << map[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;

}

void reset_visit() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            visit[i][j] = false;
        }
    }
}

bool comp(Fish &a, Fish &b) {
    if (a.distance < b.distance)
        return true;
    else if (a.distance == b.distance) {
        if (a.row < b.row) {
            return true;
        } else if (a.row == b.row) {
            if (a.column < b.column) {
                return true;
            } else
                return false;
        } else {
            return false;
        }
    } else {
        return false;
    }
}

int bfs() {
    q.push({sx, sy, 0});
    int shark = 2;
    int ate = 0;
    int count = 0;
    int closest_distance = 999999;
    vector<Fish> next;
    bool check;

    while (!q.empty()) {
        while (!q.empty()) {
            int rx = q.front().row;
            int ry = q.front().column;
            int dist = q.front().distance;

//            visit[rx][ry] = true;

//            cout << "first : (" << rx << ", " << ry << ")" << endl;

            q.pop();

            if (map[rx][ry] != 0 && map[rx][ry] != 9 && map[rx][ry] < shark && dist <= closest_distance) {
                if (!next.empty() && next[next.size() - 1].row != rx && next[next.size() - 1].column != ry) {
                    closest_distance = closest_distance > dist ? dist : closest_distance;
                    next.push_back({rx, ry, dist});
                } else if (next.empty()) next.push_back({rx, ry, dist});
            }

            // 탐색
            for (int i = 0; i < 4; i++) {
                // 맵 밖 검사
                if (rx + dx[i] < 0 || rx + dx[i] > N - 1 || ry + dy[i] < 0 || ry + dy[i] > N - 1) continue;

                if (visit[rx + dx[i]][ry + dy[i]] == 1) {
                    continue;
                }

                if (map[rx + dx[i]][ry + dy[i]] <= shark && visit[rx + dx[i]][ry + dy[i]] != 1) {
                    visit[rx + dx[i]][ry + dy[i]] = true;
                    q.push({rx + dx[i], ry + dy[i], dist + 1});
                }
            }
        }

        if (next.empty()) return count;

//        int rx = 999999;
//        int ry = 999999;
//        int max_distance = 999999;

        sort(next.begin(), next.end(), comp);
//
//        for (int i = 0; i < next.size(); i++) {
//            int nx = next[i].row;
//            int ny = next[i].column;
//            int dist = next[i].distance;
////            cout << "next $" << i << " : (" << nx << ", " << ny << ")" << endl;
//
//            if (dist < max_distance) {
//                max_distance = dist;
//
//                rx = nx;
//                ry = ny;
//            }
//
//            if (dist == max_distance) {
//                if (nx < rx) {
//                    rx = nx;
//                    ry = ny;
//                }
//
//                if (nx == rx) {
//                    if (ny < ry) {
//                        rx = nx;
//                        ry = ny;
//                    }
//                }
//            }
//        }

        int rx = next.at(0).row;
        int ry = next.at(0).column;
        int max_distance = next.at(0).distance;

//        int distance = abs(rx - sx) + abs(ry - sy);

        if (map[rx][ry] != 0 && map[rx][ry] != 9 && map[rx][ry] < shark) {
//            cout << "eat : (" << rx << ", " << ry << ")" << endl;
//            cout << "distance : " << max_distance << endl;
//            cout << "shark : " << shark << endl;
//            cout << "ate : " << ate << endl;

            ate++;
            if (ate == shark) {
                ate = 0;
                shark++;
            }
            count += max_distance;

//            cout << "count : " << count << endl;

            map[sx][sy] = 0;
            map[rx][ry] = 9;
            sx = rx;
            sy = ry;
            closest_distance = 999999;
            next.clear();
            reset_visit();
//            showMap();

            while (!q.empty()) {
                q.pop();
            }
            q.push({sx, sy, 0});
//            continue;
        }
    }
    return count;
}

int main() {
    cin >> N;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            int tile;
            cin >> tile;
            map[i][j] = tile;

            if (tile == 9) {
                sx = i;
                sy = j;
            }
        }
    }
//    showMap();

    int count = bfs();

    cout << count << endl;
}

