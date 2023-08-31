/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.ListInterface;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Yu
 */
public class Programme implements Serializable {
    private String code;
    private String name;
    private String faculty;
    private String programmeType;
    private String description;
    private String duration;
    private double fee;
    private int totalCreditHour;
    private String status;
    private ListInterface<Course> courseList;

    public Programme() {
    }

    public Programme(String code) {
        this.code = code;
    }

    public Programme(String code, String name, String faculty, String programmeType, String description, String duration, double fee, int totalCreditHour, String status) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
        this.programmeType = programmeType;
        this.description = description;
        this.duration = duration;
        this.fee = fee;
        this.totalCreditHour = totalCreditHour;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProgrammeType() {
        return programmeType;
    }

    public void setProgrammeType(String programmeType) {
        this.programmeType = programmeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getTotalCreditHour() {
        return totalCreditHour;
    }

    public void setTotalCreditHour(int totalCreditHour) {
        this.totalCreditHour = totalCreditHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ListInterface<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ListInterface<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.code);
        return hash;
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
        final Programme other = (Programme) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public String toString() {
        return "Programme{" + "code=" + code + ", name=" + name + ", faculty=" + faculty + ", programmeType=" + programmeType + ", description=" + description + ", duration=" + duration + ", fee=" + fee + ", totalCreditHour=" + totalCreditHour + ", status=" + status + ", courseList=" + courseList + '}';
    }
    
    
}
