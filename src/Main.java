import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import managers.AgendaManager;
import models.Agenda;
import models.Appointment;

public class Main {
    public static void main(String[] args) {
        AgendaManager manager = new AgendaManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu principale:");
            System.out.println("1 - Crea agenda vuota");
            System.out.println("2 - Importa agenda da file");
            System.out.println("3 - Seleziona agenda");
            System.out.println("4 - Esci");
            System.out.print  (">> ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": 
                    // Crea agenda vuota
                    System.out.print("Inserisci nome agenda: ");
                    String name = scanner.nextLine();
                    try {
                        Agenda createdAgenda = manager.createAgenda(name);
                        if (createdAgenda != null)
                            System.out.println("Agenda creata con successo!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    // Importa agenda da file
                    System.out.print("Inserisci nome file: ");
                    String filename = scanner.nextLine();
                    try {
                        manager.loadAgendaFromFile(filename);
                        System.out.println("Agenda importata con successo!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    // Seleziona agenda
                    System.out.println("Agende disponibili:");
                    for (String agendaName : manager.listAgendas())
                        System.out.println(agendaName);

                    System.out.print("Inserisci nome agenda da selezionare: ");
                    String toSelect = scanner.nextLine();
                    Agenda selectedAgenda = manager.getAgendaByName(toSelect);
                    if (selectedAgenda != null) {
                        agendaSubMenu(scanner, selectedAgenda, manager);
                    } else {
                        System.out.println("Agenda non trovata.");
                    }
                    break;
                case "4": 
                    // Esci
                    System.out.println("Arrivederci");
                    scanner.close();
                    return;
                default:
                    System.out.println("Inserisci un'opzione valida");
                    break;
            }
        }
    }

    public static void agendaSubMenu(Scanner scanner, Agenda selectedAgenda, AgendaManager manager) {
        while (true) {
            System.out.println("Menu agenda '" + selectedAgenda.getName() + "':");
            System.out.println("1 - Elimina questa agenda");
            System.out.println("2 - Esporta questa agenda su file");
            System.out.println("3 - Inserisci nuovo appuntamento");
            System.out.println("4 - Modifica appuntamento");
            System.out.println("5 - Cerca appuntamenti");
            System.out.println("6 - Elenca appuntamenti (ordine alfabetico)");
            System.out.println("7 - Torna al menu principale");
            System.out.print  (">> ");
            String agendaSubChoice = scanner.nextLine();

            switch (agendaSubChoice) {
                case "1":
                    // Elimina questa agenda
                    System.out.print("Sei sicuro di voler eliminare l'agenda: " + selectedAgenda.getName() + "? (y/n): ");
                    String confirmDelete;

                    do {
                        confirmDelete = scanner.nextLine().trim();
                        if ("y".equalsIgnoreCase(confirmDelete)) {
                            manager.deleteAgenda(selectedAgenda.getName());
                            System.out.println("Agenda eliminata con successo!");
                            return;
                        } else if ("n".equalsIgnoreCase(confirmDelete)) {
                            System.out.println("Eliminazione annullata.");
                            return;
                        } else {
                            System.out.print("Per favore, inserisci 'y' o 'n' come risposta: ");
                        }
                    } while (!"y".equalsIgnoreCase(confirmDelete) && !"n".equalsIgnoreCase(confirmDelete));
                    
                    break;
                case "2":
                    // Esporta questa agenda su file
                    System.out.print("Inserisci nome file per esportare: ");
                    String filename = scanner.nextLine();
                    try {
                        manager.writeAgendaToFile(selectedAgenda.getName(), filename);
                        System.out.println("Agenda esportata con successo!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    // Inserisci nuovo appuntamento
                    System.out.print("Inserisci data appuntamento: ");
                    String date = scanner.nextLine();
                    System.out.print("Inserisci orario appuntamento: ");
                    String time = scanner.nextLine();
                    System.out.print("Inserisci durata appuntamento: ");
                    String duration = scanner.nextLine();
                    System.out.print("Inserisci nome persona: ");
                    String person = scanner.nextLine();
                    System.out.print("Inserisci luogo appuntamento: ");
                    String location = scanner.nextLine();

                    System.out.print("Confermi di voler inserire l'appuntamento con i seguenti dati: \n\tData: " + date + "\n\tOrario: " + time + "\n\tDurata: " + duration + "\n\tPersona: " + person + "\n\tLuogo: " + location + "\n(y/n): ");
                    String confirmData;

                    do {
                        confirmData = scanner.nextLine().trim();
                        if ("y".equalsIgnoreCase(confirmData)) {
                            Boolean appointmentInserted = selectedAgenda.addAppointment(LocalDate.parse(date), LocalTime.parse(time), Integer.parseInt(duration), person, location);
                            if (appointmentInserted)
                                System.out.println("Appuntamento inserito con successo!");
                            return;
                        } else if ("n".equalsIgnoreCase(confirmData)) {
                            System.out.println("Inserimento annullato.");
                            return;
                        } else {
                            System.out.print("Per favore, inserisci 'y' o 'n' come risposta: ");
                        }
                    } while (!"y".equalsIgnoreCase(confirmData) && !"n".equalsIgnoreCase(confirmData));

                    break;
                case "4":
                    // Modifica appuntamento
                    List<Appointment> appointments = selectedAgenda.getAppointments();
                    
                    if (appointments.isEmpty()) {
                        System.out.println("Nessun appuntamento da modificare.");
                        break;
                    }
                    
                    for (int i = 0; i < appointments.size(); i++) {
                        System.out.println((i + 1) + ". " + appointments.get(i));
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
                        toEdit.setDate(LocalDate.parse(newDate));

                        System.out.print("Modifica orario (lascia vuoto per non modificare, attuale: " + toEdit.getTime() + "): ");
                        String newTime = scanner.nextLine();
                        toEdit.setTime(LocalTime.parse(newTime));

                        System.out.print("Modifica durata (lascia vuoto per non modificare, attuale: " + toEdit.getDuration() + "): ");
                        String newDuration = scanner.nextLine();
                        toEdit.setDuration(Integer.parseInt(newDuration));

                        System.out.print("Modifica nome persona (lascia vuoto per non modificare, attuale: " + toEdit.getPerson() + "): ");
                        String newPerson = scanner.nextLine();
                        if (newPerson.matches("^[a-zA-Z]*$") || newPerson.isEmpty())
                            toEdit.setPerson(newPerson);

                        System.out.print("Modifica luogo (lascia vuoto per non modificare, attuale: " + toEdit.getLocation() + "): ");
                        String newLocation = scanner.nextLine();
                        if (newLocation.matches("^[a-zA-Z0-9]*$"))
                            toEdit.setLocation(newLocation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("Appuntamento modificato con successo!");
                    break;
                case "5":
                    // Cerca appuntamenti
                    System.out.println("Menu ricerca appuntamenti:");
                    System.out.println("1 - Per nome persona");
                    System.out.println("2 - Per data");
                    System.out.println("3 - Per nome persona e data");
                    System.out.println("4 - Torna al menu agende");
                    System.out.print  (">> ");
                    String searchType = scanner.nextLine();

                    switch (searchType) {
                        case "1":
                            System.out.print("Inserisci nome persona: ");
                            String searchName = scanner.nextLine();
                            if (!searchName.matches("^[a-zA-Z]*$") || searchName.isEmpty()) {
                                System.out.println("Nome non valido.");
                                break;
                            }

                            selectedAgenda.filterAppointments(searchName, null);
                            break;
                        case "2":
                            System.out.print("Inserisci data: ");
                            String searchDate = scanner.nextLine();
                            if (!searchDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                                System.out.println("Data non valida.");
                                break;
                            }

                            selectedAgenda.filterAppointments(null, LocalDate.parse(searchDate));
                            break;
                        case "3":
                            System.out.print("Inserisci nome persona: ");
                            searchName = scanner.nextLine();
                            if (!searchName.matches("^[a-zA-Z]*$") || searchName.isEmpty()) {
                                System.out.println("Nome non valido.");
                                break;
                            }

                            System.out.print("Inserisci data: ");
                            searchDate = scanner.nextLine();
                            if (!searchDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                                System.out.println("Data non valida.");
                                break;
                            }

                            selectedAgenda.filterAppointments(searchName, LocalDate.parse(searchDate));
                            break;
                        case "4":
                            return;
                        default:
                            System.out.println("Inserisci un'opzione valida");
                            break;
                    }
                    break;
                case "6":
                    // Elenca appuntamenti (ordine alfabetico)
                    System.out.println("Elenco appuntamenti: ");
                    selectedAgenda.listAppointments();
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
}