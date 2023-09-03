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

/**
 *
 * @author ong58
 */
public class TeachingAssignmentManagement {

    private ListInterface<TeachingAssignment> teachingAssignmentList = new CircularDoublyLinkedList<>();
    private static TeachingAssignmentDAO teachingAssignmentDAO = new TeachingAssignmentDAO();
    private static TeachingAssignmentManagementUI teachingAssignmentUI = new TeachingAssignmentManagementUI();
    Scanner scan = new Scanner(System.in);

    public TeachingAssignmentManagement() {
        teachingAssignmentList = teachingAssignmentDAO.retrieveFromFile();
    }

    public void assignTutor() 
    {
      TutorDAO tutorDAO = new TutorDAO();
      ListInterface tutorList = tutorDAO.retrieveFromFile();

        int selection = -1;
        do {
            selection = teachingAssignmentUI.getAssigmTutorOption();
            switch (selection) {
                case 1:
                     teachingAssignmentUI.assignByCourse(teachingAssignmentList, tutorList);
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
                        taList.add(new TeachingAssignment(tutGrp, course,new Tutor()));
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