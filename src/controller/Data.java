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
    public void print(){
        System.out.println("Список тестируемых кнопок:");
        for(Button button:buttons){
            button.print();
        }
        System.out.println("Список Типовых задач(сценариев):");
        for(Task task:tasks){
            task.print();
        }
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
                            reader.next();
                            String currentName  = reader.getText();
                            reader.nextTag();
                            int currentDemand = Integer.parseInt(reader.getElementText());

                            Button newButton = new Button(currentDemand, currentName);
                            buttons.add(newButton);
                            break;
                        case "task":
                            reader.next();
                            String currentTask = "";
                            if(("name").equals(reader.getName())) {
                                currentTask = reader.getElementText();
                            }
                            reader.next();
                            ArrayList<Button> currentScenario = new ArrayList<>();
                            if (("scenario").equals(reader.getLocalName())) {
                                reader.next();
                                while (("item").equals((reader.getName()))) {
                                    for (Button button : buttons) {
                                        if (button.getName() == reader.getElementText()) {
                                            currentScenario.add(button);
                                        }
                                    }
                                    reader.next();    //item
                                }
                            }
                            //сценарий считали

                            reader.next();    //scenario
                            ArrayList<Test> currentTests = new ArrayList<>();
                            if (("test").equals(reader.getName())) {
                                int currentId = 0;
                                if(("id").equals(reader.getName())) {
                                    currentId = Integer.parseInt(reader.getElementText());
                                }
                                reader.next();
                                if (("steps").equals(reader.getLocalName())) {
                                    ArrayList<Integer> currentSteps = new ArrayList<>();
                                    int currentMiddleTime = 0;
                                    reader.next();
                                    while (("time").equals(reader.getName())) {
                                        currentSteps.add(Integer.parseInt(reader.getElementText()));
                                        currentMiddleTime += Integer.parseInt(reader.getElementText());
                                        reader.next();    //time
                                    }
                                    reader.next();    //test
                                    reader.next(); //task
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
        this.print();
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
