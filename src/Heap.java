package src;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private final T negInfinity;
    int maxSize;
    int cur;

    ArrayList<T> items;

    public Heap(int maxSize, T negInfinity) {
        this.maxSize = maxSize;
        this.cur = 0;
        this.items = new ArrayList<>(maxSize);
        this.negInfinity = negInfinity;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getFreeSlots() {
        return maxSize - cur;
    }

    public void add(T i) throws Exception {
        if (this.getFreeSlots() == 0)
            throw new Exception("Heap is already full");

        items.add(i);
        cur++;
        fixUp(this.cur - 1);
    }

    public T showMax() {
        return items.get(0);
    }

    private static int parent(int l) {
        return (l - 1) / 2;
    }

    private static int left(int p) {
        return 2 * p + 1;
    }

    private static int right(int p) {
        return 2 * p + 2;
    }

    private T leftLeft(int p) {
        int index = left(p);

        if (index >= cur) {
            return negInfinity;
        }

        return items.get(index);
    }

    private T leafRight(int p) {
        int index = right(p);

        if (index >= cur) {
            return negInfinity;
        }

        return items.get(index);
    }

    public T pop() {
        T t = showMax();
        items.set(0, negInfinity);
        fixDown(0);
        cur--;
        return t;
    }

    private void swap(int i, int j) {
        T itemI = items.get(i);
        T itemJ = items.get(j);

        items.set(j, itemI);
        items.set(i, itemJ);
    }

    private void fixUp(int l) {
        T leaf, parent;

        int p = parent(l);

        if (p >= 0 && l < cur) {
            leaf = items.get(l);
            parent = items.get(p);

            if (leaf.compareTo(parent) > 0) {
                swap(l, p);
                fixUp(p);
            }
        }
    }

    private void fixDown(int p) {
        int lpCmp, rpCmp, lrCmp;
        T leafLeft = leftLeft(p);
        T leafRight = leafRight(p);

        if (leafLeft != negInfinity || leafRight != negInfinity) {
            lpCmp = leafLeft.compareTo(items.get(p));
            rpCmp = leafRight.compareTo(items.get(p));
            lrCmp = leafLeft.compareTo(leafRight);

            if (lpCmp > 0 && lrCmp > 0) {
                swap(p, left(p));
                fixDown(left(p));
            }

            if (rpCmp > 0 && lrCmp < 0) {
                swap(p, right(p));
                fixDown(right(p));
            }
        }
    }

    public int occupiedSlots() {
        return maxSize - cur;
    }
}
