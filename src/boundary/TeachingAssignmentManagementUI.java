/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import adt.StackInterface;
import control.TeachingAssignmentManagement;
import entity.Course;
import entity.Programme;
import entity.TeachingAssignment;
import entity.Tutor;
import entity.TutorialGroup;
import java.util.*;
import utility.MessageUI;

/**
 *
 * @author Ong Cheng Leong
 */
public class TeachingAssignmentManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        ;
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Teaching Assignment Main Menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Assign Tutor");
                System.out.println("2. Modify Assigned Tutor");
                System.out.println("3. Find Tutor Assignment");
                System.out.println("4. List Tutor Assignent");
                System.out.println("5. Filter Teaching Assignent");
                System.out.println("6. Generate Report");
                System.out.println("7. Undo Modify Changes");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 7) {
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

    public int getAssignTutorOption() {

        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Assign Tutor menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Assign Tutor by course");
                System.out.println("2. Assign Tutor by Available slot");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;
    }

    public int getModifyTutorAssignmentOption() {
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Modify Tutor Assignment menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Search by Tutor");
                System.out.println("2. Search by Course");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;
    }

    public int getSearchTutorAssignmentOption() {
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Search Tutor Assignment menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Search by Tutor");
                System.out.println("2. Search by Course");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;
    }

    public int getListTutorAssignmentOption() {
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("List Tutor Assignment menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. List tutors under a course");
                System.out.println("2. List courses under a tutor");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;
    }

    public int getFilterTutorAssignmentOption() {
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Filter Tutor Assignment menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Filter by number of classes assigned");
                System.out.println("2. Filter by program");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;
    }

    public int getModifyTutorOption() {
        boolean valid = false;
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Modify Tutor menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Replace Tutor");
                System.out.println("2. Remove Tutor");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;
    }

    public int getFilterrTutorOption() {
        boolean valid = false;
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Filter Tutor By No Of Class Assigned menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Greater than");
                System.out.println("2. Equal");
                System.out.println("3. Lesser than");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 3) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;

    }

    public int getReportOption() {
        boolean valid = false;
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Tutor Assignment Report");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Assigned Classes report");
                System.out.println("2. Unassigned Classes report");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;

    }

    public int getUndoOption() {
        boolean valid = false;
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("Tutor Assignment Modify Undo");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Undo modify changes");
                System.out.println("2. Confirm changes");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= 2) {
                    break;
                }
                MessageUI.displayInvalidChoiceMessage();
                MessageUI.pause();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
        }
        return selection;

    }

    public Tutor getTutor(ListInterface<Tutor> tutorList) {
        Iterator<Tutor> tutorIT = tutorList.getIterator();
        Tutor resultTutor = null;
        MessageUI.TeachingAssignmentTopDivider();
        System.out.printf("Search Tutor\n");
        MessageUI.TeachingAssignmentBtmDivider();
        System.out.print("Enter tutor name / ID (T001) : ");
        String input = scanner.nextLine();
        while (tutorIT.hasNext()) {
            Tutor tutor = tutorIT.next();
            if (tutor.getTutorID().equals(input) || tutor.getName().equals(input)) {
                return tutor;
            }
        }

        return resultTutor;
    }

    public Course getCourse(ListInterface<Course> courseList) {
        Iterator<Course> courseIT = courseList.getIterator();
        Course resultCourse = null;
        MessageUI.TeachingAssignmentTopDivider();
        System.out.printf("Search Course\n");
        MessageUI.TeachingAssignmentBtmDivider();
        System.out.print("Enter course name / ID (AACS1234 / MPU-1123) : ");
        String input = scanner.nextLine();
        while (courseIT.hasNext()) {
            Course course = courseIT.next();
            if (course.getCourseCode().equals(input) || course.getCourseName().equals(input)) {
                return course;
            }
        }

        return resultCourse;
    }

    public int getFilterInput(int selection) {
        int numberOfClasses = 0;
        while (true) {
            boolean valid = true;

            try {
                switch (selection) {
                    case 1:
                        MessageUI.TeachingAssignmentTopDivider();
                        System.out.printf("List Tutors who have assigned more than \n");
                        MessageUI.TeachingAssignmentBtmDivider();
                        System.out.print("Enter number of class (0-14) : ");
                        numberOfClasses = scanner.nextInt();
                        if (!(numberOfClasses >= 0 && numberOfClasses <= 14)) {
                            System.out.println("\nPlease enter the number of class in (0-14)");
                            valid = false;
                        }
                        break;
                    case 2:
                        MessageUI.TeachingAssignmentTopDivider();
                        System.out.printf("List Tutors who have assigned equal \n");
                        MessageUI.TeachingAssignmentBtmDivider();
                        System.out.print("Enter number of class (0-15) : ");
                        numberOfClasses = scanner.nextInt();
                        if (!(numberOfClasses >= 0 && numberOfClasses <= 15)) {
                            System.out.println("\nPlease enter the number of class in (0-15)");
                            valid = false;
                        }
                        break;
                    case 3:
                        MessageUI.TeachingAssignmentTopDivider();
                        System.out.printf("List Tutors who have assigned less than \n");
                        MessageUI.TeachingAssignmentBtmDivider();
                        System.out.print("Enter number of class (1-15) : ");
                        numberOfClasses = scanner.nextInt();
                        if (!(numberOfClasses >= 1 && numberOfClasses <= 15)) {
                            System.out.println("\nPlease enter the number of class in (1-15)");
                            valid = false;
                        }
                        break;
                    default:
                }
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
            }
            if (valid) {
                break;
            }

        }

        return numberOfClasses;
    }

    public String getDomainKnowledgeString(ListInterface<String> domainList) {
        Iterator<String> domainIT = domainList.getIterator();
        String domainString = "";
        while (domainIT.hasNext()) {
            String domain = domainIT.next();
            domainString += domain;
            if (domainIT.hasNext()) {
                domainString += ", ";
            }
        }
        return domainString;

    }

    public TeachingAssignment getTASelection(ListInterface<TeachingAssignment> taList, String displayString) {
        int selection = -1;
        int count = 1;
        ListInterface<String> programmeList = TeachingAssignmentManagement.getUniqueProgramme(taList);
        TeachingAssignment selectedClass = null;
        while (true) {
            Iterator<String> programmeIT = programmeList.getIterator();
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.print(displayString);
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf("Class List:\n");
                MessageUI.TeachingAssignmentBtmDivider();

                while (programmeIT.hasNext()) {
                    String programme = programmeIT.next();
                    System.out.printf("\n%-9s %-7s %-3s %s", "Programme", "Batch", "No", "GroupNo");
                    System.out.printf("\n%s ", programme);
                    ListInterface selectedProgrammeTAList = TeachingAssignmentManagement.filterByProgramme(taList, programme);
                    if (selectedProgrammeTAList.getNumberOfEntries() != 0) {
                        ListInterface batchList = TeachingAssignmentManagement.getUniqueBatch(selectedProgrammeTAList);
                        Iterator<String> batchIT = batchList.getIterator();
                        while (batchIT.hasNext()) {
                            String batch = batchIT.next();
                            ListInterface selectedProgrammeBatchTAList = TeachingAssignmentManagement.filterByProgrammeBatch(selectedProgrammeTAList, programme, batch);
                            if (selectedProgrammeBatchTAList.getNumberOfEntries() != 0) {
                                System.out.printf("\n%9s %-7s", " ", batch);
                                Iterator<TeachingAssignment> taIT = selectedProgrammeBatchTAList.getIterator();
                                while (taIT.hasNext()) {
                                    TeachingAssignment ta = taIT.next();
                                    if (ta.getTutorialGroup().getProgramme().equals(programme)) {
                                        System.out.printf("\n%9s %7s %02d. %s", " ", " ", count++, ta.getTutorialGroup().getGroup());
                                    }
                                }
                            }
                        }
                        System.out.println("");
                    }

                }
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf(" 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= taList.getNumberOfEntries()) {
                    if (selection == 0) {

                        break;
                    } else {
                        selectedClass = taList.getEntry(selection);
                        break;
                    }
                } else {
                    count = 1;
                    MessageUI.displayInvalidChoiceMessage();
                    MessageUI.pause();
                }

            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                count = 1;
                MessageUI.pause();
            }
        }
        return selectedClass;

    }

    public Programme getProgrammeSelection(ListInterface<Programme> programmeList) {

        int selection = -1;
        int count = 1;
        Programme selectedProgramme = null;

        while (true) {

            try {
                Iterator<Programme> programmeIT = programmeList.getIterator();
                MessageUI.TeachingAssignmentTopDivider();
                System.out.printf("Programme List:\n");
                MessageUI.TeachingAssignmentBtmDivider();
                while (programmeIT.hasNext()) {
                    Programme programme = programmeIT.next();
                    System.out.printf("%02d. %s\n", count++, programme.getName());
                }
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf(" 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                scanner.nextLine();

                if (selection >= 0 && selection <= programmeList.getNumberOfEntries()) {
                    if (selection == 0) {
                        selectedProgramme = null;

                        break;
                    } else {
                        selectedProgramme = programmeList.getEntry(selection);
                        break;
                    }
                } else {
                    count = 1;
                    MessageUI.displayInvalidChoiceMessage();
                    MessageUI.pause();
                }
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                count = 1;
                MessageUI.pause();
            }
        }
        return selectedProgramme;
    }

    public String getBatchSelection(String displayString, ListInterface<String> batchList) {

        int selection = -1;
        int count = 1;
        String selectedBatch = null;

        while (true) {

            try {
                Iterator<String> batchIT = batchList.getIterator();
                MessageUI.TeachingAssignmentTopDivider();
                System.out.print(displayString);
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf("Batch List:\n");
                MessageUI.TeachingAssignmentBtmDivider();
                while (batchIT.hasNext()) {
                    String batch = batchIT.next();
                    System.out.printf("%02d. %s\n", count++, batch);
                }
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf(" 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                scanner.nextLine();

                if (selection >= 0 && selection <= batchList.getNumberOfEntries()) {
                    if (selection == 0) {

                        break;
                    } else {
                        selectedBatch = batchList.getEntry(selection);
                        break;
                    }
                } else {
                    count = 1;
                    MessageUI.displayInvalidChoiceMessage();
                    MessageUI.pause();
                }
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                count = 1;
                MessageUI.pause();
            }
        }
        return selectedBatch;
    }

    public Course getCourseSelection(ListInterface<Course> courseList, String displayString) {

        int selection = -1;
        int count = 1;
        Course selectedCourse = null;
        while (true) {

            try {
                Iterator<Course> courseIT = courseList.getIterator();
                MessageUI.TeachingAssignmentTopDivider();
                System.out.print(displayString);
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf("Course List:\n");
                MessageUI.TeachingAssignmentBtmDivider();
                while (courseIT.hasNext()) {
                    Course course = courseIT.next();
                    System.out.printf("%02d. %s\n", count++, course.getCourseName());
                }
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf(" 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= courseList.getNumberOfEntries()) {
                    if (selection == 0) {
                        selectedCourse = null;

                        break;
                    } else {
                        selectedCourse = courseList.getEntry(selection);
                        break;
                    }
                } else {
                    count = 1;
                    MessageUI.displayInvalidChoiceMessage();
                    MessageUI.pause();
                }
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                count = 1;
                MessageUI.pause();

            }
        }
        return selectedCourse;
    }

    public Tutor getTutorSelection(ListInterface<Tutor> tutorList, ListInterface<TeachingAssignment> taList, String displayString) {

        int selection = -1;
        int count = 1;
        Tutor selectedTutor = null;
        while (true) {
            try {
                Iterator<Tutor> tutorIT = tutorList.getIterator();
                MessageUI.TeachingAssignmentTopDivider();
                System.out.print(displayString);
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf("Tutor List:\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf("%-3s %-20s %s", "No", "Name", "Number of class assigned\n");

                while (tutorIT.hasNext()) {
                    Tutor tutor = tutorIT.next();
                    System.out.printf("%02d. %-20s %2d\n", count++, tutor.getName(), TeachingAssignmentManagement.getNoOfClassAssigned(taList, tutor));
                }
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf(" 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection >= 0 && selection <= tutorList.getNumberOfEntries()) {
                    if (selection == 0) {
                        selectedTutor = null;

                        break;
                    } else {
                        selectedTutor = tutorList.getEntry(selection);
                        break;
                    }
                } else {
                    count = 1;
                    MessageUI.displayInvalidChoiceMessage();
                    MessageUI.pause();
                }
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                MessageUI.pause();
                count = 1;
            }
        }
        return selectedTutor;
    }

    public void displayReport(ListInterface<TeachingAssignment> taList, String displayString) {
        ListInterface<String> programmeList = TeachingAssignmentManagement.getUniqueProgramme(taList);

        Iterator<String> programmeIT = programmeList.getIterator();
        MessageUI.TeachingAssignmentTopDivider();
        System.out.print(displayString);
        MessageUI.TeachingAssignmentBtmDivider();
        System.out.printf("Class List:\n");
        MessageUI.TeachingAssignmentBtmDivider();
        int totalCount = 0;
        while (programmeIT.hasNext()) {
            int count = 0;
            String programme = programmeIT.next();
            System.out.printf("\n%-9s %-7s %-35s %s", "Programme", "GroupNo", "Course", "Tutor");
            System.out.printf("\n%s ", programme);
            ListInterface selectedProgrammeTAList = TeachingAssignmentManagement.filterByProgramme(taList, programme);
            if (selectedProgrammeTAList.getNumberOfEntries() != 0) {
                ListInterface<TutorialGroup> tutorialGroupList = TeachingAssignmentManagement.getUniqueTutGrp(selectedProgrammeTAList);

                Iterator<TutorialGroup> tutGroupIT = tutorialGroupList.getIterator();
                while (tutGroupIT.hasNext()) {
                    TutorialGroup tutGroup = tutGroupIT.next();
                    ListInterface selectedTutGrpTAList = TeachingAssignmentManagement.filterByTutorialGrp(selectedProgrammeTAList, tutGroup);
                    if (selectedTutGrpTAList.getNumberOfEntries() != 0) {
                        selectedTutGrpTAList= TeachingAssignmentManagement.sortTAByCourseName(selectedTutGrpTAList);
                        System.out.printf("\n%9s %-7s", " ", tutGroup.getGroup());
                        Iterator<TeachingAssignment> taIT = selectedTutGrpTAList.getIterator();
                        while (taIT.hasNext()) {
                            TeachingAssignment ta = taIT.next();
                            String tutorName = ta.getTutor().getName();
                            if (tutorName == null) {
                                tutorName = "Unassigned";
                            }
                            System.out.printf("\n%-9s %-7s %-35s %s", " ", " ", ta.getCourse().getCourseName(), tutorName);
                            count++;
                        }
                    }
                }
                System.out.println("");
            }

            MessageUI.TeachingAssignmentBtmDivider();
            System.out.printf("%-9s %-7s %-35s %-17s : %2d\n", " ", " ", " ", "No Of Class", count);
            MessageUI.TeachingAssignmentBtmDivider();
            totalCount += count;

        }
        MessageUI.TeachingAssignmentBtmDivider();
        System.out.printf("%-9s %-7s %-35s %-17s : %2d\n", " ", " ", " ", "Total No of Class", totalCount);
        MessageUI.TeachingAssignmentBtmDivider();

        MessageUI.pause();

    }

    public void displayChanges(StackInterface<TeachingAssignment> beforeChange, StackInterface<TeachingAssignment> afterChange) {

        Iterator<TeachingAssignment> beforeChangeIT = beforeChange.getIterator();
        Iterator<TeachingAssignment> afterChangeIT = afterChange.getIterator();
        MessageUI.TeachingAssignmentTopReportDivider();
        System.out.printf("Tutor Assignment Modify changes made\n");
        MessageUI.TeachingAssignmentBtmReportDivider();
        int count = 1;
        System.out.printf("|%-3s | %-9s | %-14s | %-35s | %-13s | %-15s | %-15s |\n", "No.", "Programme", "Tutorial Group", "Course", "Changes", "Before", "After");
        MessageUI.TeachingAssignmentBtmReportDivider();
        while (beforeChangeIT.hasNext()) {
            TeachingAssignment taBeforeChange = beforeChangeIT.next();
            TeachingAssignment taAfterChange = afterChangeIT.next();
            String changes = "";
            String tutorName = "";
            if (taAfterChange.getTutor().getTutorID() == null) {
                changes = "Remove Tutor";
                tutorName = "Unassigned";
            } else {
                changes = "Replace Tutor";
                tutorName = taAfterChange.getTutor().getName();
            }

            System.out.printf("|%2d  | %-9s | %-14s | %-35s | %-13s | %-15s | %-15s |\n", count++, taAfterChange.getTutorialGroup().getProgramme(), taAfterChange.getTutorialGroup().getGroup(), taAfterChange.getCourse().getCourseName(), changes, taBeforeChange.getTutor().getName(), tutorName);
            MessageUI.TeachingAssignmentBtmReportDivider();

        }
        System.out.printf(" %3s  %-9s  %-14s  %-35s  %-13s  %-8s  %-18s : %2d\n", "", "", "", "", "", "", "No of changes made", --count);
        MessageUI.TeachingAssignmentBtmReportDivider();
        System.out.println("");
    }
}
