#include <cstdio>
#include <vector>
#include <algorithm>
#include <queue>
#include <iostream>

using namespace std;

int N, M;
int map[9][9] = {0};
int temp[9][9] = {0};
int dx[4] = {-1, 1, 0, 0};
int dy[4] = {0, 0, -1, 1};
vector<pair<int, int>> v;
int res = -987654321;
int cnt = 0;

void showTemp() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << temp[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}

void bfs() {

    int visit[9][9] = {0};
    queue<pair<int, int>> q;
    cnt = 0;


    // 맵 복사, 바이러스 저장
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            temp[i][j] = map[i][j];
            if (map[i][j] == 2) q.push({i, j});
        }
    }

    while (!q.empty()) {

//        int qs = q.size();
//        for (int i = 0; i < qs; i++) {

        int x = q.front().first;
        int y = q.front().second;
        q.pop();

        for (int d = 0; d < 4; d++) {
            int xx = x + dx[d];
            int yy = y + dy[d];

            if (xx < 0 || xx >= N || yy < 0 || yy >= M || visit[xx][yy] || temp[xx][yy] != 0) continue;
            visit[x][y] = 1;
            temp[xx][yy] = 2;
            q.push({xx, yy});
        }
//        }
    }


    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if (temp[i][j] == 0) cnt++;
        }
    }

}

int main() {

    scanf("%d %d", &N, &M);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            scanf("%d", &map[i][j]);

            if (map[i][j] == 0) v.push_back({i, j});
        }
    }

    int vs = v.size();
    for (int i = 0; i < vs - 2; i++) {
        for (int j = i + 1; j < vs - 1; j++) {
            for (int k = j + 1; k < vs; k++) {
                map[v[i].first][v[i].second] = 1;
                map[v[j].first][v[j].second] = 1;
                map[v[k].first][v[k].second] = 1;

                bfs();
                cout << cnt << endl;
                showTemp();
                res = max(res, cnt);

                map[v[i].first][v[i].second] = 0;
                map[v[j].first][v[j].second] = 0;
                map[v[k].first][v[k].second] = 0;
            }
        }
    }

    printf("%d\n", res);

    return 0;
}
