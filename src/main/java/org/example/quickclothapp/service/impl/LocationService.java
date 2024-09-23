package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.City;
import org.example.quickclothapp.model.Department;
import org.example.quickclothapp.payload.response.CityResponse;
import org.example.quickclothapp.service.intf.ILocationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocationService implements ILocationService {

    private final ILocationDataService locationDataService;

    public LocationService(ILocationDataService locationDataService) {
        this.locationDataService = locationDataService;
    }

    @Override
    public List<Department> findAllDepartments() throws DataServiceException {
        return locationDataService.getAllDepartments();
    }

    @Override
    public List<CityResponse> findAllCitiesByDepartment(UUID departmentUuid) throws DataServiceException {
        List<City> cities = locationDataService.getAllCitiesByDepartment(departmentUuid);
        List<CityResponse> cityResponses = new ArrayList<>();

        for(City city : cities){
            cityResponses.add(CityResponse.builder()
                            .name(city.getName())
                            .departmentUuid(city.getDepartment().getUuid())
                    .build());
        }

        return cityResponses;
    }
}
