/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.DepartmentGroup;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 * Репозиторий для отделов.
 *
 * @author VVolgina
 */
public class DepartmentRepository implements Serializable {

    /**
     * Добавление новой/редактирование службы
     *
     * @param elem служба
     */
    public void save(Department elem) {

        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            sess.saveOrUpdate(elem);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
    }

    /**
     * Извлекает из БД информацию о службе
     *
     * @param id id
     * @return служба
     */
    public Department get(long id) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        Department bd = null;
        try {
            t = sess.beginTransaction();
            bd = get(id, sess);
            t.commit();
        } catch (Exception e) {
            bd = null;
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected Department get(long id, Session sess) {
        return (Department) sess.get(Department.class, id);
    }

    //Выбоп подчененных предприятий по списку idNp
    public List<Department> getActiveListDepartmentsWithDetailsALL(List<Integer> depList, int mes, int year) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.YEAR, year);
        c.add(Calendar.DATE, -1);
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println("==" + dateFormatter1.format(c.getTime()));
        Session sess = SessionFactorySingleton.getSessionFactoryInstance().openSession();
        Transaction t = null;
        List<Department> bd = null;
        try {
            t = sess.beginTransaction();
            bd = getActiveListDepartmentsWithDetailsALL(depList, c.getTime(), sess);
            t.commit();
        } catch (Exception e) {
            bd = null;
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
        return bd;
    }

    protected List<Department> getActiveListDepartmentsWithDetailsALL(List<Integer> depList, Date mes, Session sess) {
        System.out.println("*****");
        Criteria criteria = sess.createCriteria(Department.class);
        criteria.add(Restrictions.in("idVp", depList)).add(Restrictions.eq("deleted", false));
        Criterion criteria0 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(mes.getTime())), Restrictions.ge("dt_end", new Timestamp(mes.getTime())));
        Criterion criteria1 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(mes.getTime())), Restrictions.isNull("dt_end"));
        Criterion criteria2 = Restrictions.or(criteria0, criteria1);
        criteria.add(criteria2).addOrder(Order.asc("nameNp"));
        System.out.println("*********");
        return (List<Department>) criteria.list();


    }

    //Выбоп подчененных предприятий по списку idNp gthbjl
    public List<Department> getActiveListDepartmentsWithDetailsALLDate(List<Integer> depList, Date dayBegin, Date dayEnd) {

        Session sess = SessionFactorySingleton.getSessionFactoryInstance().openSession();
        Transaction t = null;
        List<Department> bd = null;
        try {
            t = sess.beginTransaction();
            bd = getActiveListDepartmentsWithDetailsALLDate(depList, dayBegin, dayEnd, sess);
            t.commit();
        } catch (Exception e) {
            bd = null;
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected List<Department> getActiveListDepartmentsWithDetailsALLDate(List<Integer> depList, Date dayBegin, Date dayEnd, Session sess) {

        Criteria criteria = sess.createCriteria(Department.class)
                .createAlias("departmentGroup_id", "dt", Criteria.LEFT_JOIN);

        //.setFetchMode("departmentgroup", FetchMode.JOIN)
        // .createAlias("departmentgroup", "kt");
        criteria.add(Restrictions.in("idVp", depList)).add(Restrictions.eq("deleted", false));
        Criterion criteria0 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(dayBegin.getTime())), Restrictions.ge("dt_end", new Timestamp(dayEnd.getTime())));
        Criterion criteria1 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(dayBegin.getTime())), Restrictions.isNull("dt_end"));
        Criterion criteria2 = Restrictions.or(criteria0, criteria1);
        criteria.
                add(criteria2).addOrder(Order.asc("dt.nameNp")).addOrder(Order.asc("nameNp"));

        return (List<Department>) criteria.list();


    }

//Формирование дерева в админке первый элемент
    public List<Department> getFirstElementsDepartment(int element, boolean delete_flag) {

        List<Department> dbList = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            dbList = getFirstElementsDepartment(element, delete_flag, sess);
            t.commit();
        } catch (Exception e) {
            dbList = null;
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return dbList;
    }

    public List<Department> getFirstElementsDepartment(int element, boolean del_fl, Session session) {

        List<Department> dbList;

        dbList = (List<Department>) session.createQuery(
                " from Department where deleted=:del_fl and "
                + " idNp=:idNp order by nameNp")
                .setParameter("del_fl", del_fl)
                .setParameter("idNp", element).list();

        return dbList;
    }
//Формирование дерева в админке > первого элемента

    public List<Department> getElementsDepartment(int element, boolean delete_flag) {

        List<Department> dbList;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            dbList = getElementsDepartment(element, delete_flag, sess);
            t.commit();
        } catch (Exception e) {
            dbList = null;
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return dbList;
    }

    public List<Department> getElementsDepartment(int element, boolean del_fl, Session session) {

        List<Department> dbList;

        dbList = (List<Department>) session.createQuery(
                " from Department where  deleted=:del_fl and "
                + " idVp=:idVp  order by nameNp")
                .setParameter("del_fl", del_fl)
                .setParameter("idVp", element).list();

        return dbList;
    }

    //выбор групп предприятий
    public List<DepartmentGroup> getActiveListDepartmentGroup() {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance().openSession();
        Transaction t = null;
        List<DepartmentGroup> bd = null;
        try {
            t = sess.beginTransaction();
            bd = getActiveListDepartmentGroup(sess);
            t.commit();
        } catch (Exception e) {
            bd = null;
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected List<DepartmentGroup> getActiveListDepartmentGroup(Session sess) {

        Date dt = new Date();
        Criteria criteria = sess.createCriteria(DepartmentGroup.class);
        criteria.add(Restrictions.eq("deleted", false));
        Criterion criteria0 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(dt.getTime())), Restrictions.ge("dt_end", new Timestamp(dt.getTime())));
        Criterion criteria1 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(dt.getTime())), Restrictions.isNull("dt_end"));
        Criterion criteria2 = Restrictions.or(criteria0, criteria1);
        criteria.add(criteria2).addOrder(Order.asc("nameNp"));

        return (List<DepartmentGroup>) criteria.list();
    }

    public DepartmentGroup getDepartmentGroup(long id) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        DepartmentGroup bd = null;
        try {
            t = sess.beginTransaction();
            bd = getDepartmentGroup(id, sess);
            t.commit();
        } catch (Exception e) {
            bd = null;
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected DepartmentGroup getDepartmentGroup(long id, Session sess) {
        return (DepartmentGroup) sess.get(DepartmentGroup.class, id);
    }

    /**
     * Возвращает id дороги, к которой принадлежит подразделение
     *
     *
     * @param idDepartment id подразделения
     * @return id дороги, к которой принадлежит подразделение
     */
    public long getDepartmentPower(long idDepartment) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance().openSession();
        Transaction t = null;
        Department dep = null;
        Department depPower = new Department();

        try {
            t = sess.beginTransaction();
            dep = get(idDepartment, sess);
            while (dep.getIdVp() != 0) {
                depPower = dep;
                dep = get( dep.getIdVp(), sess);
            }
            t.commit();
        } catch (Exception e) {
            dep = null;
            depPower = null;
            Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(DepartmentRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return depPower==null?0:depPower.getId();
    }

    public static void main(String[] args) {
        List<DepartmentGroup> activeDepartmentGroup = (new DepartmentRepository()).getActiveListDepartmentGroup();
        for (DepartmentGroup depGroup : activeDepartmentGroup) {
            System.out.println(depGroup.getNameNp());
        }
    }
}
