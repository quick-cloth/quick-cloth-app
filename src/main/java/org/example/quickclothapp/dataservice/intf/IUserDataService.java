package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.BankEmployeeRequest;
import org.example.quickclothapp.payload.request.FoundationEmployeeRequest;
import org.example.quickclothapp.payload.request.WardrobeEmployeeRequest;

import java.util.List;
import java.util.UUID;

public interface IUserDataService {
    User findUserByUuid(UUID uuid) throws DataServiceException;
    User findUserByDocumentNumber(String documentNumber) throws DataServiceException;
    User findUserByUserName(String userName) throws DataServiceException;
    User findUserByEmail(String email) throws DataServiceException;
    User findUserByPhoneNumber(String phoneNumber) throws DataServiceException;
    Role findRoleByName(String name) throws DataServiceException;
    TypeDocument findTypeDocumentByUuid(UUID uuid) throws DataServiceException;
    User saveUserClient(User newUser) throws DataServiceException;
    User saveUserFoundationEmployee(FoundationEmployeeRequest fer) throws DataServiceException;
    User saveUserBankEmployee(BankEmployeeRequest ber) throws DataServiceException;
    User saveUserWardropeEmployee(WardrobeEmployeeRequest wer) throws DataServiceException;
    List<User> findAllUsersByRol(String name) throws DataServiceException;
    WardRobeEmployee findWarRobeEmployeeByUsername(String username) throws DataServiceException;
    BankEmployee findBankEmployeeByUsername(String username) throws DataServiceException;
    List<TypeDocument> findAllTypeDocument() throws DataServiceException;
}
