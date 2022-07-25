package src;

import java.util.ArrayList;
import java.util.Iterator;

public class Map<K, V> implements Iterable<Pair<K, V>> {
    int primeIndex;
    int count;

    ArrayList<MapNode<K, V>> nodes;

    public Map() {
        this.primeIndex = 0;
        this.allocateArrayList();
        this.count = 0;
    }

    private Map(int primeIndex) {
        this.primeIndex = primeIndex;
        this.allocateArrayList();
        this.count = 0;
    }

    private void allocateArrayList() {
        nodes = new ArrayList<>(currentAllocation());

        for(int i = 0; i < currentAllocation(); ++i)
            nodes.add(null);
    }

    public int currentAllocation() {
        return Primes.get(primeIndex);
    }

    private int getNextIndex(int index) {
        index = (int) ((0.3 * index) * index + 1.5 * index + 7);
        index = index % currentAllocation();
        return index;
    }

    private int getIndex(K key) {
        int index = key.hashCode();

        while (nodes.get(index) != null)
            index = getNextIndex(index);

        return index;
    }

    public void put(MapNode<K, V> node) {
        int index = getIndex(node.key);
        this.nodes.set(index, node);
        this.count++;
    }

    public void put(K key, V value) {
        if (this.count == currentAllocation()) {
            realloc();
        }

        this.put(new MapNode<>(key, value));
    }

    private void realloc() {
        Map<K, V> map = new Map<>(primeIndex + 1);
        putAllInAnotherMap(map);
        copyMapIntoThis(map);
    }

    private void copyMapIntoThis(Map<K, V> map) {
        this.count = map.count;
        this.nodes = map.nodes;
        this.primeIndex = map.primeIndex;
    }

    private void putAllInAnotherMap(Map<K, V> map) {
        for (MapNode<K, V> nodex: nodes) {
            map.put(nodex);
        }
    }

    public V get(K key) {
        int i = 0;
        int index = key.hashCode();
        MapNode<K, V> target = nodes.get(index);

        while (target != null && !target.key.equals(key)) {
            index = getNextIndex(index);
            target = nodes.get(index);
            ++i;

            if (i > currentAllocation()) {
                target = null;
                break;
            }
        }

        return target != null ? target.value : null;
    }

    public int getCount() {
        return this.count;
    }

    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new MapIterator<>(nodes);
    }
}
