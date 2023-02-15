package cn.onecolour.dataStructure.graph.undirectedGraph;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

/**
 * 无向图
 *
 * @author yang
 * @date 2023/2/14
 * @description
 */
public abstract class UndirectedGraph<T> {
    protected int vertexCount;
    protected int edgeCount;


    public int vertexCount() {
        return vertexCount;
    }


    public int edgeCount() {
        return edgeCount;
    }

    /**
     * Add an edge into graph with two vertices v and u, if vertex v or vertex u is not in graph, then add it into graph.
     * If graph already had an edge links vertex v and u, then skip current operation
     */
    public void addEdge(T vertexV, T vertexU) {
        checkVertices(vertexV, vertexU);
    }


    /**
     * add a vertex into graph, if it was already in graph
     */
    public abstract void addVertex(T vertex);


    /**
     * remove a vertex from graph, if it was already in graph, if the vertex has more than zero edge, then throw exception.
     */
    public abstract void removeVertex(T vertex);


    public void removeEdge(T vertexV, T vertexU) {
        checkVertices(vertexV, vertexU);
    }

    public abstract boolean isLinked(T vertexV, T vertexU);

    public abstract void print();

    public abstract void write(File file, String separator) throws IOException;

    public abstract void write(File file) throws IOException;

    @SuppressWarnings("rawtypes")
    public static <T, U extends UndirectedGraph> UndirectedGraph<T> read(Function<String, T> parseFunction, File file,
                                                                         String separator, Class<U> subClazz) throws ReflectiveOperationException {
        if (parseFunction == null || file == null || separator == null) {
            throw new NullPointerException();
        }
        if (separator.length() == 0) {
            throw new IllegalArgumentException();
        }
        Method readMethod = subClazz.getDeclaredMethod("read", Function.class, File.class, String.class);
        boolean accessible = readMethod.isAccessible();
        readMethod.setAccessible(true);
        //noinspection unchecked
        UndirectedGraph<T> result = (U) readMethod.invoke(null, parseFunction, file, separator);
        readMethod.setAccessible(accessible);
        return result;
    }


    protected void checkVertex(T vertex) {
        Objects.requireNonNull(vertex, "Vertex cannot be null!");
    }

    protected void checkVertices(T vertexV, T vertexU) {
        checkVertex(vertexV);
        checkVertex(vertexU);
    }

}
