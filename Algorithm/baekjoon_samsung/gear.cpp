#include <iostream>
#include <vector>
#include <queue>

#define rangeCheck(idx) 0 < idx && idx < 5

using namespace std;

// # 조건
// 1. 8개 톱니 가진 깨의 톱니바퀴
// 2. 회전시 다른 극인 바퀴는 같은 반대 방향으로 돔.
// 3. 같은 극 일시 다른 방향으로 바퀴가 돔.
// N : 0 , S : 1
// 시계 방향 : 1
// 반시계 방향 : -1

// right : 2, left : 6

vector<int> gear[5];
//int gear2[8];
//int gear3[8];
//int gear4[8];

int N;
int answer;
vector<pair<int, int>> mv;
queue<int> q;

void showGear() {
    for (int j = 1; j < 5; j++) {
        for (int i = 0; i < 8; i++) {
            cout << gear[j][i];
        }
        cout << endl;
    }
    cout << endl;

}
// 1
void clockwise(vector<int> & g) {
    int last = g.back();
    g.pop_back();
    g.insert(g.begin(), last);
}

// - 1
void antiClockwise(vector<int> & g) {
    int front = g.front();
    g.erase(g.begin());
    g.push_back(front);
}

void turn(vector<int> &g, int dir) {
    // 원본 기어 시계방향
    if (dir == 1) {
        antiClockwise(g);
    } else { // 원본 기어 반시계방향
        clockwise(g);
    }
}

void turnSelect(vector<int> &g, int dir) {
    if (dir == 1) {
        clockwise(g);
    } else { // 원본 기어 반시계방향
        antiClockwise(g);
    }
}

void dfs(int idx, int left, int right, int dir, bool isLeft) {
    if (!(rangeCheck(idx))) return;

    int lLeft = gear[idx][6];
    int lRight = gear[idx][2];

    if (isLeft) {
        if (left != lRight && (rangeCheck(idx))) {
            dfs(idx - 1, lLeft, lRight, -dir, isLeft);
            turn(gear[idx], dir);
        }
    } else {
        if (right != lLeft && (rangeCheck(idx))) {
            dfs(idx + 1, lLeft, lRight, -dir, isLeft);
            turn(gear[idx], dir);
        }
    }
}

int solve() {
    for (int i = 0; i < mv.size(); i++) {
        int gearNum = mv[i].first;
        int dir = mv[i].second;

        int left = gear[gearNum][6];
        int right = gear[gearNum][2];

        cout << "gearNum : " << gearNum << ", dir : " << dir << ", left : " << left << ", right: " << right << endl;

        showGear();

        for (int j = 0; j < 2; j++) {
            dfs(gearNum - 1, left, right, dir, true);
            dfs(gearNum + 1, left, right, dir, false);
        }

        turnSelect(gear[gearNum], dir);
    }
}


int main() {
    for (int j = 1; j < 5; j++) {
        for (int i = 0; i < 8; i++) {
            int pole;
            scanf("%1d", &pole);
//            cin >> pole;
            gear[j].push_back(pole);
        }
    }

    cin >> N;

    for (int i = 0; i < N; i++) {
        int num, dir;
        cin >> num >> dir;
        mv.push_back({num, dir});
    }

    solve();

    showGear();

    if (gear[1][0] == 1) answer += 1;
    if (gear[2][0] == 1) answer += 2;
    if (gear[3][0] == 1) answer += 4;
    if (gear[4][0] == 1) answer += 8;

    cout << answer << endl;
}

