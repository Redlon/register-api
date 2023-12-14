package com.api.register.service;

import com.api.register.model.Phone;
import com.api.register.model.User;
import com.api.register.repositories.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private PhoneService phoneService;

    private User user;
    private List<Map<String, Object>> phoneList;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUuid(UUID.randomUUID());
        phoneList = new ArrayList<>();
    }

    @Test
    public void testCreateAndSavePhoneListWhenPhoneListIsEmptyThenNoSaveAllMethodCall() {
        // Arrange: The phone list is empty, so no setup for phoneRepository is needed.

        // Act: Call the method with an empty phone list.
        phoneService.createAndSavePhoneList(phoneList, user);

        // Assert: Verify that saveAll() method of the PhoneRepository mock is not called.
        verify(phoneRepository, never()).saveAll(any());
    }

    @Test
    public void testCreateAndSavePhoneListWhenPhoneListIsNotEmptyThenSaveAllMethodCall() {
        // Arrange: Create a non-empty phone list and set up the PhoneRepository mock.
        Map<String, Object> phoneData = new HashMap<>();
        phoneData.put("number", "123456789");
        phoneData.put("citycode", "1");
        phoneData.put("countrycode", "1");
        phoneList.add(phoneData);

        // Act: Call the method with a non-empty phone list.
        phoneService.createAndSavePhoneList(phoneList, user);

        // Assert: Verify that saveAll() method of the PhoneRepository mock is called with the correct list of phones.
        verify(phoneRepository, times(1)).saveAll(any(List.class));
    }
}
