import java.util.*;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;

        Deque<Integer> deque = new ArrayDeque<>();

        long cost = 0;
        long ans = 0;

        int j = n - 1;

        for (int i = n - 1; i >= 0; i--) {

            // Merge smaller values into the current maximum
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {

                int l = deque.pollLast();

                int r = deque.isEmpty() ? j + 1 : deque.peekLast();

                cost += (long) (r - l) * (nums[i] - nums[l]);
            }

            deque.offerLast(i);

            // Shrink window while operations exceed k
            while (cost > k) {

                cost -= nums[deque.peekFirst()] - nums[j];

                if (deque.peekFirst() == j) {
                    deque.pollFirst();
                }

                j--;
            }

            // Count valid subarrays ending at j
            ans += j - i + 1;
        }

        return ans;
    }
}