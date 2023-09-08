/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.*;
import control.ProgrammeManagement;
import dao.ProgrammeDAO;
import java.util.Scanner;
import entity.Programme;
import utility.MessageUI;

/**
 *
 * @author Yu
 */
public class ProgrammeManagementUI {

    Scanner scanner = new Scanner(System.in);
    private ListInterface<Programme> programmeList = new CircularDoublyLinkedList<>();
    private ProgrammeDAO programmeDAO = new ProgrammeDAO();

    public int getMenuChoice() {
        System.out.println("\nPROGRAMME MANAGEMENT MENU");
        System.out.println("1. Add a new programme");
        System.out.println("2. Remove a programme");
        System.out.println("3. Find programme");
        System.out.println("4. Ammend programme details");
        System.out.println("5. List all programmes");
        System.out.println("6. Add a tutorial group to a programme");
        System.out.println("7. Remove a tutorial group from a programme");
        System.out.println("8. List all tutorial groups for a programme");
        System.out.println("9. Generate reports");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void listProgrammes(String outputStr) {
        System.out.println("\nList of Programmes:");
        System.out.printf("%-4s %-80s %-60s %-10s %-15s\n" + outputStr, "Code", "Name", "Faculty", "Type", "Total fees (RM)");
    }
    
    public void listTutGroupsForProgrammes(String outputStr) {
        System.out.println("\nList of Programmes with their current tutorial groups:");
        System.out.printf("%-4s %-80s %-35s\n" + outputStr, "Code", "Name", "Tutorial Groups");
    }

    public void printProgrammeDetails(Programme programme) {
        System.out.println("\nProgramme Details:");
        System.out.println("Programme code              : " + programme.getCode());
        System.out.println("Programme name              : " + programme.getName());
        System.out.println("Programme faculty           : " + programme.getFaculty());
        System.out.println("Programme type              : " + programme.getProgrammeType());
        System.out.println("Programme description       : " + programme.getDescription());
        System.out.println("Programme duration (years)  : " + programme.getDuration());
        System.out.printf("Programme total fees (RM)   : %.2f\n", programme.getTotalFee());
    }

    public String inputProgrammeCode() {
        boolean valid;
        String code;
        do {
            valid = true;
            System.out.print("Enter programme code: ");
            code = scanner.nextLine().toUpperCase();
            if (code.length() != 3) {
                valid = false;
                System.out.println("\nInvalid length for programme code.");
            } else {
                for (int i = 0; i < code.length(); i++) {
                    if (!Character.isLetter(code.charAt(i))) {
                        valid = false;
                        System.out.println("\nInvalid programme code (must be letters).");
                        break;
                    }
                }
            }
        } while (valid == false);
        return code;
    }

    public String inputProgrammeName() {
        boolean valid;
        String name;
        do {
            valid = true;
            System.out.print("Enter programme name: ");
            name = scanner.nextLine();
            for (int i = 0; i < name.length(); i++) {
                if (Character.isDigit(name.charAt(i))) {
                    valid = false;
                    System.out.println("\nInvalid programme name (must be letters).");
                    break;
                }
            }
        } while (valid == false);
        return name;
    }

    public String inputProgrammeFaculty() {
        boolean valid;
        String faculty;
        do {
            valid = true;
            System.out.print("Enter faculty name: ");
            faculty = scanner.nextLine();
            for (int i = 0; i < faculty.length(); i++) {
                if (Character.isDigit(faculty.charAt(i))) {
                    valid = false;
                    System.out.println("\nInvalid faculty name (must be letters).");
                    break;
                }
            }
        } while (valid == false);
        return faculty;
    }

    public String inputProgrammeType() {
        System.out.println("Enter programme type: ");
        System.out.println("Choose from the following selection: ");
        String programmeType = "";
        switch (getProgrammeTypeMenuChoice()) {
            case 1:
                programmeType = "Foundation";
                break;
            case 2:
                programmeType = "Diploma";
                break;
            case 3:
                programmeType = "Bachelor";
                break;
            case 4:
                programmeType = "Masters";
                break;
            case 5:
                programmeType = "PhD";
                break;
        }
        return programmeType;
    }

    public int getProgrammeTypeMenuChoice() {
        System.out.println("\nProgramme types: ");
        System.out.println("1. Foundation");
        System.out.println("2. Diploma");
        System.out.println("3. Bachelor");
        System.out.println("4. Masters");
        System.out.println("5. PhD");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 1 && choice <= 5) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        System.out.println();
        return choice;
    }
    
    public int getModifyProgrammeMenuChoice() {
        System.out.println("\nSelect a field to modify: ");
        System.out.println("0. Stop modifications");
        System.out.println("1. Programme code");
        System.out.println("2. Programme name");
        System.out.println("3. Programme Faculty");
        System.out.println("4. Programme type");
        System.out.println("5. Programme description");
        System.out.println("6. Programme duration");
        System.out.println("7. Programme total fees");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 7) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getRemoveProgrammeMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Stop removing programmes");
        System.out.println("1. Remove a programme");
        System.out.println("2. Undo programme removal");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 2) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getRemoveTutGroupMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Stop removing tutorial groups");
        System.out.println("1. Remove a tutorial group from a programme");
        System.out.println("2. Undo tutorial group removal");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 2) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getAddTutorialGroupMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Go back");
        System.out.println("1. Add tutorial group to a programme");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 1) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getDisplayTutorialGroupMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Go back");
        System.out.println("1. View tutorial groups for a programme.");
        System.out.println("2. View tutorial groups for all programmes.");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 2) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getGenerateReportMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Go back");
        System.out.println("1. View total fee report.");
        System.out.println("2. View programme type report.");
        System.out.println("3. View tutorial group assignment report.");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 3) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getFindProgrammeMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Go back");
        System.out.println("1. Find by programme code");
        System.out.println("2. Find by name");
        System.out.println("3. Find by faculty");
        System.out.println("4. Find by programme type");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 4) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getViewProgrammeDetailsMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Go back");
        System.out.println("1. View programme details");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 1) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getAddProgrammeMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Go back");
        System.out.println("1. Start adding a programme");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 1) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public int getModifyProgrammeDetailsMenuChoice() {
        System.out.println("\nPlease select a choice: ");
        System.out.println("0. Go back");
        System.out.println("1. Modify programme details");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 1) {
                valid = true;
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (valid == false);
        return choice;
    }
    
    public String inputProgrammeDescription() {
        boolean valid;
        String description;
        do {
            valid = true;
            System.out.print("Enter programme description: ");
            description = scanner.nextLine();
            for (int i = 0; i < description.length(); i++) {
                if (Character.isDigit(description.charAt(i))) {
                    valid = false;
                    System.out.println("\nInvalid programme description (must be letters).");
                    break;
                }
            }
        } while (valid == false);
        return description;
    }
    
    public int inputProgrammeDuration() {
        System.out.print("Enter programme duration (years): ");
        int duration = scanner.nextInt();
        scanner.nextLine();
        return duration;
    }
    
    public double inputProgrammeTotalFees() {
        System.out.print("Enter programme total fee: ");
        double totalFee = scanner.nextDouble();
        scanner.nextLine();
        return totalFee;
    }

    public Programme inputProgrammeDetails() {
        String code = inputUniqueProgrammeCode();
        String name = inputProgrammeName();
        String faculty = inputProgrammeFaculty();
        String programmeType = inputProgrammeType();
        String description = inputProgrammeDescription();
        int duration = inputProgrammeDuration();
        double totalFee = inputProgrammeTotalFees();
        return new Programme(code, name, faculty, programmeType, description, duration, totalFee);
    }
    
    public String inputUniqueProgrammeCode() {
        programmeList = programmeDAO.retrieveFromFile();
        boolean validCode;
        String code;
        do {
            validCode = true;
            code = inputProgrammeCode();
            if (programmeList.contains(new Programme(code))) {
                validCode = false;
                System.out.println("Duplicate code found. Please ensure code is unique.");
            }
        } while (validCode == false);
        return code;
    }
    
    public char inputConfirmation() {
        char confirmation;
        boolean isValid = false;
        do {
            System.out.print("Enter (Y/N): ");
            confirmation = scanner.nextLine().toUpperCase().charAt(0);
            if (confirmation == 'Y' || confirmation == 'N') {
                isValid = true;
            } else {
                System.out.println("Please enter (Y/N).");
            }
        } while (isValid == false);
        return confirmation;
    }
    
    public String inputSearchQuery() {
        System.out.print("Enter your search query: ");
        return scanner.nextLine().toUpperCase();
    }
    
    public void nonexistentProductCodeMsg() {
        System.out.println("\nThe programme code entered does not exist.");
    }
    
    public void nothingToUndoMsg() {
        System.out.println("\nThere is nothing to undo.");
    }
    
    public void undoCancelledMsg() {
        System.out.println("\nUndo cancelled.");
    }

    public void generateTotalFeeReport(ListInterface<Programme> sortedProgrammeListByTotalFee) {
        String outputStr = "";
        String sortedData = "";
        
        for (int i = 1; i <= sortedProgrammeListByTotalFee.getNumberOfEntries(); i++) {
            Programme currentElement = sortedProgrammeListByTotalFee.getEntry(i);
            sortedData += String.format("%-4s %-80s %15.2f\n", currentElement.getCode(), currentElement.getName(), currentElement.getTotalFee());
        }
        
        Programme programmeWithHighestTotalFee = sortedProgrammeListByTotalFee.getFirst();
        Programme programmeWithLowestTotalFee = sortedProgrammeListByTotalFee.getLast();
        String highestFeeOutputStr = String.format("%-4s %-80s %15.2f\n", 
                programmeWithHighestTotalFee.getCode(), programmeWithHighestTotalFee.getName(), programmeWithHighestTotalFee.getTotalFee());
        String lowestFeeOutputStr = String.format("%-4s %-80s %15.2f\n", 
                programmeWithLowestTotalFee.getCode(), programmeWithLowestTotalFee.getName(), programmeWithLowestTotalFee.getTotalFee());
        
        outputStr += "\n------------------\n";
        outputStr +=   " Total Fee Report \n";
        outputStr +=   "------------------\n";
        outputStr += "\nProgramme list sorted by highest total fees: \n";
        outputStr += String.format("%-4s %-80s %-15s\n" + sortedData, "Code", "Name", "Total Fees (RM)");
        outputStr += "\nProgramme with the highest total fee: \n";
        outputStr += String.format("%-4s %-80s %-15s\n" + highestFeeOutputStr, "Code", "Name", "Total Fees (RM)");
        outputStr += "\nProgramme with the lowest total fee: \n";
        outputStr += String.format("%-4s %-80s %-15s\n" + lowestFeeOutputStr, "Code", "Name", "Total Fees (RM)");
        
        System.out.println(outputStr);
        MessageUI.pause();
    }

    public void generateProgrammeTypeReport(ListInterface<Programme> sortedProgrammeListByProgrammeType) {
        String outputStr = "";
        String sortedData = "";
        int[] programmeTypeCount = new int[5];
        
        for (int i = 1; i <= sortedProgrammeListByProgrammeType.getNumberOfEntries(); i++) {
            Programme currentElement = sortedProgrammeListByProgrammeType.getEntry(i);
            sortedData += String.format("%-4s %-80s %-10s\n", currentElement.getCode(), currentElement.getName(), currentElement.getProgrammeType());
            
            switch (currentElement.getProgrammeType()) {
                case "Bachelor":
                    programmeTypeCount[0]++;
                    break;
                case "Diploma":
                    programmeTypeCount[1]++;
                    break;
                case "Foundation":
                    programmeTypeCount[2]++;
                    break;
                case "Masters":
                    programmeTypeCount[3]++;
                    break;
                case "PhD":
                    programmeTypeCount[4]++;
                    break;
            }
        }
        
        outputStr += "\n-----------------------\n";
        outputStr +=   " Programme Type Report \n";
        outputStr +=   "-----------------------\n";
        outputStr += "\nProgramme list sorted by programme type: \n";
        outputStr += String.format("%-4s %-80s %-10s\n" + sortedData, "Code", "Name", "Type");
        outputStr += String.format("\nNumber of Bachelor programmes     : %d\n", programmeTypeCount[0]);
        outputStr += String.format(  "Number of Diploma programmes      : %d\n", programmeTypeCount[1]);
        outputStr += String.format(  "Number of Foundation programmes   : %d\n", programmeTypeCount[2]);
        outputStr += String.format(  "Number of Masters programmes      : %d\n", programmeTypeCount[3]);
        outputStr += String.format(  "Number of PhD programmes          : %d\n", programmeTypeCount[4]);
        
        System.out.println(outputStr);
        MessageUI.pause();
    }

    public void generateTutGroupAssignmentReport(ListInterface<Programme> assignedTutGroupProgrammes, ListInterface<Programme> unassignedTutGroupProgrammes) {
        String outputStr = "";
        String sortedDataAssigned = "";
        String sortedDataUnassigned = "";
        
        for (int i = 1; i <= assignedTutGroupProgrammes.getNumberOfEntries(); i++) {
            Programme currentElement = assignedTutGroupProgrammes.getEntry(i);
            sortedDataAssigned += String.format("%-4s %-80s %-35s\n", currentElement.getCode(), currentElement.getName(), ProgrammeManagement.createTutorialGroupStr(currentElement.getTutorialGroups()));
        }
        
        for (int i = 1; i <= unassignedTutGroupProgrammes.getNumberOfEntries(); i++) {
            Programme currentElement = unassignedTutGroupProgrammes.getEntry(i);
            sortedDataUnassigned += String.format("%-4s %-80s %-35s\n", currentElement.getCode(), currentElement.getName(), "none");
        }
        
        outputStr += "\n----------------------------------\n";
        outputStr +=   " Tutorial Group Assignment Report \n";
        outputStr +=   "----------------------------------\n";
        outputStr += "\nProgramme list with assigned tutorial groups: \n";
        outputStr += String.format("%-4s %-80s %-35s\n" + sortedDataAssigned, "Code", "Name", "Tutorial Groups");
        outputStr += "\nProgramme list with unassigned tutorial groups: \n";
        outputStr += String.format("%-4s %-80s %-35s\n" + sortedDataUnassigned, "Code", "Name", "Tutorial Groups");
        outputStr += String.format("\nNumber of programmes with assigned tutorial groups: %d\n", assignedTutGroupProgrammes.getNumberOfEntries());
        outputStr += String.format("Number of programmes with unassigned tutorial groups: %d\n", unassignedTutGroupProgrammes.getNumberOfEntries());
        
        System.out.println(outputStr);
        MessageUI.pause();
    }
}
