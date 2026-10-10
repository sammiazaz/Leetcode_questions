class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {

        int n = nums1.length;
        long k = (long) k1 + k2;

        int[] diff = new int[n];
        int maxDiff = 0;
        long total = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            total += diff[i];
        }

        if (total <= k) return 0;

        int left = 0, right = maxDiff;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) needed += d - mid;
            }

            if (needed <= k) right = mid;
            else left = mid + 1;
        }

        int limit = left;
        long used = 0;
        long answer = 0;

        for (int d : diff) {
            if (d > limit) {
                used += d - limit;
                d = limit;
            }
            answer += (long) d * d;
        }

        long remaining = k - used;

        // Reduce 'remaining' values from limit to limit - 1.
        answer -= remaining * (2L * limit - 1);

        return answer;
    }
}