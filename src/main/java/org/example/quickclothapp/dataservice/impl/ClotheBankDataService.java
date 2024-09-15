package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IClotheBankDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.ClotheBank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
}
