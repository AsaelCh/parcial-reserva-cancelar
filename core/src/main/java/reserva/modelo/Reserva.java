
package reserva.modelo;

import reserva.exception.ReservaInvalidaException;

import java.time.LocalDate;
import java.util.UUID;

public class Reserva {

    public enum Estado {
        ACTIVA,
        CANCELADA
    }

    private UUID id;
    private String emailContacto;
    private LocalDate fecha;
    private int cantidadPersonas;
    private Estado estado;

    private Reserva(UUID id, String emailContacto, LocalDate fecha,
                    int cantidadPersonas) {
        this.id = id;
        this.emailContacto = emailContacto;
        this.fecha = fecha;
        this.cantidadPersonas = cantidadPersonas;
        this.estado = Estado.ACTIVA;
    }

    public static Reserva instance(UUID id, String emailContacto,
                                   LocalDate fecha, int cantidadPersonas) {

        if (id == null) {
            throw new ReservaInvalidaException("El id no puede ser nulo");
        }

        if (fecha == null) {
            throw new ReservaInvalidaException("La fecha no puede ser nula");
        }

        if (emailContacto == null || emailContacto.isBlank()) {
            throw new ReservaInvalidaException("El email no puede estar vacio");
        }

        if (cantidadPersonas <= 0) {
            throw new ReservaInvalidaException(
                    "La cantidad de personas debe ser positiva");
        }

        return new Reserva(id, emailContacto, fecha, cantidadPersonas);
    }

    public UUID getId() {
        return id;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public Estado getEstado() {
        return estado;
    }
    public void cancelar() {
        if (this.estado == Estado.CANCELADA) {
            throw new reserva.exception.ReservaYaCanceladaException(
                    "La reserva ya esta cancelada"
            );
        }

        this.estado = Estado.CANCELADA;
    }
}
