package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Heap;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {
    Heap<Integer> heap;
    final int max = 1000;
    final Integer negInfinity = -1;

    @BeforeEach
    void setUp() {
        heap = new Heap<>(max, negInfinity);
    }

    @Test
    void initOk() {
        assertEquals(heap.getMaxSize(), max);
        assertEquals(heap.getFreeSlots(), max);
    }

    @Test
    void insertOneItem() throws Exception {
        Integer i = 5;

        heap.add(i);

        assertEquals(heap.showMax(), i);
        assertEquals(heap.getFreeSlots(), max - 1);
        assertEquals(heap.getMaxSize(), max);
    }

    @Test
    void insertTooManyItems() throws Exception {
        for (int i = 0; i < max; ++i)
            heap.add(i);

        try {
            heap.add(max);
            fail();
        } catch (Exception e) {
            //
        }
    }

    @Test
    void showMax() throws Exception {
        for (int i = 0; i < max; ++i)
            heap.add(i);

        assertEquals(max - 1, heap.showMax());

        for (int i = max - 1; i > -1; --i) {
            assertEquals(heap.pop(), i);

            if (i != 0)
                assertEquals(i - 1, heap.showMax());

            assertEquals(max - i, heap.occupiedSlots());
        }
    }

    @Test
    void popOnEmpty() {
        try {
            heap.pop();
            fail();
        } catch (Exception ex) {
            //
        }
    }
}