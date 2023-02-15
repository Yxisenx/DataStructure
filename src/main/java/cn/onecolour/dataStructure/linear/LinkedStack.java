package cn.onecolour.dataStructure.linear;

import java.util.Iterator;
import java.util.Objects;

/**
 * @author yang
 * @date 2023/2/14
 * @description
 */
public class LinkedStack<T> implements Stack<T> {
    private Node<T> first;
    private int size;

    @Override
    public void push(T ele) {
        first = new Node<>(ele, first);
        size++;
    }

    @Override
    public T pop() {
        if (first != null) {
            T result = first.getValue();
            first = first.getNext();
            return result;
        }
        return null;
    }

    @Override
    public T peek() {
        if (first != null) {
            return first.getValue();
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    public static class StackIterator<T> implements Iterator<T> {
        private Node<T> node;

        public StackIterator(Node<T> first) {
            this.node = first;
        }

        @Override
        public boolean hasNext() {
            return node.getNext() != null;
        }

        @Override
        public T next() {
            if (node != null) {
                T r = node.getValue();
                node = node.getNext();
                return r;
            }
            return null;
        }
    }


    private static class Node<T> {
        private T value;
        private Node<T> next;

        public Node() {
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?> node = (Node<?>) o;

            if (!Objects.equals(value, node.value)) return false;
            return Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }
}
