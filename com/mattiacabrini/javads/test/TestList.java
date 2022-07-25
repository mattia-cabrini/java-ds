package com.mattiacabrini.javads.test;

import com.mattiacabrini.javads.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mattiacabrini.javads.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestList {
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
    void addAndDeleteBack() {
        fullListEmplace();

        for (int i = max - 1; i >= 0; --i) {
            deleteAndCheckOnListItem(i);
            assertEquals(i, list.getCount());

            for (int j = i - 1; j >= 0; --j) {
                Integer selected = list.select(j);
                assertEquals(j, selected);
            }
        }

        assertFalse(list.delete(0));
    }

    @Test
    void addAndDeleteFront() {
        fullListEmplace();

        for (int i = 0; i < max; ++i) {
            deleteAndCheckOnListItem(i);
            assertEquals(max - i - 1, list.getCount());

            for (int j = 0; j < max - i - 1; ++j) {
                Integer selected = list.select(j);
                assertEquals(j + i + 1, selected);
            }
        }

        assertFalse(list.delete(0));
    }

    @Test
    void addAndDeleteMiddle() {
        int offset = 10;
        fullListEmplace();

        for (int i = offset; i < max - 10*offset; ++i) {
            deleteAndCheckOnListItem(i);
            assertEquals(max - i - 1 + offset, list.getCount());
        }

        assertFalse(list.delete(offset));
    }

    private void deleteAndCheckOnListItem(int i) {
        assertTrue(list.search(i));
        assertTrue(list.delete(i));
        assertFalse(list.search(i));
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

    @Test
    void iterableManualMode() {
        int i = 0, jx;
        fullListEmplace();

        ListIterator<Integer> iterator = list.iterator();

        while(iterator.hasNext()) {
            jx = iterator.next();
            assertEquals(i, jx);
            i++;
        }
    }
}
