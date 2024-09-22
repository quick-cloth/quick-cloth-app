package org.example.quickclothapp.service.intf;

import jakarta.mail.MessagingException;
import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.model.Sale;
import org.example.quickclothapp.model.SaleList;
import org.example.quickclothapp.model.User;
import org.example.quickclothapp.payload.request.CampaignRequest;
import org.example.quickclothapp.payload.request.EmailRequest;
import org.example.quickclothapp.payload.response.CampaignResponse;

import java.util.List;

public interface IEmailService {
    void sendEmailNewCampaign(EmailRequest emailRequest, User user, Campaign campaign);
    void sendEmailNewSale(Sale sale, List<SaleList> saleLists, EmailRequest emailRequest, List<CampaignResponse> campaignList);
}
