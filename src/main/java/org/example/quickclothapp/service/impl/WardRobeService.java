package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.impl.ClotheDataService;
import org.example.quickclothapp.dataservice.intf.IClotheDataService;
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
import java.util.*;

@Service
public class WardRobeService implements IWardRobeService {

    private final IWardRopeDataService wardRopeDataService;
    private final IClotheDataService clotheDataService;
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
    @Value("${api-server-order-state-delivered}")
    private String orderStateDelivered;

    public WardRobeService(IWardRopeDataService wardRopeDataService, ILocationDataService locationDataService, IClotheBankService clotheBankService, IUserDataService userDataService, IClotheService clotheService, IEmailService emailService, IClotheDataService clotheDataService) {
        this.wardRopeDataService = wardRopeDataService;
        this.locationDataService = locationDataService;
        this.clotheBankService = clotheBankService;
        this.userDataService = userDataService;
        this.clotheService = clotheService;
        this.emailService = emailService;
        this.clotheDataService = clotheDataService;
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

        for (SaleListResponse slr : saleListResponses){

            for(Campaign c : campaigns){
                if (currentDate.isBefore(c.getEnd_date())){

                    if(c.getTypeClothe() != null && c.getTypeGender() != null && c.getTypeStage() != null){
                        if(slr.getClotheName().equals(c.getTypeClothe().getName()) && slr.getTypeGenderName().equals(c.getTypeGender().getName()) && slr.getTypeStageName().equals(c.getTypeStage().getName())){
                            calculateDiscount(slr, c);
                        }
                    }
                    if (c.getTypeClothe() == null &&  c.getTypeGender() != null && c.getTypeStage() != null){
                        if(slr.getTypeGenderName().equals(c.getTypeGender().getName()) && slr.getTypeStageName().equals(c.getTypeStage().getName())){
                            calculateDiscount(slr, c);
                        }

                    }
                    if(c.getTypeClothe() != null && c.getTypeGender() == null && c.getTypeStage() != null){
                        if(slr.getClotheName().equals(c.getTypeClothe().getName()) && slr.getTypeStageName().equals(c.getTypeStage().getName())){
                            calculateDiscount(slr, c);
                        }

                    }
                    if(c.getTypeClothe() == null && c.getTypeGender() == null && c.getTypeStage() == null){
                        calculateDiscount(slr, c);
                    }
                    if(c.getTypeClothe() != null && c.getTypeGender() != null && c.getTypeStage() == null){
                        if(slr.getClotheName().equals(c.getTypeClothe().getName()) && slr.getTypeGenderName().equals(c.getTypeGender().getName())){
                            calculateDiscount(slr, c);
                        }
                    }
                    if(c.getTypeClothe() == null && c.getTypeGender() != null && c.getTypeStage() == null){
                        if(slr.getTypeGenderName().equals(c.getTypeGender().getName())){
                            calculateDiscount(slr, c);
                        }
                    }
                    if (c.getTypeClothe() != null && c.getTypeGender() == null && c.getTypeStage() == null){
                        if(slr.getClotheName().equals(c.getTypeClothe().getName())){
                            calculateDiscount(slr, c);
                        }
                    }
                    if(c.getTypeClothe() == null && c.getTypeGender() == null && c.getTypeStage() != null) {
                        if (slr.getTypeStageName().equals(c.getTypeStage().getName())) {
                            calculateDiscount(slr, c);
                        }
                    }
                }
            }
        }

        double totalValue = saleListResponses.stream().mapToDouble(SaleListResponse::getValue).sum();
        double payPointsValue = 0.0;
        int valuePoints = 0;

        if (payPoints) {
            valuePoints = (int) ((totalValue/AMOUNT_PER_POINT) * POINTS_PER_1000);

            if (user.getPoints() > valuePoints) {
                user.setPoints(user.getPoints() - valuePoints);
                totalValue = 0.0;
            } else {
                valuePoints = valuePoints - user.getPoints();
                user.setPoints(0);
                totalValue = totalValue - (double) (valuePoints * AMOUNT_PER_POINT) / POINTS_PER_1000;;
            }
        }else{
            int newPoints = (int) ((totalValue/AMOUNT_PER_POINT) * POINTS_PER_1000);
            user.setPoints(user.getPoints() + newPoints);
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
                .description(c.getMessage_campaign())
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

        for(SaleList sl : saleLists){

            for(Campaign c : campaigns){
                if(newSale.getSale_date().isBefore(c.getEnd_date())){
                    if(c.getTypeClothe() != null && c.getTypeGender() != null && c.getTypeStage() != null){
                        if(sl.getClothe().getTypeClothe().getName().equals(c.getTypeClothe().getName()) && sl.getClothe().getTypeGender().getName().equals(c.getTypeGender().getName()) && sl.getClothe().getTypeStage().getName().equals(c.getTypeStage().getName())){
                            calculateDiscountSale(sl, c, campaignResponses);
                        }
                    }
                    if (c.getTypeClothe() == null &&  c.getTypeGender() != null && c.getTypeStage() != null){
                        if(sl.getClothe().getTypeGender().getName().equals(c.getTypeGender().getName()) && sl.getClothe().getTypeStage().getName().equals(c.getTypeStage().getName())){
                            calculateDiscountSale(sl, c, campaignResponses);
                        }

                    }
                    if(c.getTypeClothe() != null && c.getTypeGender() == null && c.getTypeStage() != null){
                        if(sl.getClothe().getTypeClothe().getName().equals(c.getTypeClothe().getName()) && sl.getClothe().getTypeStage().getName().equals(c.getTypeStage().getName())){
                            calculateDiscountSale(sl, c, campaignResponses);
                        }

                    }
                    if(c.getTypeClothe() == null && c.getTypeGender() == null && c.getTypeStage() == null){
                        calculateDiscountSale(sl, c, campaignResponses);
                    }
                    if(c.getTypeClothe() != null && c.getTypeGender() != null && c.getTypeStage() == null){
                        if(sl.getClothe().getTypeClothe().getName().equals(c.getTypeClothe().getName()) && sl.getClothe().getTypeGender().getName().equals(c.getTypeGender().getName())){
                            calculateDiscountSale(sl, c, campaignResponses);
                        }
                    }
                    if(c.getTypeClothe() == null && c.getTypeGender() != null && c.getTypeStage() == null){
                        if(sl.getClothe().getTypeGender().getName().equals(c.getTypeGender().getName())){
                            calculateDiscountSale(sl, c, campaignResponses);
                        }
                    }
                    if (c.getTypeClothe() != null && c.getTypeGender() == null && c.getTypeStage() == null){
                        if(sl.getClothe().getTypeClothe().getName().equals(c.getTypeClothe().getName())){
                            calculateDiscountSale(sl, c, campaignResponses);
                        }
                    }
                    if(c.getTypeClothe() == null && c.getTypeGender() == null && c.getTypeStage() != null) {
                        if (sl.getClothe().getTypeStage().getName().equals(c.getTypeStage().getName())) {
                            calculateDiscountSale(sl, c, campaignResponses);
                        }
                    }
                }
            }
        }

        double totalValue = saleLists.stream().mapToDouble(SaleList::getValue).sum();
        int valuePoints = 0;
        int usedPoints = 0;
        newSale.setValue(BigInteger.valueOf((long) totalValue));

        if (payPoints) {

            valuePoints = (int) ((totalValue/AMOUNT_PER_POINT) * POINTS_PER_1000);

            if (user.getPoints() > valuePoints) {
                user.setPoints(user.getPoints() - valuePoints);
                usedPoints = valuePoints;
                totalValue = 0.0;
            } else {

                valuePoints = valuePoints - user.getPoints();
                usedPoints = user.getPoints();
                user.setPoints(0);
                totalValue = totalValue - (double) (valuePoints * AMOUNT_PER_POINT) / POINTS_PER_1000;;
            }
        }
        else {
            int newPoints = (int) ((totalValue/AMOUNT_PER_POINT) * POINTS_PER_1000);
            user.setPoints(user.getPoints() + newPoints);
        }

        newSale.setValue(BigInteger.valueOf((long) totalValue));
        newSale.setPay_points(usedPoints);

        SaleDataRequest sdr = SaleDataRequest.builder()
                .sale(newSale)
                .saleListRequests(saleLists)
                .build();

        wardRopeDataService.saveSale(sdr);

        sendEmailNewSale(newSale, saleLists, campaignResponses);

        return new MessageResponse("Sale saved successfully", null, newSale.getUuid());

    }

    @Async
    public void sendEmailNewSale(Sale sale, List<SaleList> saleLists, List<CampaignResponse> campaignResponses) throws DataServiceException {
        EmailRequest emailRequest = EmailRequest.builder()
                .to(sale.getUser().getEmail())
                .subject("Venta realizada")
                .build();
        emailService.sendEmailNewSale(sale, saleLists, emailRequest, campaignResponses);

        SendEmail sendEmail = SendEmail.builder()
                .uuid(UUID.randomUUID())
                .email(emailRequest.getTo())
                .send_date(LocalDate.now())
                .build();

        wardRopeDataService.saveSendEmail(sendEmail);
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
        List<Sale> sales = wardRopeDataService.findSalesByWardRopeUuid(uuid);

        BigInteger totalSales = BigInteger.ZERO;
        Integer totalStockSold = 0;
        int totalStock = 0;

        List<Inventory> inventories = wardRopeDataService.findInventoriesByWardRopeUuid(wardRope.getUuid());

        for (Inventory i : inventories) {
            totalStock += i.getStock();
        }


        for (Sale s : sales){
            totalSales = totalSales.add(s.getValue());
            List<SaleList> saleLists = wardRopeDataService.findSaleListBySaleUuid(s.getUuid());

            for(SaleList sl : saleLists){
                totalStockSold += sl.getQuantity();
            }
        }


        return WardRobeResponse.builder()
                .uuid(wardRope.getUuid())
                .city(wardRope.getCity())
                .name(wardRope.getName())
                .address(wardRope.getAddress())
                .valueSales(String.valueOf(totalSales))
                .unitSold(totalStockSold)
                .stock(totalStock)
                .build();
    }

    @Override
    public List<InventoryResponse> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException {
        List<Inventory> inventories = wardRopeDataService.findInventoriesByWardRopeUuid(wardRopeUuid);
        List<InventoryResponse> inventoryResponses = new ArrayList<>();


        for (Inventory i : inventories){
            inventoryResponses.add(
              InventoryResponse.builder()
                      .uuid(i.getUuid())
                      .stock(i.getStock())
                      .clothe(i.getClothe())
                      .minimumStock(i.getMinimum_stock())
                      .build()
            );
        }
        return inventoryResponses;
    }

    @Override
    public InventoryResponse findInventoryByClotheUuidAndWardRopeUuid(UUID clotheUuid, UUID wardRopeUuid) throws DataServiceException {
        Inventory inventory = wardRopeDataService.findInventoryByClotheUuidAndWardRopeUuid(clotheUuid, wardRopeUuid);

        return InventoryResponse.builder()
                .clothe(inventory.getClothe())
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
                    .city(wr.getCity())
                    .build();

            wardRobeResponses.add(wrr);
        }

        return wardRobeResponses;
    }

    @Override
    public List<OrderState> getAllOrderStates() throws DataServiceException{
        return wardRopeDataService.findAllOrderStates();
    }

    @Override
    public List<SaleWardRobeResponse> findSalesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException {
        List<Sale> sales = wardRopeDataService.findSalesByWardRopeUuid(wardRopeUuid);
        List<SaleWardRobeResponse> saleWardRobeResponses = new ArrayList<>();

        for(Sale s: sales){
            List<SaleList> saleLists = wardRopeDataService.findSaleListBySaleUuid(s.getUuid());
            int quantity = 0;

            for (SaleList sl : saleLists){
                quantity += sl.getQuantity();
            }
            SaleWardRobeResponse swr = SaleWardRobeResponse.builder()
                    .uuid(s.getUuid())
                    .date(s.getSale_date())
                    .quantity(quantity)
                    .payPoints(String.valueOf(s.getPay_points()))
                    .price(s.getValue().toString())
                    .build();

            saleWardRobeResponses.add(swr);
        }
        return saleWardRobeResponses;
    }

    @Override
    public SaleWardRobeResponse findSaleByUuid(UUID uuid) throws DataServiceException {
        List<SaleList> saleLists = wardRopeDataService.findSaleListBySaleUuid(uuid);
        List<SaleListWardRobeResponse> saleListWardRobeResponses = new ArrayList<>();
        SaleWardRobeResponse saleWardRobeResponse = SaleWardRobeResponse.builder()
                .uuid(saleLists.get(0).getSale().getUuid())
                .price(saleLists.get(0).getSale().getValue().toString())
                .date(saleLists.get(0).getSale().getSale_date())
                .payPoints(String.valueOf(saleLists.get(0).getSale().getPay_points()))
                .saleList(saleListWardRobeResponses)
                .build();

        for (SaleList sl : saleLists){
            SaleListWardRobeResponse slwr = SaleListWardRobeResponse.builder()
                    .clotheName(sl.getClothe().getTypeClothe().getName())
                    .quantity(sl.getQuantity())
                    .value(String.valueOf(sl.getValue()))
                    .typeGenderName(sl.getClothe().getTypeGender().getName())
                    .typeStageName(sl.getClothe().getTypeStage().getName())
                    .build();
            saleWardRobeResponse.getSaleList().add(slwr);
        }

        return saleWardRobeResponse;
    }
    
    @Override
    public MessageResponse confirmOrder(OrderRequest orderRequest, UUID orderUuid) throws DataServiceException {
        Order order = clotheBankService.findOrderByUuid(orderUuid);

        OrderState orderState = wardRopeDataService.findOrderStateByName(orderStateDelivered);
        
        Map<UUID, Integer> mapClothesRequest = new HashMap<>();

        List<OrderList> orderList = clotheBankService.findOrderListByOrder(order.getUuid());

        for (ClotheRequest cr : orderRequest.getClothes()) {
            mapClothesRequest.put(cr.getClotheUuid(), cr.getQuantity());
        }

        for(OrderList orl : orderList){
            int quantity = mapClothesRequest.get(orl.getClothe().getUuid());
            orl.setDelivery_value(quantity);
        }

        order.setOrderState(orderState);
        order.setDelivery_date(LocalDate.now());

        OrderDataRequest or = OrderDataRequest.builder()
                .order(order)
                .orderList(orderList)
                .build();

        wardRopeDataService.confirmOrder(or);
        
        // Get the clothes uuids from the order request
        List<UUID> clotheUuids = new ArrayList<>();
        
        for (ClotheRequest cr : orderRequest.getClothes()) {
            clotheUuids.add(cr.getClotheUuid());
        }
        
        // Get the customers that have bought the same clothes as the current wardrobe
        List<CustomerResponse> customers = wardRopeDataService.findCustomersByWardrobeAndClothes(order.getWardrobe().getUuid(), clotheUuids);
        
        // Get the clothes from the clothe data service
        List<Clothe> clothes = clotheDataService.findByUuids(clotheUuids);
        
        // Send email to customers that have bought the same clothes as the current wardrobe
        emailService.sendEmailNewClothes(clothes, order.getWardrobe(), new EmailsRequest(
                customers.stream().map(CustomerResponse::getEmail).toArray(String[]::new),
                "Nuevo stock disponible en nuestra tienda",
                "Tenemos nuevos productos en nuestra tienda, ven a verlos"
        ) );
        

        return new MessageResponse("Order confirmation successfully with state : " + orderStateDelivered, null, order.getUuid());
    }

    @Override
    public OrderResponseWardRobe findOrderByUuid(UUID orderUuid) throws DataServiceException {
        Order order = clotheBankService.findOrderByUuid(orderUuid);
        List<OrderList> orderLists = clotheBankService.findOrderListByOrder(order.getUuid());

        List<OrderListResponse> orderListResponses = new ArrayList<>();

        for (OrderList ol : orderLists){
            OrderListResponse olr = OrderListResponse.builder()
                    .clotheName(ol.getClothe().getTypeClothe().getName())
                    .genderName(ol.getClothe().getTypeGender().getName())
                    .stageName(ol.getClothe().getTypeStage().getName())
                    .orderValue(ol.getValue_order())
                    .deliveryValue(ol.getDelivery_value() != null ? ol.getDelivery_value() : 0)
                    .build();
            orderListResponses.add(olr);
        }

        return OrderResponseWardRobe.builder()
                .uuid(order.getUuid())
                .orderValue(orderListResponses.stream().mapToInt(OrderListResponse::getOrderValue).sum())
                .deliveryValue(orderListResponses.stream().mapToInt(OrderListResponse::getDeliveryValue).sum())
                .orderDate(order.getOrder_date())
                .orderState(order.getOrderState().getName())
                .orderList(orderListResponses)
                .build();
    }

    @Override
    public List<OrderResponseWardRobe> findOrdersByWardRopeUuid(UUID wardRobeUuid) throws DataServiceException {
        List<Order> orders = wardRopeDataService.findOrdersByWardRobeUuid(wardRobeUuid);
        List<OrderResponseWardRobe> orderResponseWardRobes = new ArrayList<>();

        for(Order o : orders){
            List<OrderList> orderList = clotheBankService.findOrderListByOrder(o.getUuid());

            orderResponseWardRobes.add(
                    OrderResponseWardRobe.builder()
                            .uuid(o.getUuid())
                            .orderValue(orderList.stream().mapToInt(OrderList::getValue_order).sum())
                            .orderDate(o.getOrder_date())
                            .deliveryValue(orderList.stream().mapToInt(order -> Optional.ofNullable(order.getDelivery_value()).orElse(0)).sum())
                            .orderState(o.getOrderState().getName())
                            .build()
            );
        }

        return orderResponseWardRobes;
    }

    @Override
    public Clothe findInventoryByClotheUuid(UUID typeClotheUuid, UUID typeGenderUuid, UUID typeStageUuid, UUID wardRopeUuid) throws DataServiceException {
        Clothe clothe = clotheService.findClotheByAllTypes(typeClotheUuid, typeGenderUuid, typeStageUuid);

        if(clothe == null){
            clothe = clotheService.saveClothe(typeClotheUuid, typeGenderUuid, typeStageUuid);
        }

        Inventory i = wardRopeDataService.findInventoryByClotheUuidAndWardRopeUuid(clothe.getUuid(), wardRopeUuid);

        if (i == null){
            throw new DataServiceException("Inventory not found", 404);
        }

        return i.getClothe();
    }


    @Override
    public List<TopSellingClothes> getTopSellingClothes(UUID wardrobeUuid) throws DataServiceException {

        return wardRopeDataService.getTopSellingClothes(wardrobeUuid);
    }
    
    public int calculatePoints(double totalValue){
        return (int) ((totalValue/AMOUNT_PER_POINT) * POINTS_PER_1000);
    }

    @Override
    public List<CreateMinimumStockResponse> saveMinimumStocks(List<CreateMinimumStockRequest> minimumStocksRequest) throws DataServiceException {
        return wardRopeDataService.saveMinimumStocks(minimumStocksRequest);
    }

}
