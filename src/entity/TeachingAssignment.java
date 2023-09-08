/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Ong Cheng Leong
 */
public class TeachingAssignment implements Serializable {
        private String teachingAssignmentId;
        private TutorialGroup tutorialGroup;
        private Course course;
        private Tutor tutor;
        

        
    public TeachingAssignment(TutorialGroup tutorialGroup, Course course) {
        this.teachingAssignmentId = tutorialGroup.getId() + "-" + course.getCourseCode();
        this.tutorialGroup = tutorialGroup;
        this.course = course;
    }      
    
    public TeachingAssignment(TutorialGroup tutorialGroup, Course course, Tutor tutor) {
        this.teachingAssignmentId = tutorialGroup.getId() + "-" + course.getCourseCode();
        this.tutorialGroup = tutorialGroup;
        this.course = course;
        this.tutor = tutor;
    }


    public String getTeachingAssignmentId() {
        return teachingAssignmentId;
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }

    public Course getCourse() {
        return course;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTeachingAssignmentId(String teachingAssignmentId) {
        this.teachingAssignmentId = teachingAssignmentId;
    }

    public void setTutorialGroup(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "TeachingAssignment{" + "teachingAssignmentId=" + teachingAssignmentId + ", tutorialGroup=" + tutorialGroup.getId()+ ", course=" + course.getCourseCode() +'}';
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
        final TeachingAssignment other = (TeachingAssignment) obj;
        return Objects.equals(this.teachingAssignmentId, other.teachingAssignmentId);
    }         
                     
       
    
}
