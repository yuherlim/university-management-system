/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Lim Yu Her
 */
public class TutorialGroup implements Serializable {

    private String id;
    private String group;
    private String programme;
    private String batch;
//    private ListInterface<Student> studentList;

    public TutorialGroup() {
    }

    public TutorialGroup(String id) {
        this.id = id;
    }

    public TutorialGroup(String id, String group, String programme, String batch) {
        this.id = id;
        this.group = group;
        this.programme = programme;
        this.batch = batch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final TutorialGroup other = (TutorialGroup) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "TutorialGroup{" + "id=" + id + ", group=" + group + ", programme=" + programme + ", batch=" + batch + '}';
    }
}
