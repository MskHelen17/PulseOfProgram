package controller;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Arrays;
import pulseOfProgram.Pulse;

import static view.View.primaryStage;

public class DownloadData {
    @FXML
    private TextField fileName;
    @FXML
    private Label result_field;

    private Desktop desktop = Desktop.getDesktop();

    public void download(){
        final FileChooser fileChooser = new FileChooser();
        try {
            configuringFileChooser(fileChooser);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        fileName.clear();
        File file = fileChooser.showOpenDialog(primaryStage);
        boolean f = false;
        if ((file != null) && (getExtension(file).equals("xml"))) {
            //openFile(file);
            List<File> files = Arrays.asList(file);
            printLog(fileName, files);
            f = Pulse.controller.downloadXml(file.getAbsolutePath());
            }

        if(!f){
            result_field.setStyle("warning: red");
            result_field.setText("Не удалось загрузить файл");
        }
        else{
            result_field.setStyle("warning: #111");
            result_field.setText("Данные успено загружены");
        }
    }
    private void configuringFileChooser(FileChooser fileChooser) throws URISyntaxException {
        fileChooser.setTitle("Выберите тест данные для загрузки");
        fileChooser.setInitialDirectory(new File(this.getClass().getResource("/TestData/").toURI().getPath()));
        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("Все файлы", "*.*"));

    }
    private void printLog(TextField fileName, List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        for (File file : files) {
            fileName.appendText(file.getAbsolutePath() + "\n");
        }
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
    private String getExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            result_field.setStyle("warning: red");
            result_field.setText("Не удалось загрузить файл");
            return "";
        }
    }
    public void showStandart(){
        int a=0;
        try {
            openFile(new File(this.getClass().getResource("/TestData/data.xml").toURI().getPath()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
