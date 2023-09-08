/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author temp
 */
public class UniversityManagementSystemUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("TAR University");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("University Management System Main Menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Programme Management");
                System.out.println("2. Course Management");
                System.out.println("3. Tutor Management");
                System.out.println("4. Teaching Assignment Management");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 4) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();

            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                MessageUI.pause();
                scanner.nextLine();

            }
        }
        return selection;
    }

}
