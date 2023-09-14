package menus;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import managers.AgendaManager;
import models.Agenda;
import models.Appointment;

public class AgendaMenu {
    private AgendaManager agendaManager;
    private Scanner scanner;
    private Agenda agenda;

    public AgendaMenu(AgendaManager agendaManager, Scanner scanner, Agenda agenda) {
        this.agendaManager = agendaManager;
        this.scanner = scanner;
        this.agenda = agenda;
    }

    /**
     * Mostra il menu dell'agenda
     */
    public void showMenu() {
        while (true) {
            System.out.println();
            System.out.println("Menu agenda '" + agenda.getName() + "':");
            System.out.println("1 - Elimina questa agenda");
            System.out.println("2 - Esporta questa agenda su file");
            System.out.println("3 - Inserisci nuovo appuntamento");
            System.out.println("4 - Modifica appuntamento");
            System.out.println("5 - Cerca appuntamenti");
            System.out.println("6 - Elenca appuntamenti (ordine alfabetico)");
            System.out.println("7 - Torna al menu principale");
            System.out.print(">> ");
            String agendaSubChoice = scanner.nextLine();

            switch (agendaSubChoice) {
                case "1":
                    deleteAgenda();
                    break;
                case "2":
                    exportAgenda();
                    break;
                case "3":
                    insertNewAppointment();
                    break;
                case "4":
                    editAppointment();
                    break;
                case "5":
                    SearchMenu searchAppointmentMenu = new SearchMenu(scanner, agenda);
                    searchAppointmentMenu.showMenu();
                    break;
                case "6":
                    // Elenca appuntamenti (ordine alfabetico)
                    System.out.println("Elenco appuntamenti: ");
                    agenda.listAppointments();
                    break;
                case "7":
                    // Torna al menu principale
                    return;
                default:
                    System.out.println("Inserisci un'opzione valida");
                    break;
            }
        }
    }
    
    /**
     * Cancella l'agenda
     */
    private void deleteAgenda() {
        System.out.print("Sei sicuro di voler eliminare l'agenda: " + agenda.getName() + "? (s/n): ");
        String confirmDelete;

        try {
            do {
                confirmDelete = scanner.nextLine().trim();
                if ("s".equalsIgnoreCase(confirmDelete)) {
                    this.agendaManager.deleteAgenda(agenda.getName());
                    System.out.println("Agenda eliminata con successo!");
                    return;
                } else if ("n".equalsIgnoreCase(confirmDelete)) {
                    System.out.println("Eliminazione annullata.");
                    return;
                } else {
                    System.out.print("Per favore, inserisci 's' o 'n' come risposta: ");
                }
            } while (!"s".equalsIgnoreCase(confirmDelete) && !"n".equalsIgnoreCase(confirmDelete));
        } catch (Exception e) {
            System.err.println("Errore nell'eliminazione: " + e.getMessage());
        }
    }

    /**
     * Esporta l'agenda in .\exported\*.csv
     */
    private void exportAgenda() {
        System.out.print("Inserisci nome file per esportare: ");
        String filename = scanner.nextLine();

        try {
            this.agendaManager.writeAgendaToFile(agenda.getName(), filename);
            System.out.println("Agenda esportata con successo!");
        } catch (IOException e) {
            System.err.println("Errore nell'esportazione: " + e.getMessage());
        }
    }

    /**
     * Inserisci un nuovo appuntamento
     */
    private void insertNewAppointment() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String date, time, duration, person, location;
        LocalDate formattedDate = null;
        LocalTime formattedTime = null;

        // Data
        boolean validDate = false;
        do {
            System.out.print("Inserisci data appuntamento (dd/mm/yyyy): ");
            date = scanner.nextLine();
            try {
                formattedDate = LocalDate.parse(date, dateFormatter);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Assicurati di inserire la data nel formato dd/mm/yyyy.");
            }
        } while (!validDate);

        // Ora
        boolean validTime = false;
        do {
            System.out.print("Inserisci orario appuntamento (hh:mm): ");
            time = scanner.nextLine();
            try {
                formattedTime = LocalTime.parse(time, timeFormatter);
                validTime = true;
            } catch (DateTimeParseException e) {
                System.out.println("Orario non valido. Assicurati di inserire l'orario nel formato hh:mm.");
            }
        } while (!validTime);

        // Durata
        boolean validDuration = false;
        do {
            System.out.print("Inserisci durata appuntamento in minuti: ");
            duration = scanner.nextLine();
            if (duration.matches("\\d+") && Integer.parseInt(duration) > 0) {
                validDuration = true;
            } else {
                System.out.println("Durata non valida. Assicurati di inserire un valore numerico maggiore di 0.");
            }
        } while (!validDuration);

        // Persona
        boolean validPerson = false;
        do {
            System.out.print("Inserisci nome persona: ");
            person = scanner.nextLine();
            if (person.matches("^[a-zA-Z ]+$") && !person.trim().isEmpty()) {
                validPerson = true;
            } else {
                System.out.println("Nome non valido. Assicurati di inserire solo lettere e spazi.");
            }
        } while (!validPerson);

        // Luogo
        boolean validLocation = false;
        do {
            System.out.print("Inserisci luogo appuntamento: ");
            location = scanner.nextLine();
            if (location.matches("^[a-zA-Z0-9 ]+$") && !location.trim().isEmpty()) {
                validLocation = true;
            } else {
                System.out.println("Luogo non valido. Assicurati di inserire solo lettere, numeri e spazi.");
            }
        } while (!validLocation);

        System.out.print("Confermi di voler inserire l'appuntamento con i seguenti dati: \n\tData: " + date
                + "\n\tOrario: " + time + "\n\tDurata: " + duration + " minuti\n\tPersona: " + person
                + "\n\tLuogo: " + location + "\n(s/n): ");
        String confirmData;

        try {
            do {
                confirmData = scanner.nextLine().trim();
                if ("s".equalsIgnoreCase(confirmData)) {
                    Boolean appointmentInserted = agenda.addAppointment(formattedDate, formattedTime, 
                            Integer.parseInt(duration), person, location);
                    if (appointmentInserted)
                        System.out.println("Appuntamento inserito con successo!");
                } else if ("n".equalsIgnoreCase(confirmData)) {
                    System.out.println("Inserimento annullato.");
                } else {
                    System.out.print("Per favore, inserisci 's' o 'n' come risposta: ");
                }
            } while (!"s".equalsIgnoreCase(confirmData) && !"n".equalsIgnoreCase(confirmData));
        } catch (Exception e) {
            System.err.print("Errore nell'inserimento dell'appuntamento: " + e.getMessage());
        }
    }

    /**
     * Modifica un appuntamento
     */
    private void editAppointment() {
        List<Appointment> appointments = agenda.getAppointments();
        if (appointments.isEmpty()) {
            System.out.println("Nessun appuntamento da modificare.");
            return;
        }

        for (int i = 0; i < appointments.size(); i++) {
            DateTimeFormatter editDateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            Appointment appointment = appointments.get(i);
            System.out.println((i + 1) + ". " + appointment.getDate().format(editDateFormatter) + " " + appointment.getTime()
            + " | Durata: " + appointment.getDuration() + " | Persona: " + appointment.getPerson() 
            + " | Luogo: " + appointment.getLocation());
        }

        int index;
        do {
            System.out.print("Inserisci il numero dell'appuntamento da modificare: ");
            index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index < 0 || index >= appointments.size()) {
                System.out.println("Selezione non valida.");
                break;
            }
        } while (index < 0 || index >= appointments.size());

        Appointment toEdit = appointments.get(index);

        try {
            System.out.print("Modifica data (lascia vuoto per non modificare, attuale: " + toEdit.getDate() + "): ");
            String newDate = scanner.nextLine();
            if (!newDate.isEmpty())
                toEdit.setDate(LocalDate.parse(newDate));

            System.out.print("Modifica orario (lascia vuoto per non modificare, attuale: " + toEdit.getTime() + "): ");
            String newTime = scanner.nextLine();
            if (!newTime.isEmpty())
                toEdit.setTime(LocalTime.parse(newTime));

            System.out.print("Modifica durata (lascia vuoto per non modificare, attuale: " + toEdit.getDuration() + "): ");
            String newDuration = scanner.nextLine();
            if (!newDuration.isEmpty())
                toEdit.setDuration(Integer.parseInt(newDuration));

            System.out.print("Modifica nome persona (lascia vuoto per non modificare, attuale: " + toEdit.getPerson() + "): ");
            String newPerson = scanner.nextLine();
            if (!newPerson.isEmpty() && newPerson.matches("^[a-zA-Z]*$"))
                toEdit.setPerson(newPerson);

            System.out.print("Modifica luogo (lascia vuoto per non modificare, attuale: " + toEdit.getLocation() + "): ");
            String newLocation = scanner.nextLine();
            if (!newLocation.isEmpty() && newLocation.matches("^[a-zA-Z0-9]*$"))
                toEdit.setLocation(newLocation);
        } catch (Exception e) {
            System.err.println("Errore nella modifica: " + e.getMessage());
        }

        System.out.println("Appuntamento modificato con successo!");
    }
}
