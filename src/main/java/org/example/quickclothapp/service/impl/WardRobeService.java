package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.dataservice.intf.IUserDataService;
import org.example.quickclothapp.dataservice.intf.IWardRopeDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.exception.WardRopeServiceExpetion;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.*;
import org.example.quickclothapp.payload.response.*;
import org.example.quickclothapp.service.intf.IClotheBankService;
import org.example.quickclothapp.service.intf.IClotheService;
import org.example.quickclothapp.service.intf.IEmailService;
import org.example.quickclothapp.service.intf.IWardRobeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WardRobeService implements IWardRobeService {

    private final IWardRopeDataService wardRopeDataService;
    private final ILocationDataService locationDataService;
    private final IClotheBankService clotheBankService;
    private final IUserDataService userDataService;
    private final IClotheService clotheService;
    private final IEmailService emailService;
    private static final int AMOUNT_PER_POINT = 1000;
    private static final int POINTS_PER_1000 = 4;
    private static final int MINIMUM_POINTS = 80;
    @Value("${api-server-order-state-received}")
    private String orderStateReceived;

    public WardRobeService(IWardRopeDataService wardRopeDataService, ILocationDataService locationDataService, IClotheBankService clotheBankService, IUserDataService userDataService, IClotheService clotheService, IEmailService emailService) {
        this.wardRopeDataService = wardRopeDataService;
        this.locationDataService = locationDataService;
        this.clotheBankService = clotheBankService;
        this.userDataService = userDataService;
        this.clotheService = clotheService;
        this.emailService = emailService;
    }

    @Override
    public MessageResponse saveWardRope(WardRobeRequest wardRopeRequest) throws DataServiceException {
        City city = locationDataService.findCityByUuid(wardRopeRequest.getCityUuid());
        ClotheBank clotheBank = clotheBankService.findClotheBankByUuid(wardRopeRequest.getClotheBankUuid());

        Wardrobe newWardRope = Wardrobe.builder()
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
    public SaleResponse checkValueSale(SaleRequest sale, boolean payPoints) throws DataServiceException, WardRopeServiceExpetion {
        Wardrobe wardRope = wardRopeDataService.findWardRopeByUuid(sale.getWardRopeUuid());
        List<Campaign> campaigns = clotheBankService.findCampaignsByClotheBankUuid(wardRope.getClotheBank().getUuid());
        User user = userDataService.findUserByUuid(sale.getUserUuid());

        if (payPoints) {
            if (user.getPoints() < MINIMUM_POINTS) {
                throw new WardRopeServiceExpetion("User does not have enough points to pay", 400);
            }
        }

        boolean isAllSaleDiscounted = true;
        LocalDate currentDate = LocalDate.now();

        List<SaleListResponse> saleListResponses = new ArrayList<>();

        for (SaleListRequest saleListRequest : sale.getSaleList()) {
            Clothe clothe = clotheService.findClotheByUuid(saleListRequest.getClotheUuid());

            SaleListResponse slr = SaleListResponse.builder()
                    .clotheName(clothe.getTypeClothe().getName())
                    .typeGenderName(clothe.getTypeGender().getName())
                    .typeStageName(clothe.getTypeStage().getName())
                    .quantity(saleListRequest.getQuantity())
                    .value(Double.valueOf(saleListRequest.getValue()) * saleListRequest.getQuantity())
                    .campaignList(new ArrayList<>())
                    .build();

            saleListResponses.add(slr);
        }

        for (Campaign c : campaigns) {
            if (currentDate.isBefore(c.getEnd_date())) {
                for (SaleListResponse slr : saleListResponses) {
                    if (c.getTypeClothe() != null) {
                        if (slr.getClotheName().equals(c.getTypeClothe().getName())) {
                            calculateDiscount(slr, c);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (c.getTypeGender() != null) {
                        if (slr.getTypeGenderName().equals(c.getTypeGender().getName())) {
                            calculateDiscount(slr, c);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (c.getTypeStage() != null) {
                        if (slr.getTypeStageName().equals(c.getTypeStage().getName())) {
                            calculateDiscount(slr, c);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (isAllSaleDiscounted) {
                        calculateDiscount(slr, c);
                    }
                }
            }
        }

        double totalValue = saleListResponses.stream().mapToDouble(SaleListResponse::getValue).sum();
        double payPointsValue = 0.0;
        int points = 0;

        if (payPoints) {
            payPointsValue = user.getPoints() * (double) AMOUNT_PER_POINT / POINTS_PER_1000;

            if (payPointsValue > totalValue) {
                payPointsValue = payPointsValue - totalValue;
                totalValue = 0.0;
            } else {
                totalValue = totalValue - payPointsValue;
            }

            points = (int) ((payPointsValue / AMOUNT_PER_POINT) * POINTS_PER_1000);
            user.setPoints(user.getPoints() - points);
        }

        return SaleResponse.builder()
                .totalValue(totalValue)
                .saleList(saleListResponses)
                .payPointsValue(payPointsValue * -1)
                .points(user.getPoints())
                .build();
    }

    private void calculateDiscount(SaleListResponse slr, Campaign c) {
        double discount = (slr.getValue() * c.getDiscount()) / 100;
        slr.setValue(slr.getValue() - discount);

        CampaignResponse cr = CampaignResponse.builder()
                .campaignName(c.getName())
                .valueDiscount(discount * -1)
                .discount(c.getDiscount())
                .build();
        slr.getCampaignList().add(cr);
    }

    @Override
    public MessageResponse saveSale(SaleRequest saleRequest, boolean payPoints) throws DataServiceException {
        Wardrobe wardRope = wardRopeDataService.findWardRopeByUuid(saleRequest.getWardRopeUuid());
        User user = userDataService.findUserByUuid(saleRequest.getUserUuid());
        List<Campaign> campaigns = clotheBankService.findCampaignsByClotheBankUuid(wardRope.getClotheBank().getUuid());
        boolean isAllSaleDiscounted = true;


        Sale newSale = Sale.builder()
                .uuid(UUID.randomUUID())
                .sale_date(LocalDate.now())
                .wardrobe(wardRope)
                .user(user)
                .build();

        List<SaleList> saleLists = new ArrayList<>();

        for (SaleListRequest saleListRequest : saleRequest.getSaleList()) {
            Clothe clothe = clotheService.findClotheByUuid(saleListRequest.getClotheUuid());
            SaleList saleList = SaleList.builder()
                    .uuid(UUID.randomUUID())
                    .clothe(clothe)
                    .value(saleListRequest.getValue() * saleListRequest.getQuantity())
                    .quantity(saleListRequest.getQuantity())
                    .build();

            saleLists.add(saleList);
        }
        List<CampaignResponse> campaignResponses = new ArrayList<>();

        for (Campaign c : campaigns) {
            if (newSale.getSale_date().isBefore(c.getEnd_date())) {
                for (SaleList slr : saleLists) {
                    if (c.getTypeClothe() != null) {
                        if (slr.getClothe().getTypeClothe().getName().equals(c.getTypeClothe().getName())) {
                            calculateDiscountSale(slr, c, campaignResponses);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (c.getTypeGender() != null) {
                        if (slr.getClothe().getTypeGender().getName().equals(c.getTypeGender().getName())) {
                            calculateDiscountSale(slr, c, campaignResponses);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (c.getTypeStage() != null) {
                        if (slr.getClothe().getTypeStage().getName().equals(c.getTypeStage().getName())) {
                            calculateDiscountSale(slr, c, campaignResponses);
                            isAllSaleDiscounted = false;
                        }
                    }
                    if (isAllSaleDiscounted) {
                        calculateDiscountSale(slr, c, campaignResponses);
                    }
                }
            }
        }

        double totalValue = saleLists.stream().mapToDouble(SaleList::getValue).sum();
        double payPointsValue = 0.0;
        int points = 0;
        newSale.setValue(BigInteger.valueOf((long) totalValue));

        int newPoints = newSale.getValue().divide(BigInteger.valueOf(AMOUNT_PER_POINT)).intValue() * POINTS_PER_1000;
        user.setPoints(user.getPoints() + newPoints);

        if (payPoints) {
            payPointsValue = user.getPoints() * (double) AMOUNT_PER_POINT / POINTS_PER_1000;

            if (payPointsValue > totalValue) {
                payPointsValue = payPointsValue - totalValue;
                totalValue = 0.0;
            } else {
                totalValue = totalValue - payPointsValue;
            }

            points = (int) ((payPointsValue / AMOUNT_PER_POINT) * POINTS_PER_1000);
            user.setPoints(user.getPoints() - points);
        }

        newSale.setValue(BigInteger.valueOf((long) totalValue));

        SaleDataRequest sdr = SaleDataRequest.builder()
                .sale(newSale)
                .saleListRequests(saleLists)
                .build();

        //wardRopeDataService.saveSale(sdr);

        sendEmailNewSale(newSale, saleLists, campaignResponses);

        return new MessageResponse("Sale saved successfully", null, newSale.getUuid());

    }

    @Async
    public void sendEmailNewSale(Sale sale, List<SaleList> saleLists, List<CampaignResponse> campaignResponses){
        EmailRequest emailRequest = EmailRequest.builder()
                .to(sale.getUser().getEmail())
                .subject("Venta realizada")
                .build();
        emailService.sendEmailNewSale(sale, saleLists, emailRequest, campaignResponses);
    }

    private void calculateDiscountSale(SaleList slr, Campaign c, List<CampaignResponse> campaignResponses) {
        double discount = (double) (slr.getValue() * c.getDiscount()) / 100;
        slr.setValue((int) (slr.getValue() - discount));

        CampaignResponse cr = CampaignResponse.builder()
                .campaignName(c.getName())
                .valueDiscount(discount * -1)
                .description(c.getMessage_campaign())
                .discount(c.getDiscount())
                .build();

        campaignResponses.add(cr);
    }

    @Override
    public MessageResponse updateWardRope(WardRobeRequest wardrobe) throws DataServiceException {
        Wardrobe wardRope = wardRopeDataService.findWardRopeByUuid(wardrobe.getUuid());
        City city = locationDataService.findCityByUuid(wardrobe.getCityUuid());

        wardRope.setName(wardrobe.getName());
        wardRope.setAddress(wardrobe.getAddress());
        wardRope.setCity(city);

        wardRopeDataService.saveWardrope(wardRope);

        return new MessageResponse("Ward Rope updated successfully", null, wardRope.getUuid());
    }

    @Override
    public Wardrobe findWardRopeByUuid(String uuid) throws DataServiceException {
        return wardRopeDataService.findWardRopeByUuid(UUID.fromString(uuid));
    }

    @Override
    public WardRobeResponse findWardRopeByUuid(UUID uuid) throws DataServiceException {
        Wardrobe wardRope = wardRopeDataService.findWardRopeByUuid(uuid);

        return WardRobeResponse.builder()
                .uuid(wardRope.getUuid())
                .city(wardRope.getCity().getName())
                .address(wardRope.getAddress())
                .build();
    }

    @Override
    public List<InventoryResponse> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException {
        List<Inventory> inventories = wardRopeDataService.findInventoriesByWardRopeUuid(wardRopeUuid);

        List<InventoryResponse> inventoryResponses = new ArrayList<>();

        for (Inventory i : inventories) {
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

        return InventoryResponse.builder()
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
        Wardrobe wardRope = wardRopeDataService.findWardRopeByUuid(orderRequest.getWardropeUuid());
        OrderState orderState = wardRopeDataService.findOrderStateByName(orderStateReceived);

        Order newOrder = Order.builder()
                .uuid(UUID.randomUUID())
                .order_date(LocalDate.now())
                .wardrobe(wardRope)
                .orderState(orderState)
                .build();

        List<OrderList> orderLists = new ArrayList<>();

        for (ClotheRequest orderListRequest : orderRequest.getClothes()) {
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

    @Override
    public List<WardRobeResponse> finAllWardRobeByClotheBankUuid(UUID clotheBankUuid) throws DataServiceException {
        List<Wardrobe> wardRopes = wardRopeDataService.finAllWardRopeByClotheBankUuid(clotheBankUuid);
        List<WardRobeResponse> wardRobeResponses = new ArrayList<>();

        for (Wardrobe wr : wardRopes) {
            List<Inventory> inventories = wardRopeDataService.findInventoriesByWardRopeUuid(wr.getUuid());

            int totalStock = 0;

            for (Inventory i : inventories) {
                totalStock += i.getStock();
            }

            List<Sale> sales = wardRopeDataService.findSalesByWardRopeUuid(wr.getUuid());

            double totalSales = 0;

            for (Sale s : sales) {
                totalSales += s.getValue().doubleValue();
            }

            WardRobeResponse wrr = WardRobeResponse.builder()
                    .uuid(wr.getUuid())
                    .name(wr.getName())
                    .address(wr.getAddress())
                    .stock(totalStock)
                    .valueSales(String.valueOf(totalSales))
                    .city(wr.getCity().getName())
                    .build();

            wardRobeResponses.add(wrr);
        }

        return wardRobeResponses;
    }

    @Override
    public List<OrderState> getAllOrderStates() throws DataServiceException{
        return wardRopeDataService.findAllOrderStates();
    }

}
