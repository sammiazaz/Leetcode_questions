class Solution {
    public int findPeakElement(int[] nums) {

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[mid + 1]) {
                // Peak is on left side or at mid
                right = mid;
            } else {
                // Peak is on right side
                left = mid + 1;
            }
        }

        return left;
    }
}