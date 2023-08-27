/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Yu
 */
public class DoublyLinkedList<T> implements ListInterface<T> {
    private Node firstNode;
    private Node lastNode;
    private int numberOfEntries;  	
    

    @Override
    public boolean add(T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addToStart(T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private int locateIndex(T givenEntry){
        int index = -1;
        Node currentNode = firstNode;
        for(int i=1; i < numberOfEntries; i++){
            if(currentNode.data.equals(givenEntry)){
                index = i;
                break;
            }else{
                currentNode = currentNode.next;
            }        
        }
        return index;
    }
    
    
    @Override
    public T remove(int givenPosition) {
        T result = null;
        if(givenPosition == 1){
            result = removeFirst();
        }
        else if(givenPosition == numberOfEntries){
            result = removeLast();
        }
        else{
            if((givenPosition >= 2) && (givenPosition <= numberOfEntries-1)){
                int checkDist = numberOfEntries / 2;

                if (givenPosition <= checkDist) {                     //the case if index smaller than median
                    Node nodeBeforeIndex = firstNode;                //start from first node
                    for (int i = 2; i < givenPosition - 1; i++) {
                        nodeBeforeIndex = nodeBeforeIndex.next;     //given index =3 , stop at 2
                    }
                    result = nodeBeforeIndex.next.data;                         // save node 3 data into result
                    nodeBeforeIndex.next = nodeBeforeIndex.next.next;           // node 2 -> node 4,
                    nodeBeforeIndex.next.prev = nodeBeforeIndex;                // node 4 previous set to node 2

                } else {                                          //the case if index larger than median
                    Node nodeAfterIndex = lastNode;                  //start from last node
                    for (int i = numberOfEntries - 1; i > givenPosition + 1; i--) {
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
        
        if(contains(anEntry)){
            int index = locateIndex(anEntry);
            result = remove(index);
        }
        
        return result;
    }

    @Override
    public T removeFirst() {
        T result = null;
        
        if(firstNode != null){
            if(numberOfEntries > 1){         //if there is currently more than 1 entry in the link
                result = firstNode.data;        //data to be returned        
                firstNode = firstNode.next;     //set the first node to firstNode.next
                firstNode.prev = null;          //set firstNode.next previous node to             
            }
            else{
                firstNode = null;
                lastNode = null;
            }
             --numberOfEntries;
        }
        
        return result;
    }

    @Override
    public T removeLast() {
        T result = null;
        
        if(lastNode != null){
            if(numberOfEntries > 1){         //if there is currently more than 1 entry in the link
                result = lastNode.data;        
                lastNode = lastNode.prev;     
                lastNode.next = null;              
            }
            else{
                firstNode = null;
                lastNode = null;       
            }
            --numberOfEntries; 
        }
   
        return result;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean replaceFirst(T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean replaceLast(T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T getEntry(int givenPosition) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T getFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T getLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean contains(T anEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getNumberOfEntries() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }
    
    private class Node {
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
