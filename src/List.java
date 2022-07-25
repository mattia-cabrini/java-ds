package src;

public class List<T> {
    private class Node {
        private final T i;
        private Node next;
        private Node prev;

        public Node(T i, Node next) {
            this.prev = null;
            this.i = i;
            this.next = next;
        }

        public Node(Node prev, T i, Node next) {
            this.prev = prev;
            this.i = i;
            this.next = next;
        }

        public Node(T i) {
            this.i = i;
            this.next = null;
        }

        public T getItem() {
            return this.i;
        }

        public boolean hasNext() {
            return this.next != null;
        }

        public Node next() {
            return this.next;
        }

        public void setNext(Node node) {
            this.next = node;
        }

        public Node prev() {
            return this.prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    int count;
    private Node first;
    private Node last;

    Node selectedCache;
    int selectedIndex;

    public List() {
        this.count = 0;
    }

    public int getCount() {
        return this.count;
    }

    public void emplaceBack(T i) {
        if (last == null) {
            first = new Node(i);
            last = first;

            selectedIndex = 0;
            selectedCache = this.first;
        } else {
            last.setNext(new Node(last, i, null));
            last = last.next();
        }

        this.count++;
    }

    public void push(T i) {
        if (last == null) {
            emplaceBack(i);
        } else {
            first = new Node(null, i, first);
            first.next().setPrev(first);
            count++;
            selectedIndex++;
        }
    }

    public Node searchN(T i) {
        for (Node cur = first; cur != null; cur = cur.next())
            if (cur.getItem().equals(i))
                return cur;

        return null;
    }


    public boolean delete(T i) {
        Node target = searchN(i);

        if (target != null && target == last) {
            popBack();
        } else if (target != null && target == first) {
            pop();
        } else if (target != null) {
            target.prev().setNext(target.next());
            target.next().setPrev(target.prev());
        }

        return target != null;
    }

    public boolean search(T i) {
        Node target = searchN(i);
        return target != null;
    }

    public T pop() {
        T i = null;

        if (this.first != null) {
            i = this.first.getItem();
            this.first = this.first.next();

            if (this.first == null)
                this.last = null;

            this.count--;
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
}
