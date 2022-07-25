package src;

import java.util.Iterator;

public class ListIterator<T> implements Iterator<T> {
    List<T> list;
    int index;

    public ListIterator(List<T> list, int index) {
        this.list = list;
        this.index = index;
    }

    @Override
    public boolean hasNext() {
        return index < list.getCount() - 1;
    }

    @Override
    public T next() {
        index++;
        return list.select(index - 1);
    }
}
