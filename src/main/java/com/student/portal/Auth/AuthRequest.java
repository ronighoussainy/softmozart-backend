package com.student.portal.Auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthRequest {
    @NotNull
    @Length(min = 3, max = 50)
    private String username;

    @NotNull @Length(min = 3, max = 50)
    private String password;

}