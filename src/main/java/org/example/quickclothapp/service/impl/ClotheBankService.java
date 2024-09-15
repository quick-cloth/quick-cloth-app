package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IClotheBankDataService;
import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.City;
import org.example.quickclothapp.model.ClotheBank;
import org.example.quickclothapp.model.Foundation;
import org.example.quickclothapp.payload.request.ClotheBankRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClotheBankService implements IClotheBankService {
    private final IClotheBankDataService clotheBankDataService;
    private final FoundationService foundationService;
    private final ILocationDataService locationDataService;


    public ClotheBankService(IClotheBankDataService clotheBankDataService, FoundationService foundationService, ILocationDataService locationDataService) {
        this.clotheBankDataService = clotheBankDataService;
        this.foundationService = foundationService;
        this.locationDataService = locationDataService;
    }

    @Override
    public MessageResponse saveClotheBank(ClotheBankRequest clotheBank) throws DataServiceException {
        Foundation foundation = foundationService.findFoundationByUuid(clotheBank.getFoundationUuid());
        City city = locationDataService.findCityByUuid(clotheBank.getCityUuid());

        ClotheBank newClotheBank = ClotheBank.builder()
                .uuid(UUID.randomUUID())
                .name(clotheBank.getName())
                .address(clotheBank.getAddress())
                .foundation(foundation)
                .city(city)
                .build();

        clotheBankDataService.saveClotheBank(newClotheBank);

        return new MessageResponse("Clothe Bank saved successfully", null, newClotheBank.getUuid());

    }

    @Override
    public ClotheBank findClotheBankByUuid(UUID uuid) throws DataServiceException {
        return clotheBankDataService.findClotheBankByUuid(uuid);
    }
}
