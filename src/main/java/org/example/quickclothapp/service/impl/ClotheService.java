package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IClotheDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.DonationRequest;
import org.example.quickclothapp.service.intf.IClotheService;
import org.example.quickclothapp.service.intf.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClotheService implements IClotheService {

    private final IClotheDataService clotheDataService;
    private final IUserService userService;

    public ClotheService(IClotheDataService clotheDataService, IUserService userService) {
        this.clotheDataService = clotheDataService;
        this.userService = userService;
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

    @Override
    public List<TypeStage> findAllTypeStage() throws DataServiceException {
        return clotheDataService.findAllTypeStage();
    }

    @Override
    public List<TypeGender> findAllTypeGender() throws DataServiceException{
        return clotheDataService.findAllTypeGender();
    }

    @Override
    public List<TypeClothe> findAllTypeClothe() throws DataServiceException {
        return clotheDataService.findAllTypeClothe();
    }

    @Override
    public Clothe findClotheByAllTypes(UUID typeClotheUuid, UUID typeGenderUuid, UUID typeStageUuid) throws DataServiceException{
//        TypeClothe typeClothe = clotheDataService.findTypeClotheByUuid(typeClotheUuid);
//        TypeGender typeGender = clotheDataService.findTypeGenderByUuid(typeGenderUuid);
//        TypeStage typeStage = clotheDataService.findTypeStageByUuid(typeStageUuid);

//        Clothe clothe = Clothe.builder()
//                .typeClothe(typeClothe)
//                .typeGender(typeGender)
//                .typeStage(typeStage)
//                .build();
        
        Clothe clothe = clotheDataService.findClotheByAllTypes(
                typeClotheUuid,
                typeGenderUuid,
                typeStageUuid
        );
        
//        if(clothe == null){
//            clothe = clotheDataService.saveClothe(
//                    Clothe.builder()
//                            .uuid(UUID.randomUUID())
//                            .typeClothe(typeClothe)
//                            .typeGender(typeGender)
//                            .typeStage(typeStage)
//                            .build()
//            );
//        }

        return clothe;
    }

    @Override
    public Clothe saveClothe(UUID typeClotheUuid, UUID typeGenderUuid, UUID typeStageUuid) throws DataServiceException {
        TypeClothe typeClothe = clotheDataService.findTypeClotheByUuid(typeClotheUuid);
        TypeGender typeGender = clotheDataService.findTypeGenderByUuid(typeGenderUuid);
        TypeStage typeStage = clotheDataService.findTypeStageByUuid(typeStageUuid);

        Clothe clothe = Clothe.builder()
                .uuid(UUID.randomUUID())
                .typeClothe(typeClothe)
                .typeGender(typeGender)
                .typeStage(typeStage)
                .build();

        return clotheDataService.saveClothe(clothe);
    }
}
