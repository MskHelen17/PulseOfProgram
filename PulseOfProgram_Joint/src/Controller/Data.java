package Controller;

import Model.Button;
import Model.Task;
import Model.Test;
import PulseOfProgram.Pulse;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Data {

    private ArrayList<Task> tasks;
    private ArrayList<Button> buttons;
    private static Logger log = Logger.getLogger(Data.class.getName());

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
    public void readXmlData(String fileName){
        if (Main.isLoggingOn()) {
            log.info("Начинается прочитывание файла \"" + fileName + "\"");
        }


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
                            while (("test").equals((reader.getLocalName()))) {
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
                                    reader.nextTag();
                                    reader.nextTag();
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

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
            if (Main.isLoggingOn()) {
                log.log(Level.SEVERE, "Ошибка прочтения файла \"" + fileName + "\", Exception: ", e);
            }
        }

        if (Main.isLoggingOn()) {
            log.info("Файл \"" + fileName + "\" успешно прочитан");
        }
    }
    public void writeXmlData(String filename){
        if (Main.isLoggingOn()) {
            log.info("Начинается запись файла \"" + filename + "\"");
        }
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter(filename));
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
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
            if (Main.isLoggingOn()) {
                log.log(Level.SEVERE, "Ошибка записи файла \"" + filename + "\", Exception: ", e);
            }
        }
        if (Main.isLoggingOn()) {
            log.info("Файл \"" + filename + "\" успешно записан");
        }
    }
    public void generateTestData(String fileName, int countOfTests){
        for(Task task:tasks) {
            ArrayList<Test> generatedTestData = new ArrayList();
            for (int i = 0; i < countOfTests; i++) {
                int id = i;
                int m = task.getScenario().size() - 1;
                ArrayList<Integer> generatedSteps = new ArrayList();
                int middleTime = 0;
                for (int j = 0; j < m; j++) {
                    int generatedTime = (int) (Math.random() * 10 + 1);
                    generatedSteps.add(generatedTime);
                    middleTime += generatedTime;
                }
                middleTime = middleTime / m;
                Test generatedTest = new Test(id, middleTime, generatedSteps);
                generatedTestData.add(generatedTest);

            }
            task.setTests(generatedTestData);
        }
        this.print();
        this.writeXmlData(fileName);
    }
    public ArrayList<Test> generateSomeData(int countOfTests){  //Удалить после 4 лабы

        String text = "ArrayList<Test> Add\n";
        long time = 0;
        ArrayList<Test> generatedTestArray = new ArrayList();
        for (int i = 0; i < countOfTests; i++) {
            int id = i;
            int m = tasks.get(0).getScenario().size() - 1;
            ArrayList<Integer> generatedSteps = new ArrayList();
            int middleTime = 0;
            for (int j = 0; j < m; j++) {
                int generatedTime = (int) (Math.random() * 10 + 1);
                generatedSteps.add(generatedTime);
                middleTime += generatedTime;
            }
            middleTime = middleTime / m;
            Test generatedTest = new Test(id, middleTime, generatedSteps);
            long startTime = System.nanoTime();
            generatedTestArray.add(generatedTest);
            long estimatedTime = System.nanoTime() - startTime;
            time += estimatedTime;
            text += "add, id = " + generatedTest.getId() + ", time = " + estimatedTime + "\n";
        }
        text += "addTotalCount = " + countOfTests + "\n";
        text += "addTotalTime = " + time + "\n";
        text += "addMedianTime = " + time / countOfTests + "\n";
        log.info(text);
        return generatedTestArray;
    }
    public void deleteSomeData(int countOfTests){       //Удалить после 4 лабы

        ArrayList<Test> testArray = this.generateSomeData(countOfTests);
        if(testArray.size() > 0) {
            String text = "ArrayList<Test> Remove\n";
            long time = 0;
            int count = (int) Math.round(countOfTests * 0.1);
            for (int i = 0; i < count; i++) {
                int generatedId = (int) (Math.random() * (countOfTests-i));
                long startTime = System.nanoTime();
                testArray.remove(generatedId);
                long estimatedTime = System.nanoTime() - startTime;
                time += estimatedTime;   //Удалить после 4 лабы
                text += "remove, id = " + generatedId + ", time = " + estimatedTime  + "\n";
            }
            text += "removeTotalCount = " + count + "\n";
            text += "removeTotalTime = " + time + "\n";
            text += "removeMedianTime = " + time / count + "\n";
            log.info(text);
        }


    }


}
