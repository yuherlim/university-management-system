/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.ArrayList;
import java.io.Serializable;
import java.util.Objects;


/**
 *
 * @author ASUS
 */
public class Tutor implements Serializable{
    private String tutorID;
    private String name;
    private char gender;
    private String ic;
    private String phoneNum;
    private String email;
    private String educationLevel;
    private ArrayList<String> domainKnowledgeList;
    
    public Tutor(String id,String name, char gender, String ic, String phoneNum, String email, String educationLevel, ArrayList<String> domainKnowledgeList){
        this.tutorID = id;
        this.name = name;
        this.gender = gender;
        this.ic = ic;
        this.phoneNum = phoneNum;
        this.email = email;
        this.educationLevel = educationLevel;
        this.domainKnowledgeList = domainKnowledgeList;
    }
    
    public Tutor(String tutorID){
        this.tutorID = tutorID;
    }

    public Tutor() {}
    
    public String getTutorID() {
        return tutorID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public ArrayList<String> getDomainKnowledgeList() {
        return domainKnowledgeList;
    }

    public void setDomainKnowledgeList(ArrayList<String> domainKnowledgeList) {
        this.domainKnowledgeList = domainKnowledgeList;
    }

    @Override
    public String toString() {
        return "Tutor{" + "tutorID=" + tutorID + ", name=" + name + ", ic=" + ic + ", gender=" + gender + ", educationLevel=" + educationLevel + ", domainKnowledge=" + domainKnowledgeList + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tutor other = (Tutor) obj;
        return Objects.equals(this.tutorID, other.tutorID);
    }
    
    
    
}
