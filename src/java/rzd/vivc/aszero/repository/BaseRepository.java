/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import zdislava.common.dto.configuration.SessionFactorySingleton;
import zdislava.model.dto.security.users.ExistingUserExeption;
import zdislava.model.dto.security.users.User;

/**
 * Самые обычные сохранение/обновление, извлечение элемента по Id, всего списка
 * элементов и списка неудаленных элементов. Наследуется всеми репоиториями для
 * Data_Info
 *
 * @author VVolgina
 */
public abstract class BaseRepository {

    /**
     * Сохранение/обновление в БД объекта класса-наследника Data_Info
     *
     * @param <T> класс-наследник Data_Info
     * @param elem объект
     */
    public <T extends Data_Info> void save(T elem) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();
            save(elem, sess);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
    }

    /**
     * Сохранение/обновление в БД объекта класса-наследника Data_Info
     *
     * @param <T> класс-наследник Data_Info
     * @param elem объект
     * @param sess сессия Хибернейт с открытой транзакцией
     */
    protected <T extends Data_Info> void save(T elem, Session sess) {
        sess.saveOrUpdate(elem);
    }

    /**
     * Извлечение из БД объекта по id
     *
     * @param <T> класс-наследник Data_Info
     * @param elem объект с заданным id
     * @return объект из БД с заданным id
     */
    public <T extends Data_Info> T get(T elem) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        T bd = null;
        try {
            t = sess.beginTransaction();

            bd = get(elem, sess);
            t.commit();
        } catch (Exception e) {
            bd = null;
            Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    /**
     * Извлечение из БД объекта по id
     *
     * @param <T> класс-наследник Data_Info
     * @param bd объект с заданным id
     * @param sess сессия Хибернейт с открытой транзакцией
     * @return объект из БД с заданным id
     */
    protected <T extends Data_Info> T get(T elem, Session sess) {
        return (T) sess.get(elem.getClass(), elem.getId());
    }

    /**
     * Извлечение из БД всех объектов данного класса
     *
     * @param <T> класс-наследник Data_Info
     * @param classInfo класс-наследник Data_Info
     * @return список всех объектов
     */
    public <T extends Data_Info> List<T> getAll(Class classInfo) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        List<T> bd = null;
        try {
            t = sess.beginTransaction();

            bd = getAll(classInfo, sess);
            t.commit();
        } catch (Exception e) {
            bd = null;
            Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    /**
     * Извлечение из БД всех объектов данного класса
     *
     * @param <T> класс-наследник Data_Info
     * @param classInfo класс-наследник Data_Info
     * @param sess сессия Хибернейт с открытой транзакцией
     * @return список всех объектов
     */
    protected <T extends Data_Info> List<T> getAll(Class classInfo, Session sess) {
        return (ArrayList<T>) sess.createQuery("from "
                + classInfo.getName()).list();
    }

    /**
     * Извлечение из БД всех неудаленных объектов данного класса
     *
     * @param <T> класс-наследник Data_Info
     * @param classInfo класс-наследник Data_Info
     * @return список всех неудаленных объектов
     */
    public <T extends Data_Info> List<T> getAllNonDeleted(Class classInfo) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        List<T> bd = null;
        try {
            t = sess.beginTransaction();

            bd = getAllNonDeleted(classInfo, sess);
            t.commit();
        } catch (Exception e) {
            bd = null;
            Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(BaseRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    /**
     * Извлечение из БД всех неудаленных объектов данного класса
     *
     * @param <T> класс-наследник Data_Info
     * @param classInfo класс-наследник Data_Info
     * @param sess сессия Хибернейт с открытой транзакцией
     * @return список всех неудаленных объектов
     */
    protected <T extends Data_Info> List<T> getAllNonDeleted(Class classInfo, Session sess) {
        return (ArrayList<T>) sess.createQuery("from "
                + classInfo.getName()
                + " where deleted=0").list();
    }
}
