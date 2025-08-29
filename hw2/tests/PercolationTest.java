import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }


    @Test
    public void isOpenTest() {
        int N = 2;
        Percolation p = new Percolation(N);
        int[][] openSites = {
                {0, 1},
                {1, 0},
        };

        int[][] closeSites = {
                {0, 0},
                {1, 1},
        };

        for (int[] site : openSites) {
            p.open(site[0], site[1]);
            p.open(site[0], site[1]);
            assertThat(p.isOpen(site[0], site[1])).isTrue();
        }

        for (int[] site : closeSites) {
            assertThat(p.isOpen(site[0], site[1])).isFalse();
        }
    }

    @Test
    public void numberOfOpenSitesTest() {
        int N = 2;
        Percolation p = new Percolation(N);
        int expected = 2;
        int[][] openSites = {
                {0, 1},
                {1, 0},
        };


        for (int[] site : openSites) {
            p.open(site[0], site[1]);
            p.open(site[0], site[1]);
        }

        assertThat(p.numOfOpenSites).isEqualTo(expected);
    }

    @Test
    public void isFullTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        int[][] openSites = {
                {0, 1},
                {1, 0},
                {2, 0},
                {1, 1},
                {2, 2}
        };

        int[][] notFullSites = {
                {0, 0},
                {1, 2},
                {2, 2}
        };

        int[][] fullSites = {
                {0, 1},
                {1, 0},
                {2, 0},
        };

        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }

        for (int[] site : fullSites) {
            assertThat(p.isFull(site[0], site[1])).isTrue();
        }

        for (int[] site : notFullSites) {
            assertThat(p.isFull(site[0], site[1])).isFalse();
        }
    }

    @Test
    public void percolationTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        int[][] openSites = {
                {0, 1},
                {1, 0},
                {2, 0},
                {2, 2}
        };

        for (int[] site : openSites) {
            p.open(site[0], site[1]);
            assertThat(p.percolates()).isFalse();
        }

        p.open(1, 1);
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void backwashTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        int[][] openSites = {
                {0, 0},
                {1, 0},
                {1, 2},
                {2, 0},
                {2, 2},
        };

        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }

        assertThat(p.isFull(1, 2)).isFalse();
        assertThat(p.isFull(2, 2)).isFalse();
    }
}
