package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.Button;
import model.Task;
import model.Test;

import static pulseOfProgram.Pulse.modelButton;
import static pulseOfProgram.Pulse.modelTask;

public class DataReport {
    @FXML
    private Label data_state;
    @FXML
    private VBox report;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox content;

    @FXML
    public void initialize() {
        if (modelTask.size() == 0){
            data_state.setText("Тест данные не были загружены. Выберите пункт меню \"Загрузить тест данные\"");
            scrollPane.setVisible(false);
        }
        else{
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            content.getChildren().remove(data_state);
            createItem("Список тестируемых кнопок:","h1");
            for(Button button:modelButton){
                createItem("    \""+button.getName()+"\", востребованность "+button.getDemand()+"\n","p");
            }

            createItem("\n","p");
            createItem("Типовые задачи (сценарии):","h1");
            for(Task task:modelTask){
                createItem("***********************","p");
                createItem("\""+task.getName()+"\": ","h1");
                for(Button button:task.getScenario()){
                    createItem("    \""+button.getName()+"\", востребованность "+button.getDemand()+"\n","p");
                }

                createItem("\n","p");
                createItem("Список тестов: ","h1");
                if(task.getTests().size()==0) createItem("Отсутствует","p");
                for(Test test:task.getTests()){
                    String stepsTime = "";
                    for(int time:test.getSteps()){
                        stepsTime += time+" ";
                    }
                    createItem("    Id="+test.getId()+", среднее время="+test.getMiddleTime()+", время шагов: "+stepsTime,"p");
                }
                createItem("\n","p");
            }
        }
    }

    private void createItem(String text, String classname){
        Label lb = new Label();
        lb.setText(text);
        if(classname.equals("h1")){
            lb.setStyle("-fx-font-weight: bold;");
        }
        report.getChildren().add(lb);
    }
}
