/*
 * Name: Farris Danish
 * PID: A17401247
 * Email: fbinsyahrilakmar@ucsd.edu
 * Sources used: Write-up
 */

/**
 * This class contains the implementation of the Deque ADT for the CSE12 spring
 * 2023
 */
public class MyDeque<E> implements DequeInterface<E> {

    /* Constants to avoid magic number */
    private static final int INCREMENT_RATE = 2;
    private static final String LESS_THAN_ZERO_MESSAGE =
        "Argument cannot be less than zero";
    private static final int INITIAL_CAPACITY = 10;
    private static final String NULL_ARG_MESSAGE = "Argument cannot be null";

    /* Instance variables */
    Object[] data;
    int size, rear, front;

    /* Class methods */
    /**
     * The constructor for MyDeque object. Initialize with array of capacity
     * initial capacity, size 0, front and rear index 0.
     *
     * @param initialCapacity the inital array capacity
     */
    public MyDeque(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(LESS_THAN_ZERO_MESSAGE);
        }
        this.data = new Object[initialCapacity];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    /**
     * This method returns the size of the deque
     *
     * @return the size of the MyDeque object
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * This method expands the capacity of the underlying array.
     * If the capacity is initially 0, expand the capacity to 10.
     * Otherwise double the capacity.
     */
    @Override
    public void expandCapacity() {
        Object[] temp = this.data;
        int oldLength = this.data.length;

        if (this.data.length == 0) {
            this.data = new Object[INITIAL_CAPACITY];
        } else {
            this.data = new Object[temp.length * INCREMENT_RATE];

            for (int i = 0; i < temp.length; i++) {
                this.data[i] = temp[(i + this.front + oldLength) % oldLength];
            }
            this.front = 0;
            this.rear = size() - 1;
        }

        return;
    }

    /**
     * This method adds element at the front of the MyDeque object
     *
     * @param element the element to add
     */
    @Override
    public void addFirst(E element) {
        if (element == null) {
            throw new NullPointerException(NULL_ARG_MESSAGE);
        }
        if (size() == this.data.length) expandCapacity();
        if (size() == 0) {
            this.data[this.front] = element;
            this.size++;
            return;
        }
        int index = (this.front - 1 + this.data.length) % this.data.length;
        this.data[index] = element;
        this.front = index;
        this.size++;
        return;
    }

    /**
     * This method adds element to the end of the MyDeque object
     *
     * @param element the element to add
     */
    @Override
    public void addLast(E element) {
        if (element == null) {
            throw new NullPointerException(NULL_ARG_MESSAGE);
        }
        if (size() == this.data.length) expandCapacity();
        if (size() == 0) {
            this.data[this.rear] = element;
            this.size++;
            return;
        }
        int index = (this.rear + 1 + this.data.length) % this.data.length;
        this.data[index] = element;
        this.rear = index;
        this.size++;
        return;
    }

    /**
     * This method remove element at the front of the MyDeque object
     *
     * @return the removed element, null if empty
     */
    @Override
    @SuppressWarnings("unchecked")
    public E removeFirst() {
        if (size() == 0) return null;
        Object temp = this.data[this.front];
        this.data[this.front] = null;
        this.size--;
        if (!(this.size == 0)) {
            this.front = (this.front + 1 + this.data.length) % this.data.length;
        }
        return (E) temp;
    }

    /**
     * This method remove element at the rear of the MyDeque object
     * @return the removed element, null if empty
     */
    @Override
    @SuppressWarnings("unchecked")
    public E removeLast() {
        if (size() == 0) return null;
        Object temp = this.data[this.rear];
        this.data[this.rear] = null;
        this.size--;
        if (!(this.size == 0)) {
            this.rear = (this.rear - 1 + this.data.length) % this.data.length;
        }
        return (E) temp;
    }

    /**
     * This method returns the first element in the MyDeque object
     *
     * @return front element
     */
    @Override
    @SuppressWarnings("unchecked")
    public E peekFirst() {
        if (size() == 0) return null;
        return (E) this.data[this.front];
    }

    /**
     * This method returns the last element in the MyDeque object
     *
     * @return rear element
     */
    @Override
    @SuppressWarnings("unchecked")
    public E peekLast() {
        if (size() == 0) return null;
        return (E) this.data[this.rear];
    }
}
