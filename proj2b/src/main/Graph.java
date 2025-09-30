package main;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    /**
     * Implementation of a Adjacency list graph data type
     */
    private HashMap<Integer, ArrayList<Integer>> adjList;
    private int V;
    private int E;

    public Graph() {
        this.adjList = new HashMap<>();
        this.E = 0;
        this.V = 0;
    }

    public void addNode(int v) {
        this.adjList.put(v, new ArrayList<>());
        this.V++;
    }

    public void addEdge(int v, int w) {
        this.adjList.get(v).add(w);
        this.E++;
    }

    public Iterable<Integer> adj(int v) {
        return this.adjList.get(v);
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }
}
