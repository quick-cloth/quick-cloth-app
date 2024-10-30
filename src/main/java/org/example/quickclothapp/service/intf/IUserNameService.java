package org.example.quickclothapp.service.intf;

public interface IUserNameService {
    String firstLetterNameAndLastName(String name, String lastName);
    String firstLetterLastNameAndName(String name, String lastName);
    String nameAndLastName(String name, String lastName);
}
