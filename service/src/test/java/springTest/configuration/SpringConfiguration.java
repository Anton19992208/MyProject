package springTest.configuration;

import com.example.dao.MovieRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import util.HibernateTestUtil;

import java.lang.reflect.Proxy;


@Configuration
@ComponentScan(basePackages = "com.example.dao")
public class SpringConfiguration {


    @Bean
    public Session createProxySession(SessionFactory sessionFactory) {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                ((proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args)));
    }

    @Bean
    public SessionFactory testSessionFactory() {
        return HibernateTestUtil.buildSessionFactory();
    }

}



