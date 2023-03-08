package cn.onecolour.dataStructure.practice.utils;

import java.util.Objects;

/**
 * @author yang
 * @date 2023/3/7
 * @description
 */
public class MatrixUtils {

    public static <T> int[] rowAndColumnOfMatrix(T[][] matrix) {
        Objects.requireNonNull(matrix, "matrix cannot be null");
        int rowNum = matrix.length;
        Integer columnNum = null;
        for (T[] row : matrix) {
            if (row == null) {
                throw new IllegalArgumentException("wrong matrix");
            } else {
                if (columnNum == null) {
                    columnNum = row.length;
                }
                if (columnNum != row.length) {
                    throw new IllegalArgumentException("wrong matrix");
                }
            }
        }
        //noinspection ConstantConditions
        return new int[]{rowNum, columnNum};
    }
}
