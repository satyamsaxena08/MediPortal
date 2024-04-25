package com.mediportal.entity;

import com.mediportal.entity.Patient;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String reason;
private String date;
@ManyToOne
@JoinColumn(name = "patientId")
private Patient patient;


}
