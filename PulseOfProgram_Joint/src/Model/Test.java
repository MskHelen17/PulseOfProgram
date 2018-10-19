package Model;

import java.util.ArrayList;

public class Test {
    private int id;
    int middleTime;
    private ArrayList<Integer> steps;

    public Test(int id, int middleTime, ArrayList<Integer> steps){
        this.id = id;
        this.middleTime = middleTime;
        this.steps = steps;
    }
    public int getId(){
        return this.id;
    }
    public ArrayList<Integer> getSteps(){
        return this.steps;
    }
    public void print(){
        System.out.print("Тест id="+id+", среднее время="+middleTime+", время шагов: ");
        for(int time:steps){
            System.out.print(time+" ");
        }
        System.out.print("\n");
    }
}
