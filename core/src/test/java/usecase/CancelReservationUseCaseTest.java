package usecase;

import org.junit.jupiter.api.Test;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

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
}