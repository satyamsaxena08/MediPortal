package com.mediportal.payloads;

import com.mediportal.entity.Patient;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class AppointmentDto {


    private long id;
    private String reason;
    private String date;

}
