package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IClotheDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Clothe;
import org.example.quickclothapp.model.TypeClothe;
import org.example.quickclothapp.model.TypeGender;
import org.example.quickclothapp.model.TypeStage;
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

    @Override
    public TypeClothe findTypeClotheByUuid(UUID typeClotheUuid) throws DataServiceException {
        return clotheDataService.findTypeClotheByUuid(typeClotheUuid);
    }

    @Override
    public TypeGender findTypeGenderByUuid(UUID typeGenderUuid) throws DataServiceException {
        return clotheDataService.findTypeGenderByUuid(typeGenderUuid);
    }

    @Override
    public TypeStage findTypeStageByUuid(UUID typeStageUuid) throws DataServiceException {
        return clotheDataService.findTypeStageByUuid(typeStageUuid);
    }
}
