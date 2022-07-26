package com.mattiacabrini.javads;

import org.junit.jupiter.api.Test;

class BstNode<T extends Comparable<T>> {
    BstNode<T> prev;

    T item;
    int count;

    BstNode<T> left;
    BstNode<T> right;

    public BstNode(BstNode<T> prev, T item, int count, BstNode<T> left, BstNode<T> right) {
        this.prev = prev;
        this.item = item;
        this.count = count;
        this.left = left;
        this.right = right;
    }

    public int getCount() {
        return this.count;
    }

    public BstNode<T> insertR(T i, BstNode<T> prev, BstNode<T> sentinel) {
        if (this == sentinel)
            return new BstNode<>(prev, i, 1, sentinel, sentinel);

        int cmp = item.compareTo(i);

        if (cmp < 0) {
            right = right.insertR(i, this, sentinel);
            count++;
        }

        else if (cmp > 0) {
            left = left.insertR(i, this, sentinel);
            count++;
        }

        return this;
    }

    public BstNode<T> searchR(T i) {
        if (right == null)
            return null;

        int cmp = item.compareTo(i);

        if (cmp < 0)
            return right.searchR(i);

        else if (cmp > 0)
            return left.searchR(i);

        return this;
    }

    public boolean selfCheck(BstNode<T> sentinel) {
        if (this == sentinel)
            return true;

        if (count != left.count + right.count + 1)
            return false;

        if (childPrevKo(right, sentinel) || childPrevKo(left, sentinel))
            return false;

        if (leftItemKo(sentinel) || rightItemKo(sentinel))
            return false;

        return left.selfCheck(sentinel) && right.selfCheck(sentinel);
    }

    private boolean childPrevKo(BstNode<T> child, BstNode<T> sentinel) {
        if (child == sentinel)
            return false;

        return child.prev != this;
    }

    private boolean leftItemKo(BstNode<T> sentinel) {
        if (left == sentinel)
            return false;

        return left.item.compareTo(item) >= 0;
    }

    private boolean rightItemKo(BstNode<T> sentinel) {
        if (right == sentinel)
            return false;

        return right.item.compareTo(item) <= 0;
    }

    public void setLeft(BstNode<T> left) {
        this.left = left;
    }

    public void setRight(BstNode<T> right) {
        this.right = right;
    }

    public T getItem() {
        return this.item;
    }

    public BstNode<T> getLeft() {
        return this.left;
    }

    public BstNode<T> getRight() {
        return this.right;
    }

    public BstNode<T> rotRight(BstNode<T> sentinel) {
        BstNode<T> hprev = prev;
        BstNode<T> hl = left;

        left = hl.right;
        hl.right = this;
        prev = hl;
        hl.prev = hprev;

        left.prev = this;

        count = left.count + right.count + 1;
        hl.count = hl.left.count + hl.right.count + 1;

        return hl;
    }

    public BstNode<T> rotLeft(BstNode<T> sentinel) {
        BstNode<T> hprev = prev;
        BstNode<T> hr = right;

        right = hr.left;
        hr.left = this;
        prev = hr;
        hr.prev = hprev;

        right.prev = this;

        count = left.count + right.count + 1;
        hr.count = hr.left.count + hr.right.count + 1;

        return hr;
    }

    public BstNode<T> partR(int l, BstNode<T> sentinel) {
        if (count < l + 1)
            throw new IllegalArgumentException("Cannot partition by " + l + " when BST size is " + count);

        if (left.count < l) {
            right = right.partR(l - left.count - 1, sentinel);
            return rotLeft(sentinel);
        }

        if (left.count > l) {
            left = left.partR(l, sentinel);
            return rotRight(sentinel);
        }

        return this;
    }

    public boolean isBalanced(BstNode<T> sentinel) {
        if (this == sentinel)
            return true;

        if (left.count != right.count)
            return false;

        return left.isBalanced(sentinel) && right.isBalanced(sentinel);
    }

    public BstNode<T> balance(BstNode<T> sentinel) {
        BstNode<T> n;

        if (this == sentinel)
            return this;

        n = partR(count / 2, sentinel);

        n.left = n.left.balance(sentinel);
        n.right = n.right.balance(sentinel);

        return n;
    }
}
