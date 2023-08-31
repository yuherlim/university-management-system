/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author Yu
 */
public class CircularDoublyLinkedList<T> implements ListInterface<T>, Serializable {

    private Node firstNode;
    private int numberOfEntries;

    public CircularDoublyLinkedList() {
        firstNode = null;
        numberOfEntries = 0;
    }

    @Override
    public boolean add(T newEntry) {
        Node newNode = new Node(newEntry);

        if (isEmpty()) {
            firstNode = newNode;
            firstNode.prev = firstNode.next = newNode;
        } else { // adding entry into list with more than 1 element
            newNode.prev = firstNode.prev;  // link new node to the last node.
            firstNode.prev.next = newNode;  // link last node to the new node.
            firstNode.prev = newNode;  // update first node to link to new node
            newNode.next = firstNode;  // update new node to link to first node.
        }

        numberOfEntries++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
            Node newNode = new Node(newEntry);

            if (isEmpty() || (newPosition == 1)) { // add to beginning of the list
                addToStart(newEntry);
            } else if (newPosition == numberOfEntries + 1) { // adding an entry at the end of the list.
                add(newEntry);
            } else {
                int median = numberOfEntries / 2;
                Node nodeAtPosition;
                if (newPosition <= median) {  //the case if index smaller than or equal to median
                    nodeAtPosition = firstNode;  //start from first node

                    for (int i = 1; i < newPosition; i++) {  // traverse linked list until pointer pointing to the node at given position.
                        nodeAtPosition = nodeAtPosition.next; // while not pointing at the node at given position.
                    }

                } else {                      //the case if index larger than median
                    nodeAtPosition = firstNode.prev; //start from last node

                    for (int i = numberOfEntries; i > newPosition; i--) {  // traverse linked list until pointer pointing to the node at given position.
                        nodeAtPosition = nodeAtPosition.prev; // while not pointing at the node at given position.
                    }

                }
                //insert new node at new position.
                nodeAtPosition.prev.next = newNode;
                newNode.prev = nodeAtPosition.prev;
                nodeAtPosition.prev = newNode;
                newNode.next = nodeAtPosition;
                numberOfEntries++;
            }
        } else {
            isSuccessful = false;
        }
        return isSuccessful;
    }

    @Override
    public boolean addToStart(T newEntry) {
        Node newNode = new Node(newEntry);

        add(newEntry);
        firstNode = newNode; // update the new node added to be the first node.

        return true;
    }

    private int locateIndex(T givenEntry) {
        int index = -1;
        Node currentNode = firstNode;
        for (int i = 1; i <= numberOfEntries; i++) {
            if (currentNode.data.equals(givenEntry)) {
                index = i;
                break;
            } else {
                currentNode = currentNode.next;
            }
        }
        return index;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;
        if (givenPosition == 1) {
            result = removeFirst();
        } else if (givenPosition == numberOfEntries) {
            result = removeLast();
        } else {
            if ((givenPosition >= 2) && (givenPosition <= numberOfEntries - 1)) {
                int checkDist = numberOfEntries / 2;

                if (givenPosition <= checkDist) {                     //the case if index smaller than median
                    Node nodeBeforeIndex = firstNode;                //start from first node
                    for (int i = 1; i < givenPosition - 1; i++) {
                        nodeBeforeIndex = nodeBeforeIndex.next;     //given index =3 , stop at 2
                    }
                    result = nodeBeforeIndex.next.data;                         // save node 3 data into result
                    nodeBeforeIndex.next = nodeBeforeIndex.next.next;           // node 2 -> node 4,
                    nodeBeforeIndex.next.prev = nodeBeforeIndex;                // node 4 previous set to node 2

                } else {                                          //the case if index larger than median
                    Node nodeAfterIndex = firstNode.prev;                  //start from last node
                    for (int i = numberOfEntries; i > givenPosition + 1; i--) {
                        nodeAfterIndex = nodeAfterIndex.prev;      //given index = 3, stop at 4
                    }
                    result = nodeAfterIndex.prev.data;                          //save node 3 data into result
                    nodeAfterIndex.prev = nodeAfterIndex.prev.prev;             //node 4 -> node 2
                    nodeAfterIndex.prev.next = nodeAfterIndex;                  //node 2 next set to node 4

                }
                --numberOfEntries;
            }
        }
        return result;
    }

    @Override
    public T remove(T anEntry) {
        T result = null;

        if (contains(anEntry)) {
            int index = locateIndex(anEntry);
            result = remove(index);
        }

        return result;
    }

    @Override
    public T removeFirst() {
        T result = null;

        if (!isEmpty()) {
            if (numberOfEntries > 1) {         //if there is currently more than 1 entry in the link
                result = firstNode.data;        //data to be returned        
                firstNode.next.prev = firstNode.prev;
                firstNode = firstNode.next;
                firstNode.prev.next = firstNode;
            } else {
                firstNode = firstNode.prev = firstNode.next = null;
            }
            --numberOfEntries;
        }

        return result;
    }

    @Override
    public T removeLast() {
        T result = null;

        if (!isEmpty()) {
            if (numberOfEntries > 1) {         //if there is currently more than 1 entry in the link
                result = firstNode.prev.data;
                firstNode.prev = firstNode.prev.prev;
                firstNode.prev.next = firstNode;
            } else {
                firstNode = firstNode.prev = firstNode.next = null;
            }
            --numberOfEntries;
        }

        return result;
    }

    @Override
    public void clear() {
        firstNode.prev.next = null;   //clear the previous node pointer to the firstNode
        firstNode.next.prev = null;   //clear the next node pointer to the firstNode

        Node currentNode = firstNode.next;  //set the second node to the currentNode
        firstNode = null; //set the firstNode to null

        for (int i = 2; i <= numberOfEntries - 1; i++) {   //use for loop to clear all the pointer
            currentNode.next.prev = null;
            currentNode = currentNode.next;
        }

        currentNode = null;                 //remove the reference to the last node.
        numberOfEntries = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {

        boolean isSuccessful = false;

        if (givenPosition == 1) {                 //if the position is 1, then call the replaceFirst method
            replaceFirst(newEntry);
        } else if (givenPosition == numberOfEntries) {   //if the position is the last, call the replaceLast method
            replaceLast(newEntry);
        } else if (givenPosition >= 2 && givenPosition <= numberOfEntries - 1) {  //if the position between 2 to numberOfEntries-1 

            int median = numberOfEntries / 2;
            Node nodeAtPosition;
            if (givenPosition <= median) {  //the case if index smaller than or equal to median
                nodeAtPosition = firstNode;  //start from first node

                for (int i = 1; i < givenPosition; i++) {  // traverse linked list until pointer pointing to the node at given position.
                    nodeAtPosition = nodeAtPosition.next; // while not pointing at the node at given position.
                }

            } else {                      //the case if index larger than median
                nodeAtPosition = firstNode.prev; //start from last node

                for (int i = numberOfEntries; i > givenPosition; i--) {  // traverse linked list until pointer pointing to the node at given position.
                    nodeAtPosition = nodeAtPosition.prev; // while not pointing at the node at given position.
                }

            }
            nodeAtPosition.data = newEntry;
            isSuccessful = true;
        }
        return isSuccessful;
    }

    @Override
    public boolean replaceFirst(T newEntry) {
        boolean isSuccessful = false;

        if (!isEmpty()) {            //check the list is not empty
            firstNode.data = newEntry;    //set the data into the currentNode
            isSuccessful = true;
        }

        return isSuccessful;
    }

    @Override
    public boolean replaceLast(T newEntry) {
        boolean isSuccessful = false;

        if (!isEmpty()) {
            firstNode.prev.data = newEntry;
            isSuccessful = true;
        }

        return isSuccessful;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if (givenPosition == 1) {
            result = getFirst();
        } else if (givenPosition == numberOfEntries) {
            result = getLast();
        } else if (givenPosition >= 2 && givenPosition <= numberOfEntries - 1) {
            Node currentNode = firstNode.next;
            for (int i = 2; i < givenPosition; i++) {
                currentNode = currentNode.next;
            }
            result = currentNode.data;
        }

        return result;
    }

    @Override
    public T getEntry(T anObject) {
        T result = null;

        if (!isEmpty() && contains(anObject)) {
            int index = locateIndex(anObject);
            result = getEntry(index);
        }

        return result;
    }

    @Override
    public T getFirst() {
        T result = null;
        if (!isEmpty()) {
            result = firstNode.data;
        }
        return result;
    }

    @Override
    public T getLast() {
        T result = null;
        if (!isEmpty()) {
            result = firstNode.prev.data;
        }
        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean isSuccessful = false;

        if (!isEmpty()) {
            Node currentNode = firstNode;
            for (int i = 1; i < numberOfEntries + 1; i++) {
                if (currentNode.data.equals(anEntry)) {
                    isSuccessful = true;
                    break;
                } else {
                    currentNode = currentNode.next;
                }
            }
        }
        return isSuccessful;
    }

    @Override
    public int getNumberOfEntries() { //jiajie
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return (firstNode == null) && (numberOfEntries == 0);
    }

    @Override
    public boolean isFull() {
        return false;
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
        return new CircularDoublyLinkedListIterator();
    }

    private class CircularDoublyLinkedListIterator implements Iterator<T> {

        private Node currentNode = firstNode;
        private int currentCount = 0;

        @Override
        public boolean hasNext() {
            return currentCount < numberOfEntries;
        }

        @Override
        public T next() {
            T currentElement = null;
            if (hasNext()) {
                currentElement = currentNode.data;
                currentNode = currentNode.next;
                currentCount++;
            }
            return currentElement;
        }

    }

    private class Node implements Serializable {

        private T data;
        private Node next;
        private Node prev;

        private Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }

        private Node(T data, Node prev, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
