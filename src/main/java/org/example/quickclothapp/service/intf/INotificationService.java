package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.model.User;

public interface INotificationService {
    void sendEmailNewCampaing(User user, Campaign campaign);
}
