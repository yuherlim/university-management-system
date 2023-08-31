/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import adt.ArrayList;
import java.io.Serializable;


/**
 *
 * @author SIA YEONG SHENG
 */
public class Course implements Serializable{
    private String courseCode;
    private String courseName;
    private String[] requiredDomainKnowledge;
    private int creditHR;
    private double feePCH;
    private double coureseTotalFee;
    private ArrayList<String> programmes;
    private String[] teachingList;

    public Course(String courseCode, String courseName, String[] requiredDomainKnowledge,int creditHR, double feePCH, ArrayList<String> programmes, String[] teachingList) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.requiredDomainKnowledge = requiredDomainKnowledge;
        this.creditHR = creditHR;
        this.feePCH = feePCH;
        this.coureseTotalFee = creditHR * feePCH;
        this.programmes = programmes;
        this.teachingList = teachingList;
    }
    
    public Course(String courseCode){
        this.courseCode = courseCode;
    }

    public Course() {
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditHR() {
        return creditHR;
    }

    public void setCreditHR(int creditHR) {
        this.creditHR = creditHR;
    }

    public double getFeePCH() {
        return feePCH;
    }

    public void setFeePCH(double feePCH) {
        this.feePCH = feePCH;
    }

    public double getCoureseTotalFee() {
        return coureseTotalFee;
    }

    public void setCoureseTotalFee(double coureseTotalFee) {
        this.coureseTotalFee = coureseTotalFee;
    }

    
    
     public String[] getRequiredDomainKnowledge() {
        return requiredDomainKnowledge;
    }

    public void setRequiredDomainKnowledge(String[] requiredDomainKnowledge) {
        this.requiredDomainKnowledge = requiredDomainKnowledge;
    }

    

    public String[] getTeachingList() {
        return teachingList;
    }

    public void setTeachingList(String[] teachingList) {
        this.teachingList = teachingList;
    }

    public ArrayList<String> getProgrammes() {
        return programmes;
    }

    public void setProgrammes(ArrayList<String> programmes) {
        this.programmes = programmes;
    }
    
    
    
    @Override
    public String toString() {
        String domains = "";
        for(String r: requiredDomainKnowledge)
            domains += r + ",";
        
        domains +="\b";
        
        String programmeList = "";
        for(int i=1; i<=programmes.getNumberOfEntries();i++){
            programmeList += programmes.getEntry(i) + ",";
        }
        
        programmeList += "\b";
        
        return "Course: " + courseCode + "\ncourseName: " + courseName + "\nDomains: "+ domains +"\nCredit Hour: " + creditHR + "\nFee Per Credit Hour: " + feePCH + 
                "\nTotal Fee: " + coureseTotalFee + "\nTake by Programme: " + programmeList + "\nTutor assignment: " + teachingList+ "\n";
       
    }
    
   
    
}
