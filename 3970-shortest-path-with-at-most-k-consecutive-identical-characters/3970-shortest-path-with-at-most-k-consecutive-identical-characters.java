import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        // dist[node][count] = minimum cost when the current
        // consecutive character count is count
        int[][] dist = new int[n][k + 1];

        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        // {cost, node, consecutiveCount}
        dist[0][1] = 0;
        pq.offer(new int[]{0, 0, 1});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();

            int cost = curr[0];
            int node = curr[1];
            int count = curr[2];

            if (cost != dist[node][count]) {
                continue;
            }

            if (node == n - 1) {
                return cost;
            }

            for (int[] next : graph[node]) {
                int nextNode = next[0];
                int weight = next[1];

                int nextCount;

                if (labels.charAt(node) == labels.charAt(nextNode)) {
                    nextCount = count + 1;
                } else {
                    nextCount = 1;
                }

                // Too many consecutive identical characters
                if (nextCount > k) {
                    continue;
                }

                int nextCost = cost + weight;

                if (nextCost < dist[nextNode][nextCount]) {
                    dist[nextNode][nextCount] = nextCost;

                    pq.offer(new int[]{
                        nextCost,
                        nextNode,
                        nextCount
                    });
                }
            }
        }

        return -1;
    }
}