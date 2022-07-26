package com.mattiacabrini.javads;

import org.junit.jupiter.api.Test;

public class BST<T extends Comparable<T>> {
    BstNode<T> sentinel;
    BstNode<T> root;

    public BST() {
        this.sentinel = new BstNode<>(null, null, 0, null, null);
        this.root = sentinel;
    }

    public int getCount() {
        return root.getCount();
    }

    public void insert(T i) {
        root = root.insertR(i, sentinel, sentinel);
    }

    public boolean search(T i) {
        return root.searchR(i) != null;
    }

    public boolean selfCheck() {
        return root.selfCheck(sentinel);
    }

    /*
     * TODO Delete
     * TODO Balance
     */
}
