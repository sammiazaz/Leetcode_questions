
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int maxFactor = 0;
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            minPrice = Math.min(minPrice, item[1]);
        }

        // Count frequency of factors
        int[] freq = new int[maxFactor + 1];

        for (int[] item : items) {
            freq[item[0]]++;
        }

        // Count multiples
        for (int i = 1; i <= maxFactor; i++) {
            for (int j = 2 * i; j <= maxFactor; j += i) {
                freq[i] += freq[j];
            }
        }

        // Sort by price
        Arrays.sort(items, (a, b) ->
            Integer.compare(a[1], b[1])
        );

        long ans = 0;
        long remaining = budget;

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            // Double benefit is not worthwhile
            // if price >= 2 * minimum price
            if (price >= 2L * minPrice) {
                break;
            }

            int gain = freq[factor] - 1;

            if (gain <= 0) {
                continue;
            }

            // Each purchase costs price and gives 2 items
            long count = Math.min(
                (long) gain,
                remaining / price
            );

            ans += 2L * count;
            remaining -= count * price;
        }

        // Spend remaining budget on cheapest item
        ans += remaining / minPrice;

        return (int) ans;
    }
}
