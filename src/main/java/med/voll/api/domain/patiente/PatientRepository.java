package med.voll.api.domain.patiente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findAllByActiveTrue(Pageable pageable);

    @Query("""
            select d.active
            from Patient d
            where
            d.id = :id
            """)
    Boolean findActiveById(Long id);
}
