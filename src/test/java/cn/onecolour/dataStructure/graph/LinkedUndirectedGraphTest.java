package cn.onecolour.dataStructure.graph;

import cn.onecolour.dataStructure.graph.undirectedGraph.LinkedUndirectedGraph;
import cn.onecolour.dataStructure.graph.undirectedGraph.MatrixUndirectedGraph;
import cn.onecolour.dataStructure.graph.undirectedGraph.UndirectedGraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author yang
 * @date 2023/2/15
 * @description
 */
public class LinkedUndirectedGraphTest {

    private UndirectedGraph<Integer> graph;
    private final static String LINE = "-------------------";
    private final static File FILE = new File(System.getProperty("user.dir") + "/src/test/resources/linked_undirected_graph.txt");

    @BeforeEach
    void setUp() {
//        graph = new LinkedUndirectedGraph<>();
        graph = new MatrixUndirectedGraph<>();
        graph.addEdge(1, 2);
        graph.addEdge(1, 5);
        graph.addEdge(2, 5);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(4, 5);
        graph.addEdge(3, 4);
        graph.addEdge(5, 5);
        graph.addVertex(6);
//        graph.print();
//        System.out.println(LINE);
    }

    @AfterEach
    void tearDown() {
        graph.print();
        System.out.println(LINE);
    }

    @Test
    void removeEdgeTest() {
        graph.removeEdge(1, 2);
        Assertions.assertFalse(graph.isLinked(1, 2));
        Assertions.assertEquals(7, graph.edgeCount());
        Assertions.assertEquals(6, graph.vertexCount());
    }

    @Test
    void removeVertexTest() {
        try {
            graph.removeVertex(1);
        } catch (Exception e) {
            Assertions.assertEquals("Can not remove this vertex!", e.getMessage());
        }
        graph.removeEdge(1, 2);
        graph.removeEdge(1, 5);
        graph.removeVertex(1);
        Assertions.assertEquals(6,graph.edgeCount());
        Assertions.assertEquals(5, graph.vertexCount());
    }

    @Test
    void writeTest() throws IOException {
        graph.write(FILE, ",");
    }

    @Test
    void readTest() throws ReflectiveOperationException {
        graph = UndirectedGraph.read(Integer::parseInt, FILE, ",", LinkedUndirectedGraph.class);
    }

    @Test
    void isLinkedTest() {
        Assertions.assertTrue(graph.isLinked(1,2));
        Assertions.assertFalse(graph.isLinked(5,3));
    }
}
