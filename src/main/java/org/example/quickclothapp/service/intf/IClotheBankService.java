package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.ClotheBankServiceException;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Campaign;
import org.example.quickclothapp.model.ClotheBank;
import org.example.quickclothapp.model.TypeCampaign;
import org.example.quickclothapp.payload.request.CampaignRequest;
import org.example.quickclothapp.payload.request.ClotheBankRequest;
import org.example.quickclothapp.payload.response.CampaignResponse;
import org.example.quickclothapp.payload.response.MessageResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IClotheBankService {
    MessageResponse saveClotheBank(ClotheBankRequest clotheBank) throws DataServiceException;
    ClotheBank findClotheBankByUuid(UUID uuid) throws DataServiceException;
    MessageResponse saveCampaign(CampaignRequest campaignRequest) throws DataServiceException, ClotheBankServiceException;
    List<Campaign> findCampaignsByClotheBankUuid(UUID uuid) throws DataServiceException;
    List<CampaignResponse> findAllCampaignsByClotheBankUuid(UUID clotheBankUuid, LocalDate startDate, LocalDate endDate) throws DataServiceException;
    List<TypeCampaign> findAllTypeCampaign() throws DataServiceException;
}
