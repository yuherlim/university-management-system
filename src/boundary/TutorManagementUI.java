/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.*;
import entity.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author KHO KA JIE
 */
public class TutorManagementUI {

    Scanner sc = new Scanner(System.in);

    String tutorName;
    char tutorGender;
    String tutorIC;
    String tutorPhoneNum;
    String tutorEmail;
    double tutorSalary;

    //------------------------------------------------------------
    public void addTutorHeader() {
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("|           Add Tutor            |");
        System.out.println("----------------------------------");
    }

    public void removeTutorHeader() {
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("|          Remove Tutor          |");
        System.out.println("----------------------------------");
    }

    public void findTutorHeader() {
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("|           Find Tutor           |");
        System.out.println("----------------------------------");
    }

    public void modifyTutorHeader() {
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("|          Modify Tutor          |");
        System.out.println("----------------------------------");
    }

    public void displayTutorHeader() {
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("|          Display Tutor         |");
        System.out.println("----------------------------------");
    }

    public void filterTutorHeader() {
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("|          Filter Tutor          |");
        System.out.println("----------------------------------");
    }

    public void reportTutorHeader() {
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("|          Tutor Report          |");
        System.out.println("----------------------------------");
    }

    public void displayListDivider() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    //------------------------------------------------------------
    public int tutorMainMenu() {

        boolean valid;
        int selection = 0;

        do {
            valid = true;
            try {
                System.out.println("----------------------------------");
                System.out.println("Tutor Main Menu");
                System.out.println("----------------------------------");
                System.out.println("1. Add new tutor");
                System.out.println("2. Remove tutor");
                System.out.println("3. Find tutor");
                System.out.println("4. Amend tutor details");
                System.out.println("5. List all tutor");
                System.out.println("6. Filter tutors");
                System.out.println("7. Generate report");
                System.out.println("0. Exit");
                System.out.println("----------------------------------");
                System.out.print("Your selection : ");
                selection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                valid = false;
            }
            if (selection < 0 || selection > 7) {
                valid = false;
            }
            if (!valid) {
                System.out.println("Invalid. Please select a valid selecton...");
            }
        } while (!valid);

        return selection;

    }

    public int removeTutorMenu() {

        boolean valid;
        int selection = 0;

        do {
            valid = true;
            try {
                System.out.println("1. Delete tutor ");
                System.out.println("2. Undo deletion(once per time)");
                System.out.println("0. Exit");
                System.out.print("Your selection : ");
                selection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                valid = false;
            }
            if (selection < 0 || selection > 2) {
                valid = false;
            }
            if (!valid) {
                System.out.println("Invalid. Please select a valid selection...");
            }
        } while (!valid);

        return selection;
    }

    public int searchTutorMenu() {
        int searchSelection = 0;
        boolean valid;

        do {
            valid = true;
            try {
                System.out.println("Search tutor");
                System.out.println("By: ");
                System.out.println("1. ID");
                System.out.println("2. Name");
                System.out.println("3. Email");
                System.out.println("0. Exit");
                System.out.print("Your selection : ");
                searchSelection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                valid = false;
            }
            if (searchSelection < 0 || searchSelection > 3) {
                valid = false;
            }
            if (!valid) {
                System.out.println("Invalid. Please select a valid selection...");
            }

        } while (!valid);

        return searchSelection;
    }

    public int modifyTutorSelection(Tutor tutor) {

        boolean valid;
        int selection = 0;

        do {
            valid = true;
            try {

                System.out.println("1. Modify tutor name");
                System.out.println("2. Modify tutor phone number");
                System.out.println("3. Modify tutor email");
                System.out.println("4. Modify tutor salary");
                System.out.println("5. Modify tutor education level");
                System.out.println("6. Modify tutor domain knowledge");
                System.out.println("0. Exit");
                System.out.print("Your selection : ");
                selection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                valid = false;
            }
            if (selection < 0 || selection > 6) {
                valid = false;
            }
            if (!valid) {
                System.out.println("Invalid. Please select a valid selection...");
            }
        } while (!valid);
        return selection;
    }

    public int filterTutorMenu() {
        int filterSelection = 0;
        boolean valid;

        do {
            valid = true;
            try {
                System.out.println("Filter tutor");
                System.out.println("1. Filter by gender");
                System.out.println("2. Filter by education level");
                System.out.println("3. Filter by domain knowledge");
                System.out.println("0. Exit");
                System.out.print("Your selection: ");
                filterSelection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                valid = false;
            }
            if (filterSelection < 0 || filterSelection > 3) {
                valid = false;
            } else if (filterSelection == 0) {
                System.out.println("Exit filter function...");
            }

            if (!valid) {
                System.out.println("Invalid. Please select a valid selection...");
            }

        } while (!valid);

        return filterSelection;
    }

    public int tutorReportMenu() {
        int reportSelection = 0;
        boolean valid;

        do {
            valid = true;
            try {
                System.out.println("Tutor report");
                System.out.println("1. Sort by tutor name");
                System.out.println("2. Sort by tutor's salary");
                System.out.println("0. Exit");
                System.out.print("Your selection: ");
                reportSelection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                valid = false;
            }
            if (reportSelection < 0 || reportSelection > 2) {
                valid = false;
            } else if (reportSelection == 0) {
                System.out.println("Exit generate report function...");
            }
            if (!valid) {
                System.out.println("Invalid. Please select a valid selection...");
            }

        } while (!valid);

        return reportSelection;

    }

    //------------------------------------------------------------
    public String inputTutorName() {
        boolean valid;

        do {
            valid = true;
            System.out.print("Enter tutor name : ");
            tutorName = sc.nextLine();

            if (tutorName.isEmpty() || Character.isSpaceChar(tutorName.charAt(0))) {
                valid = false;
                System.out.println("Invalid. Name can't be null...");
            } else {
                for (int i = 0; i < tutorName.length(); i++) {
                    if (!Character.isSpaceChar(tutorName.charAt(i))) {
                        if (!Character.isAlphabetic(tutorName.charAt(i))) {
                            valid = false;
                            System.out.println("Invalid. Please enter a valid name...");
                            break;
                        }
                    }
                }
            }
        } while (!valid);

        StringBuilder capitalizedTutorName = new StringBuilder();
        capitalizedTutorName.append(Character.toUpperCase(tutorName.charAt(0)));

        for (int i = 1; i < tutorName.length(); i++) {
            char currentChar = tutorName.charAt(i);
            char previousChar = tutorName.charAt(i - 1);

            if (Character.isSpaceChar(previousChar)) {
                capitalizedTutorName.append(Character.toUpperCase(currentChar));
            } else {
                capitalizedTutorName.append(currentChar);
            }
        }

        String result = capitalizedTutorName.toString();

        return result;
    }

    public char inputTutorGender() {
        boolean valid;

        do {
            valid = true;
            try {
                System.out.print("Enter tutor gender (F=Female,M=Male) : ");
                tutorGender = sc.nextLine().toUpperCase().charAt(0);
            } catch (Exception e) {
                valid = false;
            }
            if (tutorGender != 'M' && tutorGender != 'F') {
                valid = false;
                System.out.println("Invalid. Please enter (F or M)...");
            }
        } while (!valid);

        return tutorGender;
    }

    public String inputTutorIC() {
        boolean valid;

        do {
            valid = true;
            System.out.print("Enter tutor IC No (without - ) : ");
            tutorIC = sc.nextLine();
            if (tutorIC.isEmpty() || Character.isSpaceChar(tutorIC.charAt(0))) {
                valid = false;
            } else if (tutorIC.length() == 12) {
                int month = Character.getNumericValue(tutorIC.charAt(2)) * 10 + Character.getNumericValue(tutorIC.charAt(3));
                int day = Character.getNumericValue(tutorIC.charAt(4)) * 10 + Character.getNumericValue(tutorIC.charAt(5));

                switch (month) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        valid = (day >= 1 && day <= 31);
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        valid = (day >= 1 && day <= 30);
                        break;
                    case 2:
                        valid = (day >= 1 && day <= 28);
                        break;
                }

                if (valid) {
                    boolean isEvenGenderDigit = Character.getNumericValue(tutorIC.charAt(11)) % 2 == 0;
                    valid = (tutorGender == 'M' && !isEvenGenderDigit)
                            || (tutorGender == 'F' && isEvenGenderDigit);
                }
            } else {
                valid = false;
            }

            if (!valid) {
                System.out.println("Invalid. Please enter a valid IC format and follow your gender...");
            }

        } while (!valid);

        return tutorIC;
    }

    public String inputTutorPhoneNum() {
        boolean valid;

        do {
            valid = true;
            System.out.print("Enter tutor phone number(01x-xxxxxxx) : ");
            tutorPhoneNum = sc.nextLine();
            if (tutorPhoneNum.isEmpty() || Character.isSpaceChar(tutorPhoneNum.charAt(0))) {
                valid = false;
            }

            String phoneRegex = "^(\\+?6?01)[02-46-9][-][0-9]{7}$|^(\\+?6?01)[1][-][0-9]{8}$";
            Pattern phonepat = Pattern.compile(phoneRegex);
            Matcher matcher = phonepat.matcher(tutorPhoneNum);
            valid = matcher.find();

            if (!valid) {
                System.out.println("Invalid. Please enter valid phone format (01x-xxxxxxx)...");
            }

        } while (!valid);

        return tutorPhoneNum;
    }

    public String inputTutorEmail() {
        boolean valid;

        do {
            valid = true;
            System.out.print("Enter tutor email : ");
            tutorEmail = sc.nextLine();
            if (tutorEmail.isEmpty() || Character.isSpaceChar(tutorEmail.charAt(0))) {
                valid = false;
            }
            String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = emailPat.matcher(tutorEmail);
            valid = matcher.find();

            if (!valid) {
                System.out.println("Invalid. Please enter valid email format...");
            }
        } while (!valid);

        return tutorEmail;
    }

    public double inputTutorSalary() {
        boolean valid = false;

        do {
            valid = true;
            try {
                System.out.print("Enter tutor salary : ");
                tutorSalary = sc.nextDouble();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Invalid. Please select a valid salary...");
                valid = false;
            }
        } while (!valid);

        return tutorSalary;

    }

    public String inputTutorEduLevel() {

        boolean valid;
        int tutorEduLevelSelection = 0;
        String educationLevel = "";
        do {
            valid = true;
            try {
                System.out.println("Select tutor education level : ");
                System.out.println("1. Diploma");
                System.out.println("2. Bachelor's Degree");
                System.out.println("3. Master's Degree");
                System.out.println("4. phD");
                System.out.print("Your input : ");
                tutorEduLevelSelection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                valid = false;
            }
            if (tutorEduLevelSelection < 1 || tutorEduLevelSelection > 4) {
                valid = false;
            }
            if (!valid) {
                System.out.println("Invalid. Please select a valid education level...");
            }

        } while (!valid);

        switch (tutorEduLevelSelection) {
            case 1:
                educationLevel = "Diploma";
                break;
            case 2:
                educationLevel = "Bachelor's Degree";
                break;
            case 3:
                educationLevel = "Master's Degree";
                break;
            case 4:
                educationLevel = "phD";
                break;
        }

        return educationLevel;
    }

    public int inputTutorDomain() {

        System.out.println("Input the tutor domain knowledge : ");
        System.out.println("1. Accounting");
        System.out.println("2. Add-Math");
        System.out.println("3. Biology");
        System.out.println("4. Chemistry");
        System.out.println("5. Physics");

        int tutorDomainSelection = 0;
        boolean valid;
        do {
            valid = true;
            try {
                System.out.print("Your input: ");
                tutorDomainSelection = sc.nextInt();
                sc.nextLine(); // Consume the newline character
            } catch (Exception e) {
                sc.nextLine();
                valid = false;
            }
            if (tutorDomainSelection < 1 || tutorDomainSelection > 5) {
                valid = false;
            }

            if (!valid) {
                System.out.println("Invalid input. Please enter a valid number.");
            }

        } while (!valid);

        return tutorDomainSelection;
    }
    //------------------------------------------------------------

    public String getDomainName(int selection) {
        switch (selection) {
            case 1:
                return "Accounting";
            case 2:
                return "Add-Math";
            case 3:
                return "Biology";
            case 4:
                return "Chemistry";
            case 5:
                return "Physics";
            default:
                return "";
        }
    }

    //------------------------------------------------------------
    public String inputTargetTutorID() {

        boolean valid;
        String targetTutorID;

        do {
            valid = true;
            System.out.print("Please enter the tutor ID you want to search (T000): ");
            targetTutorID = sc.nextLine();
            if (targetTutorID.isEmpty() || Character.isSpaceChar(targetTutorID.charAt(0))) {
                valid = false;
                System.out.println("Invalid. Tutor ID can't be null...");
            } else if (targetTutorID.charAt(0) != 'T') {
                valid = false;
                System.out.println("Invalid. Tutor ID must start with T...");
            } else if (targetTutorID.substring(1).length() > 3) {
                valid = false;
                System.out.println("Invalid. Tutor ID only can start with 'T' and follow by 3 digit...");
            } else if (Integer.valueOf(targetTutorID.substring(1)) < 1 || Integer.valueOf(targetTutorID.substring(1)) > 999) {
                valid = false;
                System.out.println("Invalid. Please enter a valid tutor ID...");
            }
        } while (!valid);

        return targetTutorID;
    }

    public String inputTargetTutorName() {
        boolean valid;

        String targetTutorName;
        do {
            valid = true;
            System.out.print("Please enter the tutor name you want to search: ");
            targetTutorName = sc.nextLine();
            if (targetTutorName.isEmpty() || Character.isSpaceChar(targetTutorName.charAt(0))) {
                valid = false;
                System.out.println("Invalid. Name can't be null...");
            } else {
                for (int i = 0; i < targetTutorName.length(); i++) {
                    if (!Character.isSpaceChar(targetTutorName.charAt(i))) {
                        if (!Character.isAlphabetic(targetTutorName.charAt(i))) {
                            valid = false;
                            System.out.println("Invalid. Please enter a valid name...");
                            break;
                        }
                    }
                }
            }

        } while (!valid);
        
        StringBuilder capitalizedTutorName = new StringBuilder();
        capitalizedTutorName.append(Character.toUpperCase(targetTutorName.charAt(0)));

        for (int i = 1; i < targetTutorName.length(); i++) {
            char currentChar = targetTutorName.charAt(i);
            char previousChar = targetTutorName.charAt(i - 1);

            if (Character.isSpaceChar(previousChar)) {
                capitalizedTutorName.append(Character.toUpperCase(currentChar));
            } else {
                capitalizedTutorName.append(currentChar);
            }
        }

        String result = capitalizedTutorName.toString();

        return result;
    }

    public String inputTargetTutorEmail() {
        boolean valid;
        String targetTutorEmail;

        do {
            valid = true;
            System.out.print("Please enter the tutor email you want to search: ");
            targetTutorEmail = sc.nextLine();
            if (targetTutorEmail.isEmpty() || Character.isSpaceChar(targetTutorEmail.charAt(0))) {
                valid = false;
            }
            String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = emailPat.matcher(targetTutorEmail);
            valid = matcher.find();

            if (!valid) {
                System.out.println("Invalid. Please enter valid email format...");
            }
        } while (!valid);

        return targetTutorEmail;
    }

    //------------------------------------------------------------
    public int modifyTutorDomain() {

        boolean valid;
        int selection = -1;

        do {
            valid = true;
            try {
                System.out.println("Tutor domain knowledge: ");
                System.out.println("1. Add");
                System.out.println("2. Remove");
                System.out.println("0. Exit");
                System.out.print("Your Selection : ");
                selection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                valid = false;
            }
            if (selection < 0 || selection > 2) {
                valid = false;
            }

            if (!valid) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (!valid);

        return selection;
    }

    public int removeDomainKnowledgeSelection(ListInterface<String> domains) {

        boolean valid;
        int selection = 0;

        do {
            valid = true;
            try {
                System.out.print("Which domain you want to remove (index) : ");
                selection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                valid = false;
            }
            if (selection < 1 || selection > domains.getNumberOfEntries()) {
                valid = false;
            }

            if (!valid) {
                System.out.println("Invalid input. Please enter a valid number.");
            }

        } while (!valid);

        return selection;
    }

    //------------------------------------------------------------
    public char removeTutorConfirmation(Tutor tutor) {

        boolean valid;

        displayListDivider();
        System.out.println(tutor);
        displayListDivider();
        char yOrN = 'N';

        do {
            valid = true;
            try {
                System.out.print("Are you sure want to delete the above tutor? ");
                yOrN = sc.nextLine().toUpperCase().charAt(0);
            } catch (Exception e) {
                valid = false;
            }
            if (yOrN != 'Y' && yOrN != 'N') {
                valid = false;
            }
            if (!valid) {
                System.out.println("Please enter a valid input...");
            }

        } while (!valid);

        return yOrN;

    }

    public char undoRemoveTutorConfirmation() {

        boolean valid;
        char yOrN = 'N';

        do {
            valid = true;
            try {
                System.out.print("Are you sure want to undo the deletion? ");
                yOrN = sc.nextLine().toUpperCase().charAt(0);
            } catch (Exception e) {
                valid = false;
            }
            if (yOrN != 'Y' && yOrN != 'N') {
                valid = false;
            }
            if (!valid) {
                System.out.println("Please enter a valid input...");
            }

        } while (!valid);

        return yOrN;
    }

    //------------------------------------------------------------
    public String inputOneDomain() {

        String domain = null;
        System.out.println("Input the tutor domain knowledge : ");
        System.out.println("1. Accounting");
        System.out.println("2. Add-Math");
        System.out.println("3. Biology");
        System.out.println("4. Chemistry");
        System.out.println("5. Physics");
        System.out.println("0. Exit");

        int tutorDomainSelection = 0;
        boolean valid;
        do {
            valid = true;
            try {
                System.out.print("Your input: ");
                tutorDomainSelection = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                valid = false;
            }

            if (tutorDomainSelection < 0 || tutorDomainSelection > 5) {
                valid = false;
            } else if (tutorDomainSelection == 0) {
                System.out.println("Exit...");
                return null;
            } else {
                domain = getDomainName(tutorDomainSelection);
            }

            if (!valid) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (!valid);

        return domain;

    }

    //------------------------------------------------------------
    public void displayAllTutors(ListInterface<Tutor> tutorList) {

        displayRecordHeader();
        Iterator<Tutor> it = tutorList.getIterator();

        while (it.hasNext()) {
            Tutor tutor = it.next();
            System.out.println(tutor);
        }

    }

    public void displayRecordHeader() {

        System.out.printf("%-8s %-20s %-7s %-15s %-14s %-20s %-8s %-25s %-50s\n", "Tutor ID", "Name", "Gender", "IC", "Phone Number", "Email", "Salary", "Education Level", "Domain Knowledge");
        displayListDivider();
    }

    //------------------------------------------------------------  
    public char nextOrExit() {

        boolean valid;
        char yOrN = 'E';

        do {
            valid = true;
            try {
                System.out.print("Next or exit (N: Next, E: Exit) ： ");
                yOrN = sc.nextLine().toUpperCase().charAt(0);
            } catch (Exception e) {
                valid = false;
            }
            if (yOrN != 'N' && yOrN != 'E') {
                valid = false;
            }
            if (!valid) {
                System.out.println("Please enter N(next) or E(Exit)...");
            }

        } while (!valid);

        return yOrN;

    }

    public char confirmationForUndo() {

        boolean valid;
        char yOrN = 'N';

        do {
            System.out.println("All the delection cannot be undo after you exit the system...");
            valid = true;
            try {
                System.out.print("Are you confirm want to exit this subsystem (Y or N) : ");
                yOrN = sc.nextLine().toUpperCase().charAt(0);
            } catch (Exception e) {
                valid = false;
            }
            if (yOrN != 'Y' && yOrN != 'N') {
                valid = false;
            }
            if (!valid) {
                System.out.println("Please enter Y or N...");
            }

        } while (!valid);

        return yOrN;

    }

    public void removeFunctionMessage() {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("              The tutor still enrolled in teaching...");
        System.out.println("  Make sure the tutor is not teaching any class before you remove it  ");
        System.out.println("----------------------------------------------------------------------");
    }

    public void tutorNotExisting() {
        System.out.println("-----------------------------------------");
        System.out.println("This tutor is not existing in the list...");
        System.out.println("-----------------------------------------");
    }

    public void displayEmptyList() {
        System.out.println("-----------------------------------------");
        System.out.println("There is no tutor in the list...");
        System.out.println("-----------------------------------------");
    }

}
