package tests;

import models.Agenda;
import managers.AgendaManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendaManagerTest {

    private AgendaManager manager;

    @BeforeEach
    void setUp() {
        manager = new AgendaManager();
    }

    @Test
    void testCreateAgenda() {
        String name = "miaAgenda123";
        Agenda agenda = manager.createAgenda(name);

        assertNotNull(agenda);
        assertEquals(name, agenda.getName());
        assertTrue(manager.listAgendas().contains(name));
    }

    @Test
    void testCreateDuplicateAgenda() {
        String name = "miaAgenda123";
        manager.createAgenda(name);
        assertNull(manager.createAgenda(name));
    }

    @Test
    void testDeleteAgenda() {
        String name = "miaAgenda123";
        manager.createAgenda(name);
        Agenda deleted = manager.deleteAgenda(name);

        assertNotNull(deleted);
        assertFalse(manager.listAgendas().contains(name));
    }

    @Test
    void testDeleteNonExistentAgenda() {
        String name = "miaAgenda123";
        assertThrows(IllegalArgumentException.class, () -> manager.deleteAgenda(name));
    }

    @Test
    void testListAgendas() {
        String name1 = "Agenda1";
        String name2 = "Agenda2";
        manager.createAgenda(name1);
        manager.createAgenda(name2);

        assertTrue(manager.listAgendas().contains(name1));
        assertTrue(manager.listAgendas().contains(name2));
    }

    @Test
    void testGetAgendaByName() {
        String name = "miaAgenda123";
        Agenda agenda = manager.createAgenda(name);

        Agenda fetched = manager.getAgendaByName(name);
        assertNotNull(fetched);
        assertEquals(agenda, fetched);
    }
}
