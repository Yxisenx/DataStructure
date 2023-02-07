package cn.onecolour.dataStructure.tree.binary_tree;

import cn.onecolour.dataStructure.expection.AlreadyExistsException;

import java.util.Iterator;
import java.util.Objects;

/**
 * <p><h3>AVL Tree</h3></p>
 *
 * <p>Balance Factor (k) = height (left(k)) - height (right(k))</p>
 * <p>If balance factor of any node is 1, it means that the left sub-tree is one level higher than the right sub-tree.</p>
 * <p>If balance factor of any node is 0, it means that the left sub-tree and right sub-tree contain equal height.</p>
 * <p>If balance factor of any node is -1, it means that the left sub-tree is one level lower than the right sub-tree.</p>
 * <p></p>
 * <a href="https://en.wikipedia.org/wiki/AVL_tree#firstHeading">avl tree</a>
 *
 * @author yang
 * @date 2022/11/25
 * @description AVL Tree is invented by GM Adelson - Velsky and EM Landis in 1962. The tree is named AVL in honour of its inventors.
 *
 */
public class BalancedBinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    public BalancedBinarySearchTree() {
        size = 0;
    }

    public BalancedBinarySearchTree(Iterator<T> iterator) {
        size = 0;
        while (iterator.hasNext()) {
            T t = iterator.next();
            add(t);
        }
    }

    public BalancedBinarySearchTree(Iterable<T> iterable) {
        this(iterable.iterator());
    }

    public int size() {
        return size;
    }


    /**
     * add an element into the avl tree if it not exists in the tree, then check balance of the tree, if it is balance, do nothing, otherwise rebalance it
     *
     * @return the result of add element into avl tree
     */
    public boolean add(T ele) {
        if (ele == null) {
            throw new NullPointerException();
        }
        try {
            root = add(root, ele);
            size++;
            return true;
        } catch (AlreadyExistsException e) {
            return false;
        }
    }

    private Node<T> add(Node<T> node, T ele) {
        if (node == null) {
            node = new Node<>(ele);
            return node;
        } else {
            int compareRes = ele.compareTo(node.getVal());
            if (compareRes == 0) {
                throw new AlreadyExistsException();
            } else if (compareRes > 0) {
                node.right = add(node.getRight(), ele);
            } else {
                node.left = add(node.left, ele);
            }
            updateHeight(node);
            return rebalance(node);
        }

    }


    /**
     * remove an element from the avl tree if it exists in the tree
     *
     * @param ele the element will be removed from the tree
     */
    public void delete(T ele) {
        root = deleteNode(root, ele);
    }

    private Node<T> deleteNode(Node<T> node, T ele) {
        if (node == null) {
            return null;
        }
        int compareRes = ele.compareTo(node.getVal());
        if (compareRes == 0) {
            if (node.hasLeftChild() && node.hasRightChild()) {
                // inorder successor
                Node<T> successor = node.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                node.val = successor.val;
                node.right = deleteNode(node.right, node.val);
            } else {
                // node may be null
                node = node.hasLeftChild() ? node.left : node.right;
                size --;
            }
        } else if (compareRes > 0) {
            node.right = deleteNode(node.right, ele);
        } else {
            node.left = deleteNode(node.left, ele);
        }
        if (node != null) {
            updateHeight(node);
            return rebalance(node);
        }
        return null;
    }


    /**
     * calculate balance factor(height of right child tree subtract height of left child tree).
     * If the absolute value of result greater than 1 means the tree is not balanced, otherwise is balanced.
     *
     * @param node root node or son node
     * @return balance factor of the node
     */
    private int balanceFactor(Node<T> node) {
        return height(node.right) - height(node.left);
    }

    private int height(Node<T> node) {
        return node == null ? 0 : node.height;
    }


    private void updateHeight(Node<T> node) {
        node.height = Math.max(node.hasLeftChild() ? node.left.height : 0, node.hasRightChild() ? node.right.height : 0) + 1;
    }


    private Node<T> rebalance(Node<T> node) {
        int bf = balanceFactor(node);
        if (bf < -1) {
            //noinspection IfStatementWithIdenticalBranches
            if (balanceFactor(node.left) <= 0) {
                //        3                2
                //       /               /   \
                //     `2      -->      1     3
                //     /    right rotation
                //    1
                node = rotateRight(node);
            } else {
                //      3                        3                 2
                //    /                         /                /   \
                //  1           -->           `2       -->      1     3
                //    \                       /
                //      2                    1
                //         left rotation             right rotation

                // Rotate left-right
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }

        } else if (bf > 1) {
            //noinspection IfStatementWithIdenticalBranches
            if (balanceFactor(node.right) >= 0) {
                // Rotate left
                node = rotateLeft(node);
            } else {
                // Rotate right-left
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }

        }

        return node;
    }


    private Node<T> rotateRight(Node<T> node) {
        Node<T> leftChild = node.left;

        node.left = leftChild.right;
        leftChild.right = node;

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> rightChild = node.right;

        node.right = rightChild.left;
        rightChild.left = node;

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }


    /**
     * avl tree node
     */
    private static class Node<T extends Comparable<T>> {
        private T val;
        private Node<T> left;
        private Node<T> right;
        private int height;

        public Node() {

        }

        public Node(T val) {
            this.val = val;
            this.height = 1;
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

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }


        public boolean hasLeftChild() {
            return Objects.nonNull(left);
        }

        public boolean hasRightChild() {
            return Objects.nonNull(right);
        }

    }
}
