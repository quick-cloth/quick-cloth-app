package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IClotheBankDataService;
import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.dataservice.intf.IWardRopeDataService;
import org.example.quickclothapp.exception.ClotheBankServiceException;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.*;
import org.example.quickclothapp.payload.response.CampaignResponse;
import org.example.quickclothapp.payload.response.DonationResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.OrderResponse;
import org.example.quickclothapp.service.intf.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ClotheBankService implements IClotheBankService {
    private final IClotheBankDataService clotheBankDataService;
    private final ILocationDataService locationDataService;
    private final IClotheService clotheService;
    private final IUserService userService;
    private final IEmailService emailService;
    private final IWardRopeDataService wardRopeDataService;

    @Value("${api-server-order-state-on-way}")
    private String orderStateOnWay;


    public ClotheBankService(IClotheBankDataService clotheBankDataService, ILocationDataService locationDataService, IClotheService clotheService, IUserService userService, IEmailService emailService, IWardRopeDataService wardRopeDataService) {
        this.clotheBankDataService = clotheBankDataService;
        this.locationDataService = locationDataService;
        this.clotheService = clotheService;
        this.userService = userService;
        this.emailService = emailService;
        this.wardRopeDataService = wardRopeDataService;
    }

    @Override
    public MessageResponse saveClotheBank(ClotheBankRequest clotheBank) throws DataServiceException {
        City city = locationDataService.findCityByUuid(clotheBank.getCityUuid());

        ClotheBank newClotheBank = ClotheBank.builder()
                .uuid(UUID.randomUUID())
                .name(clotheBank.getName())
                .address(clotheBank.getAddress())
                .city(city)
                .build();

        clotheBankDataService.saveClotheBank(newClotheBank);

        return new MessageResponse("Clothe Bank saved successfully", null, newClotheBank.getUuid());

    }

    @Override
    public ClotheBank findClotheBankByUuid(UUID uuid) throws DataServiceException {
        return clotheBankDataService.findClotheBankByUuid(uuid);
    }

    @Override
    public MessageResponse saveCampaign(CampaignRequest campaignRequest) throws DataServiceException, ClotheBankServiceException {
        ClotheBank clotheBank = clotheBankDataService.findClotheBankByUuid(campaignRequest.getClotheBankUuid());
        TypeCampaign typeCampaign = clotheBankDataService.findTypeCampaignByUuid(campaignRequest.getTypeCampaignUuid());
        TypeClothe typeClothe = null;
        TypeGender typeGender = null;
        TypeStage typeStage = null;

        if (campaignRequest.getTypeClotheUuid() != null){
            typeClothe = clotheService.findTypeClotheByUuid(campaignRequest.getTypeClotheUuid());
        }
        if (campaignRequest.getTypeGenderUuid() != null){
            typeGender = clotheService.findTypeGenderByUuid(campaignRequest.getTypeGenderUuid());
        }
        if (campaignRequest.getTypeStageUuid() != null){
            typeStage = clotheService.findTypeStageByUuid(campaignRequest.getTypeStageUuid());
        }

        if (campaignRequest.getEnd_date().isBefore(campaignRequest.getCreation_date())){
            throw new ClotheBankServiceException("La fecha del fin de la campaña debe ser mayor al fecha de incio", 400);
        }

        Campaign newCampaign = Campaign.builder()
                .uuid(UUID.randomUUID())
                .name(campaignRequest.getName())
                .message_campaign(campaignRequest.getMessage_campaign())
                .creation_date(campaignRequest.getCreation_date())
                .end_date(campaignRequest.getEnd_date())
                .clotheBank(clotheBank)
                .typeCampaign(typeCampaign)
                .typeClothe(typeClothe)
                .typeGender(typeGender)
                .typeStage(typeStage)
                .discount(campaignRequest.getDiscount())
                .build();

        clotheBankDataService.saveCampaign(newCampaign);

        //enviar la campaña a todos los clientes
        sendCampaingToAllClients(newCampaign);

        return new MessageResponse("Campaign saved successfully", null, newCampaign.getUuid());
    }

    @Override
    public List<Campaign> findCampaignsByClotheBankUuid(UUID uuid) throws DataServiceException {
        return clotheBankDataService.findCampaignsByClotheBankUuid(uuid, null, null);
    }

    @Override
    public List<CampaignResponse> findAllCampaignsByClotheBankUuid(UUID clotheBankUuid, LocalDate startDate, LocalDate endDate) throws DataServiceException {
        List<Campaign> campaigns = clotheBankDataService.findCampaignsByClotheBankUuid(clotheBankUuid, startDate, endDate);

        List<CampaignResponse> campaignResponses = new ArrayList<>();

        for(Campaign c :  campaigns){
            CampaignResponse cr = CampaignResponse.builder()
                    .uuid(c.getUuid())
                    .campaignName(c.getName())
                    .startDate(c.getCreation_date())
                    .endDate(c.getEnd_date())
                    .build();
            cr.setDiscount(c.getDiscount());
            campaignResponses.add(cr);
        }

        return campaignResponses;
    }

    @Override
    public List<TypeCampaign> findAllTypeCampaign() throws DataServiceException {
        return clotheBankDataService.findAllTypeCampaign();
    }

    @Async
    public void sendCampaingToAllClients(Campaign campaign) throws DataServiceException {
        List<User> clientsUsers = userService.getAllClientsUsers();

        for(User us : clientsUsers){
            EmailRequest er = EmailRequest.builder()
                    .subject("Nueva Campaña")
                    .to(us.getEmail())
                    .build();
            emailService.sendEmailNewCampaign(er, us, campaign);
        }
    }

    @Override
    public MessageResponse saveDonation(DonationRequest donationRequest) throws DataServiceException{
        ClotheBank clotheBank = clotheBankDataService.findClotheBankByUuid(donationRequest.getClotheBankUuid());
        User user = null;
        if (donationRequest.getUserUuid() != null){
            user = userService.findUserByUuid(donationRequest.getUserUuid());
        }

        for(ClotheDonationRequest cd : donationRequest.getClothesDonation()){
            Clothe clothe = null;

            clothe = clotheService.findClotheByAllTypes(
                    cd.getTypeClotheUuid(),
                    cd.getTypeGenderUuid(),
                    cd.getTypeStageUuid()
            );


            if (clothe == null){
                clothe = clotheService.saveClothe(
                        cd.getTypeClotheUuid(),
                        cd.getTypeGenderUuid(),
                        cd.getTypeStageUuid()
                );
            }

            Donation donation = Donation.builder()
                    .uuid(UUID.randomUUID())
                    .clothe_bank(clotheBank)
                    .clothe(clothe)
                    .user(user)
                    .creation_date(LocalDate.now())
                    .quantity(cd.getQuantity())
                    .build();

            clotheBankDataService.saveDonation(donation);
        }

        return new MessageResponse("Donations saved successfully", null, null);
    }

    @Override
    public List<DonationResponse> findDonationByClotheBankUuid(UUID clotheBankUuid) throws DataServiceException {
        List<Donation> donations = clotheBankDataService.findDonationByClotheBankUuid(clotheBankUuid);

        List<DonationResponse> danationResponses = new ArrayList<>();

        for(Donation d : donations){
            DonationResponse dr = DonationResponse.builder()
                    .uuid(d.getUuid())
                    .donationDate(d.getCreation_date())
                    .quantity(d.getQuantity())
                    .donorName(d.getUser() != null ? d.getUser().getName() : "Anonimo")
                    .build();
            danationResponses.add(dr);
        }

        return danationResponses;
    }

    @Override
    public List<OrderResponse> findOrdersByClotheBankUuid(UUID clotheBankUuid, UUID orderStateUuid, UUID wardRobeUuid) throws DataServiceException {
        List<Order> orders = clotheBankDataService.findOrdersByClotheBankUuid(clotheBankUuid, orderStateUuid, wardRobeUuid);
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order o : orders){
            List<OrderList> orderList = clotheBankDataService.findOrderListByOrder(o.getUuid());
            int quantity = orderList.stream().mapToInt(OrderList::getValue_order).sum();
            orderResponses.add(
                    OrderResponse.builder()
                            .uuid(o.getUuid())
                            .quantity(quantity)
                            .wardrobeName(o.getWardrobe().getName())
                            .orderDate(o.getOrder_date())
                            .status(o.getOrderState().getName())
                            .build()
            );
        }

        return orderResponses;
    }

    @Override
    public MessageResponse responseOrder(OrderRequest orderRequest, UUID orderUuid) throws DataServiceException {
        Order order = clotheBankDataService.findOrderByUuid(orderUuid);
        OrderState orderState = wardRopeDataService.findOrderStateByName(orderStateOnWay);

        Map<UUID, Integer> mapClothesRequest = new HashMap<>();

        List<OrderList> orderList = clotheBankDataService.findOrderListByOrder(order.getUuid());

        for(ClotheRequest cl : orderRequest.getClothes()){
            mapClothesRequest.put(cl.getClotheUuid(), cl.getQuantity());
        }

        for (OrderList orl : orderList){
            int quantity = mapClothesRequest.get(orl.getClothe().getUuid());
            orl.setDelivery_value(quantity);
        }

        order.setOrderState(orderState);

        OrderDataRequest orderDataRequest = OrderDataRequest.builder()
                .order(order)
                .orderList(orderList)
                .build();

        wardRopeDataService.saveOrder(orderDataRequest);

        return new MessageResponse("Order response successfully with state : " + orderStateOnWay, null, order.getUuid());
    }

    @Override
    public Order findOrderByUuid(UUID orderUuid) throws DataServiceException {
        return clotheBankDataService.findOrderByUuid(orderUuid);
    }

    @Override
    public List<OrderList> findOrderListByOrder(UUID uuid) throws DataServiceException {
        return clotheBankDataService.findOrderListByOrder(uuid);
    }
}
