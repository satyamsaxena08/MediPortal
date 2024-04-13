package com.mediportal.service.impl;

import com.mediportal.entity.Patient;
import com.mediportal.payloads.PatientDto;
import com.mediportal.repository.PatientRepository;
import com.mediportal.service.PatientService;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName((patientDto.getLastName()));
        patient.setEmail(patientDto.getEmail());
        patient.setGender(patientDto.getGender());
        patient.setMobile(patientDto.getMobile());

        Patient patientE = patientRepository.save(patient);

        PatientDto dto = new PatientDto();

        dto.setFirstName(patientE.getFirstName());
        dto.setLastName(patientE.getLastName());
        dto.setEmail(patientE.getEmail());
        dto.setGender(patientE.getGender());
        dto.setMobile(patientE.getMobile());


        return dto;
    }
}
