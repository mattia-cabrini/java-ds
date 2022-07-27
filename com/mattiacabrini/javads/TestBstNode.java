package com.mattiacabrini.javads;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestBstNode {
    static BstNode<Integer> sentinel;
    int max = 1000;

    @BeforeAll
    static void sentinel() {
        sentinel = new BstNode<>( null, null, 0, null, null);
    }

    @Test
    void countWrongSingleNode() {
        BstNode<Integer> root =
                new BstNode<>(sentinel, -1, 0, sentinel, sentinel);
        assertFalse(root.selfCheck(sentinel));
    }

    @Test
    void leftWrongPrev() {
        BstNode<Integer> left =
                new BstNode<>(sentinel, 1, 1, sentinel, sentinel);
        BstNode<Integer> root =
                new BstNode<>(sentinel, 2, 2, left, sentinel);
        assertFalse(root.selfCheck(sentinel));
    }

    @Test
    void rightWrongPrev() {
        BstNode<Integer> right =
                new BstNode<>(sentinel, 1, 1, sentinel, sentinel);
        BstNode<Integer> root =
                new BstNode<>(sentinel, 2, 2, sentinel, right);
        assertFalse(root.selfCheck(sentinel));
    }

    @Test
    void inconsistentConditionLeftTree() {
        BstNode<Integer> root  = new BstNode<>(sentinel, 1, 3, sentinel, sentinel);
        BstNode<Integer> left  = new BstNode<>(root,     2, 1, sentinel, sentinel);
        BstNode<Integer> right = new BstNode<>(root,     3, 1, sentinel, sentinel);
        root.setLeft(left);
        root.setRight(right);
        assertFalse(root.selfCheck(sentinel));
    }

    @Test
    void inconsistentConditionRightTree() {
        BstNode<Integer> root  = new BstNode<>(sentinel, 1, 3, sentinel, sentinel);
        BstNode<Integer> left  = new BstNode<>(root,     0, 1, sentinel, sentinel);
        BstNode<Integer> right = new BstNode<>(root,     -1, 1, sentinel, sentinel);
        root.setLeft(left);
        root.setRight(right);
        assertFalse(root.selfCheck(sentinel));
    }

    @Test
    void rotRight() {
        BstNode<Integer> root = getRotationRightBstRoot();

        root = root.rotRight(sentinel);
        assertTrue(root.selfCheck(sentinel));

        assertEquals(root.getItem(), -2);
        assertEquals(root.getLeft().getItem(), -3);
        assertEquals(root.getRight().getItem(), 0);

        assertEquals(root.getRight().getRight().getItem(), 1);
        assertEquals(root.getRight().getLeft().getItem(), -1);
    }

    @Test
    void rotLeft() {
        BstNode<Integer> root = getRotationLeftBstRoot();

        root = root.rotLeft(sentinel);
        assertTrue(root.selfCheck(sentinel));

        assertEquals(root.getItem(), 2);
        assertEquals(root.getLeft().getItem(), 0);
        assertEquals(root.getRight().getItem(), 3);

        assertEquals(root.getLeft().getLeft().getItem(), -1);
        assertEquals(root.getLeft().getRight().getItem(), 1);
    }

    private BstNode<Integer> getRotationRightBstRoot() {
        BstNode<Integer> root  = new BstNode<>(sentinel, 0, 5, sentinel, sentinel);

        BstNode<Integer> m2  = new BstNode<>(root,     -2, 3, sentinel, sentinel);
        BstNode<Integer> p1 = new BstNode<>(root,     1, 1, sentinel, sentinel);
        root.setLeft(m2);
        root.setRight(p1);

        BstNode<Integer> m3  = new BstNode<>(m2,     -3, 1, sentinel, sentinel);
        BstNode<Integer> m1  = new BstNode<>(m2,     -1, 1, sentinel, sentinel);
        m2.setLeft(m3);
        m2.setRight(m1);

        assertTrue(root.selfCheck(sentinel));

        return root;
    }

    private BstNode<Integer> getRotationLeftBstRoot() {
        BstNode<Integer> root  = new BstNode<>(sentinel, 0, 5, sentinel, sentinel);

        BstNode<Integer> m1  = new BstNode<>(root,     -1, 1, sentinel, sentinel);
        BstNode<Integer> p2 = new BstNode<>(root,     2, 3, sentinel, sentinel);
        root.setLeft(m1);
        root.setRight(p2);

        BstNode<Integer> p1  = new BstNode<>(p2,     1, 1, sentinel, sentinel);
        BstNode<Integer> p3  = new BstNode<>(p2,     3, 1, sentinel, sentinel);
        p2.setLeft(p1);
        p2.setRight(p3);

        assertTrue(root.selfCheck(sentinel));

        return root;
    }

    @Test
    void testPart() {
        BstNode<Integer> root = sentinel;

        for (int i = 0; i < max; ++i) {
            root = root.insertR(i, sentinel, sentinel);
        }

        part0toMax(root);
    }

    @Test
    void testPart2() {
        BstNode<Integer> root = sentinel;

        for (int i = -1; i > -max-1; --i) {
            root = root.insertR(i, sentinel, sentinel);
        }

        part0toMax(root);
    }

    @Test
    void testPartEmpty() {
        BstNode<Integer> root = sentinel;

        assertThrows(IllegalArgumentException.class, () -> part0toMax(root));
    }

    @Test
    void testPartInconsistent() {
        BstNode<Integer> root = sentinel;

        for (int i = 0; i < max - 1; ++i) {
            root = root.insertR(i, sentinel, sentinel);
        }

        BstNode<Integer> rootF = root;

        assertThrows(IllegalArgumentException.class, () -> part0toMax(rootF));
    }

    @Test
    void testBalance() {
        BstNode<Integer> root = sentinel;

        for (int i = 0; i < 1024-1; ++i) {
            root = root.insertR(i, sentinel, sentinel);
        }

        root = root.balance(sentinel);

        assertTrue(root.selfCheck(sentinel));
        assertTrue(root.isBalanced(sentinel));
    }

    @Test
    void testIsBalancedTure() {
        BstNode<Integer> root = new BstNode<>(sentinel, 0, 3, sentinel, sentinel);
        BstNode<Integer> left = new BstNode<>(root, -1, 1, sentinel, sentinel);
        BstNode<Integer> right = new BstNode<>(root, 1, 1, sentinel, sentinel);
        root.setLeft(left);
        root.setRight(right);

        assertTrue(root.selfCheck(sentinel));
        assertTrue(root.isBalanced(sentinel));
    }

    @Test
    void testIsBalancedFalse() {
        BstNode<Integer> root = new BstNode<>(sentinel, 0, 3, sentinel, sentinel);
        BstNode<Integer> right = new BstNode<>(root, 1, 2, sentinel, sentinel);
        BstNode<Integer> rightright = new BstNode<>(right, 2, 1, sentinel, sentinel);
        root.setRight(right);
        right.setRight(rightright);

        assertTrue(root.selfCheck(sentinel));
        assertFalse(root.isBalanced(sentinel));
    }

    private void part0toMax(BstNode<Integer> root) {
        for (int i = 0; i < max; ++i) {
            root = root.partR(i, sentinel);
            assertTrue(root.selfCheck(sentinel));
            assertEquals(root.getLeft().getCount(), i);
            assertTrue(root.selfCheck(sentinel));
        }
    }

    @Test
    void depth() {
        assertEquals(sentinel.depthR(sentinel, 0), 0);
    }

    @Test
    void depthOfRoot() {
        BstNode<Integer> root = new BstNode<>(sentinel, 0, 1, sentinel, sentinel);
        assertEquals(root.depthR(sentinel, 0), 1);
    }

    @Test
    void depthOfBstTree() {
        BstNode<Integer> root = getRootToJoin(0, -2, 1, -3, -1, -4);

        assertTrue(root.selfCheck(sentinel));
        assertEquals(root.depthR(sentinel, 0), 4);
    }

    @Test
    void joinRight() {
        BstNode<Integer> nodeJoin;

        BstNode<Integer> root = getRootToJoin(0, -2, 1, -3, -1, -4);
        BstNode<Integer> root2 = getRootToJoin(6, 4, 7, 3, 5, 2);

        nodeJoin = root.joinR(root2, sentinel);
        assertTrue(nodeJoin.selfCheck(sentinel));
    }

    @Test
    void joinLeft() {
        BstNode<Integer> nodeJoin;

        BstNode<Integer> root = getRootToJoin(0, -2, 1, -3, -1, -4);
        BstNode<Integer> root2 = getRootToJoin(6, 4, 7, 3, 5, 2);

        nodeJoin = root2.joinR(root, sentinel);
        assertTrue(nodeJoin.selfCheck(sentinel));
    }

    @Test
    void joinSelf() {
        BstNode<Integer> root = getRootToJoin(0, -2, 1, -3, -1, -4);

        assertThrows(IllegalArgumentException.class, () -> root.joinR(root, sentinel));
    }

    private BstNode<Integer> getRootToJoin(int x, int x1, int x2, int x3, int x4, int x5) {
        BstNode<Integer> root = new BstNode<>(sentinel, x, 6, sentinel, sentinel);
        BstNode<Integer> l = new BstNode<>(root, x1, 4, sentinel, sentinel);
        BstNode<Integer> r = new BstNode<>(root, x2, 1, sentinel, sentinel);
        BstNode<Integer> ll = new BstNode<>(l, x3, 2, sentinel, sentinel);
        BstNode<Integer> lr = new BstNode<>(l, x4, 1, sentinel, sentinel);
        BstNode<Integer> lll = new BstNode<>(ll, x5, 1, sentinel, sentinel);

        root.setLeft(l);
        root.setRight(r);
        l.setLeft(ll);
        l.setRight(lr);
        ll.setLeft(lll);

        assertTrue(root.selfCheck(sentinel));

        return root;
    }

    @Test
    void joinNull() {
        BstNode<Integer> nodeJoin;

        BstNode<Integer> root = getRootToJoin(0, -2, 1, -3, -1, -4);

        assertTrue(root.selfCheck(sentinel));

        nodeJoin = root.joinR(null, sentinel);
        assertEquals(nodeJoin, root);
        assertTrue(nodeJoin.selfCheck(sentinel));
    }

    @Test
    void joinSentinel() {
        BstNode<Integer> nodeJoin;

        BstNode<Integer> root = getRootToJoin(0, -2, 1, -3, -1, -4);

        assertTrue(root.selfCheck(sentinel));

        nodeJoin = root.joinR(sentinel, sentinel);
        assertEquals(nodeJoin, root);
        assertTrue(nodeJoin.selfCheck(sentinel));
    }
}
