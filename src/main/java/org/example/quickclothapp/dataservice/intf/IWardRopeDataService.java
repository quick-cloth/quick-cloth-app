package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Sale;
import org.example.quickclothapp.model.Wardrope;
import org.example.quickclothapp.payload.request.SaleDataRequest;

import java.util.UUID;

public interface IWardRopeDataService {
    Wardrope saveWardrope(Wardrope wardrope) throws DataServiceException;
    Wardrope findWardRopeByUuid(UUID uuid) throws DataServiceException;
    Sale saveSale(SaleDataRequest newSale) throws DataServiceException;
}
