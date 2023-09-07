/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import adt.CircularDoublyLinkedList;
import adt.ListInterface;
import boundary.TeachingAssignmentManagementUI;
import dao.CourseDAO;
import dao.CourseInitializer;
import dao.ProgrammeDAO;
import dao.TeachingAssignmentDAO;
import dao.TutorDAO;
import dao.TutorialGroupDAO;
import entity.Course;
import entity.Programme;
import entity.TeachingAssignment;
import entity.Tutor;
import entity.TutorialGroup;
import java.util.Iterator;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author ong58
 */
public class TeachingAssignmentManagement {

    private ListInterface<TeachingAssignment> teachingAssignmentList = new CircularDoublyLinkedList<>();
    private static TeachingAssignmentDAO teachingAssignmentDAO = new TeachingAssignmentDAO();
    private static TeachingAssignmentManagementUI teachingAssignmentUI = new TeachingAssignmentManagementUI();
    Scanner scanner = new Scanner(System.in);

    public TeachingAssignmentManagement() {
        teachingAssignmentList = teachingAssignmentDAO.retrieveFromFile();
    }

    public void assignTutor(String currentBatch) {
        TutorDAO tutorDAO = new TutorDAO();
        ListInterface tutorList = tutorDAO.retrieveFromFile();
        assignByCourse(currentBatch, teachingAssignmentList, tutorList);

    }

    public void modifyTutorAssignment(String currentBatch) {
        TutorDAO tutorDAO = new TutorDAO();
        ListInterface tutorList = tutorDAO.retrieveFromFile();
        modifyByTutor(currentBatch, teachingAssignmentList, tutorList);
    }

    public void searchTutorAssignment(String currentBatch) {
        TutorDAO tutorDAO = new TutorDAO();
        CourseDAO courseDAO = new CourseDAO();
        ListInterface tutorList = tutorDAO.retrieveFromFile();
        ListInterface courseList = courseDAO.retrieveFromFile();
        int selection = teachingAssignmentUI.getSearchTutorAssignmentOption();
        switch (selection) {
            case 1:
                searchByTutor(currentBatch, teachingAssignmentList, tutorList);
                break;
            case 2:
                searchByCourse(currentBatch, teachingAssignmentList, courseList);
                break;
            default:
        }
    }

    public void filterTeachingAssignment(String currentBatch) {
        ProgrammeDAO programmeDAO = new ProgrammeDAO();
        ListInterface<Programme> programmeList = programmeDAO.retrieveFromFile();

        int selection = teachingAssignmentUI.getFilterTutorAssignmentOption();
        switch (selection) {
            case 1:
                tutorsFilterByNumOfClassAssigned(currentBatch, teachingAssignmentList);
                break;
            case 2:
                tutorsFilterByProgramme(currentBatch, teachingAssignmentList, programmeList);
                break;
            default:
        }
    }

    public void listTeachingAssignment(String currentBatch) {
        TutorDAO tutorDAO = new TutorDAO();
        CourseDAO courseDAO = new CourseDAO();
        ListInterface tutorList = tutorDAO.retrieveFromFile();
        ListInterface courseList = courseDAO.retrieveFromFile();
        int selection = -1;
        do {
            selection = teachingAssignmentUI.getListTutorAssignmentOption();
            switch (selection) {
                case 1:
                    listTutors(currentBatch, teachingAssignmentList, courseList);
                    break;
                case 2:
                    listCourses(currentBatch, teachingAssignmentList, tutorList);
                    break;
                default:
            }
        } while (selection != 0);
    }

    public void generateReport() {
        int selection = -1;
        do {
            selection = teachingAssignmentUI.getMenuChoice();
            switch (selection) {
                case 1:
                    break;
                case 2:
                    break;
                default:
            }
        } while (selection != 0);
    }

    public void assignByCourse(String currentBatch, ListInterface<TeachingAssignment> taList, ListInterface<Tutor> tutorList) {
        ListInterface<TeachingAssignment> currentBatchTAList = getCurrentBatchTeachingList(currentBatch, taList);
        ListInterface<TeachingAssignment> unAssignedTAList = this.getUnAssignedTeachingList(currentBatchTAList);
        Character cont = 'Y';
        do {
            if (!unAssignedTAList.isEmpty()) {
                ListInterface<Course> courseCodeList = this.getUniqueCourse(unAssignedTAList);
                String displayStringForCourseSelection = String.format(""
                        + "Assign Tutor                       : \n");
                Course selectedCourse = teachingAssignmentUI.getCourseSelection(courseCodeList, displayStringForCourseSelection);
                if (selectedCourse != null) {
                    do {
                        ListInterface<Tutor> qualifiedTutorList = this.getQualifiedTutorList(tutorList, selectedCourse.getRequiredDomainKnowledge(), currentBatchTAList);
                        if (qualifiedTutorList.getNumberOfEntries() == 0) {
                            System.out.printf("\nNo available Tutor");
                            break;
                        } else {
                            ListInterface<TeachingAssignment> selectedCoursetaList = this.filterByCourse(unAssignedTAList, selectedCourse);
                            ListInterface<TeachingAssignment> taListSortedByProgramme = this.sortTAByProgramme(selectedCoursetaList);
                            String displayStringForTutorSelection = String.format(""
                                    + "Selected Course                       : %s "
                                    + "\nNo of class available                 : %2d\n", selectedCourse.getCourseName(), selectedCoursetaList.getNumberOfEntries());
                            if (selectedCoursetaList.getNumberOfEntries() != 0) {
                                Tutor selectedTutor = teachingAssignmentUI.getTutorSelection(qualifiedTutorList, currentBatchTAList, displayStringForTutorSelection);
                                if (selectedTutor != null) {
                                    do {
                                        int noOfClassAssigned = this.getNoOfClassAssigned(currentBatchTAList, selectedTutor);
                                        String displayStringForTutGrpSelection = String.format(""
                                                + "Selected Course                       : %s "
                                                + "\nSelected Tutor                        : %s"
                                                + "\nNo Of class assigned (Max 15 classes) : %2d\n", selectedCourse.getCourseName(), selectedTutor.getName(), noOfClassAssigned);
                                        if (taListSortedByProgramme.getNumberOfEntries() != 0) {
                                            TeachingAssignment selectedTA = teachingAssignmentUI.getTASelection(taListSortedByProgramme, displayStringForTutGrpSelection);
                                            if (selectedTA != null) {
                                                this.teachingAssignmentList.replace(selectedTA, new TeachingAssignment(selectedTA.getTutorialGroup(), selectedTA.getCourse(), selectedTutor));
                                                currentBatchTAList.replace(selectedTA, new TeachingAssignment(selectedTA.getTutorialGroup(), selectedTA.getCourse(), selectedTutor));
                                                unAssignedTAList.remove(selectedTA);
                                                taListSortedByProgramme.remove(selectedTA);
                                                System.out.printf("\nTutor assigned succesful\n");
                                                MessageUI.pause();
                                            }

                                            if (this.getNoOfClassAssigned(currentBatchTAList, selectedTutor) < 15) {

                                                while (true) {
                                                    System.out.printf("\nContinue select Tutorial Group? (Y/N) : ");
                                                    cont = scanner.next().toUpperCase().charAt(0);
                                                    if (cont == 'Y' || cont == 'N') {
                                                        break;
                                                    }
                                                    System.out.printf("\nPlease enter a valid selection\n");
                                                    MessageUI.pause();
                                                }
                                            } else {
                                                displayStringForTutGrpSelection = String.format(""
                                                        + "Selected Course                       : %s "
                                                        + "\nSelected Tutor                        : %s"
                                                        + "\nNo Of class assigned (Max 15 classes) : %2d\n", selectedCourse.getCourseName(), selectedTutor.getName(), ++noOfClassAssigned);
                                                MessageUI.TeachingAssignmentTopDivider();
                                                System.out.print(displayStringForTutGrpSelection);
                                                MessageUI.TeachingAssignmentBtmDivider();
                                                System.out.println("\nThis Tutor has assigned 15 classes");
                                                cont = 'N';
                                            }
                                        } else {
                                            MessageUI.TeachingAssignmentTopDivider();
                                            System.out.print(displayStringForTutGrpSelection);
                                            MessageUI.TeachingAssignmentBtmDivider();
                                            System.out.println("\nNo available class");
                                            cont = 'N';
                                        }
                                    } while (cont == 'Y');
                                }

                                while (true) {
                                    System.out.printf("\nContinue select Tutor? (Y/N) : ");
                                    cont = scanner.next().toUpperCase().charAt(0);
                                    if (cont == 'Y' || cont == 'N') {
                                        break;
                                    }
                                    System.out.printf("\nPlease enter a valid selection\n");
                                    MessageUI.pause();
                                }
                            } else {
                                MessageUI.TeachingAssignmentTopDivider();
                                System.out.print(displayStringForTutorSelection);
                                MessageUI.TeachingAssignmentBtmDivider();
                                System.out.println("\nAll class for selected course are assigned");
                                cont = 'N';
                            }
                        }
                    } while (cont == 'Y');

                    while (true) {
                        System.out.printf("\nContinue select course? (Y/N) : ");
                        cont = scanner.next().toUpperCase().charAt(0);
                        if (cont == 'Y' || cont == 'N') {
                            break;
                        }
                        System.out.printf("\nPlease enter a valid selection\n");
                        MessageUI.pause();
                    }
                }

            } else {
                System.out.printf("\nAll course in batch %s are assigned\n", currentBatch);
                MessageUI.pause();
                cont = 'N';
            }
        } while (cont
                == 'Y');
        MessageUI.displayExit();

        MessageUI.pause();

        teachingAssignmentDAO.saveToFile(taList);

    } //    public void modifyByTutor

    public void modifyByTutor(String currentBatch, ListInterface<TeachingAssignment> taList, ListInterface<Tutor> tutorList) {
        Character cont = 'Y';
        do {
            ListInterface<TeachingAssignment> currentBatchTAList = getCurrentBatchTeachingList(currentBatch, taList);
            ListInterface<TeachingAssignment> assignedTAList = getAssignedTeachingList(currentBatchTAList);
            if (assignedTAList.getNumberOfEntries() != 0) {
                Tutor tutor = teachingAssignmentUI.getTutor(tutorList);
                if (tutor != null) {
                    do {
                        assignedTAList = getAssignedTeachingList(getCurrentBatchTeachingList(currentBatch, taList));
                        ListInterface<TeachingAssignment> assignedTAListFilterByTutor = sortTAByProgramme(TeachingAssignmentManagement.filterByTutor(assignedTAList, tutor));
                        ListInterface<Course> courseList = TeachingAssignmentManagement.getUniqueCourse(assignedTAListFilterByTutor);

                        String displayStringForCourseSelection = String.format(""
                                + "Tutor ID                              : %s"
                                + "\nTutor Name                            : %s"
                                + "\nNo Of class assigned (Max 15 classes) : %2d\n", tutor.getTutorID(), tutor.getName(), assignedTAListFilterByTutor.getNumberOfEntries());

                        Course selectedCourse = teachingAssignmentUI.getCourseSelection(courseList, displayStringForCourseSelection);

                        if (selectedCourse != null) {
                            do {
                                ListInterface<TeachingAssignment> taListfilterByCourse = TeachingAssignmentManagement.filterByCourse(assignedTAListFilterByTutor, selectedCourse);
                                String displayStringForTutGrpSelection = String.format(""
                                        + "Tutor ID                              : %s"
                                        + "\nTutor Name                            : %s"
                                        + "\nNo Of class assigned (Max 15 classes) : %2d"
                                        + "\nCourse                                : %s\n", tutor.getTutorID(), tutor.getName(), assignedTAListFilterByTutor.getNumberOfEntries(), selectedCourse.getCourseName());

                                if (taListfilterByCourse.getNumberOfEntries() != 0) {
                                    TeachingAssignment selectedTA = teachingAssignmentUI.getTASelection(taListfilterByCourse, displayStringForTutGrpSelection);
                                    if (selectedTA != null) {
                                        int selection = teachingAssignmentUI.getModifyTutorOption();
                                        switch (selection) {
                                            case 1:
                                                ListInterface<Tutor> qualifiedTutorList = this.getQualifiedTutorList(tutorList, selectedCourse.getRequiredDomainKnowledge(), currentBatchTAList);
                                                qualifiedTutorList.remove(tutor);
                                                String displayStringForTutorSelection = String.format(""
                                                        + "Selected Course                       : %s\n", selectedCourse.getCourseName());
                                                Tutor selectedTutor = teachingAssignmentUI.getTutorSelection(qualifiedTutorList, currentBatchTAList, displayStringForTutorSelection);
                                                if (selectedTutor != null) {
                                                    this.teachingAssignmentList.replace(selectedTA, new TeachingAssignment(selectedTA.getTutorialGroup(), selectedTA.getCourse(), selectedTutor));
                                                    assignedTAListFilterByTutor.remove(selectedTA);
                                                    System.out.printf("\nTutor reassigned succesful\n");
                                                }
                                                break;
                                            case 2:
                                                this.teachingAssignmentList.replace(selectedTA, new TeachingAssignment(selectedTA.getTutorialGroup(), selectedTA.getCourse(), new Tutor()));
                                                assignedTAListFilterByTutor.remove(selectedTA);
                                                assignedTAList.remove(selectedTA);
                                                System.out.printf("\nTutor unassigned succesful\n");
                                                MessageUI.pause();
                                                break;
                                            default:
                                                break;

                                        }

                                    }

                                    while (true) {
                                        System.out.printf("\nContinue select class? (Y/N) : ");
                                        cont = scanner.next().toUpperCase().charAt(0);
                                        if (cont == 'Y' || cont == 'N') {
                                            break;
                                        }
                                        System.out.printf("\nPlease enter a valid selection\n");
                                        MessageUI.pause();
                                    }
                                } else {
                                    MessageUI.TeachingAssignmentTopDivider();
                                    System.out.print(displayStringForTutGrpSelection);
                                    MessageUI.TeachingAssignmentBtmDivider();
                                    System.out.println("\nNo available class");
                                    cont = 'N';
                                }

                            } while (cont == 'Y');
                        }

                        while (true) {
                            System.out.printf("\nContinue select course? (Y/N) : ");
                            cont = scanner.next().toUpperCase().charAt(0);
                            if (cont == 'Y' || cont == 'N') {
                                break;
                            }
                            System.out.printf("\nPlease enter a valid selection\n");
                            MessageUI.pause();
                        }

                    } while (cont == 'Y');

                } else {
                    System.out.print("\nTutor not found\n");
                }

                while (true) {
                    System.out.printf("\nContinue search tutor? (Y/N) : ");
                    cont = scanner.next().toUpperCase().charAt(0);
                    if (cont == 'Y' || cont == 'N') {
                        break;
                    }
                    System.out.printf("\nPlease enter a valid selection\n");
                    MessageUI.pause();
                }

            } else {
                System.out.printf("\nNo course in batch %s are Assigned\n", currentBatch);
                MessageUI.pause();
                cont = 'N';
            }

        } while (cont
                == 'Y');
        MessageUI.displayExit();
        MessageUI.pause();
        teachingAssignmentDAO.saveToFile(taList);

    }

    public void searchByTutor(String currentBatch, ListInterface<TeachingAssignment> taList, ListInterface<Tutor> tutorList) {
        Character cont = 'Y';
        ListInterface<TeachingAssignment> currentBatchTAList = getCurrentBatchTeachingList(currentBatch, taList);
        ListInterface<TeachingAssignment> assignedTAList = getAssignedTeachingList(currentBatchTAList);
        do {
            if (assignedTAList.getNumberOfEntries() != 0) {
                Tutor tutor = teachingAssignmentUI.getTutor(tutorList);
                if (tutor != null) {
                    do {
                        ListInterface<TeachingAssignment> assignedTAListFilterByTutor = sortTAByProgramme(TeachingAssignmentManagement.filterByTutor(assignedTAList, tutor));
                        ListInterface<Course> courseList = TeachingAssignmentManagement.getUniqueCourse(assignedTAListFilterByTutor);
                        String displayStringForCourseSelection = String.format(""
                                + "Tutor ID                              : %s"
                                + "\nTutor Name                            : %s"
                                + "\nTutor Gender                          : %s"
                                + "\nTutor Education Level                 : %s"
                                + "\nTutor Domain Knowledge                : %s"
                                + "\nNo Of class assigned (Max 15 classes) : %2d\n", tutor.getTutorID(), tutor.getName(), tutor.getGender(), tutor.getEducationLevel(), teachingAssignmentUI.getDomainKnowledgeString(tutor.getDomainKnowledgeList()), assignedTAListFilterByTutor.getNumberOfEntries());

                        if (assignedTAListFilterByTutor.getNumberOfEntries() != 0) {

                            Course selectedCourse = teachingAssignmentUI.getCourseSelection(courseList, displayStringForCourseSelection);
                            if (selectedCourse != null) {
                                ListInterface<TeachingAssignment> assignedTAListFilterByCourse = TeachingAssignmentManagement.filterByCourse(assignedTAListFilterByTutor, selectedCourse);
                                String displayStringForCourseDetail = String.format(""
                                        + "Course ID                 : %s"
                                        + "\nCourse Name               : %s"
                                        + "\nRequired Domain Knowledge : %s"
                                        + "\nNo of class assigned      : %2d\n", selectedCourse.getCourseCode(), selectedCourse.getCourseName(), teachingAssignmentUI.getDomainKnowledgeString(selectedCourse.getRequiredDomainKnowledge()), assignedTAListFilterByCourse.getNumberOfEntries());
                                while (true) {
                                    TeachingAssignment ta = teachingAssignmentUI.getTASelection(assignedTAListFilterByCourse, displayStringForCourseDetail);
                                    if (ta == null) {
                                        break;
                                    }
                                    MessageUI.displayInvalidChoiceMessage();
                                    MessageUI.pause();
                                }

                            }

                            while (true) {
                                System.out.printf("\nContinue select course? (Y/N) : ");
                                cont = scanner.next().toUpperCase().charAt(0);
                                if (cont == 'Y' || cont == 'N') {
                                    break;
                                }
                                System.out.printf("\nPlease enter a valid selection\n");
                                MessageUI.pause();
                            }

                        } else {
                            MessageUI.TeachingAssignmentTopDivider();
                            System.out.print(displayStringForCourseSelection);
                            MessageUI.TeachingAssignmentBtmDivider();
                            System.out.println("\nNo class assigned to this tutor");
                            cont = 'N';
                        }

                    } while (cont == 'Y');

                } else {
                    System.out.print("\nTutor not found\n");
                }

                while (true) {
                    System.out.printf("\nContinue search tutor? (Y/N) : ");
                    cont = scanner.next().toUpperCase().charAt(0);
                    if (cont == 'Y' || cont == 'N') {
                        break;
                    }
                    System.out.printf("\nPlease enter a valid selection\n");
                    MessageUI.pause();
                }

            } else {
                System.out.printf("\nNo course in batch %s are Assigned\n", currentBatch);
                MessageUI.pause();
                cont = 'N';
            }

        } while (cont == 'Y');

    }

    public void searchByCourse(String currentBatch, ListInterface<TeachingAssignment> taList, ListInterface<Course> courseList) {
        Character cont = 'Y';
        ListInterface<TeachingAssignment> currentBatchTAList = getCurrentBatchTeachingList(currentBatch, taList);
        ListInterface<TeachingAssignment> assignedTAList = getAssignedTeachingList(currentBatchTAList);
        do {
            if (assignedTAList.getNumberOfEntries() != 0) {
                Course course = teachingAssignmentUI.getCourse(courseList);
                if (course != null) {
                    do {
                        ListInterface<TeachingAssignment> assignedTAListFilterByCourse = TeachingAssignmentManagement.filterByCourse(assignedTAList, course);
                        ListInterface<Tutor> tutorList = TeachingAssignmentManagement.getUniqueTutor(assignedTAListFilterByCourse);
                        String displayStringForTutorSelection = String.format(""
                                + "Course ID                 : %s"
                                + "\nCourse Name               : %s"
                                + "\nRequired Domain Knowledge : %s"
                                + "\nNo of class assigned      : %2d\n", course.getCourseCode(), course.getCourseName(), teachingAssignmentUI.getDomainKnowledgeString(course.getRequiredDomainKnowledge()), assignedTAListFilterByCourse.getNumberOfEntries());

                        if (assignedTAListFilterByCourse.getNumberOfEntries() != 0) {
                            Tutor selectedTutor = teachingAssignmentUI.getTutorSelection(tutorList, assignedTAListFilterByCourse, displayStringForTutorSelection);
                            if (selectedTutor != null) {
                                ListInterface<TeachingAssignment> assignedTAListFilterByTutor = TeachingAssignmentManagement.filterByTutor(assignedTAListFilterByCourse, selectedTutor);
                                String displayStringForTutorDetail = String.format(""
                                        + "Tutor ID                              : %s"
                                        + "\nTutor Name                            : %s"
                                        + "\nTutor Gender                          : %s"
                                        + "\nTutor Education Level                 : %s"
                                        + "\nTutor Domain Knowledge                : %s"
                                        + "\nNo Of class assigned (Max 15 classes) : %2d\n", selectedTutor.getTutorID(), selectedTutor.getName(), selectedTutor.getGender(), selectedTutor.getEducationLevel(), teachingAssignmentUI.getDomainKnowledgeString(selectedTutor.getDomainKnowledgeList()), assignedTAListFilterByTutor.getNumberOfEntries());

                                while (true) {
                                    TeachingAssignment ta = teachingAssignmentUI.getTASelection(assignedTAListFilterByTutor, displayStringForTutorDetail);
                                    if (ta == null) {
                                        break;
                                    }
                                    MessageUI.displayInvalidChoiceMessage();
                                    MessageUI.pause();
                                }

                            }

                            while (true) {
                                System.out.printf("\nContinue select Tutor? (Y/N) : ");
                                cont = scanner.next().toUpperCase().charAt(0);
                                if (cont == 'Y' || cont == 'N') {
                                    break;
                                }
                                System.out.printf("\nPlease enter a valid selection\n");
                                MessageUI.pause();
                            }
                        } else {
                            MessageUI.TeachingAssignmentTopDivider();
                            System.out.print(displayStringForTutorSelection);
                            MessageUI.TeachingAssignmentBtmDivider();
                            System.out.println("\nThis course didnt has any class assigned");
                            cont = 'N';
                        }

                    } while (cont == 'Y');

                } else {
                    System.out.print("\nCourse not found\n");
                }

                while (true) {
                    System.out.printf("\nContinue search course? (Y/N) : ");
                    cont = scanner.next().toUpperCase().charAt(0);
                    if (cont == 'Y' || cont == 'N') {
                        break;
                    }
                    System.out.printf("\nPlease enter a valid selection\n");
                    MessageUI.pause();
                }

            } else {
                System.out.printf("\nNo course in batch %s are Assigned\n", currentBatch);
                MessageUI.pause();
                cont = 'N';
            }

        } while (cont == 'Y');

    }

    public void listTutors(String currentBatch, ListInterface<TeachingAssignment> taList, ListInterface<Course> courseList) {
        Character cont = 'Y';
        ListInterface<TeachingAssignment> currentBatchTAList = getCurrentBatchTeachingList(currentBatch, taList);
        ListInterface<TeachingAssignment> assignedTAList = getAssignedTeachingList(currentBatchTAList);
        do {
            if (assignedTAList.getNumberOfEntries() != 0) {
                Course course = teachingAssignmentUI.getCourse(courseList);
                if (course != null) {
                    ListInterface<TeachingAssignment> assignedTAListFilterByCourse = TeachingAssignmentManagement.filterByCourse(assignedTAList, course);
                    ListInterface<Tutor> tutorList = TeachingAssignmentManagement.getUniqueTutor(assignedTAListFilterByCourse);
                    String displayStringForTutorSelection = String.format(""
                            + "Course ID                 : %s"
                            + "\nCourse Name               : %s"
                            + "\nRequired Domain Knowledge : %s"
                            + "\nNo of class assigned      : %2d\n", course.getCourseCode(), course.getCourseName(), teachingAssignmentUI.getDomainKnowledgeString(course.getRequiredDomainKnowledge()), assignedTAListFilterByCourse.getNumberOfEntries());

                    if (assignedTAListFilterByCourse.getNumberOfEntries() != 0) {

                        while (true) {
                            Tutor selectedTutor = teachingAssignmentUI.getTutorSelection(tutorList, assignedTAListFilterByCourse, displayStringForTutorSelection);
                            if (selectedTutor == null) {
                                break;
                            }
                            MessageUI.displayInvalidChoiceMessage();
                            MessageUI.pause();
                        }

                    } else {
                        MessageUI.TeachingAssignmentTopDivider();
                        System.out.print(displayStringForTutorSelection);
                        MessageUI.TeachingAssignmentBtmDivider();
                        System.out.println("\nThis course didnt has any class assigned");
                        cont = 'N';
                    }

                } else {
                    System.out.print("\nCourse not found\n");
                }

                while (true) {
                    System.out.printf("\nContinue search course? (Y/N) : ");
                    cont = scanner.next().toUpperCase().charAt(0);
                    if (cont == 'Y' || cont == 'N') {
                        break;
                    }
                    System.out.printf("\nPlease enter a valid selection\n");
                    MessageUI.pause();
                }

            } else {
                System.out.printf("\nNo course in batch %s are Assigned\n", currentBatch);
                MessageUI.pause();
                cont = 'N';
            }

        } while (cont == 'Y');

    }

    public void listCourses(String currentBatch, ListInterface<TeachingAssignment> taList, ListInterface<Tutor> tutorList) {
        Character cont = 'Y';
        ListInterface<TeachingAssignment> currentBatchTAList = getCurrentBatchTeachingList(currentBatch, taList);
        ListInterface<TeachingAssignment> assignedTAList = getAssignedTeachingList(currentBatchTAList);
        do {
            if (assignedTAList.getNumberOfEntries() != 0) {
                Tutor tutor = teachingAssignmentUI.getTutor(tutorList);
                if (tutor != null) {
                    ListInterface<TeachingAssignment> assignedTAListFilterByTutor = sortTAByProgramme(TeachingAssignmentManagement.filterByTutor(assignedTAList, tutor));
                    ListInterface<Course> courseList = TeachingAssignmentManagement.getUniqueCourse(assignedTAListFilterByTutor);
                    String displayStringForCourseSelection = String.format(""
                            + "Tutor ID                              : %s"
                            + "\nTutor Name                            : %s"
                            + "\nTutor Gender                          : %s"
                            + "\nTutor Education Level                 : %s"
                            + "\nTutor Domain Knowledge                : %s"
                            + "\nNo Of class assigned (Max 15 classes) : %2d\n", tutor.getTutorID(), tutor.getName(), tutor.getGender(), tutor.getEducationLevel(), teachingAssignmentUI.getDomainKnowledgeString(tutor.getDomainKnowledgeList()), assignedTAListFilterByTutor.getNumberOfEntries());

                    if (assignedTAListFilterByTutor.getNumberOfEntries() != 0) {

                        while (true) {
                            Course selectedCourse = teachingAssignmentUI.getCourseSelection(courseList, displayStringForCourseSelection);
                            if (selectedCourse == null) {
                                break;
                            }
                            MessageUI.displayInvalidChoiceMessage();
                            MessageUI.pause();
                        }

                    } else {
                        MessageUI.TeachingAssignmentTopDivider();
                        System.out.print(displayStringForCourseSelection);
                        MessageUI.TeachingAssignmentBtmDivider();
                        System.out.println("\nNo class assigned to this tutor");
                        cont = 'N';
                    }

                } else {
                    System.out.print("\nTutor not found\n");
                }

                while (true) {
                    System.out.printf("\nContinue search tutor? (Y/N) : ");
                    cont = scanner.next().toUpperCase().charAt(0);
                    if (cont == 'Y' || cont == 'N') {
                        break;
                    }
                    System.out.printf("\nPlease enter a valid selection\n");
                    MessageUI.pause();
                }

            } else {
                System.out.printf("\nNo course in batch %s are Assigned\n", currentBatch);
                MessageUI.pause();
                cont = 'N';
            }

        } while (cont == 'Y');

    }

    public void tutorsFilterByProgramme(String currentBatch, ListInterface<TeachingAssignment> taList, ListInterface<Programme> programmeList) {
        Character cont = 'Y';
        ListInterface<TeachingAssignment> currentBatchTAList = getCurrentBatchTeachingList(currentBatch, taList);
        ListInterface<TeachingAssignment> assignedTAList = getAssignedTeachingList(currentBatchTAList);
        ListInterface<Programme> sortedProgrammeList = TeachingAssignmentManagement.sortProgrammeByName(programmeList);
        do {
            if (assignedTAList.getNumberOfEntries() != 0) {
                Programme programme = teachingAssignmentUI.getProgrammeSelection(sortedProgrammeList);
                if (programme != null) {
                    ListInterface<TeachingAssignment> assignedTAListFilterByProgramme = sortTAByProgramme(TeachingAssignmentManagement.filterByProgramme(assignedTAList, programme.getCode()));
                    ListInterface<Tutor> tutorList = TeachingAssignmentManagement.getUniqueTutor(assignedTAListFilterByProgramme);
                    String displayStringForCourseSelection = String.format(""
                            + "Programme ID                          : %s"
                            + "\nProgramme Name                        : %s"
                            + "\nProgramme Faculty                     : %s"
                            + "\nProgramme Duration (year)             : %2d"
                            + "\nNo Of class assigned                  : %2d\n", programme.getCode(), programme.getName(), programme.getFaculty(), programme.getDuration(), assignedTAListFilterByProgramme.getNumberOfEntries());

                    if (assignedTAListFilterByProgramme.getNumberOfEntries() != 0) {

                        while (true) {
                            Tutor selectedTutor = teachingAssignmentUI.getTutorSelection(tutorList, assignedTAListFilterByProgramme, displayStringForCourseSelection);
                            if (selectedTutor == null) {
                                break;
                            }
                            MessageUI.displayInvalidChoiceMessage();
                            MessageUI.pause();
                        }

                    } else {
                        MessageUI.TeachingAssignmentTopDivider();
                        System.out.print(displayStringForCourseSelection);
                        MessageUI.TeachingAssignmentBtmDivider();
                        System.out.println("\nThis programme didnt has any class assigned");
                        cont = 'N';
                    }

                }
                while (true) {
                    System.out.printf("\nContinue select programme? (Y/N) : ");
                    cont = scanner.next().toUpperCase().charAt(0);
                    if (cont == 'Y' || cont == 'N') {
                        break;
                    }
                    System.out.printf("\nPlease enter a valid selection\n");
                    MessageUI.pause();
                }

            } else {
                System.out.printf("\nNo course in batch %s are Assigned\n", currentBatch);
                MessageUI.pause();
                cont = 'N';
            }

        } while (cont == 'Y');

    }

    public void tutorsFilterByNumOfClassAssigned(String currentBatch, ListInterface<TeachingAssignment> taList) {
        Character cont = 'Y';
        ListInterface<TeachingAssignment> currentBatchTAList = getCurrentBatchTeachingList(currentBatch, taList);
        ListInterface<TeachingAssignment> assignedTAList = getAssignedTeachingList(currentBatchTAList);

        do {
            ListInterface<Tutor> tutorList = TeachingAssignmentManagement.getUniqueTutor(assignedTAList);
            tutorList = sortTutorByNoOfClassAssigned(tutorList, assignedTAList);
            Iterator<Tutor> tutorIT = tutorList.getIterator();
            ListInterface<Tutor> filteredTutorList = new ArrayList<>();
            if (assignedTAList.getNumberOfEntries() != 0) {
                int selection = teachingAssignmentUI.getFilterrTutorOption();
                if (selection != 0) {
                    String displayStringForCourseSelection = "";
                    int filterInput = -1;
                    switch (selection) {
                        case 1:
                            filterInput = teachingAssignmentUI.getFilterInput(1);
                            while (tutorIT.hasNext()) {
                                Tutor tutor = tutorIT.next();
                                if (TeachingAssignmentManagement.getNoOfClassAssigned(assignedTAList, tutor) > filterInput) {
                                    filteredTutorList.add(tutor);
                                }
                            }
                            if (filteredTutorList.getNumberOfEntries() == 0) {
                                System.out.printf("There is no tutor has been assigned more than %d classes\n", filterInput);

                            }else{
                            displayStringForCourseSelection = String.format("Tutors who have been assigned more than %d classes\n", filterInput);}
                            break;
                        case 2:
                            filterInput = teachingAssignmentUI.getFilterInput(2);
                            while (tutorIT.hasNext()) {
                                Tutor tutor = tutorIT.next();
                                if ((TeachingAssignmentManagement.getNoOfClassAssigned(assignedTAList, tutor) == filterInput)) {
                                    filteredTutorList.add(tutor);
                                }
                            }
                            if (filteredTutorList.getNumberOfEntries() == 0) {
                                System.out.printf("There is no tutor has been assigned %d classes\n", filterInput);

                            } else {
                                displayStringForCourseSelection = String.format("Tutors who have been assigned %d classes\n", filterInput);
                            }
                            break;
                        case 3:
                            filterInput = teachingAssignmentUI.getFilterInput(3);
                            while (tutorIT.hasNext()) {
                                Tutor tutor = tutorIT.next();
                                if (TeachingAssignmentManagement.getNoOfClassAssigned(assignedTAList, tutor) < filterInput) {
                                    filteredTutorList.add(tutor);
                                }
                            }
                            if (filteredTutorList.getNumberOfEntries() == 0) {
                                System.out.printf("There is no tutor has been assigned more than %d classes\n", filterInput);

                            }else{
                            displayStringForCourseSelection = String.format("Tutors who have been assigned less than %d classes\n", filterInput);
                            }
                            break;
                        default:

                    }

                    if (filteredTutorList.getNumberOfEntries() != 0) {

                        while (true) {
                            Tutor selectedTutor = teachingAssignmentUI.getTutorSelection(filteredTutorList, assignedTAList, displayStringForCourseSelection);
                            if (selectedTutor == null) {
                                break;
                            }
                            MessageUI.displayInvalidChoiceMessage();
                            MessageUI.pause();
                        }

                    } 

                }
                while (true) {
                    System.out.printf("\nContinue select programme? (Y/N) : ");
                    cont = scanner.next().toUpperCase().charAt(0);
                    if (cont == 'Y' || cont == 'N') {
                        break;
                    }
                    System.out.printf("\nPlease enter a valid selection\n");
                    MessageUI.pause();
                }

            } else {
                System.out.printf("\nNo course in batch %s are Assigned\n", currentBatch);
                MessageUI.pause();
                cont = 'N';
            }

        } while (cont == 'Y');

    }

    public static ListInterface<Tutor> getQualifiedTutorList(ListInterface<Tutor> tutorList, ListInterface<String> domainList, ListInterface<TeachingAssignment> taList) {
        Iterator tutorIT = tutorList.getIterator();
        ListInterface<Tutor> qualifiedTutorList = new ArrayList<Tutor>();

        while (tutorIT.hasNext()) {
            Tutor currentTutor = (Tutor) tutorIT.next();
            Iterator domainIT = domainList.getIterator();
            Boolean qualifiedTutor = true;
            if (getNoOfClassAssigned(taList, currentTutor) < 15) {
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
        return sortTutorByName(qualifiedTutorList);

    }

    public static ListInterface<TeachingAssignment> getUnAssignedTeachingList(ListInterface<TeachingAssignment> taList) {
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

    public static ListInterface<TeachingAssignment> getAssignedTeachingList(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getTutor().getTutorID() != null) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public static ListInterface<TeachingAssignment> getCurrentBatchTeachingList(String currentBatch, ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getTutorialGroup().getBatch().equals(currentBatch)) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public static int getNoOfClassAssigned(ListInterface<TeachingAssignment> taList, Tutor tutor) {
        Iterator<TeachingAssignment> taListIT = taList.getIterator();
        int numberOfClass = 0;

        while (taListIT.hasNext()) {
            TeachingAssignment ta = taListIT.next();
            if (ta.getTutor().equals(tutor)) {
                numberOfClass++;
            }

        }
        return numberOfClass;

    }

    public static int getNoOfTutGrp(ListInterface<TeachingAssignment> taList, String programmeID) {
        Iterator<TeachingAssignment> taListIT = taList.getIterator();
        int numberOfClass = 0;

        while (taListIT.hasNext()) {
            TeachingAssignment ta = taListIT.next();
            if (ta.getTutorialGroup().getProgramme().equals(programmeID)) {
                numberOfClass++;
            }

        }
        return numberOfClass;

    }

    public static ListInterface<TeachingAssignment> filterByTutor(ListInterface<TeachingAssignment> taList, Tutor tutor) {
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

    public static ListInterface<TeachingAssignment> filterByProgramme(ListInterface<TeachingAssignment> taList, String programme) {
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

    public static ListInterface<TeachingAssignment> filterByTutorialGrp(ListInterface<TeachingAssignment> taList, TutorialGroup tutGrp) {
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

    public static ListInterface<TeachingAssignment> filterByCourse(ListInterface<TeachingAssignment> taList, Course course) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<TeachingAssignment>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getCourse().getCourseCode().equals(course.getCourseCode())) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public static ListInterface<TeachingAssignment> filterByProgrammeBatch(ListInterface<TeachingAssignment> taList, String programme, String batch) {
        Iterator taIT = taList.getIterator();
        ListInterface<TeachingAssignment> resultList = new ArrayList<TeachingAssignment>();
        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (currentTA.getTutorialGroup().getProgramme().equals(programme) && currentTA.getTutorialGroup().getBatch().equals(batch)) {
                resultList.add(currentTA);
            }
        }
        return resultList;
    }

    public static ListInterface<Tutor> getUniqueTutor(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<Tutor> tutorList = new ArrayList<>();

        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (!tutorList.contains(currentTA.getTutor())) {
                tutorList.add(currentTA.getTutor());
            }
        }

        return sortTutorByName(tutorList);
    }

    public static ListInterface<String> getUniqueProgramme(ListInterface<TeachingAssignment> taList) {
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

    public static ListInterface<Course> getUniqueCourse(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<Course> courseList = new ArrayList<Course>();

        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (!courseList.contains(currentTA.getCourse())) {
                courseList.add(currentTA.getCourse());
            }
        }
        return sortCourseByName(courseList);
    }

    public static ListInterface<TutorialGroup> getUniqueTutGrp(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<TutorialGroup> tutGrpList = new ArrayList<TutorialGroup>();

        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (!tutGrpList.contains(currentTA.getTutorialGroup())) {
                tutGrpList.add(currentTA.getTutorialGroup());
            }
        }
        return sortTutGrpByProgramme(tutGrpList);
    }

    public static ListInterface<String> getUniqueBatch(ListInterface<TeachingAssignment> taList) {
        Iterator taIT = taList.getIterator();
        ListInterface<String> batchList = new ArrayList<>();

        while (taIT.hasNext()) {
            TeachingAssignment currentTA = (TeachingAssignment) taIT.next();
            if (!batchList.contains(currentTA.getTutorialGroup().getBatch())) {
                batchList.add(currentTA.getTutorialGroup().getBatch());
            }
        }
        return batchList;

    }

    public static ListInterface<TeachingAssignment> convertToArrayList(ListInterface<TeachingAssignment> taList) {
        ListInterface<TeachingAssignment> presort = new ArrayList<>();
        Iterator<TeachingAssignment> it = taList.getIterator();
        while (it.hasNext()) {
            TeachingAssignment ta = it.next();
            presort.add(ta);
        }
        return presort;
    }

    public static ListInterface<Course> sortCourseByName(ListInterface<Course> courseList) {
        for (int i = 1; i < courseList.getNumberOfEntries(); i++) {
            int j = i;
            while (j > 0 && courseList.getEntry(j).getCourseName().compareTo(courseList.getEntry(j + 1).getCourseName()) > 0) {
                Course temp = courseList.getEntry(j);
                courseList.replace(j, courseList.getEntry(j + 1));
                courseList.replace(j + 1, temp);
                j--;
            }
        }

        return courseList;

    }

    public static ListInterface<Tutor> sortTutorByName(ListInterface<Tutor> tutorList) {
        for (int i = 1; i < tutorList.getNumberOfEntries(); i++) {
            int j = i;
            while (j > 0 && tutorList.getEntry(j).getName().compareTo(tutorList.getEntry(j + 1).getName()) > 0) {
                Tutor temp = tutorList.getEntry(j);
                tutorList.replace(j, tutorList.getEntry(j + 1));
                tutorList.replace(j + 1, temp);
                j--;
            }
        }
        return tutorList;
    }

    public static ListInterface<Tutor> sortTutorByNoOfClassAssigned(ListInterface<Tutor> tutorList, ListInterface<TeachingAssignment> taList) {
        for (int i = 1; i < tutorList.getNumberOfEntries(); i++) {
            int j = i;
            while (j > 0 && getNoOfClassAssigned(taList, tutorList.getEntry(j)) < getNoOfClassAssigned(taList, tutorList.getEntry(j + 1))) {
                Tutor temp = tutorList.getEntry(j);
                tutorList.replace(j, tutorList.getEntry(j + 1));
                tutorList.replace(j + 1, temp);
                j--;
            }
        }
        return tutorList;
    }

    public static ListInterface<Programme> sortProgrammeByName(ListInterface<Programme> programmeList) {
        for (int i = 1; i < programmeList.getNumberOfEntries(); i++) {
            int j = i;
            while (j > 0 && programmeList.getEntry(j).getName().compareTo(programmeList.getEntry(j + 1).getName()) > 0) {
                Programme temp = programmeList.getEntry(j);
                programmeList.replace(j, programmeList.getEntry(j + 1));
                programmeList.replace(j + 1, temp);
                j--;
            }
        }
        return programmeList;
    }

    public static ListInterface<TeachingAssignment> sortTAByProgramme(ListInterface<TeachingAssignment> taList) {
        for (int i = 1; i < taList.getNumberOfEntries(); i++) {
            int j = i;
            while (j > 0 && taList.getEntry(j).getTutorialGroup().getProgramme().compareTo(taList.getEntry(j + 1).getTutorialGroup().getProgramme()) > 0) {
                TeachingAssignment temp = taList.getEntry(j);
                taList.replace(j, taList.getEntry(j + 1));
                taList.replace(j + 1, temp);
                j--;
            }
        }

        return taList;
    }

    public static ListInterface<TutorialGroup> sortTutGrpByProgramme(ListInterface<TutorialGroup> tutGrpList) {
        for (int i = 1; i < tutGrpList.getNumberOfEntries(); i++) {
            for (int j = i + 1; j <= tutGrpList.getNumberOfEntries(); j++) {
                if (tutGrpList.getEntry(i).getId().compareTo(tutGrpList.getEntry(j).getId()) > 0) {
                    TutorialGroup tutGrp = tutGrpList.getEntry(i);
                    tutGrpList.replace(i, tutGrpList.getEntry(j));
                    tutGrpList.replace(j, tutGrp);
                }

            }
        }
        for (int i = 1; i < tutGrpList.getNumberOfEntries(); i++) {
            int j = i;
            while (j > 0 && tutGrpList.getEntry(j).getId().compareTo(tutGrpList.getEntry(j + 1).getId()) > 0) {
                TutorialGroup temp = tutGrpList.getEntry(j);
                tutGrpList.replace(j, tutGrpList.getEntry(j + 1));
                tutGrpList.replace(j + 1, temp);
                j--;
            }
        }

        return tutGrpList;

    }

    public void recordCreate(String currentBatch) {

        TutorialGroupDAO tutGrpDAO = new TutorialGroupDAO();
        CourseDAO courseDAO = new CourseDAO();
        TeachingAssignmentDAO taDAO = new TeachingAssignmentDAO();
        TutorDAO tutDAO = new TutorDAO();
        ListInterface<TeachingAssignment> taList = new CircularDoublyLinkedList();
        ListInterface<TutorialGroup> tutGrpList = tutGrpDAO.retrieveFromFile();
        ListInterface<Course> courseList = courseDAO.retrieveFromFile();
        ListInterface<Tutor> tutorList = tutDAO.retrieveFromFile();
        Iterator<TutorialGroup> tutGrpIT = tutGrpList.getIterator();

        while (tutGrpIT.hasNext()) {
            TutorialGroup tutGrp = tutGrpIT.next();
            Iterator<Course> courseIT = courseList.getIterator();
            while (courseIT.hasNext()) {
                Course course = courseIT.next();
                Iterator<String> programmeIT = course.getProgrammes().getIterator();
                while (programmeIT.hasNext()) {
                    String programmeId = programmeIT.next();
                    if (programmeId.equals(tutGrp.getProgramme())) {
                        taList.add(new TeachingAssignment(tutGrp, course, new Tutor()));
                    }
                }
            }
        }
        ListInterface<TeachingAssignment> ta2List = TeachingAssignmentManagement.convertToArrayList(taList);
        if (this.teachingAssignmentList.isEmpty()) {
            ListInterface<String> batchList = TeachingAssignmentManagement.getUniqueBatch(taList);
            Iterator<String> batchIT = batchList.getIterator();
            while (batchIT.hasNext()) {
                String batch = batchIT.next();
                ListInterface<TeachingAssignment> taListFilterbyBatch = TeachingAssignmentManagement.getCurrentBatchTeachingList(batch, taList);
                Iterator<TeachingAssignment> taListFilterbyBatchIT = taListFilterbyBatch.getIterator();
                if (!batch.equals(currentBatch)) {
                    while (taListFilterbyBatchIT.hasNext()) {
                        TeachingAssignment ta = taListFilterbyBatchIT.next();
                        ListInterface<Tutor> qualifiedTutorList = TeachingAssignmentManagement.getQualifiedTutorList(tutorList, ta.getCourse().getRequiredDomainKnowledge(), taListFilterbyBatch);
                        if (qualifiedTutorList.getNumberOfEntries() != 0) {
                            int selection = (int) Math.floor(Math.random() * (qualifiedTutorList.getNumberOfEntries() - 1 + 1) + 1);
                            ta.setTutor(qualifiedTutorList.getEntry(selection));
                        }
                    }
                } else {
                    int recordCount = taListFilterbyBatch.getNumberOfEntries() / 2;
                    while (taListFilterbyBatchIT.hasNext()) {
                        TeachingAssignment ta = taListFilterbyBatchIT.next();
                        ListInterface<Tutor> qualifiedTutorList = TeachingAssignmentManagement.getQualifiedTutorList(tutorList, ta.getCourse().getRequiredDomainKnowledge(), taListFilterbyBatch);
                        if (qualifiedTutorList.getNumberOfEntries() != 0) {
                            int selection = (int) Math.floor(Math.random() * (qualifiedTutorList.getNumberOfEntries() - 1 + 1) + 1);
                            ta.setTutor(qualifiedTutorList.getEntry(selection));
                            recordCount--;
                            if (recordCount == 0) {
                                break;
                            }
                        }
                    }

                }
            }
            this.teachingAssignmentList = taList;

        } else {
            Iterator<TeachingAssignment> taListIT = taList.getIterator();
            Iterator<TeachingAssignment> teachingAssignmentListIT = taList.getIterator();
            while (taListIT.hasNext()) {
                TeachingAssignment ta = taListIT.next();
                if (!teachingAssignmentList.contains(ta)) {
                    teachingAssignmentList.add(ta);
                }
            }
            while (teachingAssignmentListIT.hasNext()) {
                TeachingAssignment ta = teachingAssignmentListIT.next();
                if (!taList.contains(ta)) {
                    teachingAssignmentList.remove(ta);
                    continue;
                }
                ta.setCourse(courseList.getEntry(ta.getCourse()));
                if (ta.getTutor().getTutorID() != null) {
                    ta.setTutor(tutorList.getEntry(ta.getTutor()));
                }
            }
        }

        ListInterface<TeachingAssignment> ta1List = TeachingAssignmentManagement.convertToArrayList(teachingAssignmentList);
        taDAO.saveToFile(teachingAssignmentList);
    }

    public static void main(String[] args) {
        TeachingAssignmentManagement teachingAssignmentList = new TeachingAssignmentManagement();
        String currentBatch = "202301";
        teachingAssignmentList.recordCreate(currentBatch);

        int selection = -1;
        do {
            selection = teachingAssignmentList.teachingAssignmentUI.getMenuChoice();
            switch (selection) {
                case 1:
                    teachingAssignmentList.assignTutor(currentBatch);
                    break;
                case 2:
                    teachingAssignmentList.modifyTutorAssignment(currentBatch);
                    break;
                case 3:
                    teachingAssignmentList.searchTutorAssignment(currentBatch);
                    break;
                case 4:
                    teachingAssignmentList.listTeachingAssignment(currentBatch);
                    break;
                case 5:
                    teachingAssignmentList.filterTeachingAssignment(currentBatch);
                    break;
                default:
            }
        } while (selection != 0);
    }

}
