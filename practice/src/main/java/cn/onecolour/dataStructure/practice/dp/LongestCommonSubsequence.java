package cn.onecolour.dataStructure.practice.dp;

/**
 * 最长公共子序列
 *
 * @author yang
 * @date 2023/3/9
 * @description
 */
public class LongestCommonSubsequence {

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    public static String lcsViolentSolution(String strA, String strB) {
        if (isEmpty(strA) || isEmpty(strB)) {
            return "";
        }
        return lcsViolent(strA.toCharArray(), strB.toCharArray(), strA.length() - 1, strB.length() - 1);

    }

    private static String lcsViolent(char[] charsA, char[] charsB, int indexA, int indexB) {
        if (indexA == -1 || indexB == -1) {
            return "";
        }
        if (charsA[indexA] == charsB[indexB]) {
            return lcsViolent(charsA, charsB, indexA - 1, indexB - 1) + charsA[indexA];
        } else {
            String s1 = lcsViolent(charsA, charsB, indexA - 1, indexB);
            String s2 = lcsViolent(charsA, charsB, indexA, indexB - 1);
            return s1.length() > s2.length() ? s1 : s2;
        }
    }


    private static int lcsDpLen(char[] charsA, char[] charsB, int indexA, int indexB, int[][] dp) {
        if (indexA == -1 || indexB == -1) {
            return 0;
        }
        if (charsA[indexA] == charsB[indexB]) {
            dp[indexA + 1][indexB + 1] = lcsDpLen(charsA, charsB, indexA - 1, indexB - 1, dp) + 1;
        } else {
            dp[indexA + 1][indexB + 1] = Math.max(lcsDpLen(charsA, charsB, indexA - 1, indexB, dp),
                    lcsDpLen(charsA, charsB, indexA, indexB - 1, dp));
        }
        return dp[indexA + 1][indexB + 1];
    }

    public static String lcs(String strA, String strB) {
        if (isEmpty(strA) || isEmpty(strB)) {
            return "";
        }
        int[][] dp = new int[strA.length() + 1][strB.length() + 1];
        lcsDpLen(strA.toCharArray(), strB.toCharArray(), strA.length() - 1, strB.length() - 1, dp);
        for (int i = 1, j = 1; i <= strA.length();) {

        }
        return "";
    }

    public static void main(String[] args) {
        lcs("abbab", "acbbacb");
    }


}
