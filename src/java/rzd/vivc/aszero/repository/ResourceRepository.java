/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoResource;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.service.StringImprover;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 *
 * @author VVolgina
 */
public class ResourceRepository extends BaseRepository {

    /**
     * Извлекает из БД список неудаленных ресурсов, отсортированный по Id
     *
     * @return список ресурсов
     */
    public List<Resource> getActiveResource() {
        List<Resource> dbList = null;

        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            dbList = getActiveResource(sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return dbList;
    }

    /**
     * Извлечение из БД всех неудаленных объектов ресурса с ценой на данный
     * месяц по данной ЖД
     *
     * @return список всех неудаленных объектов
     */
    public List<Resource> getAllNonDeletedWithCost(long railway, Date dat) {


        List<Resource> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getAllNonDeletedWithCost(railway, dat, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    /**
     * Извлечение из БД краткой информации обо всех неудаленных объектов ресурса
     * с ценой на данный месяц по данной ЖД
     *
     * @return список всех неудаленных объектов
     */
    public List<ShortInfoResource> getAllNonDeletedWithCostShort(long railway, Date dat) {


        List<ShortInfoResource> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getAllNonDeletedWithCostShort(railway, dat, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }
    
        /**
     * Извлечение из БД краткой информации о ресурсе
     *
     * @return краткая информация о ресурсе
     */
    public ShortInfoResource getShort(long id) {


        ShortInfoResource bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getShort(id, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    /**
     * Извлечение из БД всех неудаленных объектов ресурса с ценой на данный
     * месяц по данной ЖД
     *
     * @return список всех неудаленных объектов
     */
    protected List<Resource> getAllNonDeletedWithCost(long railway, Date dat, Session sess) {
        StringImprover stringImprover = new StringImprover();
        return getAllNonDeletedWithCost(railway, stringImprover.getMonth(dat), stringImprover.getYear(dat), sess);
    }

    /**
     * Извлечение из БД краткой информации обо всех неудаленных объектов ресурса
     * с ценой на данный месяц по данной ЖД
     *
     * @return список всех неудаленных объектов
     */
    protected List<ShortInfoResource> getAllNonDeletedWithCostShort(long railway, Date dat, Session sess) {
        StringImprover stringImprover = new StringImprover();
        return getAllNonDeletedWithCostShort(railway, stringImprover.getMonth(dat), stringImprover.getYear(dat), sess);
    }

    /**
     * Извлечение из БД всех неудаленных объектов ресурса с ценой на данный
     * месяц по данной ЖД
     *
     * @return список всех неудаленных объектов
     */
    protected List<Resource> getAllNonDeletedWithCost(long railway, byte mon, byte year, Session sess) {
        List<Resource> lst = (List<Resource>) sess.createQuery("SELECT r "
                + "FROM Resource as r "
                + "left join fetch r.cost as c "
                + "where c.common.railway=:rail AND c.common.month=:mon AND c.common.year=:ye "
                + "AND r.deleted=0")
                .setLong("rail", railway)
                .setByte("mon", mon)
                .setByte("ye", year)
                .list();
        if (lst == null || lst.isEmpty()) {
            lst = (List<Resource>) sess.createQuery("SELECT r "
                    + "FROM Resource as r "
                    + "where r.deleted=0")
                    .list();
        }
        return lst;
    }

    /**
     * Извлечение из БД краткой информации о всех неудаленных объектах ресурса с
     * ценой на данный месяц по данной ЖД
     *
     * @return список всех неудаленных объектов
     */
    protected List<ShortInfoResource> getAllNonDeletedWithCostShort(long railway, byte mon, byte year, Session sess) {
        List<ShortInfoResource> lst = (List<ShortInfoResource>) sess.createQuery("SELECT new rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoResource(r.id, r.name||','||mes.valueHTML, c.cost, r.koef_polution) "
                + "FROM Resource as r "
                + "inner join r.measure as mes "
                + "left join r.cost as c "
                + "left join c.common as dat "
                + "where dat.railway=:rail AND dat.month=:mon AND dat.year=:ye "
                + "AND r.deleted=0 Order by r.name")
                .setLong("rail", railway)
                .setByte("mon", mon)
                .setByte("ye", year)
                .list();
        if (lst == null || lst.isEmpty()) {
            lst = (List<ShortInfoResource>) sess.createQuery("SELECT new rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoResource(r.id, r.name||','||mes.valueHTML, r.koef_polution) "
                    + "FROM Resource as r "
                    + "inner join r.measure as mes "
                    + "where r.deleted=0 Order by r.name")
                    .list();
        }
        return lst;
    }

    /**
     * Извлечение из БД краткой информации о ресурсе
     *
     * @return краткая информация о ресурсе
     */
    protected ShortInfoResource getShort(long id, Session sess) {
        return (ShortInfoResource) sess.createQuery("SELECT new rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoResource(r.id, r.name||','||mes.valueHTML, r.koef_polution) "
                + "FROM Resource as r "
                + "inner join r.measure as mes "
                + "where r.deleted=0 and r.id=:id").setLong("id", id)
                .uniqueResult();

    }

    /**
     * Извлекает из БД список неудаленных ресурсов, отсортированный по Id
     *
     * @param sess сессия hibernate
     * @return список ресурсов
     */
    protected List<Resource> getActiveResource(Session sess) {
        List<Resource> dbList;

        dbList = (List<Resource>) sess.createQuery(
                "from Resource where deleted=0 order by id").list();

        return dbList;
    }

    public List<Resource> getPolutionResource() {
        List<Resource> dbList = null;

        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            // TODO подумать о критериях
            dbList = (List<Resource>) sess.createQuery(
                    "from Resource where deleted=0 and koef_polution=-1").list();
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ResourceRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return dbList;
    }

    public static void main(String[] args) {
        //Resource res=new Resource("Газ природный", 5,1);
        //Resource res=new Resource("Бензин", 1,2);
        //Resource res=new Resource("Топочный мазут", 1,3);
        // Resource res=new Resource("Уголь сортовой", 1,3);
        // Resource res=new Resource("Уголь рядовой", 1,4);
        //Resource res=new Resource("Масло", 1,5);
        //Resource res=new Resource("Теплоэнергия", 6,6);
        //Resource res=new Resource("Озеленение территории", 8,7);
        //Resource res=new Resource("Озеленение территории", 7,7);
        //Resource res=new Resource("Уборка территории", 7,8);
        // Resource res=new Resource("Уборка территории", 1,8);
        //Resource res=new Resource("Сокращение выбросов загрязняющих веществ в атмосферу", 1,9);
        //Resource res=new Resource("Дизельное топливо", 1,10);
        /* Resource res=new Resource("Электроэнергия", 3,10);
         (new ResourceRepository()).save(res);
         System.out.println((new ResourceRepository()).getAll(Resource.class));
         System.out.println((new ResourceRepository()).getAllNonDeleted(Resource.class));*/
        //Resource get = (new ResourceRepository()).get(new Resource(1));
        //System.out.println(get);
        List<ShortInfoResource> allNonDeletedWithCostShort = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;

        t = sess.beginTransaction();

        allNonDeletedWithCostShort = new ResourceRepository().getAllNonDeletedWithCostShort(95, (byte) 7, (byte) 15, sess);
        t.commit();

        sess.close();

        for (ShortInfoResource shortInfoResource : allNonDeletedWithCostShort) {
            System.out.println(shortInfoResource);
        }
    }
}
