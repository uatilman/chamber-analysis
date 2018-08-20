package ru.tilman.chambers.enterprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.inbound.RequestMapping;
import org.springframework.messaging.MessageChannel;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import ru.tilman.chambers.enterprise.entity.Chamber;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients
@IntegrationComponentScan //сканипует класы в поисках аннотироанных Spring Integration элементов
@EnableIntegration//разрешить подсистему интеграции .. работает и без нее?
public class ChamberAnalysisApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ChamberAnalysisApplication.class, args);
    }

    // TODO: 20.08.18 дебажить *5
    @Bean
    public HttpRequestHandlingMessagingGateway inbound() {
        HttpRequestHandlingMessagingGateway gateway = new HttpRequestHandlingMessagingGateway(true);
        gateway.setRequestMapping(mapping());
        gateway.setRequestPayloadType(ResolvableType.forClass(Chamber.class));
        gateway.setRequestChannelName("requestChannel");
        return gateway;
    }

    // TODO: 20.08.18 дебажить *5
    @Bean
    public RequestMapping mapping() {
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setPathPatterns("/api");
        requestMapping.setMethods(HttpMethod.POST, HttpMethod.GET);
        return requestMapping;
    }

    @Bean
    public MessageChannel CashMessageChannel() {
        return new DirectChannel();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Bean("localeChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean("localeResolver")
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localResolver = new CookieLocaleResolver();
        localResolver.setCookieName("locale");
        return localResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

}
