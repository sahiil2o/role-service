package com.sahil.role_service.config;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }
    @Bean(name = "roles")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema roleSchema) {
        DefaultWsdl11Definition def = new DefaultWsdl11Definition();
        def.setPortTypeName("RolesPort");
        def.setLocationUri("/ws");
        def.setTargetNamespace("http://sahil.com/role-service/ws");
        def.setSchema(roleSchema);
        return def;
    }

    @Bean
    public XsdSchema roleSchema() {
        return new SimpleXsdSchema(new ClassPathResource("roles.xsd"));
    }
}
