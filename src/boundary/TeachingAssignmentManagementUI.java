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
import entity.Course;
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
                System.out.println("\nMAIN MENU");
                System.out.println("1. Assign Tutor");
                System.out.println("2. Modify Assigned Tutor");
                System.out.println("3. Find Teaching Assignment");
                System.out.println("4. List Teaching Assignent");
                System.out.println("5. Generate Report");
                System.out.println("0. Quit");
                System.out.print("Enter choice: \n");
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
                System.out.print("Enter choice: \n");
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

    public void assignByCourse(ListInterface<TeachingAssignment> taList, ListInterface<Tutor> tutorList) {
        ListInterface<TeachingAssignment> unAssignedTAList = this.getUnAssignedTeachingList(taList);
        int assignByCourseSelection = -1;
        Character cont = 'Y';
        do {
            if (!unAssignedTAList.isEmpty()) {
                ListInterface<Course> courseCodeList = this.getUniqueCourse(unAssignedTAList);
                Course selectedCourse = this.getCourseSelection(courseCodeList);
                if (selectedCourse != null) {
                    do {
                        ListInterface<Tutor> qualifiedTutorList = this.getQualifiedTutorList(tutorList, selectedCourse.getRequiredDomainKnowledge(), taList);
                        if (qualifiedTutorList.getNumberOfEntries() == 0) {
                            System.out.printf("\nNo available Tutor");
                            break;
                        } else {
                            Tutor selectedTutor = this.getQualifiedTutorSelection(qualifiedTutorList, taList);
                            if (selectedTutor != null) {
                                ListInterface<TeachingAssignment> selectedCoursetaList = this.filterByCourse(unAssignedTAList, selectedCourse);
                                ListInterface<TutorialGroup> tutGrpList = this.getUniqueTutGrp(selectedCoursetaList);
                                int noOfClassAssigned = this.getNoOfClassAssigned(taList, selectedTutor);
                                do {
                                    System.out.printf("\nSelected Tutor                        : %s", selectedTutor.getName());
                                    System.out.printf("\nNo Of class assigned (Max 10 classes) : %02d", noOfClassAssigned);
                                    //

                                    while (true) {
                                        System.out.printf("\nContinue select Tutorial Group? (Y/N)");
                                        cont = scanner.next().toUpperCase().charAt(0);
                                        if (cont == 'Y' || cont == 'N') {
                                            break;
                                        }
                                        System.out.printf("Please enter a valid selection");
                                    }
                                } while (cont == 'Y');
                            }

                        }
                        while (true) {
                            System.out.printf("\nContinue select Tutor? (Y/N)");
                            cont = scanner.next().toUpperCase().charAt(0);
                            if (cont == 'Y' || cont == 'N') {
                                break;
                            }
                            System.out.printf("Please enter a valid selection");
                        }
                    } while (cont == 'Y');
                }

                while (true) {
                    System.out.printf("\nContinue select course? (Y/N)");
                    cont = scanner.next().toUpperCase().charAt(0);
                    if (cont == 'Y' || cont == 'N') {
                        break;
                    }
                    System.out.printf("Please enter a valid selection");
                }

            } else {
                System.out.printf("\nAll course are Assigned");
                cont = 'N';
            }
        } while (cont == 'Y');

    }

    public String getProgrammeSelection(ListInterface<String> programmeList) {
        Iterator<String> programmeIT = programmeList.getIterator();
        int selection = -1;
        int count = 1;
        String selectedProgramme = null;

        while (true) {
            try {
                System.out.printf("\nProgramme List:");
                while (programmeIT.hasNext()) {
                    String programme = programmeIT.next();
                    System.out.printf("\n%02d. %s", count++, programme);
                }
                System.out.printf("\n 0. Exit");
                System.out.print("Enter choice: \n");
                selection = scanner.nextInt();

                if (selection >= 0 && selection <= programmeList.getNumberOfEntries()) {
                    if (selection == 0) {
                        break;
                    } else {
                        selectedProgramme = programmeList.getEntry(selection);
                    }
                }
                MessageUI.displayInvalidChoiceMessage();
            } catch (Exception e) {
                System.out.println("Please enter a valid selection in integer");
                scanner.nextLine();
            }
        }
        return selectedProgramme;
    }

    public Course getCourseSelection(ListInterface<Course> courseList) {

        int selection = -1;
        int count = 1;
        Course selectedCourse = null;
        while (true) {
            Iterator<Course> courseIT = courseList.getIterator();
            try {
                System.out.printf("\nCourse List:");
                while (courseIT.hasNext()) {
                    Course course = courseIT.next();
                    System.out.printf("\n%02d. %s", count++, course.getCourseName());
                }
                System.out.printf("\n 0. Exit");
                System.out.print("\nEnter choice: \n");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= courseList.getNumberOfEntries()) {
                    if (selection == 0) {
                        break;
                    } else {
                        selectedCourse = courseList.getEntry(selection);
                    }
                }
                MessageUI.displayInvalidChoiceMessage();
            } catch (Exception e) {
                System.out.println("Please enter a valid selection in integer");
                scanner.nextLine();
            }
        }
        return selectedCourse;
    }

    public Tutor getQualifiedTutorSelection(ListInterface<Tutor> tutorList, ListInterface<TeachingAssignment> taList) {
        Iterator<Tutor> tutorIT = tutorList.getIterator();
        int selection = -1;
        int count = 1;
        Tutor selectedTutor = null;
        while (true) {
            try {
                System.out.printf("\nCourse List:");
                System.out.printf("\n%s %s-20s %s", "No", "Name", "Number of class assigned");
                while (tutorIT.hasNext()) {
                    Tutor tutor = tutorIT.next();
                    System.out.printf("\n%02d. %s-20s %02d", count++, tutor.getName(), this.getNoOfClassAssigned(taList, tutor));
                }
                System.out.printf("\n 0. Exit");
                System.out.print("Enter choice: \n");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= tutorList.getNumberOfEntries()) {
                    if (selection == 0) {
                        break;
                    } else {
                        selectedTutor = tutorList.getEntry(selection);
                    }
                }
                MessageUI.displayInvalidChoiceMessage();
            } catch (Exception e) {
                System.out.println("Please enter a valid selection in integer");
                scanner.nextLine();
            }
        }
        return selectedTutor;
    }

    public TutorialGroup getTutorialGrpSelection(ListInterface<TutorialGroup> tutGrpList) {
        Iterator<TutorialGroup> tutGrpIT = tutGrpList.getIterator();
        TutorialGroup selectedTutgrp = null;
        int selection = -1;
        int count = 1;
        while (true) {
            try {
                System.out.printf("\nCourse List:");
                while (tutGrpIT.hasNext()) {
                    TutorialGroup tutGrp = tutGrpIT.next();
                    System.out.printf("\n%02d. %s", count++, tutGrp.getId());
                }
                System.out.printf("\n 0. Exit");
                System.out.print("Enter choice: \n");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= tutGrpList.getNumberOfEntries()) {
                    if (selection == 0) {
                        break;
                    } else {
                        selectedTutgrp = tutGrpList.getEntry(selection);
                    }
                }
                MessageUI.displayInvalidChoiceMessage();
            } catch (Exception e) {
                System.out.println("Please enter a valid selection in integer");
                scanner.nextLine();
            }
        }
        return selectedTutgrp;
    }

    public ListInterface<Tutor> getQualifiedTutorList(ListInterface<Tutor> tutorList, ListInterface<String> domainList, ListInterface<TeachingAssignment> taList) {
        Iterator tutorIT = tutorList.getIterator();
        ListInterface<Tutor> qualifiedTutorList = new ArrayList<Tutor>();

        while (tutorIT.hasNext()) {
            Tutor currentTutor = (Tutor) tutorIT.next();
            Iterator domainIT = domainList.getIterator();
            Boolean qualifiedTutor = true;
            if (this.getNoOfClassAssigned(taList, currentTutor) < 10) {
                while (domainIT.hasNext()) {
                    String domain = (String) domainIT.next();
                    if (!currentTutor.getDomainKnowledgeList().contains(domain)) {
                        qualifiedTutor = false;
                        break;
                    }

                }
            } else {
                qualifiedTutor = false;
            }

            if (qualifiedTutor == true) {
                qualifiedTutorList.add(currentTutor);
            }

        }
        return qualifiedTutorList;

    }

    public ListInterface<TeachingAssignment> getUnAssignedTeachingList(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getTutor().getTutorID() == null) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public int getNoOfClassAssigned(ListInterface<TeachingAssignment> taList, Tutor tutor) {
        Iterator<TeachingAssignment> taListIT = taList.getIterator();
        int numberOfClass = 0;

        while (taListIT.hasNext()) {
            TeachingAssignment ta = taListIT.next();
            numberOfClass++;
        }
        return numberOfClass;

    }

    public ListInterface<TeachingAssignment> filterByTutor(ListInterface<TeachingAssignment> taList, Tutor tutor) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<TeachingAssignment>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getTutor().equals(tutor)) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public ListInterface<TeachingAssignment> filterByProgramme(ListInterface<TeachingAssignment> taList, String programme) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<TeachingAssignment>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getTutorialGroup().getProgramme().equals(programme)) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public ListInterface<TeachingAssignment> filterByTutorialGrp(ListInterface<TeachingAssignment> taList, TutorialGroup tutGrp) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<TeachingAssignment>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getTutorialGroup().equals(tutGrp)) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public ListInterface<TeachingAssignment> filterByCourse(ListInterface<TeachingAssignment> taList, Course course) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<TeachingAssignment>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getCourse().equals(course)) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public ListInterface<String> getUniqueProgramme(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<String> programmeIDList = new ArrayList<>();

        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (!programmeIDList.contains(currentTA.getTutorialGroup().getProgramme())) {
                programmeIDList.add(currentTA.getTutorialGroup().getProgramme());
            }
        }
        return programmeIDList;
    }

    public ListInterface<Course> getUniqueCourse(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<Course> courseList = new ArrayList<Course>();

        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (!courseList.contains(currentTA.getCourse())) {
                courseList.add(currentTA.getCourse());
            }
        }
        return courseList;
    }

    public ListInterface<TutorialGroup> getUniqueTutGrp(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<TutorialGroup> tutGrpList = new ArrayList<TutorialGroup>();

        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (!tutGrpList.contains(currentTA.getTutorialGroup())) {
                tutGrpList.add(currentTA.getTutorialGroup());
            }
        }
        return tutGrpList;
    }

    public ListInterface<TeachingAssignment> convertToArrayList(ListInterface<TeachingAssignment> taList) {
        ListInterface<TeachingAssignment> presort = new ArrayList<>();
        Iterator<TeachingAssignment> it = taList.getIterator();
        while (it.hasNext()) {
            TeachingAssignment ta = it.next();
            presort.add(ta);
        }
        return presort;
    }

    public ListInterface<Course> sortCourseByName(ListInterface<Course> courseList) {
        for (int i = 1; i < courseList.getNumberOfEntries(); i++) {
            for (int j = i + 1; j <= courseList.getNumberOfEntries(); j++) {
                if (courseList.getEntry(i).getCourseName().compareTo(courseList.getEntry(j).getCourseName()) > 0) {
                    Course temp = courseList.getEntry(i);
                    courseList.replace(i, courseList.getEntry(j));
                    courseList.replace(j, temp);
                }
            }
        }
        return courseList;

    }

    public ListInterface<Tutor> sortTutorByName(ListInterface<Tutor> tutorList) {
        for (int i = 1; i < tutorList.getNumberOfEntries(); i++) {
            for (int j = i + 1; j <= tutorList.getNumberOfEntries(); j++) {
                if (tutorList.getEntry(i).getName().compareTo(tutorList.getEntry(j).getName()) > 0) {
                    Tutor temp = tutorList.getEntry(i);
                    tutorList.replace(i, tutorList.getEntry(j));
                    tutorList.replace(j, temp);
                }
            }
        }
        return tutorList;
    }


}
