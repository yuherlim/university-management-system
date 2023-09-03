/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Tutor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author kho ka jie
 */
public class TutorDAO {
    
    private String fileName = "Tutor.dat";

    public void saveToFile(ListInterface<Tutor> tutorList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(tutorList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<Tutor> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<Tutor> tutorList = new CircularDoublyLinkedList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            tutorList = (CircularDoublyLinkedList<Tutor>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return tutorList;
        }
    }
}