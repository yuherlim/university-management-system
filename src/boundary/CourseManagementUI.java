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
   

    public int getCourseMainMenuChoice() {
        MessageUI.courseTopDivider();
        System.out.println("Course Main Menu");
        MessageUI.courseBtmDivider();
        System.out.println("1. Add new course");       
        System.out.println("2. Modify a course");
        System.out.println("3. Remove a course");
        System.out.println("4. View courses");
        System.out.println("5. View course report");
        System.out.println("0. Quit");
         MessageUI.courseBtmDivider();
        System.out.print("Enter choice: ");
        int selection = scanner.nextInt();
        scanner.nextLine();
        return selection;
    }
    
    public int getReportMenuChoice(){
        MessageUI.courseTopDivider();
        System.out.println("Report Menu");
        MessageUI.courseBtmDivider();
        System.out.println("1. Display report");       
        System.out.println("2. Display report sorted by course code");
        System.out.println("3. Display report sorted by credit hour");
        System.out.println("0. Quit");
         MessageUI.courseBtmDivider();
        System.out.print("Enter choice: ");
        int selection = scanner.nextInt();
        scanner.nextLine();
        return selection;
    }
    
    public void reportSortByCreditHour(ArrayList<Course> courseList, String[] programme){
        for (int i = 0; i < programme.length; i++) {
            System.out.printf("%-10s %-20s %-20s %10s\n", "Programme", "Course Code", "Course Name", "Credit Hour");
            int totalCreditHours = 0;
            boolean firstLine = true;

            for(int h = 1; h <= courseList.getNumberOfEntries(); h++){
                Course course = courseList.getEntry(h);
                for (int j = 1; j <= courseList.getEntry(h).getProgrammes().getNumberOfEntries(); j++) {
                    if (courseList.getEntry(h).getProgrammes().getEntry(j).equals(programme[i])) {
                        if (firstLine) {
                            System.out.printf("%-10s %-20s %-20s %10d\n", programme[i], course.getCourseCode(), course.getCourseName(), course.getCreditHR());
                            totalCreditHours += course.getCreditHR();
                            firstLine = false;
                        } else {
                            System.out.printf("%-10s %-20s %-20s %10d\n", "", course.getCourseCode(), course.getCourseName(), course.getCreditHR());
                            totalCreditHours += course.getCreditHR();
                        }
                    }
                }
            }
            if(totalCreditHours == 0){
                System.out.printf("%-10s %25s",programme[i],"------------Pending for course assignment------------\n");
                System.out.printf("Total Credit Hour: %44d\n\n\n\n", totalCreditHours);
            }else
                System.out.printf("Total Credit Hour: %44d\n\n\n\n", totalCreditHours);
        }

    }
    
    public void reportSortByCode(ArrayList<Course> courseList, String[] programme){
        for (int i = 0; i < programme.length; i++) {
            System.out.printf("%-10s %-20s %-20s %10s\n", "Programme", "Course Code", "Course Name", "Credit Hour");
            int totalCreditHours = 0;
            boolean firstLine = true;

            for(int h = 1; h <= courseList.getNumberOfEntries(); h++){
                Course course = courseList.getEntry(h);
                for (int j = 1; j <= courseList.getEntry(h).getProgrammes().getNumberOfEntries(); j++) {
                    if (courseList.getEntry(h).getProgrammes().getEntry(j).equals(programme[i])) {
                        if (firstLine) {
                            System.out.printf("%-10s %-20s %-20s %10d\n", programme[i], course.getCourseCode(), course.getCourseName(), course.getCreditHR());
                            totalCreditHours += course.getCreditHR();
                            firstLine = false;
                        } else {
                            System.out.printf("%-10s %-20s %-20s %10d\n", "", course.getCourseCode(), course.getCourseName(), course.getCreditHR());
                            totalCreditHours += course.getCreditHR();
                        }
                    }
                }
            }
            if(totalCreditHours == 0){
                System.out.printf("%-10s %25s",programme[i],"------------Pending for course assignment------------\n");
                System.out.printf("Total Credit Hour: %44d\n\n\n\n", totalCreditHours);
            }else
                System.out.printf("Total Credit Hour: %44d\n\n\n\n", totalCreditHours);
        }

    }
    
    public void report(ListInterface<Course> courseList, String[] programme){   
        Course course = new Course();
        for (int i = 0; i < programme.length; i++) {
            Iterator<Course> it = courseList.getIterator();
            System.out.printf("%-10s %-20s %-20s %10s\n", "Programme", "Course Code", "Course Name", "Credit Hour");
            int totalCreditHours = 0;
            boolean firstLine = true;

            while (it.hasNext()) {
                course = it.next();
                for (int j = 1; j <= course.getProgrammes().getNumberOfEntries(); j++) {
                    if (course.getProgrammes().getEntry(j).equals(programme[i])) {
                        if (firstLine) {
                            System.out.printf("%-10s %-20s %-20s %10d\n", programme[i], course.getCourseCode(), course.getCourseName(), course.getCreditHR());
                            totalCreditHours += course.getCreditHR();
                            firstLine = false;
                        } else {
                            System.out.printf("%-10s %-20s %-20s %10d\n", "", course.getCourseCode(), course.getCourseName(), course.getCreditHR());
                            totalCreditHours += course.getCreditHR();
                        }
                    }
                }
            }
            if(totalCreditHours == 0){
                System.out.printf("%-10s %25s",programme[i],"------------Pending for course assignment------------\n");
                System.out.printf("Total Credit Hour: %44d\n\n\n\n", totalCreditHours);
            }else
                System.out.printf("Total Credit Hour: %44d\n\n\n\n", totalCreditHours);
        }

        //each programme ,courses, total credit hour
        
    }
    
    public ListInterface<Course> sortByCode(ListInterface<Course> courseList){
        ListInterface<Course> sorted = convertToArrayList(courseList);
        for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
            for (int j = i+1; j <= sorted.getNumberOfEntries(); j++) {
                if (sorted.getEntry(i).getCourseCode().compareTo(sorted.getEntry(j).getCourseCode()) > 0) {
                    Course temp = sorted.getEntry(i);
                    sorted.replace(i, sorted.getEntry(j));
                    sorted.replace(j, temp);
                }
            }
        }
        return sorted;
    }
    
    public ListInterface<Course> sortByCreditHour(ListInterface<Course> courseList){
        ListInterface<Course> sorted = convertToArrayList(courseList);
        for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
            for (int j = i+1; j <= sorted.getNumberOfEntries(); j++) {
                if (sorted.getEntry(i).getCreditHR() > sorted.getEntry(j).getCreditHR()) {
                    Course temp = sorted.getEntry(i);
                    sorted.replace(i, sorted.getEntry(j));
                    sorted.replace(j, temp);
                }
            }
        }
        return sorted;
    }
    
    public ListInterface<Course> convertToArrayList(ListInterface<Course> courseList){
        ListInterface<Course> presort = new ArrayList<>();
        Iterator<Course> it = courseList.getIterator();
        while (it.hasNext()) {
              Course course = it.next();
              presort.add(course);
        }
        return presort;
    }
   
    
    public int getDisplayCourseMenuSelection(){
        MessageUI.courseTopDivider();
        System.out.println("Display Course");
        MessageUI.courseBtmDivider();
        System.out.println("1. Display all courses");
        System.out.println("2. Display course by specific programme");
        System.out.println("3. Display a specific course");
        System.out.println("0. Exit");
        MessageUI.courseBtmDivider();
        int selection = scanner.nextInt();
        scanner.nextLine();
        return selection;
    }
    
    public void displayCourseByProgramme(ListInterface<Course> courseList){
       ListInterface<Course> filteredList = filterCourseByProgramme(courseList);
       displayAllCourse(filteredList);
    }
    
    public void displayACourse(ListInterface<Course> courseList){
        Course target = searchCourseByCode(courseList);
        System.out.println(target);
        MessageUI.pause();
    }
    
    public void displayAddCourseMsg(){
        MessageUI.courseTopDivider();
        System.out.println("Adding new course");
        MessageUI.courseBtmDivider();
    }
    
    
    public void displayModifyCourseMenuMsg(){
         MessageUI.courseTopDivider();
         System.out.println("Modify course");
         MessageUI.courseBtmDivider();
    }
    
    public int getModifyMenuSelection(Course course){
        System.out.println(course);
        
        MessageUI.courseBtmDivider();
        System.out.println("Modify " + course.getCourseCode());
        MessageUI.courseBtmDivider();
        System.out.println("1. Modify course name");
        System.out.println("2. Modify course required domain knowledge");
        System.out.println("3. Modify credit hour");
        System.out.println("4. Modify course fee per credit hour");
        System.out.println("5. Modify programme list");
        System.out.println("0. Exit");
        MessageUI.courseBtmDivider();
        System.out.println("Enter choice: ");
        int selection = scanner.nextInt();
        scanner.nextLine();
        return selection;   
    }
    
    public ListInterface<Course> modifyCourseName(ListInterface<Course> courseList, Course course){
       
        int targetPos = ((CircularDoublyLinkedList) courseList).locatePosition(course);
        course.setCourseName(inputCourseName());
        courseList.replace(targetPos, course);
        MessageUI.courseModificationMsg();

        return courseList;
    }
    
    public ListInterface<Course> modifyCourseCH(ListInterface<Course> courseList, Course course) {

        int targetPos = ((CircularDoublyLinkedList) courseList).locatePosition(course);
        course.setCreditHR(inputCreditHour());
        courseList.replace(targetPos, course);
        MessageUI.courseModificationMsg();

        return courseList;
    }
    
    public ListInterface<Course> modifyCourseFeePCH(ListInterface<Course> courseList, Course course){
        
        int targetPos = ((CircularDoublyLinkedList) courseList).locatePosition(course);
        course.setFeePCH(inputFeePerCreditHour());
        courseList.replace(targetPos, course);
        MessageUI.courseModificationMsg();
   
       return courseList;
    }
    
     public ListInterface<Course> modifyCourseDomainList(ListInterface<Course> courseList, String[] domainList, Course course) {
       
        int selection = -1;

        do {
            MessageUI.courseTopDivider();
            System.out.println("Modifying " + course.getCourseCode() + " domain knowledge list");
            MessageUI.courseBtmDivider();
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("0. Exit");
            MessageUI.courseBtmDivider();
            System.out.println("Your choice: ");
            selection = scanner.nextInt();
            scanner.nextLine();
            if (selection == 0) {
                MessageUI.displayExit();
                return courseList;
            } else if (selection < 0 || selection > 2) {
                MessageUI.displayInvalidChoiceMessage();
            } else {
                int targetPos = ((CircularDoublyLinkedList) courseList).locatePosition(course);
                ListInterface<String> inputDomainList = course.getRequiredDomainKnowledge();
                int index = inputDomain() - 1; //user input will return int value to access predifined programmes list;
                boolean notExist = true, modification = false;
                notExist = validator.checkExistInList(inputDomainList, domainList[index]);
                if (selection == 1) {
                    if (notExist) {
                        inputDomainList.add(domainList[index]);
                        modification = true;
                    } else {
                        System.out.println("Existing domain");
                    }

                } else if (selection == 2) {
                    if (!notExist) {
                        inputDomainList.remove(domainList[index]);
                        modification = true;
                    } else {
                        System.out.println("Domain not in the list");
                    }
                }
                if (modification) {
                    course.setRequiredDomainKnowledge((ArrayList<String>) inputDomainList);
                    courseList.replace(targetPos, course);
                    MessageUI.courseModificationMsg();
                }
            }
        } while (selection != 0);
        
       
       return courseList;
    }
    
    public ListInterface<Course> modifyCourseProgList(ListInterface<Course> courseList, String[] programmes, Course course) {
       
        int selection = -1;

        do {
            MessageUI.courseTopDivider();
            System.out.println("Modifying " + course.getCourseCode() + " programme list");
            MessageUI.courseBtmDivider();
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("0. Exit");
            MessageUI.courseBtmDivider();
            System.out.println("Your choice: ");
            selection = scanner.nextInt();
            scanner.nextLine();
            if (selection == 0) {
                MessageUI.displayExit();
                return courseList;
            } else if (selection < 0 || selection > 2) {
                MessageUI.displayInvalidChoiceMessage();
            } else {
                int targetPos = ((CircularDoublyLinkedList) courseList).locatePosition(course);
                ListInterface<String> inputProgList = course.getProgrammes();
                int index = inputProgramme() - 1; //user input will return int value to access predifined programmes list;
                boolean notExist = true, modification = false;
                notExist = validator.checkExistInList(inputProgList, programmes[index]);
                if (selection == 1) {
                    if (notExist) {
                        inputProgList.add(programmes[index]);
                        modification = true;
                    } else {
                        System.out.println("Existing programme");
                    }

                } else if (selection == 2) {
                    if (!notExist) {
                        inputProgList.remove(programmes[index]);
                        modification = true;
                    } else {
                        System.out.println("Programme not in the list");
                    }
                }
                if (modification) {
                    course.setProgrammes((ArrayList<String>) inputProgList);
                    courseList.replace(targetPos, course);
                    MessageUI.courseModificationMsg();
                }
            }
        } while (selection != 0);
        
       
       return courseList;
    }
    
    
    public String inputCourseCode(ListInterface<Course> courseList) {
        //for new course with function to check if any duplicate code id.
        boolean valid = true;
        String code;
        do {
            System.out.println("Enter course code: ");
            code = scanner.nextLine();
            code = code.toUpperCase();
            valid = validator.courseCodeCheck(code, courseList);
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
    
    public ListInterface<String> inputDomainLoop(String[] domainList){
        int domainSelection;
        ListInterface<String> domains = new ArrayList<>();
        do {
            domainSelection = inputDomain();
            boolean notDuplicated = true;
            if (domainSelection >= 1 && domainSelection <= domainList.length) {
                if (domains.getNumberOfEntries() > 0) {
                    notDuplicated = validator.checkExistInList(domains, domainList[domainSelection - 1]);
                }

            } else if (domainSelection == 0) {
              System.out.println("Exiting domain input");            
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }

            if ((domainSelection >= 1 && domainSelection <= domainList.length) && notDuplicated){ 
                domains.add(domainList[domainSelection - 1]);
                System.out.println(domainList[domainSelection-1] + " successfully added.");
            }
            
        } while (domainSelection != 0);
    
        return domains;
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
                    notDuplicated = validator.checkExistInList(result, programmes[programmeSelection - 1]);
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
                System.out.println(programmes[programmeSelection - 1] + " successfully added.");
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
        System.out.println("Course doesn't exist");
        return null;
    }
    
    
       
    public void displayAllCourse(ListInterface<Course> courseList) {
//        Iterator<Course> it = courseList.getIterator();
        Course target;
        int count = 1;
        System.out.printf("%5s %-15s %-20s %-10s %20s\n", "Count ","Course Code","Course Name","Credit Hour","Take By Programme");
//        while(it.hasNext()){
//            target = it.next();
        for(int j=1; j<=courseList.getNumberOfEntries();j++){
            String programmes = "";
            target = courseList.getEntry(j);
            for(int i=1; i<=target.getProgrammes().getNumberOfEntries(); i++){
                programmes += target.getProgrammes().getEntry(i) + ",";
            }
            programmes += "\b";
            
            System.out.printf("%-7d %-16s %-20s %-10d %20s\n", count,target.getCourseCode(), target.getCourseName(), target.getCreditHR(),programmes);
            ++count;
            
        }
        MessageUI.pause();
    }
    
    public int deleteByNo(ListInterface<Course> courseList){
        // this is used by default course list
        int selection = -1;
        do {
            displayAllCourse(courseList);
            System.out.println("Input number to delete(0 to exit): ");
            selection = scanner.nextInt();
            scanner.nextLine();
            if (selection == 0) {
                break;
            }else if(selection < 0 || selection > courseList.getNumberOfEntries()){
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (selection < 0 || selection > courseList.getNumberOfEntries());
        
        
        return selection;
    }
    
    public ListInterface<Course> filterCourseByProgramme(ListInterface<Course> courseList){
        System.out.println("Enter programme: ");
        String programme = scanner.nextLine();
        programme = programme.toUpperCase();
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
    
     public Course deleteFilteredListByNo(ListInterface<Course> courseList){
        // this is used by course list filtered by programme
        int selection = -1;
        do {
            displayAllCourse(courseList);
            System.out.println("Input number to delete(0 to exit): ");
            selection = scanner.nextInt();
            scanner.nextLine();
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
         System.out.println("The latest deletion will be undo first");
         System.out.println("\nUndo the course deletion?(Y = yes): ");
         char input = scanner.nextLine().charAt(0);
         input = Character.toUpperCase(input);
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
