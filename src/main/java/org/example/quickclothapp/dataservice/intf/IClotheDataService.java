package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Clothe;

import java.util.UUID;

public interface IClotheDataService {
    Clothe findClotheByUuid(UUID uuid) throws DataServiceException;
}
