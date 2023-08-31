/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import control.TutorInputValidator;
import control.CourseInputValidator;
import java.util.*;
import utility.MessageUI;

/**
 *
 * @author ong58
 */
public class TeachingAssignmentManagementUI {

    Scanner scanner = new Scanner(System.in);
    private TutorInputValidator tutorInputValidator = new TutorInputValidator();
    private CourseInputValidator courseInputValidator = new CourseInputValidator();

    public int getMenuChoice() {
        boolean valid = false;
        int selection = -1;
        while (true) {
            try {
                System.out.println("\nMAIN MENU");
                System.out.println("1. Assign Tutor");
                System.out.println("2. Modify Assigned Tutor");
                System.out.println("3. Find Teaching Assignment");
                System.out.println("4. List Teaching Assignent");
                System.out.println("5. Generate Report");
                System.out.println("0. Quit");
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= 4) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
            } catch (Exception e) {
                System.out.println("Please enter a valid selection in integer");
                scanner.nextLine();
                
            }
        } 
        return selection;
    }
    
      public int getAssigmTutorOption() {
        boolean valid = false;
        int selection = -1;
        while (true) {
            try {
                System.out.println("\nAssign Tutor menu");
                System.out.println("1. Assign Tutor by course");
                System.out.println("2. Assign Tutor by Available slot");
                System.out.println("0. Quit");
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
            } catch (Exception e) {
                System.out.println("Please enter a valid selection in integer");
                scanner.nextLine();
            }
        } 
        return selection;
    }
    
    

}
