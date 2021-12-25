package cn.onecolour.dataStrueture.linear;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author yang
 * @date 2021/12/22
 * @description 单向链表
 */
@SuppressWarnings("unused")
public class SingleLinkedList<T> implements Serializable {
    transient Node<T> first;
    transient int size = 0;

    public SingleLinkedList(Collection<T> collection) {
        this();
        addAll(collection);
    }

    public SingleLinkedList(T[] arr) {
        this(Arrays.asList(arr));
    }

    public SingleLinkedList() {
    }

    public SingleLinkedList(SingleLinkedList<T> list) {
        this();
        if (list == null || list.size == 0) {
            return;
        }
        this.first = new Node<>(list.first.element, list.first.next);
        Node<T> tNode = this.first;
        Node<T> temp = list.first;
        while (temp != null) {
            if (temp.next != null) {
                tNode.next = new Node<>();
                tNode.next.element = temp.next.element;
            } else {
                tNode.next = null;
            }
            tNode = tNode.next;
            temp = temp.next;
        }
        this.size = list.size();
    }


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public boolean contains(Object o) {
        if (o.getClass() != first.element.getClass()) {
            return false;
        }
        Iterator<T> iterator = iterator();
        T t;
        while ((t = iterator.next()) != null) {
            if (Objects.equals(o, t)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = first;
            private Node<T> previous;
            private Node<T> oldPrevious;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    return null;
                }
                oldPrevious = previous;
                previous = current;
                T t = current.element;
                current = current.next;
                return t;
            }

            @Override
            public void remove() {
                if (oldPrevious == null) {
                    // 移除的元素为单链表首元素
                    first = current;
                    previous = null;
                } else {
                    oldPrevious.next = current;
                }
                size--;
            }
        };
    }


    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<T> t = first; t != null; t = t.next) {
            result[i++] = t.element;
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) toArray();
    }


    public boolean add(T element) {
        Node<T> node = new Node<>(element, null);
        if (size() == 0) {
            first = node;
            size++;
            return true;
        }
        for (Node<T> t = first; t != null; t = t.next) {
            if (t.next == null) {
                t.next = node;
                size++;
                return true;
            }
        }
        return false;
    }


    public boolean remove(T element) {
        Iterator<T> iterator = iterator();
        T t;
        while (iterator.hasNext()) {
            t = iterator.next();
            if (Objects.equals(element, t)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }


    public boolean containsAll(Collection<?> c) {
        if (c == null || c.size() == 0) {
            return true;
        }
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }


    public void addAll(Collection<? extends T> c) {
        if (c == null || c.size() == 0) {
            return;
        }
        c.forEach(this::add);
    }


    @SuppressWarnings("unchecked")
    public void removeAll(Collection<?> c) {
        for (Object o : c) {
            if (contains(o)) {
                remove((T) o);
            }
        }
    }


    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T t = it.next();
            if (!c.contains(t)) {
                remove(t);
                modified = true;
            }
        }
        return modified;
    }

    public SingleLinkedList<T> reverse() {
        return reverse(this);
    }

    public static <T> SingleLinkedList<T> reverse(SingleLinkedList<T> list) {
        SingleLinkedList<T> reverse = new SingleLinkedList<>(list);
        if (reverse.size() > 1) {
            Node<T> n1;
            Node<T> newFirst = reverse.first;
            while (reverse.first.next != null) {
                n1 = reverse.first.next;
                reverse.first.next = n1.next;
                n1.next = newFirst;
                newFirst = n1;
            }
            reverse.first = newFirst;
        }
        return reverse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<T> iterator = iterator();
        T t;
        while ((t = iterator.next()) != null) {
            sb.append(t).append(",");
        }
        if (size > 0) {
            sb.replace(sb.length() - 1, sb.length(), "");
        }
        sb.append("]");
        return sb.toString();
    }

    public void clear() {
        size = 0;
        first = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SingleLinkedList)) {
            return false;
        }
        SingleLinkedList<?> list = (SingleLinkedList<?>) obj;
        if (list.size() != size) {
            return false;
        }
        if (list.first.element.getClass() != first.element.getClass()) {
            return false;
        }
        Iterator<?> iterator = ((SingleLinkedList<?>) obj).iterator();
        Iterator<T> iter = iterator();
        if (iter.hasNext() && iterator.hasNext()) {
            if (!Objects.equals(iter.next(), iterator.next())) {
                return false;
            }
        }
        return true;
    }


    protected static class Node<T> {
        T element;
        Node<T> next;

        public Node() {
        }

        public Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }


        public String toString() {
            return element.toString();
        }
    }
}
