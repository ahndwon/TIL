#include <iostream>
#include <vector>

using namespace std;

int N;
int pairN;
int check[101];
vector<int> pairs[101];
int answer;

void dfs(int start) {
    check[start] = true;

    for (int i = 0; i < pairs[start].size(); i++) {
        if (check[pairs[start][i]] == 0) {
            answer++;

            check[pairs[start][i]] = true;
            dfs(pairs[start][i]);
        }
    }
}

int main() {
    cin >> N >> pairN;


    for (int i = 0; i < pairN; i++) {
        int src;
        int dest;
        cin >> src >> dest;

        pairs[src].push_back(dest);
        pairs[dest].push_back(src);
    }

    dfs(1);

    cout << answer;
}


