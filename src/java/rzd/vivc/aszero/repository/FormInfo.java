/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoReport;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.repository.DepartmentRepository;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 * Информация, загружаемая из БД для страницы со списком форма и отчетов
 *
 * @author VVolgina
 */
public class FormInfo implements Serializable{
    //<editor-fold defaultstate="collapsed" desc="get-set">
    //список форм пользователя

    private List<Form> listForm;
    //список отчетов пользователя
    private List<ShortInfoReport> listRep;
    //название подразделения
    private String nameDep;

    /**
     * список форм пользователя
     *
     * @return список форм пользователя
     */
    public List<Form> getListForm() {
        return listForm;
    }

    /**
     * список форм пользователя
     *
     * @param listForm список форм пользователя
     */
    public void setListForm(List<Form> listForm) {
        this.listForm = listForm;
    }

    /**
     * список отчетов пользователя
     *
     * @return список отчетов пользователя
     */
    public List<ShortInfoReport> getListRep() {
        return listRep;
    }

    /**
     * список отчетов пользователя
     *
     * @param listRep список отчетов пользователя
     */
    public void setListRep(List<ShortInfoReport> listRep) {
        this.listRep = listRep;
    }

    /**
     * название подразделения
     *
     * @return название подразделения
     */
    public String getNameDep() {
        return nameDep;
    }

    /**
     * название подразделения
     *
     * @param nameDep название подразделения
     */
    public void setNameDep(String nameDep) {
        this.nameDep = nameDep;
    }
    //</editor-fold>

    
     /**
     * Извлекаем из БД всю необходимую информация для страницы FormBean
     *
     * @param userID id авторизоваавшегося пользователя
     * @param departmentID id его подраделения
     */
    public List<ShortInfoReport> loadFromDBDep(long formId, long departmentID) {
        List<ShortInfoReport> listRepDep = new ArrayList<ShortInfoReport>();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            //загружаем
           listRepDep = new ReportRepository().getShortInfoReportListByDep(departmentID,sess);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormInfo.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormInfo.class.getName()).log(Level.ERROR, null, he);
                }
            }
            //обнуляем, если загрузить не получилось
            nameDep = "Не определено";
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormInfo.class.getName()).log(Level.ERROR, null, he);
            }
            //если по запросу ничего не найдено,
            //инициализируем пустые списки во избежание NullPointerExeption
            
            
         /*   if (listForm == null) {
                listForm = new ArrayList<Form>();
            }
            if (listRep == null) {
                listRep = new ArrayList<ShortInfoReport>();
            }*/
        }
        return listRepDep;
        }
    
    
    
    /**
     * Извлекаем из БД всю необходимую информация для страницы FormBean
     *
     * @param userID id авторизоваавшегося пользователя
     * @param departmentID id его подраделения
     */
    public void loadFromDB(long userID, long departmentID) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            //загружаем
            nameDep = new DepartmentRepository().get(departmentID, sess).getNameNp();
            listForm = (new FormRepository()).getAllActiveFormsListForUser(userID,sess);
            listRep = new ReportRepository().getShortInfoReportListByDep(departmentID,sess);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(FormInfo.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(FormInfo.class.getName()).log(Level.ERROR, null, he);
                }
            }
            //обнуляем, если загрузить не получилось
            nameDep = "Не определено";
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(FormInfo.class.getName()).log(Level.ERROR, null, he);
            }
            //если по запросу ничего не найдено,
            //инициализируем пустые списки во избежание NullPointerExeption
            if (listForm == null) {
                listForm = new ArrayList<Form>();
            }
            if (listRep == null) {
                listRep = new ArrayList<ShortInfoReport>();
            }
        }

    }
}
