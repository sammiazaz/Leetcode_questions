import java.util.Stack;

class Solution {
    public boolean isValid(String s) {

        Stack<Character> st = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // If opening bracket
            if (ch == '(' || ch == '{' || ch == '[') {
                st.push(ch);
            }
            else {
                if (st.isEmpty()) return false;

                char match = st.pop();

                if ((ch == ')' && match != '(') ||
                    (ch == '}' && match != '{') ||
                    (ch == ']' && match != '[')) {
                    return false;
                }
            }
        }

        return st.isEmpty();
    }
}