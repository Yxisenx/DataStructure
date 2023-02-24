package cn.onecolour.dataStructure.graph.undirectedGraph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 
 * @author yang
 * @date 2023/2/15
 * @description
 */
// FIXME: 2023/2/24 
public class MatrixUndirectedGraph<T> extends UndirectedGraph<T> {
    private final static int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private transient boolean[][] matrix;
    private transient Object[] vertexes;


    public MatrixUndirectedGraph() {
        this(DEFAULT_CAPACITY);
    }

    public MatrixUndirectedGraph(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity + ".");
        }
        matrix = new boolean[initialCapacity][initialCapacity];
        vertexes = new Object[initialCapacity];
        vertexCount = 0;
        edgeCount = 0;
    }



    private int indexOf(T vertex) {
        for (int i = 0; i < vertexCount; i++) {
            if (Objects.equals(vertex, vertexes[i])) {
                return i;
            }
        }
        return -1;
    }

    private int[] indexOf(T v, T u) {
        int[] indexes = new int[]{-1,-1};
        boolean vFound = false;
        boolean uFound = false;
        for (int i = 0; i < vertexCount; i++) {
            if (vFound && uFound)
                break;
            if (!vFound) {
                vFound = Objects.equals(v, vertexes[i]);
                if (vFound)
                    indexes[0] = i;
            }
            if (!uFound) {
                uFound = Objects.equals(v, vertexes[i]);
                if (uFound)
                    indexes[1] = i;
            }
        }
        return indexes;
    }


    @Override
    public void addEdge(T vertexV, T vertexU) {
        super.addEdge(vertexV, vertexU);
        int[] indexes = indexOf(vertexV, vertexU);
        if (indexes[0] == -1) {
            addVertex(vertexV);
            indexes[0] = vertexCount;
        }
        if (indexes[1] == -1) {
            addVertex(vertexU);
            indexes[1] = vertexCount;
        }
        if (matrix[indexes[0]][indexes[1]]) {
            return;
        }

        matrix[indexes[0]][indexes[1]] = true;
        matrix[indexes[1]][indexes[0]] = true;
    }

    @Override
    public void removeEdge(T vertexV, T vertexU) {
        super.removeEdge(vertexV, vertexU);
        int[] indexes = indexOf(vertexV, vertexU);
        if (indexes[0] == -1 || indexes[1] == -1) {
            // v or u is not exists
            return;
        }
        matrix[indexes[0]][indexes[1]] = false;
        matrix[indexes[1]][indexes[0]] = false;
    }

    @Override
    public void addVertex(T vertex) {
        checkVertex(vertex);
        // already exists
        if (indexOf(vertex) != -1) {
            return;
        }
        capacityCheck(vertexCount + 1);
        vertexes[vertexCount] = vertex;
        vertexCount++;
    }


    private void capacityCheck(int minCapacity) {
        if (vertexCount < minCapacity) {
            return;
        }
        grow(minCapacity);
    }

    private void grow(int minCapacity) {
        int oldCapacity = vertexes.length;
        if (minCapacity - oldCapacity > 0) {
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity - minCapacity < 0)
                newCapacity = minCapacity;
            if (newCapacity - MAX_ARRAY_SIZE > 0)
                newCapacity = hugeCapacity(minCapacity);
            // 矩阵和顶点数组扩容
            vertexes = Arrays.copyOf(vertexes, newCapacity);
            for (int i = 0; i < matrix.length; i++) {
                matrix[i] = Arrays.copyOf(matrix[i], newCapacity);
            }
            matrix = Arrays.copyOf(matrix, newCapacity);
        }
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    @Override
    public void removeVertex(T vertex) {
        checkVertex(vertex);
        // not exists
        int index;
        if ((index = indexOf(vertex)) == -1) {
            return;
        }
        int movedIndex = index + 1;
        int moveLength = vertexCount - movedIndex;
        System.arraycopy(vertexes, movedIndex, vertexes, index, moveLength);
        int latestEleIndex = vertexCount - 1;
        vertexes[latestEleIndex] = null;
        // remove edges
        // matrix row
        for (int i = 0; i < latestEleIndex; i++) {
            System.arraycopy(matrix[i], movedIndex, matrix[i], index, moveLength);
            matrix[i][latestEleIndex] = false;
        }
        // matrix column
        System.arraycopy(matrix, movedIndex, matrix, index, moveLength);
        Arrays.fill(matrix[latestEleIndex], false);
        vertexCount --;
    }

    @Override
    public boolean isLinked(T vertexV, T vertexU) {
        int[] indexes = indexOf(vertexV, vertexU);
        if (indexes[0] == -1 || indexes[1] == -1) {
            return false;
        }
        return matrix[indexes[0]][indexes[1]];
    }

    @Override
    public void print() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    @Override
    public void write(File file, String separator) throws IOException {

    }

    @Override
    public void write(File file) throws IOException {

    }
}
