package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidateException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.patiente.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientValidation implements ValidationAppointment{
    @Autowired
    private PatientRepository repository;

    public void check(AppointmentDTO appointmentDTO){
        var patient = repository.findActiveById(appointmentDTO.patientId());

        if(!patient) throw new ValidateException("Appointment cannot be scheduled because doctor is inactive");
    }
}