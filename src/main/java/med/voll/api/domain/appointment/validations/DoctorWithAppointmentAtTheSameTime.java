package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidateException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorWithAppointmentAtTheSameTime implements ValidationAppointment{
    @Autowired
    private AppointmentRepository repository;

    public void check(AppointmentDTO appointmentDTO){
        var doctorWithOtherAppoint = repository.existsByDoctorIdAndDate(appointmentDTO.doctorId(), appointmentDTO.date());

        if(doctorWithOtherAppoint){
            throw new ValidateException("Doctor already appointment in this time");
        }
    }
}
