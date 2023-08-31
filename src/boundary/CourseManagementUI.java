/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import adt.ArrayList;
import adt.CircularDoublyLinkedList;
import adt.ListInterface;
import adt.StackInterface;
import control.CourseInputValidator;
import entity.Course;
import java.util.Iterator;
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
    
    public void displayAddCourseMsg(){
        System.out.println("-------------------------------");
        System.out.println("Adding new course");
        System.out.println("-------------------------------");
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
    
    public ListInterface<String> programmeInputList(String[] programmes){
            int programmeSelection;
            ListInterface<String> result = new ArrayList<>();
        do {
            programmeSelection = inputProgramme();
            boolean notDuplicated = true;
            if (programmeSelection >= 1 && programmeSelection <= programmes.length) {
                if (result.getNumberOfEntries() > 0) {
                    notDuplicated = validator.checkDuplicateProg(result, programmes[programmeSelection - 1]);
                }

            } else if (programmeSelection == 0) {
                if (result.getNumberOfEntries() > 0) {
                    System.out.println("Exiting");
                } else {
                    System.out.println("Programme list cannot be empty.");
                    programmeSelection = -1;
                }
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }

            if ((programmeSelection >= 1 && programmeSelection <= programmes.length) && notDuplicated) {
                result.add(programmes[programmeSelection - 1]) ;
            }
        } while (programmeSelection != 0);
        
      
        return result;
    }
    
    public Course searchCourseByCode(ListInterface<Course> courseList){
        System.out.println("Enter course code: ");
        String code = scanner.nextLine();
        code = code.toUpperCase();
       
        Iterator<Course> it = courseList.getIterator();
        Course target;
        while(it.hasNext()){
            target = it.next();
           
            if(target.getCourseCode().equals(code)){
                return target;
            }
        }
        return null;
    }
    
    public ListInterface<Course> filterCourseByProgramme(ListInterface<Course> courseList){
        System.out.println("Enter programme: ");
          String programme = scanner.nextLine();
        ListInterface<Course> result = new CircularDoublyLinkedList<>();
        
        Iterator<Course> it = courseList.getIterator();
        Course target;
        while(it.hasNext()){
            target = it.next();
            for(int i=1;i<=target.getProgrammes().getNumberOfEntries();i++){
                String programmeCode = target.getProgrammes().getEntry(i);
                if(programmeCode.equals(programme)){
                    result.add(target);
                }
            }
        }
        
        return result;   
    }
       
    public void displayAllCourse(ListInterface<Course> courseList) {
        Iterator<Course> it = courseList.getIterator();
        Course target;
        int count = 1;
        System.out.printf("%5s %-15s %-20s %-10s %20s\n", "Count ","Course Code","Course Name","Credit Hour","Take By Programme");
        while(it.hasNext()){
            target = it.next();
            String programmes = "";
            for(int i=1; i<=target.getProgrammes().getNumberOfEntries(); i++){
                programmes += target.getProgrammes().getEntry(i) + ",";
            }
            programmes += "\b";
            
            System.out.printf("%-7d %-16s %-20s %-10d %20s\n", count,target.getCourseCode(), target.getCourseName(), target.getCreditHR(),programmes);
            ++count;
        }
      
    }
    
    public int deleteByNo(ListInterface<Course> courseList){
        // this is used by default course list
        int selection = -1;
        do {
            displayAllCourse(courseList);
            System.out.println("Input number to delete(0 to exit): ");
            selection = scanner.nextInt();
            if (selection == 0) {
                break;
            }else if(selection < 0 || selection > courseList.getNumberOfEntries()){
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (selection < 0 || selection > courseList.getNumberOfEntries());
        
        
        return selection;
    }
    
     public Course deleteFilteredListByNo(ListInterface<Course> courseList){
        // this is used by course list filtered by programme
        int selection = -1;
        do {
            displayAllCourse(courseList);
            System.out.println("Input number to delete(0 to exit): ");
            selection = scanner.nextInt();
            if (selection == 0) {
                break;
            }else if(selection < 0 || selection > courseList.getNumberOfEntries()){
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (selection < 0 || selection > courseList.getNumberOfEntries());
        
        
        return courseList.getEntry(selection);
    }
     
     public char undo(StackInterface<Course> courseStack){
         
         System.out.println("-------------------------------");
         System.out.println("Undo deletion");
         System.out.println("-------------------------------");
         System.out.println(courseStack.peek());
         System.out.println("-------------------------------");
         System.out.println("One undo deletion at a time");
         
         System.out.println("\nUndo the course deletion?(Y = yes): ");
         char input = scanner.nextLine().charAt(0);
       
         return input;
     }
    
    public int deleteCourseMenuSelection(){
        int selection;
        
        System.out.println("-------------------------------");
        System.out.println("Removing a course");
        System.out.println("-------------------------------");
        System.out.println("1. Display all course");
        System.out.println("2. Search course by a programme");
        System.out.println("3. Delete a course by code");
        System.out.println("4. Undo deletion(cannot be undo once exit)");
        System.out.println("0. Exit");
        System.out.println("-------------------------------");
        System.out.println("Your selection: ");
        selection = scanner.nextInt();
        scanner.nextLine();
        return selection;
    }
}
