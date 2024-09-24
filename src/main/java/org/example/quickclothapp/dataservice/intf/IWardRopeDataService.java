package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.OrderDataRequest;
import org.example.quickclothapp.payload.request.SaleDataRequest;

import java.util.List;
import java.util.UUID;

public interface IWardRopeDataService {
    Wardrobe saveWardrope(Wardrobe wardrope) throws DataServiceException;
    Wardrobe findWardRopeByUuid(UUID uuid) throws DataServiceException;
    List<Wardrobe> finAllWardRopeByClotheBankUuid(UUID clotheBankUuid) throws DataServiceException;
    Sale saveSale(SaleDataRequest newSale) throws DataServiceException;
    List<Sale> findSalesByWardRopeUuid(UUID uuid) throws DataServiceException;
    List<Inventory> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException;
    Inventory findInventoryByClotheUuidAndWardRopeUuid(UUID clotheUuid, UUID wardRopeUuid) throws DataServiceException;
    OrderState findOrderStateByName(String orderName) throws DataServiceException;
    Order saveOrder(OrderDataRequest or) throws DataServiceException;
    List<OrderState> findAllOrderStates() throws DataServiceException;
    List<SaleList> findSaleListBySaleUuid(UUID uuid) throws DataServiceException;
}
