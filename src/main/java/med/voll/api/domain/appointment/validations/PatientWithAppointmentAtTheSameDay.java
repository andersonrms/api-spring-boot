package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidateException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientWithAppointmentAtTheSameDay implements ValidationAppointment{
    @Autowired
    private AppointmentRepository repository;

    @Override
    public void check(AppointmentDTO appointmentDTO) {
        var firstHour = appointmentDTO.date().withHour(7);
        var lastHour = appointmentDTO.date().withHour(18);
        var patient = repository.existsByPatientIdAndDateBetween(appointmentDTO.patientId(), firstHour, lastHour);

        if(patient){
            throw new ValidateException("Patient already appointment in this day");
        }
    }
}
