package pulseOfProgram;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import view.View;
import model.*;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class Pulse extends Application {

    public static ArrayList<Task> modelTask;
    public static ArrayList<Button> modelButton;
    public static View view;
    public static Controller controller;
    public static Properties props;

    private static Logger log = Logger.getLogger(Pulse.class.getName());
    @Override
    public void start(Stage primaryStage) throws Exception{

        view = new View(primaryStage);
        controller = new Controller(modelTask, modelButton, view, props);
        if (Controller.isLoggingOn()) {
            log.info("Запуск программы");
        }
        View.showNewScene("/view/Login.fxml",500, 300);

    }


    public static void main(String[] args) {

        props = new Properties();
        //Класс для загрузки конфигураций
        Preloader loader = new Preloader();
        //чтение файла с настройками логирования
        loader.loadLoggingConfiguration("resources/logging.properties");
        //чтение конфиг файла
        loader.loadConfigFile("resources/config.properties", props);
        //инициализация модели
        modelTask = new ArrayList<>();
        modelButton = new ArrayList<>();

        launch(args);

        if (Controller.isLoggingOn()) {
            log.info("Завершение программы\n");
        }
    }
}
