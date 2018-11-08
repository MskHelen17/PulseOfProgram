package controller;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import view.View;

import java.io.IOException;

public class Menu {
    @FXML
    private VBox content;
    @FXML
    private VBox menu;
    @FXML
    private Button first_item;
    @FXML
    private Button second_item;
    @FXML
    private Button third_item;
    @FXML
    private Button fourth_item;
    @FXML
    private Button fifth_item;
    @FXML
    private Button sixth_item;
    @FXML
    private Button seventh_item;
    @FXML
    private Button other_title;

    private String item_chosen_color = "#bddfea";



    @FXML
    public void initialize(){
       if(!View.master) {
            menu.getChildren().remove(other_title);
            menu.getChildren().remove(fifth_item);
            menu.getChildren().remove(sixth_item);
            menu.getChildren().remove(seventh_item);
        }

    }

    public void setWhite(){
        first_item.setStyle("menu-item-color: transparent");
        second_item.setStyle("menu-item-color: transparent");
        third_item.setStyle("menu-item-color: transparent");
        fourth_item.setStyle("menu-item-color: transparent");
        if(View.master) {
            fifth_item.setStyle("menu-item-color: transparent");
            sixth_item.setStyle("menu-item-color: transparent");
            seventh_item.setStyle("menu-item-color: transparent");
        }
    }
    public void loadContent(String name){
        try{
            Parent root = FXMLLoader.load(View.class.getResource(name));
            content.getChildren().setAll(root);
            setWhite();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pressedFirst()
    {
        Controller.switchMenu(1);
        this.loadContent("Scheme_demand.fxml");
        first_item.setStyle("menu-item-color:" + item_chosen_color);
    }

    public void pressedSecond(){
        Controller.switchMenu(2);
        this.loadContent("Scheme_time.fxml");
        second_item.setStyle("menu-item-color:" + item_chosen_color);
    }

    public void pressedThird(){
        Controller.switchMenu(3);
        this.loadContent("Data_report.fxml");
        third_item.setStyle("menu-item-color:" + item_chosen_color);
    }

    public void pressedFourth(){
        Controller.switchMenu(4);
        this.loadContent("Download_data.fxml");
        fourth_item.setStyle("menu-item-color:" + item_chosen_color);
    }

    public void pressedFifth(){
        Controller.switchMenu(5);
        this.loadContent("Generate_data.fxml");
        fifth_item.setStyle("menu-item-color:" + item_chosen_color);
    }

    public void pressedSixth(){
        Controller.switchMenu(6);;

    }

    public void pressedSeventh(){
        Controller.switchMenu(7);
    }
}
