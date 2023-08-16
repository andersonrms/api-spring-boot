package med.voll.api.service;

import med.voll.api.domain.ValidateException;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.DetailsAppointmentDTO;
import med.voll.api.domain.appointment.validations.ValidationAppointment;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patiente.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/*
    RESPONSÁVEL PELAS REGRAS DE NÉGOCIO
*/
public class AppointmentScheduleService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private List<ValidationAppointment> validations;

    public DetailsAppointmentDTO schedule(AppointmentDTO appointmentDTO){
        if(!patientRepository.existsById(appointmentDTO.patientId())){
            throw new ValidateException("Patient id not found");
        }

        if(hasDoctorId(appointmentDTO.doctorId()) && !doctorRepository.existsById(appointmentDTO.doctorId())){
            throw new ValidateException("Doctor id not found");
        }

        validations.forEach((validation -> validation.check(appointmentDTO))); // injecao de validador

        var doctor = doctorRepository.findById(appointmentDTO.doctorId()).get();
        var patient = patientRepository.findById(appointmentDTO.patientId()).get();

        var appointment = new Appointment(null, doctor, patient, appointmentDTO.date());

        appointmentRepository.save(appointment);

        return new DetailsAppointmentDTO(appointment);
    }

    private Doctor randomSelectDoctor(AppointmentDTO appointmentDTO){
        if(hasDoctorId(appointmentDTO.doctorId())){
            return doctorRepository.getReferenceById(appointmentDTO.doctorId());
        }

        if(appointmentDTO.specialty() == null){
            throw new ValidateException("Specialty has required because doctor id not informed");
        }

        return doctorRepository.findActiveDoctorBySpecialtyWithAvailableDate(appointmentDTO.specialty(), appointmentDTO.date());
    }

    private boolean hasDoctorId(Long id){
        if(id != null) return true;

        return false;
    }
}
