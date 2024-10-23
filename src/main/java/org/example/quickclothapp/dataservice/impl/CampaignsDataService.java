package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.ICampaignsDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service
public class CampaignsDataService implements ICampaignsDataService {

    private final RestTemplate restTemplate;
    @Value("${api-server-url}")
    private String apiServerUrl;

    public CampaignsDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Campaign> getActiveCampaigns() throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> request = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "campaigns/get_active");

            ResponseEntity<List<Campaign>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<Campaign>>() {}
            );

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<Campaign> getCampaignsForUser(UUID userUuid) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> request = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "campaigns/get_for_user")
                    .queryParam("userUuid", userUuid);

            ResponseEntity<List<Campaign>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<Campaign>>() {}
            );

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public void deleteCampaign(UUID campaignUuid) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> request = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "campaigns/campaign/" + campaignUuid);

            restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.DELETE,
                    request,
                    new ParameterizedTypeReference<Void>() {}
            );
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
