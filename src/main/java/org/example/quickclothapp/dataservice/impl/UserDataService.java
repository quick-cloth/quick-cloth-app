package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IUserDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.BankEmployeeRequest;
import org.example.quickclothapp.payload.request.FoundationEmployeeRequest;
import org.example.quickclothapp.payload.request.WardrobeEmployeeRequest;
import org.example.quickclothapp.payload.response.SalesByUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserDataService implements IUserDataService {
    private final RestTemplate restTemplate;
    @Value("${api-server-url}")
    private String apiServerUrl;

    public UserDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public User findUserByUuid(UUID uuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/get")
                    .queryParam("uuid", uuid);

            return restTemplate.getForObject(builder.toUriString(), User.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public User findUserByDocumentNumber(String documentNumber) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/get/identification")
                    .queryParam("identification", documentNumber);

            return restTemplate.getForObject(builder.toUriString(), User.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public User findUserByUserName(String userName) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/get/username")
                    .queryParam("userName", userName);

            return restTemplate.getForObject(builder.toUriString(), User.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public User findUserByEmail(String email) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/get/email")
                    .queryParam("email", email);

            return restTemplate.getForObject(builder.toUriString(), User.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/get/phone")
                    .queryParam("phoneNumber", phoneNumber);

            return restTemplate.getForObject(builder.toUriString(), User.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Role findRoleByName(String name) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/role/get/name")
                    .queryParam("name", name);

            return restTemplate.getForObject(builder.toUriString(), Role.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public TypeDocument findTypeDocumentByUuid(UUID uuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/type_document/get")
                    .queryParam("uuid", uuid);

            return restTemplate.getForObject(builder.toUriString(), TypeDocument.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public User saveUserClient(User newUser) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<User> request = new HttpEntity<>(newUser, headers);

            ResponseEntity<User> responseEntity = restTemplate.exchange(
                    apiServerUrl + "user/save",
                    HttpMethod.POST,
                    request,
                    User.class);

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public User saveUserFoundationEmployee(FoundationEmployeeRequest fer) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<FoundationEmployeeRequest> request = new HttpEntity<>(fer, headers);

            ResponseEntity<User> responseEntity = restTemplate.exchange(
                    apiServerUrl + "user/foundation_employee/save",
                    HttpMethod.POST,
                    request,
                    User.class);

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public User saveUserBankEmployee(BankEmployeeRequest ber) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<BankEmployeeRequest> request = new HttpEntity<>(ber, headers);

            ResponseEntity<User> responseEntity = restTemplate.exchange(
                    apiServerUrl + "user/bank_employee/save",
                    HttpMethod.POST,
                    request,
                    User.class);

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public User saveUserWardropeEmployee(WardrobeEmployeeRequest wer) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<WardrobeEmployeeRequest> request = new HttpEntity<>(wer, headers);

            ResponseEntity<User> responseEntity = restTemplate.exchange(
                    apiServerUrl + "user/wardrope_employee/save",
                    HttpMethod.POST,
                    request,
                    User.class);

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<User> findAllUsersByRol(String name) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/get_all/role")
                    .queryParam("roleName", name);

            ResponseEntity<User[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    User[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public WardRobeEmployee findWarRobeEmployeeByUsername(String username) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/wardrobe_employee/get")
                    .queryParam("username", username);

            return restTemplate.getForObject(builder.toUriString(), WardRobeEmployee.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public BankEmployee findBankEmployeeByUsername(String username) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/bank_employee/get")
                    .queryParam("username", username);

            return restTemplate.getForObject(builder.toUriString(), BankEmployee.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<TypeDocument> findAllTypeDocument() throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/type_document/get_all");

            return List.of(restTemplate.getForObject(builder.toUriString(), TypeDocument[].class));
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<SalesByUserResponse> findSalesByUser(UUID userUuid, LocalDate startDate, LocalDate endDate) throws DataServiceException {
        
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "user/sales/get")
                    .queryParam("userUuid", userUuid);

            if (startDate != null) {
                builder.queryParam("startDate", startDate);
            }

            if (endDate != null) {
                builder.queryParam("endDate", endDate);
            }
            
            ResponseEntity<SalesByUserResponse[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    SalesByUserResponse[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
