import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    Node root;
    int size = 0;
    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class BSTMapIterator implements Iterator<K> {
        private Set<K> keys;
        private Iterator<K> keyIterator;

        private BSTMapIterator() {
            addToSet(root, keys);
            keyIterator = keys.iterator();
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
            return keyIterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            return keyIterator.next();
        }
    }

    private enum ChildType {
        ROOT, LEFT, RIGHT
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
        this.root = putRecursive(this.root, key, value);
    }

    private Node putRecursive(Node node, K key, V value) {
        if (node == null) {
            this.size++;
            return new Node(key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = putRecursive(node.right, key, value);
        } else if (key.compareTo(node.key) < 0) {
            node.left = putRecursive(node.left, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return recursiveGet(this.root, key).value;
    }

    private Node recursiveGet(Node node, K key) {
        if (node == null) {
            return new Node(null, null);
        } else if (key.compareTo(node.key) > 0) {
            return recursiveGet(node.right, key);
        } else if (key.compareTo(node.key) < 0) {
            return recursiveGet(node.left, key);
        } else {
            return node;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return !Objects.isNull(recursiveGet(this.root, key).key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        this.size = 0;
        this.root = null;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        addToSet(this.root, keySet);
        return keySet;
    }

    private void addToSet(Node node, Set<K> keySet) {
        if (node == null) {
            return;
        }

        addToSet(node.left, keySet);
        keySet.add(node.key);
        addToSet(node.right, keySet);
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        Node parent = null;
        Node child = this.root;
        ChildType flag = ChildType.ROOT;
        while (child != null && child.key != key) {
            parent = child;
            if (key.compareTo(child.key) > 0) {
                child = child.right;
                flag = ChildType.RIGHT;
            } else {
                child = child.left;
                flag = ChildType.LEFT;
            }
        }
        if (child == null) {
            return null;
        }

        putChild(child, parent, flag);
        this.size--;
        return child.value;
    }

    private Node newChild(Node node) {
        if (node.left == null && node.right == null) {
            return null;
        } else if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }

        Node smallestRight = node.right;
        Node parent = node;
        ChildType flag = ChildType.RIGHT;
        while (smallestRight.left != null) {
            flag = ChildType.LEFT;
            parent = smallestRight;
            smallestRight = smallestRight.left;
        }
        putChild(smallestRight, parent, flag);
        smallestRight.left = node.left;
        smallestRight.right = node.right;
        return smallestRight;
    }

    private void putChild(Node child, Node parent, ChildType flag) {
        if (flag == ChildType.LEFT) {
            parent.left = newChild(child);
        } else if (flag == ChildType.RIGHT) {
            parent.right = newChild(child);
        } else {
            this.root = newChild(child);
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }
}
