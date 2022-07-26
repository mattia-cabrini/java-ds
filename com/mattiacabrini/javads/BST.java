package com.mattiacabrini.javads;

public class BST<T extends Comparable<T>> {
    BstNode<T> sentinel;
    BstNode<T> root;
    int countBalancingSensibleOperations;
    int cycleAutoBalancing;

    public BST() {
        this.sentinel = new BstNode<>(null, null, 0, null, null);
        this.root = sentinel;
        this.countBalancingSensibleOperations = 0;
        this.cycleAutoBalancing = 1024;
    }

    private void newOperation() {
        this.countBalancingSensibleOperations++;
    }

    public boolean needBalance() {
        return this.countBalancingSensibleOperations % this.cycleAutoBalancing == 0;
    }

    public int getCount() {
        return root.getCount();
    }

    public void insert(T i) {
        root = root.insertR(i, sentinel, sentinel);
        newOperation();

        if (needBalance()) {
            balance();
        }
    }

    public boolean search(T i) {
        return root.searchR(i) != null;
    }

    public boolean selfCheck() {
        return root.selfCheck(sentinel);
    }

    public void balance() {
        root = root.balance(sentinel);
    }

    public int depth() {
        return root.depthR(sentinel, 0);
    }

    /*
     * TODO Delete
     */
}
