package managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Set;

import models.Agenda;
import models.Appointment;

public class AgendaManager {
    private HashMap<String, Agenda> agendas;

    /**
     * Costruttore
     */
    public AgendaManager() {
        agendas = new HashMap<>();
    }

    /**
     * Crea una nuova agenda
     * @param name
     * @return agenda creata
     */
    public Agenda createAgenda(String name) {
        if (name == null)
            throw new IllegalArgumentException("Nome non valido");

        if (agendas.containsKey(name)) {
            System.out.println("Un'agenda con questo nome esiste già.");
            return null;
        }

        Agenda newAgenda = new Agenda(name);
        agendas.put(name, newAgenda);
        return newAgenda;
    }

    /**
     * Elimina un'agenda
     * @param name
     * @return il valore precedente associato al nome
     */
    public Agenda deleteAgenda(String name) {
        if (name == null)
            throw new IllegalArgumentException("Nome non valido");

        if (!agendas.containsKey(name))
            throw new IllegalArgumentException("Agenda non trovata");

        return agendas.remove(name);
    }

    /**
     * Carica un'agenda da file
     * @param filename
     * @throws IOException
     */
    public void loadAgendaFromFile(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty())
            throw new IllegalArgumentException("Nome file non valido");

        Path path = Paths.get("exported\\" + filename + ".csv");        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 6)
                    throw new IllegalArgumentException("Formato file non corretto");
    
                String agendaName = fields[0].trim();
                if (agendaName.isEmpty()) 
                    throw new IllegalArgumentException("Nome agenda non presente");
                
                Agenda agenda = agendas.get(agendaName);

                if (agenda == null) {
                    agenda = this.createAgenda(agendaName);
                    agendas.put(agendaName, agenda);
                }
                
                if (agenda != null) {
                    LocalDate date;
                    LocalTime time;
                    int duration;
                    
                    try {
                        date = LocalDate.parse(fields[1]);
                        time = LocalTime.parse(fields[2]);
                        duration = Integer.parseInt(fields[3]);
                    } catch (DateTimeParseException | NumberFormatException e) {
                        throw new IllegalArgumentException("Formato non valido per data/ora/durata nel file");
                    }

                    if (fields[4].trim().isEmpty() || fields[5].trim().isEmpty())
                        throw new IllegalArgumentException("Nome appuntamento o luogo non valido nel file");

                    Boolean agendaInserted = agenda.addAppointment(date, time, duration, fields[4], fields[5]);
                    if (agendaInserted)
                        System.out.println("Appuntamento con " + fields[4] + " importato nell'agenda " + agenda.getName());
                }
            }
        } catch (IOException e) {
            throw new IOException("Errore durante la lettura del file", e);
        }
    }

    /**
     * Salva un'agenda su file
     * @param name
     * @param filename
     * @throws IOException
     */
    public void writeAgendaToFile(String name, String filename) throws IOException {
        if (name == null || filename == null)
            throw new IllegalArgumentException("Nome o nome file non valido");

        Agenda agendaToWrite = agendas.get(name);
        if (agendaToWrite == null)
            throw new IllegalArgumentException("Agenda non trovata");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(".\\exported\\" + filename + ".csv"))) {
            for (Appointment appt : agendaToWrite.getAppointments()) {
                writer.write(String.format("%s,%s,%s,%d,%s,%s%n",
                        name, appt.getDate(), appt.getTime(), appt.getDuration(), appt.getPerson(), appt.getLocation()));
            }
        } catch (IOException e) {
            throw new IOException("Errore durante la scrittura del file", e);
        }
    }

    /**
     * Restituisce tutte le agende
     * @return Set di agende
     */
    public Set<String> listAgendas() {
        return agendas.keySet();
    }

    /**
     * Restituisce un'agenda
     * @param name
     * @return agenda trovata
     */
    public Agenda getAgendaByName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Nome non valido");

        return agendas.get(name);
    }
}
