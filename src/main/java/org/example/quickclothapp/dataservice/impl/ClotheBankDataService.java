package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IClotheBankDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
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
public class ClotheBankDataService implements IClotheBankDataService {
    private final RestTemplate restTemplate;
    @Value("${api-server-url}")
    private String apiServerUrl;

    public ClotheBankDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ClotheBank saveClotheBank(ClotheBank clotheBank) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ClotheBank> request = new HttpEntity<>(clotheBank, headers);

            ResponseEntity<ClotheBank> responseEntity = restTemplate.exchange(
                    apiServerUrl + "clothe_bank/save",
                    HttpMethod.POST,
                    request,
                    ClotheBank.class
            );

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public ClotheBank findClotheBankByUuid(UUID uuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe_bank/get")
                    .queryParam("uuid", uuid);

            return restTemplate.getForObject(builder.toUriString(), ClotheBank.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }


    @Override
    public Campaign saveCampaign(Campaign campaign) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Campaign> request = new HttpEntity<>(campaign, headers);

            ResponseEntity<Campaign> responseEntity = restTemplate.exchange(
                    apiServerUrl + "clothe_bank/campaign/save",
                    HttpMethod.POST,
                    request,
                    Campaign.class);

            return responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public TypeCampaign findTypeCampaignByUuid(UUID typeCampaignUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe_bank/type_campaign/get")
                    .queryParam("uuid", typeCampaignUuid);

            return restTemplate.getForObject(builder.toUriString(), TypeCampaign.class);
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<Campaign> findCampaignsByClotheBankUuid(UUID uuid, LocalDate startDate, LocalDate endDate) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe_bank/campaign/get_all/clothe_bank")
                    .queryParam("clotheBankUuid", uuid);

            if (startDate != null && endDate != null){
                builder.queryParam("startDate", startDate);
                builder.queryParam("endDate", endDate);
            }

            ResponseEntity<Campaign[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), Campaign[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<TypeCampaign> findAllTypeCampaign() throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe_bank/type_campaign/get_all");

            ResponseEntity<TypeCampaign[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), TypeCampaign[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Donation saveDonation(Donation donation) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Donation> request = new HttpEntity<>(donation, headers);

            ResponseEntity<Donation> responseEntity = restTemplate.exchange(
                    apiServerUrl + "clothe_bank/donation/save",
                    HttpMethod.POST,
                    request,
                    Donation.class);

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<Donation> findDonationByClotheBankUuid(UUID clotheBankUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe_bank/donation/get_all/clothe_bank")
                    .queryParam("clotheBankUuid", clotheBankUuid);

            ResponseEntity<Donation[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), Donation[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<Order> findOrdersByClotheBankUuid(UUID clotheBankUuid, UUID orderStateUuid, UUID wardRobeUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe_bank/order/get_all")
                    .queryParam("clotheBankUuid", clotheBankUuid);

            if (orderStateUuid != null){
                builder.queryParam("orderStateUuid", orderStateUuid);
            }
            if (wardRobeUuid != null){
                builder.queryParam("wardRobeUuid", wardRobeUuid);
            }

            ResponseEntity<Order[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), Order[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<OrderList> findOrderListByOrder(UUID orderUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe_bank/order_list/get_all")
                    .queryParam("orderUuid", orderUuid);

            ResponseEntity<OrderList[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), OrderList[].class);

            return List.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Order findOrderByUuid(UUID orderUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe_bank/order/get")
                    .queryParam("orderUuid", orderUuid);

            return restTemplate.getForObject(builder.toUriString(), Order.class);
        } catch (HttpClientErrorException e) {
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
