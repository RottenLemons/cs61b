package main;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNetGraph extends Graph {
    private HashMap<Integer, String[]> idToWordMap;
    private HashMap<String, ArrayList<Integer>> wordToIdMap;

    public WordNetGraph(String hyponymFilename, String synsetFilename) {
        super();
        this.idToWordMap = new HashMap<>();
        this.wordToIdMap = new HashMap<>();

        In hyponymIn = new In(hyponymFilename);
        In synsetIn = new In(synsetFilename);

        while (synsetIn.hasNextLine()) {
            String[] line = synsetIn.readLine().split(",");
            int synId = Integer.parseInt(line[0]);
            String[] synset = line[1].split(" ");
            for (String s : synset) {
                wordToIdMap.computeIfAbsent(s, k -> new ArrayList<>());
                wordToIdMap.get(s).add(synId);
            }
            idToWordMap.put(synId, synset);
            this.addNode(synId);
        }

        while (hyponymIn.hasNextLine()) {
            String[] line = hyponymIn.readLine().split(",");
            int hypernymId = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int hyponymId = Integer.parseInt(line[i]);
                this.addEdge(hypernymId, hyponymId);
            }
        }
    }

    public String[] wordsOfId(int id) {
        return this.idToWordMap.getOrDefault(id, new String[]{});
    }

    public ArrayList<Integer> idOfWord(String word) {
        return this.wordToIdMap.getOrDefault(word, new ArrayList<>());
    }
}
