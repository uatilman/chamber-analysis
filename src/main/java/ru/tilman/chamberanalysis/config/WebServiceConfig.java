package ru.tilman.chamberanalysis.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import ru.tilman.chamberanalysis.endpoint.ChamberEndpoint;


@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @NotNull
    private static final String WS_MAPPING = "/soap/*";

    @Bean
    @SuppressWarnings("unchecked")
    public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, WS_MAPPING);
    }

    @Bean(name = "ChamberService")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema testEndpointSchema) {
        final DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(ChamberEndpoint.PORT_TYPE_NAME);
        wsdl11Definition.setLocationUri(ChamberEndpoint.LOCATION_URI);
        wsdl11Definition.setTargetNamespace(ChamberEndpoint.NAMESPACE);
        wsdl11Definition.setSchema(testEndpointSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema testEndpointSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/chamberEndpoint.xsd"));
    }

}
