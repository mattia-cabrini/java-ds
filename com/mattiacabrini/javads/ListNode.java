package com.mattiacabrini.javads;

class ListNode<T> {
    private final T i;
    private ListNode<T> next;
    private ListNode<T> prev;

    public ListNode(T i, ListNode<T> next) {
        this.prev = null;
        this.i = i;
        this.next = next;
    }

    public ListNode(ListNode<T> prev, T i, ListNode<T> next) {
        this.prev = prev;
        this.i = i;
        this.next = next;
    }

    public ListNode(T i) {
        this.i = i;
        this.next = null;
    }

    public T getItem() {
        return this.i;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public ListNode<T> next() {
        return this.next;
    }

    public void setNext(ListNode<T> listNode) {
        this.next = listNode;
    }

    public ListNode<T> prev() {
        return this.prev;
    }

    public void setPrev(ListNode<T> prev) {
        this.prev = prev;
    }
}
