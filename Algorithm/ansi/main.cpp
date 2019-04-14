#include <iostream>
#include <vector>

using namespace std;

int ck[101];
vector<int> g[101];

void dfs(int x) {
    ck[x] = true;
    for (int i = 0; i < g[x].size(); i++) {
        int nx = g[x][i];
        if (ck[nx] == 0) {
            dfs(nx);
        }
    }
}


int main() {
    int comCount;
    int pairCount;

    cin >> comCount;
    cin >> pairCount;

    int src;
    int dest;

    for (int i = 1; i <= pairCount; i++) {
        cin >> src >> dest;
        g[src].push_back(dest);
        g[dest].push_back(src);
    }

    dfs(1);

    int answer = 0;
    for (int i : ck) {
        if (i != 0) {
            answer++;
        }
    }

    cout << answer - 1;

    return 0;
}