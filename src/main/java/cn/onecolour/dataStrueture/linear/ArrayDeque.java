package cn.onecolour.dataStrueture.linear;


import cn.onecolour.dataStrueture.expection.EmptyException;
import cn.onecolour.dataStrueture.expection.FullException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author yang
 * @date 2021/12/18
 * @description 数组模拟双向队列
 * 1. head 指向首元素的前一个位置
 * 2. tail 指向尾元素的后一个位置
 */
public class ArrayDeque<T> implements Serializable {
    private static final long serialVersionUID = 20211218202931654L;

    private final T[] arr;
    private int head;
    private int tail;
    private final int maxSize;
    private int num;

    @SuppressWarnings({"unchecked"})
    public ArrayDeque(int maxSize, T... elements) {
        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException("Elements  should greater than 0.");
        }
        int length = elements.length;
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Deque max size should greater than 0.");
        }
        if (length > maxSize) {
            throw new IllegalArgumentException(String.format("Deque max size [%s] should greater than or equal elements number [%s].", maxSize, length));
        }
        this.maxSize = maxSize;
        Class<?> clazz = elements[0].getClass();
        arr = (T[]) new Object[maxSize];
        for (int i = 0; i < elements.length; i++) {
            arr[i] = (T) clazz.cast(elements[i]);
        }
        head = -1;
        tail = length;
        num = length;
    }

    public void add(T element) {
        addTail(element);
    }

    @SafeVarargs
    public final void add(T... elements) {
        if (elements != null && elements.length + num <= maxSize) {
            for (T element : elements) {
                add(element);
            }
        }
    }

    public void addHead(T element) {
        if (num == maxSize) {
            print();
            throw new FullException("Deque is full. Cannot add more elements.");
        }
        if (head == -1) {
            arr[maxSize - 1] = element;
            head = maxSize - 2;
        } else {
            arr[head] = element;
            head--;
        }
        num++;
    }

    /**
     * 向队尾加入元素
     *
     * @param element 待加入元素
     */
    public void addTail(T element) {
        if (num == maxSize) {
            print();
            throw new FullException("Deque is full. Cannot add more elements.");
        }

        if (tail == maxSize) {
            arr[0] = element;
            tail = 1;
        } else {
            arr[tail] = element;
            tail++;
        }
        num++;
    }

    public T poll() {
        return pollHead();
    }

    public T pollHead() {
        if (num == 0) {
            throw new EmptyException("Deque was already empty.");
        }
        T ele = arr[head + 1];
        arr[head + 1] = null;
        if (head == maxSize - 2) {
            head = -1;
        } else {
            head++;
        }
        num--;
        return ele;
    }

    public T pollTail() {
        if (num == 0)
            throw new EmptyException("Deque was already empty.");
        T ele = arr[tail - 1];
        arr[tail - 1] = null;
        if (tail == 1) {
            tail = maxSize;
        } else {
            tail--;
        }
        num--;
        return ele;
    }

    public void print() {
        System.out.println(this);
    }

    public static <T> Collection<T> toCollection(ArrayDeque<T> deque) {
        return Arrays.asList(toArray(deque));
    }

    public Collection<T> toCollection(){
        return toCollection(this);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(ArrayDeque<T> deque) {
        Object[] objects = new Object[deque.num];
        int n = 0;
        if (deque.num != 0) {
            if (deque.tail - 2 >= deque.head) {
                // 从 head + 1 一直到 tail - 1
                for (int i = deque.head + 1; i < deque.tail; i++, n++) {
                    objects[n] = deque.arr[i];
                }
            } else {
                // 从 head + 1 一直到 maxSize, 再从 0 到tail - 1
                for (int i = deque.head + 1; i < deque.maxSize; i++, n++) {
                    objects[n] = deque.arr[i];
                }
                for (int i = 0; i < deque.tail; i++, n++) {
                    objects[n] = deque.arr[i];
                }
            }
        }
        return (T[]) objects;
    }

    public T[] toArray() {
        return toArray(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("@").append(Integer.toHexString(hashCode())).append("[");
        if (num != 0) {
            if (tail - 2 >= head) {
                // 从 head + 1 一直打印到 tail - 1
                for (int i = head + 1; i < tail - 1; i++) {
                    sb.append(arr[i]).append(", ");
                }
            } else {
                // 从 head + 1 一直打印到 maxSize, 再从 0 打印到tail - 1
                for (int i = head + 1; i < maxSize; i++) {
                    sb.append(arr[i]).append(", ");
                }
                for (int i = 0; i < tail - 1; i++) {
                    sb.append(arr[i]).append(", ");
                }
            }
            sb.append(arr[tail - 1]);
        }
        sb.append("]");
        return sb.toString();
    }
}
