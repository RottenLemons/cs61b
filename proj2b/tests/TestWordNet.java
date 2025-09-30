import main.Graph;
import main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class TestWordNet {
    @Test
    public void testHyponymsSimple(){
        WordNet wn=new WordNet("./data/wordnet/hyponyms11.txt", "./data/wordnet/synsets11.txt");
        assertThat(wn.hyponyms("antihistamine")).isEqualTo(Set.of("antihistamine","actifed"));
    }

    @Test
    public void testHyponymsHard(){
        WordNet wn=new WordNet("./data/wordnet/hyponyms.txt", "./data/wordnet/synsets.txt");
        assertThat(wn.hyponyms("mutation")).isEqualTo(Set.of("chromosomal_mutation", "deletion", "freak", "gene_mutation", "genetic_mutation", "inversion", "leviathan", "lusus_naturae", "monster", "monstrosity", "mutant", "mutation", "point_mutation", "reversion", "saltation", "sport", "transposition", "variation"));
        assertThat(wn.hyponyms("jump")).isEqualTo(Set.of("bounce", "bound", "caper", "capriole", "flinch", "header", "hop", "hurdle", "jump", "jumping", "jumping_up_and_down", "leap", "leaping", "Moro_reflex", "parachuting", "pounce", "quantum_jump", "quantum_leap", "saltation", "skydiving", "spring", "start", "startle", "startle_reaction", "startle_reflex", "startle_response", "vault", "wince"));
        assertThat(wn.hyponyms("joe")).isEqualTo(Set.of());
    }
}
