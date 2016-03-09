/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zdislava.common.dto.configuration;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Бин на все приложение. При инициализауии создает SessionFactory. Остальные могут ее юзать
 * @author VVolgina
 */
@ManagedBean
@ApplicationScoped
public class SessionFactoryBean {
    private static SessionFactory factory;

    /**
     * Возвращает экземпляр класса SessionFactory. он 1 на все приложение
     * @return
     */
    public static synchronized SessionFactory getFactory() {
        return factory;
    }

    /**
     * Creates a new instance of SessionFactoryBean. Инициализирует SessionFactory
     * Конфигурационный файл Hibernate Должен быть /rzdd/Hibernate.cfg.xml
     */
    public SessionFactoryBean() {
        Configuration configuration = new Configuration();
			configuration.configure("/hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			factory = configuration.buildSessionFactory(serviceRegistry);
    }
}
