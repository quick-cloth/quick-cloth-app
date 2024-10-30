package org.example.quickclothapp.dataservice.impl;

import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.City;
import org.example.quickclothapp.model.Department;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service
public class LocationDataService implements ILocationDataService {

    private final RestTemplate restTemplate;
    @Value("${api-server-url}")
    private String apiServerUrl;


    public LocationDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<Department> getAllDepartments() throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "location/department/get_all");

            return List.of(restTemplate.getForObject(builder.toUriString(), Department[].class));
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public Department findDepartmentByUuid(UUID uuid) {
        return null;
    }

    @Override
    public List<City> getAllCitiesByDepartment(UUID departmentUuid) throws DataServiceException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "location/city/get_all/by")
                    .queryParam("departmentUuid", departmentUuid);

            return List.of(restTemplate.getForObject(builder.toUriString(), City[].class));
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    @Override
    public City findCityByUuid(UUID cityUuid) throws DataServiceException {
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiServerUrl + "location/city/get")
                    .queryParam("cityUuid", cityUuid);

            return restTemplate.getForObject(builder.toUriString(), City.class);
        }
        catch (HttpClientErrorException e){
            throw new DataServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
