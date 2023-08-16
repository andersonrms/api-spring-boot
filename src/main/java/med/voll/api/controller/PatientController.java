package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.patiente.DetailsPatientDTO;
import med.voll.api.domain.patiente.Patient;
import med.voll.api.domain.patiente.PatientDTO;
import med.voll.api.domain.patiente.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PatientController {
    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid PatientDTO patientDTO, UriComponentsBuilder uriBuilder){
        var patient = new Patient(patientDTO);
        repository.save(patient);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(patient.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsPatientDTO(patient));
    }

    @GetMapping
    public ResponseEntity<Page> list(Pageable pageable){
        var patientsList = repository.findAllByActiveTrue(pageable);
        return ResponseEntity.ok(patientsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable Long id){
        var patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetailsPatientDTO(patient));
    }


    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid PatientDTO patientDTO) {
        var patient = repository.getReferenceById(patientDTO.id());
        patient.update(patientDTO);

        return ResponseEntity.ok(new DetailsPatientDTO(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var patient = repository.getReferenceById(id);
        patient.delete();

        return ResponseEntity.noContent().build();
    }
}
