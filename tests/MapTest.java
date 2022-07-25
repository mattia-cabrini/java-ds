package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Map;
import src.Pair;
import src.Primes;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
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
        assertEquals(Primes.get(0), map.currentAllocation());
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
            assertEquals(i, jx.a);
            assertEquals(Integer.toString(i), jx.b);
            assertEquals(jx.b, map.get(jx.a));
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
