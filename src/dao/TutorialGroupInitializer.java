/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.TutorialGroup;

/**
 *
 * @author Yu
 */
public class TutorialGroupInitializer {
    
    //  Method to initialize the tutorialGroup.dat with a collection of hard-coded entity values
    public void initializeTutorialGroups() {
        TutorialGroupDAO tutorialGroupDAO = new TutorialGroupDAO();
        ListInterface<TutorialGroup> tutorialGroupList = new CircularDoublyLinkedList<>();
        // Software Engineering (RSW)
        tutorialGroupList.add(new TutorialGroup("202201RSWG1", "G1", "RSW", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSWG2", "G2", "RSW", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSWG3", "G3", "RSW", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSWG4", "G4", "RSW", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSWG5", "G5", "RSW", "202201"));
        tutorialGroupList.add(new TutorialGroup("202207RSWG1", "G1", "RSW", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSWG2", "G2", "RSW", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSWG3", "G3", "RSW", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSWG4", "G4", "RSW", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSWG5", "G5", "RSW", "202207"));

        // Interactive Software Technology (RST)
        tutorialGroupList.add(new TutorialGroup("202201RSTG1", "G1", "RST", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSTG2", "G2", "RST", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSTG3", "G3", "RST", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSTG4", "G4", "RST", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSTG5", "G5", "RST", "202201"));
        tutorialGroupList.add(new TutorialGroup("202207RSTG1", "G1", "RST", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSTG2", "G2", "RST", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSTG3", "G3", "RST", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSTG4", "G4", "RST", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSTG5", "G5", "RST", "202207"));

        // Management Mathematics (RMM)
        tutorialGroupList.add(new TutorialGroup("202201RMMG1", "G1", "RMM", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RMMG2", "G2", "RMM", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RMMG3", "G3", "RMM", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RMMG4", "G4", "RMM", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RMMG5", "G5", "RMM", "202201"));
        tutorialGroupList.add(new TutorialGroup("202207RMMG1", "G1", "RMM", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RMMG2", "G2", "RMM", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RMMG3", "G3", "RMM", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RMMG4", "G4", "RMM", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RMMG5", "G5", "RMM", "202207"));

        // Software Systems (RSS)
        tutorialGroupList.add(new TutorialGroup("202201RSSG1", "G1", "RSS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSSG2", "G2", "RSS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSSG3", "G3", "RSS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSSG4", "G4", "RSS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RSSG5", "G5", "RSS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202207RSSG1", "G1", "RSS", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSSG2", "G2", "RSS", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSSG3", "G3", "RSS", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSSG4", "G4", "RSS", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RSSG5", "G5", "RSS", "202207"));
        

        // Data Science (RDS)
        tutorialGroupList.add(new TutorialGroup("202201RDSG1", "G1", "RDS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RDSG2", "G2", "RDS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RDSG3", "G3", "RDS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RDSG4", "G4", "RDS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202201RDSG5", "G5", "RDS", "202201"));
        tutorialGroupList.add(new TutorialGroup("202207RDSG1", "G1", "RDS", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RDSG2", "G2", "RDS", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RDSG3", "G3", "RDS", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RDSG4", "G4", "RDS", "202207"));
        tutorialGroupList.add(new TutorialGroup("202207RDSG5", "G5", "RDS", "202207"));
        
        //202301 batch (current batch) data
        // Software Engineering (RSW)
        tutorialGroupList.add(new TutorialGroup("202301RSWG1", "G1", "RSW", "202301"));
        tutorialGroupList.add(new TutorialGroup("202301RSWG2", "G2", "RSW", "202301"));
        tutorialGroupList.add(new TutorialGroup("202301RSWG3", "G3", "RSW", "202301"));
        
        // Interactive Software Technology (RST)
        tutorialGroupList.add(new TutorialGroup("202301RSTG1", "G1", "RST", "202301"));
        tutorialGroupList.add(new TutorialGroup("202301RSTG2", "G2", "RST", "202301"));
        
        // Data Science (RDS)
        tutorialGroupList.add(new TutorialGroup("202301RDSG1", "G1", "RDS", "202301"));
        
        // Management Mathematics (RMM)
        tutorialGroupList.add(new TutorialGroup("202301RMMG1", "G1", "RMM", "202301"));
        
        tutorialGroupDAO.saveToFile(tutorialGroupList);
//        return tutorialGroupList;
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeTutorialGroups() method
        TutorialGroupDAO tutorialGroupDAO = new TutorialGroupDAO();
        TutorialGroupInitializer tG = new TutorialGroupInitializer();
        
        
        System.out.println("Initialize data to tutorialGroup.dat");
        tG.initializeTutorialGroups();
        
        System.out.println("Reading data from tutorialGroup.dat");
        ListInterface<TutorialGroup> tutorialGroupList = tutorialGroupDAO.retrieveFromFile();
        
        System.out.println("\nRead Tutorial groups:\n" + tutorialGroupList);
    }
}
