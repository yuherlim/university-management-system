/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ArrayList;
import adt.CircularDoublyLinkedList;
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
            System.out.print("Enter tutor name : ");
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

    public ListInterface<String> inputTutorDomain(ListInterface<String> domains) {

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

        if (tutorList.isEmpty()) {

            System.out.println("There are no tutor in the list.");

        } else {

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

    public void displayModifyTutorMenu() {
        MessageUI.tutorTopDivider();
        System.out.println("Modify tutor");
        MessageUI.courseBtmDivider();
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

    public ListInterface<Tutor> modifyTutorDetails(ListInterface<Tutor> tutorList, Tutor tutor, int selection) {

        int targetPosition = ((CircularDoublyLinkedList) tutorList).locatePosition(tutor);
        switch (selection) {
            case 1:
                tutor.setName(inputTutorName());
                break;
            case 2:
                tutor.setPhoneNum(inputTutorPhoneNum());
                break;
            case 3:
                tutor.setEmail(inputTutorEmail());
                break;
            case 4:
                tutor.setEducationLevel(inputTutorEduLevel());
                break;
            case 5:
                modifyTutorDomain(tutorList, tutor);
                break;

        }
        tutorList.replace(targetPosition, tutor);

        return tutorList;

    }

    public void modifyTutorDomain(ListInterface<Tutor> tutorList, Tutor tutor) {

        ListInterface<String> domains = tutor.getDomainKnowledgeList();
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
                        domains = inputTutorDomain(domains);
                        break;
                    case 2:
                        domains = removeDomainKnowledge(domains);
                        break;
                    default:
                        System.out.println("Tutor domain knowledged updated...");
                }
            }
        } while (!valid);

    }

    public ListInterface<String> removeDomainKnowledge(ListInterface<String> domains) {

        int i = 1;
        Iterator<String> it = domains.getIterator();
        String domain = null;

        while (it.hasNext()) {
            domain = it.next();
            System.out.println(i + ".");
            System.out.println(domain);
            i++;
        }

        boolean valid = true;
        int selection = -1;

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
                    domains.remove(selection -1);
                } else {
                    System.out.println("domain is not in the list...");
                }
            }
        } while (!valid);

        return domains;
    }

}
