package org.example.quickclothapp.dataservice.intf;

import org.example.quickclothapp.exception.DataServiceException;
import org.example.quickclothapp.model.Foundation;
import org.example.quickclothapp.model.TypeMeetUs;

import java.util.List;
import java.util.UUID;

public interface IFoundationDataService {
    Foundation saveFoundation(Foundation newFoundation) throws DataServiceException;
    Foundation findFoundationByUuid(UUID foundationUuid) throws DataServiceException;
    List<Foundation> findAllFoundationByClotheBank(UUID clotheBankUuid) throws DataServiceException;
    List<TypeMeetUs> getAllTypeMeetUs() throws DataServiceException;
    TypeMeetUs findTypeMeetUsByUuid(UUID typeMeetUsUuid)throws DataServiceException;
}
