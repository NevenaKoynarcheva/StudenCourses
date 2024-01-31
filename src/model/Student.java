package model;

public class Student {
    private String firstName;
    private String lastName;
    private String courseName;
    private int credit;
    private int time;
    private String instructor;

    public Student(String firstName, String lastName, String courseName, int credit, int time, String instructor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseName = courseName;
        this.credit = credit;
        this.time = time;
        this.instructor = instructor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}