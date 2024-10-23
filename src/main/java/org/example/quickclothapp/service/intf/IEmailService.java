package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.EmailRequest;
import org.example.quickclothapp.payload.request.EmailsRequest;
import org.example.quickclothapp.payload.response.CampaignResponse;

import java.util.List;

public interface IEmailService {
    void sendEmailNewCampaign(EmailRequest emailRequest, User user, Campaign campaign);
    void sendEmailNewSale(Sale sale, List<SaleList> saleLists, Integer newPoints,EmailRequest emailRequest, List<CampaignResponse> campaignList);
    void sendEmailNewUser(User user, EmailRequest emailRequest);
    void sendEmailNewClothes(List<Clothe> clothes, Wardrobe wardrobe, EmailsRequest emailsRequest);
}
