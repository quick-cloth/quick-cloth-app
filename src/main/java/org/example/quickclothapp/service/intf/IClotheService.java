package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Clothe;
import org.example.quickclothapp.model.TypeClothe;
import org.example.quickclothapp.model.TypeGender;
import org.example.quickclothapp.model.TypeStage;

import java.util.List;
import java.util.UUID;

public interface IClotheService {
    Clothe findClotheByUuid(UUID uuid) throws DataServiceException;
    TypeClothe findTypeClotheByUuid(UUID typeClotheUuid) throws DataServiceException;
    TypeGender findTypeGenderByUuid(UUID typeGenderUuid) throws DataServiceException;
    TypeStage findTypeStageByUuid(UUID typeStageUuid) throws DataServiceException;
    List<TypeStage> findAllTypeStage() throws DataServiceException;
    List<TypeGender> findAllTypeGender() throws DataServiceException;
    List<TypeClothe> findAllTypeClothe() throws DataServiceException;
}
