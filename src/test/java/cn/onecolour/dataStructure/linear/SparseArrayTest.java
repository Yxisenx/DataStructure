package cn.onecolour.dataStructure.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * @author yang
 * @date 2021/12/16
 * @description
 */
//@TestMethodOrder(MethodOrderer.MethodName.class)
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

    @BeforeEach
    public void before(TestInfo testInfo) {
        System.out.println("-------------" + testInfo.getDisplayName() + "-------------");
    }


    String FULL_PATH = System.getProperty("user.dir") + "/src/test/resources/temp.txt";

    @Test
    @Order(1)
    public void testArrToSparseArray() {
        SparseArray sparseArray = SparseArray.builder().arr(ARRAY).build();
        sparseArray.toSparseArray();
        sparseArray.print();
    }

    @Test
    @Order(2)
    public void testSparseArrToArray() {
        SparseArray sparseArray = SparseArray.builder().sparseArr(SPARSE_ARRAY).build();
        sparseArray.toArray();
        sparseArray.print();
    }

    @Test
    @Order(3)
    public void testWriteSparseArray() {
        SparseArray sparseArray = SparseArray.builder().arr(ARRAY).build();
        sparseArray.toSparseArray();
        sparseArray.print();
        System.out.println(sparseArray.write(sparseArray, FULL_PATH));
    }

    @Test
    @Order(4)
    public void testReadSparseArray() {
        SparseArray sparseArray = SparseArray.builder().build();
        sparseArray = sparseArray.read(FULL_PATH);
        sparseArray.print();
    }
}
