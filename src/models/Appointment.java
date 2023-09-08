package models;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment implements Comparable<Appointment> {
    private LocalDate date; // data dell’appuntamento, nel formato gg-mm-aaaa
    private LocalTime time; // orario dell’appuntamento, nel formato hh-mm
    private int duration; // durata prevista espressa in minuti
    private String person; // nome della persona con cui si ha l’appuntamento
    private String location; // luogo in cui si terrà l’appuntamento

    public Appointment(LocalDate date, LocalTime time, int duration, String person, String location) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.person = person;
        this.location = location;
    }
        
    @Override
    public int compareTo(Appointment other) {
        if (this.date.isBefore(other.date)) {
            return -1;
        } else if (this.date.isAfter(other.date)) {
            return 1;
        } else {
            // le date sono uguali, confrontiamo gli orari
            if (this.time.isBefore(other.time)) {
                return -1;
            } else if (this.time.isAfter(other.time)) {
                return 1;
            } else {
                return 0; // data e ora sono entrambi uguali
            }
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
