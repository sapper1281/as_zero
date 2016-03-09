/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoReport;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.repository.FormInfo;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormBean extends BasePage implements Serializable {

    public FormBean() {
        super();
    }
    
     @ManagedProperty(value = "#{departmentTreePageBean}")
    private DepartmentTreePageBean departmentTreePageBean;
    private String frm;
    public DepartmentTreePageBean getDepartmentTreePageBean() {
        return departmentTreePageBean;
    }

    public void setDepartmentTreePageBean(DepartmentTreePageBean departmentTreePageBean) {
        this.departmentTreePageBean = departmentTreePageBean;
    }
    
     public void setFrm(String frm) {
        this.frm = frm;
        String[] split = frm.split("№");
        frmID=new Long(split[0]);
        frmAddress=split[1];
    }

    public String getFrm() {
        return frm;
    }
     
     
     
private String frmAddress;
    private long frmID;

    public String getFrmAddress() {
        return frmAddress;
    }

    public void setFrmAddress(String frmAddress) {
        this.frmAddress = frmAddress;
    }

    public long getFrmID() {
        return frmID;
    }

    public void setFrmID(long frmID) {
        this.frmID = frmID;
    }
    
    

    //<editor-fold defaultstate="collapsed" desc="поля">
    //нформацияЮ загружаемая из БД
    private FormInfo info;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private Date day;
    private String mes;
    private String year;
    private String dayBegin;
    private String dayEnd;
    
     private Calendar calendar = Calendar.getInstance();
   
     //Месяц начала периода
    private int mesBeg = calendar.get(Calendar.MONTH) + 1;
    //месяц конца периода
    private int mesEnd = calendar.get(Calendar.MONTH) + 1;
    //год начала периода
    private int yearBeg = calendar.get(Calendar.YEAR);
    //год конца периода
    private int yearEnd = calendar.get(Calendar.YEAR);

    public int getMesBeg() {
        return mesBeg;
    }

    public void setMesBeg(int mesBeg) {
        this.mesBeg = mesBeg;
    }

    public int getMesEnd() {
        return mesEnd;
    }

    public void setMesEnd(int mesEnd) {
        this.mesEnd = mesEnd;
    }

    public int getYearBeg() {
        return yearBeg;
    }

    public void setYearBeg(int yearBeg) {
        this.yearBeg = yearBeg;
    }

    public int getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(int yearEnd) {
        this.yearEnd = yearEnd;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="get-set">

    public String getDayBegin() {
        return dayBegin;
    }

    public void setDayBegin(String dayBegin) {
        this.dayBegin = dayBegin;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    /**
     * Возвращает название подразделения в котором работает авторизовавшийся
     * ползователь
     *
     * @return название подразделения в котором работает авторизовавшийся
     * ползователь
     */
    public String getNameDep() {
        return info.getNameDep();
    }

    /**
     * Назначает название подразделения в котором работает авторизовавшийся
     * ползователь
     *
     * @param nameDep название подразделения в котором работает авторизовавшийся
     * ползователь
     */
    public void setNameDep(String nameDep) {
        info.setNameDep(nameDep);
    }

    /**
     * Возвращает сессионный бин
     *
     * @return сессионный бин
     */
    public AutorizationBean getSession() {
        return session;
    }

    /**
     * Назначает сессионный бин
     *
     * @param session сессионный бин
     */
    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    /**
     * Возвращает список форм, на которые есть права у вошедшего в систему
     * пользователя
     *
     * @return список форм, на которые есть права у вошедшего в систему
     * пользователя
     */
    public List<Form> getListForm() {

        return info.getListForm();
    }

      private List<ShortInfoReport> listRepFormDep;
    private long d1 = 0;
    private long idform1 = 0;
    private List<ShortInfoReport> listRepForm;
 public List<ShortInfoReport> getListRepForm() {
        return listRepForm;
    }

    public void setListRepForm(List<ShortInfoReport> listRepForm) {
        this.listRepForm = listRepForm;
    }
    /**
     * Возвращает список отчетов по форме, доступных определенному предприятию
     *
     * @param id_form id формы
     * @return список отчетов по форме, доступных определенному предприятию
     */
    public void updateListRepDateDep() {
        /*  List<ShortInfoReport> listRepFormDep = info.loadFromDBDep(id_form, departmentTreePageBean.getDeparNode().getId());
         List<ShortInfoReport> listRepForm = new ArrayList<ShortInfoReport>();
     
        
         for (ShortInfoReport report : listRepFormDep) {
            
         if (getYearBeg()==report.getDt_begin().getYear()+1900) {
         listRepForm.add(report);
                

         }
         }*/


        FormInfo g = new FormInfo();

        long d = 0;
        if (departmentTreePageBean.getDeparNode() == null) {
            d = 0;
        } else {
            d = departmentTreePageBean.getDeparNode().getId();
        }
        if (listRepFormDep == null || d1 != d || idform1 != frmID) {

            listRepFormDep = g.loadFromDBDep(frmID, d);
            d1=d;
            idform1=frmID;
        }

        listRepForm = new ArrayList<ShortInfoReport>();
        for (ShortInfoReport report : listRepFormDep) {
            System.out.println("id_form " + frm);
            System.out.println("dayBegin " + yearBeg);
            System.out.println("getYearBeg() " + getYearBeg());
            if (report.getFormID() == frmID && getYearBeg() == report.getDt_begin().getYear() + 1900) {
                listRepForm.add(report);

            }
        }



    }
    /**
     * Возвращает список отчетов по форме, доступных авторизовавшемуся
     * пользователю
     *
     * @param id_form id формы
     * @return список отчетов по форме, доступных авторизовавшемуся пользователю
     */
    public List<ShortInfoReport> getListRep(long id_form) {

        List<ShortInfoReport> listRepForm = new ArrayList<ShortInfoReport>();
        for (ShortInfoReport report : info.getListRep()) {
            if (report.getFormID() == id_form) {
                listRepForm.add(report);

            }
        }
        return listRepForm;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    //</editor-fold>

    public void handleDateSelect(SelectEvent event) {

        day = (Date) event.getObject();
        setMes(String.valueOf(day.getMonth() + 1));
        setYear(String.valueOf(day.getYear() + 1900));
    }

    public void handleDateSelectBegin(SelectEvent event) {
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy");
        Date dt = (Date) event.getObject();
        Calendar cdt = Calendar.getInstance();
        cdt.setTime(dt);
        cdt.set(Calendar.DATE, 1);
        dayBegin = dateFormatter2.format(cdt.getTime());
    }

    public void handleDateSelectEnd(SelectEvent event) {
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy");
        Date dt = (Date) event.getObject();
        Calendar cdt = Calendar.getInstance();
        cdt.setTime(dt);
        cdt.add(Calendar.MONTH, 1);
        cdt.set(Calendar.DATE, 1);
        cdt.add(Calendar.DATE, -1);
        dayEnd = dateFormatter2.format(cdt.getTime());
    }

    //<editor-fold defaultstate="collapsed" desc="конструктор">
    /**
     * Creates a new instance of FormBean
     */
    @PostConstruct
    public void Init() {
        if (session.getUser() == null) {
            session.setError("Необходимо выполнить вход в систему");
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("errorpage");
            } catch (IOException ex) {
                Logger.getLogger(FormBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            info = new FormInfo();
            info.loadFromDB(session.getUser().getUserID(), session.getUser().getDepartmentID());
        }
    }
    //</editor-fold>
}
