package com.example.configuration;

import com.example.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "com.example")
public class ApplicationConfiguration {

    @Bean
    public Session createProxySession(SessionFactory sessionFactory){
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));
    }


    @Bean
    public SessionFactory baseSessionFactory() {
        return SessionUtil.buildSessionFactory();
    }
}
