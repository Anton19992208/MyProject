package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class ProxySession {

    protected static SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    protected Session createProxySession(SessionFactory sessionFactory) {
        return  (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }
}
