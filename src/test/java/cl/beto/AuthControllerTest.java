package cl.beto;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cl.beto.rest.AuthController;
import cl.beto.rest.UserController;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
@AutoConfigureMockMvc

public class AuthControllerTest{

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AuthController authControllet;
	
    @Test
    public void shouldNotAllowAccessToWrongCredentials() throws Exception {
        String usernameOrEmail = "exampleUsername";
        String password = "examplePassword";

        String body = "{\"usernameOrEmail\":\"" + usernameOrEmail + "\", \"password\":\" "
                      + password + "\"}";;
        
        mvc.perform(MockMvcRequestBuilders.post("/login", body)).andExpect(status().isUnauthorized());
    }

}
