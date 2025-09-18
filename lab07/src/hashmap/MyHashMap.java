package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Mahir
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int numItems;
    private double loadFactor;
    private int numOfBuckets;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.numOfBuckets = 16;
        this.numItems = 0;
        this.loadFactor = 0.75;
        this.buckets = new Collection[numOfBuckets];
    }

    public MyHashMap(int initialCapacity) {
        this.numOfBuckets = initialCapacity;
        this.numItems = 0;
        this.loadFactor = 0.75;
        this.buckets = new Collection[numOfBuckets];
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.numOfBuckets = initialCapacity;
        this.numItems = 0;
        this.loadFactor = loadFactor;
        this.buckets = new Collection[numOfBuckets];
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        Collection<Node> bucket = calcIdxAndBucket(key);

        for (Node node : bucket) {
            if (key.equals(node.key)) {
                node.value = value;
                return;
            }
        }
        bucket.add(new Node(key, value));
        this.numItems++;

        if ((double)this.numItems/this.numOfBuckets > this.loadFactor) {
            Collection<Node>[] temp = new Collection[numOfBuckets * 2];
            for (int i = 0; i < numOfBuckets; i++) {
                if (this.buckets[i] != null) {
                    for (Node node : this.buckets[i]) {
                        bucket = calcIdxAndBucket(node.key, temp);
                        bucket.add(node);
                    }
                }
            }
            numOfBuckets *= 2;
            this.buckets = temp;
        }
    }

    private Collection<Node> calcIdxAndBucket(K key) {
        int bucketIdx = Math.floorMod(key.hashCode(), numOfBuckets);
        if (this.buckets[bucketIdx] == null) {
            this.buckets[bucketIdx] = createBucket();
        }
        return this.buckets[bucketIdx];
    }

    private Collection<Node> calcIdxAndBucket(K key, Collection<Node>[] buckets) {
        int bucketIdx = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[bucketIdx] == null) {
            buckets[bucketIdx] = createBucket();
        }
        return buckets[bucketIdx];
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Collection<Node> bucket = calcIdxAndBucket(key);

        for (Node node : bucket) {
            if (key.equals(node.key)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        Collection<Node> bucket = calcIdxAndBucket(key);

        for (Node node : bucket) {
            if (key.equals(node.key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return numItems;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        this.buckets = new Collection[numOfBuckets];
        numItems = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        List<K> list = new ArrayList<K>();
        for (int i = 0; i < this.numOfBuckets; i++) {
            if (this.buckets[i] != null) {
                for (Node node : this.buckets[i]) {
                    list.add(node.key);
                }
            }
        }
        return new HashSet<>(list);
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        Collection<Node> bucket = this.calcIdxAndBucket(key);
        for (Node node : bucket) {
            if (key.equals(node.key)) {
                this.numItems--;
                bucket.remove(node);
                return node.value;
            }
        }
        return null;
    }


    public V remove(K key, V value) {
        Collection<Node> bucket = this.calcIdxAndBucket(key);
        if (bucket.remove(new Node(key, value))) {
            return value;
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private class HashMapIterator implements Iterator<K> {
        int idx = 0;
        int bucketIdx = 0;
        Iterator<Node> bucketIterator;

        private void findNewBucketIndex() {
            while (buckets[this.bucketIdx] != null) {
                this.bucketIdx++;
            }
            this.bucketIterator = buckets[this.bucketIdx].iterator();
        }

        HashMapIterator() {
            findNewBucketIndex();
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return this.idx < numItems;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            K key = this.bucketIterator.next().key;
            if (!this.bucketIterator.hasNext()) {
                findNewBucketIndex();
            }
            this.idx++;
            return key;
        }
    }
}
