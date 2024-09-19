package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.IClotheDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Clothe;
import org.example.quickclothapp.model.TypeClothe;
import org.example.quickclothapp.model.TypeGender;
import org.example.quickclothapp.model.TypeStage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
public class ClotheDataService implements IClotheDataService {
    private final RestTemplate restTemplate;
    @Value("${api-server-url}")
    private String apiServerUrl;

    public ClotheDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Clothe findClotheByUuid(UUID uuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe/get")
                    .queryParam("uuid", uuid);

            return restTemplate.getForObject(builder.toUriString(), Clothe.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public TypeClothe findTypeClotheByUuid(UUID typeClotheUuid) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe/type_clothe/get")
                    .queryParam("uuid", typeClotheUuid);

            return restTemplate.getForObject(builder.toUriString(), TypeClothe.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public TypeGender findTypeGenderByUuid(UUID typeGenderUuid) throws DataServiceException{
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe/type_gender/get")
                    .queryParam("uuid", typeGenderUuid);

            return restTemplate.getForObject(builder.toUriString(), TypeGender.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public TypeStage findTypeStageByUuid(UUID typeStageUuid) throws DataServiceException{
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "clothe/type_stage/get")
                    .queryParam("uuid", typeStageUuid);

            return restTemplate.getForObject(builder.toUriString(), TypeStage.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}