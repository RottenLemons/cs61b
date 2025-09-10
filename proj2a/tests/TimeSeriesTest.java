import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testDivide() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 3.0);
        dogPopulation.put(1992, 100.0);
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries dividePopulation = catPopulation.dividedBy(dogPopulation);

        List<Double> expectedDivide = new ArrayList<>();
        expectedDivide.add(0.0);
        expectedDivide.add(1.0);
        expectedDivide.add(0.5);

        for (int i = 0; i < expectedDivide.size(); i += 1) {
            assertThat(dividePopulation.data().get(i)).isWithin(1E-10).of(expectedDivide.get(i));
        }
    }


    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testConstructor() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(1995, 200.0);
        catPopulation.put(1996, 200.0);
        catPopulation.put(1997, 200.0);

        TimeSeries catPopSub = new TimeSeries(catPopulation, 1993, 1997);
        catPopulation.put(1995, 20.0);

        double expected = 200.0;

        for (int i = 0; i < 1997 - 1993; i += 1) {
            assertThat(catPopSub.data().get(i)).isWithin(1E-10).of(expected);
        }
    }

    @Test
    public void testYears() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1993, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(1995, 200.0);
        catPopulation.put(1996, 200.0);
        catPopulation.put(1997, 200.0);

        for (int i = 0; i < catPopulation.size(); i += 1) {
            assertThat(catPopulation.years().get(i)).isEqualTo(i + 1991);
        }
    }
} 