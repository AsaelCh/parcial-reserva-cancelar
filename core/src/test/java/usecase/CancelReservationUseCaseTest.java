package usecase;

import org.junit.jupiter.api.Test;
import reserva.exception.PersistenceException;
import reserva.exception.ReservaYaCanceladaException;
import reserva.exception.ReservationNotFoundException;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;
import reserva.usecase.CancelReservationUseCase;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CancelReservationUseCaseTest {

    @Test
    void cancelarReservaExistente() {
        UUID id = UUID.randomUUID();

        Reserva reserva = Reserva.instance(
                id,
                "cliente@gmail.com",
                LocalDate.of(2026, 11, 10),
                2
        );

        ReservationRepository repository =
                mock(ReservationRepository.class);

        when(repository.buscarPorId(id))
                .thenReturn(Optional.of(reserva));

        when(repository.guardar(reserva))
                .thenReturn(true);

        CancelReservationUseCase useCase =
                new CancelReservationUseCase(repository);

        Reserva resultado = useCase.execute(id);

        assertEquals(Reserva.Estado.CANCELADA, resultado.getEstado());
        verify(repository).buscarPorId(id);
        verify(repository).guardar(reserva);
    }

    @Test
    void cancelarReservaInexistenteLanzaExcepcion() {
        UUID id = UUID.randomUUID();

        ReservationRepository repository =
                mock(ReservationRepository.class);

        when(repository.buscarPorId(id))
                .thenReturn(Optional.empty());

        CancelReservationUseCase useCase =
                new CancelReservationUseCase(repository);

        assertThrows(
                ReservationNotFoundException.class,
                () -> useCase.execute(id)
        );

        verify(repository).buscarPorId(id);
        verify(repository, never()).guardar(any());
    }

    @Test
    void cancelarReservaYaCanceladaLanzaExcepcion() {
        UUID id = UUID.randomUUID();

        Reserva reserva = Reserva.instance(
                id,
                "cliente@gmail.com",
                LocalDate.of(2026, 11, 10),
                2
        );

        // Primero cancelamos la reserva para preparar el escenario.
        reserva.cancelar();

        ReservationRepository repository =
                mock(ReservationRepository.class);

        when(repository.buscarPorId(id))
                .thenReturn(Optional.of(reserva));

        CancelReservationUseCase useCase =
                new CancelReservationUseCase(repository);

        assertThrows(
                ReservaYaCanceladaException.class,
                () -> useCase.execute(id)
        );

        verify(repository).buscarPorId(id);
        verify(repository, never()).guardar(any());
    }


    @Test
    void cancelarReservaConErrorAlGuardarLanzaExcepcion() {
        UUID id = UUID.randomUUID();

        Reserva reserva = Reserva.instance(
                id,
                "cliente@gmail.com",
                LocalDate.of(2026, 11, 10),
                2
        );

        ReservationRepository repository =
                mock(ReservationRepository.class);

        when(repository.buscarPorId(id))
                .thenReturn(Optional.of(reserva));

        when(repository.guardar(reserva))
                .thenReturn(false);

        CancelReservationUseCase useCase =
                new CancelReservationUseCase(repository);

        assertThrows(
                PersistenceException.class,
                () -> useCase.execute(id)
        );

        verify(repository).buscarPorId(id);
        verify(repository).guardar(reserva);
    }


}