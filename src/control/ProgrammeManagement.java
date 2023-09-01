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
                    programmeManagementUI.listAllProgrammes(getAllProgrammes());
                    break;
                case 5:
                    displayProgrammes();
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
        System.out.println("Enter a programme code to view programme details.");
        String code = programmeManagementUI.inputProgrammeCode();
        Iterator<Programme> it = programmeList.getIterator();
        Programme currentProgramme = null;
        boolean found = true;
        while(it.hasNext()) {
            currentProgramme = it.next();
            if (currentProgramme.equals(new Programme(code))) {
                found = true;
                break;
            } else {
                found = false;
            }
        }
        if (found == true) {
            programmeManagementUI.printProgrammeDetails(currentProgramme);
        } else {
            System.out.println("The programme code entered does not exist.");
        }
        
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