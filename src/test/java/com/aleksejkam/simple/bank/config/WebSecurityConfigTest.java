package com.aleksejkam.simple.bank.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSecurityConfigTest {

    private TestRestTemplate restTemplate;
    private URL base;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() throws MalformedURLException {
        base = new URL("http://localhost:" + port + "/v1/account/");
    }

    @Test
    public void shouldAuthorizedWithCorrectCredentials()
            throws IllegalStateException {
        // setup
        restTemplate = new TestRestTemplate("user3", "pwd1");

        // execute
        ResponseEntity<String> response =
                restTemplate.getForEntity(base.toString(), String.class);

        // verify
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertTrue(response.getBody().contains("{\"id\":103,\"userId\":103,\"amount\":0"));
    }

    @Test
    public void shouldUnAuthorizedWithWrongCredentials() {
        // setup
        restTemplate = new TestRestTemplate("user1", "wrong_pwd");

        // execute
        ResponseEntity<String> response =
                restTemplate.getForEntity(base.toString(), String.class);

        // verify
        assertThat(HttpStatus.UNAUTHORIZED).isEqualTo(response.getStatusCode());
        assertThat(response.getBody()).isNull();
    }
}