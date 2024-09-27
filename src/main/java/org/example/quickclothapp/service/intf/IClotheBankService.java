package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.ClotheBankServiceException;
import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.CampaignRequest;
import org.example.quickclothapp.payload.request.ClotheBankRequest;
import org.example.quickclothapp.payload.request.DonationRequest;
import org.example.quickclothapp.payload.request.OrderRequest;
import org.example.quickclothapp.payload.response.CampaignResponse;
import org.example.quickclothapp.payload.response.DonationResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.OrderResponse;

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
    MessageResponse saveDonation(DonationRequest donationRequest) throws DataServiceException;
    List<DonationResponse> findDonationByClotheBankUuid(UUID clotheBankUuid) throws DataServiceException;
    List<OrderResponse> findOrdersByClotheBankUuid(UUID clotheBankUuid, UUID orderStateUuid, UUID wardRobeUuid) throws DataServiceException;
    MessageResponse responseOrder(OrderRequest orderRequest, UUID orderUuid) throws DataServiceException;
    Order findOrderByUuid(UUID orderUuid) throws DataServiceException;
    List<OrderList> findOrderListByOrder(UUID uuid) throws DataServiceException;
}
