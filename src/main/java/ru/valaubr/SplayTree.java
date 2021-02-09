package ru.valaubr;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SplayTree<T extends Comparable<T>> implements Iterable<T> {

    private final Node aux;
    private Node root;

    public SplayTree() {
        root = null;
        aux = new Node(null);
    }

    public boolean insert(T element) {
        if (root == null) {
            root = new Node(element);
            return true;
        }
        splay(element);

        final int c = element.compareTo(root.key);
        if (c == 0) {
            return false;
        }

        Node n = new Node(element);
        if (c < 0) {
            n.left = root.left;
            n.right = root;
            root.left = null;
        } else {
            n.right = root.right;
            n.left = root;
            root.right = null;
        }
        root = n;
        return true;
    }

    public boolean remove(T element) {
        splay(element);

        if (element.compareTo(root.key) != 0) {
            return false;
        }
        if (root.left == null) {
            root = root.right;
        } else {
            Node x = root.right;
            root = root.left;
            splay(element);
            root.right = x;
        }
        return true;
    }

    public T findMin() {
        Node x = root;
        if (root == null) return null;
        while (x.left != null) x = x.left;
        splay(x.key);
        return x.key;
    }

    public T findMax() {
        Node x = root;
        if (root == null) return null;
        while (x.right != null) x = x.right;
        splay(x.key);
        return x.key;
    }

    public T find(T element) {
        if (root == null) return null;
        splay(element);
        if (root.key.compareTo(element) != 0) return null;
        return root.key;
    }

    public boolean contains(T element) {
        return find(element) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    //Top-Down Splay
    private void splay(T element) {
        Node l, r, t, y;
        l = r = aux;
        t = root;
        aux.left = aux.right = null;
        while (true) {
            final int comp = element.compareTo(t.key);
            if (comp < 0) {
                if (t.left == null) break;
                if (element.compareTo(t.left.key) < 0) {
                    y = t.left;
                    t.left = y.right;
                    y.right = t;
                    t = y;
                    if (t.left == null) break;
                }
                r.left = t;
                r = t;
                t = t.left;
            } else if (comp > 0) {
                if (t.right == null) break;
                if (element.compareTo(t.right.key) > 0) {
                    y = t.right;
                    t.right = y.left;
                    y.left = t;
                    t = y;
                    if (t.right == null) break;
                }
                l.right = t;
                l = t;
                t = t.right;
            } else {
                break;
            }
        }
        l.right = t.left;
        r.left = t.right;
        t.left = aux.right;
        t.right = aux.left;
        root = t;
    }

    public Iterator<T> iterator() {
        return new SplayTreeIterator();
    }

    private class Node {
        public final T key;
        public Node left;
        public Node right;

        public Node(T theKey) {
            key = theKey;
            left = right = null;
        }
    }

    private class SplayTreeIterator implements Iterator<T> {

        private final Deque<Node> nodes = new ArrayDeque<>();

        public SplayTreeIterator() {
            pushLeft(root);
        }

        public boolean hasNext() {
            return !nodes.isEmpty();
        }

        public T next() {
            Node node = nodes.pop();
            if (node != null) {
                pushLeft(node.right);
                return node.key;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void pushLeft(Node node) {
            while (node != null) {
                nodes.push(node);
                node = node.left;
            }
        }
    }
}
