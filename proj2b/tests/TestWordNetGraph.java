import main.WordNetGraph;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static com.google.common.truth.Truth.assertThat;

public class TestWordNetGraph {
    @Test
    public void testConstructor() {
        WordNetGraph graph = new WordNetGraph("./data/wordnet/hyponyms11.txt", "./data/wordnet/synsets11.txt");
        assertThat(graph.V()).isEqualTo(11);
        assertThat(graph.adj(5)).containsExactly(6, 7);
        assertThat(graph.adj(8)).containsExactly(10);
    }

    @Test
    public void testWordsOfId() {
        WordNetGraph graph = new WordNetGraph("./data/wordnet/hyponyms11.txt", "./data/wordnet/synsets11.txt");

        assertThat(graph.wordsOfId(6)).isEqualTo(new String[]{"jump", "leap"});
        assertThat(graph.wordsOfId(8)).isEqualTo(new String[]{"antihistamine"});
    }

    @Test
    public void testIdOfWord() {
        WordNetGraph graph = new WordNetGraph("./data/wordnet/hyponyms11.txt", "./data/wordnet/synsets11.txt");
        assertThat(graph.idOfWord("jump")).containsExactly(4, 6);
        assertThat(graph.idOfWord("increase")).containsExactly(5);
    }
}
