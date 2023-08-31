/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ArrayList;
import adt.ListInterface;
import control.TutorInputValidator;
import entity.Tutor;
import java.util.Iterator;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author ASUS
 */
public class TutorManagementUI {

    Scanner sc = new Scanner(System.in);
    private TutorInputValidator validator = new TutorInputValidator();
    MessageUI messageUI = new MessageUI();

    String tutorName;
    char tutorGender;
    String tutorIC;
    String tutorPhoneNum;
    String tutorEmail;

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

        int selection = sc.nextInt();
        return selection;

    }

    public String inputTutorName() {
        boolean valid = true;

        do {
            System.out.println("Enter tutor name : ");
            tutorName = sc.nextLine();
            valid = validator.checkTutorName(tutorName);
        } while (!valid);

        return tutorName;
    }

    public char inputTutorGender() {
        boolean valid = true;

        do {
            System.out.println("Enter tutor gender (F=Female,M=Male) : ");
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

    public String inputTutoerEduLevel() {
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
                System.out.println("Your input: ");
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

    public ArrayList<String> inputTutorDomain() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> domains = new ArrayList<>();

        do {
            System.out.println("\nInput the tutor domain knowledge : ");
            System.out.println("1. Accounting");
            System.out.println("2. Add.Math");
            System.out.println("3. Biology");
            System.out.println("4. Chemistry");
            System.out.println("5. Physics");

            int tutorDomainSelection = 0;
            boolean valid = true;
            do {
                try {
                    System.out.println("Your input: ");
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

            System.out.println("Do you want to select more domain knowledge? (Y or N)");
        } while (sc.next().equalsIgnoreCase("Y"));

        sc.close();
        return domains;
    }

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

    public void displayAllTutors(ListInterface<Tutor> tutorList) {
        Iterator<Tutor> it = tutorList.getIterator();

        while (it.hasNext()) {
            Tutor tutor = it.next();
            System.out.printf("%-5s %-15s %c %-12s %-12s %-20s %-20s", tutor.getTutorID(), tutor.getName(), tutor.getGender(), tutor.getIc(), tutor.getPhoneNum(), tutor.getEmail(), tutor.getEducationLevel());
            for (int i = 1; i <= tutor.getDomainKnowledgeList().getNumberOfEntries(); i++) {
                System.out.printf("%-10s ", tutor.getDomainKnowledgeList().getEntry(i));
            }
            System.out.println("");
        }
    }

    public int searchTutor() {
        int searchSelection = 0;
        boolean valid = false;

        do {
            try {
                System.out.println("Search tutor by: ");
                System.out.println("1. ID");
                System.out.println("2. Name");
                System.out.println("3. Email");
                System.out.println("0. Exit");
                System.out.print("Your selection: ");
                searchSelection = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid. Please select a valid education level...");
                sc.nextLine();
                valid = false;
            }
            if (searchSelection < 1 || searchSelection > 3) {
                System.out.println("Invalid. Please select number between 1 to 3...");
                valid = false;
            }
            
            sc.nextLine();

        } while (!valid);

        return searchSelection;
    }

    public String inputTargetTutorID() {
        System.out.println("Please enter the tutor ID you want to search (T000): ");
        String targetTutorID = sc.nextLine();
        
        return targetTutorID;
    }

    public String inputTargetTutorName() {
        boolean valid = false;
        String targetTutorName;
        do {
            System.out.println("Please enter the tutor name you want to search: ");
            targetTutorName = sc.nextLine();
            valid = validator.checkTutorName(targetTutorName);
            if (!valid) {
                System.out.println("Invalid Name. Please enter again...");
            }
        } while (!valid);
        
        return targetTutorName;
    }

    public String inputTargetTutorEmail() {
        boolean valid = false;
        String targetTutorEmail;

        do {
            System.out.println("Please enter the tutor email you want to search: ");
            targetTutorEmail = sc.nextLine();
            valid = validator.checkTutorEmail(targetTutorEmail);
            if (!valid) {
                System.out.println("Invalid Email. Please enter again...");
            }
        } while (!valid);
        
        return targetTutorEmail;
    }

    public Tutor searchTutorDetails(ListInterface<Tutor> tutorList, String searchingString, int index) {

        Tutor target = null;
        Iterator<Tutor> it = tutorList.getIterator();

        while (it.hasNext()) {
            target = it.next();
            switch (index) {
                case 1:
                    if (target.getTutorID().equals(searchingString)) {
                        return target;
                    }
                    break;
                case 2:
                    if (target.getName().equals(searchingString)) {
                        return target;
                    }
                    break;
                default:
                    if (target.getEmail().equals(searchingString)) {
                        return target;
                    }
                    break;
            }
        }

        return null;
    }
}
