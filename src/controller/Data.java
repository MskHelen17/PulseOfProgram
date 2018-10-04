package controller;

import java.util.ArrayList;
import javax.xml.stream.XMLStreamReader;
import java.nio.file.Paths;
import java.nio.file.Files;
import model.Button;
import model.Task;
import model.Test;

public class Data {
    private ArrayList<Task> tasks;
    private ArrayList<Button> buttons;

    public void readXmlData(){
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get("../../../TestData/data.xml")))) {
            XMLStreamReader reader = processor.getReader();
            while (processor.startElement("button", "buttons")) {
                System.out.println(processor.getAttribute("id") +":" + processor.getText());
            }
        }
    }
    public void generateTestData(){

    }
    public void writeXmlData(){
    }
}
