package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size;

    private class LinkedListDequeIterator implements Iterator<T> {
        Node ptr;

        public LinkedListDequeIterator() {
            this.ptr = sentinel;
        }

        @Override
        public boolean hasNext() {
            if (ptr.next == sentinel) {
                return false;
            }

            return true;
        }

        @Override
        public T next() {
            ptr = ptr.next;
            return ptr.item;
        }
    }

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

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Deque61B otherList) {
            if (otherList.size() != this.size()) {
                return false;
            }
            Iterator otherIterator = otherList.iterator();
            for (T thisItem : this) {
                if  (!thisItem.equals(otherIterator.next())) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        return this.toList().toString();
    }
}
