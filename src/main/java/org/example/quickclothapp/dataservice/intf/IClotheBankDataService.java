package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.model.ClotheBank;
import org.example.quickclothapp.model.TypeCampaign;

import java.util.List;
import java.util.UUID;

public interface IClotheBankDataService {
    ClotheBank saveClotheBank(ClotheBank clotheBank) throws DataServiceException;
    ClotheBank findClotheBankByUuid(UUID uuid) throws DataServiceException;
    Campaign saveCampaign(Campaign campaign) throws DataServiceException;
    TypeCampaign findTypeCampaignByUuid(UUID typeCampaignUuid) throws DataServiceException;
    List<Campaign> findCampaignsByClotheBankUuid(UUID uuid) throws DataServiceException;
}
