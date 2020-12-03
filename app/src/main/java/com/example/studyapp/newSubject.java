package com.example.studyapp;

public class newSubject {
    String subject;
    String time;
    String date;

    public newSubject(String subject, String time, String date) {
        this.subject = subject;
        this.time = time;
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Subject : " + subject + " Date : " + date + " Time : " + time;
    }
}
