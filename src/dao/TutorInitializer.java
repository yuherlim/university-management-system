/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.ArrayList;
import adt.CircularDoublyLinkedList;
import adt.ListInterface;
import entity.Tutor;

/**
 *
 * @author ASUS
 */
public class TutorInitializer {

    public void initializeTutorList() {

        TutorDAO tutorDAO = new TutorDAO();
        ListInterface<Tutor> tutorList = new CircularDoublyLinkedList<>();
        ArrayList<String> domainList1 = new ArrayList<>();
        ArrayList<String> domainList2 = new ArrayList<>();
        ArrayList<String> domainList3 = new ArrayList<>();

        domainList1.add("Accounting");
        domainList1.add("Add-Math");
        domainList1.add("Biology");
        domainList1.add("Chemistry");

        domainList2.add("Biology");
        domainList2.add("Chemistry");

        domainList3.add("Add-Math");
        domainList3.add("Physics");

        tutorList.add(new Tutor("T001", "Kate", 'F', "830221015382", "019-3456791", "kate@gmail.com", 4900.00, "phD", domainList2));
        tutorList.add(new Tutor("T002", "Bob", 'M', "930201011387", "012-3456782", "bob@gmail.com", 2900.00, "phD", domainList2));
        tutorList.add(new Tutor("T003", "Charlie", 'M', "980201011383", "012-3456783", "charlie@gmail.com", 3300.00, "Bachelor's Degree", domainList2));
        tutorList.add(new Tutor("T004", "David", 'M', "880214011335", "011-3456784", "david@gmail.com", 2800.00, "phD", domainList3));
        tutorList.add(new Tutor("T005", "Sophia", 'F', "930816011234", "012-3456799", "sophia@gmail.com", 4100.00, "phD", domainList1));
        
        tutorList.add(new Tutor("T006", "Ryan", 'M', "921102044227", "015-3456798", "ryan@gmail.com", 3800.00, "Master's Degree", domainList2));
        tutorList.add(new Tutor("T007", "Grace", 'F', "940402015626", "012-3456787", "grace@gmail.com", 3700.00, "Master's Degree", domainList1));
        tutorList.add(new Tutor("T008", "Hannah", 'F', "910423061226", "018-3456788", "hannah@gmail.com", 4900.00, "Bachelor's Degree", domainList2));
        tutorList.add(new Tutor("T009", "Ivy", 'F', "810605091388", "015-3456789", "ivy@gmail.com", 5000.00, "phD", domainList2));
        tutorList.add(new Tutor("T010", "Tyler", 'M', "980602037687", "016-3456800", "tyler@gmail.com", 3000.00, "Diploma", domainList3));
        
        tutorList.add(new Tutor("T011", "Alice", 'F', "921202011466", "010-3456781", "alice@gmail.com", 2500.00, "Diploma", domainList1));
        tutorList.add(new Tutor("T012", "Liam", 'M', "920605011387", "016-3456792", "liam@gmail.com", 5000.00, "Bachelor's Degree", domainList1));
        tutorList.add(new Tutor("T013", "Mia", 'F', "920207051384", "011-3456793", "mia@gmail.com", 4200.00, "Diploma", domainList2));
        tutorList.add(new Tutor("T014", "Jack", 'M', "990610017685", "012-3456790", "jack@gmail.com", 4200.00, "Diploma", domainList1));
        tutorList.add(new Tutor("T015", "Olivia", 'F', "930621015386", "012-3456795", "olivia@gmail.com", 4900.00, "phD", domainList2));
        
        tutorList.add(new Tutor("T016", "Peter", 'M', "920423014521", "013-3456796", "peter@gmail.com", 5000.00, "Bachelor's Degree", domainList3));
        tutorList.add(new Tutor("T017", "Quinn", 'F', "931002026552", "014-3456797", "quinn@gmail.com", 4200.00, "Master's Degree", domainList1));
        tutorList.add(new Tutor("T018", "Frank", 'M', "810402015635", "013-3456786", "frank@gmail.com", 4800.00, "Master's Degree", domainList3));
        tutorList.add(new Tutor("T019", "Eve", 'F', "930601011388", "012-3456785", "eve@gmail.com", 4400.00, "Master's Degree", domainList1));
        tutorList.add(new Tutor("T020", "Noah", 'M', "930616017689", "012-3456794", "noah@gmail.com", 3800.00, "Diploma", domainList3));


        tutorDAO.saveToFile(tutorList);

    }
    
    public static void main(String[] args) {
        
        // To illustrate how to use the initializeTutorialGroups() method
        TutorDAO tutorDAO = new TutorDAO();
        TutorInitializer t = new TutorInitializer();
        
        
        System.out.println("Initialize data to tutor.dat");
        t.initializeTutorList();
        
        System.out.println("Reading data from tutor.dat");
        ListInterface<Tutor> tutorList = tutorDAO.retrieveFromFile();
        
        System.out.print("\nRead Tutor lists:\n" + tutorList);
    }
}
