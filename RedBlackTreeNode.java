package com.gc22;

/**
 * Created by Garry Craig on 09/04/2015.
 */
public class RedBlackTreeNode<K extends Comparable<K>,V> implements Comparable<RedBlackTreeNode<K,V>> {
    @Override
    public int compareTo(RedBlackTreeNode<K, V> o){
        return this.key.compareTo(o.key);
    }

    public enum Color {
        Red,
        Black
    }

    private RedBlackTreeNode<K, V> parent = null, left = null, right = null;
    private K key;
    private V value;
    private Color color;

    public RedBlackTreeNode(K key, V value, Color color) {
        this.key = key;
        this.value = value;
        this.color = color;
    }

    public RedBlackTreeNode<K,V> getGrandparent(){
        RedBlackTreeNode<K,V> grandparent = null;
        if(this.parent != null){
            grandparent = this.parent.getParent();
        }
        return grandparent;
    }

    public RedBlackTreeNode<K, V> getUncle(){
        RedBlackTreeNode<K,V> uncle = null;
        if(this.parent != null){
            uncle = this.parent.getSibling();
        }
        return uncle;
    }

    public RedBlackTreeNode<K, V> getSibling(){
        RedBlackTreeNode<K, V> sibling = null;
        if(this.parent != null) {
            sibling = this.parent.getLeft();
            if (this == sibling) {
                sibling = this.parent.getRight();
            }
        }
        return sibling;
    }

    public void setLeft(RedBlackTreeNode<K, V> left) {
        System.out.println("Setting " + this + " left node to " + left);
        this.left = left;
    }

    public RedBlackTreeNode<K, V> getLeft() {
        return left;
    }

    public void setRight(RedBlackTreeNode<K, V>  right) {
        System.out.println("Setting " + this + " right node to " + right);
        this.right = right;
    }

    public RedBlackTreeNode<K, V> getRight() {
        return right;
    }

    public RedBlackTreeNode<K, V> getParent() {
        return parent;
    }

    public void setParent(RedBlackTreeNode<K,V> parent) {
        System.out.println("Setting " + this + " parent to " + parent);
        this.parent = parent;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        System.out.println("Setting " + this + " color to " + color.toString());
        this.color = color;
    }

    @Override
    public String toString() {
        return "[" + this.getKey() + " " + this.getColor() + "]";
    }
}
