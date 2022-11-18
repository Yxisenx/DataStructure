package cn.onecolour.dataStructure.tree.binary_tree;


import java.util.Objects;

class Node<T> {
    private T val;
    private Node<T> left;
    private Node<T> right;

    public Node() {
    }

    public Node(T val, Node<T> left, Node<T> right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public boolean hasLeftChild() {
        return Objects.nonNull(left);
    }

    public boolean hasRightChild() {
        return Objects.nonNull(right);
    }
}
