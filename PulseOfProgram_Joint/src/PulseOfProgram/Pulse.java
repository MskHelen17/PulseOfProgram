package PulseOfProgram;

import Controller.Main;
import Model.Button;
import Model.Task;
import View.Menu;


import java.util.ArrayList;
//import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class Pulse {


    public static ArrayList<Task> modelTask;
    public static ArrayList<Button> modelButton;
    public static Menu view;
    public static Main controller;
    //private static boolean loggingOn;
    private static Logger log = Logger.getLogger(Pulse.class.getName());

    //public static boolean isLoggingOn() {
        //return loggingOn;
    //}

    //public static void setLoggingOn(boolean loggingOn) {
        //Pulse.loggingOn = loggingOn;
    //}

    public static void main(String[] args) {

        //чтение конфиг файла
        Properties props = new Properties();
        Preloader loader = new Preloader();
        loader.loadConfigFile("resources/config.properties", props);
        loader.loadLoggingConfiguration("resources/logging.properties");
        //loggingOn = Boolean.parseBoolean(props.getProperty("modeDebug"));
        //инициализация модели
        //model = new ArrayList<Task>();
        String treeFilePath = props.getProperty("treeFilePath");
        //int i = 0;
        //for (Task t : model) {
        //model.loadData(treeFilePath + i.toString())
        //}

        //инициализация представления, контроллера
        view = new Menu();
        modelTask = new ArrayList<>();
        modelButton = new ArrayList<>();
        controller = new Main(modelTask, modelButton, view, props);
        String userName = props.getProperty("userName");
        if (Main.isLoggingOn()) {
            log.info("Запуск программы пользователем " + userName);
        }

        //приветствие и вход пользователя в систему
        controller.checkPassword();

        //меню
        boolean b = true;
        while (b) {
            b = controller.switchMenu();
        }

        if (Main.isLoggingOn()) {
            log.info("Завершение программы");

        }
    }

}