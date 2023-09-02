/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import boundary.*;
import entity.*;
import dao.*;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author ASUS
 */
public class TutorManagement {

    private ListInterface<Tutor> tutorList = new CircularDoublyLinkedList<>();
    private TutorDAO tutorDAO = new TutorDAO();
    private TutorManagementUI tutorUI = new TutorManagementUI();
    private MessageUI message = new MessageUI();
    private TutorInputValidator validation = new TutorInputValidator();
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
            tutorDomainList = (ArrayList<String>) tutorUI.inputTutorDomain(tutorDomainList);

            tutorList.add(new Tutor(tutorID, tutorName, tutorGender, tutorIC, tutorPhoneNum, tutorEmail, tutorSalary, educationLevel, tutorDomainList));
            tutorDAO.saveToFile(tutorList);

            System.out.print("Do you want to add more tutor? (Y/N): ");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');

    }

    public void removeTutor() {

        StackInterface<Integer> index = new ArrayStack<>();
        StackInterface<Tutor> deleteTutorList = new ArrayStack<>();
        //StackInterface<TeachingAssignment> TeachingAssignmentList = new CircularDoublyLinkedList<>();

        int selection = tutorUI.removeTutorSelection();
        switch (selection) {
            case 1:
                Tutor targetTutor = findTutorSelection();

                char confirmation = tutorUI.removeTutorConfirmation(targetTutor);
                if (confirmation == 'Y') {

//                    Iterator<TeachingAssignment> it = teachingAssignmentList.getIterator();
//                    
//                    while(it.hasNext()){
//                        TeachingAssignment teachingAssignment = it.next();
//                        
//                        if(teachingAssignment.get) //check if the tutor exist in the list
//                        if exist, then break;
//                          show invalid message
//                          get the position
//                          push the position into indexStack
//                          push the tutor into deleteTutorList
//                    }
                }
                break;
            case 2:
                //add confirmation
                Tutor undoTutor = deleteTutorList.pop();
                int undoIndex = index.pop();
                tutorList.add(undoIndex, undoTutor);

        }

    }

    public void findTutor() {

        do {
            Tutor targetTutor = findTutorSelection();

            if (targetTutor != null) {
                System.out.println(targetTutor);
            } else {
                System.out.println("This tutor is not existing in the list...");
            }

            System.out.print("Do you want to find any more tutor details?");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');

    }

    public void modifyTutor() {

        tutorUI.displayModifyTutorMenu();
        tutorUI.displayAllTutors(tutorList);

        do {
            Tutor targetTutor = findTutorSelection();

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
                                tutorUI.modifyTutorDomain(tutorList, targetTutor);
                                break;
                        }
                    } else {
                        System.out.println("Done edit...");
                    }
                } while (selection != 0);
            }else{
                System.out.println("This tutor is not existing in the list...");
            }

            System.out.print("Do you want to modify any more tutor details?");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');

        tutorDAO.saveToFile(tutorList);

    }

    public void filterTutor() {

        do {
            int selection = tutorUI.filterTutorMenu();
            switch (selection) {
                case 1:
                    char filterGender = tutorUI.inputTutorGender();
                    tutorUI.filterByGender(tutorList, filterGender);
                    break;
                case 2:
                    String filterEducationLevel = tutorUI.inputTutorEduLevel();
                    tutorUI.filterByEducationLevel(tutorList, filterEducationLevel);
                    break;
                case 3:
                    String filterDomain = tutorUI.inputOneDomain();
                    if (filterDomain != null) {
                        tutorUI.filterByDomainKnowLedge(tutorList, filterDomain);
                    }
                    break;
            }
            System.out.println("Do you want to filter more tutor details (Y or N)");

        } while (sc.nextLine().toUpperCase().charAt(0) == 'Y');
    }

    public void generateTutorReport() {

    }

    public Tutor findTutorSelection() {

        int findSelection = tutorUI.searchTutorMenu();

        Tutor targetTutor;
        switch (findSelection) {
            case 1:
                String targetTutorID = tutorUI.inputTargetTutorID();
                targetTutor = tutorUI.searchTutorDetails(tutorList, targetTutorID, findSelection);
                break;
            case 2:
                String targetTutorName = tutorUI.inputTargetTutorName();
                targetTutor = tutorUI.searchTutorDetails(tutorList, targetTutorName, findSelection);
                break;
            default:
                String targetTutorEmail = tutorUI.inputTargetTutorEmail();
                targetTutor = tutorUI.searchTutorDetails(tutorList, targetTutorEmail, findSelection);
        }
        return targetTutor;
    }

    public static void main(String[] args) {
        TutorManagement test = new TutorManagement();
        TutorManagementUI uitest = new TutorManagementUI();
        //test.addNewTutor();

        uitest.displayAllTutors(test.tutorList);
        //test.modifyTutor();
        //test.findTutor();

    }
}
