
package reserva.usecase;

import reserva.exception.PersistenceException;
import reserva.exception.ReservationNotFoundException;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

import java.util.UUID;

public class CancelReservationUseCase {

    private ReservationRepository repository;

    public CancelReservationUseCase(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reserva execute(UUID id) {
        Reserva reserva = repository.buscarPorId(id)
                .orElseThrow(() ->
                        new ReservationNotFoundException(
                                "No se encontro la reserva"
                        )
                );

        reserva.cancelar();

        boolean guardado = repository.guardar(reserva);

        if (!guardado) {
            throw new PersistenceException(
                    "No se pudo guardar la reserva cancelada"
            );
        }

        return reserva;
    }
}
