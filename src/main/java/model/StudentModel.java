/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author KPES
 */
public class StudentModel {
    private int studentNo;
    private String firstName;
    private String lastName;
    private int yearLevel;
    private int age;
    private String gender;
    private String program;

    public StudentModel(int studentNo, String firstName, String lastName, int yearLevel, int age, String gender, String program) {
        this.studentNo = studentNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearLevel = yearLevel;
        this.age = age;
        this.gender = gender;
        this.program = program;
    }

    public StudentModel() {
    }

    @Override
    public String toString() {
        return "EmployeeModel{" + "studentNo=" + studentNo + ", firstName=" + firstName + ", lastName=" + lastName + ", yearLevel=" + yearLevel + ", age=" + age + ", gender=" + gender + ", program=" + program + '}';
    }

    public int getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
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

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }    
        
    
}
