package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IWardRopeDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.OrderDataRequest;
import org.example.quickclothapp.payload.request.SaleDataRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service
public class WardRopeDataService implements IWardRopeDataService {
    private final RestTemplate restTemplate;
    @Value("${api-server-url}")
    private String apiServerUrl;

    public WardRopeDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Wardrope saveWardrope(Wardrope wardrope) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Wardrope> request = new HttpEntity<>(wardrope, headers);

            ResponseEntity<Wardrope> responseEntity = restTemplate.exchange(
                    apiServerUrl + "ward_rope/save",
                    HttpMethod.POST,
                    request,
                    Wardrope.class
            );

            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Wardrope findWardRopeByUuid(UUID uuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/get")
                    .queryParam("uuid", uuid);

            return restTemplate.getForObject(builder.toUriString(), Wardrope.class);
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
    public List<Inventory> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "ward_rope/inventory/get_all")
                    .queryParam("wardRopeUuid", wardRopeUuid);

            ResponseEntity<Inventory[]> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    Inventory[].class);

            return List.of(responseEntity.getBody());
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
}