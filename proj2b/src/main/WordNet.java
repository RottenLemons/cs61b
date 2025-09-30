package main;

import ngrams.NGramMap;

import java.util.*;

public class WordNet {
    private WordNetGraph graph;
    private DFSPath path;
    private NGramMap ngramMap;

    public WordNet(String hyponymFilename, String synsetFilename, String wordFilename, String countFilename) {
        this.graph = new WordNetGraph(hyponymFilename, synsetFilename);
        this.path = new DFSPath(this.graph);
        this.ngramMap = new NGramMap(wordFilename, countFilename);
    }

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

    public Set<String> hyponyms(List<String> words) {
        Set<String> hyponyms = hyponyms(words.getFirst());
        for (int i = 1; i < words.size(); i++) {
            hyponyms.retainAll(hyponyms(words.get(i)));
        }
        return hyponyms;
    }

    public Set<String> hyponyms(List<String> words, int k, int startYear, int endYear) {
        Set<String> hyponyms = hyponyms(words);
        if (k != 0 && k < hyponyms.size()) {
            PriorityQueue<String> pq = new PriorityQueue<>(hyponyms.size(), new HistoryCompare(startYear, endYear));
            for (String hyponym : hyponyms) {
                if (sum(ngramMap.countHistory(hyponym, startYear, endYear).data()) > 0) {
                    pq.add(hyponym);
                }
            }
            while (pq.size() > k) {
                pq.remove();
            }
            hyponyms = new TreeSet<>(pq);
        }
        return hyponyms;
    }

    int sum(List<Double> list) {
        double sum = 0;
        for (Double d : list) {
            sum += d;
        }
        return (int) sum;
    }

    private class HistoryCompare implements Comparator<String> {
        int startYear;
        int endYear;
        HistoryCompare(int startYear, int endYear) {
            this.startYear = startYear;
            this.endYear = endYear;
        }
        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * The implementor must ensure that {@link Integer#signum
         * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * compare(x, y)} must throw an exception if and only if {@code
         * compare(y, x)} throws an exception.)<p>
         * <p>
         * The implementor must also ensure that the relation is transitive:
         * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
         * {@code compare(x, z)>0}.<p>
         * <p>
         * Finally, the implementor must ensure that {@code compare(x,
         * y)==0} implies that {@code signum(compare(x,
         * z))==signum(compare(y, z))} for all {@code z}.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         * @apiNote It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         */
        @Override
        public int compare(String o1, String o2) {
            List<Double> o1Data = ngramMap.countHistory(o1, this.startYear, this.endYear).data();
            List<Double> o2Data = ngramMap.countHistory(o2, this.startYear, this.endYear).data();
            return sum(o1Data) - sum(o2Data);
        }
    }
}
