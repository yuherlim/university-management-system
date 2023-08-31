/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import adt.ListInterface;
import entity.Course;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author syshe
 */
public class CourseInputValidator {
    Scanner scan = new Scanner(System.in);
    
    public boolean courseCodeCheck(String code, ListInterface<Course> courseList){              
        if(code.length() != 8){
            System.out.println("Invalid length");
            return false;
        }
        if(code.charAt(0) == 'M' && code.charAt(1) == 'P' && code.charAt(2) == 'U'){
            if(code.charAt(3) != '-'){
                System.out.println("Invalid MPU");
                return false;
            }
        }
        else{
            for(int i=0;i<4;i++){
                if(!Character.isAlphabetic(code.charAt(i))){
                    System.out.println("Invalid course code structure");
                    return false;
                }
            }
        }
        for(int i=4; i<code.length();i++){
            if(!Character.isDigit(code.charAt(i))){
                System.out.println("Invalid course code structure");
                return false;
            }
        }
        
        Iterator<Course> it = courseList.getIterator();
        Course target;
        while(it.hasNext()){
            target = it.next();
                
                if(target.getCourseCode().equals(code)){
                    System.out.println("Duplicate course code found. \nCourse code must be unique!");
                   return false;
                }
            }
        
        
        return true;
    }
    
    public boolean checkCreditHourInput(int input){       
        try{
            input = scan.nextInt();
        }catch(Exception e){
            e.printStackTrace();
            scan.nextLine();
            return false;
        }
      
        return true;
    }
    
    public boolean checkFeePCHInput(double input){
        try{
            input = scan.nextDouble();
        }catch(Exception e){
            e.printStackTrace();
            scan.nextLine();
            return false;
        }
        return true;
    }
    
    public boolean checkProgSelection(int input){
        try{
            input = scan.nextInt();
        }catch(InputMismatchException e){
            e.printStackTrace();
            scan.nextLine();
            return false;
        }           
        return true;
    }
    
    public boolean checkDuplicateProg(String[] progList, String programme) {
        //function to check if user input the same programme during adding new course.
        for (String prog : progList) {
            if (prog != null) {
                if (prog.equals(programme)) 
                    return false;                       //if found duplicated programme return false;            
            } else {
                break;
            }

        }
        return true;
    }
        
        public boolean checkExistInList(ListInterface<String> list, String input) {
        //function to check if user input the same programme during adding new course.
       for(int i=1;i<=list.getNumberOfEntries();i++){
            if (list.getEntry(i).equals(input)) 
                    return false;                       //if found duplicated programme return false;            
       } 
        
        return true;
    }
}
