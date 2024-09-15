package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.ClotheBank;

import java.util.UUID;

public interface IClotheBankDataService {
    ClotheBank saveClotheBank(ClotheBank clotheBank) throws DataServiceException;
    ClotheBank findClotheBankByUuid(UUID uuid) throws DataServiceException;
}
