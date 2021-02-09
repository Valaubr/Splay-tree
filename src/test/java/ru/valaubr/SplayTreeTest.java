package ru.valaubr;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SplayTreeTest {
    private final int nums = 400000;
    private final int randomNum = new Random().nextInt(400000);
    private final int linearLen = 255;
    private SplayTree<Integer> splayTree;

    @Test
    public void create() {
        splayTree = new SplayTree();
        assertNotNull(splayTree);
        assertTrue(splayTree.isEmpty());
    }

    @Test
    public void checkMainOperations() {
        splayTree = new SplayTree();

        for (int i = linearLen; i != 0; i = (i + linearLen) % nums) {
            assertTrue(splayTree.insert(i));
        }

        assertTrue(splayTree.contains(randomNum));

        for (int i = 1; i < nums; i += 2) {
            splayTree.remove(i);
        }

        assertEquals(2, splayTree.findMin(), 0);
        assertEquals(nums - 2, splayTree.findMax(), 0);

        for (int i = 2; i < nums; i += 2) {
            assertEquals(i, splayTree.find(i), 0);
        }

        for (int i = 1; i < nums; i += 2) {
            assertNull(splayTree.find(i));
        }
    }

    @Test
    public void checkIterator() {
        splayTree = new SplayTree();

        for (int i = 0; i < linearLen; i++) {
            splayTree.insert(i);
        }
    }
}