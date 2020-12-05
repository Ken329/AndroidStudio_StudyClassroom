package com.example.studyapp;

public class newStudent {
    String username;
    String password;
    String phone;
    String semester;
    String subject1;
    String subject2;
    String additional;

    public newStudent(String username, String password, String phone, String semester, String subject1, String subject2, String additional) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.semester = semester;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.additional = additional;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
}
