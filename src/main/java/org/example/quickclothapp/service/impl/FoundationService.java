package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IFoundationDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Foundation;
import org.example.quickclothapp.service.intf.IFoundationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FoundationService implements IFoundationService {

    private final IFoundationDataService foundationDataService;

    public FoundationService(IFoundationDataService foundationDataService) {
        this.foundationDataService = foundationDataService;
    }

    @Override
    public Foundation findFoundationByUuid(UUID foundationUuid) throws DataServiceException, DataServiceException {
        return foundationDataService.findFoundationByUuid(foundationUuid);
    }
}
