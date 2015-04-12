package com.example.vinnie.pixelpicker;

/**
 * Created by Luis on 2/17/2015.
 */
//PLACE HOLDER CLASS IF ANOTHER OBJECT OR TABLE IS NEEDED
public class Course {

    int id;
    String courseName;

    public Course() {

    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public Course(int id, String courseName) {
        this.courseName = courseName;
        this.id = id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }
}
