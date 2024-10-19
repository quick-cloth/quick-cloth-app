package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IWardRopeDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.CreateMinimumStockRequest;
import org.example.quickclothapp.payload.request.OrderDataRequest;
import org.example.quickclothapp.payload.request.SaleDataRequest;
import org.example.quickclothapp.payload.response.CreateMinimumStockResponse;
import org.example.quickclothapp.payload.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WardRobeDataService implements IWardRopeDataService {
    private final RestTemplate restTemplate;
    @Value("${api-server-url}")
    private String apiServerUrl;

    public WardRobeDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Wardrobe saveWardrope(Wardrobe wardrope) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Wardrobe> request = new HttpEntity<>(wardrope, headers);

            ResponseEntity<Wardrobe> responseEntity = restTemplate.exchange(
                    apiServerUrl + "ward_rope/save",
                    HttpMethod.POST,
                    request,
                    Wardrobe.class
            );

            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Wardrobe findWardRopeByUuid(UUID uuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/get")
                    .queryParam("uuid", uuid);

            return restTemplate.getForObject(builder.toUriString(), Wardrobe.class);
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<Wardrobe> finAllWardRopeByClotheBankUuid(UUID clotheBankUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/get_all")
                    .queryParam("clotheBankUuid", clotheBankUuid);

            ResponseEntity<Wardrobe[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    Wardrobe[].class);

            return List.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Sale saveSale(SaleDataRequest newSale) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<SaleDataRequest> request = new HttpEntity<>(newSale, headers);

            ResponseEntity<Sale> responseEntity = restTemplate.exchange(
                    apiServerUrl + "ward_rope/sale/save",
                    HttpMethod.POST,
                    request,
                    Sale.class
            );

            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<Sale> findSalesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/sale/get_all")
                    .queryParam("wardRopeUuid", wardRopeUuid);

            ResponseEntity<Sale[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    Sale[].class);

            return List.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<Inventory> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/inventory/get_all")
                    .queryParam("wardRopeUuid", wardRopeUuid);

            ResponseEntity<Inventory[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    Inventory[].class);
            return List.of(Objects.requireNonNull(responseEntity.getBody()));
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Inventory findInventoryByClotheUuidAndWardRopeUuid(UUID clotheUuid, UUID wardRopeUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/inventory/get/by")
                    .queryParam("clotheUuid", clotheUuid)
                    .queryParam("wardRopeUuid", wardRopeUuid);

            return restTemplate.getForObject(builder.toUriString(), Inventory.class);
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public OrderState findOrderStateByName(String orderName) throws DataServiceException{
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/order_state/get/name")
                    .queryParam("orderName", orderName);

            return restTemplate.getForObject(builder.toUriString(), OrderState.class);
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Order saveOrder(OrderDataRequest or) throws DataServiceException {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<OrderDataRequest> request = new HttpEntity<>(or, headers);

            ResponseEntity<Order> responseEntity = restTemplate.exchange(
                    apiServerUrl + "ward_rope/order/create",
                    HttpMethod.POST,
                    request,
                    Order.class);

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Order confirmOrder(OrderDataRequest or) throws DataServiceException {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<OrderDataRequest> request = new HttpEntity<>(or, headers);

            ResponseEntity<Order> responseEntity = restTemplate.exchange(
                    apiServerUrl + "ward_rope/order/confirm?orderUuid=" + or.getOrder().getUuid(),
                    HttpMethod.POST,
                    request,
                    Order.class);

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<OrderState> findAllOrderStates() throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/order_state/get_all");

            ResponseEntity<OrderState[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    OrderState[].class);

            return List.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<SaleList> findSaleListBySaleUuid(UUID uuid) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/sale_list/get_all")
                    .queryParam("saleUuid", uuid);

            ResponseEntity<SaleList[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    SaleList[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<Order> findOrdersByWardRobeUuid(UUID wardRobeUuid) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/order/get_all")
                    .queryParam("wardRobeUuid", wardRobeUuid);

            ResponseEntity<Order[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    Order[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public void saveSendEmail(SendEmail sendEmail) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<SendEmail> request = new HttpEntity<>(sendEmail, headers);

            ResponseEntity<SendEmail> responseEntity = restTemplate.exchange(
                    apiServerUrl + "ward_rope/send_email/save",
                    HttpMethod.POST,
                    request,
                    SendEmail.class);
        }catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<TopSellingClothes> getTopSellingClothes(UUID wardrobeUuid, LocalDate startDate, LocalDate endDate) throws DataServiceException {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> request = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/top_selling_clothes")
                    .queryParam("wardRobeUuid", wardrobeUuid);

            if (startDate != null) {
                builder.queryParam("startDate", startDate);
            }

            if (endDate != null) {
                builder.queryParam("endDate", endDate);
            }


            ResponseEntity<List<TopSellingClothes>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<TopSellingClothes>>() {}
            );

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }

    }

    @Override
    public List<CreateMinimumStockResponse> saveMinimumStocks(List<CreateMinimumStockRequest> minimumStocksRequest) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<List<CreateMinimumStockRequest>> request = new HttpEntity<>(minimumStocksRequest, headers);
            
            ResponseEntity<List<CreateMinimumStockResponse>> responseEntity = restTemplate.exchange(
                    apiServerUrl + "ward_rope/minimum_stocks/save",
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<CreateMinimumStockResponse>>() {}
            );
            
            return responseEntity.getBody();
        
        }catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
    
    @Override
    public List<CustomerResponse> findCustomersByWardrobeAndClothes(UUID wardrobeUuid, List<UUID> clotheUuids) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> request = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/customers/get")
                    .queryParam("wardRobeUuid", wardrobeUuid)
                    .queryParam("clotheUuids", clotheUuids);

            System.out.println(builder.toUriString());

            ResponseEntity<List<CustomerResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<CustomerResponse>>() {}
            );

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
