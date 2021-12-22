package com.onecolour.linear;

import cn.onecolour.dataStrueture.linear.ArrayDeque;
import org.junit.Before;
import org.junit.Test;

/**
 * @author yang
 * @date 2021/12/18
 * @description
 */
public class ArrayDequeTest {
    ArrayDeque<Integer> deque;

    @Before
    public void before() {
        System.out.println("Before:");
        deque = new ArrayDeque<>(10, 1, 2, 3, 4);
        deque.print();
    }

    @Test
    public void testAdd() {
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                deque.addHead(i + 5);
            } else {
                deque.addTail(i + 5);
            }
            System.out.println(deque.toCollection());
        }
    }

    @Test
    public void testPoll() {
        deque.add(5, 6, 7, 8, 9, 10);
        for (int i = 0; i < 100; i++) {
            System.out.println("-------------------------");
            int n = 11 + i;
            int polled;
            if (i % 4 == 0) {
                polled = deque.pollHead();
                deque.addHead(n);
                System.out.println(1);
            } else if (i % 4 == 1) {
                polled = deque.pollTail();
                deque.addHead(n);
                System.out.println(2);
            } else if (i % 4 == 2) {
                polled = deque.pollHead();
                deque.addTail(n);
                System.out.println(3);
            } else {
                polled = deque.pollTail();
                deque.addTail(n);
                System.out.println(4);
            }
            System.out.printf("added: %d, polled: %d\n", n, polled);
            System.out.println(deque);
        }
    }
}
