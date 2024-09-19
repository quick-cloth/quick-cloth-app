package org.example.quickclothapp.service.impl;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.model.User;
import org.example.quickclothapp.service.intf.INotificationService;
import org.example.quickclothapp.service.intf.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService {
    private final IUserService userService;

    @Value("${api-resend-api-key}")
    private String resendApiKey;

    public NotificationService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void sendEmailNewCampaing(User user, Campaign campaign) {
        Resend resend = new Resend(resendApiKey);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Acme <onboarding@resend.dev>")
                .to(user.getEmail())
                .subject(String.format("Nueva campaña %s", campaign.getName()))
                .html(this.createMessage(user, campaign))
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }

    private String createMessage(User user, Campaign campaign) {
        return String.format("Hola %s" +
                "Nos complase anunciar que hemos lanzado la campaña %s \n" +
                "Descripcion: %s \n" +
                "Detalles de la campaña: De %s a %s \n"
                , user.getName(), campaign.getName(), campaign.getMessage_campaign(), campaign.getCreation_date(), campaign.getEnd_date());
    }
}
