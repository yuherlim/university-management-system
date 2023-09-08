package adt;

import java.util.Iterator;

/**
 * ArrayStack.java A class that implements the ADT Stack using an array.
 * Adopted from Frank M. Carrano
 * @author Kho Ka Jie, Lim Yu Her, Ong Cheng Leong, Sia Yeong Sheng
 * @version 2.0
 * @param <T>
 */
public class ArrayStack<T> implements StackInterface<T> {

    private T[] array;
    private int topIndex; // index of top entry
    private static final int DEFAULT_CAPACITY = 50;

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayStack(int initialCapacity) {
        array = (T[]) new Object[initialCapacity];
        topIndex = -1;
    }

    @Override
    public void push(T newEntry) {
        topIndex++;

        if (topIndex < array.length) {
            array[topIndex] = newEntry;
        }
    }

    @Override
    public T peek() {
        T top = null;

        if (!isEmpty()) {
            top = array[topIndex];
        }

        return top;
    }

    @Override
    public T pop() {
        T top = null;

        if (!isEmpty()) {
            top = array[topIndex];
            array[topIndex] = null;
            topIndex--;

        } // end if

        return top;
    }

    @Override
    public boolean isEmpty() {
        return topIndex < 0;
    }

    public void clear() {
        topIndex = -1;
    }

    @Override
    public String toString() {
        String outputStr = "";
        Iterator<T> it = getIterator();
        while (it.hasNext()) {
            outputStr += it.next() + "\n";
        }
        return outputStr;
    }

    @Override
    public Iterator<T> getIterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<T> {

        int currentIndex = topIndex;

        @Override
        public boolean hasNext() {
            return currentIndex != -1;
        }

        @Override
        public T next() {
            T currentElement = null;
            if (hasNext()) {
                currentElement = array[currentIndex];
                currentIndex--;
            }
            return currentElement;
        }

    }

}
