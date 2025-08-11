package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] deque;
    private int size;
    private int firstIdx; // Index of first element, Can be negative
    private int afterLastIdx; // Index after last element Can be negative

    private class ArrayDequeIterator implements Iterator<T> {
        int index;

        public ArrayDequeIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if (index < size()) {
                return true;
            }

            return false;
        }

        @Override
        public T next() {
            T item = get(this.index);
            index++;
            return item;
        }
    }

    public ArrayDeque61B() {
        deque = (T[]) new Object[8];
        size = 0;
        firstIdx = 0;
        afterLastIdx = 0;
    }

    @Override
    public void addFirst(T x) {
        checkAndExpandDeque();
        this.firstIdx--;
        this.deque[Math.floorMod(this.firstIdx, this.deque.length)] = x;
        this.size++;
    }

    @Override
    public void addLast(T x) {
        checkAndExpandDeque();
        this.deque[Math.floorMod(this.afterLastIdx, this.deque.length)] = x;
        this.afterLastIdx++;
        this.size++;
    }

    private void checkAndExpandDeque() {
        if (this.size + 1 > this.deque.length) {
            changeDeque(this.deque.length * 2);
        }
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
        checkAndContractDeque();
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
        checkAndContractDeque();
        this.afterLastIdx--;
        T item = this.deque[Math.floorMod(this.afterLastIdx, this.deque.length)];
        this.size--;
        return item;
    }

    private void checkAndContractDeque() {
        if ((double) this.size() / this.deque.length <= 0.25) {
            changeDeque(this.deque.length / 2);
        }
    }

    private void changeDeque(int newSize) {
        T[] newDeque = (T[]) new Object[newSize];
        for (int i = this.firstIdx; i < this.afterLastIdx ; i++) {
            newDeque[i - this.firstIdx] = this.deque[Math.floorMod(i, this.deque.length)];
        }
        this.deque = newDeque;
        this.firstIdx = 0;
        this.afterLastIdx = size();
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
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
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
