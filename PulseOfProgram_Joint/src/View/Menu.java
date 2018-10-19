package View;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    //контроллер должен прочитать настройки в конфиге и указать, какие пункты следует вывести
    //для пользователя с правами root добавляются пункты меню "отладка" и "автотесты"
    private static HashMap<Integer, String> menuItems;

    public Menu() {
        menuItems = new HashMap();
        menuItems.put(0, "Востребованность элементов по кликам (не реализовано)");
        menuItems.put(1, "Время выполнения типовых задач (не реализовано)");
        menuItems.put(2, "Сводка по тест данным");
        menuItems.put(3, "Загрузить файл с тест данными");
        menuItems.put(4, "Выход");
        menuItems.put(5, "Отладка");
        menuItems.put(6, "Сгенериировать новые тест данные");
        menuItems.put(7, "Генерация ArrayList<Test> и удаление из него 10% эл (для 4 лабы)");
    }

    //приветствие пользователя
    public void sayHello(String userName) {
        System.out.println("Welcome, " + userName);
        System.out.print("Password: ");
    }

    public void showMenu(boolean isRoot) {
        if (!isRoot) {
            menuItems.remove(5);
            menuItems.remove(6);
            menuItems.remove(7);
        }

        System.out.println("\nМеню:");
        System.out.println("-----------------------------------------");

        for(Map.Entry<Integer, String> item : menuItems.entrySet()){
            System.out.printf("%d. %s \n", item.getKey(), item.getValue());
        }
        System.out.println("-----------------------------------------");
        System.out.print("Номер пункта:");

    }

    public void showTimeTree() {
        //отображает дерево с временами
    }

    public void showDemandTree() {
        //отображает дерево востребованности элементов
    }

    public void showTestData() {
        //показывает оформленную сводку по тест данным
    }

    //private ??? showTree() { } используется методами showTimeTree и showDemandTree
}
