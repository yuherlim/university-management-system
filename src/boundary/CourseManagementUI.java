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
import dao.ProgrammeDAO;
import entity.Course;
import entity.Programme;
import java.util.Iterator;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author Sia Yeong Sheng
 */
public class CourseManagementUI {

    Scanner scanner = new Scanner(System.in);
    private CourseInputValidator validator = new CourseInputValidator();
    int selection = -1;

    public int getCourseMainMenuChoice() {
        try {
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
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Expecting numeric input");
            scanner.nextLine();
        }
        return selection;
    }

    //add course related function
    public void displayAddCourseMsg() {
        MessageUI.courseTopDivider();
        System.out.println("Adding new course");
        MessageUI.courseBtmDivider();
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
        } while (!valid);

        return code;
    }

    public String inputCourseName() {
        boolean valid = true;
        String name;
        do {
            System.out.print("Enter course name: ");
            name = scanner.nextLine();
            valid = validator.checkCourseNameInput(name);
        } while (!valid);

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

    public ListInterface<String> inputDomainLoop(String[] domainList) {
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
                System.out.println("Exiting domain input\n");
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }

            if ((domainSelection >= 1 && domainSelection <= domainList.length) && notDuplicated) {
                domains.add(domainList[domainSelection - 1]);
                System.out.println(domainList[domainSelection - 1] + " successfully added.\n");
            } else if (!notDuplicated) {
                System.out.println("The domain already in the list.\n");
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

    public int inputProgramme(String[] programmes, char addOrDelete) {
        int programmeSelection = 0;
        boolean valid = true;
        do {
            try {
                if (addOrDelete == 'I') {
                    System.out.println("\nInput the programme that are taking the course");
                } else {
                    System.out.println("\nRemove the programme from taking the course");
                }
                for (int i = 0; i < programmes.length; i++) {
                    System.out.println(i + 1 + ". " + programmes[i]);
                }
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

    //modifiy
    public void displayModifyCourseMenuMsg() {
        MessageUI.courseTopDivider();
        System.out.println("Modify course");
        MessageUI.courseBtmDivider();
    }

    public int getModifyMenuSelection(Course course) {
        int selection = -1;
        try {
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
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Expecting numeric input");
            scanner.nextLine();
        }
        return selection;
    }

    public Course searchCourseByCode(ListInterface<Course> courseList) {
        System.out.println("Enter course code: ");
        String code = scanner.nextLine();

        code = code.toUpperCase();

        Iterator<Course> it = courseList.getIterator();
        Course target;
        while (it.hasNext()) {
            target = it.next();

            if (target.getCourseCode().equals(code)) {
                return target;
            }
        }
        MessageUI.nonExistCourse();
        return null;
    }

    public int addOrDelDLorPL() {
        int selection = -1;
        try {
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("0. Exit");
            MessageUI.courseBtmDivider();
            System.out.println("Your choice: ");
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Expecting numeric input");
            scanner.nextLine();
        }

        return selection;
    }

    //remove
    public int deleteByNo(ListInterface<Course> courseList) {
        // this is used by default course list
        int selection = -1;
        do {
            try {
                displayAllCourse(courseList);
                System.out.println("Input number to delete(0 to exit): ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection == 0) {
                    break;
                } else if (selection < 0 || selection > courseList.getNumberOfEntries()) {
                    MessageUI.displayInvalidChoiceMessage();
                }
            } catch (Exception e) {
                System.out.println("Expecting numeric input");
                scanner.nextLine();
            }
        } while (selection < 0 || selection > courseList.getNumberOfEntries());

        return selection;
    }

    public ListInterface<Course> filterCourseByProgramme(ListInterface<Course> courseList) {
        System.out.println("Enter programme: ");
        String programme = scanner.nextLine();
        programme = programme.toUpperCase();
        ListInterface<Course> result = new CircularDoublyLinkedList<>();

        Iterator<Course> it = courseList.getIterator();
        Course target;
        while (it.hasNext()) {
            target = it.next();
            for (int i = 1; i <= target.getProgrammes().getNumberOfEntries(); i++) {
                String programmeCode = target.getProgrammes().getEntry(i);
                if (programmeCode.equals(programme)) {
                    result.add(target);
                }
            }
        }

        return result;
    }

    public Course deleteFilteredListByNo(ListInterface<Course> courseList) {
        // this is used by course list filtered by programme
        int selection = -1;
        do {
            try {
                displayAllCourse(courseList);
                System.out.println("Input number to delete(0 to exit): ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection == 0) {
                    break;
                } else if (selection < 0 || selection > courseList.getNumberOfEntries()) {
                    MessageUI.displayInvalidChoiceMessage();
                }
            } catch (Exception e) {
                System.out.println("Expecting numeric input");
                scanner.nextLine();
            }
        } while (selection < 0 || selection > courseList.getNumberOfEntries());

        return courseList.getEntry(selection);
    }

    public char exitConfirmationForDelete() {
        //if stack isn't empty tell user once exit undo will be clear
        char input = ' ';
        System.out.println("Confirm to exit?");
        System.out.println("Once exit undo will be clear");

        try {
            System.out.println("Your choice(Y = yes, other input to back to delete function): ");
            input = scanner.nextLine().charAt(0);
            input = Character.toUpperCase(input);
        } catch (Exception e) {
            input = 'N'; // consider empty space as other input, which brings user back to delete function
        }

        if (input != 'Y') {
            System.out.println("Back to delete menu");
        }

        return input;
    }

    public char undo(StackInterface<Course> courseStack) {
        char input = ' ';
        MessageUI.courseTopDivider();
        System.out.println("Undo deletion");
        MessageUI.courseBtmDivider();
        System.out.println(courseStack.peek());
        MessageUI.courseBtmDivider();
        System.out.println("One undo deletion at a time");
        System.out.println("The latest deletion will be undo first");
        try {
            System.out.println("\nUndo the course deletion?(Y = yes, other input to exit undo): ");
            input = scanner.nextLine().charAt(0);
            input = Character.toUpperCase(input);
        } catch (Exception e) {
            input = 'N'; //assume empty input = other input;
        };
        return input;
    }

    public int deleteCourseMenuSelection() {
        int selection = -1;

        try {
            MessageUI.courseTopDivider();
            System.out.println("Removing a course");
            MessageUI.courseBtmDivider();
            System.out.println("1. Display all course to delete a course");
            System.out.println("2. Search course by a PROGRAMME");
            System.out.println("3. Delete a course by code");
            System.out.println("4. Undo deletion(cannot be undo once exit)");
            System.out.println("0. Exit");
            MessageUI.courseBtmDivider();
            System.out.println("Your selection: ");
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Expecting numeric input");
            scanner.nextLine();
        }
        return selection;
    }

    //view course
    public int getDisplayCourseMenuSelection() {
        int selection = -1;
        try {
            MessageUI.courseTopDivider();
            System.out.println("Display Course");
            MessageUI.courseBtmDivider();
            System.out.println("1. Display all courses");
            System.out.println("2. Display course by specific programme");
            System.out.println("3. Display a specific course");
            System.out.println("0. Exit");
            MessageUI.courseBtmDivider();
            System.out.println("Your selection: ");
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Expecting numeric input");
            scanner.nextLine();
        }
        return selection;
    }

    public void displayAllCourse(ListInterface<Course> courseList) {
//        Iterator<Course> it = courseList.getIterator();
        Course target;
        int count = 1;
        System.out.printf("%5s %-15s %-40s %-10s %30s\n", "Count ", "Course Code", "Course Name", "Credit Hour", "Take By Programme");
//        while(it.hasNext()){
//            target = it.next();
        for (int j = 1; j <= courseList.getNumberOfEntries(); j++) {
            String programmes = "";
            target = courseList.getEntry(j);
            for (int i = 1; i <= target.getProgrammes().getNumberOfEntries(); i++) {
                programmes += target.getProgrammes().getEntry(i) + ",";
            }
            programmes += "\b";

            System.out.printf("%-6d %-16s %-40s %5d %35s\n", count, target.getCourseCode(), target.getCourseName(), target.getCreditHR(), programmes);
            ++count;

        }
        MessageUI.pause();
    }

    public void displayCourseByProgramme(ListInterface<Course> courseList) {
        ListInterface<Course> filteredList = filterCourseByProgramme(courseList);
        displayAllCourse(filteredList);
    }

    public void displayACourse(ListInterface<Course> courseList) {
        Course target = searchCourseByCode(courseList);
        System.out.println(target);
        MessageUI.pause();
    }

    //report
    public int getReportMenuChoice() {
        int selection = -1;
        try {
            MessageUI.courseTopDivider();
            System.out.println("Report Menu");
            MessageUI.courseBtmDivider();
            System.out.println("1. Display report");
            System.out.println("2. Display report sorted by course code");
            System.out.println("3. Display report sorted by credit hour");
            System.out.println("0. Quit");
            MessageUI.courseBtmDivider();
            System.out.print("Enter choice: ");

            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Expecting numeric input");
            scanner.nextLine();
        }
        return selection;
    }

    public void report(ListInterface<Course> courseList, String[] programme) {
        for (String prog : programme) {
            MessageUI.courseTopDivider();
            Iterator<Course> it = courseList.getIterator();
            System.out.printf("%-10s %-20s %-50s %10s\n", "Programme", "Course Code", "Course Name", "Credit Hour");
            int totalCreditHours = 0;
            boolean firstLine = true;

            while (it.hasNext()) {
                Course course = it.next();
                for (int j = 1; j <= course.getProgrammes().getNumberOfEntries(); j++) {
                    if (course.getProgrammes().getEntry(j).equals(prog)) {
                        if (firstLine) {
                            System.out.printf("%-10s %-20s %-50s %10d\n", prog, course.getCourseCode(), course.getCourseName(), course.getCreditHR());
                            totalCreditHours += course.getCreditHR();
                            firstLine = false;
                        } else {
                            System.out.printf("%-10s %-20s %-50s %10d\n", "", course.getCourseCode(), course.getCourseName(), course.getCreditHR());
                            totalCreditHours += course.getCreditHR();
                        }
                    }
                }
            }
            if (totalCreditHours == 0) {
                System.out.printf("%-10s %25s", prog, "-----------------------Pending for course assignment-----------------------\n");
                System.out.printf("Total Credit Hour: %74d\n", totalCreditHours);
            } else {
                System.out.printf("Total Credit Hour: %74d\n", totalCreditHours);
            }
            MessageUI.courseBtmDivider();
        }

        //each programme ,courses, total credit hour
    }

}
