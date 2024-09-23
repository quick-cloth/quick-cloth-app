package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.IClotheBankDataService;
import org.example.quickclothapp.dataservice.intf.ILocationDataService;
import org.example.quickclothapp.exception.ClotheBankServiceException;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.CampaignRequest;
import org.example.quickclothapp.payload.request.ClotheBankRequest;
import org.example.quickclothapp.payload.request.EmailRequest;
import org.example.quickclothapp.payload.response.CampaignResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.service.intf.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClotheBankService implements IClotheBankService {
    private final IClotheBankDataService clotheBankDataService;
    private final ILocationDataService locationDataService;
    private final IClotheService clotheService;
    private final IUserService userService;
    private final IEmailService emailService;


    public ClotheBankService(IClotheBankDataService clotheBankDataService, ILocationDataService locationDataService, IClotheService clotheService, IUserService userService, IEmailService emailService) {
        this.clotheBankDataService = clotheBankDataService;
        this.locationDataService = locationDataService;
        this.clotheService = clotheService;
        this.userService = userService;
        this.emailService = emailService;
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
}
