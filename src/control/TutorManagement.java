/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import boundary.*;
import entity.*;
import dao.*;
import utility.*;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class TutorManagement {

    private ListInterface<Tutor> tutorList = new CircularDoublyLinkedList<>();
    private TutorDAO tutorDAO = new TutorDAO();
    private TutorManagementUI tutorUI = new TutorManagementUI();
    private TeachingAssignmentDAO taDAO = new TeachingAssignmentDAO();
    Scanner sc = new Scanner(System.in);

    public TutorManagement() {
        tutorList = tutorDAO.retrieveFromFile();
    }

    public void addNewTutor() {

        char nextOrN = 'E';
        do {
            int id = tutorList.getNumberOfEntries();
            ArrayList<String> tutorDomainList = new ArrayList<>();
            id++;
            String tutorID = "T".concat(String.format("%03d", id));
            String tutorName = tutorUI.inputTutorName();
            char tutorGender = tutorUI.inputTutorGender();
            String tutorIC = tutorUI.inputTutorIC();
            String tutorPhoneNum = tutorUI.inputTutorPhoneNum();
            String tutorEmail = tutorUI.inputTutorEmail();
            double tutorSalary = tutorUI.inputTutorSalary();
            String educationLevel = tutorUI.inputTutorEduLevel();
            tutorUI.inputTutorDomain(tutorDomainList);

            tutorList.add(new Tutor(tutorID, tutorName, tutorGender, tutorIC, tutorPhoneNum, tutorEmail, tutorSalary, educationLevel, tutorDomainList));
            tutorDAO.saveToFile(tutorList);

            nextOrN = tutorUI.nextOrExit();

        } while (nextOrN == 'N');

    }

    public void removeTutor() {

        StackInterface<Integer> indexStack = new ArrayStack<>();
        StackInterface<Tutor> deleteTutorStack = new ArrayStack<>();
        ListInterface<TeachingAssignment> teachingAssignmentList = taDAO.retrieveFromFile();

        boolean valid = true;
        int selection;
        do {
            selection = tutorUI.removeTutorMenu();
            switch (selection) {
                case 1:
                    Tutor targetTutor = findTutorSelection();

                    char confirmation = tutorUI.removeTutorConfirmation(targetTutor);
                    if (confirmation == 'Y') {

                        Iterator<TeachingAssignment> it = teachingAssignmentList.getIterator();

                        while (it.hasNext()) {
                            TeachingAssignment teachingAssignment = it.next();

                            if (teachingAssignment.getTutor().equals(targetTutor)) { //check if the tutor exist in the list
                                System.out.println("The tutor still enrolled in teaching...");
                                System.out.println("Make sure the tutor is not teaching any class before you remove it");
                                valid = false;
                                break;
                            }
                        }

                        if (valid) {
                            int tutorIndex = ((CircularDoublyLinkedList) tutorList).locatePosition(targetTutor);
                            indexStack.push(tutorIndex);
                            deleteTutorStack.push(targetTutor);
                            tutorList.remove(targetTutor);

                            System.out.println("Deleted : " + targetTutor.getTutorID());
                        }
                    }
                    break;
                case 2:
                    if (!deleteTutorStack.isEmpty()) {

                        System.out.println(deleteTutorStack.peek());

                        char undoConfirmation = tutorUI.undoRemoveTutorConfirmation();
                        if (undoConfirmation == 'Y') {
                            Tutor undoTutor = deleteTutorStack.pop();
                            int undoIndex = indexStack.pop();
                            tutorList.add(undoIndex, undoTutor);
                        } else {
                            System.out.println("Exit undo function...");
                        }

                    } else {
                        System.out.println("There is nothing can undo...");
                    }
                    break;

                case 0:
                    System.out.println("Exit remove tutor function...");
                    tutorDAO.saveToFile(tutorList);
                    break;
            }
        } while (selection != 0);
    }

    public void findTutor() {

        char nextOrN = 'E';
        do {
            Tutor targetTutor = findTutorSelection();

            if (targetTutor != null) {
                System.out.println(targetTutor);
            } else {
                System.out.println("This tutor is not existing in the list...");
            }

            nextOrN = tutorUI.nextOrExit();

        } while (nextOrN == 'N');

    }

    public void modifyTutor() {

        char nextOrN = 'E';
        tutorUI.displayAllTutors(tutorList);
        

        do {
            Tutor targetTutor = findTutorSelection();
            ListInterface<String> domains123 = targetTutor.getDomainKnowledgeList();
            if (targetTutor != null) {
                int selection = -1;
                do {
                    selection = tutorUI.modifyTutorSelection(targetTutor);
                    if (selection != 0) {
                        switch (selection) {
                            case 1:
                                targetTutor.setName(tutorUI.inputTutorName());
                                break;
                            case 2:
                                targetTutor.setPhoneNum(tutorUI.inputTutorPhoneNum());
                                break;
                            case 3:
                                targetTutor.setEmail(tutorUI.inputTutorEmail());
                                break;
                            case 4:
                                targetTutor.setEducationLevel(tutorUI.inputTutorEduLevel());
                                break;
                            case 5:
                                tutorUI.modifyTutorDomain(domains123);
                                targetTutor.setDomainKnowledgeList((ArrayList<String>) domains123);
                                
                                break;
                        }
                    } else {
                        System.out.println("Done edit...");
                    }
                } while (selection != 0);
            } else {
                System.out.println("This tutor is not existing in the list...");
            }

            System.out.println("Next or exit (N: Next, E: Exit) ： ");
            nextOrN = sc.nextLine().toUpperCase().charAt(0);

        } while (nextOrN == 'N');

        tutorDAO.saveToFile(tutorList);

    }

    public void displayTutor() {

        tutorUI.displayAllTutors(tutorList);
        MessageUI.pause();
    }

    public void filterTutor() {

        char nextOrN = 'E';
        Tutor target;
        Iterator<Tutor> it;
        String domain = null;

        do {
            int selection = tutorUI.filterTutorMenu();

            switch (selection) {

                case 1:
                    char filterGender = tutorUI.inputTutorGender();
                    target = null;
                    it = tutorList.getIterator();

                    while (it.hasNext()) {
                        target = it.next();
                        if (target.getGender() == filterGender) {
                            System.out.println(target);
                        }
                    }
                    break;

                case 2:
                    String filterEducationLevel = tutorUI.inputTutorEduLevel();
                    target = null;
                    it = tutorList.getIterator();

                    while (it.hasNext()) {
                        target = it.next();
                        if (target.getEducationLevel().equals(filterEducationLevel)) {
                            System.out.println(target);
                        }
                    }
                    break;
                case 3:
                    String filterDomain = tutorUI.inputOneDomain();
                    target = null;
                    it = tutorList.getIterator();

                    while (it.hasNext()) {
                        target = it.next();
                        if (filterDomain != null) {
                            Iterator<String> et = target.getDomainKnowledgeList().getIterator();
                            while (et.hasNext()) {
                                domain = et.next();

                                if (domain.equals(filterDomain)) {
                                    System.out.println(target);
                                }
                            }
                        }
                    }
                    break;
            }
            nextOrN = tutorUI.nextOrExit();

        } while (nextOrN == 'N');
    }

    public void generateTutorReport() {

        char nextOrN = 'E';
        int selection;
        
        do {
            ListInterface<Tutor> sorted = tutorDAO.retrieveFromFile();
            convertToArrayList(sorted);
            selection = tutorUI.tutorReportMenu();
            switch (selection) {
                case 1:
                    for (int i = 1; i < sorted.getNumberOfEntries() ; i++) {
                        int j =i;
                        while( j > 0 && sorted.getEntry(j).getName().compareTo(sorted.getEntry(j+1).getName()) > 0){
                            Tutor temp = sorted.getEntry(j);
                            sorted.replace(j, sorted.getEntry(j+1));
                            sorted.replace(j+1, temp);
                            j--;
                        }
                    }
                    break;
                case 2:
                    for (int i = 1; i < sorted.getNumberOfEntries() ; i++) {
                        int j =i;
                        while( j > 0 && sorted.getEntry(j).getSalary() > (sorted.getEntry(j+1).getSalary()) ){
                            Tutor temp = sorted.getEntry(j);
                            sorted.replace(j, sorted.getEntry(j+1));
                            sorted.replace(j+1, temp);
                            j--;
                        }
                    }
                    break;
            }
            tutorUI.displayAllTutors(sorted);
            
            if(selection == 0){
                break;
            }
            
            nextOrN = tutorUI.nextOrExit();
            
        } while (nextOrN == 'N');

    }

    public Tutor findTutorSelection() {

        int findSelection = tutorUI.searchTutorMenu();
        Iterator<Tutor> it = tutorList.getIterator();

        Tutor target;

        switch (findSelection) {
            case 1:
                String targetTutorID = tutorUI.inputTargetTutorID();
                while (it.hasNext()) {
                    target = it.next();
                    if (target.getTutorID().equals(targetTutorID)) {
                        return target;
                    }
                }
                break;
            case 2:
                String targetTutorName = tutorUI.inputTargetTutorName();
                while (it.hasNext()) {
                    target = it.next();
                    if (target.getName().equals(targetTutorName)) {
                        return target;
                    }
                }
                break;
            default:
                String targetTutorEmail = tutorUI.inputTargetTutorEmail();
                while (it.hasNext()) {
                    target = it.next();
                    if (target.getEmail().equals(targetTutorEmail)) {
                        return target;
                    }
                }
        }
        return null;
    }

    public void convertToArrayList(ListInterface<Tutor> tutorList) {
        ListInterface<Tutor> arrayList = new ArrayList<>();
        Iterator<Tutor> it = tutorList.getIterator();
        while (it.hasNext()) {
            Tutor tutor = it.next();
            arrayList.add(tutor);
        };
    }

    public static void main(String[] args) {
        TutorManagement tutorManagement = new TutorManagement();
        TutorManagementUI tutorManagementUI = new TutorManagementUI();

        int selection = -1;
        do {
            selection = tutorManagementUI.tutorMainMenu();
            switch (selection) {
                case 1:
                    tutorManagement.addNewTutor();
                    break;
                case 2:
                    tutorManagement.removeTutor();
                    break;
                case 3:
                    tutorManagement.findTutor();
                    break;
                case 4:
                    tutorManagement.modifyTutor();
                    break;
                case 5:
                    tutorManagement.displayTutor();
                    break;
                case 6:
                    tutorManagement.filterTutor();
                    break;
                case 7:
                    tutorManagement.generateTutorReport();
                    break;
                case 0:
                    System.out.println("Exit tutor management...");
                    break;
                default:
                    System.out.println("Invalid selection...");
            }
        } while (selection != 0);

    }
}
