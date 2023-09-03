/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ArrayList;
import adt.CircularDoublyLinkedList;
import adt.ListInterface;
import control.TutorInputValidator;
import control.CourseInputValidator;
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
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Teaching Assignment Main Menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Assign Tutor");
                System.out.println("2. Modify Assigned Tutor");
                System.out.println("3. Find Teaching Assignment");
                System.out.println("4. List Teaching Assignent");
                System.out.println("5. Generate Report");
                System.out.println("0. Quit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
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

    public int getAssigmTutorOption() {
        boolean valid = false;
        int selection = -1;
        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.println("Assign Tutor menu");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.println("1. Assign Tutor by course");
                System.out.println("2. Assign Tutor by Available slot");
                System.out.println("0. Quit");
                 MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice : ");
                selection = scanner.nextInt();
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

    public TeachingAssignment getTASelection(ListInterface<TeachingAssignment> taList, Course selectedCourse, Tutor selectedTutor, int noOfClassAssigned) {
        int selection = -1;
        int count = 1;
        ListInterface<String> programmeList = TeachingAssignmentManagement.getUniqueProgramme(taList);
        TeachingAssignment selectedClass = null;
        while (true) {
            Iterator<String> programmeIT = programmeList.getIterator();
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.printf("Selected Course                       : %s", selectedCourse.getCourseName());
                System.out.printf("\nSelected Tutor                        : %s", selectedTutor.getName());
                System.out.printf("\nNo Of class assigned (Max 15 classes) : %2d\n", noOfClassAssigned);
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf("Class List:");

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
                System.out.printf(" 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= taList.getNumberOfEntries()) {
                    if (selection == 0) {
                        MessageUI.displayExit();
                        break;
                    } else {
                        selectedClass = taList.getEntry(selection);
                        noOfClassAssigned++;
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

    public String getProgrammeSelection(ListInterface<String> programmeList) {
        Iterator<String> programmeIT = programmeList.getIterator();
        int selection = -1;
        int count = 1;
        String selectedProgramme = null;

        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.printf("Programme List:");
                MessageUI.TeachingAssignmentBtmDivider();
                while (programmeIT.hasNext()) {
                    String programme = programmeIT.next();
                    System.out.printf("\n%02d. %s", count++, programme);
                }
                System.out.printf(" 0. Exit");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();

                if (selection >= 0 && selection <= programmeList.getNumberOfEntries()) {
                    if (selection == 0) {
                        MessageUI.displayExit();
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

    public String getBatchSelection(ListInterface<String> batchList) {
        Iterator<String> batchIT = batchList.getIterator();
        int selection = -1;
        int count = 1;
        String selectedBatch = null;

        while (true) {
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.printf("Batch List:");
                MessageUI.TeachingAssignmentBtmDivider();
                while (batchIT.hasNext()) {
                    String batch = batchIT.next();
                    System.out.printf("\n%02d. %s", count++, batch);
                }
                System.out.printf("\n 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();

                if (selection >= 0 && selection <= batchList.getNumberOfEntries()) {
                    if (selection == 0) {
                        MessageUI.displayExit();
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

    public Course getCourseSelection(ListInterface<Course> courseList) {

        int selection = -1;
        int count = 1;
        Course selectedCourse = null;
        while (true) {
            Iterator<Course> courseIT = courseList.getIterator();
            try {
                MessageUI.TeachingAssignmentTopDivider();
                System.out.printf("Course List:\n");
                MessageUI.TeachingAssignmentBtmDivider();
                while (courseIT.hasNext()) {
                    Course course = courseIT.next();
                    System.out.printf("%02d. %s\n", count++, course.getCourseName());
                }
                System.out.printf(" 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= courseList.getNumberOfEntries()) {
                    if (selection == 0) {
                        MessageUI.displayExit();
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

    public TutorialGroup getTutorialGrpSelection(ListInterface<TutorialGroup> tutGrpList) {
        Iterator<TutorialGroup> tutGrpIT = tutGrpList.getIterator();
        TutorialGroup selectedTutgrp = null;
        int selection = -1;
        int count = 1;
        while (true) {
            try {
                 MessageUI.TeachingAssignmentTopDivider();
                System.out.printf("Course List:");
                MessageUI.TeachingAssignmentBtmDivider();
                while (tutGrpIT.hasNext()) {
                    TutorialGroup tutGrp = tutGrpIT.next();
                    System.out.printf("\n%02d. %s", count++, tutGrp.getId());
                }
                System.out.printf("\n 0. Exit\n");
                 MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= tutGrpList.getNumberOfEntries()) {
                    if (selection == 0) {
                       MessageUI.pause();
                        break;
                    } else {
                        selectedTutgrp = tutGrpList.getEntry(selection);
                        break;
                    }
                } else {
                    MessageUI.displayInvalidChoiceMessage();
                    MessageUI.pause();
                    count = 1;
                }
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid selection in integer");
                scanner.nextLine();
                count = 1;
                MessageUI.pause();
            }
        }
        return selectedTutgrp;
    }

    public Tutor getQualifiedTutorSelection(ListInterface<Tutor> tutorList, ListInterface<TeachingAssignment> taList, Course selectedCourse) {

        int selection = -1;
        int count = 1;
        Tutor selectedTutor = null;
        while (true) {
            try {
                Iterator<Tutor> tutorIT = tutorList.getIterator();
                MessageUI.TeachingAssignmentTopDivider();
                System.out.printf("Selected Course : %s\n", selectedCourse.getCourseName());
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.printf("Tutor List      :");
                System.out.printf("\n%-3s %-20s %s", "No", "Name", "Number of class assigned");
                
                while (tutorIT.hasNext()) {
                    Tutor tutor = tutorIT.next();
                    System.out.printf("\n%02d. %-20s %2d", count++, tutor.getName(), TeachingAssignmentManagement.getNoOfClassAssigned(taList, tutor));
                }
                System.out.printf("\n 0. Exit\n");
                MessageUI.TeachingAssignmentBtmDivider();
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= tutorList.getNumberOfEntries()) {
                    if (selection == 0) {
                        MessageUI.displayExit();
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

}
