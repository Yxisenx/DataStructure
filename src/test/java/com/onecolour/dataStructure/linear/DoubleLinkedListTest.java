package com.onecolour.dataStructure.linear;

import cn.onecolour.dataStrueture.linear.DoubleLinkedList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author yang
 * @date 2021/12/25
 * @description
 */
public class DoubleLinkedListTest {
    @Test
    public void testGenerate() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 19; i++) {
            list.add(i);
        }
        System.out.println(list);
    }

    @Test
    public void testRemove() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 19; i++) {
            list.add(i);
            if (i % 2 == 1) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }

    @Test
    public void testRetainAll() {
        Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5);
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>(new Integer[]{1, 2, 5, 6, 7});
        list.retainAll(collection);
        System.out.println(list);
    }

    @Test
    public void testRemoveAll() {
        Collection<Integer> collection = Arrays.asList(1, 3, 4, 5);
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>(new Integer[]{1, 2, 5, 6, 7});
        list.removeAll(collection);
        System.out.println(list);
    }

    @Test
    public void testReverse() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>(new Integer[]{1, 2, 5, 6, 7});
        System.out.println(list);
        DoubleLinkedList<Integer> reverse = list.reverse();
        System.out.println(reverse);
        DoubleLinkedList<Integer> reverse1 = reverse.reverse();
        System.out.println(reverse1);
        System.out.println(list == reverse1);
        System.out.println(list.equals(reverse1));
    }
}
