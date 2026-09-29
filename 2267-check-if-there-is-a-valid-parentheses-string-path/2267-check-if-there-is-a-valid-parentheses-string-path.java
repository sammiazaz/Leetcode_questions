class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        
        // Path length (m + n - 1) must be even, start with '(', and end with ')'
        if ((m + n) % 2 == 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        
        // Max valid balance <= 99 fits in two 64-bit longs (128 bits total)
        long[] lo = new long[n]; // Bits 0..63
        long[] hi = new long[n]; // Bits 64..127
        lo[0] = 1L;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j > 0) {
                    lo[j] |= lo[j - 1];
                    hi[j] |= hi[j - 1];
                }
                if (grid[i][j] == '(') {
                    hi[j] = (hi[j] << 1) | (lo[j] >>> 63);
                    lo[j] = (lo[j] << 1);
                } else {
                    lo[j] = (lo[j] >>> 1) | (hi[j] << 63);
                    hi[j] = (hi[j] >>> 1);
                }
            }
        }
        
        return (lo[n - 1] & 1L) != 0;
    }
}