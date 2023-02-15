package cn.onecolour.dataStructure.graph.undirectedGraph;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * implement undirected graph by adjacency list
 * 邻接链表实现无向图
 *
 * @author yang
 * @date 2023/2/15
 * @description
 */
public class LinkedUndirectedGraph<T> extends UndirectedGraph<T> {


    private final LinkedHashMap<T, LinkedHashSet<T>> vertices;


    public LinkedUndirectedGraph() {
        this.vertices = new LinkedHashMap<>();
    }

    @Override
    public void addVertex(T vertex) {
        checkVertex(vertex);
        addVertexIfAbsent(vertex);
    }

    private LinkedHashSet<T> addVertexIfAbsent(T vertex) {
        LinkedHashSet<T> set = vertices.get(vertex);
        if (set == null) {
            set = new LinkedHashSet<>();
            vertices.put(vertex, set);
            vertexCount++;
        }
        return set;
    }

    @Override
    public void removeVertex(T vertex) {
        checkVertex(vertex);
        LinkedHashSet<T> set = vertices.get(vertex);
        if (set != null) {
            if (!set.isEmpty()) {
                throw new RuntimeException("Can not remove this vertex!");
            }
            vertices.remove(vertex);
            vertexCount--;
        }
    }

    @Override
    public void addEdge(T vertexV, T vertexU) {
        super.addEdge(vertexV, vertexU);
        boolean flagA = addVertexIfAbsent(vertexV).add(vertexU);
        boolean flagB = addVertexIfAbsent(vertexU).add(vertexV);
        if (flagA || flagB) {
            edgeCount++;
        }
    }

    @Override
    public void removeEdge(T vertexV, T vertexU) {
        super.removeEdge(vertexV, vertexU);
        LinkedHashSet<T> setV = vertices.get(vertexV);
        // graph not contain vertex v or there is no edge between vertex 'v' and vertex 'u'
        if (setV == null || !setV.remove(vertexU)) {
            return;
        }
        vertices.get(vertexU).remove(vertexV);
        edgeCount--;
    }

    @Override
    public boolean isLinked(T vertexV, T vertexU) {
        LinkedHashSet<T> set = vertices.get(vertexV);
        return set != null && set.contains(vertexU);
    }

    @Override
    public void print() {
        splice(System.out::println, " -> ");
    }

    private void splice(Consumer<StringBuilder> consumer, String separator) {
        vertices.keySet().stream().sorted().forEach(v -> {
            StringBuilder sb = new StringBuilder(v.toString());
            LinkedHashSet<T> set = vertices.get(v);
            if (!set.isEmpty())
                sb.append(separator).append(set.stream().sorted().map(String::valueOf).collect(Collectors.joining(separator)));
            consumer.accept(sb);
        });
    }

    @Override
    public void write(File file, String separator) throws IOException {
        List<String> lines = new ArrayList<>(vertexCount);
        splice(sb -> lines.add(sb.toString()), separator);
        FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), lines, false);
    }

    @Override
    public void write(File file) throws IOException {
        write(file, ",");
    }

    /**
     * @see UndirectedGraph#read(Function, File, String, Class)
     */
    private static <T> UndirectedGraph<T> read(Function<String, T> parseFunction, File file, String separator) throws IOException {
        LinkedUndirectedGraph<T> graph = new LinkedUndirectedGraph<>();
        List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
        for (String line : lines) {
            String[] splits = line.split(separator);
            int arrSize = splits.length;
            T vertex;
            if (arrSize > 0) {
                vertex = parseFunction.apply(splits[0]);
                graph.addVertex(vertex);
                if (arrSize > 1)
                    for (int i = 1; i < arrSize; i++)
                        graph.addEdge(vertex, parseFunction.apply(splits[i]));
            }
        }

        return graph;
    }
}
