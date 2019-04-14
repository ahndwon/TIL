#include <iostream>
#include <queue>
#include <algorithm>

using namespace std;
// ## 실수
// dfs시 재귀마다 리스트가 달라져야할 경우
// compShot()에서 distance가 같을 경우 아래쪽 행은 row가 커진다.

//
// ## 조건
// 궁수는 맽밑줄 (N+1)에 존재
// 궁수는 3명
// D거리는 r1-r2 + c1-c2
// 궁수는 거리가 D이하인 가장 가까운 적을 공격
// 같은 거리인 적이 여러명일 경우 가장 왼쪽에 있는 적 공격
// 동시에 공격 가능
// 공격 받으면 제외됨
// 공격이 끝나면 적이 이동함
// 적이 성이 있는 칸으로 이동한 경우 게임에서 제외됨
// 모든 적이 격자판에서 제외되면 게임이 끝남.

// ## 해결법
// dfs + bfs
// 궁수 자리 배치 (dfs)
// 궁수로부터 가장 가까운 적 배열에 저장
//  가장 가까운 적들 sort
//  맨 앞에 있던 적 죽임
//      죽이면 그자리 맵 0
// move -> 적들 한칸씩 아래로
// 반복

class Point {
public:
    int row;
    int column;
};

class Shot {
public:
    Point point;
    int distance;
};

class Archer {
public:
    Point point;
    vector<Shot> availableShots;
};

int map[16][15] = { 0 };
vector<Point> enemies;
vector<Archer> ach;
int maxKill;
int visit[15];
int N;
int M;
int D;

void showArchers(vector<Archer> archers) {
    cout << "archers : ";
    for (int i = 0; i < archers.size(); i++) {
        cout << "(" << archers[i].point.row << ", " << archers[i].point.column << ") ";
    }
    cout << endl;
}

void showMap(int m[16][15], vector<Archer> arch) {
    for (int i = 0; i < 3; i++) {
        m[N][arch[i].point.column] = 2;
    }
    for (int i = 0; i <= N; i++) {
        for (int j = 0; j < M; j++) {
            cout << m[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}

bool compShot(Shot &a, Shot &b) {
    if (a.distance != b.distance) {
        return a.distance < b.distance;
    } else {
        if (a.point.row == b.point.row)
            return a.point.column < b.point.column;
        else return a.point.row > b.point.row;
    }
}



int dist(Point a, Point b) {
    return abs(a.row - b.row) + abs(a.column - b.column);
}


void play(int idx, int cnt, vector<Archer> archers) {
//    visit[idx] = 1;
//    cout << "idx : " << idx << endl;
//    cout << "cnt play : " << cnt << endl;
    archers.push_back({N, idx});

    // 게임 시작
    if (cnt == 2) {
        int mapCopy[16][15] = { 0 };

        // 맵 복사
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                mapCopy[i][j] = map[i][j];
                if (map[i][j]) enemies.push_back({i, j});
            }
        }


//        showArchers(archers);
        int kills = 0;


        while (true) {
            if (enemies.empty()) {
                maxKill = max(kills, maxKill);
                enemies.clear();
                archers.clear();
                archers.push_back({N, 0});
                return;
            }
//            showMap(mapCopy, archers);

//            cout << "cnt 2: " << cnt << endl;

            vector<Shot> shots;
            // shoot
            for (Archer &a : archers) {
                for (Point &enemy : enemies) {
                    int distance = dist(enemy, a.point);
                    if (distance <= D) {
//                        cout << "distance : " << distance << endl;
                        a.availableShots.push_back({enemy, distance});
//                        cout << "availableShots.size() : " << a.availableShots.size() << endl;
                    }
                }
            }

            // kill
            for (Archer &a : archers) {
//                cout << "archer : " << a.availableShots.size() << endl;

                if (a.availableShots.empty()) continue;
                sort(a.availableShots.begin(), a.availableShots.end(), compShot);

                int row = a.availableShots.at(0).point.row;
                int column = a.availableShots.at(0).point.column;

//                cout << "archer " << a.point.column << " : " << "shot (" << row << ", " << column << ")" << endl;

                if (mapCopy[row][column] == 1) {
                    mapCopy[row][column] = 0;
                    kills++;
//                    cout << "killed : " << "(" << row << ", " << column << ")" << endl;
//                    cout << "kill : " << kills << endl;
                }
            }
//            cout << endl;

            // reset
            enemies.clear();
            for (Archer &a : archers) {
                a.availableShots.clear();
            }

            // move
            for (int i = N - 1; i >= 0; i--) {
                for (int j = 0; j < M; j++) {
                    if (mapCopy[i][j] == 1) {
                        mapCopy[i][j] = 0;
                        if (i == N - 1) continue;
                        mapCopy[i + 1][j] = 1;
                        enemies.push_back({i + 1, j});
                    }
                }
            }
        }
    }

    for (int i = idx + 1; i < M; i++) {
            play(i, cnt + 1, archers);
    }
}

int main() {
    cin >> N >> M >> D;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cin >> map[i][j];
            if (map[i][j] == 1) enemies.push_back({i, j});
        }
    }

    if (D > N - 1) cout << 0 << endl;

    play(0, 0, ach);




    cout << maxKill << endl;
}