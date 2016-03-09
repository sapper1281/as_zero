package zdislava.common.dto.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Дает доступ к объекту SessionFactory. Он будет 1 на все приложение
 * @author VVolgina
 *
 */
public final class SessionFactorySingleton {

	private static SessionFactory factory;

	private SessionFactorySingleton() {

	}

	//

	/**
	 * Возвращает объект SessionFactory. 1 и тот же на все приложение
	 * Конфигурационный файл Hibernate Должен быть /rzdd/Hibernate.cfg.xml
	 * 
	 * @return объект SessionFactory
	 */
	public static synchronized SessionFactory getSessionFactoryInstance() {
		if (factory == null) {
			Configuration configuration = new Configuration();
			configuration.configure("/hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			factory = configuration.buildSessionFactory(serviceRegistry);
		}
		return factory;
	}

}
