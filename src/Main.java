import java.util.Scanner;

import managers.AgendaManager;
import menus.MainMenu;

public class Main {
    public static void main(String[] args) {
        AgendaManager manager = new AgendaManager();
        Scanner scanner = new Scanner(System.in);
        MainMenu menuManager = new MainMenu(manager, scanner);

        menuManager.showMenu();
    }
}