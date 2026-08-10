class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<String>();
        generate(n, 0, 0, ans, "");
        return ans;
    }
    public void generate(int n, int openingBracketCount, int closingBracketCount, List<String> ans, String ssf) {
        if(openingBracketCount > n || closingBracketCount > n || closingBracketCount > openingBracketCount) {
            return;
        }
        if(openingBracketCount == n && closingBracketCount == n) {
            ans.add(ssf);
            return;
        }
 
        generate(n, openingBracketCount + 1, closingBracketCount, ans, ssf + "(");
        generate(n, openingBracketCount, closingBracketCount + 1, ans, ssf + ")");
    }
}