package main;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class WordNet {
    private WordNetGraph graph;
    private DFSPath path;

    public WordNet(String hyponymFilename, String synsetFilename) {
        this.graph = new WordNetGraph(hyponymFilename, synsetFilename);
        this.path = new DFSPath(this.graph);
    }

    public Set<String> hyponyms(String word) {
        TreeSet<String> hyponyms = new TreeSet<>();
        HashSet<Integer> nodes = path.hyponymDFS(graph.idOfWord(word));
        for (Integer node : nodes) {
            for (String hyponym : graph.wordsOfId(node)) {
                hyponyms.add(hyponym);
            }
        }
        return hyponyms;
    }
}
