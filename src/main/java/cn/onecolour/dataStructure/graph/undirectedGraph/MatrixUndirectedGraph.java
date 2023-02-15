package cn.onecolour.dataStructure.graph.undirectedGraph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author yang
 * @date 2023/2/15
 * @description 
 */
// TODO: 2023/2/15  
public class MatrixUndirectedGraph<T> extends UndirectedGraph<T> {


    public MatrixUndirectedGraph() {
        
    }

    @Override
    public void addVertex(T vertex) {

    }

    @Override
    public void removeVertex(T vertex) {

    }

    @Override
    public boolean isLinked(T vertexV, T vertexU) {
        return false;
    }

    @Override
    public void print() {

    }

    @Override
    public void write(File file, String separator) throws IOException {

    }

    @Override
    public void write(File file) throws IOException {

    }

    public static class Matrix<T> {
        private final static Integer DEFAULT_CAPACITY = 10;
        private int rowUseSize;
        private int columnUseSize;
        private int rowCapacity;
        private int columnCapacity;
        private Object[][] array;
        private final T defaultValue;

        public Matrix(int rowInitialCapacity, int columnInitialCapacity, T defaultValue) {
            if (rowInitialCapacity > 0 && columnInitialCapacity > 0) {
                this.rowUseSize = 0;
                this.columnUseSize = 0;
                this.rowCapacity = rowInitialCapacity;
                this.columnCapacity = columnInitialCapacity;
                array = new Object[rowCapacity][columnCapacity];
                for (Object[] objects : array) {
                    Arrays.fill(objects, defaultValue);
                }
                this.defaultValue = defaultValue;
            } else {
                throw new IllegalArgumentException("Illegal Capacities: row: " + rowInitialCapacity + ", column: " + columnInitialCapacity);
            }
        }

        public Matrix(int rowInitialCapacity, int columnInitialCapacity) {
            this(rowInitialCapacity, columnInitialCapacity, null);
        }

        public Matrix(T defaultValue){
            this(DEFAULT_CAPACITY, DEFAULT_CAPACITY, defaultValue);
        }
        public Matrix() {
            this(DEFAULT_CAPACITY, DEFAULT_CAPACITY, null);
        }



        public void removeRowAndColumn(int rowIndex, int columnIndex) {

        }

        public T getValue(int rowIndex, int columnIndex) {
            //noinspection unchecked
            return (T) array[rowIndex][columnIndex];
        }

        public void setValue(int rowIndex, int columnIndex, T value) {
            array[rowIndex][columnIndex] =  value;
        }

        public int getRowSize() {
            return rowCapacity;
        }

        public int getColumnCapacity() {
            return columnCapacity;
        }

        public void expand(int expandRowSize, int expandColumnSize) {
            if (expandRowSize < 0 || expandColumnSize < 0) {
                throw new IllegalArgumentException("Illegal expand size: row: " + expandRowSize + ", column: " + expandColumnSize);
            }
            if (expandRowSize > 0 || expandColumnSize > 0) {
                int newColumnSize = expandColumnSize + columnCapacity;
                int newRowSize = expandRowSize + rowCapacity;
                Object[][] newMatrix = new Object[newRowSize][newColumnSize];
                if (expandRowSize > 0) {
                    for (int i = rowCapacity; i < newRowSize; i++) {
                        newMatrix[i] = new Object[newColumnSize];
                        Arrays.fill(newMatrix[i], defaultValue);
                    }
                    System.arraycopy(array, 0, newMatrix, 0, rowCapacity);
                }

                if (expandColumnSize > 0) {
                    for (int i = 0; i < rowCapacity; i++) {
                        System.arraycopy(array[i], 0, (newMatrix[i] = new Object[newColumnSize]), 0, columnCapacity);
                        Arrays.fill(newMatrix[i], columnCapacity, newColumnSize, defaultValue);
                    }
                }

                array = newMatrix;
                columnCapacity = newColumnSize;
                rowCapacity = newRowSize;
            }
        }

    }
}
