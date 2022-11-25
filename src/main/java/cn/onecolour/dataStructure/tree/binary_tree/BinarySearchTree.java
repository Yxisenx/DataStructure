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
@SuppressWarnings("UnusedReturnValue")
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
        return result;
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
        Node<T> temp = root;

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


    /**
     * delete an element from the bst.
     * <p>the node to be deleted is a leaf.</p>
     * <p>&nbsp&nbsp&nbsp&nbsp instead the node of null</p>
     * <p>the node has just one child.</p>
     * <p>&nbsp&nbsp&nbsp&nbsp replace the node as the child of the node </p>
     * <p>the node has two child.</p>
     * <p>&nbsp&nbsp&nbsp&nbsp First, find the inorder successor of the node to be deleted.</p>
     * <p>&nbsp&nbsp&nbsp&nbsp After that, replace that node with the inorder successor until the target node is placed at the leaf of tree.</p>
     * <p>&nbsp&nbsp&nbsp&nbsp And at last, replace the node with NULL and free up the allocated space.</p>
     * <p></p>
     * <p>delete by moving node</p>
     */
    private boolean deleteV1(T ele) {
        Node<T> parent = null;
        Node<T> cur = root;
        while (cur != null) {
            int compareVal = ele.compareTo(cur.getVal());
            // equals
            if (compareVal == 0) {
                if (parent == null) {
                    // remove root (cur -> root)
                    if (cur.hasRightChild() && cur.hasLeftChild()) {
                        // two child
                        // successor is the minimum node of the right subtree
                        Node<T> successorP = null;
                        Node<T> successor = cur.getRight();
                        while (successor.hasLeftChild()) {
                            successorP = successor;
                            successor = successor.getLeft();
                        }
                        if (successorP == null) {
                            // successor is cur right child
                            successor.setLeft(root.getLeft());
                            clearNode(root);
                            root = successor;
                        } else {
                            if (successor.hasRightChild()) {
                                // is not a leaf
                                successorP.setLeft(successor.getRight());
                                successor.setLeft(root.getLeft());
                                successor.setRight(root.getRight());
                                clearNode(root);
                                root = successor.getRight();
                            } else {
                                // is a leaf
                                successorP.setLeft(null);
                                successor.setLeft(root.getLeft());
                                successor.setRight(root.getRight());
                                clearNode(root);
                                root = successor;
                            }
                        }
                    } else if (cur.hasLeftChild() || cur.hasRightChild()) {
                        // one child
                        Node<T> child = cur.hasLeftChild() ? cur.getLeft() : cur.getRight();
                        clearNode(root);
                        root = child;
                    } else {
                        // no child
                        clearNode(root);
                        root = null;
                    }
                } else {
                    if (cur.hasRightChild() && cur.hasLeftChild()) {
                        // two child
                        boolean curIsLeftChild = cur.equals(parent.getLeft());
                        // successor is the minimum node of the right subtree
                        Node<T> successorP = null;
                        Node<T> successor = cur.getRight();
                        // find minimum
                        while (successor.hasLeftChild()) {
                            successorP = successor;
                            successor = successor.getLeft();
                        }

                        if (successorP == null) {
                            // successor is right of cur.
                            if (curIsLeftChild) {
                                parent.setLeft(successor);
                            } else {
                                parent.setRight(successor);
                            }
                            successor.setLeft(cur.getLeft());
                        } else {
                            successorP.setLeft(successor.hasRightChild() ? successor.getRight() : null);
                            if (curIsLeftChild) {
                                parent.setLeft(successor);
                            } else {
                                parent.setRight(successor);
                            }
                            successor.setLeft(cur.getLeft());
                            successor.setRight(cur.getRight());
                        }
                        clearNode(cur);


                    } else if (cur.hasLeftChild() || cur.hasRightChild()) {
                        // one child
                        if (cur.equals(parent.getLeft())) {
                            parent.setLeft(cur.hasLeftChild() ? cur.getLeft() : cur.getRight());
                        } else {
                            parent.setRight(cur.hasLeftChild() ? cur.getLeft() : cur.getRight());
                        }
                        clearNode(cur);
                    } else {
                        // no child
                        if (cur.equals(parent.getLeft())) {
                            parent.setLeft(null);
                        } else {
                            parent.setRight(null);
                        }
                        cur.setVal(null);
                    }
                }
                return true;
            } else {
                parent = cur;
                cur = compareVal > 0 ? cur.getRight() : cur.getLeft();
            }
        }
        return false;
    }

    public boolean delete(T ele) {
        return deleteV2(ele);
    }

    public boolean delete(T ele, DeleteType type) {
        boolean result;
        switch (type) {
            case V1:
                result = deleteV1(ele);
                break;
            case V2:
                result = deleteV2(ele);
                break;
            default:
                throw new IllegalArgumentException("Not found the type.");
        }
        if (result) {
            size--;
        }
        return result;
    }

    private boolean deleteV2(T ele) {
        Node<T> parent = null;
        Node<T> cur = root;
        while (cur != null) {
            int compareVal = ele.compareTo(cur.getVal());
            // equals
            if (compareVal == 0) {
                if (cur.hasLeftChild() || cur.hasRightChild()) {
                    if (cur.hasLeftChild() && cur.hasRightChild()) {
                        // has two child
                        // find successor( the minimum node of the subtree whose root node is the right child of cur), inorder
                        Node<T> successor = cur.getRight();
                        Node<T> successorParent = cur;
                        // minimum
                        while (successor.hasLeftChild()) {
                            successorParent = successor;
                            successor = successor.getLeft();
                        }
                        // change value of cur with successor
                        T temp = cur.getVal();
                        cur.setVal(successor.getVal());
                        successor.setVal(temp);

                        boolean successorIsLeftChild = successor.equals(successorParent.getLeft());
                        if (successorIsLeftChild) {
                            successorParent.setLeft(successor.getRight());
                        } else {
                            successorParent.setRight(successor.getRight());
                        }
                        clearNode(successor);
                    } else {
                        // has one child, replace cur with the child
                        Node<T> child = cur.hasLeftChild() ? cur.getLeft() : cur.getRight();
                        if (parent != null) {
                            if (cur.equals(parent.getLeft())) {
                                parent.setLeft(child);
                            } else {
                                parent.setRight(child);
                            }
                            clearNode(cur);
                        } else {
                            // cur is the root node
                            clearNode(root);
                            root = child;
                        }
                    }
                } else {
                    // is a leaf
                    if (parent != null) {
                        // cur is different with root
                        if (cur.equals(parent.getLeft())) {
                            // cur is left child of parent
                            parent.setLeft(null);
                        } else {
                            parent.setRight(null);
                        }
                    } else {
                        root = null;
                    }
                    clearNode(cur);
                }
                return true;
            } else {
                parent = cur;
                cur = compareVal > 0 ? cur.getRight() : cur.getLeft();
            }
        }
        return false;
    }

    public enum DeleteType {
        V1, V2
    }

    private void clearNode(Node<T> node) {
        node.setLeft(null);
        node.setRight(null);
        node.setVal(null);
    }

}
