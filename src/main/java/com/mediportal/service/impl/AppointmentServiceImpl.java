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
 //       appointment.setPatient(patient);

        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        appointment.setPatient(patient);
        Appointment app = appointmentRepository.save(appointment);

//        AppointmentDto dto = new AppointmentDto();
//        dto.setId(app.getId());
//        dto.setDate(app.getDate());
//        dto.setReason(app.getReason());

        AppointmentDto dto = modelMapper.map(app, AppointmentDto.class);

        return dto;
    }

    @Override
    public void deleteAppointmet(long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentDto updateAppointment(long id, AppointmentDto appointmentDto, long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient NOt Found With Id:-" + id)
        );

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Appointment is not Found with id:-" + id)
        );
        Appointment appointment1 = mapToEntity(appointmentDto);
        appointment1.setId(appointment.getId());
        appointment1.setPatient(patient);

        Appointment save = appointmentRepository.save(appointment1);
        AppointmentDto appointmentDto1 = mapToDto(save);

        return appointmentDto1;

    }

    Appointment mapToEntity(AppointmentDto appointmentDto){
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        return appointment;
    }

    AppointmentDto mapToDto(Appointment appointment){
        AppointmentDto dto = modelMapper.map(appointment, AppointmentDto.class);
        return dto;
    }
}
