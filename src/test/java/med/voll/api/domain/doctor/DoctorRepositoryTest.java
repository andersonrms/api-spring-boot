package med.voll.api.domain.doctor;


import med.voll.api.domain.address.AddressDTO;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.enuns.Specialty;
import med.voll.api.domain.patiente.Patient;
import med.voll.api.domain.patiente.PatientDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {
    @Autowired
    private  DoctorRepository repository;
    @Autowired
    private TestEntityManager em;

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("should return null with register doctor unavailable date")
    void findActiveDoctorBySpecialtyWithAvailableDateScnario1() {
        var nextMonday = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = registerDoctor("Fulano de Tal", "fulano@voll.med", "123456", Specialty.CARDIOLOGIA);
        var patient = registerPatient("Patient", "patient@email.com", "22222222222");
        registerAppointment(doctor, patient, nextMonday);

        var freeDoctor = repository.findActiveDoctorBySpecialtyWithAvailableDate(Specialty.CARDIOLOGIA, nextMonday);
        System.out.println(freeDoctor);
        assertThat(freeDoctor).isNull();
    }

    @Test
    @DisplayName("should return available doctor at date")
    void findActiveDoctorBySpecialtyWithAvailableDateScnario2() {
        var appointmentDateFree = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = registerDoctor("Fulano de Tal", "fulano@voll.med", "123456", Specialty.CARDIOLOGIA);
        var freeDoctor = repository.findActiveDoctorBySpecialtyWithAvailableDate(Specialty.CARDIOLOGIA, appointmentDateFree);

        assertThat(freeDoctor).isNotNull();
        assertThat(freeDoctor).isEqualTo(doctor);
    }

    private void registerAppointment(Doctor doctor, Patient patient, LocalDateTime date){
        em.persist(new Appointment(null, doctor, patient, date));
    }

    private Doctor registerDoctor(String name, String email, String crm, Specialty specialty){
        var doctor = new Doctor(doctorDTO(name, email, crm, specialty));
        em.persist(doctor);
        return doctor;
    }

    private DoctorDTO doctorDTO(String name, String email, String crm, Specialty specialty){
        return new DoctorDTO(name, email, crm, "21995277630",specialty, addressDTO());
    }

    private Patient registerPatient(String name, String email, String cpf){
        var patient = new Patient(patientDTO(name, email, cpf));
        em.persist(patient);
        return patient;
    }

    private PatientDTO patientDTO(String name, String email, String cpf){
        return new PatientDTO(null, name, email, "999999999", cpf, true,addressDTO());
    }

    private AddressDTO addressDTO(){
        return new AddressDTO("Av Brasil", "Bairro", "0000000", "RJ", "Rio de Janeiro", "7690", null);
    }
}