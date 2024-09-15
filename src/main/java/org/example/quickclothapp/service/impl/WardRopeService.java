package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.dataservice.intf.IUserDataService;
import org.example.quickclothapp.dataservice.intf.IWardRopeDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.SaleDataRequest;
import org.example.quickclothapp.payload.request.SaleListRequest;
import org.example.quickclothapp.payload.request.SaleRequest;
import org.example.quickclothapp.payload.request.WardRopeRequest;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.example.quickclothapp.service.intf.IClotheService;
import org.example.quickclothapp.service.intf.IUserService;
import org.example.quickclothapp.service.intf.IWardRopeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WardRopeService implements IWardRopeService {

    private final IWardRopeDataService wardRopeDataService;
    private final ILocationDataService locationDataService;
    private final IClotheBankService clotheBankService;
    private final IUserDataService userDataService;
    private final IClotheService clotheService;

    public WardRopeService(IWardRopeDataService wardRopeDataService, ILocationDataService locationDataService, IClotheBankService clotheBankService, IUserDataService userDataService, IClotheService clotheService) {
        this.wardRopeDataService = wardRopeDataService;
        this.locationDataService = locationDataService;
        this.clotheBankService = clotheBankService;
        this.userDataService = userDataService;
        this.clotheService = clotheService;
    }

    @Override
    public MessageResponse saveWardRope(WardRopeRequest wardRopeRequest) throws DataServiceException {
        City city = locationDataService.findCityByUuid(wardRopeRequest.getCityUuid());
        ClotheBank clotheBank = clotheBankService.findClotheBankByUuid(wardRopeRequest.getClotheBankUuid());

        Wardrope newWardRope = Wardrope.builder()
                .uuid(UUID.randomUUID())
                .name(wardRopeRequest.getName())
                .creation_date(LocalDate.now())
                .address(wardRopeRequest.getAddress())
                .city(city)
                .clotheBank(clotheBank)
                .build();

        wardRopeDataService.saveWardrope(newWardRope);

        return new MessageResponse("Ward Rope saved successfully", null, newWardRope.getUuid());
    }

    @Override
    public MessageResponse saveSale(SaleRequest saleRequest) throws DataServiceException {
        Wardrope wardRope = wardRopeDataService.findWardRopeByUuid(saleRequest.getWardRopeUuid());
        User user = userDataService.findUserByUuid(saleRequest.getUserUuid());

        Sale newSale = Sale.builder()
                .uuid(UUID.randomUUID())
                .sale_date(LocalDate.now())
                .wardrope(wardRope)
                .value(saleRequest.getValue())
                .user(user)
                .build();

        List<SaleList> saleLists = new ArrayList<>();

        for(SaleListRequest saleListRequest : saleRequest.getSaleList()){
            Clothe clothe = clotheService.findClotheByUuid(saleListRequest.getClotheUuid());
            SaleList saleList = SaleList.builder()
                    .uuid(UUID.randomUUID())
                    .clothe(clothe)
                    .value(saleListRequest.getValue())
                    .sale(newSale)
                    .build();

            saleLists.add(saleList);
        }

        SaleDataRequest sdr = SaleDataRequest.builder()
                .sale(newSale)
                .saleListRequests(saleLists)
                .build();

        wardRopeDataService.saveSale(sdr);

        return new MessageResponse("Sale saved successfully", null, newSale.getUuid());
    }

    @Override
    public Wardrope findWardRopeByUuid(String uuid) throws DataServiceException {
        return wardRopeDataService.findWardRopeByUuid(UUID.fromString(uuid));
    }

}
