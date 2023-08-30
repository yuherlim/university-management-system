/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import java.util.Iterator;

/**
 *
 * @author Yu
 */
public class ProgrammeManagement {

    public static void main(String[] args) {
        ListInterface<Integer> testList = new CircularDoublyLinkedList<>();
        ListInterface<Integer> testArrayList = new ArrayList<>();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);
        testList.add(5);
        testList.add(4);
        testList.add(3);
        testList.add(1);
        
//        testList.clear();
        
        System.out.println(testList);
        
        Iterator<Integer> it = testList.getIterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
        testList.remove(5);
        System.out.println("");
        
        it = testList.getIterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
        
        testArrayList.add(1);
        testArrayList.add(2);
        testArrayList.add(3);
        testArrayList.add(4);
        testArrayList.add(5);
        
        System.out.println("\n\n\n\nInteger array list: \n");
        
        Iterator<Integer> alit = testArrayList.getIterator();
        while(alit.hasNext()) {
            System.out.println(alit.next());
        }
        
        
        System.out.println("\n\n");
        
        alit = testArrayList.getIterator();
        while(alit.hasNext()) {
            System.out.println(alit.next());
        }
    }
}
