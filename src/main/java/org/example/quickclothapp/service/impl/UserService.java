package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IUserDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.BankEmployeeRequest;
import org.example.quickclothapp.payload.request.FoundationEmployeeRequest;
import org.example.quickclothapp.payload.request.UserRequest;
import org.example.quickclothapp.payload.request.WardropeEmployeeRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.example.quickclothapp.service.intf.IFoundationService;
import org.example.quickclothapp.service.intf.IUserService;
import org.example.quickclothapp.service.intf.IWardRopeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final IUserDataService userDataService;
    private final IFoundationService foundationService;
    private final IClotheBankService clotheBankService;
    private final IWardRopeService wardRopeService;

    @Value("${api-server-rol-client}")
    private String clientRol;

    @Value("${api-server-rol-foundation-employee}")
    private String foundationEmployeeRol;

    @Value("${api-server-rol-bank-employee}")
    private String bankEmployeeRol;

    @Value("${api-server-rol-wardRope-employee}")
    private String wardRopeEmployeeRol;

    public UserService(IUserDataService userDataService, IFoundationService foundationService, IClotheBankService clotheBankService, IWardRopeService wardRopeService) {
        this.userDataService = userDataService;
        this.foundationService = foundationService;
        this.clotheBankService = clotheBankService;
        this.wardRopeService = wardRopeService;
    }

    @Override
    public User findUserByUuid(UUID uuid) throws DataServiceException {
        return userDataService.findUserByUuid(uuid);
    }

    @Override
    public User findUserByDocumentNumber(String documentNumber) throws DataServiceException {
        return userDataService.findUserByDocumentNumber(documentNumber);
    }

    @Override
    public MessageResponse saveUserClient(UserRequest user) throws DataServiceException {
        validateUserInsert(user);

        Role role = userDataService.findRoleByName(clientRol);
        TypeDocument typeDocument = userDataService.findTypeDocumentByUuid(user.getTypeDocumentUuid());

        User newUser = User.builder()
                .uuid(UUID.randomUUID())
                .name(user.getName())
                .last_name(user.getLastName())
                .user_name(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .type_document(typeDocument)
                .role(role)
                .build();

        userDataService.saveUserClient(newUser);
        return new MessageResponse("User created successfully", 200, newUser.getUuid());
    }

    @Override
    public MessageResponse saveUserFoundation(UserRequest user, UUID foundationUuid) throws DataServiceException {
        validateUserInsert(user);

        Foundation foundation = foundationService.findFoundationByUuid(foundationUuid);
        Role role = userDataService.findRoleByName(foundationEmployeeRol);
        TypeDocument typeDocument = userDataService.findTypeDocumentByUuid(user.getTypeDocumentUuid());

        User newUser = User.builder()
                .uuid(UUID.randomUUID())
                .name(user.getName())
                .last_name(user.getLastName())
                .user_name(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .type_document(typeDocument)
                .role(role)
                .build();

        FoundationEmployeeRequest fer = FoundationEmployeeRequest.builder()
                .uuid(UUID.randomUUID())
                .foundation(foundation)
                .user(newUser)
                .build();

        userDataService.saveUserFoundationEmployee(fer);

        return new MessageResponse("User Foundation created successfully", 200, newUser.getUuid());
    }

    @Override
    public MessageResponse saveUserBank(UserRequest user, UUID clotheBankUuid) throws DataServiceException {
        validateUserInsert(user);
        ClotheBank clotheBank = clotheBankService.findClotheBankByUuid(clotheBankUuid);
        Role role = userDataService.findRoleByName(bankEmployeeRol);
        TypeDocument typeDocument = userDataService.findTypeDocumentByUuid(user.getTypeDocumentUuid());

        User newUser = User.builder()
                .uuid(UUID.randomUUID())
                .name(user.getName())
                .last_name(user.getLastName())
                .user_name(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .type_document(typeDocument)
                .role(role)
                .build();

        BankEmployeeRequest ber = BankEmployeeRequest.builder()
                .uuid(UUID.randomUUID())
                .clotheBank(clotheBank)
                .user(newUser)
                .build();

        userDataService.saveUserBankEmployee(ber);

        return new MessageResponse("User Bank created successfully", 200, newUser.getUuid());
    }

    @Override
    public MessageResponse saveUserWardrope(UserRequest user, UUID wardRopeUuid) throws DataServiceException {
        validateUserInsert(user);
        Wardrope wardRope = wardRopeService.findWardRopeByUuid(String.valueOf(wardRopeUuid));
        Role role = userDataService.findRoleByName(wardRopeEmployeeRol);
        TypeDocument typeDocument = userDataService.findTypeDocumentByUuid(user.getTypeDocumentUuid());

        User newUser = User.builder()
                .uuid(UUID.randomUUID())
                .name(user.getName())
                .last_name(user.getLastName())
                .user_name(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .type_document(typeDocument)
                .role(role)
                .build();

        WardropeEmployeeRequest wer = WardropeEmployeeRequest.builder()
                .uuid(UUID.randomUUID())
                .wardrope(wardRope)
                .user(newUser)
                .build();

        userDataService.saveUserWardropeEmployee(wer);

        return new MessageResponse("User Wardrope created successfully", 200, newUser.getUuid());
    }

    private void validateUserInsert(UserRequest userRequest) throws DataServiceException {
        if(userDataService.findUserByDocumentNumber(userRequest.getDocumentNumber()) != null){
            throw new DataServiceException("User document already exists", 400);
        }

        if (userDataService.findUserByUserName(userRequest.getUserName()) != null){
            throw new DataServiceException("User name already exists", 400);
        }

        if (userDataService.findUserByEmail(userRequest.getEmail()) != null){
            throw new DataServiceException("User email already exists", 400);
        }

        if (userDataService.findUserByPhoneNumber(String.valueOf(userRequest.getPhone())) != null){
            throw new DataServiceException("User phone already exists", 400);
        }
    }
}
