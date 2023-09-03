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

    public void assignTutor() {
        ProgrammeDAO programmeDAO = new ProgrammeDAO();
        TutorDAO tutorDAO = new TutorDAO();
        ListInterface tutorList = tutorDAO.retrieveFromFile();
        ListInterface programmeList = programmeDAO.retrieveFromFile();

        int selection = -1;
        do {
            selection = teachingAssignmentUI.getAssigmTutorOption();
            switch (selection) {
                case 1:
                    assignByCourse(teachingAssignmentList, tutorList);
                    break;
                case 2:
                    break;
                default:
            }
        } while (selection != 0);
    }

    public void modifyTutor() {
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

    public void findTeachingAssignment() {
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

    public void listTeachingAssignment() {
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

    public void assignByCourse(ListInterface<TeachingAssignment> taList, ListInterface<Tutor> tutorList) {
        ListInterface<TeachingAssignment> unAssignedTAList = this.getUnAssignedTeachingList(taList);
        int assignByCourseSelection = -1;
        Character cont = 'Y';
        do {
            if (!unAssignedTAList.isEmpty()) {
                ListInterface<Course> courseCodeList = this.getUniqueCourse(unAssignedTAList);
                Course selectedCourse = teachingAssignmentUI.getCourseSelection(courseCodeList);
                if (selectedCourse != null) {
                    do {
                        ListInterface<Tutor> qualifiedTutorList = this.getQualifiedTutorList(tutorList, selectedCourse.getRequiredDomainKnowledge(), taList);
                        if (qualifiedTutorList.getNumberOfEntries() == 0) {
                            System.out.printf("\nNo available Tutor");
                            break;
                        } else {

                            Tutor selectedTutor = teachingAssignmentUI.getQualifiedTutorSelection(qualifiedTutorList, taList, selectedCourse);
                            if (selectedTutor != null) {
                                ListInterface<TeachingAssignment> selectedCoursetaList = this.filterByCourse(unAssignedTAList, selectedCourse);
                                ListInterface<TeachingAssignment> taListSortedByProgramme = this.sortTAByProgramme(selectedCoursetaList);
                                int noOfClassAssigned = this.getNoOfClassAssigned(taList, selectedTutor);
                                do {
                                    TeachingAssignment selectedTA = teachingAssignmentUI.getTASelection(taListSortedByProgramme, selectedCourse, selectedTutor, 0);
                                    if (selectedTA != null) {
                                        this.teachingAssignmentList.replace(selectedTA, new TeachingAssignment(selectedTA.getTutorialGroup(), selectedTA.getCourse(), selectedTutor));
                                        unAssignedTAList.remove(selectedTA);
                                        taListSortedByProgramme.remove(selectedTA);
                                        System.out.printf("\nTutor assigned succesful\n");
                                        MessageUI.pause();
                                    }

                                    while (true) {
                                        System.out.printf("\nContinue select Tutorial Group? (Y/N) : ");
                                        cont = scanner.next().toUpperCase().charAt(0);
                                        if (cont == 'Y' || cont == 'N') {
                                            break;
                                        }
                                        System.out.printf("\nPlease enter a valid selection\n");
                                        MessageUI.pause();
                                    }
                                } while (cont == 'Y');
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

            } else {
                System.out.printf("\nAll course are Assigned\n");
                MessageUI.pause();
                cont = 'N';
            }
        } while (cont == 'Y');
        MessageUI.displayExit();
        MessageUI.pause();
        
        teachingAssignmentDAO.saveToFile(taList);

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
            if (currentTA.getCourse().equals(course)) {
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

    public static ListInterface<Tutor> sortTutorByName(ListInterface<Tutor> tutorList) {
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

    public static ListInterface<TeachingAssignment> sortTAByProgramme(ListInterface<TeachingAssignment> taList) {
        for (int i = 1; i < taList.getNumberOfEntries(); i++) {
            for (int j = i + 1; j <= taList.getNumberOfEntries(); j++) {
                if (taList.getEntry(i).getTutorialGroup().getProgramme().compareTo(taList.getEntry(j).getTutorialGroup().getProgramme()) > 0 || (taList.getEntry(i).getTutorialGroup().getProgramme().equals(taList.getEntry(j).getTutorialGroup().getProgramme()) == true && taList.getEntry(i).getTutorialGroup().getId().compareTo(taList.getEntry(j).getTutorialGroup().getId()) > 0)) {
                    TeachingAssignment temp = taList.getEntry(i);
                    taList.replace(i, taList.getEntry(j));
                    taList.replace(j, temp);
                }

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
        return tutGrpList;

    }

    public void recordCreate() {

        TutorialGroupDAO tutGrpDAO = new TutorialGroupDAO();
        CourseDAO courseDAO = new CourseDAO();
        TeachingAssignmentDAO taDAO = new TeachingAssignmentDAO();
        ListInterface taList = new CircularDoublyLinkedList();
        ListInterface tutGrpList = tutGrpDAO.retrieveFromFile();
        ListInterface courseList = courseDAO.retrieveFromFile();
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
        if (this.teachingAssignmentList.isEmpty()) {
            this.teachingAssignmentList = taList;
        } else {
            Iterator<TeachingAssignment> taListIT = taList.getIterator();
            while (taListIT.hasNext()) {
                TeachingAssignment ta = taListIT.next();
                if (!teachingAssignmentList.contains(ta)) {
                    teachingAssignmentList.add(ta);
                }
            }

        }
        taDAO.saveToFile(teachingAssignmentList);
    }

    public static void main(String[] args) {
        TeachingAssignmentManagement teachingAssignmentList = new TeachingAssignmentManagement();
        teachingAssignmentList.recordCreate();

        int selection = -1;
        do {
            selection = teachingAssignmentList.teachingAssignmentUI.getMenuChoice();
            switch (selection) {
                case 1:
                    teachingAssignmentList.assignTutor();
                    break;
                case 2:
                    teachingAssignmentList.modifyTutor();
                    break;
                case 3:
                    teachingAssignmentList.findTeachingAssignment();
                    break;
                case 4:
                    teachingAssignmentList.listTeachingAssignment();
                    break;
                case 5:
                    teachingAssignmentList.generateReport();
                    break;
                default:
            }
        } while (selection != 0);
    }

}
