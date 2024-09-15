package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IClotheDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Clothe;
import org.example.quickclothapp.service.intf.IClotheService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClotheService implements IClotheService {

    private final IClotheDataService clotheDataService;

    public ClotheService(IClotheDataService clotheDataService) {
        this.clotheDataService = clotheDataService;
    }

    @Override
    public Clothe findClotheByUuid(UUID uuid) throws DataServiceException {
        return clotheDataService.findClotheByUuid(uuid);
    }
}
