// https://www.acmicpc.net/problem/10159

#include <iostream>
#include <vector>


using namespace std;

vector<int> smaller[101];
vector<int> bigger[101];
int visit[101][101];
int smallerVisited[101];
int biggerVisited[101];
int N;
int M;
int cnt = 0;


// 출력 : 각 물건에 대해서 그 물건과의 비교 결과를 알 수 없는 물건의 개수를 출력 -> 연관된게 하나도 없는 노드 개수 출력
void biggerDfs(int index) {
    if (bigger[index].empty()) return;
//    biggerVisited[index] = 1;
//    visit[index] = 1;
    cnt++;
    for (int i = 0; i < bigger[index].size(); i++) {
        if (visit[index][bigger[index][i]] != 1) {
            visit[index][bigger[index][i]] = 1;
            biggerDfs(bigger[index][i]);
        }
    }
}

void smallerDfs(int index) {
//    if (smaller[index].empty()) return;
//    smallerVisited[index] = 1;
    cnt++;
    for (int i = 0; i < smaller[index].size(); i++) {
//        if (smallerVisited[smaller[index][i]] != 1) {
//            smallerDfs(smaller[index][i]);
//        }
        if (visit[index][smaller[index][i]] != 1) {
            visit[index][smaller[index][i]] = 1;
            smallerDfs(bigger[index][i]);
        }
    }
}

int main() {
    cin >> N >> M;

    for (int i = 0; i < M; i++) {
        int heavy, light;
        cin >> heavy >> light;
        bigger[heavy].push_back(light);
        smaller[light].push_back(heavy);
    }

    for (int i = 1; i <= N; i++) {
        cnt = 0;
        biggerDfs(i);
        smallerDfs(i);
        int answer;
        for (int j = 0; j < 101; j++) {
            if (visit[i][j] == 1) answer ++;
        }
//        cout << N - 1 - cnt << endl;
        cout << N - 1 - answer << endl;
    }
}