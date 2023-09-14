package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private String name; // nome agenda
    private List<Appointment> appointments; // lista di appuntamenti

    /**
     * Costruttore
     * @param name
     */
    public Agenda(String name) {
        this.name = name;
        this.appointments = new ArrayList<>();
    }

    /**
     * Aggiunge un appuntamento
     * @param date
     * @param time
     * @param duration
     * @param name
     * @param location
     * @return successo o fallimento
     */
    public Boolean addAppointment(LocalDate date, LocalTime time, Integer duration, String name, String location) {
        if (date == null || time == null || duration <= 0 || name == null || location == null 
                || !name.matches("^[a-zA-Z ]+$") || !location.matches("^[a-zA-Z0-9 ]+$"))
            throw new IllegalArgumentException("Parametri non validi");

        LocalTime newEndTime = time.plusMinutes(duration);

        for (Appointment appointment : appointments) {
            if (date.equals(appointment.getDate())) {
                LocalTime startTime = appointment.getTime();
                LocalTime endTime = startTime.plusMinutes(appointment.getDuration());

                if ((time.equals(startTime) || time.isAfter(startTime)) && time.isBefore(endTime)
                        || (newEndTime.isAfter(startTime) && newEndTime.isBefore(endTime) || newEndTime.equals(startTime))) {
                    System.out.println("Hai già un appuntamento a quell'ora.");
                    return false;
                }
            }
        }
        
        Appointment newAppointment = new Appointment(date, time, duration, name, location);
        appointments.add(newAppointment);
        return true;
    }

    /**
     * Rimuove un appuntamento
     * @param appointment
     */
    public void removeAppointment(Appointment appointment) {
        if (appointment == null)
            throw new IllegalArgumentException("Appuntamento non valido");

        appointments.remove(appointment);
    }

    /**
     * Modifica un appuntamento
     * @param oldAppointment
     * @param newAppointment
     */
    public void modifyAppointment(Appointment oldAppointment, Appointment newAppointment) {
        if (oldAppointment == null || newAppointment == null)
            throw new IllegalArgumentException("Parametri non validi");

        int index = appointments.indexOf(oldAppointment);
        if (index != -1) {
            appointments.set(index, newAppointment);
        }
    }

    /**
     * Controlla se un appuntamento è presente
     * @param appointment
     * @return true se presente, false altrimenti
     */
    public Boolean containsAppointment(Appointment appointment) {
        if (appointment == null)
            throw new IllegalArgumentException("Appuntamento non valido");

        return appointments.contains(appointment);
    }

    /**
     * Restituisce l'appuntamento nella posizione indicata
     * @param index
     * @return appuntamento
     */
    public Appointment getAppointment(int index) {
        if (index < 0 || index >= appointments.size())
            throw new IllegalArgumentException("Indice non valido");

        return appointments.get(index);
    }

    /**
     * Filtra gli appuntamenti per persona e/o data
     * @param person
     * @param date
     * @return lista di appuntamenti
     */
    public List<Appointment> filterAppointments(String person, LocalDate date) {
        List<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            boolean isDateMatch = (date == null || appointment.getDate().equals(date));
            boolean isPersonMatch = (person == null || appointment.getPerson().contains(person));
            if (isDateMatch && isPersonMatch) {
                filteredAppointments.add(appointment);
            }
        }
    
        return filteredAppointments;
    }
    
    /**
     * Ordina gli appuntamenti in ordine alfabetico
     * @return lista di appuntamenti ordinata
     */
    public List<Appointment> sortAppointments() {
        appointments.sort((a1, a2) -> a1.getPerson().compareTo(a2.getPerson()));
        return appointments;
    }

    /**
     * Stampa tutti gli appuntamenti
     * @param appointments
     */
    public void listAppointments() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        appointments.sort((a1, a2) -> a1.getPerson().compareTo(a2.getPerson()));
        
        for (Appointment appointment : appointments) {
            System.out.println("- " + appointment.getDate().format(dateFormatter) + " " + appointment.getTime()
                    + " | Durata: " + appointment.getDuration() + " | Persona: " + appointment.getPerson() 
                    + " | Luogo: " + appointment.getLocation());
        }
    }

    /**
     * Stampa gli appuntamenti specificando gli appuntamenti da stampare
     * @param appointmentsToList
     */
    public void listAppointments(List<Appointment> appointmentsToList) {
        if (appointmentsToList == null || appointmentsToList.isEmpty())
            throw new IllegalArgumentException("Parametro non valido");
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Appointment appointment : appointmentsToList) {
            System.out.println("- " + appointment.getDate().format(dateFormatter) + " " + appointment.getTime()
                    + " | Durata: " + appointment.getDuration() + " | Persona: " + appointment.getPerson() 
                    + " | Luogo: " + appointment.getLocation());
        }
    }

    /**
     * Restituisce gli appuntamenti
     * @return lista di appuntamenti
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Imposta gli appuntamenti
     * @param appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        if (appointments == null)
            throw new IllegalArgumentException("Parametro non valido");

        this.appointments = appointments;
    }

    /**
     * Restituisce il nome
     * @return nome dell'agenda
     */
    public String getName() {
        return name;
    }
}
