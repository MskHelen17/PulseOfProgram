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
    public void print(){
        System.out.println("Сценарий \""+name+"\": ");
        for(Button button:scenario){
            button.print();
        }
        System.out.println("Список тестов:");
        for(Test test:tests){
            test.print();
        }
    }
}
