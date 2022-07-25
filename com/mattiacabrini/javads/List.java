package com.mattiacabrini.javads;

import java.util.Iterator;

public class List<T> implements Iterable<T> {
    int count;
    private ListNode<T> first;
    private ListNode<T> last;

    ListNode<T> selectedCache;
    int selectedIndex;

    public List() {
        this.count = 0;
    }

    public int getCount() {
        return this.count;
    }

    public void emplaceBack(T i) {
        if (last == null) {
            first = new ListNode<>(i);
            last = first;

            selectedIndex = 0;
            selectedCache = this.first;
        } else {
            last.setNext(new ListNode<>(last, i, null));
            last = last.next();
        }

        this.count++;
    }

    public void push(T i) {
        if (last == null) {
            emplaceBack(i);
        } else {
            first = new ListNode<>(null, i, first);
            first.next().setPrev(first);
            count++;
            selectedIndex++;
        }
    }

    public ListNode<T> searchN(T i) {
        for (ListNode<T> cur = first; cur != null; cur = cur.next())
            if (cur.getItem().equals(i))
                return cur;

        return null;
    }


    public boolean delete(T i) {
        ListNode<T> target = searchN(i);

        if (target != null && target == last) {
            popBack();
        } else if (target != null && target == first) {
            pop();
        } else if (target != null) {
            target.prev().setNext(target.next());
            target.next().setPrev(target.prev());
            this.count--;
        }

        resetSelectCache();

        return target != null;
    }

    private void resetSelectCache() {
        selectedIndex = 0;
        selectedCache = this.first;
    }

    public boolean search(T i) {
        ListNode<T> target = searchN(i);
        return target != null;
    }

    public T pop() {
        T i = null;

        if (first != null) {
            i = first.getItem();
            first = first.next();

            if (first == null)
                last = null;
            else
                first.setPrev(null);

            count--;
        }

        return i;
    }

    public T popBack() {
        T i = null;

        if (first != null) {
            i = last.getItem();
            last = last.prev();

            if (last != null)
                last.setNext(null);
            else
                first = null;

            this.count--;

            if (this.count == 0)
                this.first = this.last;
        }

        return i;
    }

    public T select(int i) throws IndexOutOfBoundsException {
        if (i >= count)
            throw new IndexOutOfBoundsException();

        if (i <= selectedIndex)
            return selectBackward(i);
        else
            return selectForward(i);
    }

    private T selectBackward(int i) {
        for (; selectedIndex != i; --selectedIndex)
            selectedCache = selectedCache.prev();

        return selectedCache.getItem();
    }

    private T selectForward(int i) {
        for (; selectedIndex != i; ++selectedIndex)
            selectedCache = selectedCache.next();

        return selectedCache.getItem();
    }

    @Override
    public ListIterator<T> iterator() {
        return new ListIterator<>(this, 0);
    }
}
