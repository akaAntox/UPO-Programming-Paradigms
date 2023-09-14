package tests;

import models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment appointment3;

    @BeforeEach
    public void setUp() {
        appointment1 = new Appointment(LocalDate.of(2023, 1, 10), LocalTime.of(10, 0), 60, "Mario", "Ufficio");
        appointment2 = new Appointment(LocalDate.of(2023, 1, 10), LocalTime.of(12, 0), 30, "Luca", "Bar");
        appointment3 = new Appointment(LocalDate.of(2023, 1, 9), LocalTime.of(12, 0), 30, "Anna", "Casa");
    }

    @Test
    public void testCompareTo() {
        assertTrue(appointment1.compareTo(appointment2) < 0);
        assertTrue(appointment2.compareTo(appointment1) > 0);
        assertTrue(appointment1.compareTo(appointment1) == 0);
        assertTrue(appointment3.compareTo(appointment1) < 0);
    }

    @Test
    public void testGetSetDate() {
        LocalDate newDate = LocalDate.of(2023, 5, 5);
        appointment1.setDate(newDate);
        assertEquals(newDate, appointment1.getDate());
    }

    @Test
    public void testGetSetTime() {
        LocalTime newTime = LocalTime.of(15, 0);
        appointment1.setTime(newTime);
        assertEquals(newTime, appointment1.getTime());
    }

    @Test
    public void testGetSetDuration() {
        int newDuration = 90;
        appointment1.setDuration(newDuration);
        assertEquals(newDuration, appointment1.getDuration());
    }

    @Test
    public void testGetSetPerson() {
        String newPerson = "Elena";
        appointment1.setPerson(newPerson);
        assertEquals(newPerson, appointment1.getPerson());
    }

    @Test
    public void testGetSetLocation() {
        String newLocation = "Palestra";
        appointment1.setLocation(newLocation);
        assertEquals(newLocation, appointment1.getLocation());
    }
}
