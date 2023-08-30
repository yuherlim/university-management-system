/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;

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
        
        testList.clear();
        
        System.out.println(testList.getFirst());
        for (int i = 1; i <= testList.getNumberOfEntries(); i++) {
            System.out.println(testList.getEntry(i));
        }
    }
}
