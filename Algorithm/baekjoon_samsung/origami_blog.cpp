// https://blog.naver.com/fool28/221508292031
#include <iostream>
#define rangecheck(a,b) a<0 || a>=10 || b<0 || b>=10

using namespace std;
int map[11][11], one_cnt=0, ans=-1;
int paper[6];
bool visited[11][11];
bool check(int r, int c, int k) {
    for (int i = r; i < r + k; i++) {
        for (int j = c; j < c + k; j++) {
            if (rangecheck(i, j)) return false;
            if (map[i][j] == 0 || visited[i][j]) return false;
        }
    }
    return true;
}
void visit_t(int r, int c, int k) {
    for (int i = r; i <= r + k; i++) {
        for (int j = c; j <= c + k; j++) {
            visited[i][j] = true;
        }
    }
}
void visit_f(int r, int c, int k) {
    for (int i = r; i <= r + k; i++) {
        for (int j = c; j <= c + k; j++) {
            visited[i][j] = false;
        }
    }
}
void print() {
    printf("\n");
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            if (visited[i][j]) printf("1 ");
            else printf("0 ");
        }
        printf("\n");
    }
}
void dfs(int r, int c, int depth, int cnt) {
    if (cnt > one_cnt) return;
    if (ans!=-1 && depth >= ans) return;
    if (cnt == one_cnt) {
        if (ans == -1 || depth < ans) {
            ans = depth;
        }
        return;
    }
    if (r == 10) return;
    //printf("r = %d c = %d cnt = %d depth = %d  \n", r, c, cnt, depth);
    if (map[r][c] == 1 && !visited[r][c]) {
        for (int k = 5; k >= 1 ; k--) {
            if (paper[k] < 5) {
                if (check(r, c, k)) {
                    paper[k]++;
                    visit_t(r, c, k - 1);
                    //printf("r = %d c = %d cnt = %d depth = %d  k =%d \n", r, c, cnt, depth, k);
                    //print();
                    if(c+1<10) dfs(r, c+1, depth+1, cnt+(k*k));
                    else dfs(r + 1, 0, depth+1, cnt+(k*k));
                    visit_f(r, c, k - 1);
                    paper[k]--;
                }
            }
        }
    }
    // 이 부분 중요!!!! 반드시 else로 처리해야댐
    else {
        if (c + 1 < 10) dfs(r, c + 1, depth, cnt);
        else dfs(r + 1, 0, depth, cnt);
    }

}
int main() {
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            scanf("%d", &map[i][j]);
            if (map[i][j] == 1) one_cnt++;
        }
    }
    //printf("one_cnt = %d\n", one_cnt);
    dfs(0, 0, 0, 0);
    if (one_cnt == 0) printf("0\n");
    else printf("%d\n", ans);
    return 0;
}