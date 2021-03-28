package com.pentax.pentazon.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull
    @NotBlank(message = "Please provide your username")
    private String username;
    @NotNull
    @NotBlank(message = "Please provide your password")
    private String password;
}
