/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.*;
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
    private int duration;
    private double totalFee;
    private ArrayList<String> courses;

    public Programme() {
    }

    public Programme(String code) {
        this.code = code;
    }

    public Programme(String code, String name, String faculty, String programmeType, String description, int duration, double totalFee) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
        this.programmeType = programmeType;
        this.description = description;
        this.duration = duration;
        this.totalFee = totalFee;
    }
    
    //Used only when initializing data.
    public Programme(String code, String name, String faculty, String programmeType, String description, int duration, double totalFee, ArrayList<String> courses) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
        this.programmeType = programmeType;
        this.description = description;
        this.duration = duration;
        this.totalFee = totalFee;
        this.courses = courses;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
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
        return String.format("%-4s %-80s %-60s %-10s %15.2f", code, name, faculty, programmeType, totalFee);
    }

    
    
    
}
