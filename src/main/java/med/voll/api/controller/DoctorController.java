package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {
    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid DoctorDTO doctorDto, UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(doctorDto);
        repository.save(doctor);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(doctor.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsDoctorDTO(doctor));
    };

    @GetMapping
    public ResponseEntity<Page<ListDoctorDTO>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var doctorsList = repository.findAllByActiveTrue(pageable).map(ListDoctorDTO::new);
        return ResponseEntity.ok(doctorsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetailsDoctorDTO(doctor));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdateDoctorDTO updateDoctorDTO) {
        var doctor = repository.getReferenceById(updateDoctorDTO.id());
        doctor.updateData(updateDoctorDTO);
        return ResponseEntity.ok(new DetailsDoctorDTO(doctor));
    }

    /*

        EXCLUSION IN DATABASE

        @DeleteMapping("/{id}")
        @Transactional
        public void delete(@PathVariable Long id){
            repository.deleteById(id);
        }
    */

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);
        doctor.delete();
        return ResponseEntity.noContent().build();
    }
}