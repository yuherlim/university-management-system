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
    private ArrayList<String> requiredDomainKnowledge;
    private int creditHR;
    private double feePCH;
    private double coureseTotalFee;
    private ArrayList<String> programmes;

    
    public Course(String courseCode, String courseName, ArrayList<String> requiredDomainKnowledge,int creditHR, double feePCH, ArrayList<String> programmes) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.requiredDomainKnowledge = requiredDomainKnowledge;
        this.creditHR = creditHR;
        this.feePCH = feePCH;
        this.coureseTotalFee = creditHR * feePCH;
        this.programmes = programmes;
    }
    
    public Course(String courseCode){
        this.courseCode = courseCode;
    }

    public Course() {
    }

    public String getCourseCode() {
        return courseCode;
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
        this.coureseTotalFee = this.feePCH * this.creditHR;
    }

    public double getFeePCH() {
        return feePCH;
    }

    public void setFeePCH(double feePCH) {
        this.feePCH = feePCH;
        this.coureseTotalFee = this.feePCH * this.creditHR;
    }

    public double getCoureseTotalFee() {
        return coureseTotalFee;
    }    
    
     public ArrayList<String> getRequiredDomainKnowledge() {
        return requiredDomainKnowledge;
    }

    public void setRequiredDomainKnowledge(ArrayList<String> requiredDomainKnowledge) {
        this.requiredDomainKnowledge = requiredDomainKnowledge;
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
        for(int i=1;i<requiredDomainKnowledge.getNumberOfEntries();i++){
            domains += requiredDomainKnowledge.getEntry(i) + ",";
        }
        
        domains +="\b";
        
        String programmeList = "";
        for(int i=1; i<=programmes.getNumberOfEntries();i++){
            programmeList += programmes.getEntry(i) + ",";
        }
        
        programmeList += "\b";
        
        return "Course: " + courseCode + "\ncourseName: " + courseName + "\nDomains: "+ domains +"\nCredit Hour: " + creditHR + "\nFee Per Credit Hour: " + feePCH + 
                "\nTotal Fee: " + coureseTotalFee + "\nTake by Programme: " + programmeList + "\n";
       
    }
    
   
    
}
