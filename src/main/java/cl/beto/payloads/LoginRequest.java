package cl.beto.payloads;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class LoginRequest {
	
    @NotBlank
    @Getter @Setter
    private String usernameOrEmail;

    @NotBlank
    @Getter @Setter
    private String password;

}