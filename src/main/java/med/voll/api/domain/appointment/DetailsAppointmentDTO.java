package med.voll.api.domain.appointment;

import java.time.LocalDateTime;

public record DetailsAppointmentDTO(
        Long id,
        Long doctorId,
        Long patientId,
        LocalDateTime date) {
    public DetailsAppointmentDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getDate());
    }
}
