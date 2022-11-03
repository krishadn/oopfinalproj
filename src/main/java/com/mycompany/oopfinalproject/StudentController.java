/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oopfinalproject;

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

    public StudentController() {
        this.curModel = new StudentModel();
        this.contents = new ArrayList<>();
        try {
            this.reader = null;
            this.reader = new BufferedReader(new FileReader("student.csv"));
            String line;
            int flag = 0;
            while((line = reader.readLine()) != null){
                StudentModel entry = new StudentModel();
                if (flag == 0){
                    // TODO add codes for column header
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
        
        try {
            this.writer = null;
            this.writer = new BufferedWriter(new FileWriter("student.csv"));
        } catch (IOException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(contents.isEmpty()){
            this.initStudentNo = 0;
        } else {
            this.initStudentNo = contents.get(contents.size()-1).getStudentNo();
        }             
    }

    public int getInitStudentNo() {
        return initStudentNo;
    }
    
    
    
    
    
}
