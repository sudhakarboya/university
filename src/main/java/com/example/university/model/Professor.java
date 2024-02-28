/*
 *
 * You can use the following import statements
 * 
 * import javax.persistence.*;
 * 
 */

// Write your code here
package com.example.university.model;

import javax.persistence.*;
import com.example.university.model.*;
import java.util.*;

@Entity
@Table(name = "professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int professorId;

    @Column(name = "name")
    private String professorName;

    @Column(name = "department")
    private String department;

    public Professor() {
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

}