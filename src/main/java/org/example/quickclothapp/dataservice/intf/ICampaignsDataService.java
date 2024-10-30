package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;

import java.util.List;
import java.util.UUID;

public interface ICampaignsDataService {
    List<Campaign> getActiveCampaigns() throws DataServiceException;
    List<Campaign> getCampaignsForUser(UUID userUuid) throws DataServiceException;
    void deleteCampaign(UUID campaignUuid) throws DataServiceException;
}
