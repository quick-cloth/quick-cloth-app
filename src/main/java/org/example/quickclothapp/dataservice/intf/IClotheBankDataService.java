package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IClotheBankDataService {
    ClotheBank saveClotheBank(ClotheBank clotheBank) throws DataServiceException;
    ClotheBank findClotheBankByUuid(UUID uuid) throws DataServiceException;
    Campaign saveCampaign(Campaign campaign) throws DataServiceException;
    TypeCampaign findTypeCampaignByUuid(UUID typeCampaignUuid) throws DataServiceException;
    List<Campaign> findCampaignsByClotheBankUuid(UUID uuid, LocalDate startDate, LocalDate endDate) throws DataServiceException;
    List<TypeCampaign> findAllTypeCampaign() throws DataServiceException;
    Donation saveDonation(Donation donation) throws DataServiceException;
    List<Donation> findDonationByClotheBankUuid(UUID clotheBankUuid) throws DataServiceException;
    List<Order> findOrdersByClotheBankUuid(UUID clotheBankUuid, UUID orderStateUuid, UUID wardRobeUuid) throws DataServiceException;
    List<OrderList> findOrderListByOrder(UUID orderUuid) throws DataServiceException;
    Order findOrderByUuid(UUID orderUuid) throws DataServiceException;
}
