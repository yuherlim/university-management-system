package adt;

/**
 * @author Frank M. Carrano
 * @version 2.0
 */
import java.io.Serializable;
import java.util.Iterator;

public class ArrayList<T> implements ListInterface<T>, Serializable {

    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        numberOfEntries = 0;
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(T newEntry) {
        if (isArrayFull()) {
            doubleArray();
        }

        array[numberOfEntries] = newEntry;
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
            if (isArrayFull()) {
                doubleArray();
            }
            makeRoom(newPosition);
            array[newPosition - 1] = newEntry;
            numberOfEntries++;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            result = array[givenPosition - 1];

            if (givenPosition < numberOfEntries) {
                removeGap(givenPosition);
            }

            numberOfEntries--;
        }

        return result;
    }

    @Override
    public void clear() {
        numberOfEntries = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            array[givenPosition - 1] = newEntry;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            result = array[givenPosition - 1];
        }

        return result;
    }
    
    @Override
    public T getEntry(T anObject) {
        T result = null;
        int objIndex = indexOf(anObject);
        if(!isEmpty()) {
            if (objIndex != -1) {
                result = array[objIndex];
            }
        }
        return result;
    }
    
    private int indexOf(T anElement) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(anElement)) {
                return i; // anElement exists in the set
            }
        }
        return -1; // anElement does not exist in the set   
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int index = 0; !found && (index < numberOfEntries); index++) {
            if (anEntry.equals(array[index])) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    private void doubleArray() {
        T[] oldArray = array;
        array = (T[]) new Object[oldArray.length * 2];
        for (int i = 0; i < oldArray.length; i++) {
            array[i] = oldArray[i];
        }
    }

    private boolean isArrayFull() {
        return numberOfEntries == array.length;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < numberOfEntries; ++index) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

    /**
     * Task: Makes room for a new entry at newPosition. Precondition: 1 <=
     * newPosition <= numberOfEntries + 1; numberOfEntries is array's
     * numberOfEntries before addition.
     */
    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = numberOfEntries - 1;

        // move each entry to next higher index, starting at end of
        // array and continuing until the entry at newIndex is moved
        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    /**
     * Task: Shifts entries that are beyond the entry to be removed to the next
     * lower position. Precondition: array is not empty; 1 <= givenPosition <
     * numberOfEntries; numberOfEntries is array's numberOfEntries before
     * removal.
     */
    private void removeGap(int givenPosition) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        int removedIndex = givenPosition - 1;
        int lastIndex = numberOfEntries - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    @Override
    public boolean addToStart(T newEntry) {
        if (isArrayFull()) {
            doubleArray();
        }
        
        makeRoom(1);
        array[0] = newEntry;
        numberOfEntries++;
        return true;
    }

    @Override
    public T remove(T anEntry) {
        T result = null;

        if (contains(anEntry)) {
            int i = indexOf(anEntry);
            result = array[i];
            removeGap(i + 1);
            --numberOfEntries;
        }
        return result;
    }

    @Override
    public T removeFirst() {
        T result = array[0];
        removeGap(1);
        --numberOfEntries;
        return result;
    }

    @Override
    public T removeLast() {
        T result = array[numberOfEntries - 1];
        removeGap(numberOfEntries);
        --numberOfEntries;
        return result;
    }

    @Override
    public boolean replaceFirst(T newEntry) {
        boolean isSuccessful = false;

        if (!isEmpty()) {
            array[0] = newEntry;
            isSuccessful = true;
        }
        return isSuccessful;
    }

    @Override
    public boolean replaceLast(T newEntry) {
        boolean isSuccessful = false;

        if (!isEmpty()) {
            array[numberOfEntries] = newEntry;
            isSuccessful = true;
        }
        return isSuccessful;
    }

    @Override
    public T getFirst() {
        T result = null;

        if (!isEmpty()) {
            result = array[0];
        }

        return result;
    }

    @Override
    public T getLast() {
        T result = null;

        if (!isEmpty()) {
            result = array[numberOfEntries - 1];
        }

        return result;
    }

    public Iterator<T> getIterator() {
        return new ArrayListIterator();
    }

   
    private class ArrayListIterator implements Iterator<T> {
        int index = 0;
        
        @Override
        public boolean hasNext() {
            return index < numberOfEntries;
        }

        @Override
        public T next() {
            T currentElement = null;
            if (hasNext()) {
                currentElement = array[index];
                index++;
            }
            return currentElement;
        }
       }
}
