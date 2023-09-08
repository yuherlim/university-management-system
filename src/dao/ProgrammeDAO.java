/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Programme;
import java.io.*;

/**
 *
 * @author Lim Yu Her
 */
public class ProgrammeDAO {
    private final String fileName = "programme.dat"; // For security and maintainability, should not have filename hardcoded here.

    public void saveToFile(ListInterface<Programme> programmeList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(programmeList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<Programme> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<Programme> programmeList = new CircularDoublyLinkedList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            programmeList = (CircularDoublyLinkedList<Programme>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return programmeList;
        }
    }
}
