package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import src.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListTest {
    List<Integer> list;
    final int max = 1000;

    @BeforeEach
    void setUp() {
        list = new List<>();
    }
    
    void fullListEmplace() {
        for (int i = 0; i < max; ++i)
            list.emplaceBack(i);
    }

    private void fullListPush() {
        for (int i = 0; i < max; ++i)
            list.push(i);
    }

    @Test
    void countZeroAfterInit() {
        assertEquals(0, list.getCount());
    }

    @Test
    void add() {
        list.emplaceBack(-1);
        assertEquals(1, list.getCount());
    }

    @Test
    void addAndSearch() {
        list.emplaceBack(-1);
        assertTrue(list.search(-1));
    }

    @Test
    void addAndCount() {
        for (int i = 0; i < max; ++i)
            list.emplaceBack(i);

        assertEquals(max, list.getCount());
    }

    @Test
    void addAndSearchAll() {
        for (int i = 0; i < max; ++i)
            list.emplaceBack(i);

        for (int i = 0; i < max; ++i)
            assertTrue(list.search(i));
    }

    @Test
    void addAndPopAll() {
        for (int i = 0; i < max; ++i)
            list.emplaceBack(i);

        for (int i = 0; i < max; ++i) {
            Integer popped = list.pop();
            assertEquals(i, popped);
            assertEquals(max - (i + 1), list.getCount());
        }

        assertEquals(0, list.getCount());
    }

    @Test
    void pushAndPopAll() {
        fullListPush();

        for (int i = 0; i < max; ++i) {
            Integer popped = list.pop();
            assertEquals(max - (i + 1), popped);
            assertEquals(max - (i + 1), list.getCount());
        }

        assertEquals(0, list.getCount());
    }

    @Test
    void addAndPopBackAll() {
        fullListEmplace();

        for (int i = 0; i < max; ++i) {
            Integer popped = list.popBack();
            assertEquals(max - (i + 1), popped);
            assertEquals(max - (i + 1), list.getCount());
        }

        assertEquals(0, list.getCount());
    }

    @Test
    void pushAndPopBackAll() {
        fullListPush();

        for (int i = 0; i < max; ++i) {
            Integer popped = list.popBack();
            assertEquals(i, popped);
            assertEquals(max - (i + 1), list.getCount());
        }

        assertEquals(0, list.getCount());
    }

    @Test
    void addAndSelectForward() {
        fullListEmplace();

        for (int i = 0; i < max; ++i) {
            Integer selected = list.select(i);
            assertEquals(i, selected);
            assertEquals(max, list.getCount());
        }
    }

    @Test
    void addAndSelectBackward() {
        fullListEmplace();

        for (int i = max - 1; i >= 0; --i) {
            Integer selected = list.select(i);
            assertEquals(i, selected);
            assertEquals(max, list.getCount());
        }
    }

    @Test
    void addAndSelectOutOfBound() {
        fullListEmplace();

        assertThrows(IndexOutOfBoundsException.class, () -> list.select(max + 1));
    }

    @Test
    void addAndDelete() {
        fullListEmplace();

        for (int i = max - 1; i >= 0; --i) {
            assertTrue(list.search(i));
            assertTrue(list.delete(i));
            assertFalse(list.search(i));
            assertEquals(i, list.getCount());

            for (int j = i - 1; j >= 0; --j) {
                Integer selected = list.select(j);
                assertEquals(j, selected);
            }
        }

        assertFalse(list.delete(0));
    }

    @Test
    void iterable() {
        int i = 0;
        fullListEmplace();

        for(Integer jx: list) {
            assertEquals(i, jx);
            i++;
        }
    }
}
