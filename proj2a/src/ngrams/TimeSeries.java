package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        this.putAll(ts.subMap(startYear, true, endYear, true));
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet());
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        return new ArrayList<>(this.values());
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        if (ts.isEmpty() && this.isEmpty()) {
            return new TimeSeries();
        } else if (this.isEmpty()) {
            return new TimeSeries(ts, ts.firstKey(), ts.lastKey());
        } else if (ts.isEmpty()) {
            return new TimeSeries(this, this.firstKey(), this.lastKey());
        }
        TimeSeries newTS = new TimeSeries();
        int minYear = Math.min(ts.firstKey(), this.firstKey());
        int maxYear = Math.max(ts.lastKey(), this.lastKey());

        for (int i = minYear; i <= maxYear; i++) {
            if (this.containsKey(i) || ts.containsKey(i)) {
                newTS.put(i, this.getOrDefault(i, 0.0) + ts.getOrDefault(i, 0.0));
            }
        }
        return newTS;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();
        newTS.forEach((key, value) -> {
            if (!ts.containsKey(key)) {
                throw new IllegalArgumentException();
            }

            newTS.put(key, value/ts.get(key));
        });
        return null;
    }
}
