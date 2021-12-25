package cn.onecolour.dataStrueture.linear;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author yang
 * @date 2021/12/22
 * @description 双向链表
 */


public class DoubleLinkedList<T> implements Serializable {
    private static final long serialVersionUID = 20211225201144396L;

    private Node<T> first;
    private Node<T> last;
    private int size = 0;


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DoubleLinkedList)) {
            return false;
        }
        DoubleLinkedList<?> list = (DoubleLinkedList<?>) obj;
        if (list.size() != size) {
            return false;
        }
        if (list.first.element.getClass() != first.element.getClass()) {
            return false;
        }
        Iterator<?> iterator = ((DoubleLinkedList<?>) obj).iterator();
        Iterator<T> iter = iterator();
        if (iter.hasNext() && iterator.hasNext()) {
            if (!Objects.equals(iter.next(), iterator.next())) {
                return false;
            }
        }
        return true;
    }


    public DoubleLinkedList(Collection<T> collection) {
        this();
        addAll(collection);
    }

    public DoubleLinkedList(T[] arr) {
        this(Arrays.asList(arr));
    }

    public DoubleLinkedList() {
    }

    public DoubleLinkedList(DoubleLinkedList<T> list) {
        this();
        if (list == null || list.size == 0) {
            return;
        }
        Node<T> temp = list.last;
        while (temp != null) {
            addLast(temp.element);
            temp = temp.previous;
        }

        this.size = list.size();
    }

    public DoubleLinkedList<T> reverse() {
        return reverse(this);
    }

    public static <T> DoubleLinkedList<T> reverse(DoubleLinkedList<T> list) {
        DoubleLinkedList<T> reverse = new DoubleLinkedList<>();
//        if (reverse.size() > 1) {
//            Node<T> p = reverse.first;
//            while (p != null) {
//                Node<T> temp = p.next;
//                Node<T> oldPre = p.previous;
//                Node<T> oldNext = p.next;
//                p.next = oldPre;
//                p.previous = oldNext;
//                p = temp;
//            }
//            p = reverse.first;
//            reverse.first.next = null;
//            reverse.last.previous = null;
//            reverse.first  = reverse.last;
//            reverse.last = p;
//        }
        if (list.size() > 0) {
            Node<T> temp = list.last;
            while (temp != null) {
                reverse.addLast(temp.element);
                temp = temp.previous;
            }
        }
        return reverse;
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

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    return null;
                }
                previous = current;
                T t = current.element;
                current = current.next;
                return t;
            }

            @Override
            public void remove() {
                if (previous == null) {
                    first = null;
                    last = null;
                } else if (previous.previous == null) {
                    if (current == null) {
                        first = null;
                        last = null;
                    } else {
                        first = current;
                    }
                } else {
                    if (current == null) {
                        last = previous.previous;
                    } else {
                        current.previous = previous.previous;
                    }
                    previous.previous.next = current;
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
        return addLast(element);
    }

    public boolean addLast(T element) {
        Node<T> tNode = new Node<>(null, element, null);
        if (size() == 0) {
            first = tNode;
        } else {
            tNode.previous = last;
            last.next = tNode;
        }
        last = tNode;
        size++;
        return true;
    }

    public boolean addFirst(T element) {
        Node<T> tNode = new Node<>(null, element, null);
        if (size() == 0) {
            last = tNode;
        } else {
            tNode.next = first;
            first.previous = tNode;
        }
        first = tNode;
        size++;
        return true;
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
                it.remove();
                modified = true;
            }
        }
        return modified;
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
        last = null;
    }


    protected static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node() {
        }

        public Node(Node<T> previous, T element, Node<T> next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }


        public String toString() {
            return element.toString();
        }
    }
}
