package org.example.quickclothapp.service.impl;

import jakarta.mail.MessagingException;
import org.example.quickclothapp.dataservice.intf.IClotheBankDataService;
import org.example.quickclothapp.dataservice.intf.IUserDataService;
import org.example.quickclothapp.dataservice.intf.IWardRopeDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.*;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.UserResponse;
import org.example.quickclothapp.service.intf.IEmailService;
import org.example.quickclothapp.service.intf.IFoundationService;
import org.example.quickclothapp.service.intf.IUserNameService;
import org.example.quickclothapp.service.intf.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final IUserDataService userDataService;
    private final IFoundationService foundationService;
    private final IClotheBankDataService clotheBankService;
    private final IWardRopeDataService wardRopeService;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final IUserNameService userNameService;
    private final IEmailService emailService;

    @Value("${api-server-rol-client}")
    private String clientRol;

    @Value("${api-server-rol-donor}")
    private String donorRol;

    @Value("${api-server-rol-foundation-employee}")
    private String foundationEmployeeRol;

    @Value("${api-server-rol-bank-employee}")
    private String bankEmployeeRol;

    @Value("${api-server-rol-wardRope-employee}")
    private String wardRopeEmployeeRol;

    public UserService(IUserDataService userDataService, IFoundationService foundationService, IClotheBankDataService clotheBankService, IWardRopeDataService wardRopeService, BCryptPasswordEncoder bcryptEncoder, IUserNameService userNameService, IEmailService emailService) {
        this.userDataService = userDataService;
        this.foundationService = foundationService;
        this.clotheBankService = clotheBankService;
        this.wardRopeService = wardRopeService;
        this.bcryptEncoder = bcryptEncoder;
        this.userNameService = userNameService;
        this.emailService = emailService;
    }

    @Override
    public User findUserByUuid(UUID uuid) throws DataServiceException {
        return userDataService.findUserByUuid(uuid);
    }

    @Override
    public UserResponse findUserByDocumentNumber(String documentNumber) throws DataServiceException {
        User user = userDataService.findUserByDocumentNumber(documentNumber);

        if(user == null){
            throw new DataServiceException("User not found", 404);
        }

        return UserResponse.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .lastName(user.getLast_name())
                .email(user.getEmail())
                .build();
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
                .user_name(generateUserName(user.getName(), user.getLastName()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .password(bcryptEncoder.encode("12345"))
                .type_document(typeDocument)
                .role(role)
                .build();

        userDataService.saveUserClient(newUser);
        sendEmailNewUser(newUser);

        return new MessageResponse("User created successfully", 200, newUser.getUuid());
    }

    @Override
    public MessageResponse saveUserDonor(UserRequest user) throws DataServiceException {
        validateUserInsert(user);

        Role role = userDataService.findRoleByName(donorRol);
        TypeDocument typeDocument = userDataService.findTypeDocumentByUuid(user.getTypeDocumentUuid());

        User newUser = User.builder()
                .uuid(UUID.randomUUID())
                .name(user.getName())
                .last_name(user.getLastName())
                .user_name(generateUserName(user.getName(), user.getLastName()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .password(bcryptEncoder.encode("12345"))
                .type_document(typeDocument)
                .role(role)
                .build();

        userDataService.saveUserClient(newUser);
        sendEmailNewUser(newUser);

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
                .user_name(generateUserName(user.getName(), user.getLastName()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .password(bcryptEncoder.encode("12345"))
                .type_document(typeDocument)
                .role(role)
                .build();

        FoundationEmployeeRequest fer = FoundationEmployeeRequest.builder()
                .uuid(UUID.randomUUID())
                .foundation(foundation)
                .user(newUser)
                .build();

        userDataService.saveUserFoundationEmployee(fer);
        sendEmailNewUser(newUser);

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
                .user_name(generateUserName(user.getName(), user.getLastName()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .type_document(typeDocument)
                .password(bcryptEncoder.encode("12345"))
                .role(role)
                .build();

        BankEmployeeRequest ber = BankEmployeeRequest.builder()
                .uuid(UUID.randomUUID())
                .clotheBank(clotheBank)
                .user(newUser)
                .build();

        userDataService.saveUserBankEmployee(ber);
        sendEmailNewUser(newUser);

        return new MessageResponse("User Bank created successfully", 200, newUser.getUuid());
    }

    @Override
    public MessageResponse saveUserWardrope(UserRequest user, UUID wardRopeUuid) throws DataServiceException {
        validateUserInsert(user);
        Wardrobe wardRope = wardRopeService.findWardRopeByUuid(wardRopeUuid);
        Role role = userDataService.findRoleByName(wardRopeEmployeeRol);
        TypeDocument typeDocument = userDataService.findTypeDocumentByUuid(user.getTypeDocumentUuid());

        User newUser = User.builder()
                .uuid(UUID.randomUUID())
                .name(user.getName())
                .last_name(user.getLastName())
                .user_name(generateUserName(user.getName(), user.getLastName()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(0)
                .creation_date(LocalDate.now())
                .document(new BigInteger(user.getDocumentNumber()))
                .type_document(typeDocument)
                .password(bcryptEncoder.encode("12345"))
                .role(role)
                .build();

        WardrobeEmployeeRequest wer = WardrobeEmployeeRequest.builder()
                .uuid(UUID.randomUUID())
                .wardrope(wardRope)
                .user(newUser)
                .build();

        userDataService.saveUserWardropeEmployee(wer);
        sendEmailNewUser(newUser);

        return new MessageResponse("User Wardrope created successfully", 200, newUser.getUuid());
    }

    @Async
    public void sendEmailNewUser(User user) throws DataServiceException {
        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject("Bienvenido!!")
                .build();

        emailService.sendEmailNewUser(user, emailRequest);

        SendEmail sendEmail = SendEmail.builder()
                .uuid(UUID.randomUUID())
                .email(user.getEmail())
                .send_date(LocalDate.now())
                .build();

        wardRopeService.saveSendEmail(sendEmail);
    }

    @Override
    public List<User> getAllClientsUsers() throws DataServiceException {
        return userDataService.findAllUsersByRol(clientRol);
    }

    @Override
    public UserResponse UserWardRobeByUsername(String username) throws DataServiceException {
        WardRobeEmployee wardRobeEmployee = userDataService.findWarRobeEmployeeByUsername(username);

        return UserResponse.builder()
                .uuid(wardRobeEmployee.getUser().getUuid())
                .name(wardRobeEmployee.getUser().getName())
                .lastName(wardRobeEmployee.getUser().getLast_name())
                .email(wardRobeEmployee.getUser().getEmail())
                .wardRopeUuid(wardRobeEmployee.getWardrobe().getUuid())
                .build();
    }

    @Override
    public UserResponse findBankEmployeeByUsername(String username) throws DataServiceException {
        BankEmployee bankEmployee = userDataService.findBankEmployeeByUsername(username);

        return UserResponse.builder()
                .uuid(bankEmployee.getUser().getUuid())
                .name(bankEmployee.getUser().getName())
                .lastName(bankEmployee.getUser().getLast_name())
                .email(bankEmployee.getUser().getEmail())
                .clotheBankUuid(bankEmployee.getClotheBank().getUuid())
                .build();
    }

    @Override
    public List<TypeDocument> findAllTypeDocument() throws DataServiceException {
        return userDataService.findAllTypeDocument();
    }

    private void validateUserInsert(UserRequest userRequest) throws DataServiceException {
        if(userDataService.findUserByDocumentNumber(userRequest.getDocumentNumber()) != null){
            throw new DataServiceException("User document already exists", 409);
        }

        if (userDataService.findUserByEmail(userRequest.getEmail()) != null){
            throw new DataServiceException("User email already exists", 409);
        }

        if (userDataService.findUserByPhoneNumber(String.valueOf(userRequest.getPhone())) != null){
            throw new DataServiceException("User phone already exists", 409);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDataService.findUserByUserName(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getUser_name(), user.getPassword(), getAuthority(user.getRole()));
            return userDetails;
        } catch (DataServiceException e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(Role roleUser){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleUser.getName()));
        return authorities;
    }

    private String randomUserNameSelector(String name, String lastName) {
        Random random = new Random();
        int randomInt = random.nextInt(3);
        return switch (randomInt) {
            case 0 -> userNameService.firstLetterNameAndLastName(name, lastName);
            case 1 -> userNameService.firstLetterLastNameAndName(name, lastName);
            case 2 -> userNameService.nameAndLastName(name, lastName);
            default -> "";
        };
    }

    public boolean validateUserNames(String username) throws DataServiceException {
        User user = userDataService.findUserByUserName(username);
        return user == null;
    }

    private String generateUserName(String name, String lastName) throws DataServiceException {
        String username = randomUserNameSelector(name, lastName);
        while (!validateUserNames(username)) {
            username = randomUserNameSelector(name, lastName);
        }
        return username;
    }
}
