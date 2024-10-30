package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IFoundationDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.ClotheBank;
import org.example.quickclothapp.model.Foundation;
import org.example.quickclothapp.model.TypeMeetUs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
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
    public Foundation saveFoundation(Foundation newFoundation) throws DataServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Foundation> request = new HttpEntity<>(newFoundation, headers);

            ResponseEntity<Foundation> responseEntity = restTemplate.exchange(
                    apiServerUrl + "foundation/save",
                    HttpMethod.POST,
                    request,
                    Foundation.class
            );

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
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

    @Override
    public List<Foundation> findAllFoundationByClotheBank(UUID clotheBankUuid) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "foundation/get_all/clothe_bank")
                    .queryParam("clotheBankUuid", clotheBankUuid);

            ResponseEntity<Foundation[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), Foundation[].class);

            return List.of(responseEntity.getBody());

        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public List<TypeMeetUs> getAllTypeMeetUs() throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "foundation/type_meet_us/get_all");

            ResponseEntity<TypeMeetUs[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), TypeMeetUs[].class);

            return List.of(responseEntity.getBody());
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public TypeMeetUs findTypeMeetUsByUuid(UUID typeMeetUsUuid) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "foundation/type_meet_us/get")
                    .queryParam("uuid", typeMeetUsUuid);

            ResponseEntity<TypeMeetUs> responseEntity = restTemplate.getForEntity(builder.toUriString(), TypeMeetUs.class);

            return responseEntity.getBody();

        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
