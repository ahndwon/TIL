#include <iostream>
#include <algorithm>

#define rangecheck(a, b) a<0 || a>=10 || b<0 || b>=10

using namespace std;

// ## 5가지의 색종이
// 1, 2, 3, 4, 5 각 5개씩
// 종이 크기 : 10 x 10

// idx : size, value : count
//int squares[5];

int mapOG[10][10];
int visited[10][10];
int answer = -1;
int oneCount = 0;
int squares[6] = {5, 5, 5, 5, 5, 5};

void showMap(int m[10][10]) {
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            cout << visited[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}

void visit(int r, int c, int k, bool isVisit) {
    for (int i = r; i < r + k; i++) {
        for (int j = c; j < c + k; j++) {
            visited[i][j] = isVisit;
        }
    }
}

void dfs(int i, int j, int depth, int papered) {
    if (oneCount < papered) return;
    if (answer!= -1 && depth >= answer) return;


    cout << "oneCount : " << oneCount << ", papered : " << papered << endl;

    if (oneCount == papered && oneCount != 0) {
        if (answer == -1 || depth < answer) {
            answer = depth;
        }
//        cout << "oneCount : " << oneCount << ", papered : " << papered << endl;
//        cout << "depth : " << depth << ", answer : " << answer << endl;
//        answer = min(answer, depth);
        return;
    }

    if (i > 9) {
        return;
    }

    cout << i << ", " << j << endl;

    if (mapOG[i][j] == 1 && !visited[i][j]) {
        cout << "map[i][j] == 1" << endl;

        for (int k = 5; k >= 1; k--) {
            if (squares[k] < 1) continue;

            int isSquare = 0;
            int size = k;

            // n**2
            // 껴지나 검사
            for (int m = i; m < i + k; m++) {
                for (int n = j; n < j + k; n++) {
                    if (mapOG[m][n] == 1) isSquare++;
                    if (rangecheck(i, j)) break;
                }
            }

            if (size * size != isSquare) continue;

            // copy squares
//            int sq[6];
//            for (int m = 0; m < 6; m++) sq[m] = squares[m];

            if (size * size == isSquare) {
//                cout << "isSquare" << endl;

//                sq[k] = sq[k] - 1;

                squares[k] -= 1;
                visit(i, j, k, true);
//                if (j + size > 9) dfs(i + 1, 0, depth + 1, papered + isSquare, sq);
//                else dfs(i, j + size, depth + 1, papered + isSquare, sq);

                showMap(mapOG);

                if (j + size > 9) dfs(i + 1, 0, depth + 1, papered + isSquare);
                else dfs(i, j + size, depth + 1, papered + isSquare);
                visit(i, j, k, false);

                squares[k] += 1;
            }
        }
    } else {
        if (j + 1 > 9) dfs(i + 1, 0, depth, papered);
        else dfs(i, j + 1, depth, papered);
    }


}

int main() {
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            cin >> mapOG[i][j];

            if (mapOG[i][j] == 1) oneCount++;
        }
    }

    dfs(0, 0, 0, 0);

    if (oneCount == 0) cout << 0 << endl;
//    if (answer == -1)
//        cout << -1 << endl;
    else
        cout << answer << endl;

      return 0;
}
