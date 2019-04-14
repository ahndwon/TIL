//13시16분  - 13시41분
#include <cstdio>
using namespace std;

int N,L, map[101][101] = { 0 };

int main() {

    scanf("%d %d", &N, &L);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            scanf("%d", &map[i][j]);
        }
    }

    int flag, cnt, res = 0;
    for (int i = 0; i < N; i++) {

        //가로
        cnt = 1, flag = 0;
        for (int j = 0; j < N - 1; j++) {
            //다음것과 같을때
            if (map[i][j] == map[i][j + 1]) {
                cnt++;
            }
                //다음이 낮을때
            else if (map[i][j] == map[i][j + 1] + 1) {
                if (cnt < 0) { flag = 1; break; }
                cnt = -L + 1;
            }
                //다음이 높을때
            else if (map[i][j] == map[i][j + 1] - 1) {
                if (cnt < L) { flag = 1; break; }
                cnt = 1;
            }
                //2이상 차이날때
            else { flag = 1; break; }
        }
        if (!flag && cnt >= 0) res++;

        //세로
        cnt = 1, flag = 0;
        for (int j = 0; j < N - 1; j++) {
            //다음것과 같을때
            if (map[j][i] == map[j + 1][i]) {
                cnt++;
            }
                //다음이 낮을때
            else if (map[j][i] == map[j + 1][i] + 1) {
                if (cnt < 0) { flag = 1; break; }
                cnt = -L + 1;
            }
                //다음이 높을때
            else if (map[j][i] == map[j + 1][i] - 1) {
                if (cnt < L) { flag = 1; break; }
                cnt = 1;
            }
                //2이상 차이날때
            else { flag = 1; break; }
        }
        if (!flag && cnt >= 0) res++;

    }

    printf("%d\n", res);

    return 0;
}
