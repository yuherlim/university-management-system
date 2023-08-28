/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Yu
 */
public class CircularDoublyLinkedList<T> implements ListInterface<T> {
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
            firstNode = firstNode.prev = firstNode.next = newNode;
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
            } else if ((numberOfEntries == 1) && (newPosition == 2) || newPosition == numberOfEntries + 1) { // adding a second entry, or when adding an entry at the end of the list.
                add(newEntry);
            }
            else {
//                Node nodeAtPosition = firstNode;
//                for (int i = 1; i < newPosition; i++) {  // traverse linked list until pointer pointing to the node at given position.
//                    nodeAtPosition = nodeAtPosition.next; // while not pointing at the node at given position.
//                }
//                //insert new node at new position.
//                nodeAtPosition.prev.next = newNode;
//                newNode.prev = nodeAtPosition.prev;
//                nodeAtPosition.prev = newNode;
//                newNode.next = nodeAtPosition;
//                numberOfEntries++;
                int checkDist = numberOfEntries / 2;

                if (newPosition <= checkDist) {                     //the case if index smaller than median
                    Node nodeBeforeIndex = firstNode;                //start from first node
                    for (int i = 2; i < givenPosition - 1; i++) {
                        nodeBeforeIndex = nodeBeforeIndex.next;     //given index =3 , stop at 2
                    }
                    result = nodeBeforeIndex.next.data;                         // save node 3 data into result
                    nodeBeforeIndex.next = nodeBeforeIndex.next.next;           // node 2 -> node 4,
                    nodeBeforeIndex.next.prev = nodeBeforeIndex;                // node 4 previous set to node 2

                } else {                                          //the case if index larger than median
                    Node nodeAfterIndex = firstNode.prev;                  //start from last node
                    for (int i = numberOfEntries - 1; i > givenPosition + 1; i--) {
                        nodeAfterIndex = nodeAfterIndex.prev;      //given index = 3, stop at 4
                    }
                    result = nodeAfterIndex.prev.data;                          //save node 3 data into result
                    nodeAfterIndex.prev = nodeAfterIndex.prev.prev;             //node 4 -> node 2
                    nodeAfterIndex.prev.next = nodeAfterIndex;                  //node 2 next set to node 4

                }
                --numberOfEntries;
            }
        } else {
            isSuccessful = false;
        }
        return isSuccessful;
    }

    @Override
    public boolean addToStart(T newEntry) {
        Node newNode = new Node(newEntry);
        
        if (numberOfEntries == 1) { // add to start when there is already one entry.
            firstNode.prev = firstNode.next = newNode;
            newNode.next = newNode.prev = firstNode;
        } else if (numberOfEntries > 1) { // add to start when there is more than 1 entry.
            firstNode.prev.next = newNode;
            newNode.prev = firstNode.prev;
            newNode.next = firstNode;
            firstNode.prev = newNode;
        }
        firstNode = newNode;
        numberOfEntries++;
        return true;
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
        
        if(contains(anEntry)){
            int index = locateIndex(anEntry);
            result = remove(index);
        }
        
        return result;
    }

    @Override
    public T removeFirst() {
        T result = null;
        
        if(!isEmpty()){
            if(numberOfEntries > 1){         //if there is currently more than 1 entry in the link
                result = firstNode.data;        //data to be returned        
                firstNode = firstNode.next;     //set the first node to firstNode.next
                firstNode.prev = null;          //set firstNode.next previous node to             
            }
            else{
                firstNode = null;  
            }
             --numberOfEntries;
        }
        
        return result;
    }

    @Override
    public T removeLast() {
        T result = null;
        
        if(!isEmpty()){
            if(numberOfEntries > 1){         //if there is currently more than 1 entry in the link
                result = firstNode.prev.data;        
                firstNode.prev = firstNode.prev.prev;     
                firstNode.prev.next = null;              
            }
            else{
                firstNode = null;     
            }
            --numberOfEntries; 
        }
   
        return result;
    }

    @Override
    public void clear() {
        firstNode = null;    //set the firstnode to null, then all the following node will dereferences
        numberOfEntries = 0;
        
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        
        boolean isSuccessful = false;
        
        if(givenPosition == 1){                 //if the position is 1, then call the replaceFirst method
            replaceFirst(newEntry);
        }else if(givenPosition == numberOfEntries){   //if the position is the last, call the replaceLast method
            replaceLast(newEntry);
        }else if(givenPosition >= 2 && givenPosition <= numberOfEntries-1){  //if the position between 2 to numberOfEntries-1 
            Node currentNode = firstNode.next;
            for(int i = 2; i < givenPosition ; i++){   //for loop to arrive the givenPosition
                currentNode = currentNode.next;
            }
            currentNode.data = newEntry;
            isSuccessful = true;
    }
        return isSuccessful;
    }

    @Override
    public boolean replaceFirst(T newEntry) {
        boolean isSuccessful = false;
        
        if(!isEmpty()){            //check the list is not empty
            firstNode.data = newEntry;    //set the data into the currentNode
            isSuccessful = true;
        }
        
        return isSuccessful;
    }

    @Override
    public boolean replaceLast(T newEntry) {
        boolean isSuccessful = false;
        
        if(!isEmpty()){
            Node currentNode = firstNode;
            for(int i = 1 ; i < numberOfEntries; i++){   //for lopp to arrive the last position of the list
                currentNode = currentNode.next;
            }
            currentNode.data = newEntry;
            isSuccessful = true;
        }
        
        return isSuccessful;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;
        
        if(givenPosition == 1){
            result = getFirst();
        }else if(givenPosition == numberOfEntries){
            result = getLast();
        }else  if(givenPosition >= 2 && givenPosition <= numberOfEntries-1){
            Node currentNode = firstNode.next;
            for(int i = 2; i < givenPosition ; i++){
                currentNode = currentNode.next;
            }
            result = currentNode.data;
        }
        
        return result;
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
    public boolean contains(T anEntry) { //jiajie //macam same with getIndex
        boolean isSuccessful = false;
        
        if(!isEmpty()){
            Node currentNode = firstNode;
            for(int i=1; i < numberOfEntries; i++){
            if(currentNode.data.equals(anEntry)){
                isSuccessful = true;
                break;
            }else{
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
        return firstNode == null & numberOfEntries == 0;
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
