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
    Scanner scan = new Scanner(System.in);

    public CourseManagement() {
        courseList = courseDAO.retrieveFromFile();
    }

    public void addNewCourse() {
        String code, name;
        int creditHR, programmeSelection;
        double feePerCH;
        courseUI.displayAddCourseMsg();
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
        ListInterface<String> inputProgList = courseUI.programmeInputList(programmes);
              
        String[] temp = {"temp"};
        courseList.add(new Course(code, name, inputDomains, creditHR, feePerCH, (ArrayList<String>) inputProgList, temp));       
        courseDAO.saveToFile(courseList);
    }
     
    public void removeCourse(){
        StackInterface<Integer> undoStackIndex = new ArrayStack();
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
                        undoStackIndex.push(entryAt);
                        undoStackCourse.push(courseList.getEntry(entryAt));
                        courseList.remove(entryAt);             
                    }
                    break;
                    
                case 2:    
                    ListInterface<Course> temp = courseUI.filterCourseByProgramme(courseList);
                    course = courseUI.deleteFilteredListByNo(temp);
                    undoStackIndex.push(((CircularDoublyLinkedList)(courseList)).locatePosition(course));
                    undoStackCourse.push(course);
                    courseList.remove(course);
                    break;
                    
                case 3:
                    course = courseUI.searchCourseByCode(courseList);
                    undoStackIndex.push(((CircularDoublyLinkedList)(courseList)).locatePosition(course));
                    undoStackCourse.push(course);
                    courseList.remove(course);
                    break;
                
                case 4:
                    if(!undoStackCourse.isEmpty()){
                        char undo = courseUI.undo(undoStackCourse);
                        if(undo == 'Y')
                            courseList.add(undoStackIndex.pop(), undoStackCourse.pop());                     
                    }else{
                        System.out.println("Nothing to undo");
                        MessageUI.pause();
                    }
                    
                    
                case 0:                  
                    MessageUI.displayExit();
                    undoStackIndex.clear();
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
    
    public void listAllCourse(){
        courseUI.displayAllCourse(courseList);
    }

    public static void main(String[] args) {
        CourseManagement test = new CourseManagement();
       
//        test.addNewCourse();  
        test.removeCourse();
//        test.listAllCourse();
    }  
}
