package ru.tilman.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Date;
import java.util.Map;

@SpringBootApplication
@IntegrationComponentScan //сканипует класы в поисках аннотироанных Spring Integration элементов
@EnableIntegration//разрешить подсистему интеграции .. работает и без нее?
public class IntegrationApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(IntegrationApplication.class, args);
        Message<String> message = MessageBuilder
                .withPayload("body") //полезная нагрузка
                .setHeader("header", new Date()) //заголовок может содержать любые пары ключ-значение
                .build();
        MessageChannel channel = (DirectChannel) context.getBean("channel_no5"); // получение канала из контектста
        channel.send(message);
    }

    @ServiceActivator(inputChannel = "channel_no5")
    public void foo(@Payload String payload, @Headers Map<String, Object>
            headerMap){
        headerMap.forEach((s, o) -> System.out.printf("%s:%s\n", s, o));
        System.out.println(payload);
    }
}
