package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;

import java.util.List;

public interface ICampaignsDataService {
    List<Campaign> getActiveCampaigns() throws DataServiceException;
}
