package med.voll.api.domain.appointment;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.enuns.Specialty;

import java.time.LocalDateTime;

public record AppointmentDTO(
        @JsonAlias("doctor_id") Long doctorId,
        @NotNull
        @JsonAlias("patient_id") Long patientId,

        @NotNull
        @Future
        LocalDateTime date,

        Specialty specialty) {
}
