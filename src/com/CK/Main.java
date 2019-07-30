package com.CK;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        String s = "227";
//        String s = "012";
//        String s = "10";
//        String s = "110";
//        String s = "1212";
//        String s = "4757562545844617494555774581341211511296816786586787755257741178599337186486723247528324612117156948";
        String s = "301";
        Solution2 solution = new Solution2();
        System.out.println(solution.numDecodings(s));
    }
}

//TLE
class Solution {
    public int numDecodings(String s) {
        List<List<String>> res = new ArrayList<>();

        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                if (i - j == 0 && s.charAt(j) != '0') {
                    dp[j][i] = true;
                } else if ((i - j == 1) && Integer.parseInt(s.substring(j, i + 1)) >= 1 && Integer.parseInt(s.substring(j, i + 1)) <= 26 && s.charAt(j) != '0') {
                    dp[j][i] = true;
                }
            }
        }
        helper(res, new ArrayList<>(), dp, s, 0);
        return res.size();
    }

    private void helper(List<List<String>> res, List<String> path, boolean[][] dp, String s, int pos) {
        if (pos == s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = pos; i < s.length(); i++) {
            if (dp[pos][i]) {
                path.add(s.substring(pos, i + 1));
                helper(res, path, dp, s, i + 1);
                path.remove(path.size() - 1);
            }
        }
    }
}

//Pure DP
class Solution2 {
    public int numDecodings(String s) {
        if (s.length() == 0) return 0;
        int[] dp = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) != '0') dp[i] = 1;
            else if (i == 0 && s.charAt(i) == '0') dp[i] = 0;
            else if (i == 1) {
                String subS = s.substring(0, i + 1);
                if (Integer.parseInt(subS) <= 9) dp[i] = 0;
                else if (s.charAt(i) == '0' && s.charAt(i - 1) != '1' && s.charAt(i - 1) != '2') dp[i] = 0;
                else if ((Integer.parseInt(subS) > 10 && Integer.parseInt(subS) < 20) || (Integer.parseInt(subS) > 20 && Integer.parseInt(subS) < 27))
                    dp[i] = 2;
                else dp[i] = 1;
            } else {
                String subS = s.substring(i - 1, i + 1);
                if (s.charAt(i) == '0') {
                    if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2') dp[i] = dp[i - 2];
                    else dp[i] = 0;
                } else if ((Integer.parseInt(subS) > 10 && Integer.parseInt(subS) < 20) || (Integer.parseInt(subS) > 20 && Integer.parseInt(subS) < 27))
                    dp[i] = dp[i - 1] + dp[i - 2];
                else dp[i] = dp[i - 1];
            }
        }
        return dp[s.length() - 1];
    }
}