package com.mediportal.service;

import com.mediportal.payloads.AppointmentDto;

public interface AppointmentService {


    AppointmentDto createAppointment(AppointmentDto appointmentDto, long patientId);

    void deleteAppointmet(long id);

    AppointmentDto updateAppointment(long id, AppointmentDto appointmentDto, long patientId);
}
