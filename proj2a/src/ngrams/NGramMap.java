package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private HashMap<String, TimeSeries> wordSeries;
    private TimeSeries countSeries;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordSeries = new HashMap<>();
        this.countSeries = new TimeSeries();
        In wordsIn = new In(wordsFilename);
        In countsIn = new In(countsFilename);

        while (wordsIn.hasNextLine()) {
            String word = wordsIn.readString();
            int year = wordsIn.readInt();
            double numTimes = wordsIn.readDouble();

            if (!this.wordSeries.containsKey(word)) {
                this.wordSeries.put(word, new TimeSeries());
            }
            this.wordSeries.get(word).put(year, numTimes);
            wordsIn.readLine();
        }

        while (countsIn.hasNextLine()) {
            String[] line = countsIn.readLine().split(",");
            int year = Integer.parseInt(line[0]);
            double numTotalWords = Double.parseDouble(line[1]);
            this.countSeries.put(year, numTotalWords);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(this.wordSeries.getOrDefault(word, new TimeSeries()), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return new TimeSeries(this.wordSeries.getOrDefault(word, new TimeSeries()), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(this.countSeries, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return this.countHistory(word, startYear, endYear).dividedBy(this.countSeries);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return this.countHistory(word).dividedBy(this.countSeries);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries newTS = new TimeSeries();
        for (String word : words) {
            newTS = newTS.plus(this.countHistory(word, startYear, endYear));
        }
        return newTS.dividedBy(this.countSeries);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries newTS = new TimeSeries();
        for (String word : words) {
            newTS = newTS.plus(this.countHistory(word));
        }
        return newTS.dividedBy(this.countSeries);
    }
}
