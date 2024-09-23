package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.exception.WardRopeServiceExpetion;
import org.example.quickclothapp.model.OrderState;
import org.example.quickclothapp.model.Wardrobe;
import org.example.quickclothapp.payload.request.OrderRequest;
import org.example.quickclothapp.payload.request.SaleRequest;
import org.example.quickclothapp.payload.request.WardRobeRequest;
import org.example.quickclothapp.payload.response.InventoryResponse;
import org.example.quickclothapp.payload.response.MessageResponse;
import org.example.quickclothapp.payload.response.SaleResponse;
import org.example.quickclothapp.payload.response.WardRobeResponse;

import java.util.List;
import java.util.UUID;

public interface IWardRobeService {
    MessageResponse saveWardRope(WardRobeRequest wardRopeRequest) throws DataServiceException;
    SaleResponse checkValueSale(SaleRequest sale, boolean payPoints) throws DataServiceException, WardRopeServiceExpetion;
    MessageResponse saveSale(SaleRequest saleRequest, boolean payPoints) throws DataServiceException, WardRopeServiceExpetion;
    MessageResponse updateWardRope(WardRobeRequest wardrobe) throws DataServiceException;
    Wardrobe findWardRopeByUuid(String uuid) throws DataServiceException;
    WardRobeResponse findWardRopeByUuid(UUID uuid) throws DataServiceException;
    List<InventoryResponse> findInventoriesByWardRopeUuid(UUID wardRopeUuid) throws DataServiceException;
    InventoryResponse findInventoryByClotheUuidAndWardRopeUuid(UUID clotheUuid, UUID wardRopeUuid) throws DataServiceException;
    MessageResponse createOrder(OrderRequest orderRequest) throws DataServiceException;
    List<WardRobeResponse> finAllWardRobeByClotheBankUuid(UUID clotheBankUuid) throws DataServiceException;
    List<OrderState> getAllOrderStates() throws DataServiceException;
}
