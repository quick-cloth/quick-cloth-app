package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Department;
import org.example.quickclothapp.payload.response.CityResponse;

import java.util.List;
import java.util.UUID;

public interface ILocationService {
    List<Department> findAllDepartments() throws DataServiceException;
    List<CityResponse> findAllCitiesByDepartment(UUID departmentUuid) throws DataServiceException;
}
