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


    private static LcsResult lcs(char[] charsA, char[] charsB) {
        int indexA = charsA.length - 1;
        int indexB = charsB.length - 1;
        LcsResult lcsResult = new LcsResult(indexA, indexB);
        int[][] dp = lcsResult.dp;
        MOVE[][] moves = lcsResult.moves;
        for (int a = 0; a <= indexA; a++) {
            for (int b = 0; b <= indexB; b++) {
                if (charsA[a] == charsB[b]) {
                    dp[a + 1][b + 1] = dp[a][b] + 1;
                    moves[a + 1][b + 1] = MOVE.MOVE_CORNER;
                } else if (dp[a][b + 1] > dp[a + 1][b]) {
                    dp[a + 1][b + 1] = dp[a][b + 1];
                    moves[a + 1][b + 1] = MOVE.MOVE_UP;
                } else {
                    dp[a + 1][b + 1] = dp[a + 1][b];
                    moves[a + 1][b + 1] = MOVE.MOVE_LEFT;
                }
            }
        }
        lcsResult.charsA = charsA;
        lcsResult.charsB = charsB;
        lcsResultResolve(lcsResult);
        return lcsResult;
    }

    private static void lcsResultResolve(LcsResult result) {
        StringBuilder sb = new StringBuilder();
        MOVE[][] moves = result.moves;
        int a = result.charsA.length;
        int b = result.charsB.length;

        result.length = result.dp[a][b];
        while (a > 0 && b > 0) {
            switch (moves[a][b]) {
                case MOVE_UP:
                    a--;
                    break;
                case MOVE_LEFT:
                    b--;
                    break;
                case MOVE_CORNER:
                    sb.append(result.charsB[b - 1]);
                    a--;
                    b--;
            }
        }
        result.common = sb.reverse().toString();
    }


    public static LcsResult lcs(String strA, String strB) {
        if (isEmpty(strA) || isEmpty(strB)) {
            LcsResult result = new LcsResult(0, 0);
            result.length = 0;
            result.common = "";
            return result;
        }
        char[] charsA = strA.toCharArray();
        char[] charsB = strB.toCharArray();
        return lcs(charsA, charsB);
    }

    public static String printLcsResult(LcsResult result) {
        int[][] dp = result.dp;
        char[] charsB = result.charsB;
        StringBuilder sb = new StringBuilder(" \t").append(" \t");
        for (char c : charsB) {
            sb.append(c).append("\t");
        }
        sb.append("\n");
        char[] charsA = result.charsA;
        for (int i = 0; i < dp.length; i++) {
            if (i > 0) {
                sb.append(charsA[i - 1]).append("\t");
            } else {
                sb.append(" \t");
            }
            for (int j = 0; j < dp[i].length; j++) {
                if (result.moves[i][j] != null) {
                    String s = "";
                    switch (result.moves[i][j]) {
                        case MOVE_CORNER:
                            s = "↖";
                            break;
                        case MOVE_LEFT:
                            s = "←";break;
                        case MOVE_UP:
                            s = "↑";
                            break;
                    }
                    sb.append(s);
                } else {
                    sb.append(" ");
                }
                sb.append(dp[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private enum MOVE {
        MOVE_UP, // 上移
        MOVE_LEFT,  // 左移
        MOVE_CORNER // 下移
    }

    public static class LcsResult {
        private final int[][] dp;
        private final MOVE[][] moves;
        private char[] charsA;
        private char[] charsB;

        private String common;

        private int length;

        public LcsResult(int indexA, int indexB) {
            this.dp = new int[indexA + 2][indexB + 2];
            this.moves = new MOVE[indexA + 2][indexB + 2];
        }

        public String getCommon() {
            return common;
        }

        public int getLength() {
            return length;
        }
    }


    public static void main(String[] args) {
        LcsResult lcsResult = lcs("ABCBDAB", "BDCABA");
        System.out.println("Lcs-length: " + lcsResult.length);
        System.out.println("Result: " + lcsResult.common);
        System.out.println(printLcsResult(lcsResult));
    }


}
