package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IClotheBankDataService;
import org.example.quickclothapp.dataservice.intf.IFoundationDataService;
import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.City;
import org.example.quickclothapp.model.ClotheBank;
import org.example.quickclothapp.model.Foundation;
import org.example.quickclothapp.model.TypeMeetUs;
import org.example.quickclothapp.payload.request.FoundationRequest;
import org.example.quickclothapp.payload.response.FoundationResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.example.quickclothapp.service.intf.IFoundationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FoundationService implements IFoundationService {

    private final IFoundationDataService foundationDataService;
    private final IClotheBankDataService clotheBankService;
    private final ILocationDataService locationDataService;

    public FoundationService(IFoundationDataService foundationDataService, IClotheBankDataService clotheBankService, ILocationDataService locationDataService) {
        this.foundationDataService = foundationDataService;
        this.clotheBankService = clotheBankService;
        this.locationDataService = locationDataService;
    }

    @Override
    public MessageResponse saveFoundation(FoundationRequest foundation) throws DataServiceException{
        ClotheBank clotheBank = clotheBankService.findClotheBankByUuid(foundation.getClotheBankUuid());
        TypeMeetUs typeMeetUs = foundationDataService.findTypeMeetUsByUuid(foundation.getTypeMeetUsUuid());
        City city = locationDataService.findCityByUuid(foundation.getCityUuid());

        foundation.getContactUser().setUuid(UUID.randomUUID());

        Foundation newFoundation = Foundation.builder()
                .uuid(UUID.randomUUID())
                .name(foundation.getName())
                .nit(foundation.getNit())
                .legal_representative(foundation.getLegalRepresentative())
                .phone(foundation.getPhone())
                .email(foundation.getEmail())
                .creation_date(LocalDate.now())
                .clotheBank(clotheBank)
                .typeMeetUs(typeMeetUs)
                .city(city)
                .contactUser(foundation.getContactUser())
                .build();

        foundationDataService.saveFoundation(newFoundation);

        return new MessageResponse("Foundation created successfully", null, newFoundation.getUuid());
    }

    @Override
    public MessageResponse updateFoundation(FoundationRequest foundation) throws DataServiceException {
        Foundation foundationToUpdate = foundationDataService.findFoundationByUuid(foundation.getUuid());

        foundationToUpdate.setName(foundation.getName());
        foundationToUpdate.setNit(foundation.getNit());
        foundationToUpdate.setLegal_representative(foundation.getLegalRepresentative());
        foundationToUpdate.setPhone(foundation.getPhone());
        foundationToUpdate.setEmail(foundation.getEmail());
        foundationToUpdate.setContactUser(foundation.getContactUser());
        foundationToUpdate.setTypeMeetUs(foundationDataService.findTypeMeetUsByUuid(foundation.getTypeMeetUsUuid()));
        foundationToUpdate.setContactUser(foundation.getContactUser());

        foundationDataService.saveFoundation(foundationToUpdate);

        return new MessageResponse("Foundation updated successfully", null, foundationToUpdate.getUuid());
    }

    @Override
    public Foundation findFoundationByUuid(UUID foundationUuid) throws DataServiceException {
        return foundationDataService.findFoundationByUuid(foundationUuid);
    }

    @Override
    public List<FoundationResponse> findAllFoundationByClotheBank(UUID clotheBankUuid) throws DataServiceException {
        List<Foundation> foundations = foundationDataService.findAllFoundationByClotheBank(clotheBankUuid);

        List<FoundationResponse> foundationResponses = new ArrayList<>();

        for (Foundation f : foundations){
            foundationResponses.add(
                    FoundationResponse.builder()
                            .uuid(f.getUuid())
                            .name(f.getName())
                            .nit(f.getNit())
                            .phone(f.getPhone())
                            .email(f.getEmail())
                            .legalRepresentative(f.getLegal_representative())
                            .creationDate(f.getCreation_date())
                            .typeMeetUsName(f.getTypeMeetUs().getName())
                            .contactUser(f.getContactUser())
                            .city(f.getCity().getName())
                            .build()
            );
        }

        return foundationResponses;
    }

    @Override
    public FoundationResponse findFoundationResponseByUuid(UUID foundationUuid) throws DataServiceException {
        Foundation f = foundationDataService.findFoundationByUuid(foundationUuid);

        return FoundationResponse.builder()
                .uuid(f.getUuid())
                .name(f.getName())
                .nit(f.getNit())
                .phone(f.getPhone())
                .email(f.getEmail())
                .creationDate(f.getCreation_date())
                .legalRepresentative(f.getLegal_representative())
                .typeMeetUsName(f.getTypeMeetUs().getName())
                .city(f.getCity().getName())
                .contactUser(f.getContactUser())
                .build();
    }

    @Override
    public List<TypeMeetUs> getAllTypeMeetUs() throws DataServiceException {
        return foundationDataService.getAllTypeMeetUs();
    }
}
