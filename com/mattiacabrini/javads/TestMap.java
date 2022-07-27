package com.mattiacabrini.javads;

import com.mattiacabrini.javads.MapIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mattiacabrini.javads.Map;
import com.mattiacabrini.javads.Pair;
import com.mattiacabrini.javads.Primes;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TestMap {
    Map<Integer, String> map;
    int max = 160000;

    @BeforeEach
    void setUp() {
        map = new Map<>();
    }

    void fullMap() {
        for (int i = 0; i < max; ++i)
            map.put(i, Integer.toString(i));
    }

    @Test
    void emptyMapPrime() {
        Assertions.assertEquals(Primes.get(0), map.currentAllocation());
    }

    @Test
    void addItemAndGet() {
        map.put(0, "0");
        assertEquals(1, map.getCount());
        assertEquals("0", map.get(0));
    }

    @Test
    void addAnyGetAny() {
        assertTrue(max > map.currentAllocation());
        assertEquals(map.currentAllocation(), Primes.get(0));
        assertTrue(max < Primes.get(1));

        fullMap();

        assertEquals(max, map.getCount());
        assertEquals(Primes.get(1), map.currentAllocation());

        for (int i = 0; i < max; ++i)
            assertEquals(Integer.toString(i), map.get(i));
    }

    @Test
    void addAnyGetAnyReallocN() {
        max = 390000;
        assertTrue(max > map.currentAllocation());
        assertEquals(map.currentAllocation(), Primes.get(0));
        assertTrue(max > Primes.get(3));
        assertTrue(max < Primes.get(4));

        fullMap();

        assertEquals(max, map.getCount());
        assertEquals(Primes.get(4), map.currentAllocation());

        for (int i = 0; i < max; ++i)
            assertEquals(Integer.toString(i), map.get(i));
    }

    @Test
    void iterable() {
        int i = 0;
        fullMap();

        for(Pair<Integer, String> jx: map) {
            verifyJX(i, jx);
            i++;
        }

        assertEquals(i, max);
    }

    private void verifyJX(int i, Pair<Integer, String> jx) {
        assertEquals(Integer.toString(i), jx.b);
        assertEquals(jx.b, map.get(jx.a));
    }

    @Test
    void iterableManualMode() {
        int i = 0;
        fullMap();

        MapIterator<Integer, String> iterator = map.iterator();

        while (iterator.hasNext()) {
            verifyExtractedElement(iterator);
            i++;
        }

        assertEquals(i, max);
    }

    @Test
    void mapAddAndGetNotFull() {
        int i = 0;

        map.put(3, "3");
        map.put(3+Primes.get(0), "£");

        assertEquals("£", map.get(3+Primes.get(0)));
    }

    @Test
    void mapGetNotInThere() {
        max = Primes.get(0);
        fullMap();
        assertThrows(
                IllegalArgumentException.class,
                () -> map.get(3+Primes.get(0))
        );
    }

    private void verifyExtractedElement(MapIterator<Integer, String> iterator) {
        Pair<Integer, String> jx = iterator.next();
        verifyJX(jx.a, jx);
    }

    @Test
    void iterableManualModeNotFull() {
        int i = 0;
        max = 19;

        for (int j = 0; j < 2*max; j += 2) {
            map.put(j, Integer.toString(j));
        }

        MapIterator<Integer, String> iterator = map.iterator();

        while (iterator.hasNext()) {
            verifyExtractedElement(iterator);
            i++;
        }

        assertEquals(i, max);
    }

    @Test
    void getNotExistingItem() {
        assertThrows(IllegalArgumentException.class, () -> map.get(0));
    }

    @Test
    void unset() {
        fullMap();

        map.unset(0);

        assertThrows(IllegalArgumentException.class, () -> map.get(0));
    }

    /*
     * Only to roughly test performance
    @Test
    void addAnyGetAnyReallocN2() {
        max = 9925387;
        assertTrue(max > map.currentAllocation());

        fullMap();

        assertEquals(max, map.getCount());

        for (int i = 0; i < max; ++i)
            assertEquals(Integer.toString(i), map.get(i));
    }
    */
}
