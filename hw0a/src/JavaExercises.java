/**
 * Skeleton file for HW0A.
 * Exercises sourced from Practice-It by the University of Washington.
 * Original problems available at: https://practiceit.cs.washington.edu/
 *
 * @author Erik Kizior
 */
public class JavaExercises {

    /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on.
     */
    public static void starTriangle() {
        // TODO: Fill in this function
        for (int i = 1; i <= 5; i++) {
            System.out.println("*".repeat(i));
        }
    }

    /**
     * Prints each character of a given string followed by its reverse index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
    public static void printIndexed(String s) {
        // TODO: Fill in this function
        int idxStrLength = s.length() - 1;
        for (int i = idxStrLength; i >= 0; i--) {
            System.out.print(String.valueOf(s.charAt(idxStrLength - i)) + i);
        }
        System.out.println();
    }

    /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: stutter("hello") -> "hheelllloo"
     */
    public static String stutter(String s) {
        // TODO: Fill in this function
        StringBuilder stutterText = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            stutterText.append(String.valueOf(s.charAt(i)).repeat(2));
        }
        return stutterText.toString();
    }

    /**
     * Determines the quadrant of a Cartesian coordinate (x, y).
     * Returns:
     *   1 for the first quadrant (x > 0, y > 0),
     *   2 for the second quadrant (x < 0, y > 0),
     *   3 for the third quadrant (x < 0, y < 0),
     *   4 for the fourth quadrant (x > 0, y < 0),
     *   0 if the point lies on an axis.
     */
    public static int quadrant(int x, int y) {
        // TODO: Fill in this function
        if (x == 0 || y == 0) {
            return 0;
        } else if (x > 0) {
            if (y < 0) {
                return 4;
            } else {
                return 1;
            }
        } else {
            if (y < 0) {
                return 3;
            } else {
                return 2;
            }
        }
    }

    public static void main(String[] args) {
        starTriangle();
        printIndexed("hello");
        System.out.println(stutter("hello"));
        System.out.println(quadrant(3, 4));  // Output: 1
        System.out.println(quadrant(-3, 4)); // Output: 2
        System.out.println(quadrant(-3, -4));// Output: 3
        System.out.println(quadrant(3, -4)); // Output: 4
        System.out.println(quadrant(0, 5));  // Output: 0
    }
}
