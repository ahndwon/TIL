
#include <iostream>
#include <queue>
#include <math.h>

using namespace std;

// 보드 세로 : N, 가로 : M
// 바깥은 벽
// 빨간공, 파란공
// 공은 동시에 움직임
// 빨간 구슬이 빠지면 성공, 파란 구슬이 빠지면 실패
// '.' : 빈칸,
// '#' : 벽
// '0' : 구멍의 위치
// 'R' : 빨간 공
// 'B' : 파란 공

// BFS
// 큐에 노드 푸시
// 방문 표시
// cnt = 0;

// while(q.size())
//      qs = qsize()
//          while (qs--)
//              q.front
//              q.pop
//              if(빨강이 구멍 && 빨강공과 파란공이 같지 않을떄) return cnt;
//
//              for (방향
//                  위치 복사
//                  while (다음 위치가 벽이 아니고 && 구멍이 아닐때)
//                      방향으로 이동
//                  while (다음 위치가 벽이 아니고 && 구멍이 아닐때)
//                      방향으로 이동
//              if (빨강과 파란이 겹칠때)
//                  if (빨강이 구멍) continue;
//                  // 누가 더 많이 움직엿는지 판단
//                  // 더 많이 움직인 놈이 한칸 반대로 이동
//
//              if (방문햇으면) continue
//              q.push()
//              방문 표시
//

int N, M;
char map[11][11];
int rx, ry, bx, by;
int dx[4] = {-1, 1, 0, 0};
int dy[4] = {0, 0, -1, 1};
int visit[11][11][11][11];
queue<pair<pair<int,int>, pair<int, int>>> q;

int bfs() {
    q.push({{rx, ry}, {bx, by}});
    visit[rx][ry][bx][by] = 1;
    int cnt = 0;

    while (q.size()) {
        int qs = q.size();
        while(qs--) {
            int rrx = q.front().first.first;
            int rry = q.front().first.second;
            int bbx = q.front().second.first;
            int bby = q.front().second.second;
            q.pop();

            // 종료 조건
            if (map[rrx][rry] == 'O' && map[rrx][rry] != map[bbx][bby]) return cnt;

            // 움직임
            for (int i = 0; i < 4; i++) {
                int drx = rrx, dry = rry, dbx = bbx, dby = bby;

                while (map[drx + dx[i]][dry + dy[i]] != '#' && map[drx][dry] != 'O') {
                    drx += dx[i];
                    dry += dy[i];
                }

                while (map[dbx + dx[i]][dby + dy[i]] != '#' && map[dbx][dby] != 'O') {
                    dbx += dx[i];
                    dby += dy[i];
                }

                if (drx == dbx && dry == dby) {
                    if (map[drx][dry] == 'O') continue;

                    if (abs(drx - rrx) + abs(dry - rry) > abs(dbx - bbx) + abs(dby - bby)) {
                        drx -= dx[i];
                        dry -= dy[i];
                    } else {
                        dbx -= dx[i];
                        dby -= dy[i];
                    }
                }

                if (visit[drx][dry][dbx][dby]) continue;
                q.push({{drx, dry}, {dbx, dby}});
                visit[drx][dry][dbx][dby] = 1;
            }
        }
        if (cnt == 10) return -1;
        cnt++;
    }
    return -1;
}

int main() {
    cin >> N >> M;
//    cout << N << " " << M << endl;


    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            char tile;
            cin >> tile;
            map[i][j] = tile;

            if (tile == 'R') {
                rx = i;
                ry = j;
            }

            if (tile == 'B') {
                bx = i;
                by = j;
            }

        }
    }

    // 맵 출력
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << map[i][j];
        }
        cout << endl;
    }
    int answer = bfs();

    cout << answer;


}