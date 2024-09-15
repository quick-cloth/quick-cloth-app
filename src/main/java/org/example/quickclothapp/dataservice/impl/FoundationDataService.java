package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IFoundationDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Foundation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
public class FoundationDataService implements IFoundationDataService {

    private final RestTemplate restTemplate;
    @Value("${api-server-url}")
    private String apiServerUrl;

    public FoundationDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Foundation findFoundationByUuid(UUID foundationUuid) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "foundation/get")
                    .queryParam("uuid", foundationUuid);

            ResponseEntity<Foundation> responseEntity = restTemplate.getForEntity(builder.toUriString(), Foundation.class);

            return responseEntity.getBody();

        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
