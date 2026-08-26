class Solution {
    public int rob(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        return maxRobbery(nums, 0, dp);
    }
    public int maxRobbery(int[] nums, int idx, int[] dp) {
        if(idx > nums.length - 1) return 0;
        if(dp[idx] != -1) return dp[idx];
        int rob = nums[idx] + maxRobbery(nums, idx + 2, dp);
        int skip = 0 + maxRobbery(nums, idx + 1, dp);
        dp[idx] = Math.max(rob, skip);
        return Math.max(rob, skip);
    }
}