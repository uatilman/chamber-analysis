package ru.tilman.chamberanalysis.endpoint;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.tilman.chamberanalysis.entity.Chamber;
import ru.tilman.chamberanalysis.services.ChamberService;
import ru.tilman.chamberanalysis.soap.chamber.*;

import java.util.List;


@Endpoint
public class ChamberEndpoint {

//    @NotNull
//    private final static Logger LOGGER = Logger.getLogger(ChamberEndpoint.class.getSimpleName());

    @NotNull
    public final static String LOCATION_URI = "/soap/chamber";

    @NotNull
    public final static String PORT_TYPE_NAME = "ChamberEndpointPort";

    @NotNull
    public final static String NAMESPACE = "http://tilman.ru/chamberanalysis/soap/chamber";
    //
    private final ChamberService chamberService;

    @Autowired
    public ChamberEndpoint(ChamberService chamberService) {
        this.chamberService = chamberService;
    }

    //
    @ResponsePayload
    @PayloadRoot(localPart = "pingRequest", namespace = NAMESPACE)
    public PingResponse ping(@RequestPayload final PingRequest request) {
        final PingResponse result = new PingResponse();
        result.setSuccess(true);
        return result;
    }

    //
    @ResponsePayload
    @PayloadRoot(localPart = "testRequest", namespace = NAMESPACE)
    public TestResponse test(@RequestPayload TestRequest request) {
        // TODO Impl logic
        return new TestResponse();
    }

    //
    @ResponsePayload
    @PayloadRoot(localPart = "getChambersRequest", namespace = NAMESPACE)
    public GetChambersResponse getTasks(@RequestPayload final GetChambersRequest request) {
        final GetChambersResponse result = new GetChambersResponse();

        final List<ru.tilman.chamberanalysis.entity.Chamber> chambers = chamberService.getChambersListByOrderByIdAsc();
        for (Chamber chamber : chambers) {
            result.getRows().add(chamber); // TODO: 13.08.18  add to record method
        }
        return result;
    }


}
