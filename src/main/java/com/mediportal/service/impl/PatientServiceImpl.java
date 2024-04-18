package com.mediportal.service.impl;

import com.mediportal.entity.Patient;
import com.mediportal.exception.ResourceNotFoundException;
import com.mediportal.payloads.PatientDto;
import com.mediportal.repository.PatientRepository;
import com.mediportal.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = mapToEntity(patientDto); //PatientDto convert into Entity

        Patient patientE = patientRepository.save(patient);

        PatientDto dto = mapToDto(patientE);   //Entity convert into PatientDtos


        return dto;
    }

//    @Override
//    public PatientDto getById(long id) {
//        Patient patient = patientRepository.getById(id);
//        PatientDto dto = new PatientDto();
//
//        dto.setId(patient.getId());
//        dto.setFirstName(patient.getFirstName());
//        dto.setLastName(patient.getLastName());
//        dto.setEmail(patient.getEmail());
//        dto.setGender(patient.getGender());
//        dto.setMobile(patient.getMobile());
//        return dto;
//    }
@Override
public PatientDto getById(long id) {
    Patient patient = patientRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Patient Not Found with id:-" + id)
    );
    PatientDto dto = new PatientDto();
    dto.setId(patient.getId());
    dto.setFirstName(patient.getFirstName());
    dto.setLastName(patient.getLastName());
    dto.setEmail(patient.getEmail());
    dto.setGender(patient.getGender());
    dto.setMobile(patient.getMobile());
    return dto;
}

    @Override
    public List<PatientDto> getAllPatients(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Patient> pagePatient = patientRepository.findAll(pageable);//this fetch entity object from db
        List<Patient> patients = pagePatient.getContent();
        List<PatientDto> dtos = patients.stream().map(patient -> mapToDto(patient)).collect(Collectors.toList());
        return dtos;
    }

    PatientDto mapToDto(Patient patientE){
        PatientDto dto = new PatientDto();                 /*this will convert */

         dto.setId(patientE.getId());                                                   /*Entity to Dtos*/
        dto.setFirstName(patientE.getFirstName());
        dto.setLastName(patientE.getLastName());
        dto.setEmail(patientE.getEmail());
        dto.setGender(patientE.getGender());
        dto.setMobile(patientE.getMobile());
        return dto;
    }

    Patient mapToEntity(PatientDto patientDto){
        Patient patient = new Patient();                     /*this will convert */
       patient.setId(patientDto.getId());
        patient.setFirstName(patientDto.getFirstName());     /* Dtos to entity*/
        patient.setLastName((patientDto.getLastName()));
        patient.setEmail(patientDto.getEmail());
        patient.setGender(patientDto.getGender());
        patient.setMobile(patientDto.getMobile());
        return patient;
    }

}
