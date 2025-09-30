package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DFSPath {
    private Graph graph;

    public DFSPath(Graph graph) {
        this.graph = graph;
    }

    public HashSet<Integer> hyponymDFS(ArrayList<Integer> nodes) {
        HashSet<Integer> visited = new HashSet<>();
        for (Integer node : nodes) {
            hyponymDFSHelper(node, visited);
        }
        return visited;
    }

    private void hyponymDFSHelper(int node, Set<Integer> visited) {
        visited.add(node);
        for (Integer neighbor : this.graph.adj(node)) {
            if (!visited.contains(neighbor)) {
                hyponymDFSHelper(neighbor, visited);
            }
        }
    }
}
