import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    Node sentinel;
    int size;

    private class Node {
        T item;
        Node next;
        Node prev;

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node prevFirst = sentinel.next;
        sentinel.next = new Node(x, sentinel, prevFirst);
        prevFirst.prev = sentinel.next;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node prevLast = sentinel.prev;
        sentinel.prev = new Node(x, prevLast, sentinel);
        prevLast.next = sentinel.prev;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (Node n = sentinel.next; n != sentinel; n = n.next) {
            returnList.add(n.item);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node oldFirst = sentinel.next;
        sentinel.next = oldFirst.next;
        oldFirst.next.prev = sentinel;
        size--;
        return oldFirst.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node oldLast = sentinel.prev;
        sentinel.prev = oldLast.prev;
        oldLast.prev.next = sentinel;
        size--;
        return oldLast.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()){
            return null;
        }

        Node n = sentinel.next;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }

        return n.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size()){
            return null;
        }

        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node n, int index) {
        if (index == 0) {
            return n.item;
        }

        return getRecursiveHelper(n.next, index--);
    }
}
