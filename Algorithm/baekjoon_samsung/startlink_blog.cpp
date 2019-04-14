// https://www.acmicpc.net/problem/14889
#include <cstdio>
#include <algorithm>
using namespace std;

int n;
int map[21][21] = { 0 };
int visit[21] = { 0 };
int mini = 987654321;


void dfs(int idx, int cnt) {
    visit[idx] = 1;
    if (cnt == (n / 2)-1) {
        int s = 0, l = 0;
        for (int p = 0; p < n - 1; p++) {
            for (int q = p + 1; q < n; q++) {
                if (visit[p] == 1 && visit[q] == 1) s = s + map[p][q] + map[q][p];
                if (visit[p] == 0 && visit[q] == 0) l = l + map[p][q] + map[q][p];
            }
        }
        int res = s - l;
        if (res < 0) res = -res;
        mini = min(mini, res);
        return;
    }


    for (int i = idx + 1; i < n; i++) {
        dfs(i, cnt + 1);
        visit[i] = 0;
    }
}

int main() {

    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            scanf("%d", &map[i][j]);
        }
    }

    dfs(0, 0);

    printf("%d\n", mini);

    return 0;
}