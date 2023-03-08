package cn.onecolour.dataStructure.practice.array.matrix;

import cn.onecolour.dataStructure.practice.utils.MatrixUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author yang
 * @date 2023/3/7
 * @description
 */
public class ZigZagMatrix {

    public static <T> T[] zigZagMatrix(T[][] matrix) {
        int[] rowAndColumn = MatrixUtils.rowAndColumnOfMatrix(matrix);
        return zigZagMatrix(matrix, rowAndColumn[0], rowAndColumn[1]);
    }

    public static <T> T[] zigZagMatrix(T[][] matrix, int rowNum, int columnNum) {
        int size = rowNum * columnNum;
        if (size < 0) {
            throw new RuntimeException("Matrix is too big.");
        }
        Object[] arr = new Object[size];
        arr[0] = matrix[0][0];
        int rowIndex = 0;
        int columnIndex = 1;
        boolean rowIndexPlus = true;
        int count = 1;
        while (rowIndex != rowNum -1 || columnIndex != columnNum -1) {
            arr[count++] = matrix[rowIndex][columnIndex];
            if (rowIndexPlus) {
                if (rowIndex == rowNum - 1) {
                    columnIndex++;
                    rowIndexPlus = false;
                } else
                if (columnIndex == 0) {
                    rowIndex++;
                    rowIndexPlus = false;
                } else {
                    rowIndex++;
                    columnIndex--;
                }
            } else {
                if (columnIndex == columnNum - 1) {
                    rowIndex++;
                    rowIndexPlus = true;
                }
                else if (rowIndex == 0) {
                    columnIndex++;
                    rowIndexPlus = true;
                } else {
                    rowIndex--;
                    columnIndex++;
                }
            }
        }
        arr[arr.length - 1] = matrix[rowIndex][columnIndex];
        //noinspection unchecked
        return (T[]) arr;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(zigZagMatrix(new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 3, 3)));
    }

}
