package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.*;
import org.example.quickclothapp.payload.request.OrderDataRequest;
import org.example.quickclothapp.payload.request.SaleDataRequest;
import org.example.quickclothapp.payload.response.InventoryResponse;

import java.util.List;
import java.util.UUID;

public interface IWardRopeDataService {
    Wardrope saveWardrope(Wardrope wardrope) throws DataServiceException;
    Wardrope findWardRopeByUuid(UUID uuid) throws DataServiceException;
    Sale saveSale(SaleDataRequest newSale) throws DataServiceException;
    List<Inventory> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException;
    Inventory findInventoryByClotheUuidAndWardRopeUuid(UUID clotheUuid, UUID wardRopeUuid) throws DataServiceException;
    OrderState findOrderStateByName(String orderName) throws DataServiceException;
    Order saveOrder(OrderDataRequest or) throws DataServiceException;
}
