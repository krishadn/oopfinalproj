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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KPES
 */
public class StudentController {
    private StudentModel curModel;
    private ArrayList<StudentModel> contents;
    private BufferedReader reader;
    private BufferedWriter writer;
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
            while((line = reader.readLine()) != null){
                StudentModel entry = new StudentModel();
                if (flag == 0){
                    // Do nothing for header columns
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
}
