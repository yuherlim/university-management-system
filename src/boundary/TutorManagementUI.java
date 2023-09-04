/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.*;
import control.*;
import entity.*;
import utility.*;
import java.util.Iterator;
import java.util.Scanner;


/**
 *
 * @author ASUS
 */
public class TutorManagementUI {

    Scanner sc = new Scanner(System.in);
    private TutorInputValidator validator = new TutorInputValidator();

    String tutorName;
    char tutorGender;
    String tutorIC;
    String tutorPhoneNum;
    String tutorEmail;
    double tutorSalary;

    //------------------------------------------------------------
    
    public int tutorMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Add new tutor");
        System.out.println("2. Remove tutor");
        System.out.println("3. Find tutor");
        System.out.println("4. Amend tutor details");
        System.out.println("5. List all tutor");
        System.out.println("6. Filter tutors");
        System.out.println("7. Generate report");
        System.out.println("0. Exit");

        System.out.print("Your selection : ");
        int selection = sc.nextInt();

        sc.nextLine();
        return selection;

    }
    
    public int removeTutorMenu() {

        boolean valid = true;
        int selection = 0;

        do {
            try {
                System.out.println("1. Delete tutor: ");
                System.out.println("2. Undo deletion(once per time)");
                System.out.println("0. Exit");
                System.out.print("Your selection : ");
                selection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid. Please select a valid selection...");
                sc.nextLine();
                valid = false;
            }
            if (selection < 0 || selection > 2) {
                System.out.println("Invalid. Please select number between 0 to 2...");
                valid = false;
            }
        } while (!valid);

        return selection;
    }
    
    public int searchTutorMenu() {
        int searchSelection = 0;
        boolean valid = false;

        do {
            try {
                System.out.println("Search tutor");
                System.out.println("By: ");
                System.out.println("1. ID");
                System.out.println("2. Name");
                System.out.println("3. Email");
                System.out.println("0. Exit");
                System.out.print("Your selection : ");
                searchSelection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid. Please select a valid selection...");
                sc.nextLine();
                valid = false;
            }
            if (searchSelection < 0 || searchSelection > 3) {
                System.out.println("Invalid. Please select number between 0 to 3...");
                valid = false;
            }

            sc.nextLine();

        } while (!valid);

        return searchSelection;
    }
    
    public int modifyTutorSelection(Tutor tutor) {

        boolean valid = false;
        int selection = 0;

        do {
            try {
                System.out.println(tutor);
                System.out.println("1. Modify tutor name");
                System.out.println("2. Modify tutor phone number");
                System.out.println("3. Modify tutor email");
                System.out.println("4. Modify tutor education level");
                System.out.println("5. Modify tutor domain knowledge");
                System.out.println("0. Exit");
                System.out.print("Your selection : ");
                selection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid. Please select a valid selection...");
                sc.nextLine();
                valid = false;
            }
            if (selection < 0 || selection > 5) {
                System.out.println("Invalid. Please select number between 0 to 5...");
                valid = false;
            }

            sc.nextLine();

        } while (!valid);
        return selection;
    }
    
    public int filterTutorMenu() {
        int filterSelection = 0;
        boolean valid = false;

        do {
            try {
                System.out.println("Filter tutor");
                System.out.println("1. Filter by gender");
                System.out.println("2. Filter by education level");
                System.out.println("3. Filter by domain knowledge");
                System.out.println("0. Exit");
                System.out.print("Your selection: ");
                filterSelection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid. Please select a valid selection...");
                sc.nextLine();
                valid = false;
            }
            if (filterSelection < 0 || filterSelection > 3) {
                System.out.println("Invalid. Please select number between 0 to 3...");
                valid = false;
            } else if (filterSelection == 0) {
                System.out.println("Exit filter function...");
            }

            sc.nextLine();

        } while (!valid);

        return filterSelection;
    }
    
    public int tutorReportMenu(){
        int reportSelection = 0;
        boolean valid = false;

        do {
            try {
                System.out.println("Tutor report");
                System.out.println("1. Sort by tutor name");
                System.out.println("2. Sort by tutor's salary");
                System.out.println("0. Exit");
                System.out.print("Your selection: ");
                reportSelection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid. Please select a valid selection...");
                sc.nextLine();
                valid = false;
            }
            if (reportSelection < 0 || reportSelection > 2) {
                System.out.println("Invalid. Please select number between 0 to 2...");
                valid = false;
            } else if (reportSelection == 0) {
                System.out.println("Exit generate report function...");
            }

            sc.nextLine();

        } while (!valid);

        return reportSelection;
        
    }
    
    //------------------------------------------------------------

    public String inputTutorName() {
        boolean valid = true;

        do {
            System.out.print("Enter tutor name : ");
            tutorName = sc.nextLine();
            valid = validator.checkTutorName(tutorName);
        } while (!valid);

        return tutorName;
    }

    public char inputTutorGender() {
        boolean valid = true;

        do {
            System.out.print("Enter tutor gender (F=Female,M=Male) : ");
            tutorGender = sc.nextLine().toUpperCase().charAt(0);
            valid = validator.checkTutorGender(tutorGender);
        } while (!valid);

        return tutorGender;
    }

    public String inputTutorIC() {
        boolean valid = true;

        do {
            System.out.print("Enter tutor IC No (without - ) : ");
            tutorIC = sc.nextLine();
            valid = validator.checkTutorIC(tutorIC, tutorGender);

        } while (!valid);

        return tutorIC;
    }

    public String inputTutorPhoneNum() {
        boolean valid = true;

        do {
            System.out.print("Enter tutor phone number(01x-xxxxxxx) : ");
            tutorPhoneNum = sc.nextLine();
            valid = validator.checkTutorPhoneNum(tutorPhoneNum);
        } while (!valid);

        return tutorPhoneNum;
    }

    public String inputTutorEmail() {
        boolean valid = true;

        do {
            System.out.print("Enter tutor email : ");
            tutorEmail = sc.nextLine();
            valid = validator.checkTutorEmail(tutorEmail);
        } while (!valid);

        return tutorEmail;
    }

    public double inputTutorSalary() {
        boolean valid = false;

        do {
            try {
                System.out.print("Enter tutor salary : ");
                tutorSalary = sc.nextDouble();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid. Please select a valid salary...");
                sc.nextLine();
                valid = false;
            }
        } while (!valid);

        return tutorSalary;

    }

    public String inputTutorEduLevel() {
        boolean valid = true;
        int tutorEduLevelSelection = 0;
        String educationLevel = "";
        do {
            try {
                System.out.println("Select tutor education level : ");
                System.out.println("1. Diploma");
                System.out.println("2. Bachelor's Degree");
                System.out.println("3. Master's Degree");
                System.out.println("4. phD");
                System.out.print("Your input : ");
                tutorEduLevelSelection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid. Please select a valid education level...");
                sc.nextLine();
                valid = false;
            }
            if (tutorEduLevelSelection < 1 || tutorEduLevelSelection > 4) {
                System.out.println("Invalid. Please select number between 1 to 4...");
                valid = false;
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

    public void inputTutorDomain(ListInterface<String> domains) {

        do {
            System.out.println("Input the tutor domain knowledge : ");
            System.out.println("1. Accounting");
            System.out.println("2. Add.Math");
            System.out.println("3. Biology");
            System.out.println("4. Chemistry");
            System.out.println("5. Physics");

            int tutorDomainSelection = 0;
            boolean valid = true;
            do {
                try {
                    System.out.print("Your input: ");
                    tutorDomainSelection = sc.nextInt();
                    sc.nextLine(); // Consume the newline character
                    valid = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    valid = false;
                }

                if (tutorDomainSelection < 1 || tutorDomainSelection > 5) {
                    System.out.println("Invalid. Please select a number between 1 to 5...");
                    valid = false;
                } else {
                    String domain = getDomainName(tutorDomainSelection);

                    if (!domains.contains(domain)) {
                        domains.add(domain);
                        System.out.println("Domain added: " + domain);
                    } else {
                        System.out.println("This domain is already selected.");
                    }

                }
            } while (!valid);
            
            System.out.print("Do you want to select more domain knowledge? (Y or N)");
        } while (sc.next().equalsIgnoreCase("Y"));

    }
    
    //------------------------------------------------------------

    public String getDomainName(int selection) {
        switch (selection) {
            case 1:
                return "Accounting";
            case 2:
                return "Add.Math";
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
        System.out.print("Please enter the tutor ID you want to search (T000): ");
        String targetTutorID = sc.nextLine();

        return targetTutorID;
    }

    public String inputTargetTutorName() {
        boolean valid = false;
        String targetTutorName;
        do {
            System.out.print("Please enter the tutor name you want to search: ");
            targetTutorName = sc.nextLine();
            valid = validator.checkTutorName(targetTutorName);
        } while (!valid);

        return targetTutorName;
    }

    public String inputTargetTutorEmail() {
        boolean valid = false;
        String targetTutorEmail;

        do {
            System.out.print("Please enter the tutor email you want to search: ");
            targetTutorEmail = sc.nextLine();
            valid = validator.checkTutorEmail(targetTutorEmail);
        } while (!valid);

        return targetTutorEmail;
    }
    
    //------------------------------------------------------------

    public void modifyTutorDomain(ListInterface<String> domains) {

        
        boolean valid = true;
        int selection = -1;
        do {
            try {
                System.out.println("Tutor domain knowledge: ");
                System.out.println("1. Add");
                System.out.println("2. Remove");
                System.out.println("0. Exit");
                System.out.print("Your Selection : ");
                selection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                valid = false;
            }

            if (selection < 0 || selection > 2) {
                System.out.println("Invalid. Please select a number between 0 to 2...");
                valid = false;
            } else {
                switch (selection) {
                    case 1:
                        inputTutorDomain(domains);
                        break;
                    case 2:
                        removeDomainKnowledge(domains);
                        break;
                    default:
                        System.out.println("Tutor domain knowledged updated...");
                }
            }
        } while (!valid);

    }

    public void removeDomainKnowledge(ListInterface<String> domains) {

        int i = 1;
        Iterator<String> it = domains.getIterator();
        String domain = null;

        while (it.hasNext()) {
            domain = it.next();
            System.out.print(i + ".");
            System.out.println(domain);
            i++;
        }

        boolean valid = true;
        int selection = 0;

        do {
            try {
                System.out.print("Which domain you want to remove (index) : ");
                selection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                valid = false;
            }
            if (selection < 1 || selection > domains.getNumberOfEntries()) {
                System.out.println("Invalid. Please select a valid number...");
                valid = false;
            } else {
                if (domains.contains(domain)) {
                    domains.remove(selection);
                } else {
                    System.out.println("domain is not in the list...");
                }
            }
        } while (!valid);

    }
    
    //------------------------------------------------------------

    public char removeTutorConfirmation(Tutor tutor) {

        System.out.println(tutor);
        System.out.print("Are you sure want to delete the above tutor? ");
        return sc.nextLine().toUpperCase().charAt(0);

    }
    
    public char undoRemoveTutorConfirmation() {

        sc.nextLine();
        System.out.print("Are you sure want to undo the deletion? ");
        
        return sc.nextLine().toUpperCase().charAt(0);
    }

    //------------------------------------------------------------
    
    public String inputOneDomain() {

        String domain = null;
        System.out.println("Input the tutor domain knowledge : ");
        System.out.println("1. Accounting");
        System.out.println("2. Add.Math");
        System.out.println("3. Biology");
        System.out.println("4. Chemistry");
        System.out.println("5. Physics");
        System.out.println("0. Exit");

        int tutorDomainSelection = 0;
        boolean valid = true;
        do {
            try {
                System.out.println("Your input: ");
                tutorDomainSelection = sc.nextInt();
                sc.nextLine();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                valid = false;
            }

            if (tutorDomainSelection < 0 || tutorDomainSelection > 5) {
                System.out.println("Invalid. Please select a number between 1 to 5...");
                valid = false;
            } else if (tutorDomainSelection == 0) {
                System.out.println("Exit...");
                return null;
            } else {
                domain = getDomainName(tutorDomainSelection);
            }
        } while (!valid);

        return domain;

    }
    
    //------------------------------------------------------------
    
    public void displayAllTutors(ListInterface<Tutor> tutorList) {

        if (tutorList.isEmpty()) {

            System.out.println("There are no tutor in the list.");

        } else {

            System.out.printf("%-8s %-20s %-7s %-15s %-14s %-20s %-8s %-25s %-50s\n", "Tutor ID", "Name", "Gender", "IC", "Phone Number", "Email", "Salary", "Education Level", "Domain Knowledge");
            Iterator<Tutor> it = tutorList.getIterator();

            while (it.hasNext()) {
                Tutor tutor = it.next();
                System.out.println(tutor);
            }
        }
    }
    
    //------------------------------------------------------------  
    
    public char nextOrExit(){
        
        sc.nextLine();
        System.out.println("Next or exit (N: Next, E: Exit) ： ");
        return sc.nextLine().toUpperCase().charAt(0);
    }
}
