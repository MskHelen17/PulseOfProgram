package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pulseOfProgram.Pulse;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static view.View.primaryStage;

public class GenerateData {
    private static int num;
    @FXML
    private TextField num_of_tests;

    @FXML
    private TextField directoryName;

    @FXML
    private Label warning_field;

    @FXML
    private Label result_field;

    @FXML
    private VBox directoryBlock;

    @FXML
    private VBox numBlock;

    @FXML
    private VBox content;


    private Desktop desktop = Desktop.getDesktop();

    public void submit(){
        if (!num_of_tests.getText().isEmpty()) {
            try {
                num = Integer.parseInt(num_of_tests.getText());
                warning_field.setText(" ");
                warning_field.setStyle("warning: #111");
                directoryBlock.setVisible(true);
                content.getChildren().remove(numBlock);


            } catch (NumberFormatException e) {
                warning_field.setText("Неправильно заполнена форма!");
                warning_field.setStyle("warning: red");
            }


        } else {
            warning_field.setText("Неправильно заполнена форма!");
            warning_field.setStyle("warning: red");
        }
    }

    public void download() throws URISyntaxException {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);
        File dir = directoryChooser.showDialog(primaryStage);
        if (dir != null) {
            directoryName.setText(dir.getAbsolutePath());
        } else {
            directoryName.setText(null);
        }
        boolean f = false;
        f = Pulse.controller.generateXml(dir.getAbsolutePath(),num);
        if(f){
            result_field.setStyle("warning:#111");
            result_field.setText("Данные были успешно сохранены");
        }
        else{
            result_field.setStyle("warning:red;");
            result_field.setText("Ошибка сохранения данных!");
        }
    }
    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) throws URISyntaxException {
        directoryChooser.setTitle("Выберите директорию для сохранения сгенерированных тест данных");

        //String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();

        //directoryChooser.setInitialDirectory(new File(currentPath));
        directoryChooser.setInitialDirectory(new File(this.getClass().getResource("/TestData/").toURI().getPath()));
    }

    private boolean openFile(File file) {
        try {
            if(file.exists())
                this.desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public void showStandart(){
        try {
            openFile(new File(this.getClass().getResource("/TestData/data.xml").toURI().getPath()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
