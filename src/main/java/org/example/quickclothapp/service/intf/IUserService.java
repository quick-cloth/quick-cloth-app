package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.TypeDocument;
import org.example.quickclothapp.model.User;
import org.example.quickclothapp.payload.request.UserRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.SalesByUserResponse;
import org.example.quickclothapp.payload.response.SalesByUserWithPointsResponse;
import org.example.quickclothapp.payload.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User findUserByUuid(UUID uuid) throws DataServiceException;
    UserResponse findUserByDocumentNumber(String documentNumber) throws DataServiceException;
    MessageResponse saveUserClient(UserRequest user) throws DataServiceException;
    MessageResponse saveUserDonor(UserRequest user) throws DataServiceException;
    MessageResponse saveUserFoundation(UserRequest user, UUID foundationUuid) throws DataServiceException;
    MessageResponse saveUserBank(UserRequest user, UUID clotheBankUuid) throws DataServiceException;
    MessageResponse saveUserWardrope(UserRequest user, UUID wardRopeUuid) throws DataServiceException;
    List<User> getAllClientsUsers() throws DataServiceException;
    UserResponse UserWardRobeByUsername(String username) throws DataServiceException;
    UserResponse findBankEmployeeByUsername(String username) throws DataServiceException;
    List<TypeDocument> findAllTypeDocument() throws DataServiceException;
    List<SalesByUserWithPointsResponse> findSalesByUser(UUID userUuid) throws DataServiceException;
}
