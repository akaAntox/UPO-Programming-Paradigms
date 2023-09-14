package models;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment implements Comparable<Appointment> {
    private LocalDate date; // data dell’appuntamento, nel formato gg-mm-aaaa
    private LocalTime time; // orario dell’appuntamento, nel formato hh-mm
    private int duration; // durata prevista espressa in minuti
    private String person; // nome della persona con cui si ha l’appuntamento
    private String location; // luogo in cui si terrà l’appuntamento

    /**
     * Costruttore
     * @param date
     * @param time
     * @param duration
     * @param person
     * @param location
     */
    public Appointment(LocalDate date, LocalTime time, int duration, String person, String location) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.person = person;
        this.location = location;
    }
    
    /**
     * Confronta due appuntamenti per data e ora
     * @param  otherAppointment l'appuntamento con cui eseguire il confronto
     * @return -1 se l'appuntamento è antecedente,
     *         1 se l'appuntamento è successivo,
     *         0 se gli appuntamenti sono uguali
     */
    @Override
    public int compareTo(Appointment otherAppointment) {
        if (this.date.isBefore(otherAppointment.date)) {
            return -1;
        } else if (this.date.isAfter(otherAppointment.date)) {
            return 1;
        } else {
            if (this.time.isBefore(otherAppointment.time)) {
                return -1;
            } else if (this.time.isAfter(otherAppointment.time)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Restituisce la data
     * @return data
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Modifica la data
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Restituisce l'orario
     * @return ora
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Modifica l'orario
     * @param time
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Restituisce la durata
     * @return durata
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Modifica la durata
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Restituisce il nome
     * @return nome
     */
    public String getPerson() {
        return person;
    }

    /**
     * Modifica il nome
     * @param person
     */
    public void setPerson(String person) {
        this.person = person;
    }

    /**
     * Restituisce il luogo
     * @return luogo
     */
    public String getLocation() {
        return location;
    }

    /**
     * Modifica il luogo
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
