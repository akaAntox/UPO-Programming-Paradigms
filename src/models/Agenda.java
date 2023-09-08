package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agenda {
    private String name;
    private List<Appointment> appointments;

    public Agenda(String name) {
        this.name = name;
        this.appointments = new ArrayList<>();
    }

    public Boolean addAppointment(LocalDate date, LocalTime time, int duration, String name, String location) {
        if (date == null || time == null || duration <= 0 || name == null || location == null 
            || name.matches("[a-zA-Z]+") || location.matches("^[a-zA-Z0-9]*$"))
            throw new IllegalArgumentException("Parametri non validi");

        try {
            Appointment newAppointment = new Appointment(date, time, duration, name, location);
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
    
            appointments.add(newAppointment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }

    public void removeAppointment(Appointment appointment) {
        if (appointment == null)
            throw new IllegalArgumentException("Appuntamento non valido");

        appointments.remove(appointment);
    }

    public void modifyAppointment(Appointment oldAppointment, Appointment newAppointment) {
        if (oldAppointment == null || newAppointment == null)
            throw new IllegalArgumentException("Parametri non validi");

        int index = appointments.indexOf(oldAppointment);
        if (index != -1) {
            appointments.set(index, newAppointment);
        }
    }

    public Boolean containsAppointment(Appointment appointment) {
        if (appointment == null)
            throw new IllegalArgumentException("Appuntamento non valido");

        return appointments.contains(appointment);
    }

    public Appointment getAppointment(int index) {
        if (index < 0 || index >= appointments.size())
            throw new IllegalArgumentException("Indice non valido");

        return appointments.get(index);
    }

    public List<Appointment> filterAppointments(String person, LocalDate date) {
        List<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPerson().equals(person) && appointment.getDate().equals(date) ||
                    appointment.getPerson().equals(person) && date == null ||
                    appointment.getDate().equals(date) && person == null) {
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }

    public List<Appointment> sortAppointments() {
        Collections.sort(appointments);
        return appointments;
    }

    public void listAppointments() {
        for (Appointment appointment : appointments) {
            System.out.println("\t" + appointment);
        }
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        if (appointments == null)
            throw new IllegalArgumentException("Parametro non valido");

        this.appointments = appointments;
    }

    public String getName() {
        return name;
    }
}
