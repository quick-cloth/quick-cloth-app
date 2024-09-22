package org.example.quickclothapp.service.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Foundation;
import org.example.quickclothapp.model.TypeMeetUs;
import org.example.quickclothapp.payload.request.FoundationRequest;
import org.example.quickclothapp.payload.response.FoundationResponse;
import org.example.quickclothapp.payload.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface IFoundationService {
    MessageResponse saveFoundation(FoundationRequest foundation) throws DataServiceException;
    MessageResponse updateFoundation(FoundationRequest foundation) throws DataServiceException;
    Foundation findFoundationByUuid(UUID foundationUuid) throws DataServiceException;
    List<FoundationResponse> findAllFoundationByClotheBank(UUID clotheBankUuid) throws DataServiceException;
    FoundationResponse findFoundationResponseByUuid(UUID foundationUuid) throws DataServiceException;
    List<TypeMeetUs> getAllTypeMeetUs() throws DataServiceException;
}
