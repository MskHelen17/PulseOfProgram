package auto;

import controller.Data;
import model.Button;
import model.Task;
import java.util.ArrayList;

public class Common {

    private ArrayList<Task> tasks;
    private ArrayList<Button> buttons;

    public static void main(String[] args){

        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Button> buttons = new ArrayList<Button>();
        Data newData = new Data(tasks, buttons);

        newData.readXmlData();
        //newData.writeXmlData();
    }
}
