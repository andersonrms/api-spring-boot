package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidateException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorAttending implements ValidationAppointment{
    @Autowired
    private AppointmentRepository repository;

    public void check(AppointmentDTO appointmentDTO){
        var doctor = repository.existsByDoctorIdAndDate(appointmentDTO.doctorId(), appointmentDTO.date());

        if(doctor) throw new ValidateException("Doctor has appointment scheduled in this hour");
    }
}
