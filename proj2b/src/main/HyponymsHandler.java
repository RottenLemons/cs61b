package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;

    public HyponymsHandler(String hyponymFilename, String synsetFilename) {
        this.wordNet = new WordNet(hyponymFilename, synsetFilename);
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        return wordNet.hyponyms(words.get(0)).toString();
    }
}
