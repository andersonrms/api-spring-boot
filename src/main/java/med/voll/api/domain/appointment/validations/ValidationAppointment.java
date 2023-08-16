package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentDTO;

public interface ValidationAppointment {

    void check(AppointmentDTO appointmentDTO);
}
