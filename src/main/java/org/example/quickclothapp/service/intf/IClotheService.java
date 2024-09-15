package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Clothe;

import java.util.UUID;

public interface IClotheService {
    Clothe findClotheByUuid(UUID uuid) throws DataServiceException;
}
