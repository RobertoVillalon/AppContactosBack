package cl.beto.payloads;

import lombok.Getter;
import lombok.Setter;

public class JwtAuthenticationResponse {
	@Getter @Setter
    private String accessToken;
	
	@Getter @Setter
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}