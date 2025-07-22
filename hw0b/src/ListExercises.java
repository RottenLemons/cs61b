import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        int sum = 0;
        for (int i : L) {
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evenOnly = new ArrayList<>();
        for (int i : L) {
            if (i % 2 == 0) {
                evenOnly.add(i);
            }
        }
        return evenOnly;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> commonList = new ArrayList<>();
        for (int i : L1) {
            if (L2.contains(i)) {
                commonList.add(i);
            }
        }
        return commonList;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int countOccurences = 0;
        for (String word : words) {
            for (char c2 : word.toCharArray()) {
                if (c == c2) {
                    countOccurences++;
                }
            }
        }
        return countOccurences;
    }
}
