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
    private CourseInputValidator validator = new CourseInputValidator();
    private String[] programmes = {"RSW", "RDS", "RSD", "ABC", "DEF", "GHI"};
    private String[] domainKnowledges = {"Accounting","Add.Math","Biology","Chemistry","Physics"};
    Scanner scan = new Scanner(System.in);

    public CourseManagement() {
        courseList = courseDAO.retrieveFromFile();
    }

    public void addNewCourse() {
        String code, name;
        int creditHR, programmeSelection;
        double feePerCH;
        courseUI.displayAddCourseMsg();
        code = courseUI.inputCourseCode(courseList);
        name = courseUI.inputCourseName();

        ListInterface<String> inputDomains = courseUI.inputDomainLoop(domainKnowledges);        
               
        creditHR = courseUI.inputCreditHour();
        feePerCH = courseUI.inputFeePerCreditHour();
        

        //programme selection to be amended
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        ListInterface<String> inputProgList = courseUI.programmeInputList(programmes);
           
        String[] temp = {"temp"};
        courseList.add(new Course(code, name, (ArrayList<String>)inputDomains, creditHR, feePerCH, (ArrayList<String>)inputProgList, temp));       
        courseDAO.saveToFile(courseList);
    }
     
    public void removeCourse(){
        StackInterface<Integer> undoStackPosition = new ArrayStack();
        StackInterface<Course> undoStackCourse = new ArrayStack();
        Course course;
        int selection;
        do{      
            selection = courseUI.deleteCourseMenuSelection();
            int entryAt;
            
            switch(selection){
                case 1:

                    entryAt = courseUI.deleteByNo(courseList);
                    if(entryAt >= 1  && entryAt <= courseList.getNumberOfEntries()){
                        undoStackPosition.push(entryAt);
                        undoStackCourse.push(courseList.getEntry(entryAt));
                        courseList.remove(entryAt);
                        MessageUI.courseDeleteMsg();
                    }
                    break;
                    
                case 2:    
                    ListInterface<Course> temp = courseUI.filterCourseByProgramme(courseList);
                    course = courseUI.deleteFilteredListByNo(temp);
                    if(course != null){
                        undoStackPosition.push(((CircularDoublyLinkedList)(courseList)).locatePosition(course));
                        undoStackCourse.push(course);
                        courseList.remove(course);
                        MessageUI.courseDeleteMsg();
                    }
                    break;
                    
                case 3:
                    course = courseUI.searchCourseByCode(courseList);
                    if(course != null){
                        undoStackPosition.push(((CircularDoublyLinkedList)(courseList)).locatePosition(course));
                        undoStackCourse.push(course);
                        courseList.remove(course);
                        MessageUI.courseDeleteMsg();
                    }else{
                        System.out.println("No match found");
                    }
                    break;
                
                case 4:
                    if(!undoStackCourse.isEmpty()){
                        char undo = courseUI.undo(undoStackCourse);
                        if(undo == 'Y'){
                            courseList.add(undoStackPosition.pop(), undoStackCourse.pop()); 
                            MessageUI.courseUndoDeleteMsg();
                        }else{
                            System.out.println("exiting undo");
                        }
                    }else{
                        System.out.println("Nothing to undo");
                        MessageUI.pause();
                    }
                    break;
                    
                    
                case 0:                  
                    MessageUI.displayExit();
                    undoStackPosition.clear();
                    undoStackCourse.clear();
                    break;
                
                    
            }         
        }while(selection != 0);
        
        //current available cost
        //option to remove specific course by number
        //option to remove specific course by ID
            //check if the ID does exist
            //return fail to delete / delete success
            //write the update into file
    }

    public void searchCourse(){
        Course targetCourse = courseUI.searchCourseByCode(courseList);   
        if(targetCourse!=null){
            System.out.println(targetCourse);
        }else{
            System.out.println("Invalid Course Code");
        }
    }
    
    public void modifyCourse() {
        courseUI.displayModifyCourseMenuMsg();
        courseUI.displayAllCourse(courseList);
        Course target = courseUI.searchCourseByCode(courseList);
        
        if(target != null){
            int selection = -1;
            do {
                selection = courseUI.getModifyMenuSelection(target);
                switch (selection) {
                    case 1:
                        courseList = courseUI.modifyCourseName(courseList, target);
                        break;

                    case 2:
                        courseList = courseUI.modifyCourseDomainList(courseList, domainKnowledges, target);
                        break;

                    case 3:
                        courseList = courseUI.modifyCourseCH(courseList, target);
                        break;

                    case 4:
                        courseList = courseUI.modifyCourseFeePCH(courseList, target);
                        break;

                    case 5:
                        courseList = courseUI.modifyCourseProgList(courseList, programmes, target);
                        break;

                    case 0:
                        MessageUI.displayExit();
                        MessageUI.savingIntoFile();
                        courseDAO.saveToFile(courseList);
                        break;
                }
            } while (selection != 0);
        }else{
            MessageUI.nonExistCourse();
        }
    }
    
    public void displayCourse(){
        int selection = -1;
        do{
            selection = courseUI.getDisplayCourseMenuSelection();
            switch(selection){
                case 1:
                    courseUI.displayAllCourse(courseList);
                    break;
                case 2:
                    
                    courseUI.displayCourseByProgramme(courseList);
                    break;
                case 3:
                    courseUI.displayACourse(courseList);
                    break;
                    
                case 0:
                    MessageUI.displayExit();
                    break;
                    
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        }while(selection != 0);        
    }
    
  
    public static void main(String[] args) {
        CourseManagement courseManagement = new CourseManagement();
        CourseManagementUI courseManagementUI = new CourseManagementUI();
        int selection = -1;
        do{
            selection = courseManagementUI.getCourseMainMenuChoice();
            switch(selection){
                case 1:
                    courseManagement.addNewCourse();
                    break;
                    
                case 2:
                    courseManagement.modifyCourse();
                    break;
                    
                case 3:
                    courseManagement.removeCourse();
                    break;
                    
                case 4:
                    courseManagement.displayCourse();
                    break;
                    
                case 5:
                    break;
                    
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                    
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        
        }while(selection != 0);
    }  
}