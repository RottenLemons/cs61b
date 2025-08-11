import deque.ArrayDeque61B;

import deque.Deque61B;
import deque.LinkedListDeque61B;
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

        assertThat(a.get(5)).isNull();
        assertThat(a.get(-1)).isNull();
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
        a.addLast(4);
        a.addLast(5);
        a.toList();
        assertThat(a.toList()).containsExactly(1, 2, 3, 4, 5).inOrder();
    }

    @Test
    public void addFirstTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();

        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addFirst(1);
        a.addFirst(1);
        a.addFirst(1);
        a.addFirst(1);
        a.addFirst(1);
        a.addFirst(1);

        assertThat(a.toList()).containsExactly(1, 1, 1, 1, 1, 1, 1, 2, 3).inOrder();
    }

    @Test
    public void addLastTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();

        a.addLast(3);
        a.addLast(2);
        a.addLast(1);

        assertThat(a.toList()).containsExactly(3, 2, 1).inOrder();
    }

    @Test
    public void sizeTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();

        a.addLast(3);
        a.addLast(2);
        a.addLast(1);

        assertThat(a.size()).isEqualTo(3);
    }

    @Test
    public void isEmptyTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.isEmpty()).isTrue();

        a.addLast(3);
        a.addLast(2);
        a.addLast(1);

        assertThat(a.isEmpty()).isFalse();
    }

    @Test
    public void removeFirstTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.removeFirst()).isNull();
        a.addLast(3);
        a.addLast(2);
        a.addLast(1);

        assertThat(a.removeFirst()).isEqualTo(3);
        assertThat(a.removeFirst()).isEqualTo(2);
        assertThat(a.removeFirst()).isEqualTo(1);

        a.addLast(1);
        assertThat(a.toList()).containsExactly(1);
    }

    @Test
    public void removeLastTest() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.removeLast()).isNull();
        a.addLast(3);
        a.addLast(2);
        a.addLast(1);

        assertThat(a.removeLast()).isEqualTo(1);
        assertThat(a.removeLast()).isEqualTo(2);
        assertThat(a.removeLast()).isEqualTo(3);

        a.addLast(1);
        assertThat(a.toList()).containsExactly(1);
    }

    @Test
    public void iteratorTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");

        lld1 = new ArrayDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    public void equalsTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]

        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld2.addLast("front"); // after this call we expect: ["front"]
        lld2.addLast("middle"); // after this call we expect: ["front", "middle"]
        assertThat(lld1).isNotEqualTo(lld2);
        lld2.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void toStringTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]

        assertThat(lld1.toString()).isEqualTo("[front, middle, back]");
    }
}
