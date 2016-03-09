/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 *
 * @author apopovkin
 */
public class InputASKYEInfoRepository extends BaseRepository{
  /**
     * Сохранение/обновление в БД объекта класса-наследника Data_Info
     * @param <T> класс-наследник Data_Info
     * @param elem объект
     */
    public <T > void save(T elem){
         Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            save(elem, sess);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(InputASKYEInfoRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(InputASKYEInfoRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(InputASKYEInfoRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
    }
    
    /**
     *Сохранение/обновление в БД объекта класса-наследника Data_Info
     * @param <T> класс-наследник Data_Info
     * @param elem объект
     * @param sess сессия Хибернейт с открытой транзакцией
     */
    protected <T > void save(T elem, Session sess){
         sess.saveOrUpdate(elem);
    }
       
}
