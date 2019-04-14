#include <iostream>
#include <queue>
#include <vector>
#include <math.h>

// 16236
// https://www.acmicpc.net/problem/16236

using namespace std;

int N;
int M;
int map[20][20];
bool visit[20][20];

// 위, 왼쪽, 아래,  오른쪽
int dx[4] = {-1, 0, 0, 1};
int dy[4] = {0, -1, 1, 0};

int sx, sy;

queue<pair<int, int>> q;



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
            visit[i][j] = 0;
        }
    }
}

int bfs() {
    q.push({sx, sy});
//    visit[sx][sy] = 1;
    int shark = 2;
    int ate = 0;
    int count = 0;
    int closest_distance = 999999;
    vector<pair<int, int>> next;

    while (!q.empty()) {
//        int qs = q.size();
        while (!q.empty()) {
            int rx = q.front().first;
            int ry = q.front().second;

//        int visit[20][20];

            visit[rx][ry] = 1;

//        cout << "first : (" << rx << ", " << ry << ")" << endl;

            q.pop();

            int distance = abs(rx - sx) + abs(ry - sy);

            if (map[rx][ry] != 0 && map[rx][ry] != 9 && map[rx][ry] < shark && distance <= closest_distance) {
//                cout << "closest_distance : " << closest_distance << endl;
                if (next.size() > 0 && next[next.size() - 1].first != rx && next[next.size() - 1].second != ry) {
                    closest_distance = closest_distance > distance ? distance : closest_distance;
                    next.push_back({rx, ry});
                } else if (next.empty()) next.push_back({rx, ry});
            }
            // 먹음


            // 가장 가까운 물고기들을 검색
            // 가장 가까운 물고기가 여러마리 -> 가장 왼쪽부터 먹음


            //
            for (int i = 0; i < 4; i++) {
                // 맵 밖 검사
                if (rx + dx[i] < 0 || rx + dx[i] > N - 1 || ry + dy[i] < 0 || ry + dy[i] > N - 1) continue;

                if (map[rx + dx[i]][ry + dy[i]] <= shark && visit[rx + dx[i]][ry + dy[i]] != 1) {
//                visit[rx + dx[i]][ry + dy[i]] = 1;
                    q.push({rx + dx[i], ry + dy[i]});
                }
            }
        }

//        int closest = 999999;

        if (next.size() == 0) return count;

        int rx = 999999;
        int ry = 999999;

        int max_distance = 999999;

        for (int i = 0; i < next.size(); i++) {
            int nx = next[i].first;
            int ny = next[i].second;
            cout << "next $" << i << " : (" << nx << ", " << ny << ")" << endl;

            int distance = abs(nx - sx) + abs(ny - sy);

            if (distance < max_distance ) {
                max_distance = distance;

                rx = nx;
                ry = ny;
            }

            if (distance == max_distance) {
                if (nx < rx) {
                    rx = nx;
                    ry = ny;
                }

                if (nx == rx) {
                    if (ny < ry) {
                        rx = nx;
                        ry = ny;
                    }
                }
            }
        }

        int distance = abs(rx - sx) + abs(ry - sy);

        if (map[rx][ry] != 0 && map[rx][ry] != 9 && map[rx][ry] < shark) {
//            cout << "eat : (" << rx << ", " << ry << ")" << endl;
//            cout << "distance : " << abs(rx - sx) + abs(ry - sy) << endl;
//            cout << "shark : " << shark << endl;
//            cout << "ate : " << ate << endl;

            ate++;
            if (ate == shark) {
                ate = 0;
                shark++;
            }
            count += distance;


//            cout << "count : " << count << endl;

            map[sx][sy] = 0;
            map[rx][ry] = 9;
            sx = rx;
            sy = ry;
            closest_distance = 999999;
            next.clear();
            reset_visit();
            showMap();

            while (!q.empty()) {
                q.pop();
            }
            q.push({sx, sy});
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

