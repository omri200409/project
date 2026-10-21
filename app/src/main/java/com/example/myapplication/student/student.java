package com.example.myapplication.student;

public class student {
    private String name;
    private String lname;
    private String email;
    private String class1;
    private boolean terms;
    private boolean notifications;
    private String major;

    public student(String name, String lname, String email, String class1, boolean terms, boolean notifications, String major) {
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.class1 = class1;
        this.terms = terms;
        this.notifications = notifications;
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public boolean isTerms() {
        return terms;
    }

    public void setTerms(boolean terms) {
        this.terms = terms;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", last name: " + lname + ", email: " + email +
                ", grade: " + class1 + ", major: " + major + ", notifications: " + (notifications ? "on" : "off");
    }
}