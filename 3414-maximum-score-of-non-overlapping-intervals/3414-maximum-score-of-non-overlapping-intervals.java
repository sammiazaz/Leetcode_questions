class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        ArrayList<Interval> arr = new ArrayList<>();
        for (int i = 0; i < intervals.size(); i++)
        {
            Interval interval = new Interval(intervals.get(i).get(0), intervals.get(i).get(1), intervals.get(i).get(2), i);
            arr.add(interval);
        }

        // Memoize DP
        HashMap<String, ArrayList<Interval>> dp = new HashMap<>();

        // Sort intervals by start time
        Collections.sort(arr, Comparator.comparingInt(a -> a.left));
        ArrayList<Interval> ret = rec(0, 4, arr, dp);
        int[] ans = new int[ret.size()];
        for (int i = 0; i < ret.size(); i++)
            ans[i] = ret.get(i).index;

        return ans;
    }

    private ArrayList<Interval> rec(int i, int k, ArrayList<Interval> intervals, HashMap<String, ArrayList<Interval>> dp)
    {
        if (i == intervals.size() || k == 0)
            return new ArrayList<>();

        String key = i + " " + k;
        if (dp.containsKey(key))
            return new ArrayList<>(dp.get(key));
        
        // Leave it
        ArrayList<Interval> skip = rec(i + 1, k, intervals, dp);

        // Take it. Get the next non-overlapping interval
        int nextIndex = binary(i + 1, i, intervals);
        ArrayList<Interval> take = new ArrayList<>(rec(nextIndex, k - 1, intervals, dp));
        take.add(intervals.get(i));

        Collections.sort(skip, Comparator.comparingInt(a -> a.index));
        Collections.sort(take, Comparator.comparingInt(a -> a.index));

        long skipWt = 0;
        for (Interval interval : skip)
            skipWt += interval.weight;

        long takeWt = 0;
        for (Interval interval : take)
            takeWt += interval.weight;

        // Take the set with maximum cumulative weight
        if (skipWt < takeWt)
        {
            dp.put(key, take);
            return new ArrayList<>(take);
        }

        if (skipWt > takeWt)
        {
            dp.put(key, skip);
            return new ArrayList<>(skip);
        }

        // If cumulative weights are the same then take the lexicographically smaller set of indices
        int small = smaller(skip, take);
        if (small == 0)
        {
            dp.put(key, skip);
            return new ArrayList<>(skip);
        }

        dp.put(key, take);
        return new ArrayList<>(take);
    }

    // Compare the arrays lexicographically
    private int smaller(ArrayList<Interval> arr1, ArrayList<Interval> arr2)
    {
        for (int j = 0; j < Math.min(arr1.size(), arr2.size()); j++)
        {
            if (arr1.get(j).index < arr2.get(j).index)
                return 0;
            else if (arr1.get(j).index > arr2.get(j).index)
                return 1;
        }

        if (arr1.size() < arr2.size())
            return 0;

        return 1;
    }

    // Get the next non-overlapping interval
    private int binary(int l, int prev, ArrayList<Interval> intervals)
    {
        int r = intervals.size() - 1, ans = intervals.size();
        int threshold = intervals.get(prev).right;

        while (l <= r)
        {
            int mid = l + (r - l) / 2;

            if (intervals.get(mid).left > threshold)
            {
                ans = Math.min(ans, mid);
                r = mid - 1;
            }
            else
                l = mid + 1;
        }

        return ans;
    }

    class Interval
    {
        int left;
        int right;
        long weight;
        int index;

        Interval(int left, int right, int weight, int index)
        {
            this.left = left;
            this.right = right;
            this.weight = weight;
            this.index = index;
        }
    }
}