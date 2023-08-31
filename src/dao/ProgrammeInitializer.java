/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Programme;

/**
 *
 * @author Yu
 */
public class ProgrammeInitializer {
    //  Method to initialize the programme.dat with a collection of hard-coded entity values
    public void initializeProgrammes() {
        ProgrammeDAO programmeDAO = new ProgrammeDAO();
        ListInterface<Programme> programmeList = new CircularDoublyLinkedList<>();
        
        
        programmeDAO.saveToFile(programmeList);
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeProgrammes() method
        ProgrammeDAO programmeDAO = new ProgrammeDAO();
        ProgrammeInitializer programmesInit = new ProgrammeInitializer();
        
        
        System.out.println("Initialize data to programme.dat");
        programmesInit.initializeProgrammes();
        
        System.out.println("Reading data from programme.dat");
        ListInterface<Programme> programmeList = programmeDAO.retrieveFromFile();
        
        System.out.println("\nRead programmes :\n" + programmeList);
    }
}
