package com.mediportal.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private long mobile;
}
