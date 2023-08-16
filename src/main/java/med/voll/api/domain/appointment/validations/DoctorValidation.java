package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidateException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorValidation implements ValidationAppointment{
    @Autowired
    private DoctorRepository repository;

    public void check(AppointmentDTO appointmentDTO){
        if(appointmentDTO.doctorId() == null) return;

        var doctor = repository.findActiveById(appointmentDTO.doctorId());

        if(!doctor) throw new ValidateException("Appointment cannot be scheduled because doctor is inactive");
    }
}
