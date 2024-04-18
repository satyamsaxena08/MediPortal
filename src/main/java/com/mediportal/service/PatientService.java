package com.mediportal.service;

import com.mediportal.payloads.PatientDto;

import java.util.List;

public interface PatientService {

    PatientDto createPatient(PatientDto patientDto);

    PatientDto getById(long id);


    List<PatientDto> getAllPatients(int pageNo, int pageSize, String sortBy, String sortDir);
}
