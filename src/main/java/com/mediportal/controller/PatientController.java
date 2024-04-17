package com.mediportal.controller;

import com.mediportal.payloads.PatientDto;
import com.mediportal.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //http://localhost:8080/api/patient
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto){
        PatientDto dto = patientService.createPatient(patientDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/patient/byId?id=1
    @GetMapping("/byId")
    public ResponseEntity<PatientDto> getPatientById(@RequestParam long id){
        PatientDto dto = patientService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/patient?pageNo=0&pageSize=5
    @GetMapping
    public List<PatientDto> getAllPatient(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize
    ){
        List<PatientDto> dto = patientService.getAllPatients(pageNo,pageSize);
        return dto;
    }
}
