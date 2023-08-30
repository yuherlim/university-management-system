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
    }
}
