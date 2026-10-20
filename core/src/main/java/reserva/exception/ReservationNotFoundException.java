
package reserva.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String mensaje) {
        super(mensaje);
    }
}
