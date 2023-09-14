package menus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import models.Appointment;
import models.Agenda;

public class SearchMenu {
    public Scanner scanner;
    public Agenda agenda;
    
    public SearchMenu(Scanner scanner, Agenda agenda) {
        this.scanner = scanner;
        this.agenda = agenda;
    }

    /**
     * Mostra il menu ricerca appuntamenti
     */
    public void showMenu() {
        System.out.println();
        System.out.println("Menu ricerca appuntamenti:");
        System.out.println("1 - Per nome persona");
        System.out.println("2 - Per data");
        System.out.println("3 - Per nome persona e data");
        System.out.println("4 - Torna al menu agenda");
        System.out.print(">> ");
        String searchType = scanner.nextLine();

        DateTimeFormatter searchDateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        switch (searchType) {
            case "1":
                searchAppointmentByPerson();
                break;
            case "2":
                searchAppointmentByDate(searchDateFormatter);
                break;
            case "3":
                searchAppointmentByPersonAndDate(searchDateFormatter);
                break;
            case "4":
                return;
            default:
                System.out.println("Inserisci un'opzione valida");
                break;
        }
    }

    /**
     * Filtra gli appuntamenti per nome persona
     */
    private void searchAppointmentByPerson() {
        System.out.print("Inserisci nome persona: ");
        String searchName = scanner.nextLine();
        if (!searchName.matches("^[a-zA-Z ]+$") || searchName.isEmpty()) {
            System.out.println("Nome non valido.");
            return;
        }

        try {
            List<Appointment> filteredAppointments = agenda.filterAppointments(searchName, null);
            if (filteredAppointments.isEmpty()) {
                System.out.println("Nessun appuntamento trovato per '" + searchName + "'.");
            } else {
                System.out.println("Appuntamenti trovati per '" + searchName + "':");
                agenda.listAppointments(filteredAppointments);
            }
        } catch (Exception e) {
            System.err.print("Errore nella ricerca dell'appuntamento: " + e.getMessage());
        }
    }

    /**
     * Filtra gli appuntamenti per data
     * @param searchDateFormatter
     */
    private void searchAppointmentByDate(DateTimeFormatter searchDateFormatter) {
        System.out.print("Inserisci data (dd/mm/yyyy): ");
        String searchDate = scanner.nextLine();

        try {
            List<Appointment> filteredAppointments = agenda.filterAppointments(null, LocalDate.parse(searchDate, searchDateFormatter));
            if (filteredAppointments.isEmpty()) {
                System.out.println("Nessun appuntamento trovato in data '" + searchDate + "'.");
            } else {
                System.out.println("Appuntamenti trovati in data '" + searchDate + "':");
                agenda.listAppointments(filteredAppointments);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Data non valida. Assicurati di inserire la data nel formato dd/mm/yyyy.");
        }
    }

    /**
     * Filtra gli appuntamenti per nome persona e data
     * @param searchDateFormatter
     */
    private void searchAppointmentByPersonAndDate(DateTimeFormatter searchDateFormatter) {
        System.out.print("Inserisci nome persona: ");
        String searchName = scanner.nextLine();
        if (!searchName.matches("^[a-zA-Z]*$") || searchName.isEmpty()) {
            System.out.println("Nome non valido.");
            return;
        }

        System.out.print("Inserisci data (dd/mm/yyyy): ");
        String searchDate = scanner.nextLine();
        try {
            List<Appointment> filteredAppointments = agenda.filterAppointments(searchName, LocalDate.parse(searchDate, searchDateFormatter));
            if (filteredAppointments.isEmpty()) {
                System.out.println("Nessun appuntamento trovato per '" + searchName + "' in data '" + searchDate + "'.");
            } else {
                System.out.println("Appuntamenti trovati per '" + searchName + "' in data '" + searchDate + "':");
                agenda.listAppointments(filteredAppointments);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Data non valida. Assicurati di inserire la data nel formato dd/mm/yyyy.");
        } catch (Exception e) {
            System.err.print("Errore nella ricerca dell'appuntamento: " + e.getMessage());
        }
    }
}
