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
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.form.Form;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 *
 * @author apopovkin
 */
public class FormRepository {

    /**
     * Добавление новой/редактирование службы
     *
     * @param elem служба
     */
    public void save(Form elem) {

        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            sess.saveOrUpdate(elem);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
    }

    /**
     * Извлекает из БД информацию о службе
     *
     * @param id id
     * @return служба
     */
    public Form get(long id) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        Form bd = null;
        try {
            t = sess.beginTransaction();
            bd = get(id, sess);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
            bd = null;
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected Form get(long id, Session sess) {
        return (Form) sess.get(Form.class, id);
    }

    /**
     * Извлекает из БД весь список неудаленных служб, отсортированный по
     * названию
     *
     * @return список пользователей
     */
    public List<Form> getActiveForm() {
        List<Form> dbList = null;

        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            dbList = (List<Form>) sess.createQuery(
                    "from Form where deleted=0 order by serialNumForm").list();
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
            dbList = null;
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return dbList;
    }

    /**
     * Извлекает из БД весь список неудаленных служб, отсортированный по
     * названию
     *
     * @return список пользователей
     */
    public List<Form> getActiveFormWithReportList() {
        List<Form> dbList = null;

        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            dbList = (ArrayList<Form>) sess.createQuery(
                    "select distinct f from Form f left join fetch f.report rep "
                    + "where f.deleted=0 and rep.deleted=0 order by f.serialNumForm").list();
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
            dbList = null;
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return dbList;
    }

    public List<Form> getAllActiveFormsListForUser(long id) {
        List<Form> dbList = null;

        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            dbList = getAllActiveFormsListForUser(id, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
            dbList = null;
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return dbList;

    }
    
    public List<Form> getAllActiveFormsListForUser(long id, Session sess) {

            return (ArrayList<Form>) sess.createQuery(
                    "select distinct f from Form f join f.users u "
                    + "where f.deleted=0 and u.id=:uid and u.deleted=0 order by f.serialNumForm").setLong("uid", id).list();

    }

    public List<Form> getActiveFormWithReportAndDepartmentList(int idNp) {
        List<Form> dbList = null;

        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            dbList = (ArrayList<Form>) sess.createQuery(
                    "select distinct f from Form f left join fetch f.report rep left join fetch rep.department dep "
                    + "where f.deleted=0 and rep.deleted=0 and  dep.deleted=0 and dep.idNp=:idNp  order by f.serialNumForm").setLong("idNp", idNp).list();

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
            dbList = null;
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return dbList;
    }

    public List<Form> getElementsForm(int element, boolean delete_flag) {
        List<Form> dbList = null;

        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            dbList = getElementsForm(element, delete_flag, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
            dbList = null;
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return dbList;

    }

    public List<Form> getElementsForm(int element, boolean deleted, Session session) {

        List<Form> dbList;
        /* if(dt_end==null && dt_beg==null){
         dbList = (List<Department>) session.createQuery(
         "from Department where delete_flag=:delete_flag "
         + "order by department_sn")
         .setParameter("delete_flag", delete_flag).list();
         } else{*/
        dbList = (List<Form>) session.createQuery(
                " from Form where deleted=:deleted and "
                + " serialNumForm=:serialNumForm")
                //+ " ((dt_beg<:dt_end and dt_end>:dt_beg) or (dt_beg<:dt_end and dt_end is null)) order by department_sn")
                .setParameter("deleted", deleted)
                .setParameter("serialNumForm", element).list();
        // .setDate("dt_beg", dt_beg)
        // .setDate("dt_end", dt_end).list();
        // }
        return dbList;
    }

    public List<Form> getActiveFormWithReportListByDep(long id) {
        List<Form> dbList;

        // извлекаем список неудаленных пользователей отсортированный по фамилии
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            // TODO подумать о критериях
            Department dep = (new DepartmentRepository()).get(id, sess);
            dbList = (ArrayList<Form>) sess.createQuery(
                    "select distinct f from Form f left join fetch f.report rep  where f.deleted=0 and rep.deleted=0 and rep.department.deleted=0 and (rep.department.id=:dep or rep.department.idVp=:deps) order by rep.department,rep.usr, rep.dt_begin").setLong("dep", id).setLong("deps", dep.getIdNp()).list();
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
            dbList = null;
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
        return dbList;
    }

    public static void main(String[] args) {
        List<Form> activeFormWithReportListForUser = (new FormRepository()).getAllActiveFormsListForUser(2);
        for (Form form : activeFormWithReportListForUser) {
            System.out.println(form);
        }

        /*   FormRepository formRep=new FormRepository();
         Form form=new Form();
        
        
               
        
         form.setSerialNumForm(3);
         form.setShortNameForm("Ф3");
         form.setFullNameForm("Форма 3");
        
         formRep.save(form);*/

        /*  FormRepository formRep=new FormRepository();
         List<Form> f=   formRep.getActiveFormWithReportAndDepartmentList(1287);
        
         Iterator itr =  f.iterator();
         while (itr.hasNext()) {
         Object object = itr.next();
         if (object instanceof Form) {
         
         
         System.out.println(((Form)object).getId()+"======"+((Form)object).getReport());
         Iterator per =  (((Form)object).getReport()).iterator();
         while (per.hasNext()) {
         Object objectper = per.next();
         if (objectper instanceof Report) {
         System.out.println("======"
         +((Report)objectper).getDepartment().getNameNp());
         
         }
         }
         
         
         }}*/

    }
}
