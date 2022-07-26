package com.mattiacabrini.javads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestBST {
    BST<Integer> bst;
    int max = 1000;

    @BeforeEach
    void setUp() {
        bst = new BST<>();
    }

    @Test
    void countAfter() {
        assertEquals(0, bst.getCount());
    }

    @Test
    void addLeafAndCheck() {
        bst.insert(-1);
        assertTrue(bst.search(-1));
    }

    @Test
    void addLeafs() {
        fill(0, max);
        fill(-max, -1);

        assertTrue(bst.selfCheck());
        assertExistence(0, max);
        assertExistence(-max, -1);
    }

    @Test
    void searchNotExistent() {
        assertFalse(bst.search(-1));
    }

    @Test
    void searchUntilSentinel() {
        bst.insert(-1);
        assertFalse(bst.search(-2));
    }

    @Test
    void searchOnNullTree() {
        assertFalse(new BST<Integer>().search(-1));
    }

    private void fill(int low, int up) {
        for (int i = low; i < up; ++i)
            bst.insert(i);
    }

    private void assertExistence(int low, int up) {
        for (int i = low; i < up; ++i)
            assertTrue(bst.search(i));
    }
}
