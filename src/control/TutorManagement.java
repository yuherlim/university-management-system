/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * @author ASUS
 */
public class TutorManagement {
    
    private ListInterface<Tutor> tutorList = new CircularDoublyLinkedList<>();
    private TutorDAO tutorDAO = new TutorDAO();
    private TutorManagementUI tutorUI = new TutorManagementUI();
    private MessageUI message = new MessageUI();
    private TutorInputValidator validation = new TutorInputValidator();
    Scanner sc = new Scanner(System.in);
    
    public TutorManagement(){
        tutorList = tutorDAO.retrieveFromFile();
    }
    
    public void addNewTutor(){
        
        String tutorName = tutorUI.inputTutorName();
        char tutorGender = tutorUI.inputTutorGender();
        String tutorIC = tutorUI.inputTutorIC();
        String tutorPhoneNum = tutorUI.inputTutorPhoneNum();
        String tutorEmail = tutorUI.inputTutorEmail();
        
        int educationLevelSelection = tutorUI.inputTutoerEduLevel();
        String educationLevel = "";
        switch(educationLevelSelection){
            case 1:
                educationLevel = "Diploma";
                break;
            case 2:
                educationLevel = "Bachelor's Degree";
                break;
            case 3:
                educationLevel = "Master's Degree";
                break;
            case 4:
                educationLevel = "phD";
                break;   
        }
        
        String[] tutorDomainList = tutorUI.inputTutorDomain();
        
        tutorList.add(new Tutor(tutorName, tutorGender, tutorIC, tutorPhoneNum, tutorEmail, educationLevel, tutorDomainList));
        tutorDAO.saveToFile(tutorList);
            
    }
    
    public void removeTutor(){
        
    }
    
    public void findTutor(){
        
    }
    
    public void modifyTutor(){
        
    }
    
    public void filterTutor(){
        
    }
    
    public void generateTutorReport(){
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    }
    
    
    public static void main(String[] args) {
        TutorManagement test = new TutorManagement();
        TutorManagementUI uitest = new TutorManagementUI();
        //test.addNewTutor();
        
        uitest.displayAllTutors(test.tutorList);
        
        
        
    }
}