#include <iostream>
#include <queue>
#include <vector>

using namespace std;

#define MAX_INT 987654321
// # 조건
// N x N
// 빈칸, 치킨집, 집
// 치킨 거리 : 집과 가장 가까운 치킨집 사이의 거리 (BFS)
// 각각의 집은 치킨 거리를 갖고 있음
// 도시의 치킨 거리는 모든 집의 치킨 거리의 합.
// 거리 r1-r1 + c1-c2

// 풀이
// 첫번쨰
// 1. 치킨집, 집 벡터로 저장
// 2. 치킨집을 최대 개수만큼 제한 (dfs)
// 3. 집마다 가장 가까운 치킨집 (distance)
// 4. 최소값을 출력

// 제한 N(2 ≤ N ≤ 50)과 M(1 ≤ M ≤ 13)
// 집의 개수는 2N개를 넘지 않으며, 적어도 1개는 존재한다.
// 치킨집의 개수는 M보다 크거나 같고, 13보다 작거나 같다.



class Point {
public:
    int row;
    int col;
};

int N;
int M;
int map[50][50];
int answer = 999999;
vector<Point> chickens;
vector<Point> houses;
int visit[14];

int distance(Point &a, Point &b) {
    return abs(a.row - b.row) + abs(a.col - b.col);
}

void dfs(int idx, int cnt) {
    if (idx > chickens.size()) return;

    if (cnt == M) {

        int cmp = 0;

        for (int i = 0; i < houses.size(); i++) {
            int dist = MAX_INT;

            for (int j = 0; j < chickens.size(); j++) {
                if (visit[j]) {
                    dist = min(dist, distance(houses[i], chickens[j]));
                }
            }
            cmp += dist;
        }
        answer = min(answer, cmp);
    }

    // 사용하는 경우
    visit[idx] = true;
    dfs(idx + 1, cnt + 1);
    // 사용하지 않는 경우
    visit[idx] = false;
    dfs(idx + 1, cnt);
}

int main() {
    cin >> N >> M;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> map[i][j];

            if (map[i][j] == 1) houses.push_back({i, j});
            else if(map[i][j] == 2) chickens.push_back({i, j});
        }
    }

    dfs(0, 0);

    cout << answer << endl;
}