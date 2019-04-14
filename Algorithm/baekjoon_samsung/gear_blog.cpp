//10시 38분 - 11시26분
#include <cstdio>
using namespace std;

int tobni[5][9] = { 0 };
int temp[5][9] = { 0 };//변하면안돼
int K, num, dir;

void watch(int n) {

    for (int i = 1; i <= 7; i++) {
        tobni[n][i+1] = temp[n][i];
    }
    tobni[n][1] = temp[n][8];
}

void banwatch(int n) {

    tobni[n][8] = temp[n][1];
    for (int i = 1; i <= 7; i++) {
        tobni[n][i] = temp[n][i + 1];
    }
}

int main() {

    for (int i = 1; i <= 4; i++) {
        for (int j = 1; j <= 8; j++) {
            scanf("%1d", &tobni[i][j]);
            temp[i][j] = tobni[i][j];
        }
    }

    scanf("%d", &K);
    for (int k = 0; k < K; k++) {
        scanf("%d %d", &num, &dir);

        if (num == 1) {
            //1,watch
            if (dir == 1) {	watch(1);
                //2,banwtach
                if (temp[1][3] != temp[2][7]) {	banwatch(2);
                    //3,watch
                    if (temp[2][3] != temp[3][7]) {	watch(3);
                        //4,banwath
                        if (temp[3][3] != temp[4][7]) banwatch(4);
                    }
                }
            }
                //1,banwatch
            else if (dir == -1) { banwatch(1);
                //2,wtach
                if (temp[1][3] != temp[2][7]) {	watch(2);
                    //3,banwatch
                    if (temp[2][3] != temp[3][7]) {	banwatch(3);
                        //4,wath
                        if (temp[3][3] != temp[4][7]) watch(4);
                    }
                }
            }

        }

        else if (num == 2) {
            //2,watch
            if (dir == 1) {	watch(2);
                //1,banwatch
                if (temp[2][7] != temp[1][3]) banwatch(1);
                //3,banwtach
                if (temp[2][3] != temp[3][7]) {	banwatch(3);
                    //4,watch
                    if (temp[3][3] != temp[4][7]) watch(4);
                }
            }
                //2,banwatch
            else if (dir == -1) { banwatch(2);
                //1,watch
                if (temp[2][7] != temp[1][3]) watch(1);
                //3,wtach
                if (temp[2][3] != temp[3][7]) {	watch(3);
                    //4,banwatch
                    if (temp[3][3] != temp[4][7]) banwatch(4);
                }
            }
        }


        else if (num == 3) {
            //3,watch
            if (dir == 1) {	watch(3);
                //4,banwatch
                if (temp[3][3] != temp[4][7]) banwatch(4);
                //2,banwtach
                if (temp[3][7] != temp[2][3]) {	banwatch(2);
                    //1,watch
                    if (temp[2][7] != temp[1][3]) watch(1);
                }
            }
                //3,banwatch
            else if (dir == -1) { banwatch(3);
                //4,watch
                if (temp[3][3] != temp[4][7]) watch(4);
                //2,wtach
                if (temp[3][7] != temp[2][3]) {	watch(2);
                    //1,banwatch
                    if (temp[2][7] != temp[1][3]) banwatch(1);
                }
            }
        }


        else if (num == 4) {
            //4,watch
            if (dir == 1) {	watch(4);
                //3,banwtach
                if (temp[4][7] != temp[3][3]) {	banwatch(3);
                    //2,watch
                    if (temp[3][7] != temp[2][3]) {	watch(2);
                        //1,banwath
                        if (temp[2][7] != temp[1][3]) banwatch(1);
                    }
                }
            }
                //4,banwatch
            else if (dir == -1) { banwatch(4);
                //3,wtach
                if (temp[4][7] != temp[3][3]) {	watch(3);
                    //2,banwatch
                    if (temp[3][7] != temp[2][3]) { banwatch(2);
                        //1,wath
                        if (temp[2][7] != temp[1][3]) watch(1);
                    }
                }
            }
        }


        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 8; j++) {
                temp[i][j] = tobni[i][j];
            }
        }

    }

    printf("%d\n", (1 * tobni[1][1]) + (2 * tobni[2][1]) + (4 * tobni[3][1]) + (8 * tobni[4][1]));

    return 0;
}