package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.service.intf.IUserNameService;
import org.springframework.stereotype.Service;

@Service
public class UserNameService implements IUserNameService {
    @Override
    public String firstLetterNameAndLastName(String name, String lastName) {
        return name.substring(0, 1).toLowerCase() + lastName;
    }

    @Override
    public String firstLetterLastNameAndName(String name, String lastName) {
        return lastName.substring(0, 1).toLowerCase() + name;
    }

    @Override
    public String nameAndLastName(String name, String lastName) {
        return name +  "." + lastName;
    }
}
