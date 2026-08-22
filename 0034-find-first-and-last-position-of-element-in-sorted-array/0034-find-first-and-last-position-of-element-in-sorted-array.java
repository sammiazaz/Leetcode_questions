class Solution {
    public int[] searchRange(int[] nums, int target) {
        
        int n = nums.length;
        int fi = -1, li = -1;

        // Find first occurrence
        int lo = 0, hi = n - 1;

        while (lo <= hi) {
            int m = (lo + hi) / 2;

            if (nums[m] == target) {
                fi = m;
                hi = m - 1;
            } 
            else if (nums[m] < target) {
                lo = m + 1;
            } 
            else {
                hi = m - 1;
            }
        }

        // Find last occurrence
        lo = 0;
        hi = n - 1;

        while (lo <= hi) {
            int m = (lo + hi) / 2;

            if (nums[m] == target) {
                li = m;
                lo = m + 1;
            } 
            else if (nums[m] < target) {
                lo = m + 1;
            } 
            else {
                hi = m - 1;
            }
        }

        return new int[]{fi, li};
    }
}