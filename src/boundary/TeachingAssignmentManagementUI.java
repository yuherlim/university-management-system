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

    public void assignByCourse(ListInterface<TeachingAssignment> taList, ListInterface<Tutor> tutorList) {
        ListInterface<TeachingAssignment> unAssignedTAList = this.getUnAssignedTeachingList(taList);
        int assignByCourseSelection = -1;
        
        if (!unAssignedTAList.isEmpty()) {
            do {
                ListInterface<Course> courseCodeList = this.getUniqueCourse(unAssignedTAList);
                int selection = -1;
                do{
                int courseSelection = this.getCourseSelection(courseCodeList);
                ListInterface<Tutor> qualifiedTutorList = this.getQualifiedTutorList(tutorList, courseCodeList.getEntry(courseSelection).getRequiredDomainKnowledge());
                         
                
                
                }while(selection !=0);
                

            } while (assignByCourseSelection != 0);

        } else {
            System.out.printf("\nAll course are Assigned");
        }

    }

    public int getProgrammeSelection(ListInterface<String> programmeList) {
        Iterator<String> programmeIT = programmeList.getIterator();
        int selection = -1;
        int count = 1;
        while (true) {
            try {
                System.out.println("Programme List:");
                while (programmeIT.hasNext()) {
                    String programme = programmeIT.next();
                    System.out.printf("\n%02d. %s", count++, programme);
                }
                System.out.printf("\n 0. Exit");
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= programmeList.getNumberOfEntries()) {
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

    public int getCourseSelection(ListInterface<Course> courseList) {
       
        int selection = -1;
        int count = 1;
        while (true) {
            Iterator<Course> courseIT = courseList.getIterator();
            try {
                System.out.printf("\nCourse List:");
                while (courseIT.hasNext()) {
                    Course course = courseIT.next();
                    System.out.printf("\n%02d. %s", count++, course.getCourseName());
                }
                System.out.printf("\n 0. Exit");
                System.out.print("\nEnter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= courseList.getNumberOfEntries()) {
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

    public int getQualifiedTutorSelection(ListInterface<Tutor> tutorList) {
        Iterator<Tutor> tutorIT = tutorList.getIterator();
        int selection = -1;
        int count = 1;
        while (true) {
            try {
                System.out.println("Course List:");
                while (tutorIT.hasNext()) {
                    Tutor tutor = tutorIT.next();
                    System.out.printf("\n%02d. %s", count++, tutor.getName());
                }
                System.out.printf("\n 0. Exit");
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= tutorList.getNumberOfEntries()) {
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

    public int getTutorialGrpSelection(ListInterface<TutorialGroup> tutGrpList) {
        Iterator<TutorialGroup> tutGrpIT = tutGrpList.getIterator();
        int selection = -1;
        int count = 1;
        while (true) {
            try {
                System.out.println("Course List:");
                while (tutGrpIT.hasNext()) {
                    TutorialGroup tutGrp = tutGrpIT.next();
                    System.out.printf("\n%02d. %s", count++, tutGrp.getId());
                }
                System.out.printf("\n 0. Exit");
                System.out.print("Enter choice: ");
                selection = scanner.nextInt();
                if (selection >= 0 && selection <= tutGrpList.getNumberOfEntries()) {
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

    public ListInterface<Tutor> getQualifiedTutorList(ListInterface<Tutor> tutorList, ListInterface<String> domainList) {
        Iterator tutorIT = tutorList.getIterator();
        ListInterface<Tutor> qualifiedTutorList = (ListInterface<Tutor>) new ArrayList<Tutor>();

        while (tutorIT.hasNext()) {
            Tutor currentTutor = (Tutor) tutorIT.next();
            Iterator domainIT = domainList.getIterator();
            Boolean qualifiedTutor = true;
            while (domainIT.hasNext()) {
                String domain = (String) domainIT.next();
                if (!currentTutor.getDomainKnowledgeList().contains(domain)) {
                    qualifiedTutor = false;
                }
                break;
            }

            if (qualifiedTutor == true) {
                qualifiedTutorList.add(currentTutor);
            }

        }
        return qualifiedTutorList;

    }

    public ListInterface<TeachingAssignment> getUnAssignedTeachingList(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<>() ;
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getTutor().getTutorID() == null) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public ListInterface<TeachingAssignment> filterByTutor(ListInterface<TeachingAssignment> taList, Tutor tutor) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList =  new ArrayList<TeachingAssignment>();
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
        ListInterface<TeachingAssignment> resultList =  new ArrayList<TeachingAssignment>();
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
        ListInterface<TeachingAssignment> resultList =  new ArrayList<TeachingAssignment>();
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
        ListInterface<TeachingAssignment> resultList =  new ArrayList<TeachingAssignment>();
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
        ListInterface<String> programmeIDList =  new ArrayList<>();

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
        ListInterface<Course> courseList =  new ArrayList<Course>();

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

}
