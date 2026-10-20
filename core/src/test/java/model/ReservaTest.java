package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    void crearReservaValida() {

        UUID id = UUID.randomUUID();
        String email = "cliente@gmail.com";
        LocalDate fecha = LocalDate.of(2026, 11, 10);

        Reserva reserva = Reserva.instance(
                id,
                email,
                fecha,
                2
        );

        assertNotNull(reserva);
        assertEquals(id, reserva.getId());
        assertEquals(email, reserva.getEmailContacto());
        assertEquals(fecha, reserva.getFecha());
        assertEquals(2, reserva.getCantidadPersonas());
        assertEquals(Reserva.Estado.ACTIVA, reserva.getEstado());
    }

    @Test
    void crearReservaConIdNuloLanzaExcepcion() {
        assertThrows(
                ReservaInvalidaException.class,
                () -> Reserva.instance(
                        null,
                        "cliente@gmail.com",
                        LocalDate.of(2026, 11, 10),
                        2
                )
        );
    }

    @Test
    void crearReservaConFechaNulaLanzaExcepcion() {
        assertThrows(
                ReservaInvalidaException.class,
                () -> Reserva.instance(
                        UUID.randomUUID(),
                        "cliente@gmail.com",
                        null,
                        2
                )
        );
    }



}