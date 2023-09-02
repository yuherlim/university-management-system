/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import dao.*;
import entity.*;
import boundary.*;
import java.util.Iterator;
import utility.MessageUI;

/**
 *
 * @author Yu
 */
public class ProgrammeManagement {

    private ListInterface<Programme> programmeList = new CircularDoublyLinkedList<>();
    private ProgrammeDAO programmeDAO = new ProgrammeDAO();
    private ProgrammeManagementUI programmeManagementUI = new ProgrammeManagementUI();

    public ProgrammeManagement() {
        programmeList = programmeDAO.retrieveFromFile();
    }

    public void runProgrammeManagement() {
        int choice = 0;
        do {
            choice = programmeManagementUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewProgramme();
                    break;
                case 2:
                    removeProgramme();
                    break;
                case 3:
                    findProgramme();
                    break;
                case 4:
                    ammendProgramme();
                    break;
                case 5:
                    displayProgrammes();
                    break;
                case 6:
                    addTutorialGroupToProgramme();
                    break;
                case 7:
                    removeTutorialGroupFromProgramme();
                    break;
                case 8:
                    displayTutorialGroupForProgramme();
                    break;
                case 9:
                    generateReports();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void addNewProgramme() {
        char cont;
        char confirmation;
        do {
            Programme newProgramme = programmeManagementUI.inputProgrammeDetails();
            programmeList.add(newProgramme);
            programmeManagementUI.printProgrammeDetails(newProgramme);

            System.out.println("Confirm addition of this programme?");

            confirmation = programmeManagementUI.inputConfirmation();
            if (confirmation == 'Y') {
                programmeDAO.saveToFile(programmeList);
                System.out.println("");
                System.out.println("New programme added successfully.\n");
            } else { // resets the programmeList back to original;
                programmeList = programmeDAO.retrieveFromFile();
            }

            System.out.println("Continue adding programmes?");
            cont = programmeManagementUI.inputConfirmation();
        } while (cont == 'Y');
        System.out.println("Exiting programme addition...");
    }

    public String getAllProgrammes() {
        String outputStr = "";
        Iterator<Programme> it = programmeList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            outputStr += currentProgramme + "\n";
        }
        return outputStr;
    }

    public void displayProgrammes() {
        programmeManagementUI.listAllProgrammes(getAllProgrammes());
        System.out.println("\nEnter a programme code to view programme details.");
        String code = programmeManagementUI.inputProgrammeCode();
        Programme programmeToView = programmeList.getEntry(new Programme(code));

        if (programmeToView != null) {
            programmeManagementUI.printProgrammeDetails(programmeToView);
        } else {
            System.out.println("\nThe programme code entered does not exist.");
        }
    }

    private void removeProgramme() {
        programmeManagementUI.listAllProgrammes(getAllProgrammes());
        System.out.println("");
        ArrayStack<Integer> undoStackPosition = new ArrayStack<>();
        ArrayStack<Programme> undoStackProgramme = new ArrayStack<>();
        boolean stop = false;
        boolean isRemoved = false;
        boolean removeProgramme = false;
        ArrayList<String> codeToRemove = new ArrayList<>(); // used to modify course list if there is changes to programme code.
        char confirmation;
        int selection = programmeManagementUI.getRemoveProgrammeMenuChoice();
        do {
            switch (selection) {
                case 0:
                    stop = true;
                    if (isRemoved) {
                        System.out.println("\nConfirm the following modification?");
                        confirmation = programmeManagementUI.inputConfirmation();
                        if (confirmation == 'Y') {
                            // clear the undo stack data.
                            while (!(undoStackProgramme.isEmpty())) {
                                codeToRemove.add(undoStackProgramme.pop().getCode());
                            }
                            undoStackPosition.clear();
                            
                            programmeDAO.saveToFile(programmeList);
                            if (removeProgramme) {
                                removeProgrammeFromCourseList(codeToRemove);
                            }
                        } else { // reset list to original.
                            programmeList = programmeDAO.retrieveFromFile();
                        }
                    }
                    break;
                case 1:
                    System.out.println("\nEnter the programme code of the program to remove.");
                    String code = programmeManagementUI.inputProgrammeCode();
                    Programme programmeToRemove = programmeList.getEntry(new Programme(code));
                    int removedProgrammePosition = ((CircularDoublyLinkedList) programmeList).locatePosition(programmeToRemove);
                    
                    char removeConfirmation;
                    if (programmeToRemove != null) {
                        programmeManagementUI.printProgrammeDetails(programmeToRemove);
                        System.out.println("\nAre you sure you want to remove the above programme?");
                        removeConfirmation = programmeManagementUI.inputConfirmation();
                        if (removeConfirmation == 'Y') {
                            undoStackProgramme.push(programmeToRemove);
                            undoStackPosition.push(removedProgrammePosition);
                            programmeList.remove(programmeToRemove);
                        }
                    } else {
                        System.out.println("\nThe programme code entered does not exist.");
                    }
                    break;
                case 2:
                    
                    break;
            }
        } while (selection != 0);

        System.out.println("Exiting programme removal...");
    }

    private void findProgramme() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void ammendProgramme() {
        programmeManagementUI.listAllProgrammes(getAllProgrammes());
        System.out.println("\nEnter a programme code to modify programme details.");
        String code = programmeManagementUI.inputProgrammeCode();
        Programme programmeToModify = programmeList.getEntry(new Programme(code));
        String codeBeforeModification = programmeToModify.getCode(); // used to modify course list if there is changes to programme code.
        boolean modifyCode = false;
        boolean isModified = false;
        int programmeToModifyPosition = ((CircularDoublyLinkedList) programmeList).locatePosition(programmeToModify);
        boolean stop = false;
        char confirmation;
        if (programmeToModify != null) {
            programmeManagementUI.printProgrammeDetails(programmeToModify);
            System.out.println("");
            do {
                switch (programmeManagementUI.getModifyProgrammeMenuChoice()) {
                    case 0:
                        stop = true;
                        if (isModified) {
                            System.out.println("Confirm the following modification?");
                            confirmation = programmeManagementUI.inputConfirmation();
                            if (confirmation == 'Y') {
                                programmeDAO.saveToFile(programmeList);
                                if (modifyCode) {
                                    modifyCourseList(codeBeforeModification, programmeToModify.getCode());
                                }
                            } else { // reset list to original.
                                programmeList = programmeDAO.retrieveFromFile();
                            }
                        }
                        break;
                    case 1:
                        String newCode = programmeManagementUI.inputUniqueProgrammeCode();
                        programmeToModify.setCode(newCode);
                        isModified = true;
                        modifyCode = true;
                        break;
                    case 2:
                        String newName = programmeManagementUI.inputProgrammeName();
                        programmeToModify.setName(newName);
                        isModified = true;
                        break;
                    case 3:
                        String newFaculty = programmeManagementUI.inputProgrammeFaculty();
                        programmeToModify.setFaculty(newFaculty);
                        isModified = true;
                        break;
                    case 4:
                        String newProgrammeType = programmeManagementUI.inputProgrammeType();
                        programmeToModify.setProgrammeType(newProgrammeType);
                        isModified = true;
                        break;
                    case 5:
                        String newDescription = programmeManagementUI.inputProgrammeDescription();
                        programmeToModify.setDescription(newDescription);
                        isModified = true;
                        break;
                    case 6:
                        int newDuration = programmeManagementUI.inputProgrammeDuration();
                        programmeToModify.setDuration(newDuration);
                        isModified = true;
                        break;
                    case 7:
                        double newTotalFees = programmeManagementUI.inputProgrammeTotalFees();
                        programmeToModify.setTotalFee(newTotalFees);
                        isModified = true;
                        break;
                    case 8:
                        int newTotalCreditHours = programmeManagementUI.inputProgrammeTotalCreditHours();
                        programmeToModify.setTotalCreditHour(newTotalCreditHours);
                        isModified = true;
                        break;

                }
                programmeManagementUI.printProgrammeDetails(programmeList.getEntry(programmeToModifyPosition));

            } while (stop == false);
        } else {
            System.out.println("\nThe programme code entered does not exist.");
        }

        System.out.println("Exiting programme modifications...");
    }

    private void addTutorialGroupToProgramme() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void removeTutorialGroupFromProgramme() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void displayTutorialGroupForProgramme() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void generateReports() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        ProgrammeManagement programmeManagement = new ProgrammeManagement();
        programmeManagement.runProgrammeManagement();
    }

    private void modifyCourseList(String code, String newCode) {
        CourseDAO courseDAO = new CourseDAO();
        ListInterface<Course> courseList = courseDAO.retrieveFromFile();

        Iterator<Course> it = courseList.getIterator();
        while (it.hasNext()) {
            Course currentCourse = it.next();
            ArrayList<String> courseProgrammeList = currentCourse.getProgrammes();
            if (courseProgrammeList != null) {
                courseProgrammeList.replace(code, newCode);
            }
        }
        courseDAO.saveToFile(courseList);
    }

    private void removeProgrammeFromCourseList(ArrayList<String> codeToRemove) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

/*
RFS
Bachelor of Science (Hons) in Food Science
Faculty of Applied Sciences (FOAS)
This programme applies the pure science subjects, such as chemistry, biochemistry, nutrition, biology and microbiology to the study of the nature, properties and composition of foods.
3
30000
120
 */
