package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Wardrope;
import org.example.quickclothapp.payload.request.SaleRequest;
import org.example.quickclothapp.payload.request.WardRopeRequest;
import org.example.quickclothapp.payload.response.MessageResponse;

public interface IWardRopeService {
    MessageResponse saveWardRope(WardRopeRequest wardRopeRequest) throws DataServiceException;
    MessageResponse saveSale(SaleRequest saleRequest) throws DataServiceException;
    Wardrope findWardRopeByUuid(String uuid) throws DataServiceException;
}
