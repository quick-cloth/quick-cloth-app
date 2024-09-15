package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Foundation;

import java.util.UUID;

public interface IFoundationDataService {
    Foundation findFoundationByUuid(UUID foundationUuid) throws DataServiceException;
}
