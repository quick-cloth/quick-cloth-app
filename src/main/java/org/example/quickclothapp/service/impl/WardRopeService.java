package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.dataservice.intf.IUserDataService;
import org.example.quickclothapp.dataservice.intf.IWardRopeDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.*;
import org.example.quickclothapp.payload.response.*;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.example.quickclothapp.service.intf.IClotheService;
import org.example.quickclothapp.service.intf.IWardRopeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
    private static final int AMOUNT_PER_POINT = 1000;
    private static final int POINTS_PER_1000 = 4;
    @Value("${api-server-order-state-received}")
    private String orderStateReceived;

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
    public SaleResponse checkValueSale(SaleRequest sale) throws DataServiceException {
        Wardrope wardRope = wardRopeDataService.findWardRopeByUuid(sale.getWardRopeUuid());
        List<Campaign> campaigns = clotheBankService.findCampaignsByClotheBankUuid(wardRope.getClotheBank().getUuid());

        boolean isAllSaleDiscounted = true;
        LocalDate currentDate = LocalDate.now();

        List<SaleListResponse> saleListResponses = new ArrayList<>();

        for(SaleListRequest saleListRequest : sale.getSaleList()){
            Clothe clothe = clotheService.findClotheByUuid(saleListRequest.getClotheUuid());

            SaleListResponse slr = SaleListResponse.builder()
                    .clotheName(clothe.getTypeClothe().getName())
                    .typeGenderName(clothe.getTypeGender().getName())
                    .typeStageName(clothe.getTypeStage().getName())
                    .quantity(saleListRequest.getQuantity())
                    .value(Double.valueOf(saleListRequest.getValue()))
                    .campaignList(new ArrayList<>())
                    .build();

            saleListResponses.add(slr);
        }

        for(Campaign c : campaigns){
            if(currentDate.isBefore(c.getEnd_date())){
                for (SaleListResponse slr : saleListResponses){
                    if (c.getTypeClothe() != null){
                        if (slr.getClotheName().equals(c.getTypeClothe().getName())){
                            calculateDiscount(slr, c);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (c.getTypeGender() != null){
                        if (slr.getTypeGenderName().equals(c.getTypeGender().getName())){
                            calculateDiscount(slr, c);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (c.getTypeStage() != null){
                        if (slr.getTypeStageName().equals(c.getTypeStage().getName())){
                            calculateDiscount(slr, c);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (isAllSaleDiscounted){
                        calculateDiscount(slr, c);
                    }
                }
            }
        }
        return  SaleResponse.builder()
                .totalValue(saleListResponses.stream().mapToDouble(SaleListResponse::getValue).sum())
                .saleList(saleListResponses)
                .build();
    }

    private void calculateDiscount(SaleListResponse slr, Campaign c){
        double discount = (slr.getValue() * c.getDiscount()) / 100;
        slr.setValue(slr.getValue() - discount);

        CampaignResponse cr = CampaignResponse.builder()
                .campaignName(c.getName())
                .valueDiscount(discount)
                .discount(c.getDiscount())
                .build();
        slr.getCampaignList().add(cr);
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
                    .quantity(saleListRequest.getQuantity())
                    .build();

            saleLists.add(saleList);
        }

        user.setPoints(newSale.getValue().divide(BigInteger.valueOf(AMOUNT_PER_POINT)).intValue() * POINTS_PER_1000);


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

    @Override
    public List<InventoryResponse> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException {
        List<Inventory> inventories = wardRopeDataService.findInventoriesByWardRopeUuid(wardRopeUuid);

        List<InventoryResponse> inventoryResponses = new ArrayList<>();

        for (Inventory i : inventories){
            InventoryResponse ir = InventoryResponse.builder()
                    .uuid(i.getUuid())
                    .clotheUuid(i.getClothe().getUuid())
                    .stock(i.getStock())
                    .minimumStock(i.getMinimum_stock())
                    .typeGenderName(i.getClothe().getTypeGender().getName())
                    .clotheName(i.getClothe().getTypeClothe().getName())
                    .typeStageName(i.getClothe().getTypeStage().getName())
                    .unitPrice(i.getUnit_price())
                    .build();

            inventoryResponses.add(ir);
        }

        return inventoryResponses;
    }

    @Override
    public InventoryResponse findInventoryByClotheUuidAndWardRopeUuid(UUID clotheUuid, UUID wardRopeUuid) throws DataServiceException {
        Inventory inventory = wardRopeDataService.findInventoryByClotheUuidAndWardRopeUuid(clotheUuid, wardRopeUuid);

        return  InventoryResponse.builder()
                .uuid(inventory.getUuid())
                .clotheUuid(inventory.getClothe().getUuid())
                .stock(inventory.getStock())
                .minimumStock(inventory.getMinimum_stock())
                .typeGenderName(inventory.getClothe().getTypeGender().getName())
                .clotheName(inventory.getClothe().getTypeClothe().getName())
                .typeStageName(inventory.getClothe().getTypeStage().getName())
                .unitPrice(inventory.getUnit_price())
                .build();
    }

    @Override
    public MessageResponse createOrder(OrderRequest orderRequest) throws DataServiceException {
        Wardrope wardRope = wardRopeDataService.findWardRopeByUuid(orderRequest.getWardropeUuid());
        OrderState orderState = wardRopeDataService.findOrderStateByName(orderStateReceived);

        Order newOrder = Order.builder()
                .uuid(UUID.randomUUID())
                .order_date(LocalDate.now())
                .wardrope(wardRope)
                .orderState(orderState)
                .build();

        List<OrderList> orderLists = new ArrayList<>();

        for(ClotheRequest orderListRequest : orderRequest.getClothes()){
            Clothe clothe = clotheService.findClotheByUuid(orderListRequest.getClotheUuid());
            OrderList orderList = OrderList.builder()
                    .uuid(UUID.randomUUID())
                    .clothe(clothe)
                    .value_order(orderListRequest.getQuantity())
                    .build();

            orderLists.add(orderList);
        }

        OrderDataRequest or = OrderDataRequest.builder()
                .order(newOrder)
                .orderList(orderLists)
                .build();

        wardRopeDataService.saveOrder(or);

        return new MessageResponse("Order saved successfully", null, newOrder.getUuid());
    }

}
