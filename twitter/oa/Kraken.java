package twitter;

import java.util.Arrays;

public class Kraken {
	int krakenCount(int m, int n) {
		if (m == 0 || n == 0) {
			return 0;
		}

		int[] dp = new int[n];
		Arrays.fill(dp, 1);

		for (int i = m - 2; i >= 0; i--) {
			int prev = 1;
			for (int j = n - 2; j >= 0; j--) {
				int tmp = dp[j];
				dp[j] += dp[j + 1];
				dp[j] += prev;
				prev = tmp;
			}
		}

		return dp[0];
	}
	
	public static void main(String[] args) {
		Kraken k = new Kraken();
		System.out.println(k.krakenCount(2, 2));
		
		System.out.println(k.krakenCount(2, 3));
	}
}
