package model;

public class Button {
    private String name;
    private int demand;

    public Button(int demand, String name){
        this.name = name;
        this.demand = demand;
    }
    public String getName(){
        return this.name;
    }
    public int getDemamd(){
        return this.demand;
    }
}
