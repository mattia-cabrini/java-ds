package src;

import java.util.ArrayList;
import java.util.Iterator;

public class MapIterator<K, V> implements Iterator<Pair<K, V>> {
    ArrayList<MapNode<K, V>> list;
    int indexNext;

    public MapIterator(ArrayList<MapNode<K, V>> list) {
        this.list = list;
        this.indexNext = -1;
        travelToValidIndex();
    }

    public void travelToValidIndex() {
        indexNext++;

        while(indexNext < list.size() && list.get(indexNext) == null) {
            indexNext++;
        }
    }

    @Override
    public boolean hasNext() {
        return indexNext < list.size() && list.get(indexNext) != null;
    }

    @Override
    public Pair<K, V> next() {
        MapNode<K, V> node = list.get(indexNext);
        travelToValidIndex();
        return new Pair<>(node.key, node.value);
    }
}
