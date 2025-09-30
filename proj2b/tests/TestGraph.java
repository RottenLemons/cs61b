import main.Graph;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class TestGraph {
    @Test
    public void testAddNode() {
        Graph graph = new Graph();
        graph.addNode(10);
        graph.addNode(1);
        graph.addNode(0);
        assertThat(graph.V()).isEqualTo(3);
    }

    @Test
    public void testAddEdge() {
        Graph graph = new Graph();
        graph.addNode(10);
        graph.addNode(1);
        graph.addNode(0);
        graph.addNode(2);
        graph.addEdge(10, 1);
        graph.addEdge(10, 0);
        graph.addEdge(10, 2);
        graph.addEdge(2, 10);
        assertThat(graph.E()).isEqualTo(4);
        assertThat(graph.adj(10)).containsExactly(1, 0, 2);
        assertThat(graph.adj(2)).containsExactly(10);
        assertThat(graph.adj(0)).containsExactly();
    }
}
