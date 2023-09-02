/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.*;
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
        System.out.printf("%-4s %-75s\n" + outputStr, "Code", "Name");
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
        System.out.println("Programme total credit hours: " + programme.getTotalCreditHour());
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

    private int getProgrammeTypeMenuChoice() {
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
        System.out.println("8. Programme total credit hours");
        int choice;
        boolean valid = false;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice >= 0 && choice <= 8) {
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
    
    public int inputProgrammeTotalCreditHours() {
        System.out.print("Enter programme total credit hours: ");
        int totalCreditHours = scanner.nextInt();
        scanner.nextLine();
        return totalCreditHours;
    }

    public Programme inputProgrammeDetails() {
        String code = inputUniqueProgrammeCode();
        String name = inputProgrammeName();
        String faculty = inputProgrammeFaculty();
        String programmeType = inputProgrammeType();
        String description = inputProgrammeDescription();
        int duration = inputProgrammeDuration();
        double totalFee = inputProgrammeTotalFees();
        int totalCreditHour = inputProgrammeTotalCreditHours();
        return new Programme(code, name, faculty, programmeType, description, duration, totalFee, totalCreditHour);
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
}
