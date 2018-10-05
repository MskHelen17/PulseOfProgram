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
            XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter("../TestData/result.xml"));

            // Открываем XML-документ и Пишем корневой элемент BookCatalogue
            writer.writeStartDocument("1.0");
            writer.writeStartElement("BookCatalogue");
            // Делаем цикл для книг
            for (int i = 0; i < 5; i++) {
                // Записываем Book
                writer.writeStartElement("Book");

                // Заполняем все тэги для книги
                // Title
                writer.writeStartElement("Title");
                writer.writeCharacters("Book #" + i);
                writer.writeEndElement();
                // Author
                writer.writeStartElement("Author");
                writer.writeCharacters("Author #" + i);
                writer.writeEndElement();
                // ISBN
                writer.writeStartElement("ISBN");
                writer.writeCharacters("ISBN #" + i);
                writer.writeEndElement();
                // Publisher
                writer.writeStartElement("Publisher");
                writer.writeCharacters("Publisher #" + i);
                writer.writeEndElement();
                // Cost
                writer.writeStartElement("Cost");
                writer.writeAttribute("currency", "USD");
                writer.writeCharacters("" + (i+10));
                writer.writeEndElement();

                // Закрываем тэг Book
                writer.writeEndElement();
            }
            // Закрываем корневой элемент
            writer.writeEndElement();
            // Закрываем XML-документ
            writer.writeEndDocument();
            writer.flush();
        } catch (XMLStreamException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
