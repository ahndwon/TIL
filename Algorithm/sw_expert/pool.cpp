#include <iostream>

using namespace std;


int pay[4];
int useMonth[12];
int result;

void solve(int idx, int sum) {

    if (result < sum) return;

    if (idx >= 12) {
        result = result < sum ? result : sum;
        return;
    }

    solve(idx + 1, sum + useMonth[idx] * pay[0]);
    solve(idx + 1, sum + pay[1]);
    solve(idx + 3, sum + pay[2]);
    solve(idx + 12, sum + pay[3]);
}


int main() {
    int testCount;
    cin >> testCount;

    for (int i = 1; i <= testCount; i++) {
        result = INT32_MAX;

        for (int j = 0; j < 4; j++) {
            cin >> pay[j];
        }

        for (int j = 0; j < 12; j++) {
            cin >> useMonth[j];

        }

        solve(0, 0);
        cout << "#" << i << " " << result << endl;
    }
}