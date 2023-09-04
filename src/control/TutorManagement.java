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
<<<<<<< HEAD
            inputDomainList(tutorDomainList);
=======
            tutorDomainList = (ArrayList<String>) tutorUI.inputTutorDomain(tutorDomainList);
>>>>>>> 94e6b516edf21037a3195489364b00da7811b9bb

            tutorList.add(new Tutor(tutorID, tutorName, tutorGender, tutorIC, tutorPhoneNum, tutorEmail, tutorSalary, educationLevel, tutorDomainList));
            tutorDAO.saveToFile(tutorList);

            System.out.print("Do you want to add more tutor (Y or N) : ");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');

    }

    public void removeTutor() {

        StackInterface<Integer> indexStack = new ArrayStack<>();
        StackInterface<Tutor> deleteTutorStack = new ArrayStack<>();
        ListInterface<TeachingAssignment> teachingAssignmentList = taDAO.retrieveFromFile();

        do {
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
            
            System.out.print("Do you still want to remove any tutor? (Y/N): ");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');
    }

    public void findTutor() {

        do {
            Tutor targetTutor = findTutorSelection();

            if (targetTutor != null) {
                System.out.println(targetTutor);
            } else {
                System.out.println("This tutor is not existing in the list...");
            }

            System.out.print("Do you want to find any more tutor details (Y or N) : ");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');

    }

    public void modifyTutor() {

        tutorUI.displayAllTutors(tutorList);

        do {
            Tutor targetTutor = findTutorSelection();
<<<<<<< HEAD
            ListInterface<String> domains = targetTutor.getDomainKnowledgeList();
=======

>>>>>>> 94e6b516edf21037a3195489364b00da7811b9bb
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
<<<<<<< HEAD
                                int modifySelection = tutorUI.modifyTutorDomain();
                                switch (modifySelection) {
                                    case 1:
                                        inputDomainList(domains);
                                        break;
                                    case 2:
                                        removeDomainKnowledge(domains);
                                        break;
                                    default:
                                        System.out.println("Tutor domain knowledged updated...");
                                        targetTutor.setDomainKnowledgeList((ArrayList<String>) domains);

                                        break;
                                }
=======
                                tutorUI.modifyTutorDomain(tutorList, targetTutor);
                                break;
>>>>>>> 94e6b516edf21037a3195489364b00da7811b9bb
                        }
                    } else {
                        System.out.println("Done edit...");
                    }
                } while (selection != 0);
            } else {
                System.out.println("This tutor is not existing in the list...");
            }

            System.out.print("Do you want to modify any more tutor details (Y or N) : ");

<<<<<<< HEAD
            tutorDAO.saveToFile(tutorList);

        } while (nextOrN == 'N');
=======
        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');

        tutorDAO.saveToFile(tutorList);

>>>>>>> 94e6b516edf21037a3195489364b00da7811b9bb
    }

    public void displayTutor() {

        tutorUI.displayAllTutors(tutorList);
        MessageUI.pause();
    }

    public void filterTutor() {

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
            System.out.print("Do you want to filter more tutor details (Y or N) : ");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');
    }

    public void generateTutorReport() {

<<<<<<< HEAD
        char nextOrN = 'E';
        int selection;

=======
>>>>>>> 94e6b516edf21037a3195489364b00da7811b9bb
        do {
            ListInterface<Tutor> sorted;

            int selection = tutorUI.tutorReportMenu();
            switch (selection) {
                case 1:
<<<<<<< HEAD
                    for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
                        int j = i;
                        while (j > 0 && sorted.getEntry(j).getName().compareTo(sorted.getEntry(j + 1).getName()) > 0) {
                            Tutor temp = sorted.getEntry(j);
                            sorted.replace(j, sorted.getEntry(j + 1));
                            sorted.replace(j + 1, temp);
                            j--;
=======
                    sorted = convertToArrayList(tutorList);

                    for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
                        for (int j = i + 1; j <= sorted.getNumberOfEntries(); j++) {
                            if (sorted.getEntry(i).getName().compareTo(sorted.getEntry(j).getName()) > 0) {
                                Tutor temp = sorted.getEntry(i);
                                sorted.replace(i, sorted.getEntry(j));
                                sorted.replace(j, temp);
                            }
>>>>>>> 94e6b516edf21037a3195489364b00da7811b9bb
                        }
                    }
                    tutorUI.displayAllTutors(sorted);
                    break;

                case 2:
<<<<<<< HEAD
                    for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
                        int j = i;
                        while (j > 0 && sorted.getEntry(j).getSalary() > (sorted.getEntry(j + 1).getSalary())) {
                            Tutor temp = sorted.getEntry(j);
                            sorted.replace(j, sorted.getEntry(j + 1));
                            sorted.replace(j + 1, temp);
                            j--;
=======
                    sorted = convertToArrayList(tutorList);
                    for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
                        for (int j = i + 1; j <= sorted.getNumberOfEntries(); j++) {
                            if (sorted.getEntry(i).getSalary() > sorted.getEntry(j).getSalary()) {
                                Tutor temp = sorted.getEntry(i);
                                sorted.replace(i, sorted.getEntry(j));
                                sorted.replace(j, temp);
                            }
>>>>>>> 94e6b516edf21037a3195489364b00da7811b9bb
                        }
                    }
                    tutorUI.displayAllTutors(sorted);
                    break;

            }
<<<<<<< HEAD
            tutorUI.displayAllTutors(sorted);

            if (selection == 0) {
                break;
            }

            nextOrN = tutorUI.nextOrExit();

        } while (nextOrN == 'N');
=======
            System.out.print("Do you want to generate more report (Y or N) : ");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');
>>>>>>> 94e6b516edf21037a3195489364b00da7811b9bb

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

    public ListInterface<Tutor> convertToArrayList(ListInterface<Tutor> tutorList) {
        ListInterface<Tutor> arrayList = new ArrayList<>();
        Iterator<Tutor> it = tutorList.getIterator();
        while (it.hasNext()) {
            Tutor tutor = it.next();
            arrayList.add(tutor);
        }
        return arrayList;
    }

    public void inputDomainList(ListInterface<String> domains) {
        do {
            int tutorDomainSelection = tutorUI.inputTutorDomain();
            String domain = tutorUI.getDomainName(tutorDomainSelection);
            if (!domains.contains(domain)) {
                domains.add(domain);
                System.out.println("Domain added: " + domain);
            } else {
                System.out.println("This domain is already selected.");
            }

            System.out.print("Do you want to select more domain knowledge? (Y or N)");
        } while (sc.next().equalsIgnoreCase("Y"));
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

    private void removeDomainKnowledge(ListInterface<String> domains) {

        int i = 1;
        Iterator<String> it = domains.getIterator();
        String domain = null;

        while (it.hasNext()) {
            domain = it.next();
            System.out.print(i + ".");
            System.out.println(domain);
            i++;
        }

        int indexRemove = tutorUI.removeDomainKnowledgeSelection(domains);

        if (domains.contains(domain)) {
            domains.remove(indexRemove);
        } else {
            System.out.println("domain is not in the list...");
        }
    }
}
