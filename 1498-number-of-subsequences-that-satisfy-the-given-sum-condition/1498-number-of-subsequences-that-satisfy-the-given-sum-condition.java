class Solution {
    public int numSubseq(int[] nums, int target) {

        int MOD = 1_000_000_007;

        Arrays.sort(nums);

        int n = nums.length;

        int[] pow = new int[n];

        pow[0] = 1;

        for (int i = 1; i < n; i++) {
            pow[i] = (pow[i - 1] * 2) % MOD;
        }

        int left = 0;
        int right = n - 1;

        long answer = 0;

        while (left <= right) {

            if (nums[left] + nums[right] <= target) {

                answer = (answer + pow[right - left]) % MOD;

                left++;

            } else {

                right--;
            }
        }

        return (int) answer;
    }
}