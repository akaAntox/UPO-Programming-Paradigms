package managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Set;

import models.Agenda;
import models.Appointment;

public class AgendaManager {
    private HashMap<String, Agenda> agendas;

    public AgendaManager() {
        agendas = new HashMap<>();
    }

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

    public Agenda deleteAgenda(String name) {
        if (name == null)
            throw new IllegalArgumentException("Nome non valido");

        if (!agendas.containsKey(name))
            throw new IllegalArgumentException("Agenda non trovata");

        return agendas.remove(name);
    }

    public void loadAgendaFromFile(String filename) throws IOException {
        if (filename == null)
            throw new IllegalArgumentException("Nome file non valido");

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 6)
                    throw new IllegalArgumentException("Formato file non corretto");

                String agendaName = fields[0];
                Agenda agenda = this.createAgenda(agendaName);
                if (agenda != null) {
                    Boolean agendaInserted = agenda.addAppointment(LocalDate.parse(fields[1]), LocalTime.parse(fields[2]), Integer.parseInt(fields[3]), fields[4], fields[5]);
                    if (agendaInserted)
                        System.out.println("Appuntamento con " + fields[4] + " importato nell'agenda " + agenda.getName());
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void writeAgendaToFile(String name, String filename) throws IOException {
        if (name == null || filename == null)
            throw new IllegalArgumentException("Nome o nome file non valido");

        Agenda agendaToWrite = agendas.get(name);
        if (agendaToWrite == null)
            throw new IllegalArgumentException("Agenda non trovata");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Appointment appt : agendaToWrite.getAppointments()) {
                writer.write(String.format("%s,%s,%s,%d,%s,%s%n",
                        name, appt.getDate(), appt.getTime(), appt.getDuration(), appt.getPerson(), appt.getLocation()));
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public Set<String> listAgendas() {
        return agendas.keySet();
    }

    public Agenda getAgendaByName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Nome non valido");

        return agendas.get(name);
    }
}
