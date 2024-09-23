package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IClotheBankDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.model.ClotheBank;
import org.example.quickclothapp.model.TypeCampaign;
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
}
