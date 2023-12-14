package com.api.register.model.dto;

import com.api.register.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class OutputUserDTOTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("test 'outputUserDTO' method when 'uuid' field of 'user' object is not null")
    void testOutputUserDTOWhenUuidNotNullThenUuidSet() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken("sample-token");
        user.setIsActive(true);

        // Act
        OutputUserDTO outputUserDTO = OutputUserDTO.outputUserDTO(user);

        // Assert
        assertThat(outputUserDTO.getUuid()).isEqualTo(uuid.toString());
        assertThat(outputUserDTO.getCreated()).isEqualTo(user.getCreated());
        assertThat(outputUserDTO.getModified()).isEqualTo(user.getModified());
        assertThat(outputUserDTO.getLastLogin()).isEqualTo(user.getLastLogin());
        assertThat(outputUserDTO.getToken()).isEqualTo(user.getToken());
        assertThat(outputUserDTO.getIsActive()).isEqualTo(user.getIsActive());
    }

    @Test
    @DisplayName("test 'outputUserDTO' method when 'uuid' field of 'user' object is null")
    void testOutputUserDTOWhenUuidNullThenUuidNull() {
        // Arrange
        user.setUuid(null);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken("sample-token");
        user.setIsActive(true);

        // Act
        OutputUserDTO outputUserDTO = OutputUserDTO.outputUserDTO(user);

        // Assert
        assertThat(outputUserDTO.getUuid()).isNull();
        assertThat(outputUserDTO.getCreated()).isEqualTo(user.getCreated());
        assertThat(outputUserDTO.getModified()).isEqualTo(user.getModified());
        assertThat(outputUserDTO.getLastLogin()).isEqualTo(user.getLastLogin());
        assertThat(outputUserDTO.getToken()).isEqualTo(user.getToken());
        assertThat(outputUserDTO.getIsActive()).isEqualTo(user.getIsActive());
    }

    @Test
    @DisplayName("test 'outputUserDTO' method when 'user' object is null")
    void testOutputUserDTOWhenUserNullThenNull() {
        // Act & Assert
        assertThatNullPointerException().isThrownBy(() -> OutputUserDTO.outputUserDTO(null));
    }
}
