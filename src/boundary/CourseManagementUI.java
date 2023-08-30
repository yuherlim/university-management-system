/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import adt.ListInterface;
import control.CourseInputValidator;
import entity.Course;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author syshe
 */
public class CourseManagementUI {

    Scanner scanner = new Scanner(System.in);
    private CourseInputValidator validator = new CourseInputValidator();
    MessageUI messageUI = new MessageUI();

    public int getMenuChoice() {
        int selection = scanner.nextInt();
        System.out.println("\nMAIN MENU");
        System.out.println("1. Add new course");
        System.out.println("2. View all courses");
        System.out.println("3. Modify a course");
        System.out.println("4. Remove a course");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        return selection;
    }

    public String inputCourseCode() {
        boolean valid = true;
        String code;
        do {
            System.out.println("Enter course code: ");
            code = scanner.nextLine();
            code = code.toUpperCase();
            valid = validator.courseCodeCheck(code);
        } while (valid != true);

        return code;
    }

    public String inputCourseName() {
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();

        return name;
    }

    public int inputDomain() {
        int domainSelection = 0;
        boolean valid = true;
        do {
            try {
                System.out.println("\nInput the domain knowledge required: ");
                System.out.println("1. Accounting");
                System.out.println("2. Add.Math");
                System.out.println("3. Biology");
                System.out.println("4. Chemistry");
                System.out.println("5. Physics");         
                System.out.println("0. Quit");
                System.out.println("Your input: ");
                domainSelection = scanner.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Please enter a valid domain knowledge number");
                scanner.nextLine();
                valid = false;
            }
        } while (!valid);
        return domainSelection;
    }
    
    public String[] inputDomainLoop(int index, String[] domainList){
        int domainSelection;
        String[] domains = new String[domainList.length];
        do {
            domainSelection = inputDomain();
            boolean notDuplicated = true;
            if (domainSelection >= 1 && domainSelection <= domainList.length) {
                if (index > 0) {
                    notDuplicated = validator.checkDuplicateProg(domains, domainList[domainSelection - 1]);
                }

            } else if (domainSelection == 0) {
                if (index > 0) {
                    System.out.println("Exiting domain input");
                } else {
                    System.out.println("Domain list cannot be empty.");
                    domainSelection = -1;                    
                }
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }

            if ((domainSelection >= 1 && domainSelection <= domainList.length) && notDuplicated) {
                domains[index] = domainList[domainSelection - 1];
                ++index;

            }
        } while (domainSelection != 0);
                
        String[] domainReturn = new String[index];
        
        for(int i=0; i<domainReturn.length;i++){
            domainReturn[i] = domains[i];
        }        
         
        return domainReturn;
    }

    public int inputCreditHour() {
        boolean valid = true;
        int creditHR = 0;
        do {
            try {
                System.out.println("Enter the course's credit hour: ");
                creditHR = scanner.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Please enter a valid credit hour in integer");
                scanner.nextLine();
                valid = false;
            }
        } while (!valid);
        return creditHR;
    }

    public double inputFeePerCreditHour() {
        double feePerCH = 0.0;
        boolean valid = true;
        do {
            try {
                System.out.println("Enter the course's fee per credit hour: ");
                feePerCH = scanner.nextDouble();
                valid = true;
            } catch (Exception e) {
                System.out.println("Please enter a valid fee");
                scanner.nextLine();
                valid = false;
            }
        } while (!valid);

        return feePerCH;
    }

    public int inputProgramme() {
        int programmeSelection = 0;
        boolean valid = true;
        do {
            try {
                System.out.println("\nInput the programme that are taking the course");
                System.out.println("1. RSW");
                System.out.println("2. RDS");
                System.out.println("3. RSD");
                System.out.println("4. ABC");
                System.out.println("5. DEF");
                System.out.println("6. GHI");
                System.out.println("0. Quit");
                System.out.println("Your input: ");

                programmeSelection = scanner.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println("Please enter a valid programme number");
                scanner.nextLine();
                valid = false;
            }
        } while (!valid);
        return programmeSelection;
    }
    
    

    public void displayAllCourse(ListInterface<Course> courseList) {
        for (int i = 1; i <= courseList.getNumberOfEntries(); i++) {
            System.out.println(courseList.getEntry(i));

        }
    }
}
