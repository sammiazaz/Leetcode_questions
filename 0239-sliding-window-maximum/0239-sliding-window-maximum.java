class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;

        int[] ans = new int[n - k + 1];

        Deque<Integer> deque = new ArrayDeque<>();

        int j = 0;

        for (int i = 0; i < n; i++) {

            // Remove elements outside the window
            while (!deque.isEmpty() &&
                   deque.peekFirst() <= i - k) {

                deque.pollFirst();
            }

            // Remove smaller elements
            while (!deque.isEmpty() &&
                   nums[deque.peekLast()] <= nums[i]) {

                deque.pollLast();
            }

            // Add current index
            deque.offerLast(i);

            // Window is ready
            if (i >= k - 1) {

                ans[j] = nums[deque.peekFirst()];
                j++;
            }
        }

        return ans;
    }
}