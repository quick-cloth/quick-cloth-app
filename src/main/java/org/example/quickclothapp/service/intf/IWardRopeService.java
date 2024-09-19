package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Inventory;
import org.example.quickclothapp.model.Wardrope;
import org.example.quickclothapp.payload.request.OrderRequest;
import org.example.quickclothapp.payload.request.SaleRequest;
import org.example.quickclothapp.payload.request.WardRopeRequest;
import org.example.quickclothapp.payload.response.InventoryResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.SaleResponse;

import java.util.List;
import java.util.UUID;

public interface IWardRopeService {
    MessageResponse saveWardRope(WardRopeRequest wardRopeRequest) throws DataServiceException;
    SaleResponse checkValueSale(SaleRequest sale) throws DataServiceException;
    MessageResponse saveSale(SaleRequest saleRequest) throws DataServiceException;
    Wardrope findWardRopeByUuid(String uuid) throws DataServiceException;
    List<InventoryResponse> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException;
    InventoryResponse findInventoryByClotheUuidAndWardRopeUuid(UUID clotheUuid, UUID wardRopeUuid) throws DataServiceException;
    MessageResponse createOrder(OrderRequest orderRequest) throws DataServiceException;
}
