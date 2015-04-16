package com.gc22;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Garry Craig on 09/04/2015.
 */
public class RedBlackTree<K extends Comparable<K>, V> implements Iterable<RedBlackTreeNode<K, V>> {
    LinkedList<RedBlackTreeNode<K, V>> nodes = new LinkedList<RedBlackTreeNode<K, V>>();
    RedBlackTreeNode<K, V> root = null;

    public RedBlackTree(K key, V value) {
        this.root = new RedBlackTreeNode<K, V>(key, value, RedBlackTreeNode.Color.Black);
    }

    public RedBlackTree(K key, V value, RedBlackTreeNode.Color color) {
        this.root = new RedBlackTreeNode<K, V>(key, value, color);
    }

    public void addNode(RedBlackTreeNode<K, V> newNode) {
        nodes.add(newNode);
        System.out.println("Add " + newNode);
        newNode.setColor(RedBlackTreeNode.Color.Red);
        if (this.root == null) {
            System.out.println("New node is now the root.");
            this.root = newNode;
        } else {
            RedBlackTreeNode<K, V> currentNode = root;
            while (true) {
                int comp_result = newNode.compareTo(currentNode);
                if (comp_result == 0) {
                    currentNode.setValue(newNode.getValue());
                    return;
                } else if (comp_result < 0) {
                    RedBlackTreeNode<K, V> currentNodeLeft = currentNode.getLeft();
                    if (currentNodeLeft == null) {
                        currentNode.setLeft(newNode);
                        break;
                    } else {
                        currentNode = currentNodeLeft;
                    }
                } else {
                    assert (comp_result > 0);
                    RedBlackTreeNode<K, V> currentNodeRight = currentNode.getRight();
                    if (currentNodeRight == null) {
                        currentNode.setRight(newNode);
                        break;
                    } else {
                        currentNode = currentNodeRight;
                    }
                }
            }
            newNode.setParent(currentNode);
        }
        fixTreeAfterInsert(newNode);
    }

    private void fixTreeAfterInsert(RedBlackTreeNode<K, V> node) {
        while (node != null) {
            if (node.getParent() == null) {
                node.setColor(RedBlackTreeNode.Color.Black);
                node = null;
            } else {
                RedBlackTreeNode<K, V> parent = node.getParent();
                boolean isParentBlack = parent.getColor() == RedBlackTreeNode.Color.Black;
                System.out.println("Is parent black? " + isParentBlack);
                if (isParentBlack) {
                    node = null;
                } else {
                    RedBlackTreeNode<K, V> grandparent = node.getGrandparent();
                    RedBlackTreeNode<K, V> uncle = node.getUncle();
                    if (uncle != null && uncle.getColor() == RedBlackTreeNode.Color.Red) {
                        grandparent.setColor(RedBlackTreeNode.Color.Red);
                        uncle.setColor(RedBlackTreeNode.Color.Black);
                        parent.setColor(RedBlackTreeNode.Color.Black);
                        System.out.println("Setting node to grandparent " + grandparent);
                        node = grandparent;
                    } else {
                        if (node == parent.getRight() && parent == grandparent.getLeft()) {
                            rotateLeft(parent);
                            node = node.getLeft();
                        } else if (node == parent.getLeft() && parent == grandparent.getRight()) {
                            rotateRight(parent);
                            node = node.getRight();
                        }
                        parent = node.getParent();
                        grandparent = node.getGrandparent();
                        parent.setColor(RedBlackTreeNode.Color.Black);
                        grandparent.setColor(RedBlackTreeNode.Color.Red);
                        if (node == parent.getLeft() && parent == grandparent.getLeft()) {
                            rotateRight(grandparent);
                        } else {
                            assert (node == parent.getRight() && parent == grandparent.getRight());
                            rotateLeft(grandparent);
                        }
                        node = null;
                    }
                }
            }
        }
        //System.out.println("fix node looping");
    }

    void rotateLeft(RedBlackTreeNode<K, V> node) {
        System.out.println("rotateLeft " + node);
        RedBlackTreeNode<K, V> right = node.getRight();
        swapNode(node, right);
        RedBlackTreeNode<K, V> rightLeft = right.getLeft();
        node.setRight(rightLeft);
        if (rightLeft != null) {
            rightLeft.setParent(node);
        }
        right.setLeft(node);
        node.setParent(right);
    }

    void rotateRight(RedBlackTreeNode<K, V> node) {
        System.out.println("rotateRight " + node);
        RedBlackTreeNode<K, V> left = node.getLeft();
        swapNode(node, left);
        RedBlackTreeNode<K, V> leftRight = left.getRight();
        node.setLeft(leftRight);
        if (leftRight != null) {
            leftRight.setParent(node);
        }
        left.setRight(node);
        node.setParent(left);
    }

    void swapNode(RedBlackTreeNode<K, V> nodeOld, RedBlackTreeNode<K, V> nodeNew) {
        RedBlackTreeNode<K, V> nodeOldParent = nodeOld.getParent();
        if (nodeOldParent == null) {
            this.root = nodeNew;
        } else {
            if (nodeOld == nodeOldParent.getLeft()) {
                nodeOldParent.setLeft(nodeNew);
            } else {
                nodeOldParent.setRight(nodeNew);
            }
        }
        if (nodeNew != null) {
            nodeNew.setParent(nodeOldParent);
        }
    }

    public RedBlackTree() {
        this.root = null;
    }

    public RedBlackTree(RedBlackTreeNode root) {
        this.root = root;
    }

    public RedBlackTreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public Iterator<RedBlackTreeNode<K, V>> iterator() {
        return nodes.iterator();
    }

    @Override
    public void forEach(final Consumer action) {
        this.nodes.forEach(action);
    }

    @Override
    public Spliterator spliterator() {
        return nodes.spliterator();
    }
}
