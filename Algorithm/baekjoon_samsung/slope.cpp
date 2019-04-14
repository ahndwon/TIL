#include <iostream>
#include <queue>

#define rangeCheck(r, c) r < 0 || r > 99 || c < 0 || c > 99


using namespace std;

// ## 조건
// 길 : 한 행 or 한 열
// 경사로 조건 : 낮은 칸에 놓임, L개의 연속된 칸에 경사로의 바닥이 모두 접해야 함.
//          낮은칸과 높은 칸 높이 차이 1
//          경사로를 놓을 낮은 칸의 높이는 모두 같고, L개의 칸이 연속되어 있어야 함.

// 경사로 실패 조건 :
// 1. 경사로 놓인 곳에 또 경사로를 놓는 경우
// 2. 낮은 칸과 높은 카의 높이 차이가 1이 아닌 경우
// 3. 낮은 지점의 칸의 높이가 모두 같지 않고, L개가 연속되지 않음
// 4. 경사로를 놓다가 범위를 벗어나는 경우


// ## 풀이법
// row 따로 col 따로
// 맵 입력시 -> 평평한 길 있는지 같이 검사
// 높이 1 차이나는 tile만 별도에 queue에 저장
// queue에서 하나씩 꺼내서 row, col에 대해 bfs

class Candidate {
public:
    int row;
    int col;
    int height;
    int isVertical;
};

int N, L;
int map[100][100];
int answer = 0;
queue<Candidate> candidates;
int slopes[100][100];


// 상하좌우
int dir[4][2] = {{-1, 0},
                 {1,  0},
                 {0,  -1},
                 {0,  1}};

void showSlope() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cout << slopes[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}

int dfs(int direction[2], int r, int c, int height, int count) {
    int nr = r + direction[0];
    int nc = c + direction[1];
    count++;

    cout << "#dfs r,c  : " << r << ", " << c << "-> nr, nc: " << nr << ", " << nc << " count : " << count<< endl;

    if (rangeCheck(nr, nc)) return count;
    if (map[nr][nc] == height) {
        if (count == L) {
            return count;
        } else {
            dfs(direction, nr, nc, height, count);
        }
    } else {
        return count;
    }
    return 0;
}

void visit(int direction[2], int r, int c, int count) {
    int nr = r + direction[0];
    int nc = c + direction[1];
    cout << "#visit r,c  : " << r << ", " << c << "-> nr, nc: " << nr << ", " << nc << " count : " << count<< endl;

    if (rangeCheck(nr, nc)) return;

    slopes[r][c] = true;
    count++;
    if (count == L) return;
    visit(direction, nr, nc, count);
}


void solve() {
    while (!candidates.empty()) {
        int r = candidates.front().row;
        int c = candidates.front().col;
        bool isVertical = candidates.front().isVertical;
        candidates.pop();

        cout << "r,c  : " << r << ", " << c << endl;
//
//        while (true) {
        if (slopes[r][c] == 1) continue;

        if (isVertical) {
            for (int i = 0; i < 2; i++) {
                int value = dfs(dir[i], r, c, map[r][c], 0);
                cout << "value isVertical : " << value << endl;
                if (dfs(dir[i], r, c, map[r][c], 0) == L) {
                    cout << "visit go " << endl;

                    visit(dir[i], r, c, 0);
                    showSlope();
                }
            }
        } else {
            for (int i = 2; i < 4; i++) {
                if (dfs(dir[i], r, c, map[r][c], 0) == L) {
                    cout << "visit go " << endl;
                    visit(dir[i], r, c, 0);
                    showSlope();
                }
            }
        }

//        }
    }
}

int main() {
    cin >> N >> L;

    // input && row flat
    for (int r = 0; r < N; r++) {
        int isFlat = 0;
        for (int c = 0; c < N; c++) {
            cin >> map[r][c];

            if (c > 0) {
                int diff = map[r][c - 1] - map[r][c];
                if (diff != 0) isFlat++;

                if (abs(diff) == 1) {
                    if (diff == -1) {
                        candidates.push({r, c - 1, map[r][c - 1], false});
                    } else {
                        candidates.push({r, c, map[r][c], false});
                    }
                }

            }
        }
        if (isFlat == 0) answer++;
    }

    // column flat check
    for (int c = 0; c < N; c++) {
        int isFlat = 0;
        for (int r = 0; r < N; r++) {
            if (r > 0) {
                int diff = map[r - 1][c] - map[r][c];
                if (diff != 0) isFlat++;

                if (abs(diff) == 1) {
                    if (diff == -1) {
                        candidates.push({r - 1, c, map[r - 1][c], true});
                    } else {
                        candidates.push({r, c, map[r][c], true});
                    }
                }
            }
        }
        if (isFlat == 0) answer++;
    }

    cout << "candidates : " << candidates.size() << endl;

    solve();



    cout << answer << endl;

}