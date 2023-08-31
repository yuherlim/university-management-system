/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import adt.CircularDoublyLinkedList;
import adt.ListInterface;
import boundary.TeachingAssignmentManagementUI;
import dao.TeachingAssignmentDAO;
import entity.Course;
import entity.TeachingAssignment;
import entity.Tutor;
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

    public void assignTutor(){
        
        int selection = -1;
       do {
            selection = teachingAssignmentUI.getMenuChoice();
            switch (selection) {
                case 1:
                    assignByCourse();
                    break;
                case 2:
                    assignBySlot();
                    break;
                default:
            }
        } while (selection != 0);       
    }
    
    public void assignByCourse(){
        
    
    
    }
    
    public void assignBySlot(){
    
    
    
    }
    
    public void Tutgrp(){}

    public void modifyTutor() {
    }

    public void replaceTutor(Tutor tutorReplace) {
    }

    public void replaceTutor() {
    }

    public void findTeachingAssignemt() {
    }

    public ArrayList<TeachingAssignment> searchTeachingAssignmentByTutor(Tutor tutorSearch) {
        return null;
    }

    public ArrayList<TeachingAssignment> searchTeachingAssignmentByCourse(Course courseSearch) {
        return null;
    }

    public void ListTeachingAssignment() {
    }

    public void generateTeachingAssignmentReport() {
    }

    public static void main(String[] args) {
        TeachingAssignmentManagement teachingAssignmentList = new TeachingAssignmentManagement();
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
                    teachingAssignmentList.findTeachingAssignemt();
                    break;
                case 4:
                    teachingAssignmentList.ListTeachingAssignment();
                    break;
                case 5:
                    teachingAssignmentList.generateTeachingAssignmentReport();
                    break;
                default:
            }
        } while (selection != 0);

    }

}
