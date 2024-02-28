/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

import com.example.university.model.*;
import com.example.university.repository.*;

@Service
public class StudentJpaService implements StudentRepository {
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private ProfessorJpaRepository professorJpaRepository;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Override
    public ArrayList<Student> getStudents() {
        return (ArrayList<Student>) studentJpaRepository.findAll();
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            return studentJpaRepository.findById(studentId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student) {
        List<Integer> courseIds = new ArrayList<>();
        for (Course course : student.getCourses()) {
            courseIds.add(course.getCourseId());
        }
        List<Course> courses = courseJpaRepository.findAllById(courseIds);
        if (courseIds.size() != courses.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        student.setCourses(courses);

        return studentJpaRepository.save(student);
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        try {
            Student existStudent = studentJpaRepository.findById(studentId).get();
            if (student.getStudentName() != null) {
                existStudent.setStudentName(student.getStudentName());
            }
            if (student.getEmail() != null) {
                existStudent.setEmail(student.getEmail());
            }
            if (student.getCourses() != null) {

                List<Integer> courseIds = new ArrayList<>();
                for (Course course : student.getCourses()) {
                    courseIds.add(course.getCourseId());
                }
                List<Course> courses = courseJpaRepository.findAllById(courseIds);

                if(courses.size()!=courseIds.size()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                existStudent.setCourses(courses);

            }
            return studentJpaRepository.save(existStudent);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        try {
            
            studentJpaRepository.deleteById(studentId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Course> getStudentCourses(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            return student.getCourses();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}