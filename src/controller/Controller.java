package controller;

import model.Button;
import model.Task;
import view.View;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

public class Controller {

    ArrayList<Task> modelTask;
    ArrayList<Button> modelButton;
    View view;
    private boolean isRoot;
    private String password;
    private String testDataFilePath;
    private static boolean loggingOn;
    private Data data;

    public Controller(ArrayList<Task> modelTask, ArrayList<Button> modelButton, View view, Properties props) throws URISyntaxException {
        this.modelTask = modelTask;
        this.modelButton = modelButton;
        this.view = view;
        parseProperties(props);
        testDataFilePath = this.getClass().getResource("/TestData/data.xml").toURI().getPath();
        data = new Data(modelTask, modelButton);
    }
    public boolean downloadXml(String filename){
        return data.readXmlData(filename);

    }
    public boolean generateXml(String filedirectory, int num){
        String filename = filedirectory + "/GeneratedData.xml";
        data.readXmlData(testDataFilePath);
        return data.generateTestData(filename,num);
    }
    public static void switchMenu(int item){
        switch(item){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;

        }
    }

    private void parseProperties(Properties props) {
        String userGroup = props.getProperty("userGroup", "root");
        if (userGroup.equals("root")) {
            isRoot = true;
        } else {
            isRoot = false;
        }
        loggingOn = Boolean.parseBoolean(props.getProperty("modeDebug", "true"));
        try {
            /*
            if ((userName = props.getProperty("userName")) == null) {
                throw new NullPointerException("Cвойство userName не найдено");
            }
            */
            if ((password = props.getProperty("password")) == null) {
                throw new NullPointerException("Cвойство password не найдено");
            }
            /*if ((testDataFilePath = props.getProperty("testDataFilePath")) == null) {
                throw new NullPointerException("Cвойство testDataFilePath не найдено");
            }*/
        } catch (NullPointerException e) {
            ErrorMessageLogger err = ErrorMessageLogger.getInstance();
            err.addErrorToListAndLog(e);
            err.showErrText(e);
        }
        int aaa = 0;
    }

    public static boolean isLoggingOn() {
        return loggingOn;
    }
}
