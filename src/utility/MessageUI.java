/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.util.Scanner;

/**
 *
 * @author Yu, Sia Yeong Sheng
 */
public class MessageUI {

    public static void displayInvalidChoiceMessage() {
        System.out.println("\nInvalid choice");
    }

    public static void displayExitMessage() {
        System.out.println("\nExiting system");
    }
    
    public static void displayExit(){
        System.out.println("\nExiting");
    }
    
    public static void pause(){
        System.out.println("Press any key to continue...");
        new Scanner(System.in).nextLine();
    }
    
    public static void courseDeleteMsg(){
        System.out.println("Course Deleted");
        System.out.println("Press any key to continue...");
        new Scanner(System.in).nextLine();
    }
    
    public static void courseUndoDeleteMsg(){
        System.out.println("Undo deletion successed");
        System.out.println("Press any key to continue...");
        new Scanner(System.in).nextLine();
    }
    
    public static void courseModificationMsg(){
        System.out.println("Modification succeed");
        System.out.println("Press any key to continue...");
        new Scanner(System.in).nextLine();
    }
    
    
    public static void courseModificationFailMsg(){
        System.out.println("Modification cancelled");
        System.out.println("Press any key to continue...");
        new Scanner(System.in).nextLine();
    }
    
    public static void savingIntoFile(){
        System.out.println("Saving updates");
        System.out.println("Press any key to continue...");
        new Scanner(System.in).nextLine();
    }
    
    public static void nonExistCourse(){
        System.out.println("Course doesn't exist");
        System.out.println("Press any key to continue...");
        new Scanner(System.in).nextLine();
    }
    
    public static void courseTopDivider(){
        System.out.println("\n\n\n\n---------------------------------------------------------------------------------------------");
    }
    
    public static void courseBtmDivider(){
        System.out.println("---------------------------------------------------------------------------------------------");

    }
    
    public static void tutorTopDivider(){
        System.out.println("\n\n\n----------------------------------");
    }
    
    public static void tutorBtmDivider(){
        System.out.println("----------------------------------");
    }
    
     public static void TeachingAssignmentTopDivider(){
        System.out.println("\n\n\n\n---------------------------------------------------------------------------------------------");
    }
    
    public static void TeachingAssignmentBtmDivider(){
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    } 

    

