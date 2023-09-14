package menus;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import managers.AgendaManager;
import models.Agenda;

public class MainMenu {
    private AgendaManager agendaManager;
    private Scanner scanner;

    public MainMenu(AgendaManager agendaManager, Scanner scanner) {
        this.agendaManager = agendaManager;
        this.scanner = scanner;
    }

    /**
     * Mostra il menu principale
     */
    public void showMenu() {
        while (true) {
            System.out.println();
            System.out.println("Menu principale:");
            System.out.println("1 - Crea agenda vuota");
            System.out.println("2 - Importa agenda da file");
            System.out.println("3 - Seleziona agenda");
            System.out.println("4 - Esci");
            System.out.print(">> ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createAgenda();
                    break;
                case "2":
                    importAgenda();
                    break;
                case "3":
                    selectAgenda();
                    break;
                case "4":
                    exitProgram();
                    return;
                default:
                    System.out.println("Inserisci un'opzione valida");
                    break;
            }
        }
    }

    /**
     * Crea l'agenda
     */
    private void createAgenda() {
        System.out.print("Inserisci nome agenda: ");
        String name = scanner.nextLine();
        if (name == null || name.isEmpty() || !name.matches("^[a-zA-Z0-9]*$")) {
            System.out.println("Nome non valido.");
            return;
        }

        try {
            Agenda createdAgenda = agendaManager.createAgenda(name);
            if (createdAgenda != null)
                System.out.println("Agenda creata con successo!");
        } catch (Exception e) {
            System.err.println("Errore nella creazione: " + e.getMessage());
        }
    }

    /**
     * Importa l'agenda da file in .\esporta\*.csv
     */
    private void importAgenda() {
        System.out.print("Inserisci nome file (.csv): ");
        String filename = scanner.nextLine();
        if (filename == null || filename.isEmpty() || !filename.matches("^[a-zA-Z0-9]*$")) {
            System.out.println("Nome file non valido.");
            return;
        }

        try {
            agendaManager.loadAgendaFromFile(filename);
            System.out.println("Agenda importata con successo!");
        } catch (IOException e) {
            System.err.println("Errore nell'importazione: " + e.getMessage());
        }
    }

    /**
     * Seleziona un'agenda
     */
    private void selectAgenda() {
        Set<String> agendas = agendaManager.listAgendas();
        if (agendas.isEmpty()) {
            System.out.println("Non hai nessuna agenda.");
            return;
        }

        System.out.println("Agende disponibili:");
        for (String agendaName : agendas)
            System.out.println("- " + agendaName);

        System.out.print("Inserisci nome agenda da selezionare: ");
        String toSelect = scanner.nextLine();

        try {
            Agenda selectedAgenda = agendaManager.getAgendaByName(toSelect);
            if (selectedAgenda != null) {
                AgendaMenu agendaMenu = new AgendaMenu(agendaManager, scanner, selectedAgenda);
                agendaMenu.showMenu();
            } else {
                System.out.println("Agenda non trovata.");
            }
        } catch (Exception e) {
            System.err.println("Errore nella selezione: " + e.getMessage());
        }
    }

    /**
     * Esci dal programma
     */
    private void exitProgram() {
        System.out.println("Arrivederci");
        scanner.close();
    }
}
