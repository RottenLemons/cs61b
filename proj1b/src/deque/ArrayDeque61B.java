package deque;

import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>{
    T[] deque;
    int size;
    int firstIdx; // Index of first element, Can be negative
    int afterLastIdx; // Index after last element Can be negative

    public ArrayDeque61B() {
        deque = (T[]) new Object[8];
        size = 0;
        firstIdx = 0;
        afterLastIdx = 0;
    }

    @Override
    public void addFirst(T x) {
        if (this.size + 1 > this.deque.length) {
            expandDeque();
        }
        this.firstIdx--;
        this.deque[Math.floorMod(this.firstIdx, this.deque.length)] = x;
        this.size++;
    }

    @Override
    public void addLast(T x) {
        if (this.size + 1 > this.deque.length) {
            expandDeque();
        }
        this.deque[Math.floorMod(this.afterLastIdx, this.deque.length)] = x;
        this.afterLastIdx++;
        this.size++;
    }

    private void expandDeque() {
        T[] newDeque = (T[]) new Object[this.deque.length * 2];
        for (int i = this.firstIdx; i < this.afterLastIdx ; i++) {
            newDeque[i - this.firstIdx] = this.deque[Math.floorMod(i, this.deque.length)];
        }
        this.deque = newDeque;
        this.firstIdx = 0;
        this.afterLastIdx = size();
    }

    @Override
    public List<T> toList() {
        List<T> listRepr = new ArrayList<>();
        for (int i = this.firstIdx; i < this.afterLastIdx ; i++) {
            listRepr.add(this.deque[Math.floorMod(i, this.deque.length)]);
        }

        return listRepr;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T item = this.deque[Math.floorMod(this.firstIdx, this.deque.length)];
        this.firstIdx++;
        this.size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        this.afterLastIdx--;
        T item = this.deque[Math.floorMod(this.afterLastIdx, this.deque.length)];
        this.size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= this.size() || index < 0) {
            return null;
        }
        return deque[Math.floorMod(this.firstIdx + index, this.deque.length)];
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
