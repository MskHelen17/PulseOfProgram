package Controller;

import Model.Button;
import Model.Task;
import PulseOfProgram.Pulse;
import View.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    ArrayList<Task> modelTask;
    ArrayList<Button> modelButton;
    Menu view;
    //Properties props;
    //Data data;
    Scanner in = new Scanner(System.in);
    private boolean isRoot;
    private  String userName;
    private String password;
    private static boolean loggingOn;
    private static Logger log = Logger.getLogger(Main.class.getName());
    private String testDataFilePath;
    private String generatedTestDataFilePath;
    private int countOfTests;
    private Data data;

    public Main(ArrayList<Task> modelTask, ArrayList<Button> modelButton, Menu view, Properties props) {
        this.modelTask = modelTask;
        this.modelButton = modelButton;
        this.view = view;
        parseProperties(props);
        data = new Data(modelTask, modelButton);
        data.readXmlData(testDataFilePath);
        //this.props = props;

    }

    private void parseProperties(Properties props) {
        String userGroup = props.getProperty("userGroup");
        if (userGroup.equals("root")) {
            isRoot = true;
        } else {
            isRoot = false;
        }
        userName = props.getProperty("userName");
        password = props.getProperty("password");
        loggingOn = Boolean.parseBoolean(props.getProperty("modeDebug"));
        testDataFilePath = props.getProperty("testDataFilePath");
        generatedTestDataFilePath = props.getProperty("generatedTestDataFilePath");
        countOfTests = Integer.parseInt(props.getProperty("countOfTests"));
    };

    public static boolean isLoggingOn() {
        return loggingOn;
    }

    public static void setLoggingOn(boolean loggingOn) {
        Main.loggingOn = loggingOn;
    }

    public boolean switchMenu() {
        view.showMenu(isRoot);
        //Data data = new Data(modelTask, modelButton);
        try {
            switch (Integer.parseInt(in.nextLine())) {
                case 0:
                    view.showDemandTree();
                    break;
                case 1:
                    view.showTimeTree();
                    break;
                case 2:
                    data.print();
                    //view.showTestData();
                    break;
                case 3:
                    //String fileName = props.getProperty("testDataFilePath");
                    System.out.println("Путь к файлу:");
                    String path = in.nextLine();
                    data.readXmlData(path);

                    break;
                case 4:
                    return false;
                case 5:
                    if (isRoot == false) {
                        throw new NumberFormatException();
                    }
                    //Лог с информацией о включении режима "Отладка" в меню
                    setLoggingOn(true);
                    break;
                case 6:
                    if (isRoot == false) {
                        throw new NumberFormatException();
                    }
                    //Data data = new Data(modelTask, modelButton);
                    data.generateTestData(generatedTestDataFilePath,countOfTests);
                    break;
                case 7:
                    if (isRoot == false) {
                        throw new NumberFormatException();
                    }
                    data.deleteSomeData(countOfTests);
                    break;
                default:
                    throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            if (isLoggingOn()) {
                log.log(Level.SEVERE, "Exception: ", e);
            }
            System.err.println("Введен неверный номер пункта меню");
        }

        return true;
    }

    //приветствие пользователя и его вход в систему
    public void checkPassword() {
        //String userName = props.getProperty("userName");
        //String correctPassword = props.getProperty("password");
        view.sayHello(userName);
        String enteredPassword =  in.nextLine();
        while (!(enteredPassword.equals(password))) {
            System.out.println("Wrong password. Try again");
            enteredPassword = in.nextLine();
        }
    }

}
