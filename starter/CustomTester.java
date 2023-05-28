/*
 * Name: Farris Danish
 * PID: A17401247
 * Email: fbinsyahrilakmar@ucsd.edu
 * Sources used: Write-up
 */

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class provides unit tests for MyDeque, MyStack and MyQueue classes
 */
public class CustomTester {

    /**
     * Helper method to initialize all instance variables of MyDeque
     * @param deque The deque to initialize
     * @param data The data array
     * @param size The value for size
     * @param front The value for front
     * @param rear The value for rear
     */
    static void initDeque(
        MyDeque<Integer> deque,
        Object[] data,
        int size,
        int front,
        int rear
    ) {
        deque.data = data;
        deque.size = size;
        deque.front = front;
        deque.rear = rear;
    }

    /**
     * Tests exception thrown by MyDeque constructor if arg if null
     */
    @Test
    public void testMyDequeConstrutor() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new MyDeque<Integer>(-1)
        );
    }

    /**
     * Tests expand capacity when initial capacity is 0
     * and when full but front is not 0 and rear is not size - 1
     */
    @Test
    public void testMyDequeExpandCapacity() {
        /* An empty deque should be expanded to capacity 10 */
        MyDeque<Integer> zeroCapDeque = new MyDeque<>(0);
        assertEquals(
            "Initial capacity should be 0",
            0,
            zeroCapDeque.data.length
        );
        assertEquals("size should be initialized to 0", 0, zeroCapDeque.size);
        assertEquals("Front should be initialized to 0", 0, zeroCapDeque.front);
        assertEquals("Rear should be initialized to 0", 0, zeroCapDeque.rear);

        zeroCapDeque.expandCapacity();

        assertEquals(
            "underlying array capacity should be 10",
            10,
            zeroCapDeque.data.length
        );
        assertEquals("front should stay the same", 0, zeroCapDeque.front);
        assertEquals("rear should stay same", 0, zeroCapDeque.rear);
        assertEquals("size should stay the same", 0, zeroCapDeque.size);

        /*
         * If full, expand capacity should reset the front to be zero and rear
         * to be size -1 and all the elements should be arranged accordingly
         * And expandCapacity should double the capacity not just add 10
         */
        MyDeque<Integer> deque = new MyDeque<>(5);
        Integer[] orig = { 3, 4, 5, 1, 2 };
        Integer[] changed = { 1, 2, 3, 4, 5, null, null, null, null, null };
        initDeque(deque, orig, 5, 3, 2);

        deque.expandCapacity();
        assertEquals("size should stay the same", 5, deque.size);
        assertEquals("front should be 0", 0, deque.front);
        assertEquals("rear should be set to 4", 4, deque.rear);
        assertArrayEquals("arrays should be equal", changed, deque.data);
    }

    //-------------------------addFirst---------------------------

    /**
     * Tests for wrapping behavior when adding when front is index 0
     */
    @Test
    public void testMyDequeAddFirst() {
        /* Should wrap to be back when front is index 0 */
        MyDeque<Integer> deque = new MyDeque<>(5);
        Integer[] orig = { 1, 2, 3, null, null };
        Integer[] changed = { 1, 2, 3, null, 4 };
        initDeque(deque, orig, 3, 0, 2);

        deque.addFirst(4);

        assertEquals("size should increase to 4", 4, deque.size);
        assertEquals("capacity should not change", 5, deque.data.length);
        assertEquals("front should change to index 4", 4, deque.front);
        assertEquals("rear should not change", 0, 0, 0);
        assertArrayEquals(
            "The two arrays should be equal",
            changed,
            deque.data
        );
    }

    /**
     * Test addFirst when deque is at capacity
     */
    @Test
    public void testMyDequeAddFirstAtCapacity() {
        /* Should call expandCapacity when at capacity */
        MyDeque<Integer> deque2 = new MyDeque<>(5);
        Integer[] orig2 = { 3, 4, 5, 1, 2 };
        Integer[] changed2 = { 1, 2, 3, 4, 5, null, null, null, null, 6 };
        initDeque(deque2, orig2, 5, 3, 2);

        deque2.addFirst(6);

        assertEquals("capacity should increase to 10", 10, deque2.data.length);
        assertEquals("size should increase by 1 to 6", 6, deque2.size);
        assertEquals("front should be 9", 9, deque2.front);
        assertEquals("rear should be 4", 4, deque2.rear);
        assertArrayEquals(changed2, deque2.data);
    }

    /**
     * Tests when adding 1000 elements
     */
    @Test
    public void testMyDequeAddFirst100Adds() {
        MyDeque<Integer> stressDeque = new MyDeque<>(0);
        for (int i = 0; i < 1000; i++) {
            stressDeque.addFirst(i);
        }
        assertEquals("capacity should be 1280", 1280, stressDeque.data.length);
        assertEquals("size should be 1000", 1000, stressDeque.size);
        assertEquals("front should be 920", 920, stressDeque.front);
        assertEquals("rear should be 639", 639, stressDeque.rear);
    }

    /**
     * Tests for when adding null
     */
    @Test
    public void testMyDequeAddFirstNull() {
        /* Throws exception */
        MyDeque<Integer> deque = new MyDeque<>(5);
        assertThrows(
            "throws exception when adding null",
            NullPointerException.class,
            () -> deque.addFirst(null)
        );
    }

    // -------------------------addLast---------------------------

    /**
     * Test for when rear is already at length - 1 and wrap
     */
    @Test
    public void testMyDequeAddLast() {
        /* Wrap when rear is at length - 1 */
        MyDeque<Integer> deque = new MyDeque<>(5);
        Integer[] orig = { null, null, 3, 4, 5 };
        Integer[] changed = { 1, null, 3, 4, 5 };
        initDeque(deque, orig, 3, 2, 4);

        deque.addLast(1);

        assertEquals("capacity should not change", 5, deque.data.length);
        assertEquals("size should increase by 1 to 4", 4, deque.size);
        assertEquals("front should stay the same", 2, deque.front);
        assertEquals("rear should be set to 0", 0, deque.rear);
        assertArrayEquals(changed, deque.data);
    }

    /**
     * Test addLast when deque is at capacity
     */
    @Test
    public void testMyDequeAddLasttAtCapacity() {
        /* Should call expandCapacity when deque is at capacity */
        MyDeque<Integer> deque2 = new MyDeque<>(5);
        Integer[] orig2 = { 3, 4, 5, 1, 2 };
        Integer[] changed2 = { 1, 2, 3, 4, 5, 6, null, null, null, null };
        initDeque(deque2, orig2, 5, 3, 2);

        deque2.addLast(6);

        assertEquals("capacity should increase to 10", 10, deque2.data.length);
        assertEquals("size should increase by 1 to 6", 6, deque2.size);
        assertEquals("front should be 0", 0, deque2.front);
        assertEquals("rear should be 5", 5, deque2.rear);
        assertArrayEquals(changed2, deque2.data);
    }

    /**
     * Test add last with 1000 adds
     */
    @Test
    public void testMyDequeAddLast1000Adds() {
        MyDeque<Integer> stressDeque = new MyDeque<>(0);
        for (int i = 0; i < 1000; i++) {
            stressDeque.addLast(i);
        }
        assertEquals("capacity should be 1280", 1280, stressDeque.data.length);
        assertEquals("size should be 1000", 1000, stressDeque.size);
        assertEquals("front should be 0", 0, stressDeque.front);
        assertEquals("rear should be 999", 999, stressDeque.rear);
    }

    /**
     * Test addLast should thrown exception when adding null
     */
    @Test
    public void testMyDequeAddLastNull() {
        MyDeque<Integer> deque = new MyDeque<>(5);
        assertThrows(
            "throws exception when adding null",
            NullPointerException.class,
            () -> deque.addLast(null)
        );
    }

    /**
     * Test if deque wraps around
     */
    @Test
    public void testMyDequeRemoveFirst() {
        MyDeque<Integer> deque = new MyDeque<>(5);
        Integer[] orig = { 3, 4, 5, 1, 2 };
        Integer[] changed = { 3, 4, 5, null, 2 };
        Integer[] changed2 = { 3, 4, 5, null, null };
        initDeque(deque, orig, 5, 3, 2);

        deque.removeFirst();
        assertEquals("capacity remains the same", 5, deque.data.length);
        assertEquals("size decrease by 1", 4, deque.size);
        assertEquals("front changes to 4", 4, deque.front);
        assertEquals("rear remains the same", 2, deque.rear);
        assertArrayEquals(changed, deque.data);

        deque.removeFirst();
        assertEquals("capacity remains the same", 5, deque.data.length);
        assertEquals("size decrease by 1", 3, deque.size);
        assertEquals("front changes to 0", 0, deque.front);
        assertEquals("rear remains the same", 2, deque.rear);
        assertArrayEquals(changed2, deque.data);
    }

    /**
     * Test return null if deque is empty
     */
    @Test
    public void testMyDequeRemoveFirstEmpty() {
        MyDeque<Integer> emptyDeque = new MyDeque<>(5);
        assertNull("Should return null since empty", emptyDeque.removeFirst());
    }

    /**
     * Test the behavior of removeLast is deque is wrapped around
     */
    @Test
    public void testMyDequeRemoveLast() {
        MyDeque<Integer> deque = new MyDeque<>(5);
        Integer[] orig = { 5, 1, 2, 3, 4 };
        Integer[] changed = { null, 1, 2, 3, 4 };
        Integer[] changed2 = { null, 1, 2, 3, null };
        initDeque(deque, orig, 5, 1, 0);

        deque.removeLast();
        assertEquals("capacity remains the same", 5, deque.data.length);
        assertEquals("size decrease by 1", 4, deque.size);
        assertEquals("front remains the same", 1, deque.front);
        assertEquals("rear changes to 4", 4, deque.rear);
        assertArrayEquals(changed, deque.data);

        deque.removeLast();
        assertEquals("capacity remains the same", 5, deque.data.length);
        assertEquals("size decrease by 1", 3, deque.size);
        assertEquals("front remains the same", 1, deque.front);
        assertEquals("rear changes to 3", 3, deque.rear);
        assertArrayEquals(changed2, deque.data);
    }

    /**
     * Test if null is returned for empty deque when removeLast is called
     */
    @Test
    public void testMyDequeRemoveLastEmpty() {
        MyDeque<Integer> emptyDeque = new MyDeque<>(5);
        assertNull("Should return null since empty", emptyDeque.removeLast());
    }

    @Test
    public void testMyDequeNotSetToZero() {
        MyDeque<Integer> deque = new MyDeque<>(5);
        assertEquals(0, deque.size);
        assertEquals(5, deque.data.length);
        assertEquals(0, deque.front);
        assertEquals(0, deque.rear);

        deque.addLast(0);
        Integer[] changed = { 0, null, null, null, null };
        assertEquals(5, deque.data.length);
        assertEquals(1, deque.size);
        assertEquals(0, deque.front);
        assertEquals(0, deque.rear);
        assertArrayEquals(changed, deque.data);

        deque.addFirst(10);
        Integer[] changed2 = { 0, null, null, null, 10 };
        assertEquals(5, deque.data.length);
        assertEquals(2, deque.size);
        assertEquals(4, deque.front);
        assertEquals(0, deque.rear);
        assertArrayEquals(changed2, deque.data);

        deque.removeLast();
        Integer[] changed3 = { null, null, null, null, 10 };
        assertEquals(5, deque.data.length);
        assertEquals(1, deque.size);
        assertEquals(4, deque.front);
        assertEquals(4, deque.rear);
        assertArrayEquals(changed3, deque.data);

        deque.removeLast();
        Integer[] changed4 = { null, null, null, null, null };
        assertEquals(5, deque.data.length);
        assertEquals(0, deque.size);
        assertEquals(4, deque.front);
        //assertEquals(4, deque.rear);
        assertArrayEquals(changed4, deque.data);

        deque.addFirst(5);
        Integer[] changed5 = { null, null, null, null, 5 };
        assertEquals(5, deque.data.length);
        assertEquals(1, deque.size);
        assertEquals(4, deque.front);
        assertEquals(4, deque.rear);
        assertArrayEquals(changed5, deque.data);
    }

    //-------------------------MyStack----------------------------

    /** Test constructor with arg less than zero */
    @Test
    public void testMyStackConstructorLessThanZero() {
        assertThrows(
            "Should throw exception when arg is less than 0",
            IllegalArgumentException.class,
            () -> new MyStack<>(-1)
        );
    }

    @Test
    public void testMyStackEmptySize2() {
        MyStack<Integer> stack = new MyStack<>(5);
        Integer[] orig = { 1, 2, null, null, null };
        initDeque(stack.theStack, orig, 2, 0, 1);

        assertFalse("Call to empty should return true", stack.empty());
        assertEquals(
            "capacity should not changed",
            5,
            stack.theStack.data.length
        );
        assertEquals("size should not change", 2, stack.theStack.size);
        // assertEquals("front should not change", 0, stack.theStack.front);
        // assertEquals("rear should not change", 1, stack.theStack.rear);
        // assertEquals("peek should return 2", 2, (stack.peek()).intValue());
    }

    @Test
    public void testMyStackPushAtCapacity() {
        MyStack<Integer> stack = new MyStack<>(5);
        Integer[] orig = { 3, 4, 5, 1, 2 };
        initDeque(stack.theStack, orig, 5, 3, 2);

        stack.push(6);
        Integer[] changed = { 1, 2, 3, 4, 5, 6, null, null, null, null };
        assertEquals("capacity should be 10", 10, stack.theStack.data.length);
        assertEquals("size should be incremented", 6, stack.theStack.size);
        // assertEquals("front should be 0", 0, stack.theStack.front);
        // assertEquals("rear should be 5", 5, stack.theStack.rear);
        // assertArrayEquals(changed, stack.theStack.data);
        assertEquals("peek should give us 6", 6, (stack.peek()).intValue());
    }

    @Test
    public void testMyStackPush10() {
        MyStack<Integer> stack = new MyStack<>(0);
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        Integer[] changed = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        assertEquals("capacity should be 10", 10, stack.theStack.data.length);
        assertEquals("size should be incremented", 10, stack.theStack.size);
        // assertEquals("front should be 0", 0, stack.theStack.front);
        // assertEquals("rear should be 9", 9, stack.theStack.rear);
        // assertArrayEquals(changed, stack.theStack.data);
        assertEquals("peek should give 9", 9, (stack.peek()).intValue());
    }

    @Test
    public void testMyStackPop10() {
        MyStack<Integer> stack = new MyStack<>(0);
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 6; i++) {
            assertEquals(9 - i, (stack.pop()).intValue());
        }
        // Integer[] changed = { 0, 1, 2, 3, null, null, null, null, null, null };
        // assertArrayEquals(changed, stack.theStack.data);
        assertEquals("capacity should be 10", 10, stack.theStack.data.length);
        assertEquals("size should be 4", 4, stack.theStack.size);
        // assertEquals("front should be 0", 0, stack.theStack.front);
        // assertEquals("rear should be 3", 3, stack.theStack.rear);
    }

    @Test
    public void testMyStackPopEmpty() {
        MyStack<Integer> stack = new MyStack<>(5);
        assertNull(stack.pop());
        assertEquals(
            "capacity should not change",
            5,
            stack.theStack.data.length
        );
        assertEquals("size should not change", 0, stack.theStack.size);
        // Integer[] changed = { null, null, null, null, null };
        // assertArrayEquals(changed, stack.theStack.data);
    }

    //---------------------------MyQueue-----------------------------
    @Test
    public void testMyQueuePeek() {
        MyQueue<Integer> queue = new MyQueue<>(5);
        Integer[] orig = { 1, 2, 3, 4, 5 };
        initDeque(queue.theQueue, orig, 5, 0, 4);
        int res = queue.peek();
        if (res != 1 && res != 5) {
            fail("should give last element to join queue");
        }
    }

    @Test
    public void testMyQueueNotEmpty() {
        MyQueue<Integer> queue = new MyQueue<>(10);
        Integer[] orig = {
            1,
            2,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
        };
        initDeque(queue.theQueue, orig, 2, 0, 1);

        assertFalse("Call to empty should return true", queue.empty());
        assertEquals(
            "Capacity should not have changed",
            10,
            queue.theQueue.data.length
        );
        // assertEquals("Size should not have changed", 2, queue.theQueue.size);
        // assertEquals("Front should not have changed", 0, queue.theQueue.front);
        // assertEquals("Rear should not have changed", 1, queue.theQueue.rear);
    }

    @Test
    public void testMyQueueEnqueueAtCapacity() {
        MyQueue<Integer> queue = new MyQueue<>(1);
        Integer[] orig = { 0 };
        initDeque(queue.theQueue, orig, 1, 0, 0);
        queue.enqueue(1);
        // Integer[] changed = { 0, 1, 2, 3, 4, 1, null, null, null, null };
        // assertArrayEquals(changed, queue.theQueue.data);
        assertEquals(2, queue.theQueue.data.length);
        assertEquals(0, (queue.peek()).intValue());
    }

    @Test
    public void testMyQueueDequeue10() {
        MyQueue<Integer> queue = new MyQueue<>(10);
        Integer[] orig = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        initDeque(queue.theQueue, orig, 10, 0, 9);
        int res = queue.peek();
        if (res == 0) {
            for (int i = 0; i < orig.length; i++) {
                assertEquals(i, (queue.dequeue()).intValue());
            }
        } else if (res == 9) {
            for (int i = 9; i <= 0; i++) {
                assertEquals(i, (queue.dequeue()).intValue());
            }
        }
    }

    @Test
    public void testMyQueueDequeueWrap() {
        MyQueue<Integer> queue = new MyQueue<>(5);
        Integer[] orig = { 3, 4, 5, 1, 2 };
        initDeque(queue.theQueue, orig, 5, 3, 2);
        int res = queue.dequeue();
        if (res == 1) {
            for (int i = 1; i < orig.length; i++) {
                assertEquals(i + 1, (queue.dequeue()).intValue());
            }
        } else if (res == 5) {
            for (int i = 1; i > orig.length; i++) {
                assertEquals(5 - 1, (queue.dequeue()).intValue());
            }
        }
        assertEquals("capacity should be 5", 5, queue.theQueue.data.length);
    }
}
