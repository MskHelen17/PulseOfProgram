package controller;

import java.util.ArrayList;
import java.util.Random;
import model.Button;
import model.Task;
import model.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamConstants;

public class Data {

    private ArrayList<Task> tasks;
    private ArrayList<Button> buttons;

    public Data(ArrayList<Task> tasks, ArrayList<Button> buttons){
        this.tasks = tasks;
        this.buttons = buttons;
    }
    public void print(){
        System.out.println("__________________________");
        System.out.println("Список тестируемых кнопок:");
        for(Button button:buttons){
            button.print();
        }

        System.out.println("________________________________");
        System.out.println("Список Типовых задач(сценариев):");
        for(Task task:tasks){
            task.print();
        }
        System.out.println();
        System.out.println();
    }
    public void readXmlData(){
        final String fileName = "../TestData/data.xml";

        try {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));

            String tagName;
            while (reader.hasNext()) {
                reader.next();

                if(reader.isStartElement()) {
                    tagName = reader.getLocalName();
                    switch (tagName) {
                        case "button":
                            reader.nextTag();
                            reader.next();
                            String currentName = "";
                            if(reader.hasText())
                                currentName = reader.getText();
                            reader.nextTag();
                            reader.nextTag();
                            reader.next();
                            int currentDemand = 0;
                            if(reader.hasText())
                                currentDemand = Integer.parseInt(reader.getText());
                            Button newButton = new Button(currentDemand, currentName);
                            buttons.add(newButton);
                            break;
                        case "task":
                            reader.nextTag();
                            reader.next();
                            String currentTask = "";
                            if(reader.hasText()) {
                                currentTask = reader.getText();
                            }
                            reader.nextTag();
                            reader.nextTag();;
                            ArrayList<Button> currentScenario = new ArrayList<>();
                            if (("scenario").equals(reader.getLocalName())) {
                                reader.nextTag();
                                while (("item").equals((reader.getLocalName()))) {
                                    reader.next();
                                    for (Button button : buttons) {
                                        if (button.getName().equals(reader.getText())) {
                                            currentScenario.add(button);
                                        }
                                    }
                                    reader.nextTag();
                                    reader.nextTag();
                                }
                            }
                            //сценарий считали

                            reader.nextTag();
                            ArrayList<Test> currentTests = new ArrayList<>();
                            if (("test").equals(reader.getLocalName())) {
                                reader.nextTag();
                                reader.next();
                                int currentId = 0;
                                if(reader.hasText()) {
                                    currentId = Integer.parseInt(reader.getText());
                                }
                                reader.nextTag();
                                reader.nextTag();
                                if (("steps").equals(reader.getLocalName())) {
                                    ArrayList<Integer> currentSteps = new ArrayList<>();
                                    int currentMiddleTime = 0;
                                    reader.nextTag();
                                    while (("time").equals(reader.getLocalName())) {
                                        reader.next();
                                        currentSteps.add(Integer.parseInt(reader.getText()));
                                        currentMiddleTime += Integer.parseInt(reader.getText());
                                        reader.nextTag();
                                        reader.nextTag();
                                    }
                                    currentMiddleTime = currentMiddleTime / currentSteps.size();
                                    Test newTest = new Test(currentId, currentMiddleTime, currentSteps);
                                    currentTests.add(newTest);
                                }
                            }
                            //список тестов получили
                            Task newTask = new Task(currentTask, currentScenario, currentTests);
                            tasks.add(newTask);
                            break;
                        default:
                            break;
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        }
    }

    public void generateTestData(int n){
        for(Task task:tasks){
            ArrayList<Test> generatedTestData = new ArrayList<>();
            for(int i=0; i<n; i++){
                int id = i;
                int m = task.getScenario().size()-1;
                ArrayList<Integer> generatedSteps = new ArrayList<>();
                int middleTime = 0;
                for(int j=0; j<m; j++) {
                    int generatedTime = (int) (Math.random() * 10 + 1);
                    generatedSteps.add(generatedTime);
                    middleTime+=generatedTime;
                }
                middleTime = middleTime/m;
                Test generatedTest = new Test(id,middleTime,generatedSteps);
                generatedTestData.add(generatedTest);
            }
            task.setTests(generatedTestData);
        }
        this.print();
    }
    public void writeXmlData(){
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter("../TestData/generatedData.xml"));

            writer.writeStartDocument("1.0");
            writer.writeStartElement("data");
            writer.writeStartElement("buttons");
            for (int i = 0; i < buttons.size(); i++) {
                writer.writeStartElement("button");
                    writer.writeStartElement("name");
                        writer.writeCharacters(buttons.get(i).getName());
                    writer.writeEndElement();
                    writer.writeStartElement("demand");
                        writer.writeCharacters(String.valueOf(buttons.get(i).getDemand()));
                    writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();

            writer.writeStartElement("tasks");
            for (int i = 0; i < tasks.size(); i++) {
                writer.writeStartElement("task");
                    writer.writeStartElement("name");
                        writer.writeCharacters(tasks.get(i).getName());
                    writer.writeEndElement();
                    writer.writeStartElement("scenario");
                    for(int j = 0; j < tasks.get(i).getScenario().size(); j++){
                        writer.writeStartElement("item");
                            writer.writeCharacters(tasks.get(i).getScenario().get(j).getName());
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    for(int j = 0; j < tasks.get(i).getTests().size(); j++) {
                        writer.writeStartElement("test");
                            writer.writeStartElement("id");
                             writer.writeCharacters(String.valueOf(tasks.get(i).getTests().get(j).getId()));
                            writer.writeEndElement();
                            writer.writeStartElement("steps");
                                for (int k=0; k <  tasks.get(i).getTests().get(j).getSteps().size(); k++){
                                    writer.writeStartElement("time");
                                        writer.writeCharacters(String.valueOf(tasks.get(i).getTests().get(j).getSteps().get(k)));
                                    writer.writeEndElement();
                                }
                            writer.writeEndElement();
                        writer.writeEndElement();
                    }
                writer.writeEndElement();
            }
            writer.writeEndElement();

            writer.writeEndDocument();
            writer.flush();
        } catch (XMLStreamException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
