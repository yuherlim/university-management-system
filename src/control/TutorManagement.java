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
 * @author Kho Ka Jie
 */
public class TutorManagement {

    StackInterface<Integer> indexStack = new ArrayStack<>();
    StackInterface<Tutor> deleteTutorStack = new ArrayStack<>();

    private ListInterface<Tutor> tutorList = new CircularDoublyLinkedList<>();

    private TutorDAO tutorDAO = new TutorDAO();
    private TutorManagementUI tutorUI = new TutorManagementUI();
    private TeachingAssignmentDAO taDAO = new TeachingAssignmentDAO();
    Scanner sc = new Scanner(System.in);

    public TutorManagement() {
        tutorList = tutorDAO.retrieveFromFile();
    }

    public void addNewTutor(){

        tutorUI.addTutorHeader();
        
        char nextOrExit = 'E';
        do {
            int id;
            if (tutorList.isEmpty()) {
                id = 1;
            } else {
                id = Integer.valueOf(tutorList.getLast().getTutorID().substring(1));
                id++;

            }
            ArrayList<String> tutorDomainList = new ArrayList<>();
            String tutorID = "T".concat(String.format("%03d", id));
            String tutorName = tutorUI.inputTutorName();
            char tutorGender = tutorUI.inputTutorGender();
            String tutorIC = tutorUI.inputTutorIC();
            String tutorPhoneNum = tutorUI.inputTutorPhoneNum();
            String tutorEmail = tutorUI.inputTutorEmail();
            double tutorSalary = tutorUI.inputTutorSalary();
            String educationLevel = tutorUI.inputTutorEduLevel();
            tutorDomainList = (ArrayList<String>) inputDomainList(tutorDomainList);

            tutorList.add(new Tutor(tutorID, tutorName, tutorGender, tutorIC, tutorPhoneNum, tutorEmail, tutorSalary, educationLevel, tutorDomainList));
            tutorDAO.saveToFile(tutorList);

            nextOrExit = tutorUI.nextOrExit();

        } while (nextOrExit == 'N');

    }

    public void removeTutor() {

        tutorUI.removeTutorHeader();
        
        ListInterface<TeachingAssignment> teachingAssignmentList = taDAO.retrieveFromFile();

        boolean valid = true;
        int selection;
        do {
            selection = tutorUI.removeTutorMenu();
            switch (selection) {
                case 1:

                    Tutor targetTutor = findTutorSelection();
                    if (targetTutor != null) {
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
                                System.out.println();
                            }
                        }
                    } else {
                        System.out.println("This tutor is not existing in the list...");
                    }

                    break;

                case 2:
                    if (!deleteTutorStack.isEmpty()) {

                        tutorUI.displayListDivider();
                        System.out.println(deleteTutorStack.peek());
                        tutorUI.displayListDivider();

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

        tutorUI.findTutorHeader();
        
        char nextOrExit = 'E';
        do {
            Tutor targetTutor = findTutorSelection();

            if (targetTutor != null) {
                tutorUI.displayListDivider();
                System.out.println(targetTutor);
                tutorUI.displayListDivider();
                System.out.println();
                MessageUI.pause();
            }

            nextOrExit = tutorUI.nextOrExit();

        } while (nextOrExit == 'N');

    }

    public void modifyTutor() {

        tutorUI.modifyTutorHeader();
        
        Tutor targetTutor = findTutorSelection();

        if (targetTutor != null) {
            ListInterface<String> domains = targetTutor.getDomainKnowledgeList();
            int selection = -1;
            do {
                tutorUI.displayListDivider();
                System.out.println(targetTutor);
                tutorUI.displayListDivider();
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
                            int modifySelection = tutorUI.modifyTutorDomain();
                            switch (modifySelection) {
                                case 1:
                                    domains = inputDomainList(domains);
                                    targetTutor.setDomainKnowledgeList((ArrayList<String>) domains);
                                    break;
                                case 2:
                                    domains = removeDomainKnowledge(domains);
                                    targetTutor.setDomainKnowledgeList((ArrayList<String>) domains);
                                    break;
                                default:
                                    System.out.println("Tutor domain knowledged updated...");

                                    break;
                            }
                    }
                } else {
                    System.out.println("Done edit...");
                }
            } while (selection != 0);

        } 
        tutorDAO.saveToFile(tutorList);
    }

    public void displayTutor() {

        tutorUI.displayTutorHeader();
        System.out.println();
        
        tutorUI.displayListDivider();
        tutorUI.displayAllTutors(tutorList);
        tutorUI.displayListDivider();
        
        System.out.println();
        MessageUI.pause();
    }

    public void filterTutor() {

        tutorUI.filterTutorHeader();
        
        Tutor target;
        Iterator<Tutor> it;
        String domain = null;

        int selection = 0;

        do {
            selection = tutorUI.filterTutorMenu();
            switch (selection) {

                case 1:
                    char filterGender = tutorUI.inputTutorGender();
                    tutorUI.displayListDivider();
                    target = null;
                    it = tutorList.getIterator();

                    while (it.hasNext()) {
                        target = it.next();
                        if (target.getGender() == filterGender) {
                            System.out.println(target);
                        }
                    }
                    tutorUI.displayListDivider();
                    System.out.println();
                    MessageUI.pause();
                    break;
                case 2:
                    String filterEducationLevel = tutorUI.inputTutorEduLevel();
                    tutorUI.displayListDivider();
                    target = null;
                    it = tutorList.getIterator();

                    while (it.hasNext()) {
                        target = it.next();
                        if (target.getEducationLevel().equals(filterEducationLevel)) {
                            System.out.println(target);
                        }
                    }
                    tutorUI.displayListDivider();
                    System.out.println();
                    MessageUI.pause();
                    break;
                case 3:

                    String filterDomain = tutorUI.inputOneDomain();
                    tutorUI.displayListDivider();
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
                    tutorUI.displayListDivider();
                    System.out.println();
                    MessageUI.pause();
                    break;
            }
        } while (selection != 0);
    }

    public void generateTutorReport() {

        tutorUI.reportTutorHeader();
        
        int selection;
        do {
            ListInterface<Tutor> sorted;
            sorted = convertToArrayList(tutorList);
            selection = tutorUI.tutorReportMenu();
            switch (selection) {
                case 1:
                    for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
                        int j = i;
                        while (j > 0 && sorted.getEntry(j).getName().compareTo(sorted.getEntry(j + 1).getName()) > 0) {
                            Tutor temp = sorted.getEntry(j);
                            sorted.replace(j, sorted.getEntry(j + 1));
                            sorted.replace(j + 1, temp);
                            j--;
                        }
                    }
                    tutorUI.displayListDivider();
                    tutorUI.displayAllTutors(sorted);
                    tutorUI.displayListDivider();
                    System.out.println();
                    MessageUI.pause();
                    break;

                case 2:
                    for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
                        int j = i;
                        while (j > 0 && sorted.getEntry(j).getSalary() > (sorted.getEntry(j + 1).getSalary())) {
                            Tutor temp = sorted.getEntry(j);
                            sorted.replace(j, sorted.getEntry(j + 1));
                            sorted.replace(j + 1, temp);
                            j--;
                        }
                    }
                    tutorUI.displayListDivider();
                    tutorUI.displayAllTutors(sorted);
                    tutorUI.displayListDivider();
                    System.out.println();
                    MessageUI.pause();
                    break;

            }

        } while (selection != 0);

    }

    public Tutor findTutorSelection() {

        int findSelection = tutorUI.searchTutorMenu();
        Iterator<Tutor> it = tutorList.getIterator();

        Tutor target = null;

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
            case 3:
                String targetTutorEmail = tutorUI.inputTargetTutorEmail();
                while (it.hasNext()) {
                    target = it.next();
                    if (target.getEmail().equals(targetTutorEmail)) {
                        return target;
                    }
                }
                break;
            case 0:
                System.out.println("Exit...");
        }
        if(target == null && findSelection != 0){
            System.out.println("This tutor is not existing in the list...");
        }
        return null;
    }

    public ListInterface<Tutor> convertToArrayList(ListInterface<Tutor> tutorList) {
        ListInterface<Tutor> arrayList = new ArrayList<>();
        Iterator<Tutor> it = tutorList.getIterator();
        while (it.hasNext()) {
            Tutor tutor = it.next();
            arrayList.add(tutor);
        }
        return arrayList;
    }

    public ListInterface<String> inputDomainList(ListInterface<String> domains) {

        ListInterface<String> copyArray = new ArrayList<>();

        for (int i = 0; i < domains.getNumberOfEntries(); i++) {
            copyArray.add(domains.getEntry(i + 1));
        }

        do {
            int tutorDomainSelection = tutorUI.inputTutorDomain();
            String domain = tutorUI.getDomainName(tutorDomainSelection);
            if (!copyArray.contains(domain)) {
                copyArray.add(domain);
                System.out.println("Domain added: " + domain);
            } else {
                System.out.println("This domain is already selected.");
            }

            System.out.print("Do you want to select more domain knowledge? (Y or N)");
        } while (sc.next().equalsIgnoreCase("Y"));

        return copyArray;
    }

    public ListInterface<String> removeDomainKnowledge(ListInterface<String> domains) {

        ListInterface<String> copyArray = new ArrayList<>();

        for (int i = 0; i < domains.getNumberOfEntries(); i++) {
            copyArray.add(domains.getEntry(i + 1));
        }

        int i = 1;
        Iterator<String> it = copyArray.getIterator();
        String domain = null;

        while (it.hasNext()) {
            domain = it.next();
            System.out.print(i + ".");
            System.out.println(domain);
            i++;
        }

        int indexRemove = tutorUI.removeDomainKnowledgeSelection(copyArray);

        if (copyArray.contains(domain)) {
            copyArray.remove(indexRemove);
        } else {
            System.out.println("domain is not in the list...");
        }

        return copyArray;
    }

    public static void main(String[] args) {
        TutorManagement tutorManagement = new TutorManagement();
        TutorManagementUI tutorManagementUI = new TutorManagementUI();
        char confirmation = ' ';

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
                    confirmation = tutorManagementUI.confirmationForUndo();
                    if (confirmation == 'Y') {
                        System.out.println("Exit tutor management...");
                        //back to the main menu;
                    } else {
                        System.out.println("Reenter tutor management...");
                    }

                    break;
            }
        } while (selection != 0 || confirmation == 'N');

    }

}
