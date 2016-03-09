/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rzd.vivc.aszero.dto.Counter;
import rzd.vivc.aszero.dto.InputASKYEInfo;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 *
 * @author apopovkin
 */
public class CounterRepository extends BaseRepository {
    
    
    
    
    /**
     * Сохранение/обновление в БД объекта класса-наследника Data_Info
     * @param <T> класс-наследник Data_Info
     * @param elem объект
     */
    public <T > void save(T elem){
         Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();
            save(elem, sess);
        t.commit();
        sess.close();
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
    
    
    
    public <T1,T2,T3> void saveAll(T1 elem1,T2 elem2,T3 elem3){
         Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();
            saveAll(elem1,elem2,elem3, sess);
        t.commit();
        sess.close();
    }
    
     protected <T1,T2,T3> void saveAll(T1 elem1,T2 elem2,T3 elem3, Session sess){
         
         
         new InputASKYERepository().save(elem1,sess);
         sess.saveOrUpdate(elem2);
         
          for (InputASKYEInfo inputASKYEInfo : (List<InputASKYEInfo>)elem3) {
                           new InputASKYEInfoRepository().save(inputASKYEInfo,sess);
                        }
         
         //new InputASKYERepository().save(inASFirst);
         
        // sess.saveOrUpdate(elem);
    }
    
    
    public Counter get(long id) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();

        // пытаемся извлечь из БД пользователя с данным логином
        Counter bd = get(id, sess);

        t.commit();
        sess.close();

        return bd;
    }
    
    public Counter getByNum(String num) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();
        List<Counter> list = (List<Counter>) sess.createQuery("from Counter c where c.num=:num and c.deleted=0 order by c.id").setString("num", num).list();

        t.commit();
        sess.close();

        return list.get(list.size() - 1);
    }

    public Counter getWithAskueInfo(long id, Date dt_begin) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();

        // пытаемся извлечь из БД пользователя с данным логином
         //Counter bd = (Counter) sess.createQuery("from Counter c left join fetch c. where c.num=:num and c.deleted=0").setLong("num", id).uniqueResult();
         //Counter bd =(Counter)sess.createQuery("SELECT DISTINCT c FROM Counter c left join fetch c.сounterInputASKYEInfo inf where c.num=:num and c.deleted=0 and inf.inputASKYEinf.dtToday=:dt_begin").setLong("num", id).setDate("dt_begin", dt_begin).uniqueResult();
         //if(bd==null){
           Counter  bd =(Counter)sess.createQuery("SELECT c FROM Counter c where c.num=:num and c.deleted=0").setLong("num", id).uniqueResult();
        // }

        t.commit();
        sess.close();

        return bd;
    }
    
    protected Counter get(long id, Session sess) {
        return (Counter) sess.get(Counter.class, id);
    }
    
    /*
    public List<Counter> getCounter(Long element) {

        List<Counter> dbList;
         Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();

        dbList = getCounter(element, sess);

        t.commit();
        sess.close();

        return dbList;
    } 
  
   public List<Counter> getCounter(String element,  Session session) {

        List<Counter> dbList;
       
            dbList = (List<Counter>) session.createQuery(
                " from Counter where deleted=0 and "
               +" num=:element ")
               
              
                .setParameter("element", element).list();
            
        return dbList;
    }*/
    
    public static void main(String[] args) throws ParseException{
        CounterRepository rep=new CounterRepository();
        rep.getWithAskueInfo(7200116155l, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2013-10-25 00:00:00"));
    }
}
