package com.gmarcolla.demo_park_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioLoginDto {

    @NotBlank
    @Email(message = "Formato do e-mail está invalido", regexp = "^[A-Za-z0-9.+-]+@[a-z0-9.-]+\\.[A-Za-z]{2,}$")
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
