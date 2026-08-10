class Solution {
    public boolean exist(char[][] board, String word) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == word.charAt(0)) {
                    boolean ans = dfs(board, i, j, word, 0);
                    if(ans) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean dfs(char[][] board, int i, int j, String word, int idx) {
        if(idx > word.length() - 1) {
            return true;
        }
        if(i < 0 || j < 0 || i > board.length - 1 || j > board[0].length - 1) {
            return false;
        }
        if(board[i][j] != word.charAt(idx)) {
            return false;
        }
        char temp = board[i][j];
        board[i][j] = '#';
        boolean up = dfs(board, i - 1, j, word, idx + 1);
        boolean left = dfs(board, i, j - 1, word, idx + 1);
        boolean down = dfs(board, i + 1, j, word, idx + 1);
        boolean right = dfs(board, i, j + 1, word, idx + 1);
        board[i][j] = temp;
        return up || left || down || right;
    }
}