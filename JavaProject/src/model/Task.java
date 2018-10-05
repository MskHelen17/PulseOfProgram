package model;

import java.util.ArrayList;

public class Task {
    private String name;
    private ArrayList<Button> scenario;
    private ArrayList<Test> tests;

    public Task(String name, ArrayList<Button> scenario, ArrayList<Test> tests){
        this.name = name;
        this.scenario = scenario;
        this.tests = tests;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Test> getTests(){
        return this.tests;
    }
    public ArrayList<Button> getScenario(){
        return this.scenario;
    }
    public void setTests(ArrayList<Test> newTests){
        this.tests = newTests;
    }
    public void print(){
        System.out.println();
        System.out.println("***********************");
        System.out.println("Сценарий \""+name+"\": ");
        for(Button button:scenario){
            button.print();
        }
        System.out.print("Список тестов: ");
        System.out.println();
        if(tests.size()==0) System.out.println("null");
        for(Test test:tests){
            test.print();
        }
    }
}
