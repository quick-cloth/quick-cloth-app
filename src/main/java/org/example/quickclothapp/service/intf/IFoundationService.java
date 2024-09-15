package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Foundation;

import java.util.UUID;

public interface IFoundationService {
    Foundation findFoundationByUuid(UUID foundationUuid) throws DataServiceException;
}
