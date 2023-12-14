package com.api.register.service;

import com.api.register.model.Phone;
import com.api.register.model.User;
import com.api.register.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public void createAndSavePhoneList(List<Map<String, Object>> phoneList, User user){
        if (phoneList.isEmpty()){
            return;
        }
        List<Phone> phones = new ArrayList<>();
        phoneList.forEach(phone -> {
            Phone newPhone = new Phone();
            newPhone.setNumber((String) phone.get("number"));
            newPhone.setCityCode((String) phone.get("citycode"));
            newPhone.setCountryCode((String) phone.get("countrycode"));
            newPhone.setUser(user);
            phones.add(newPhone);
        });
        phoneRepository.saveAll(phones);
    }
}
