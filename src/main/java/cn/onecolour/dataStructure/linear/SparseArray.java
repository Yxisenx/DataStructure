package cn.onecolour.dataStructure.linear;

import cn.onecolour.dataStructure.expection.EmptyException;
import cn.onecolour.io.Readable;
import cn.onecolour.io.Writeable;

import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * @author yang
 * @date 2021/12/16
 * @description
 * 1. 稀疏数组与数组之间的转换
 * 2. 类打印
 * 3. 稀疏数组写入文件
 * 4. 稀疏数组从文件中读取
 */
public class SparseArray implements Readable<SparseArray>, Writeable<SparseArray> {
    private int[][] arr; // 二维数组
    private int[][] sparseArr; // 稀疏数组

    public static int[][] arrayToSparseArray(int[][] array) {
        checkArray(array);
        int rowSize = array.length;
        int columnSize = array[0].length;
        int sum = 0;
        for (int[] ints : array) {
            for (int anInt : ints) {
                if (anInt != 0) {
                    sum++;
                }
            }
        }
        int[][] sparseArray = new int[sum + 1][3];
        int index = 1;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (array[i][j] != 0) {
                    sparseArray[index][0] = i;
                    sparseArray[index][1] = j;
                    sparseArray[index][2] = array[i][j];
                    index++;
                }
            }
        }
        sparseArray[0][0] = rowSize;
        sparseArray[0][1] = columnSize;
        sparseArray[0][2] = index - 1;
        return sparseArray;
    }

    public void toArray() {
        this.arr = sparseArrayToArray(this.sparseArr);
    }


    public static int[][] sparseArrayToArray(int[][] sparseArray) {
        checkArray(sparseArray);
        int[] first = sparseArray[0];
        if (first.length != 3) {
            throw new IllegalArgumentException("SparseArray second size must be  3.");
        }
        int rowSize = first[0];
        int columnSize = first[1];
        if (rowSize < 0 || columnSize < 0) {
            throw new IllegalArgumentException(String.format("Row size[%s] or column size[%s] is illegal.", rowSize, columnSize));
        }
        int[][] array = new int[rowSize][columnSize];
        for (int i = 1; i < sparseArray.length; i++) {
            int row = sparseArray[i][0];
            int column = sparseArray[i][1];
            int value = sparseArray[i][2];
            if (row < 0 || column < 0) {
                throw new IllegalArgumentException(String.format("Row [%s] or column [%s] is illegal.", row, column));
            }
            if (value == 0) {
                throw new IllegalArgumentException(String.format("Sparse array value cannot be 0.row[%s], column[%s]", row, column));
            }
            if (array[row][column] != 0) {
                throw new IllegalArgumentException(String
                        .format("Sparse array has repeated row and column. row[%s], column[%s], value1[%s], value2[%s]"
                                , row, column, array[row][column], value));
            }
            array[row][column] = value;
        }
        return array;
    }


    public void toSparseArray() {
        this.sparseArr = arrayToSparseArray(this.arr);
    }

    public static void print(int[][] arr, String separator) {
        checkArray(arr);
        for (int[] ints : arr) {
            for (int i : ints) {
                System.out.print(i + separator);
            }
            System.out.print("\n");
        }
    }

    public static void print(int[][] arr) {
        print(arr, "\t");
    }


    public void print(String separator) {
        System.out.println("Array:");
        if (arr != null) {
            print(arr, separator);
        } else {
            System.out.println("null");
        }
        System.out.println("SparseArray:");
        if (sparseArr != null) {
            print(sparseArr, separator);
        } else {
            System.out.println("null");
        }
    }

    public void print() {
        print("\t");
    }


    private static void checkArray(int[][] array) {
        if (array == null) {
            throw new NullPointerException("Array cannot be null.");
        }
        if (array.length == 0) {
            throw new EmptyException("Array cannot be empty.");
        }
    }

    @Override
    public SparseArray read(String fullPath) {
        BufferedReader br = null;
        FileReader fr = null;
        int[][] sparseArray = null;
        try {
            fr = new FileReader(fullPath);
            br = new BufferedReader(fr);
            String str;
            int times = 0;
            int sum;
            while ((str = br.readLine()) != null) {
                String[] line = str.split(",");
                if (line.length == 3) {
                    int row = Integer.parseInt(line[0]);
                    int column = Integer.parseInt(line[1]);
                    int value = Integer.parseInt(line[2]);
                    if (times == 0) {
                        sparseArray = new int[value + 1][3];
                    }
                    sparseArray[times][0] = row;
                    sparseArray[times][1] = column;
                    sparseArray[times][2] = value;
                    times++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SparseArray sa = new SparseArray();
        sa.sparseArr = sparseArray;
        sa.toArray();
        return sa;
    }

    @Override
    public boolean write(SparseArray sparseArray, String fullPath) {
        checkArray(sparseArr);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fullPath, false);
            bos = new BufferedOutputStream(fos);
            StringBuilder sb = new StringBuilder();
            for (int[] ints : sparseArr) {
                sb.append(ints[0]).append(",").append(ints[1]).append(",").append(ints[2]).append("\n");
            }
            bos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final SparseArray sparseArr;

        public Builder() {
            sparseArr = new SparseArray();
        }

        public Builder arr(int[][] arr) {
            sparseArr.arr = arr;
            return this;
        }

        public Builder sparseArr(int[][] sparseArray) {
            sparseArr.sparseArr = sparseArray;
            return this;
        }

        public SparseArray build() {
            return sparseArr;
        }

    }
}
