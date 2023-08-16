package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidateException;
import med.voll.api.domain.appointment.AppointmentDTO;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class OpeningHoursValidation implements ValidationAppointment{
    public void check(AppointmentDTO appointmentDTO) {
        var appointmentDate = appointmentDTO.date();

        var isSunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var isBeforeOpeningHours = appointmentDate.getHour() < 7;
        var isAfterOpeningHours = appointmentDate.getHour() > 18;

        if(isSunday || isBeforeOpeningHours || isAfterOpeningHours) {
            throw new ValidateException("Appointment outside opening hours");
        }
    }
}
