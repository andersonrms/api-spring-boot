package med.voll.api.domain.doctor;

import med.voll.api.domain.enuns.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    Page<Doctor> findAllByActiveTrue(Pageable pageable);

    @Query("""
    SELECT d FROM Doctor d
    WHERE d.active = true
    AND d.specialty = :specialty
    AND d.id NOT IN (
        SELECT a.doctor.id FROM Appointment a
        WHERE a.date = :date
    )
    ORDER BY RAND()
    LIMIT 1
    """)
    Doctor findActiveDoctorBySpecialtyWithAvailableDate(Specialty specialty, LocalDateTime date);

    @Query("""
            select d.active
            from Doctor d
            where
            d.id = :idDoctor
            """)
    Boolean findActiveById(Long idDoctor);
}