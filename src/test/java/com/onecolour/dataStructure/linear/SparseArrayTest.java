package com.onecolour.dataStructure.linear;

import cn.onecolour.dataStrueture.linear.SparseArray;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * @author yang
 * @date 2021/12/16
 * @description
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SparseArrayTest {
    private final int[][] SPARSE_ARRAY = {
            {2, 7, 3},
            {0, 1, 1},
            {1, 1, 1},
            {1, 2, 1}
    };
    private final int[][] ARRAY = {
            {0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0},
    };
    @Rule
    public TestName name = new TestName();

    @Before
    public void before() {
        System.out.println("-------------" + name.getMethodName() + "-------------");
    }


    String FULL_PATH = System.getProperty("user.dir") + "/src/test/resources/temp.txt";

    @Test()
    public void A1_TestArrToSparseArray() throws IOException {
        SparseArray sparseArray = SparseArray.builder().arr(ARRAY).build();
        sparseArray.toSparseArray();
        sparseArray.print();
    }

    @Test
    public void A2_TestSparseArrToArray() throws IOException {
        SparseArray sparseArray = SparseArray.builder().sparseArr(SPARSE_ARRAY).build();
        sparseArray.toArray();
        sparseArray.print();
    }

    @Test
    public void A3_TestWriteSparseArray() {
        SparseArray sparseArray = SparseArray.builder().arr(ARRAY).build();
        sparseArray.toSparseArray();
        sparseArray.print();
        System.out.println(sparseArray.write(sparseArray, FULL_PATH));
    }

    @Test
    public void A4_TestReadSparseArray() {
        SparseArray sparseArray = SparseArray.builder().build();
        sparseArray = sparseArray.read(FULL_PATH);
        sparseArray.print();
    }
}
