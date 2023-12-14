package com.api.register.service;

import com.api.register.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneService phoneService;

    @InjectMocks
    private UserService userService;

    private Map<String, Object> validUserMap;
    private Map<String, Object> invalidEmailUserMap;
    private Map<String, Object> existingEmailUserMap;
    private Map<String, Object> invalidPasswordUserMap;

    @BeforeEach
    void setUp() {
        validUserMap = new HashMap<>();
        validUserMap.put("email", "test@example.com");
        validUserMap.put("password", "Password@123");
        validUserMap.put("name", "Test User");
        validUserMap.put("phones", null);

        invalidEmailUserMap = new HashMap<>(validUserMap);
        invalidEmailUserMap.put("email", "invalid-email");

        existingEmailUserMap = new HashMap<>(validUserMap);
        existingEmailUserMap.put("email", "existing@example.com");

        invalidPasswordUserMap = new HashMap<>(validUserMap);
        invalidPasswordUserMap.put("password", "pass");
    }

    @Test
    public void testAddUserWhenEmailIsValidAndDoesNotExistThenReturnOk() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = userService.addUser("Bearer token", validUserMap);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddUserWhenEmailIsInvalidThenReturnInternalServerError() {
        ResponseEntity<Map<String, Object>> response = userService.addUser("Bearer token", invalidEmailUserMap);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assert responseBody != null;
        assertEquals("El mail ingresado no es valido", responseBody.get("mensaje"));
    }

    @Test
    public void testAddUserWhenEmailAlreadyExistsThenReturnInternalServerError() {
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = userService.addUser("Bearer token", existingEmailUserMap);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assert responseBody != null;
        assertEquals("El mail ingresado ya existe", responseBody.get("mensaje"));
    }

    @Test
    public void testAddUserWhenPasswordIsInvalidThenReturnInternalServerError() {
        ResponseEntity<Map<String, Object>> response = userService.addUser("Bearer token", invalidPasswordUserMap);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assert responseBody != null;
        assertEquals("La contrasena ingresada no es valida", responseBody.get("mensaje"));
    }
}
