package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.ICampaignsDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.service.intf.ICampaignsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CampaignsService implements ICampaignsService {

    ICampaignsDataService campaignsDataService;

    public CampaignsService(ICampaignsDataService campaignsDataService) {
        this.campaignsDataService = campaignsDataService;
    }


    @Override
    public List<Campaign> getActiveCampaigns() throws DataServiceException {
        return campaignsDataService.getActiveCampaigns();
    }

    @Override
    public List<Campaign> getCampaignsForUser(UUID userUuid) throws DataServiceException {
        return campaignsDataService.getCampaignsForUser(userUuid);
    }

    @Override
    public void deleteCampaign(UUID campaignUuid) throws DataServiceException {
        campaignsDataService.deleteCampaign(campaignUuid);
    }


}
