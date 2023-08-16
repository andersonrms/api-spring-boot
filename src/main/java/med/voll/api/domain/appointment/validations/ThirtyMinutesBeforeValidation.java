package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidateException;
import med.voll.api.domain.appointment.AppointmentDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ThirtyMinutesBeforeValidation implements ValidationAppointment{
    public void check(AppointmentDTO appointmentDTO){
        var appointmentDate = appointmentDTO.date();
        var differenceInMinutes = Duration.between(LocalDateTime.now(), appointmentDate).toMinutes();

        if (differenceInMinutes < 30){
            throw new ValidateException("Appointment has schedule with 30 minutes precedence");
        }
    }
}
