// https://www.acmicpc.net/problem/13460
// 구슬 탈출 2

#include <iostream>

using namespace std;

int N, M;
char map[10][10];
int answer;
int dir[4][2] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
pair<int, int> red;
pair<int, int> blue;
bool checkR, checkB;

void move(int direction) {
    pair<int, int> firstGo;
    pair<int, int> secondGo;
    bool redFirst;
    int prevR, prevC, nextR, nextC;

    switch (direction) {
        case 0:
            if (red.first < blue.first) {
                redFirst = true;
                firstGo = red;
                secondGo = blue;
            } else {
                redFirst = false;
                firstGo = blue;
                secondGo = red;
            }
            break;
        case 1:
            if (red.first > blue.first) {
                redFirst = true;
                firstGo = red;
                secondGo = blue;
            } else {
                redFirst = false;
                firstGo = blue;
                secondGo = red;
            }
            break;
        case 2:
            if (red.second < blue.second) {
                redFirst = true;
                firstGo = red;
                secondGo = blue;
            } else {
                redFirst = false;
                firstGo = blue;
                secondGo = red;
            }
            break;
        case 3:
            if (red.second > blue.second) {
                redFirst = true;
                firstGo = red;
                secondGo = blue;
            } else {
                redFirst = false;
                firstGo = blue;
                secondGo = red;
            }
            break;
    }

    // move first
    prevR = firstGo.first;
    prevC = firstGo.second;
    nextR = prevR + dir[direction][0];
    nextC = prevC + dir[direction][1];
    while (true) {
        if (map[nextR][nextC] == '#' || map[nextR][nextC] == 'R' ||
            map[nextR][nextC] == 'B') {
            break;
        }
        if (map[nextR][nextC] == '.') {
            map[nextR][nextC] = map[prevR][prevC];
            map[prevR][prevC] = '.';
            prevR = nextR;
            prevC = nextC;
            nextR += dir[direction][0];
            nextC += dir[direction][1];
            continue;
        }
        if (map[nextR][nextC] == 'O') {
            map[prevR][prevC] = '.';
            prevR = nextR;
            prevC = nextC;
            if (redFirst == true) {
                checkR = true;
            } else {
                checkB = true;
            }
            break;
        }
    }
    firstGo = {prevR, prevC};

    // move second
    prevR = secondGo.first;
    prevC = secondGo.second;
    nextR = prevR + dir[direction][0];
    nextC = prevC + dir[direction][1];
    while (true) {
        if (map[nextR][nextC] == '#' || map[nextR][nextC] == 'R' ||
            map[nextR][nextC] == 'B') {
            break;
        }
        if (map[nextR][nextC] == '.') {
            map[nextR][nextC] = map[prevR][prevC];
            map[prevR][prevC] = '.';
            prevR = nextR;
            prevC = nextC;
            nextR += dir[direction][0];
            nextC += dir[direction][1];
            continue;
        }
        if (map[nextR][nextC] == 'O') {
            map[prevR][prevC] = '.';
            prevR = nextR;
            prevC = nextC;
            if (redFirst == true) {
                checkB = true;
            } else {
                checkR = true;
            }
            break;
        }
    }
    secondGo = {prevR, prevC};

    if (redFirst == true) {
        red = firstGo;
        blue = secondGo;
    } else {
        red = secondGo;
        blue = firstGo;
    }
    return;
}

void solve(int depth, int direction) {
    if (depth > 10) {
        checkR = false;
        checkB = false;
        return;
    }
    if (depth > answer) {
        checkR = false;
        checkB = false;
        return;
    }
    if (checkB == true) {
        checkR = false;
        checkB = false;
        return;
    }
    if (checkR == true) {
        checkR = false;
        checkB = false;
        answer = answer < depth ? answer : depth;
        return;
    }

    int cpMap[10][10];
    pair<int, int> cpRed;
    pair<int, int> cpBlue;
    // copy
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cpMap[i][j] = map[i][j];
        }
    }
    cpRed = red;
    cpBlue = blue;
    if (direction == 0 || direction == 1) {
        for (int i = 2; i < 4; i++) {
            move(i);
            solve(depth + 1, i);
            // restore
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    map[j][k] = cpMap[j][k];
                }
            }
            red = cpRed;
            blue = cpBlue;
        }
    } else if (direction == 2 || direction == 3) {
        for (int i = 0; i < 2; i++) {
            move(i);
            solve(depth + 1, i);
            // restore
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    map[j][k] = cpMap[j][k];
                }
            }
            red = cpRed;
            blue = cpBlue;
        }
    }
    return;
}

int main(void) {
    cin >> N >> M;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cin >> map[i][j];
            if (map[i][j] == 'R') {
                red = {i, j};
            }
            if (map[i][j] == 'B')
                blue = {i, j};
        }
    }
    answer = 99999999;
    solve(0, 0);
    solve(0, 1);
    solve(0, 2);
    solve(0, 3);
    if (answer == 99999999)
        answer = -1;
    cout << answer << endl;
}

/*
5 5
#####
#..B#
#.#.#
#RO.#
#####

1

7 7
#######
#...RB#
#.#####
#.....#
#####.#
#O....#
#######

5

7 7
#######
#..R#B#
#.#####
#.....#
#####.#
#O....#
#######

5

10 10
##########
#R#...##B#
#...#.##.#
#####.##.#
#......#.#
#.######.#
#.#....#.#
#.#.#.#..#
#...#.O#.#
##########

-1

3 7

 #######
#R.O.B#
#######

1

10 10
##########
#R#...##B#
#...#.##.#
#####.##.#
#......#.#
#.######.#
#.#....#.#
#.#.##...#
#O..#....#
##########

7

3 10
##########
#.O....RB#
##########

-1

7 7
#######
#.....#
#.#####
#.....#
#####B#
#O..R.#
#######
*/