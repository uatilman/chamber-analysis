package ru.tilman.chambers.enterprise.integration;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import ru.tilman.chambers.enterprise.entity.Chamber;

import static ru.tilman.chambers.enterprise.integration.CashMessageGateway.CHANEL;
import static ru.tilman.chambers.enterprise.integration.CashMessageGateway.GATEWAY;

@MessagingGateway(name = GATEWAY, defaultRequestChannel = CHANEL)
public interface CashMessageGateway {

    String CHANEL = "cashMessageChannel";
    String GATEWAY = "cashMessageGateway";

    @Gateway
    void send(Message<Chamber> message);

}
