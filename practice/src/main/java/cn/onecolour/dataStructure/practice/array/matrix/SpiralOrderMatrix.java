package cn.onecolour.dataStructure.practice.array.matrix;

import cn.onecolour.dataStructure.practice.utils.MatrixUtils;

import java.util.Arrays;

/**
 * @author yang
 * @date 2023/3/7
 * @description
 */
public class SpiralOrderMatrix {
    public static <T> T[] spiralOrder(T[][] mat) {
        int[] rowAndColumn = MatrixUtils.rowAndColumnOfMatrix(mat);
        return spiralOrder(mat, rowAndColumn[0], rowAndColumn[1]);
    }

    public static <T> T[] spiralOrder(T[][] mat, int rowMax, int columnMax) {
        int size = rowMax * columnMax;
        Object[] array = new Object[size];
        int count = 0;
        int rowIndex = 0;
        int columnIndex = 0;
        int[] rowBorder = {1, rowMax - 1};
        int[] columnBorder = {0, columnMax - 1};
        Operation operation = Operation.COLUMN_PLUS;
        while (count < size) {
            array[count++] = mat[rowIndex][columnIndex];
            switch (operation) {
                case COLUMN_PLUS:
                    columnIndex++;
                    if (columnIndex == columnBorder[1]) {
                        columnBorder[1] -= 1;
                        operation = Operation.ROW_PLUS;
                    }
                    break;
                case ROW_PLUS:
                    rowIndex++;
                    if (rowIndex == rowBorder[1]) {
                        rowBorder[1] -= 1;
                        operation = Operation.COLUMN_MINUS;
                    }
                    break;
                case COLUMN_MINUS:
                    columnIndex--;
                    if (columnIndex == columnBorder[0]) {
                        columnBorder[0] += 1;
                        operation = Operation.ROW_MINUS;
                    }
                    break;
                case ROW_MINUS:
                    rowIndex--;
                    if (rowIndex == rowBorder[0]) {
                        rowBorder[0] += 1;
                        operation = Operation.COLUMN_PLUS;
                    }
                    break;
            }
        }
        //noinspection unchecked
        return (T[]) array;
    }

    public static void main(String[] args) {
        Integer[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};
        System.out.println(Arrays.toString(spiralOrder(matrix)));
    }

    private enum Operation {
        ROW_PLUS, COLUMN_PLUS, ROW_MINUS, COLUMN_MINUS
    }
}
