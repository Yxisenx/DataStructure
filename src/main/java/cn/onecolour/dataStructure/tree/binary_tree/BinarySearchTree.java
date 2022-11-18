package cn.onecolour.dataStructure.tree.binary_tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yang
 * @date 2022/11/15
 * @description BST
 */
public class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    public BinarySearchTree() {

    }

    public BinarySearchTree(T root) {
        this.root = new Node<T>(root, null, null);
    }

    public void insert(T ele) {
//        root = insertRecursive(root, ele);
        root = insertLoop(root, ele);
        size++;
    }

    private Node<T> insertRecursive(Node<T> root, T ele) {
        if (root == null) {
            root = new Node<T>(ele, null, null);
        } else if (root.getVal().compareTo(ele) > 0) {
            root.setLeft(insertRecursive(root.getLeft(), ele));
        } else {
            root.setRight(insertRecursive(root.getRight(), ele));
        }
        return root;
    }

    private Node<T> insertLoop(Node<T> root, T ele) {
        if (root == null) {
            root = new Node<T>(ele, null, null);
            return root;
        }

        Node<T> pre = null;
        Node<T> temp = root;

        while (temp != null) {
            pre = temp;
            if (temp.getVal().compareTo(ele) > 0) {
                temp = temp.getLeft();
            } else {
                temp = temp.getRight();
            }
        }
        Node<T> newNode = new Node<>(ele, null, null);
        if (pre.getVal().compareTo(ele) > 0) {
            pre.setLeft(newNode);
        } else {
            pre.setRight(newNode);
        }
        return root;
    }



    public void preorderTraverseByRecursive() {
        preorderTraverseByRecursive(root);
        System.out.println();
    }
    private void preorderTraverseByRecursive(Node<T> node) {
        System.out.print(node.getVal() + ",");
        if (node.hasLeftChild()) {
            preorderTraverseByRecursive(node.getLeft());
        }
        if (node.hasRightChild()) {
            preorderTraverseByRecursive(node.getRight());
        }
    }

    public void preorderTraverseByLoop() {
        LinkedList<Node<T>> stack = new LinkedList<>();
        stack.push(root);

        Node<T> temp;

        while ((temp = stack.poll()) != null) {
            System.out.print(temp.getVal() + ",");
            if (temp.hasRightChild()) {
                stack.push(temp.getRight());
            }
            if (temp.hasLeftChild()) {
                stack.push(temp.getLeft());
            }
        }
        System.out.println();
    }

    public void inorderTraverseByRecursive() {
        inorderTraverseByRecursive(root);
        System.out.println();
    }

    private void inorderTraverseByRecursive(Node<T> node) {
        if (node.hasLeftChild()) {
            inorderTraverseByRecursive(node.getLeft());
        }
        System.out.print(node.getVal() + ",");
        if (node.hasRightChild()) {
            inorderTraverseByRecursive(node.getRight());
        }
    }

    public void inorderTraverseByLoop() {
        LinkedList<Node<T>> stack = new LinkedList<>();
        List<T> result = new ArrayList<>(size);

        Node<T> temp = root;
        while (temp != null || !stack.isEmpty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            }
            temp = stack.pop();
            result.add(temp.getVal());
            temp = temp.getRight();
        }
        System.out.println(result.stream().map(Object::toString).collect(Collectors.joining(",", "", ",")));
    }


    public void postorderTraverseByRecursive() {
        postorderTraverseByRecursive(root);
        System.out.println();
    }

    private void postorderTraverseByRecursive(Node<T> node) {
        if (node.hasLeftChild()) {
            postorderTraverseByRecursive(node.getLeft());
        }
        if (node.hasRightChild()) {
            postorderTraverseByRecursive(node.getRight());
        }
        System.out.print(node.getVal() + ",");
    }

}
