package cn.onecolour.dataStructure.tree.binary_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
        this.root = new Node<>(root, null, null);
    }

    public void insert(T ele) {
//        root = insertRecursive(root, ele);
        root = insertLoop(root, ele);
        size++;
    }

    private Node<T> insertRecursive(Node<T> root, T ele) {
        if (root == null) {
            root = new Node<>(ele, null, null);
        } else if (root.getVal().compareTo(ele) > 0) {
            root.setLeft(insertRecursive(root.getLeft(), ele));
        } else {
            root.setRight(insertRecursive(root.getRight(), ele));
        }
        return root;
    }

    private Node<T> insertLoop(Node<T> root, T ele) {
        if (root == null) {
            root = new Node<>(ele, null, null);
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
        int compareVal = pre.getVal().compareTo(ele);
        if (compareVal > 0) {
            pre.setLeft(newNode);
        } else if (compareVal < 0) {
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
        System.out.println(inorderList().stream().map(Object::toString).collect(Collectors.joining(",", "", ",")));
    }

    public List<T> inorderList() {
        return BstUtils.inorderList(root, size);
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

    public void postorderTraverseByLoop() {
        LinkedList<Node<T>> stack = new LinkedList<>();
        List<T> result = new ArrayList<>(size);

        Node<T> temp = root;
        Node<T> pre = null;
        while (temp != null || !stack.isEmpty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            }
            temp = stack.pop();
            if (temp.getRight() == null || pre == temp.getRight()) { // 访问节点的条件
                result.add(temp.getVal()); // 访问
                pre = temp; // 这一步是记录上一次访问的节点
                temp = null; // 此处为了跳过下一次循环的访问左子节点的过程，直接进入栈的弹出阶段，因为但凡在栈中的节点，它们的左子节点都肯定被经过且已放入栈中。
            } else { // 不访问节点的条件
                stack.push(temp); // 将已弹出的根节点放回栈中
                temp = temp.getRight(); // 经过右子节点
            }

        }
        System.out.println(result.stream().map(Object::toString).collect(Collectors.joining(",", "", ",")));
    }


    private boolean depthFirstSearch(T ele) {
        return BstUtils.search(ele, root);
    }

    private boolean breadthFirstSearch(T ele) {
        Queue<Node<T>> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        Node<T> temp;
        while ((temp = queue.poll()) != null) {
            if (ele.equals(temp.getVal())) {
                return true;
            } else {
                if (temp.hasLeftChild()) {
                    queue.offer(temp.getLeft());
                }
                if (temp.hasRightChild()) {
                    queue.offer(temp.getRight());
                }
            }
        }
        return false;
    }

    /**
     * search in bst
     * @return if ele exists in bst return 'true', otherwise 'false'
     */
    public boolean search(T ele) {
        return search(ele, SearchType.DFS);
    }

    public boolean search(T ele, SearchType type) {
        switch (type) {
            case DFS:
                return depthFirstSearch(ele);
            case BFS:
                return breadthFirstSearch(ele);
            default:
                throw new IllegalArgumentException("Unknown search type.");
        }
    }


    public enum SearchType {
        BFS, // Breadth-first search
        DFS // Depth first search
    }


    public void delete(T ele) {
        delete(ele, root);
    }
    private Node<T> delete(T ele, Node<T> node) {
        if (node == null) {
            return null;
        }
        int compareVal = ele.compareTo(node.getVal());
        if (compareVal == 0) {
            if (node.hasLeftChild() && node.hasRightChild()) {
                // inorder successor
                Node<T> successor = node.getRight();
                while (successor.getLeft() != null) {
                    successor = successor.getLeft();
                }
                node.setVal(successor.getVal());
                node.setRight(delete( node.getVal(), node.getRight()));
            } else {
                node = node.hasLeftChild() ? node.getLeft() : node.getRight();
                size--;
            }
        } else if (compareVal > 0) {
            node.setRight(delete(ele, node.getRight()));
        } else {
            node.setLeft(delete(ele, node.getLeft()));
        }
        return node;
    }

    private static class Node<T> extends BaseTreeNode<T> {
        public Node() {
        }

        public Node(T val, Node<T> left, BaseTreeNode<T> right) {
            super(val, left, right);
        }


        @Override
        public Node<T> getLeft() {
            return (Node<T>) super.getLeft();
        }


        public void setLeft(Node<T> left) {
            super.setLeft(left);
        }

        @Override
        public Node<T> getRight() {
            return (Node<T>) super.getRight();
        }

        public void setRight(Node<T> right) {
            super.setRight(right);
        }

        @Override
        public boolean hasLeftChild() {
            return super.hasLeftChild();
        }

        @Override
        public boolean hasRightChild() {
            return super.hasRightChild();
        }
    }

}
