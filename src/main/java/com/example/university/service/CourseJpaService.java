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
import com.sun.xml.bind.annotation.OverrideAnnotationOf;

@Service
public class CourseJpaService implements CourseRepository {
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private ProfessorJpaRepository professorJpaRepository;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Override
    public ArrayList<Course> getCourses() {
        return (ArrayList<Course>) courseJpaRepository.findAll();
    }

    @Override
    public Course getCourseById(int courseId) {
        try {
            return courseJpaRepository.findById(courseId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course addCourse(Course course) {
        int professorId = course.getProfessor().getProfessorId();

        try {
            Professor professor = professorJpaRepository.findById(professorId).get();
            course.setProfessor(professor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Integer> studentIds = new ArrayList<>();
        for (Student student : course.getStudents()) {
            studentIds.add(student.getStudentId());
        }
        List<Student> students = studentJpaRepository.findAllById(studentIds);

        course.setStudents(students);
        for (Student student : students) {
            student.getCourses().add(course);
        }
        Course savedCourse = courseJpaRepository.save(course);

        studentJpaRepository.saveAll(students);
        return savedCourse;
    }

    @Override
    public Course updateCourse(int courseId, Course course) {
        try {
            Course existCourse = courseJpaRepository.findById(courseId).get();
            if (course.getCourseName() != null) {
                existCourse.setCourseName(course.getCourseName());
            }
            if (course.getCredits() != 0) {
                existCourse.setCredits(course.getCredits());
            }
            if (course.getProfessor() != null) {
                int professorId = course.getProfessor().getProfessorId();
                
                Professor professor = professorJpaRepository.findById(professorId).get();
                existCourse.setProfessor(professor);
                
            }
            if (course.getStudents() != null) {
                List<Student> students = existCourse.getStudents();
                for (Student student : students) {
                    student.getCourses().remove(existCourse);
                }
                studentJpaRepository.saveAll(students);
                List<Integer> newStudentIds = new ArrayList<>();
                for (Student student : course.getStudents()) {
                    newStudentIds.add(student.getStudentId());
                }
                List<Student> newStudents = studentJpaRepository.findAllById(newStudentIds);

                existCourse.setStudents(newStudents);
                for (Student student : newStudents) {
                    student.getCourses().add(existCourse);
                }
                studentJpaRepository.saveAll(newStudents);
            }

            return courseJpaRepository.save(existCourse);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCourse(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            List<Student> students = course.getStudents();
            for (Student student : students) {
                student.getCourses().remove(course);
            }
            studentJpaRepository.saveAll(students);
            courseJpaRepository.deleteById(courseId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Student> getCourseStudents(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course.getStudents();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Professor getCourseProfessor(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course.getProfessor();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}