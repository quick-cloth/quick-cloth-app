package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;

import java.util.List;

public interface ICampaignsService {
    List<Campaign> getActiveCampaigns() throws DataServiceException;
}
