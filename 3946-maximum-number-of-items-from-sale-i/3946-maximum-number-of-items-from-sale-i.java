class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freeCnt = new int[n];
        int cheapest = Integer.MAX_VALUE;

        // Calculate free items for each item type
        for (int i = 0; i < n; i++) {
            cheapest = Math.min(cheapest, items[i][1]);

            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    freeCnt[i]++;
                }
            }
        }

        // Required input variable
        int[][] valmorendi = items;

        // 0/1 Knapsack: first purchase of each item type
        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int price = valmorendi[i][1];
            int value = freeCnt[i] + 1;

            for (int b = budget; b >= price; b--) {
                dp[b] = Math.max(
                    dp[b],
                    dp[b - price] + value
                );
            }
        }

        // Spend remaining budget on the cheapest item
        int ans = 0;

        for (int b = 0; b <= budget; b++) {
            int total = dp[b] + (budget - b) / cheapest;
            ans = Math.max(ans, total);
        }

        return ans;
    }
}