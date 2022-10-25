package com.assignment.lotterysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto implements Serializable {
    @NotBlank(message = "Username cannot be null or empty")
    private String username;

    @NotBlank(message = "Firstname cannot be null or empty")
    private String firstname;

    @NotNull(message = "Lastname cannot be null")
    private String lastname;
}
