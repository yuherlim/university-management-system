/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Programme;
import java.util.Iterator;

/**
 *
 * @author Yu
 */
public class ProgrammeInitializer {
    //  Method to initialize the programme.dat with a collection of hard-coded entity values
    public void initializeProgrammes() {
        ProgrammeDAO programmeDAO = new ProgrammeDAO();
        ListInterface<Programme> programmeList = new CircularDoublyLinkedList<>();
        ArrayList<String> courseListRSW = new ArrayList<>();
        ArrayList<String> courseListRST = new ArrayList<>();
        ArrayList<String> courseListRDS = new ArrayList<>();
        ArrayList<String> courseListRMM = new ArrayList<>();
        ArrayList<String> courseListRSS = new ArrayList<>();
        
        // Adding data to courseListRSW
        courseListRSW.add("AACS1234");
        courseListRSW.add("AACS2312");
        courseListRSW.add("CASD1234");
        courseListRSW.add("GASD1333");
        courseListRSW.add("ASDA1323");
        courseListRSW.add("AABS1215");
        courseListRSW.add("MPU-3123");
        courseListRSW.add("MPU-1213");
        courseListRSW.add("BSAS3124");
        courseListRSW.add("ABCD3333");
        courseListRSW.add("BACD1234");
        courseListRSW.add("DASD1234");
        courseListRSW.add("BCSA1234");
        courseListRSW.add("BDAS3251");
        courseListRSW.add("BBAA3213");

        // Adding data to courseListRST (same as RSW)
        courseListRST.add("AACS1234");
        courseListRST.add("AACS2312");
        courseListRST.add("CASD1234");
        courseListRST.add("GASD1333");
        courseListRST.add("ASDA1323");
        courseListRST.add("AABS1215");
        courseListRST.add("MPU-3123");
        courseListRST.add("MPU-1213");
        courseListRST.add("BSAS3124");
        courseListRST.add("ABCD3333");
        courseListRST.add("BACD1234");
        courseListRST.add("DASD1234");
        courseListRST.add("BCSA1234");
        courseListRST.add("BDAS3251");
        courseListRST.add("BBAA3213");

        // Adding data to courseListRDS (same as RSW)
        courseListRDS.add("AACS1234");
        courseListRDS.add("AACS2312");
        courseListRDS.add("CASD1234");
        courseListRDS.add("GASD1333");
        courseListRDS.add("ASDA1323");
        courseListRDS.add("AABS1215");
        courseListRDS.add("MPU-3123");
        courseListRDS.add("MPU-1213");
        courseListRDS.add("BSAS3124");
        courseListRDS.add("ABCD3333");
        courseListRDS.add("BACD1234");
        courseListRDS.add("DASD1234");
        courseListRDS.add("BCSA1234");
        courseListRDS.add("BDAS3251");
        courseListRDS.add("BBAA3213");

        // Adding data to courseListRMM
        courseListRMM.add("BASD1234");
        courseListRMM.add("MPU-1321");
        courseListRMM.add("BSAQ1211");
        courseListRMM.add("DACA1231");
        courseListRMM.add("BAAA1251");
        courseListRMM.add("ABCD3333");
        courseListRMM.add("DASD1234");
        courseListRMM.add("BCSA1234");
        courseListRMM.add("BDAS3251");
        courseListRMM.add("BBAA3213");

        // Adding data to courseListRSS
        courseListRSS.add("BASD1234");
        courseListRSS.add("MPU-1321");
        courseListRSS.add("BSAQ1211");
        courseListRSS.add("DACA1231");
        courseListRSS.add("BAAA1251");
        courseListRSS.add("ABCD3333");
        courseListRSS.add("BACD1234");
        courseListRSS.add("DASD1234");
        courseListRSS.add("BCSA1234");
        courseListRSS.add("BDAS3251");
        courseListRSS.add("BBAA3213");
        
        // insert mock data here.
        programmeList.add(new Programme("RSW", "Bachelor of Software Engineering (Honours)", "Faculty of Computing and Information Technology (FOCS)", "Bachelor", "Graduates of this programme will be able to develop, manage and maintain high-quality software in a systematic, controlled and efficient manner.", 3, 34600, 125, courseListRSW));
        programmeList.add(new Programme("RST", "Bachelor of Computer Science (Honours) in Interactive Software Technology", "Faculty of Computing and Information Technology (FOCS)", "Bachelor", "This programme aims to teach students the technical knowledge and skills in computer science with a further focus on the design and development of interactive software such as mobile applications, video games, simulations, virtual reality, electronic magazines, educational and training materials. ", 3, 35100, 130, courseListRST));
        programmeList.add(new Programme("RDS", "Bachelor of Computer Science (Honours) in Data Science", "Faculty of Computing and Information Technology (FOCS)", "Bachelor", "This programme is designed to train students in both computer science and data science, which prepares them well for data professionals or data scientist career pathway.", 3, 34300, 123, courseListRDS));
        programmeList.add(new Programme("RMM", "Bachelor of Science (Honours) in Management Mathematics with Computing", "Faculty of Computing and Information Technology (FOCS)", "Bachelor", "This programme is a multi-disciplinary blend with Management Mathematics as the major, Computing as the minor and Economics or Accounting Methods as the associate study.", 3, 34100, 120, courseListRMM));
        programmeList.add(new Programme("RSS", "Bachelor of Information Technology (Honours) in Software Systems Development", "Faculty of Computing and Information Technology (FOCS)", "Bachelor", "This programme produces and equips graduates with in-depth knowledge and skills that are essential to work as professionals in the software systems development and computer networking sectors.", 3, 34600, 124, courseListRSS));
        
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
//        StackInterface<String> test = new ArrayStack<>();
//        
//        test.push("Jimmy");
//        test.push("Timmy");
//        test.push("Jimmin");
//        test.push("Adam");
//        test.push("George");
//        
//        
//        
//        System.out.println("after display stack: ");
//        Iterator<String> it = test.getIterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
//        
//        System.out.println("Before display stack: ");
//        System.out.println(test);
    }
}
