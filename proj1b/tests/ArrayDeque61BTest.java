import deque.ArrayDeque61B;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }


    @Test
    public void getTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);

        assertThat(a.get(5)).isEqualTo(null);
        assertThat(a.get(-1)).isEqualTo(null);
        assertThat(a.get(0)).isEqualTo(3);
        assertThat(a.get(2)).isEqualTo(1);
    }

    @Test
    public void toListTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.toList()).containsExactly();

        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.toList();
        assertThat(a.toList()).containsExactly(1, 2, 3).inOrder();
    }

    @Test
    public void addFirstTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();

        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);

        assertThat(a.toList()).containsExactly(1, 2, 3).inOrder();
    }



}
