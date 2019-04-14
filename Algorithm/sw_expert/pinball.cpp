#include <iostream>
#include <vector>
#include <unordered_map>
#include <stdio.h>      /* printf, scanf, puts, NULL */
#include <stdlib.h>     /* srand, rand */
#include <time.h>

using namespace std;

//unordered_map<int, int> tiles;

vector<int> map;
vector<int> starters;
unordered_map<int, vector<int>> wormHoles;
unordered_map<int, int> blackHoles;
int testCount;
int N;
int Nborder;

int direction = 4;
const int LEFT = 0;
const int UP = 1;
const int RIGHT = 2;
const int DOWN = 4;

int firstPosition = -1;

int score = 0;
int maxScore = 0;
int isStart = 1;


int randomPosition() {
//    srand(time(NULL));
//    int random = rand() % (N * N);
//    if (map[random] != 0) randomPosition();
//    firstPosition == random;
//    return random;

//    for (int i = 0; i < N * N; i++) {
//
//    }
}

int up(int position) {
//    if (position / N == 0) {
//        score++;
//
////        position += N;
//        direction = DOWN;
//        return position;
//    }
    direction = UP;
//    position += -N;
    return position;
}

int down(int position) {
//    if (position / N == N - 1) {
//        score++;
//
////        position -= N;
//        direction = UP;
//        return position;
//    }
    direction = DOWN;
//    position += N;
    return position;
}

int right(int position) {
//    if (position % N == N - 1) {
//        score++;
//
////        position += -1;
//        direction = LEFT;
//        return position;
//    }

    direction = RIGHT;
//    position += 1;
    return position;
}

int left(int position) {
//    if (position % N == 0) {
//        score++;
//
////        position += 1;
//        direction = RIGHT;
//        return position;
//    }

    direction = LEFT;
//    position += -1;
    return position;
}

int checkDirection(int position) {
    switch (direction) {
        case LEFT : {
            return left(position);
        }

        case RIGHT : {
            return right(position);
        }

        case UP : {
            return up(position);
        }

        case DOWN : {
            return down(position);
        }
        default:
            return position;
    }
}

int wormHoleMove(int number, int position) {
    for (int i = 0; i < wormHoles[number].size(); i++) {
        if (wormHoles[number][i] != position) {
            return wormHoles[number][i];
        }
    }
    return position;
}


int checkTile(int position) {
    int tile = map[position];
//    cout << "tile : " << tile << endl;
    switch (tile) {
        case 1: {
            score++;

            if (direction == DOWN) return right(position);
            else if (direction == LEFT) return up(position);
            else if (direction == RIGHT) return left(position);
            else return down(position);

        }

        case 2: {
            score++;

            if (direction == DOWN) return up(position);
            else if (direction == LEFT) return down(position);
            else if (direction == RIGHT) return left(position);
            else return right(position);
        }

        case 3: {
            score++;

            if (direction == DOWN) return up(position);
            else if (direction == LEFT) return right(position);
            else if (direction == RIGHT) return down(position);
            else return left(position);
        }

        case 4: {
            score++;

            if (direction == DOWN) return left(position);
            else if (direction == LEFT) return right(position);
            else if (direction == RIGHT) return up(position);
            else return down(position);
        }

        case 5: {
            score++;

            if (direction == DOWN) return up(position);
            else if (direction == LEFT) return right(position);
            else if (direction == RIGHT) return left(position);
            else return down(position);
        }

        case 6: {
            return wormHoleMove(6, position);
        }

        case 7: {
            return wormHoleMove(7, position);
        }

        case 8: {
            return wormHoleMove(8, position);
        }

        case 9: {
            return wormHoleMove(9, position);
        }

        case 10: {
            return wormHoleMove(10, position);
        }

        default:
            return position;
    }
}

int moveByDirection(int position) {
    switch (direction) {
        case LEFT: {
            return position += -1;
        }
        case RIGHT: {
            return position += 1;
        }
        case UP: {
            return position -= N;
        }

        case DOWN: {
            return position += N;
        }
    }
}

void showDirection() {
    if (direction == LEFT) cout << "LEFT" << endl;
    if (direction == UP) cout << "UP" << endl;
    if (direction == DOWN) cout << "DOWN" << endl;
    if (direction == RIGHT) cout << "RIGHT" << endl;
}

// recursive
void move(int position) {
    if (isStart > 10) return;
    if (isStart == 0 && position == firstPosition) return;

    if (map[position] == -1) return;
    cout << "position: " << position << endl;
    showDirection();

//    int next = checkDirection(position);
    position = checkDirection(position);

    position = moveByDirection(position);
    position = checkTile(position);
////    position = checkTile(next);
////    cout << "next : " << next << endl;
    cout << "next : " << position << endl;
    cout << "afterPosition : " << position << endl;
//    cout << "direction : " << direction << endl;
    cout << "firstPosition : " << firstPosition << endl;
    cout << "score : " << score << endl;
    cout << endl;
//    isStart = 0;
    isStart++;

//    if (position == firstPosition) return;
    move(position);
}

// recursive
int moveTest(int position) {
    if (!isStart && position == firstPosition) return position;

    if (map[position] == -1) return position;
    cout << "position: " << position << endl;
    showDirection();

//    int next = checkDirection(position);
    position = checkDirection(position);

    if (position < 0 || position > N * N - 1) return position;
    position = moveByDirection(position);
    position = checkTile(position);
////    position = checkTile(next);
////    cout << "next : " << next << endl;
    cout << "next : " << position << endl;
    cout << "afterPosition : " << position << endl;
//    cout << "direction : " << direction << endl;
    cout << "firstPosition : " << firstPosition << endl;
    cout << "score : " << score << endl;
    cout << endl;

//    if (position == firstPosition) return position;
    isStart = 0;
    return position;
//    move(position);
}

// recursive

int getMax() {
    for (int i = 0; i < starters.size(); i++) {
        for (int dir = 0; dir < 4; dir++) {
            direction = dir;
            score = 0;
            firstPosition = starters[i];
//            firstPosition = 2;
            isStart = 1;
//            cout << "fucking starter " << starters[i] << endl;
            move(starters[i]);
//            move(2);
            maxScore = maxScore < score ? score : maxScore;

//            for (int k = 0 ; k < 10; k++) {
//                moveTest(starters[i]);
//            }
        }
    }
}


int main() {
    cin >> testCount;

    for (int i = 1; i <= testCount; i++) {
        cin >> N;

        Nborder = N + 2;
        starters.clear();
        wormHoles.clear();
        map.clear();
        score = 0;
        maxScore = 0;


        for (int j = 0; j < Nborder * Nborder; j++) {
            if (j / Nborder == 0 || j / Nborder == Nborder - 1 || j % Nborder == 0 || j % Nborder == Nborder - 1) {
                map.push_back(5);
                continue;
            }
            int tile;
            cin >> tile;

            // add wormholes
            if (tile >= 6 && tile <= 10) {
                if (wormHoles.find(tile) != wormHoles.end()) {
                    wormHoles.at(tile).push_back(j);
                } else {
                    vector<int> tileIdx;
                    tileIdx.push_back(j);
                    wormHoles.insert(make_pair(tile, tileIdx));
                }
            }

            if (tile == 0) {
                starters.push_back(j);
            }

//            // add blackholes1
//            if (tile == -1) {
//                blackHoles.insert(make_pair(tile, j));
//            }


//            .insert(make_pair(j, tile));

            map.push_back(tile);
        }
        getMax();
//        move(firstPosition);
//        move(firstPosition);


        for (int j = 0; j < Nborder * Nborder; j++) {
            cout << map[j] << " ";
            if (j % Nborder == Nborder - 1) cout << endl;
        }

        cout << "starters size : " << starters.size() << endl;

        for (int j = 0; j < starters.size(); j++) {
            cout << "starters : " << starters[j] << " ";
            cout << endl;

        }

//        for (int j = 0; j < w1
        cout << maxScore << endl;
    }
}

