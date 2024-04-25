package com.mediportal.controller;

import com.mediportal.payloads.AppointmentDto;
import com.mediportal.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //Http://localhost:8081/api/appointment?patientId=1
    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointments(@RequestBody AppointmentDto appointmentDto,
                                                             @RequestParam long patientId) {
        AppointmentDto dto = appointmentService.createAppointment(appointmentDto, patientId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8081/api/appointment/2
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable long id) {
        appointmentService.deleteAppointmet(id);
        return new ResponseEntity<>("Appointment is deleted!! " + id, HttpStatus.OK);
    }

    //http://localhost:8081/api/appointment/appointment/2/patient/1
    @PutMapping("/{appointment}/{id}/{patient}/{patientId}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable long id, @RequestBody AppointmentDto appointmentDto, @PathVariable long patientId) {

        AppointmentDto dto = appointmentService.updateAppointment(id, appointmentDto, patientId);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
}
