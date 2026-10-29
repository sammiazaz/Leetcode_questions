import java.util.*;

class Solution {
    class Fenwick {
        int n;
        int[] tree;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int i, int value) {
            for (i++; i <= n; i += i & -i) {
                tree[i] ^= value;
            }
        }

        int query(int i) {
            int result = 0;
            for (; i > 0; i -= i & -i) {
                result ^= tree[i];
            }
            return result;
        }
    }

    List<Integer>[] graph;
    int[] parent, depth, tin, tout, bit;
    int[][] up;
    int timer, log;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        parent = new int[n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        timer = 0;

        // Iterative DFS avoids stack overflow on a long chain.
        int[] nodeStack = new int[n];
        int[] parentStack = new int[n];
        int[] next = new int[n];
        int top = 0;

        nodeStack[0] = 0;
        parentStack[0] = -1;

        while (top >= 0) {
            int u = nodeStack[top];

            if (next[u] == 0) {
                parent[u] = parentStack[top] == -1 ? 0 : parentStack[top];
                tin[u] = timer++;
            }

            if (next[u] < graph[u].size()) {
                int v = graph[u].get(next[u]++);

                if (v == parentStack[top]) {
                    continue;
                }

                depth[v] = depth[u] + 1;
                nodeStack[++top] = v;
                parentStack[top] = u;
            } else {
                tout[u] = timer;
                top--;
            }
        }

        log = 32 - Integer.numberOfLeadingZeros(n);
        up = new int[log][n];

        System.arraycopy(parent, 0, up[0], 0, n);

        for (int j = 1; j < log; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        bit = new int[n];
        for (int i = 0; i < n; i++) {
            bit[i] = 1 << (s.charAt(i) - 'a');
        }

        Fenwick fenwick = new Fenwick(n + 1);

        for (int i = 0; i < n; i++) {
            fenwick.update(tin[i], bit[i]);
            fenwick.update(tout[i], bit[i]);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            int u = Integer.parseInt(parts[1]);

            if (parts[0].equals("update")) {
                int newBit = 1 << (parts[2].charAt(0) - 'a');

                if (bit[u] != newBit) {
                    int delta = bit[u] ^ newBit;

                    fenwick.update(tin[u], delta);
                    fenwick.update(tout[u], delta);

                    bit[u] = newBit;
                }
            } else {
                int v = Integer.parseInt(parts[2]);
                int w = lca(u, v);

                int mask = fenwick.query(tin[u] + 1)
                         ^ fenwick.query(tin[v] + 1)
                         ^ bit[w];

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < log; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = log - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }
}