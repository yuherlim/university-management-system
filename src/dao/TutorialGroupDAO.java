/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.TutorialGroup;
import java.io.*;

/**
 *
 * @author Yu
 */
public class TutorialGroupDAO {

    private final String fileName = "tutorialGroup.dat"; // For security and maintainability, should not have filename hardcoded here.

    public void saveToFile(ListInterface<TutorialGroup> tutorialGroupList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(tutorialGroupList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<TutorialGroup> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<TutorialGroup> tutorialGroupList = new CircularDoublyLinkedList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            tutorialGroupList = (CircularDoublyLinkedList<TutorialGroup>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return tutorialGroupList;
        }
    }
}
