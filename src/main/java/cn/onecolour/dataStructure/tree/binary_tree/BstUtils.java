package cn.onecolour.dataStructure.tree.binary_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yang
 * @date 2022/11/29
 * @description
 */
public class BstUtils {

    public static <T extends Comparable<T>> boolean search(T ele, BaseTreeNode<T> root) {
        BaseTreeNode<T> temp = root;
        while (temp != null) {
            int compareTo = temp.getVal().compareTo(ele);
            if (compareTo > 0) {
                temp = temp.getLeft();
            } else if (compareTo < 0) {
                temp = temp.getRight();
            } else {
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> inorderList(BaseTreeNode<T> root, int size) {
        LinkedList<BaseTreeNode<T>> stack = new LinkedList<>();
        List<T> result = new ArrayList<>(size);

        BaseTreeNode<T> temp = root;
        while (temp != null || !stack.isEmpty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            }
            temp = stack.pop();
            result.add(temp.getVal());
            temp = temp.getRight();
        }
        return result;
    }
}
