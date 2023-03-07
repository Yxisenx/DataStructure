package cn.onecolour.dataStructure.graph.undirectedGraph;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author yang
 * @date 2023/2/15
 * @description
 */
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

    /**
     * return indexes of vertex 'v' and vertex 'u', if v or u is not in graph, return -1
     * @param v vertex 'v'
     * @param u vertex 'u'
     * @return [index of v or -1, index of u or -1]
     */
    private int[] indexOf(T v, T u) {
        int[] indexes = new int[]{-1, -1};
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
                uFound = Objects.equals(u, vertexes[i]);
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
            indexes[0] = vertexCount - 1;
        }
        if (indexes[1] == -1) {
            addVertex(vertexU);
            indexes[1] = vertexCount - 1;
        }
        if (matrix[indexes[0]][indexes[1]]) {
            return;
        }

        matrix[indexes[0]][indexes[1]] = true;
        matrix[indexes[1]][indexes[0]] = true;
        edgeCount++;
    }

    @Override
    public void removeEdge(T vertexV, T vertexU) {
        super.removeEdge(vertexV, vertexU);
        int[] indexes = indexOf(vertexV, vertexU);
        if (indexes[0] == -1 || indexes[1] == -1) {
            // v or u is not exists
            return;
        }
        if (!matrix[indexes[0]][indexes[1]]) {
            return;
        }
        matrix[indexes[0]][indexes[1]] = false;
        matrix[indexes[1]][indexes[0]] = false;
        edgeCount--;
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
        vertexCount--;
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

        StringBuilder vertexesSb = new StringBuilder("[\t");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertexCount; i++) {
            vertexesSb.append(vertexes[i]).append("\t");
            boolean[] row = this.matrix[i];
            sb.append("[\t");
            for (int j = 0; j < vertexCount; j++) {
                sb.append(row[j] ? 1 : 0).append("\t");
            }
            sb.append("]\n");
        }

        vertexesSb.append("]\n\n");
        System.out.println(vertexesSb);
        System.out.println(sb);
    }

    @Override
    public void write(File file, String separator) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (vertexCount > 0) {
            for (int i = 0; i < vertexCount; i++) {
                sb.append(vertexes[i]);
                // start from i, save disk space
                for (int j = i; j < vertexCount; j++) {
                    if (matrix[i][j]) {
                        sb.append(separator).append(vertexes[j]);
                    }
                }
                sb.append("\n");
            }
        }
        FileUtils.write(file, sb.replace(sb.length() - 1, sb.length(), ""), StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file) throws IOException {
        write(file, ",");
    }

    /**
     * @see UndirectedGraph#read(Function, File, String, Class)
     */
    private static <T> UndirectedGraph<T> read(Function<String, T> parseFunction, File file, String separator) throws IOException {
        List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
        UndirectedGraph<T> graph = new MatrixUndirectedGraph<>(lines.size());
        for (String line : lines) {
            String[] arr = line.split(separator);
            if (arr.length > 0) {
                T u = parseFunction.apply(arr[0]);
                if (arr.length == 1) {
                    graph.addVertex(u);
                    continue;
                }

                for (int i = 1; i < arr.length; i++) {
                    graph.addEdge(u, parseFunction.apply(arr[i]));
                }
            }
        }

        return graph;
    }
}
