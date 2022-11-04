/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.StudentModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author KPES
 */
public class StudentController {
    private StudentModel curModel;
    private ArrayList<StudentModel> contents;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String header;
    private final int initStudentNo;  
    public int nextID;

    public StudentController() {
        this.curModel = new StudentModel();
        this.contents = new ArrayList<>();
        loadData();
        if(contents.isEmpty()){
            this.initStudentNo = 1;
        } else {
            this.initStudentNo = (contents.get(contents.size()-1).getStudentNo()) + 1;
        }
    }
    
    public void loadData() {
        try {
            this.reader = new BufferedReader(new FileReader("resources/student.csv"));
            String line;
            int flag = 0;
            this.nextID = Integer.parseInt(new BufferedReader(new FileReader("resources/idCounter.txt")).readLine());
            contents.clear();
            while((line = reader.readLine()) != null){
                StudentModel entry = new StudentModel();
                if (flag == 0){
                    header = line;
                } else {
                    String[] temp = line.split(",");                  
                    entry.setStudentNo(Integer.parseInt(temp[0]));    //student no.
                    entry.setFirstName(temp[1]);                     //first name
                    entry.setLastName(temp[2]);                     //last name
                    entry.setYearLevel(Integer.parseInt(temp[3]));    //year level
                    entry.setAge(Integer.parseInt(temp[4]));    //age
                    entry.setGender(temp[5]);                     //gender
                    entry.setProgram(temp[6]);                     //program
                    contents.add(entry);
                }
                flag++;
            }           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            try {
                if (reader != null){
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getInitStudentNo() {
        return initStudentNo;
    }
    
    public int getCurrentID() {
        return this.nextID;
    }
    
    public void incrementID() {
        try {
            String nextNum = Integer.toString(this.nextID++);
            // Creates a FileWriter
            FileWriter file = new FileWriter("resources/idCounter.txt");

            // Writes the string to the file
            // Creates a BufferedWriter
            try (BufferedWriter output = new BufferedWriter(file)) {
                // Writes the string to the file
                output.write(nextNum);
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<String> searchRegex(String inp){
        ArrayList<String> result = new ArrayList<>();
        String regex = ("^[a-zA-Z\\s]*" + inp + "[a-zA-Z\\s]*$");
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            for (StudentModel student: contents){
                String fullName = student.getFirstName() + " " + student.getLastName();
                int studentNo = student.getStudentNo();
            
                Matcher mtFullName = pattern.matcher(fullName);
            
                if(mtFullName.find()){
                    String studentDet = String.format("Student no. 2022-%d: %s", studentNo,  fullName);
                    result.add(studentDet);
                }
            }               
        return result;    
    }
    
    public String[] searchStudent(String viewBy, String inp){
        String[] studentDetails = new String[7];
        Arrays.fill(studentDetails, null);
        if(viewBy.equals("Student no.")){
            int number = Integer.parseInt(inp);
            for(StudentModel student: contents){
                if(student.getStudentNo() == number){
                    studentDetails[0] = String.valueOf(student.getStudentNo());
                    studentDetails[1] = student.getFirstName();
                    studentDetails[2] = student.getLastName();
                    studentDetails[3] = String.valueOf(student.getYearLevel());
                    studentDetails[4] = String.valueOf(student.getAge());
                    studentDetails[5] = student.getGender();
                    studentDetails[6] = student.getProgram();
                    break;
                }
                
            }
        } else if (viewBy.equals("Last name")){
            for(StudentModel student: contents){
                if(student.getLastName().equalsIgnoreCase(inp.trim())){
                    studentDetails[0] = String.valueOf(student.getStudentNo());
                    studentDetails[1] = student.getFirstName();
                    studentDetails[2] = student.getLastName();
                    studentDetails[3] = String.valueOf(student.getYearLevel());
                    studentDetails[4] = String.valueOf(student.getAge());
                    studentDetails[5] = student.getGender();
                    studentDetails[6] = student.getProgram();
                    break;
                }                
            }
        }        
        return studentDetails;
    }
    
    
    public void createStudent(StudentModel student) {
        System.out.println(student.toString());
        System.out.println(contents.size());
        try {
            // Creates a FileWriter
            FileWriter studentsFile = new FileWriter("resources/student.csv", true); // Second parameter is append
            FileWriter idCounterFile = new FileWriter("resources/idCounter.txt");
            
            // Append new row
            // Creates a BufferedWriter
            try (BufferedWriter output = new BufferedWriter(studentsFile)) {
                // Append new row
                output.write("\n" + student.toFormattedCSVRow());
            }
            
            try (BufferedWriter output = new BufferedWriter(idCounterFile)) {
                // Append new row
                output.write(Integer.toString(this.getCurrentID()));
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }


    public boolean updateStudent(int studentNo, String firstName, String lastName, int yearLevel, int age, String gender, String program){
        StudentModel studentToUpd = new StudentModel(studentNo, firstName, lastName, yearLevel, age, gender, program);
        ArrayList<String> entries = new ArrayList<>();
        entries.add(header);
        
        for(StudentModel student: contents){
            if(student.getStudentNo() == studentToUpd.getStudentNo()){               
                entries.add("\n" + studentToUpd.toFormattedCSVRow());     
            } else {
                entries.add("\n" + student.toFormattedCSVRow());               
            }
        }
        try {
            FileWriter studentsFile = new FileWriter("resources/student.csv");
            try (BufferedWriter output = new BufferedWriter(studentsFile)) {
               for(String entry: entries){
                output.write(entry);
                }
               return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }     
    }
}
