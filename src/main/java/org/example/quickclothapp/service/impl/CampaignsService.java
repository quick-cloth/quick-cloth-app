package org.example.quickclothapp.service.impl;

import org.example.quickclothapp.dataservice.intf.ICampaignsDataService;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.service.intf.ICampaignsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
}
