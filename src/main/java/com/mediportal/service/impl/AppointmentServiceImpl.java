package com.mediportal.service.impl;

import com.mediportal.entity.Appointment;
import com.mediportal.entity.Patient;
import com.mediportal.exception.ResourceNotFoundException;
import com.mediportal.payloads.AppointmentDto;
import com.mediportal.repository.AppointmentRepository;
import com.mediportal.repository.PatientRepository;
import com.mediportal.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private PatientRepository patientRepository;

    private AppointmentRepository appointmentRepository;
    private ModelMapper modelMapper;

    public AppointmentServiceImpl(PatientRepository patientRepository, AppointmentRepository appointmentRepository,
                                  ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto, long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient is noy found with id:- " + patientId)
        );
//        Appointment appointment = new Appointment();
//
//        appointment.setDate(appointmentDto.getDate());
//        appointment.setReason(appointmentDto.getReason());
//        appointment.setPatient(patient);

        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);

        Appointment app = appointmentRepository.save(appointment);

//        AppointmentDto dto = new AppointmentDto();
//        dto.setId(app.getId());
//        dto.setDate(app.getDate());
//        dto.setReason(app.getReason());

        AppointmentDto dto = modelMapper.map(app, AppointmentDto.class);

        return dto;
    }
}
