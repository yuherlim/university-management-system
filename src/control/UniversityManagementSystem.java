package control;

import adt.ListInterface;
import boundary.UniversityManagementSystemUI;
import dao.CourseDAO;
import dao.CourseInitializer;
import dao.ProgrammeDAO;
import dao.ProgrammeInitializer;
import dao.TutorDAO;
import dao.TutorInitializer;
import dao.TutorialGroupDAO;
import dao.TutorialGroupInitializer;
import entity.Course;
import entity.Programme;
import entity.Tutor;
import entity.TutorialGroup;
import utility.MessageUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Kho Ka Jie, Lim Yu Her, Ong Cheng Leong, Sia Yeong Sheng
 */
public class UniversityManagementSystem {

    public static void recordInitializer() {
        String[] args = null;
        ProgrammeDAO programmeDAO = new ProgrammeDAO();
        CourseDAO courseDAO = new CourseDAO();
        TutorDAO tutorDAO = new TutorDAO();
        TutorialGroupDAO tutorialGroupDao = new TutorialGroupDAO();
        ListInterface<Programme> programList = programmeDAO.retrieveFromFile();
        ListInterface<Course> courseList = courseDAO.retrieveFromFile();
        ListInterface<Tutor> tutorList = tutorDAO.retrieveFromFile();
        ListInterface<TutorialGroup> tutGrpList = tutorialGroupDao.retrieveFromFile();
        if (programList.isEmpty()) {           
            ProgrammeInitializer.main(args);
            System.out.println("Programme Record Loaded");
        }
        
        if (courseList.isEmpty()) {           
            CourseInitializer.main(args);
            System.out.println("Course Record Loaded");
        }
        
        if (tutorList.isEmpty()) {           
            TutorInitializer.main(args);
            System.out.println("Tutor Record Loaded");
        }
        
        if (tutGrpList.isEmpty()) {           
            TutorialGroupInitializer.main(args);
            System.out.println("Tutor Group Record Loaded");
        }
        

    }

    public static void main(String[] args) {
        UniversityManagementSystemUI universityManagementUI = new UniversityManagementSystemUI();
        recordInitializer();
        int selection = -1;
        do {
            selection = universityManagementUI.getMenuChoice();
            switch (selection) {
                case 1:
                    ProgrammeManagement.main(args);
                    break;
                case 2:
                    CourseManagement.main(args);
                    break;
                case 3:
                    TutorManagement.main(args);
                    break;
                case 4:
                    TeachingAssignmentManagement.main(args);
                    break;
                default:
                    MessageUI.displayExit();
                    MessageUI.pause();
                    break;

            }
        } while (selection != 0);
    }

}
