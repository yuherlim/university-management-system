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
        char yOrN;
        do {
            int id = tutorList.getNumberOfEntries();
            ArrayList<String> tutorDomainList = null;
            id++;
            String tutorID = "T".concat(String.format("%03d", id));
            String tutorName = tutorUI.inputTutorName();
            char tutorGender = tutorUI.inputTutorGender();
            String tutorIC = tutorUI.inputTutorIC();
            String tutorPhoneNum = tutorUI.inputTutorPhoneNum();
            String tutorEmail = tutorUI.inputTutorEmail();
            String educationLevel = tutorUI.inputTutorEduLevel();
            tutorDomainList = (ArrayList<String>) tutorUI.inputTutorDomain(tutorDomainList);

            tutorList.add(new Tutor(tutorID, tutorName, tutorGender, tutorIC, tutorPhoneNum, tutorEmail, educationLevel, tutorDomainList));
            tutorDAO.saveToFile(tutorList);

            System.out.print("Do you want to add more tutor? (Y/N): ");

            // Check if there is a line available to read
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                yOrN = input.trim().equalsIgnoreCase("Y") ? 'Y' : 'N';
            } else {
                // Handle the case where no input is available (e.g., assume 'N')
                yOrN = 'N';
            }

        } while (yOrN == 'Y');

        System.out.println();
    }

    public void removeTutor() {

    }

    public Tutor findTutor() {

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

        if (targetTutor != null) {
            System.out.println(targetTutor);
        } else {
            System.out.println("This tutor is not existing in the list...");
        }

        return targetTutor;

    }

    public void modifyTutor() {

        tutorUI.displayModifyTutorMenu();
        tutorUI.displayAllTutors(tutorList);

        do {
            Tutor targetTutor = findTutor();

            if (targetTutor != null) {
                int selection = -1;
                do {
                    selection = tutorUI.modifyTutorSelection(targetTutor);
                    if (selection != 0) {
                        tutorList = tutorUI.modifyTutorDetails(tutorList, targetTutor, selection);
                    } else {
                        System.out.println("Done edit...");
                    }
                } while (selection != 0);

                System.out.print("Do you want to modify any more tutor details?");
            }
            else{
                System.out.print("Do you want to modify any more tutor details?");
            }
        } while (sc.next().equalsIgnoreCase("Y"));
        
        tutorDAO.saveToFile(tutorList);

    }

    public void filterTutor() {
        //filter by gender
        //domain knowledfe
    }

    public void generateTutorReport() {

    }

    public static void main(String[] args) {
        TutorManagement test = new TutorManagement();
        TutorManagementUI uitest = new TutorManagementUI();
        //test.addNewTutor();

        uitest.displayAllTutors(test.tutorList);
        test.modifyTutor();

    }
}
