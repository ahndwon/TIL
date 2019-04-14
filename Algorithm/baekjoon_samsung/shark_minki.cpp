// https://www.acmicpc.net/problem/16236
// 아기 상어

#include <algorithm>
#include <iostream>
#include <queue>
#include <vector>

using namespace std;

class Shark {
public:
    int row;
    int column;
    int size;
    int eatNum;
    int curTime;
};

class Fish {
public:
    int row;
    int column;
    int distance;
};
int answer;
int N;
int map[20][20];
int dir[4][2] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} }; // 상하좌우
Shark shark;
vector<Fish> fishes;


void showMap() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cout << map[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
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

bool isInside(int row, int column) {
    return (row >= 0 && row < N && column >= 0 && column < N);
}
void solve() {
    int curR, curC, nextR, nextC;
    int sz, eN, cT;
    bool check;
    int time = 0;
    bool visited[20][20];
    queue<Shark> q;

    while (true) {
        check = false;
        sz = shark.size;
        eN = shark.eatNum;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                visited[i][j] = false;
            }
        }
        visited[shark.row][shark.column] = true;
        q.push({shark.row, shark.column, shark.size, shark.eatNum, shark.curTime});
        while (!q.empty()) {
            curR = q.front().row;
            curC = q.front().column;
            cT = q.front().curTime;
//            cout << "first : (" << curR << ", " << curC << ", " << cT << ")" << endl;

            q.pop();
            for (int i = 0; i < 4; i++) {
                nextR = curR + dir[i][0];
                nextC = curC + dir[i][1];
                if (isInside(nextR, nextC) == false)
                    continue;
                if (visited[nextR][nextC] == true)
                    continue;
                if (map[nextR][nextC] == 0 || map[nextR][nextC] == sz) {
                    visited[nextR][nextC] = true;
                    q.push({nextR, nextC, sz, eN, cT + 1});
                    continue;
                }
                if (map[nextR][nextC] > sz) {
                    visited[nextR][nextC] = true;
                    continue;
                }
                if (map[nextR][nextC] < sz) {
                    check = true;
                    visited[nextR][nextC] = true;
                    fishes.push_back({nextR, nextC, cT + 1});
                }
            }
        }
        if (check == false) {
            break;
        }
        sort(fishes.begin(), fishes.end(), comp);
        map[shark.row][shark.column] = 0;
        map[fishes.at(0).row][fishes.at(0).column] = 9;
        eN++;
        if (sz == eN) {
            sz++;
            eN = 0;
        }
        shark = {fishes.at(0).row, fishes.at(0).column, sz, eN,
                 fishes.at(0).distance};
//        showMap();
        fishes.clear();
    }
    answer = shark.curTime;
    return;
}

int main(void) {
    cin >> N;
    answer = 0;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> map[i][j];
            if (map[i][j] == 9) {
                shark = {i, j, 2, 0, 0};
                map[i][j] = 0;
            }
        }
    }
    solve();
    cout << answer << endl;
}