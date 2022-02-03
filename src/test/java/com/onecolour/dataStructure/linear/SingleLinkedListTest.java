package com.onecolour.dataStructure.linear;

import cn.onecolour.dataStrueture.linear.SingleLinkedList;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author yang
 * @date 2021/12/22
 * @description
 */
public class SingleLinkedListTest {
    @Test
    public void testGenerate() {
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        for (int i = 0; i < 19; i++) {
            list.add(i);
        }
        System.out.println(list);
    }

    @Test
    public void testRemove() {
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
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
        SingleLinkedList<Integer> list = new SingleLinkedList<>(new Integer[]{1, 2, 5, 6, 7});
        list.retainAll(collection);
        System.out.println(list);
    }

    @Test
    public void testRemoveAll() {
        Collection<Integer> collection = Arrays.asList(1, 3, 4, 5);
        SingleLinkedList<Integer> list = new SingleLinkedList<>(new Integer[]{1, 2, 5, 6, 7});
        list.removeAll(collection);
        System.out.println(list);
    }

    @Test
    public void testReverse() {
        SingleLinkedList<Integer> list = new SingleLinkedList<>(new Integer[]{1, 2, 5, 6, 7});
        System.out.println(list);
        SingleLinkedList<Integer> reverse = list.reverse();
        System.out.println(reverse);
    }
}
