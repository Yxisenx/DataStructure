package cn.onecolour.dataStructure.tree.binary_tree;


import java.util.Objects;

class BaseTreeNode<T> {
    private T val;
    private BaseTreeNode<T> left;
    private BaseTreeNode<T> right;

    public BaseTreeNode() {
    }

    public BaseTreeNode(T val, BaseTreeNode<T> left, BaseTreeNode<T> right) {
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

    public BaseTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BaseTreeNode<T> left) {
        this.left = left;
    }

    public BaseTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BaseTreeNode<T> right) {
        this.right = right;
    }

    public boolean hasLeftChild() {
        return Objects.nonNull(left);
    }

    public boolean hasRightChild() {
        return Objects.nonNull(right);
    }
}
