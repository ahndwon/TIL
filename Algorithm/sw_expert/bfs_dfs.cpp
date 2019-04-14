#include <iostream>
#include <vector>
#include <queue>

using namespace std;

vector<int> adj[10000];
int check[1000];
int checkBFS[1000];

int N;
int M;
int V;

int dfs(int node) {
    check[node] = true;
    cout << node << " ";

    for (int i = 0; i < adj[node].size(); i++) {
        if (check[adj[node][i]] == 0) {
            dfs(adj[node][i]);
        }
    }
}

int bfs(int node) {
    queue<int> q;
    q.push(node);
    checkBFS[node] = true;

    while(!q.empty()) {
        int x = q.front();
        q.pop();
        cout << x << " ";

        for (int i = 0; i < adj[x].size(); i++) {
            int y = adj[x][i];

            if (!checkBFS[y]) {
                q.push(y);
                checkBFS[y] = true;
            }
        }
    }
}

int main() {
    cin >> N >> M >> V;

    for (int i = 0; i < M; i++) {
        int src;
        int dest;

        cin >> src >> dest;
        adj[src].push_back(dest);
        adj[dest].push_back(src);
    }

    dfs(V);
    cout << endl;
    bfs(V);
}
