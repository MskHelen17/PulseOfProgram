package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import view.View;

public class Login {
    @FXML
    private TextField password_field;

    public void skipLogin(){
        View.showNewScene("/view/Menu.fxml",1000,600);


    }
    public void tryToLogin(){
        if(password_field.getText().equals("123")){
            View.master = true;
            View.showNewScene("/view/Menu.fxml",1000,600);
        }else{
            password_field.setText("Неверный пароль!");
            password_field.setFocusTraversable(true);
            password_field.setStyle("-fx-text-fill:red");
        }
    }
    public void focusField(){
        password_field.setText("");
        password_field.setFocusTraversable(true);
        password_field.setStyle("-fx-text-fill: #888");
    }

    public void submit(KeyEvent key){

        if (key.getCode() == KeyCode.ENTER)  {
            this.tryToLogin();
        }
    }
}
