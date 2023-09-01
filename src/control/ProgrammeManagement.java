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
        Programme newProgramme = programmeManagementUI.inputProgrammeDetails();
        programmeList.add(newProgramme);
        programmeDAO.saveToFile(programmeList);
        programmeManagementUI.printProgrammeDetails(newProgramme);
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void findProgramme() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void ammendProgramme() {
        programmeManagementUI.listAllProgrammes(getAllProgrammes());
        System.out.println("\nEnter a programme code to modify programme details.");
        String code = programmeManagementUI.inputProgrammeCode();
        Programme programmeToModify = programmeList.getEntry(new Programme(code));
        Programme programmeToBeReplacedWith = programmeToModify;
        if (programmeToModify != null) {
            programmeManagementUI.printProgrammeDetails(programmeToModify);
        } else {
            System.out.println("\nThe programme code entered does not exist.");
        }
        System.out.println("");
        boolean stop = false;
        do {
            switch (programmeManagementUI.getModifyProgrammeMenuChoice()) {
                case 0:
                    stop = true;
                    break;
                case 1:
                    String newCode = programmeManagementUI.inputProgrammeCode();
                    programmeToModify.setCode(newCode);
                    break;
                case 2:
                    String newName = programmeManagementUI.inputProgrammeName();
                    programmeToModify.setName(newName);
                    break;
                case 3:
                    String newFaculty = programmeManagementUI.inputProgrammeFaculty();
                    programmeToModify.setFaculty(newFaculty);
                    break;
                case 4:
                    String newProgrammeType = programmeManagementUI.inputProgrammeType();
                    programmeToModify.setProgrammeType(newProgrammeType);
                    break;
                case 5:
                    String newDescription = programmeManagementUI.inputProgrammeDescription();
                    programmeToModify.setDescription(newDescription);
                    break;
                case 6:
                    int newDuration = programmeManagementUI.inputProgrammeDuration();
                    programmeToModify.setDuration(newDuration);
                    break;
                case 7:
                    double newTotalFees = programmeManagementUI.inputProgrammeTotalFees();
                    programmeToModify.setTotalFee(newTotalFees);
                    break;
                case 8:
                    int newTotalCreditHours = programmeManagementUI.inputProgrammeTotalCreditHours();
                    programmeToModify.setTotalCreditHour(newTotalCreditHours);
                    break;

            }
            programmeManagementUI.printProgrammeDetails(programmeToModify);
        } while (stop == false);
        programmeList.replace(programmeList.get);
        programmeDAO.saveToFile(programmeList);
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
