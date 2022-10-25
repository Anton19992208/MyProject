package com.example.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class SessionUtil {

    public static SessionFactory buildSessionFactory() {
        return buildConfiguration()
                .configure()
                .buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        return new Configuration();

    }
}



