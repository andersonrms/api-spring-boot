package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.service.AppointmentScheduleService;
import med.voll.api.domain.appointment.DetailsAppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
public class AppointmentController {

    @Autowired
    private AppointmentScheduleService appointmentScheduleService;

    @PostMapping
    @Transactional
    public ResponseEntity appointment(@RequestBody @Valid AppointmentDTO appointmentDTO){
        var scheduling = appointmentScheduleService.schedule(appointmentDTO);
        return ResponseEntity.ok(scheduling);
    }
}
