package tests;

import models.Agenda;
import models.Appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaTest {
    private Agenda agenda;

    @BeforeEach
    public void setUp() {
        agenda = new Agenda("TestAgenda");
    }

    @Test
    public void testAddValidAppointment() {
        assertTrue(agenda.addAppointment(LocalDate.now(), LocalTime.of(15, 0), 60, "Anton Iliev", "Casa"));
    }

    @Test
    public void testAddOverlappingAppointment() {
        agenda.addAppointment(LocalDate.now(), LocalTime.of(15, 0), 60, "Anton Iliev", "Casa");
        assertFalse(agenda.addAppointment(LocalDate.now(), LocalTime.of(15, 30), 60, "Giorgio Pietruzzi", "Ufficio1"));
    }

    @Test
    public void testRemoveAppointment() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(15, 0), 60, "Anton Iliev", "Casa");
        agenda.addAppointment(LocalDate.now(), LocalTime.of(15, 0), 60, "Anton Iliev", "Casa");
        agenda.removeAppointment(appt);
        assertFalse(agenda.containsAppointment(appt));
    }

    @Test
    public void testModifyAppointment() {
        agenda.addAppointment(LocalDate.now(), LocalTime.of(15, 0), 60, "Anton Iliev", "Casa");
        Appointment oldAppt = agenda.getAppointment(0);
        Appointment newAppt = new Appointment(LocalDate.now(), LocalTime.of(16, 0), 60, "Giorgio Pietruzzi", "Ufficio1");
        agenda.modifyAppointment(oldAppt, newAppt);
        assertTrue(agenda.containsAppointment(newAppt));
        assertFalse(agenda.containsAppointment(oldAppt));
    }

    @Test
    public void testFilterAppointmentsByPerson() {
        agenda.addAppointment(LocalDate.now(), LocalTime.of(15, 0), 60, "Anton Iliev", "Casa");
        List<Appointment> result = agenda.filterAppointments("Anton Iliev", null);
        assertEquals(1, result.size());
        assertEquals("Anton Iliev", result.get(0).getPerson());
    }

    @Test
    public void testFilterAppointmentsByDate() {
        LocalDate actualTime = LocalDate.now();
        agenda.addAppointment(actualTime, LocalTime.of(15, 0), 60, "Anton Iliev", "Casa");
        List<Appointment> result = agenda.filterAppointments(null, actualTime);
        assertEquals(1, result.size());
        assertEquals(LocalDate.now(), result.get(0).getDate());
    }

    @Test
    public void testSortAppointments() {
        LocalDate actualTime = LocalDate.now();
        agenda.addAppointment(actualTime, LocalTime.of(15, 0), 60, "Giorgio Pietruzzi", "Ufficio1");
        agenda.addAppointment(actualTime.plusDays(1), LocalTime.of(15, 0), 60, "Anton Iliev", "Casa");
        List<Appointment> sorted = agenda.sortAppointments();
        assertEquals("Anton Iliev", sorted.get(0).getPerson());
        assertEquals("Giorgio Pietruzzi", sorted.get(1).getPerson());
    }
}
