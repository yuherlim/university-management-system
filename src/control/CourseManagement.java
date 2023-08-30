/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import adt.*;
import boundary.*;
import entity.*;
import dao.*;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author syshe
 */
public class CourseManagement {
    private ListInterface<Course> courseList = new CircularDoublyLinkedList<>();
    private CourseDAO courseDAO = new CourseDAO();
    private CourseManagementUI courseUI = new CourseManagementUI();
    private MessageUI message = new MessageUI();
    private CourseInputValidator validator = new CourseInputValidator();
    Scanner scan = new Scanner(System.in);

    public CourseManagement() {
        courseList = courseDAO.retrieveFromFile();
    }

    public void addNewCourse() {
        String code, name;
        int creditHR, programmeSelection;
        double feePerCH;
        System.out.println("-------------------------------");
        System.out.println("Adding new course\n");
        System.out.println("-------------------------------");
        code = courseUI.inputCourseCode();
        name = courseUI.inputCourseName();
        
        int index = 0;
        String[] domainKnowledges = {"Accounting","Add.Math","Biology","Chemistry","Physics"};
        String[] inputDomains = courseUI.inputDomainLoop(index, domainKnowledges);        
               
        creditHR = courseUI.inputCreditHour();
        feePerCH = courseUI.inputFeePerCreditHour();
        

        //programme selection to be amended
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        String[] programmes = {"RSW", "RDS", "RSD", "ABC", "DEF", "GHI"};
        String[] inputProgList = new String[programmes.length];
        index = 0;
        
        do {
            programmeSelection = courseUI.inputProgramme();
            boolean notDuplicated = true;
            if (programmeSelection >= 1 && programmeSelection <= programmes.length) {
                if (index > 0) {
                    notDuplicated = validator.checkDuplicateProg(inputProgList, programmes[programmeSelection - 1]);
                }

            } else if (programmeSelection == 0) {
                if (index > 0) {
                    System.out.println("Exiting");
                } else {
                    System.out.println("Programme list cannot be empty.");
                    programmeSelection = -1;
                }
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }

            if ((programmeSelection >= 1 && programmeSelection <= programmes.length) && notDuplicated) {
                inputProgList[index] = programmes[programmeSelection - 1];
                ++index;

            }
        } while (programmeSelection != 0);
        
        String[] progList = new String[index];
        for(int i=0; i<progList.length;i++){
            progList[i] = inputProgList[i];
        }
              
        String[] temp = {"temp"};
        courseList.add(new Course(code, name, inputDomains, creditHR, feePerCH, progList, temp));
        
        courseList.add(new Course(code));
        courseDAO.saveToFile(courseList);
    }
     
    public void removeCourse(){
        System.out.println("-------------------------------");
        System.out.println("Removing a course\n");
        System.out.println("-------------------------------");
        
        //current available cost
        //option to remove specific course by number
        //option to remove specific course by ID
            //check if the ID does exist
            //return fail to delete / delete success
            //write the update into file
    }

    public Course searchCourse(ListInterface<Course> courseList){
        Course targetCourse = new Course();
        //search the list , if found return the index
        
        return targetCourse;
    }

    public static void main(String[] args) {
        CourseManagement test = new CourseManagement();
        //CourseManagementUI testUI = new CourseManagementUI();
        test.addNewCourse();  
        //testUI.displayAllCourse(test.courseList);
    }  
}
