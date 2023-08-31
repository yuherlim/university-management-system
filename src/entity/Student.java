/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author
 */
public class Student implements Serializable{
    private String studentID;
    private String name;
    private char gender;
    private String ic;
    private String phoneNum;
    private String email;
    private String tutorialGroup;

    public Student(String studentID, String name, char gender, String ic, String phoneNum, String email, String tutorialGroup) {
        this.studentID = studentID;
        this.name = name;
        this.gender = gender;
        this.ic = ic;
        this.phoneNum = phoneNum;
        this.email = email;
        this.tutorialGroup = tutorialGroup;
    }

    public Student(String studentID) {
        this.studentID = studentID;
    }

    public Student() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
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

    public String getTutorialGroup() {
        return tutorialGroup;
    }

    public void setTutorialGroup(String tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    @Override
    public String toString() {
        return "Student{" + "studentID=" + studentID + ", name=" + name + ", gender=" + gender + ", ic=" + ic + ", phoneNum=" + phoneNum + ", email=" + email + ", tutorialGroup=" + tutorialGroup + '}';
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
        final Student other = (Student) obj;
        return Objects.equals(this.studentID, other.studentID);
    }
    
    
    
    
}

