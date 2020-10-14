package com.example.cse_solution.ui.gallery;

public class Model_R {
    String day,time,course,teacher;

    public Model_R(String day, String time, String course, String teacher) {
        this.day = day;
        this.time = time;
        this.course = course;
        this.teacher = teacher;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
