package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.ClotheBank;
import org.example.quickclothapp.payload.request.ClotheBankRequest;
import org.example.quickclothapp.payload.response.MessageResponse;

import java.util.UUID;

public interface IClotheBankService {
    MessageResponse saveClotheBank(ClotheBankRequest clotheBank) throws DataServiceException;
    ClotheBank findClotheBankByUuid(UUID uuid) throws DataServiceException;
}
