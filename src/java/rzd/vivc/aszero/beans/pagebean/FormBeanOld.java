/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.FormRepository;
import rzd.vivc.aszero.repository.ReportRepository;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormBeanOld extends BasePage implements Serializable {

    public FormBeanOld() {
        super();
    }

    //<editor-fold defaultstate="collapsed" desc="поля">
    private List<Form> listForm;
    private List<Report> listRep;
    private String nameDep;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private Date day;
    private String mes;
    private String year;
    private String dayBegin;

    private String dayEnd;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="get-set">
    public String getDayBegin() {
      /* if(dayBegin==null||dayBegin.equals("")){
       SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yy");
       Date dt=new Date();
       Calendar cdt= Calendar.getInstance();
       cdt.setTime(dt);
       cdt.set(Calendar.DATE, 1);
           System.out.println("----"+dateFormatter2.format(cdt.getTime()));
       dayBegin = dateFormatter2.format(cdt.getTime());
       System.out.println("=====dayBegin "+dayBegin);
        
        }*/
        
        
        
        return dayBegin;
    }

    public void setDayBegin(String dayBegin) {
        this.dayBegin = dayBegin;
    }

    public String getDayEnd() {
      /*   if(dayEnd==null||dayEnd.equals("")){
        
        
         SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yy");
          Date dt=new Date();
         // dt.setMonth(dt.getMonth() + 1);
         // dt.setYear(dt.getYear() + 1900);
          Calendar cdt= Calendar.getInstance();
          cdt.setTime(dt);
          cdt.add(Calendar.MONTH, 1);
          cdt.set(Calendar.DATE, 1);
          cdt.add(Calendar.DATE, -1);
          dayEnd = dateFormatter2.format(cdt.getTime());
          System.out.println("=====dayEnd "+dayEnd);
         }*/
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }
    
    
    
    
    public Date getDay() {
        /*if(day==null){
        day = new Date();
        setMes(String.valueOf(day.getMonth() + 1));
        setYear(String.valueOf(day.getYear() + 1900));
        }*/
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
       
        return nameDep;
    }

    /**
     * Назначает название подразделения в котором работает авторизовавшийся
     * ползователь
     *
     * @param nameDep название подразделения в котором работает авторизовавшийся
     * ползователь
     */
    public void setNameDep(String nameDep) {
        this.nameDep = nameDep;
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
       
        return listForm;
    }

    /**
     * Возвращает список отчетов по форме, доступных авторизовавшемуся
     * пользователю
     *
     * @param id_form id формы
     * @return список отчетов по форме, доступных авторизовавшемуся пользователю
     */
    public List<Report> getListRep(long id_form) {

        List<Report> listRepForm = new ArrayList<Report>();
        for (Report report : listRep) {
            if (report.getForm().getId() == id_form) {
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
       Date dt=(Date) event.getObject();
      // dt.setMonth(dt.getMonth() + 1);
      // dt.setYear(dt.getYear() + 1900);
       Calendar cdt= Calendar.getInstance();
       cdt.setTime(dt);
       cdt.set(Calendar.DATE, 1);
       dayBegin = dateFormatter2.format(cdt.getTime());
         System.out.println("=====dayBegin "+dayBegin);
    }
     
      public void handleDateSelectEnd(SelectEvent event) {
          SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy");
          Date dt=(Date) event.getObject();
         // dt.setMonth(dt.getMonth() + 1);
         // dt.setYear(dt.getYear() + 1900);
          Calendar cdt= Calendar.getInstance();
          cdt.setTime(dt);
          cdt.add(Calendar.MONTH, 1);
          cdt.set(Calendar.DATE, 1);
          cdt.add(Calendar.DATE, -1);
          dayEnd = dateFormatter2.format(cdt.getTime());
          System.out.println("=====dayEnd "+dayEnd);
    }
    

    //<editor-fold defaultstate="collapsed" desc="конструктор">
    /**
     * Creates a new instance of FormBean
     */
    @PostConstruct
    public void Init() {
         //пробует извлечь подразделение по Id пользователя из БД
        try {
             nameDep = (new DepartmentRepository().get(session.getUser().getDepartmentID())).getNameNp();
        } catch (Exception e) {
            //в случае ошибки возвращает пустую строку
            nameDep="Не определено";
        }

        try {
            //Извлекаем из БД список
            listForm = (new FormRepository()).getAllActiveFormsListForUser(session.getUser().getUserID());
            //если по запросу ничего не найдено,
            //инициализируем пустой список во избежание NullPointerExeption
            //при просмотре списка
            if (listForm == null) {
                listForm = new ArrayList<Form>();
            }
        } catch (Exception e) {
            //если произошла ошибка соединения с БД, вернем пустой список
            listForm = new ArrayList<Form>();
        }



        try {
            //Извлекаем из БД список
            listRep = (new ReportRepository()).getReportListByDep(session.getUser().getDepartmentID());
            //если по запросу ничего не найдено,
            //инициализируем пустой список во избежание NullPointerExeption
            //при просмотре списка
            if (listRep == null) {
                listRep = new ArrayList<Report>();
            }
        } catch (Exception e) {
            //если произошла ошибка соединения с БД, вернем пустой список
            listRep = new ArrayList<Report>();
        }





    }

  
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="не используемое">
       /* private List<Form> listFormDep;
     private int idFormSelect;
     private boolean formSelected = false;
     private boolean formView = false;*/
    /*public void saveFormView() {
     * formView = true;
     * }
     * 
     * public void saveSerialNumForm(int serialNumForm) {
     * //if(serialNumForm instanceof Integer){
     * System.out.println("idFormSelect==========" + idFormSelect);
     * idFormSelect = Integer.parseInt(String.valueOf(serialNumForm));
     * formSelected = true;
     * // }
     * 
     * }
     * 
     * public boolean isFormView() {
     * return formView;
     * }
     * 
     * public void setFormView(boolean formView) {
     * this.formView = formView;
     * }
     * 
     * public boolean isFormSelectad() {
     * return formSelected;
     * }
     * 
     * public void setFormSelectad(boolean formSelected) {
     * this.formSelected = formSelected;
     * }
     * 
     * public int getIdFormSelect() {
     * return idFormSelect;
     * }
     * 
     * public void setIdFormSelect(int idFormSelect) {
     * this.idFormSelect = idFormSelect;
     * }
     
     * 
     * 
     * 
     * public List<Report> getListRep(long id_form){
     * List<Report> listRep= new ArrayList<Report>();
     * List<Form> listForm1 = (new FormRepository()).getActiveFormWithReportList();
     * 
     * // итзменить на более удобный запрос rep привязать к dep
     * 
     * Iterator itr =  listForm1.iterator();
     * while (itr.hasNext()) {
     * Object object = itr.next();
     * if (object instanceof Form) {
     * 
     * if  (((Form)object).getId()==id_form) {
     * 
     * Department   dep= (new UserRepository().get(new User(session.getUser().getUserID()))).getDepartment();
     * 
     * 
     * Iterator per =  (((Form)object).getReport()).iterator();
     * while (per.hasNext()) {
     * Object objectper = per.next();
     * if (objectper instanceof Report) {
     * 
     * 
     * if(dep.getId()==((Report)objectper).getDepartment().getId())
     * listRep.add((Report)objectper);
     * 
     * }}}
     * }}
     * 
     * 
     * 
     * return listRep;
     * }
     *
     *
     * public List<Form> getListFormDep() {
     * //список документов введенных в определенном СП
     * listFormDep = (new FormRepository()).getActiveFormWithReportAndDepartmentList((new UserRepository().get(new User(session.getUser().getUserID()))).getDepartment().getIdNp());
     * 
     * return listFormDep;
     * }
     * 
     * public void setListFormDep(List<Form> listFormDep) {
     * this.listFormDep = listFormDep;
     * }
     */
    /*
     * public List<Form> getListForm() {
     * 
     * 
     * listForm = (new FormRepository()).getActiveFormWithReportList();
     * return listForm;
     * 
     * }*/
    /**
     * Назначает список форм, на которые есть права у вошедшего в систему
     * пользователя, вместе с введенными по ним отчетами
     *
     * @param listForm список форм, на которые есть права у вошедшего в систему
     * пользователя, вместе с введенными по ним отчетами
     */
    /* public void setListForm(List<Form> listForm) {
     this.listForm = listForm;
     }*/
    /*public List<Report> getListRep(long id_form) {
     List<Report> listRep = new ArrayList<Report>();
     List<Form> listForm1 = (new FormRepository()).getActiveFormWithReportListByDep(session.getUser().getDepartmentID());
     for (Form form : listForm1) {
     if (form.getId() == id_form) {
     listRep = form.getReport();
     }
     }
     return listRep;
     }*/
    //</editor-fold>
}
