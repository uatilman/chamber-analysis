package ru.tilman.chambers.enterprise.integration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import ru.tilman.chambers.enterprise.ChamberAnalysisApplication;
import ru.tilman.chambers.enterprise.entity.Chamber;

@MessageEndpoint
public class ChamberMessageListener {
    private static final Logger LOGGER = LogManager.getLogger(ChamberAnalysisApplication.class);

    @ServiceActivator(inputChannel = CashMessageGateway.CHANEL)
    public void handler(@Payload Chamber payloadChamber, @Header("CHAMBER") Long headerId) {
        LOGGER.info("chamber id: " + headerId);
        LOGGER.info("Chamber name: " + payloadChamber.getName());
    }

    // TODO: 20.08.18 дебажить *5
    @Bean
    @ServiceActivator(inputChannel = "receiveChannel")
    public HttpRequestExecutingMessageHandler outbound() {
        HttpRequestExecutingMessageHandler handler =
                new HttpRequestExecutingMessageHandler("http://localhost:8080/api");
        handler.setHttpMethod(HttpMethod.POST);
        handler.setExpectedResponseType(Chamber.class);
        return handler;
    }


}
