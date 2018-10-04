package controller;

import java.util.ArrayList;
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
    public void readXmlData(){
        final String fileName = "../TestData/data.xml";

        try {
            XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));

            String tagName;
            while (xmlr.hasNext()) {
                int type = xmlr.next();

                if(type == XMLStreamConstants.START_ELEMENT) {
                    tagName = xmlr.getLocalName();
                    switch (tagName) {
                        case "button":
                            int currentDemand = Integer.parseInt(xmlr.getAttributeValue(null, "demand"));
                            String currentName = xmlr.getText();
                            Button newButton = new Button(currentDemand, currentName);
                            buttons.add(newButton);
                            break;
                        case "task":
                            String currentTask = xmlr.getAttributeValue(null, "name");
                            xmlr.next();
                            ArrayList<Button> currentScenario = new ArrayList<>();
                            if (new String("scenario").equals(xmlr.getLocalName())) {

                                xmlr.next();
                                while (new String("item").equals((xmlr.getLocalName()))) {
                                    for (Button button : buttons) {
                                        if (button.getName() == xmlr.getText()) {
                                            currentScenario.add(button);
                                        }
                                    }
                                    xmlr.next();    //item
                                }
                            }
                            //сценарий считали

                            xmlr.next();    //scenario
                            ArrayList<Test> currentTests = new ArrayList<>();
                            if (new String("test").equals(xmlr.getLocalName())) {
                                int currentId = Integer.parseInt(xmlr.getAttributeValue(null, "id"));
                                xmlr.next();
                                if (new String("steps").equals(xmlr.getLocalName())) {
                                    ArrayList<Integer> currentSteps = new ArrayList<>();
                                    int currentMiddleTime = 0;
                                    xmlr.next();
                                    while (new String("time").equals(xmlr.getLocalName())) {
                                        currentSteps.add(Integer.parseInt(xmlr.getText()));
                                        currentMiddleTime += Integer.parseInt(xmlr.getText());
                                        xmlr.next();    //time
                                    }
                                    xmlr.next();    //test
                                    xmlr.next(); //task
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

    public void generateTestData(){

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
