package cn.onecolour.dataStructure.practice.array.matrix;

import cn.onecolour.dataStructure.practice.utils.MatrixUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author yang
 * @date 2023/3/8
 * @description
 */
public class SwapMatrixDiagonals {
    public static <T> T[][] swapDiagonals(T[][] mat) {
        int[] rowAndColumn = MatrixUtils.rowAndColumnOfMatrix(mat);
        if (rowAndColumn[0] != rowAndColumn[1]) {
            throw new IllegalArgumentException("row and column size should be same");
        }
        return swapDiagonals(mat, rowAndColumn[0]);
    }

    public static <T> T[][] swapDiagonals(T[][] mat, int size) {
        Class<? extends Object[]> rowClass = mat[0].getClass();
        Class<?> eleClass = mat[0][0].getClass();
        //noinspection unchecked
        T[][] res = (T[][]) Array.newInstance(rowClass, size);
        // left == row
        int left = 0;
        int right = size - 1;
        while (left < size) {
            //noinspection unchecked
            res[left] = (T[]) Array.newInstance(eleClass, size);
            System.arraycopy(mat[left], 0, res[left], 0, size);
            swap(res[left], left, right);
            right--;
            left++;
        }
        return res;
    }

    private static<T> void swap(T[] arr, int i1, int i2) {
        T temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    public static void main(String[] args) {
        String[][] matrix = {
                {"a", "b", "c", "d", "e"},
                {"f", "g", "h", "i", "j"},
                {"k", "l", "m", "n", "o"},
                {"p", "q", "r", "s", "t"},
                {"u", "v", "", "s", "t"},
        };
        String[][] mat = swapDiagonals(matrix);
        for (String[] m : mat) {

            System.out.println(Arrays.toString(m));
        }
    }
}
