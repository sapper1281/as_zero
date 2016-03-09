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
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.autorisation.CheckRights;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.DetailsForm2;
import rzd.vivc.aszero.dto.DetailsForm2_dop;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsForm2;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.ReportForm2DetailsRepository;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.repository.UserRepository;
import rzd.vivc.aszero.service.StringImprover;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormTwoPageBeanNew extends BasePage implements Serializable {

    public FormTwoPageBeanNew() {
        super();
    }
    private String FONT = "Courier New";
    //private Report repForm2;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    /*даты доступности редактирования дат*/
    private String dt_max_cal_st;
    private String dt_min_cal_st;
    private String dt_min_cal_st_NEW;
    private DetailsForm2_dop f2dop;
    private List<DetailsForm2> listf2dop;
    private Date dayBeginD;
    private Date dayEndD;
    private float plan_tyt;
    private float fact_tyt;
    private float planE;
    private float factE;
    private float factASKUE;
    private boolean tr3;
     private int id;

    public boolean isTr3() {
        return tr3;
    }

    public void setTr3(boolean tr3) {
        this.tr3 = tr3;
    }
     
   
    
    public float getPlan_tyt() {
        return plan_tyt;
    }

    public void setPlan_tyt(float plan_tyt) {
        this.plan_tyt = plan_tyt;
    }

    public float getFact_tyt() {
        return fact_tyt;
    }

    public void setFact_tyt(float fact_tyt) {
        this.fact_tyt = fact_tyt;
    }

    public float getPlanE() {
        return planE;
    }

    public void setPlanE(float planE) {
        this.planE = planE;
    }

    public float getFactE() {
        return factE;
    }

    public void setFactE(float factE) {
        this.factE = factE;
    }

    public float getFactASKUE() {
        return factASKUE;
    }

    public void setFactASKUE(float factASKUE) {
        this.factASKUE = factASKUE;
    }

    public List<DetailsForm2> getListf2dop() {
        return listf2dop;
    }

    public void setListf2dop(List<DetailsForm2> listf2dop) {
        this.listf2dop = listf2dop;
    }

    public DetailsForm2_dop getF2dop() {
        return f2dop;
    }

    public void setF2dop(DetailsForm2_dop f2dop) {
        this.f2dop = f2dop;
    }

    public String getDt_min_cal_st_NEW() {
        return dt_min_cal_st_NEW;
    }

    public void setDt_min_cal_st_NEW(String dt_min_cal_st_NEW) {
        this.dt_min_cal_st_NEW = dt_min_cal_st_NEW;
    }

    public void setDt_max_cal_st(String dt_max_cal_st) {
        this.dt_max_cal_st = dt_max_cal_st;
    }

    public String getDt_min_cal_st() {
        return dt_min_cal_st;
    }

    public void setDt_min_cal_st(String dt_min_cal_st) {
        this.dt_min_cal_st = dt_min_cal_st;
    }

    public String getDt_max_cal_st() {
        return dt_max_cal_st;
    }

    @PostConstruct
    public void Init() {
         if((new CheckRights()).isAutorised()){
       long departmentID= session.getUser().getDepartmentID();
       long userID= session.getUser().getUserID();
       int num=session.getUser().getNum();;
        

        /*если документ сформирован*/
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id"))
                {
               plan_tyt=0;
                fact_tyt=0;
                planE=0;
                factE=0;
                factASKUE=0;
      
             id = new Integer(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString());

            /*Поиск информации кто когда сделал запись*/
            f2dop = (new ReportRepository()).getListWithDetailsForm2_dol(id);
            Date dt_max = f2dop.getDt_begin();
            SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat dateFormatter0 = new SimpleDateFormat("MM.yyyy");
            dt_min_cal_st_NEW = dateFormatter0.format(dt_max);

            tr = false;
            tr1 = false;
            tr3=false;
            
            Calendar dt_max_cal = Calendar.getInstance();
            dt_max_cal.setTime(dt_max);
            dt_max_cal.set(Calendar.DATE, 1);
            dayBeginD = dt_max_cal.getTime();
            System.out.println(dateFormatter1.format(dt_max_cal.getTime()));

            dt_max_cal.add(Calendar.MONTH, 1);
            dt_max_cal.set(Calendar.DATE, 1);
            dayEndD = dt_max_cal.getTime();
            System.out.println(dateFormatter1.format(dt_max_cal.getTime()));
            /*Формирование данных*/
            listf2dop = (new ReportRepository()).getListWithDetailsForm2(id, dayBeginD, dayEndD);
 /*по дороге + определение цвета*/
            for (DetailsForm2 detailsForm2 : listf2dop) {



                plan_tyt=plan_tyt+detailsForm2.getPlan_tyt().floatValue();
                fact_tyt=fact_tyt+detailsForm2.getFact_tyt().floatValue();
                planE=planE+detailsForm2.getPlanE().floatValue();
                factE=factE+detailsForm2.getFactE().floatValue();
                factASKUE=factASKUE+detailsForm2.getFactASKUE().floatValue();


                System.out.println("+++" + detailsForm2.getIdRep());
                /*определение цвета*/
                if (detailsForm2.getIdRep() != null) {
                    Date dt_b = new Date();
                    //дата начала проведения акции
                    System.out.println("+++1 " + detailsForm2.getDt_begin());
                    System.out.println("+++2 " + detailsForm2.getTime_begin());
                    dt_b.setTime(detailsForm2.getTime_begin().getTime() + detailsForm2.getDt_begin().getTime());

                    Calendar DT_NOW = Calendar.getInstance();
                    DT_NOW.setTime(dt_b);

                    Date dt_f = new Date();
                    //дата окончания проведения акции
                    System.out.println("+++1 " + detailsForm2.getTime_finish());
                    System.out.println("+++2 " + detailsForm2.getDt_begin());
                    dt_f.setTime(detailsForm2.getTime_finish().getTime() + detailsForm2.getDt_begin().getTime());



                    Date dt_b_m1 = new Date();
                    //дата начала проведения акции за 1 день
                    dt_b_m1.setTime(dt_b.getTime() - 86400 * 1000 + 3*60*60*1000 - num * 60 * 1000);


                    Date dt_f_p1 = new Date();
                    //дата окончания проведения акции +3 дня 12 часов
                    dt_f_p1.setTime(dt_f.getTime()/* + 86400 * 1000 */ + 3*60*60*1000 - num * 60 * 1000);

                    Calendar ct = Calendar.getInstance();
                    System.out.println("1=============" + dt_f_p1.toString());
                    ct.setTime(dt_f_p1);
                    ct.add(Calendar.HOUR, 72);
                    ct.set(Calendar.MINUTE, 0);
                    ct.set(Calendar.HOUR_OF_DAY, 12);
                    dt_f_p1.setTime(ct.getTime().getTime());
                    System.out.println("1=============" + dt_f_p1.toString());




                    if (detailsForm2.getDt_inputfact() != null) {
                        if (dt_f_p1.after(detailsForm2.getDt_inputfact())) {
                            detailsForm2.setInputActivityFact("green");
                        } else {
                            detailsForm2.setInputActivityFact("yellow");
                        }
                    } else {
                        detailsForm2.setInputActivityFact("red");
                    }



                    if (detailsForm2.getDt_inputplan() != null) {
                        if (dt_b_m1.after(detailsForm2.getDt_inputplan())) {
                            detailsForm2.setInputActivityPlan("green");
                        } else {
                            detailsForm2.setInputActivityPlan("yellow");
                        }
                    } else {
                        detailsForm2.setInputActivityPlan("red");
                    }
                } else {


                    detailsForm2.setInputActivityFact("red");
                    detailsForm2.setInputActivityPlan("red");

                }


            }

       } else {
            
             if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("form_id")) {
                 tr3=true;
                   plan_tyt=0;
                fact_tyt=0;
                planE=0;
                factE=0;
                factASKUE=0;
             Date dt_max = (new ReportRepository()).getDateDetails(2, departmentID);
             SimpleDateFormat dateFormatter0 = new SimpleDateFormat("MM.yyyy");
             SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
             tr = true;
             tr1 = false;
              /*Поиск информации кто когда сделал запись*/
             f2dop = (new ReportRepository()).getListWithDetailsForm2_dol_1(userID);
             Calendar dt = Calendar.getInstance();
             f2dop.setDt_create(dt.getTime());
              
             if (dt_max == null) {
             dt_max = new Date();
                
             Calendar dt_max_cal = Calendar.getInstance();
             dt_max_cal.setTime(dt_max);
              f2dop.setDt_begin(dt_max_cal.getTime());
             dayBeginD = dt_max_cal.getTime();
             dt_min_cal_st_NEW = dateFormatter0.format(dt_max_cal.getTime());
             System.out.println("=="+ dateFormatter1.format(dt_max_cal.getTime()));
             dt_max_cal.add(Calendar.MONTH, 1);
             dt_max_cal.set(Calendar.DATE, 1);
             dayEndD = dt_max_cal.getTime();
             dt_min_cal_st = "";
             dt_max_cal_st = "";
             tr1 = true;
             }else {
             Calendar dt_max_cal = Calendar.getInstance();
             dt_max_cal.setTime(dt_max);
             dt_max_cal.set(Calendar.DATE, 1);
             dt_max_cal.add(Calendar.MONTH, 1);
              dayBeginD = dt_max_cal.getTime();
             dt_min_cal_st_NEW = dateFormatter0.format(dt_max_cal.getTime());
             dt_min_cal_st = dateFormatter1.format(dt_max_cal.getTime());
             dt_max_cal.add(Calendar.MONTH, 1);
             dt_max_cal.add(Calendar.DATE, -1);
             dt_max_cal_st = dateFormatter1.format(dt_max_cal.getTime());
             dt_max_cal.add(Calendar.DATE, 1);
             dayEndD = dt_max_cal.getTime();
            // dt_max_cal_st = dateFormatter1.format(dt_max_cal.getTime());
             f2dop.setDt_begin(dayBeginD); 
            }
            
             
             listf2dop = (new ReportRepository()).getListWithDetailsForm2_1(departmentID, dayBeginD, dayEndD);
             
             /*по дороге + определение цвета*/
            for (DetailsForm2 detailsForm2 : listf2dop) {



                plan_tyt=plan_tyt+detailsForm2.getPlan_tyt().floatValue();
                fact_tyt=fact_tyt+detailsForm2.getFact_tyt().floatValue();
                planE=planE+detailsForm2.getPlanE().floatValue();
                factE=factE+detailsForm2.getFactE().floatValue();
                factASKUE=factASKUE+detailsForm2.getFactASKUE().floatValue();


                System.out.println("+++" + detailsForm2.getIdRep());
                /*определение цвета*/
                if (detailsForm2.getIdRep() != null) {
                    Date dt_b = new Date();
                    //дата начала проведения акции
                    System.out.println("+++1 " + detailsForm2.getDt_begin());
                    System.out.println("+++2 " + detailsForm2.getTime_begin());
                    dt_b.setTime(detailsForm2.getTime_begin().getTime() + detailsForm2.getDt_begin().getTime());

                    Calendar DT_NOW = Calendar.getInstance();
                    DT_NOW.setTime(dt_b);

                    Date dt_f = new Date();
                    //дата окончания проведения акции
                    System.out.println("+++1 " + detailsForm2.getTime_finish());
                    System.out.println("+++2 " + detailsForm2.getDt_begin());
                    dt_f.setTime(detailsForm2.getTime_finish().getTime() + detailsForm2.getDt_begin().getTime());



                    Date dt_b_m1 = new Date();
                    //дата начала проведения акции за 1 день
                    dt_b_m1.setTime(dt_b.getTime() - 86400 * 1000 + 3*60*60*1000 - num * 60 * 1000);


                    Date dt_f_p1 = new Date();
                    //дата окончания проведения акции +3 дня 12 часов
                    dt_f_p1.setTime(dt_f.getTime()/* + 86400 * 1000 */ + 3*60*60*1000 - num * 60 * 1000);

                    Calendar ct = Calendar.getInstance();
                    System.out.println("1=============" + dt_f_p1.toString());
                    ct.setTime(dt_f_p1);
                    ct.add(Calendar.HOUR, 72);
                    ct.set(Calendar.MINUTE, 0);
                    ct.set(Calendar.HOUR_OF_DAY, 12);
                    dt_f_p1.setTime(ct.getTime().getTime());
                    System.out.println("1=============" + dt_f_p1.toString());




                    if (detailsForm2.getDt_inputfact() != null) {
                        if (dt_f_p1.after(detailsForm2.getDt_inputfact())) {
                            detailsForm2.setInputActivityFact("green");
                        } else {
                            detailsForm2.setInputActivityFact("yellow");
                        }
                    } else {
                        detailsForm2.setInputActivityFact("red");
                    }



                    if (detailsForm2.getDt_inputplan() != null) {
                        if (dt_b_m1.after(detailsForm2.getDt_inputplan())) {
                            detailsForm2.setInputActivityPlan("green");
                        } else {
                            detailsForm2.setInputActivityPlan("yellow");
                        }
                    } else {
                        detailsForm2.setInputActivityPlan("red");
                    }
                } else {


                    detailsForm2.setInputActivityFact("red");
                    detailsForm2.setInputActivityPlan("red");
 }}}}}
    }
    
    
    
    
    
      public void updatedRep() {
           long userID= session.getUser().getUserID();
          int num=session.getUser().getNum();;
        
             long departmentID= session.getUser().getDepartmentID();
             SimpleDateFormat dateFormatter0 = new SimpleDateFormat("MM.yyyy");
             SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
              plan_tyt=0;
                fact_tyt=0;
                planE=0;
                factE=0;
                factASKUE=0;
                
             Calendar dt_max_cal = Calendar.getInstance();
             dt_max_cal.setTime(f2dop.getDt_begin());
             dt_max_cal.set(Calendar.DATE, 1);
             System.out.println(f2dop.getDt_begin());
             dayBeginD = dt_max_cal.getTime();
             dt_min_cal_st_NEW = dateFormatter0.format(dt_max_cal.getTime());
             System.out.println("=="+ dateFormatter1.format(dayBeginD));
             dt_max_cal.add(Calendar.MONTH, 1);
             dt_max_cal.set(Calendar.DATE, 1);
             dayEndD = dt_max_cal.getTime();
             dt_min_cal_st = "";
             dt_max_cal_st = "";
             tr1 = true;
                listf2dop = (new ReportRepository()).getListWithDetailsForm2_1(departmentID, dayBeginD, dayEndD);
             
             /*по дороге + определение цвета*/
            for (DetailsForm2 detailsForm2 : listf2dop) {



                plan_tyt=plan_tyt+detailsForm2.getPlan_tyt().floatValue();
                fact_tyt=fact_tyt+detailsForm2.getFact_tyt().floatValue();
                planE=planE+detailsForm2.getPlanE().floatValue();
                factE=factE+detailsForm2.getFactE().floatValue();
                factASKUE=factASKUE+detailsForm2.getFactASKUE().floatValue();


                System.out.println("+++" + detailsForm2.getIdRep());
                /*определение цвета*/
                if (detailsForm2.getIdRep() != null) {
                    Date dt_b = new Date();
                    //дата начала проведения акции
                    System.out.println("+++1 " + detailsForm2.getDt_begin());
                    System.out.println("+++2 " + detailsForm2.getTime_begin());
                    dt_b.setTime(detailsForm2.getTime_begin().getTime() + detailsForm2.getDt_begin().getTime());

                    Calendar DT_NOW = Calendar.getInstance();
                    DT_NOW.setTime(dt_b);

                    Date dt_f = new Date();
                    //дата окончания проведения акции
                    System.out.println("+++1 " + detailsForm2.getTime_finish());
                    System.out.println("+++2 " + detailsForm2.getDt_begin());
                    dt_f.setTime(detailsForm2.getTime_finish().getTime() + detailsForm2.getDt_begin().getTime());



                    Date dt_b_m1 = new Date();
                    //дата начала проведения акции за 1 день
                    dt_b_m1.setTime(dt_b.getTime() - 86400 * 1000 + 3*60*60*1000 - num * 60 * 1000);


                    Date dt_f_p1 = new Date();
                    //дата окончания проведения акции +3 дня 12 часов
                    dt_f_p1.setTime(dt_f.getTime()/* + 86400 * 1000 */ + 3*60*60*1000 - num * 60 * 1000);

                    Calendar ct = Calendar.getInstance();
                    System.out.println("1=============" + dt_f_p1.toString());
                    ct.setTime(dt_f_p1);
                    ct.add(Calendar.HOUR, 72);
                    ct.set(Calendar.MINUTE, 0);
                    ct.set(Calendar.HOUR_OF_DAY, 12);
                    dt_f_p1.setTime(ct.getTime().getTime());
                    System.out.println("1=============" + dt_f_p1.toString());




                    if (detailsForm2.getDt_inputfact() != null) {
                        if (dt_f_p1.after(detailsForm2.getDt_inputfact())) {
                            detailsForm2.setInputActivityFact("green");
                        } else {
                            detailsForm2.setInputActivityFact("yellow");
                        }
                    } else {
                        detailsForm2.setInputActivityFact("red");
                    }



                    if (detailsForm2.getDt_inputplan() != null) {
                        if (dt_b_m1.after(detailsForm2.getDt_inputplan())) {
                            detailsForm2.setInputActivityPlan("green");
                        } else {
                            detailsForm2.setInputActivityPlan("yellow");
                        }
                    } else {
                        detailsForm2.setInputActivityPlan("red");
                    }
                } else {


                    detailsForm2.setInputActivityFact("red");
                    detailsForm2.setInputActivityPlan("red");
 }}
    }

   
    
    
    
    
    
    
    
    
    
     public String save(boolean t) {
 
       User u = new UserRepository().get((new User(session.getUser().getUserID())));
       Report repForm2=null;
        /*если старый form_id*/
        if (!tr3){
          
            System.out.println(id);
            repForm2= (new ReportRepository()).getWithDetailsForm2((long)id);
           repForm2.setUsr(u); 
           repForm2.setDt_begin(f2dop.getDt_begin());
            repForm2.getDt_begin().setDate(15);
           for (ReportDetailsForm2 object : repForm2.getDetailsForm2()) {
               object.setDeleted(true);
                new ReportForm2DetailsRepository().save(((ReportDetailsForm2) object));
            }
           List<ReportDetailsForm2> reportDetailsForm2List = new ArrayList<ReportDetailsForm2>();
           for (DetailsForm2 detailsForm2 : listf2dop) {
         ReportDetailsForm2 reportDetailsForm2 = new ReportDetailsForm2();
         reportDetailsForm2.setDepartmentName(detailsForm2.getNamenp());
         reportDetailsForm2.setDepartment(new DepartmentRepository().get(detailsForm2.getId().longValue()));
         reportDetailsForm2.setFioChief(detailsForm2.getFiochief());
         reportDetailsForm2.setPhoneChief(detailsForm2.getPhonechief());
         reportDetailsForm2.setRorsChief(detailsForm2.getRorschief());
         reportDetailsForm2.setForm2Coll7(detailsForm2.getPlan_tyt().floatValue());
         reportDetailsForm2.setForm2Coll8(detailsForm2.getFact_tyt().floatValue());
         reportDetailsForm2.setForm2Coll9(detailsForm2.getPlanE().floatValue());
         reportDetailsForm2.setForm2Coll10(detailsForm2.getFactE().floatValue());
         reportDetailsForm2.setAskye(detailsForm2.getFactASKUE().floatValue());
         reportDetailsForm2.setInputActivityPlan(detailsForm2.getInputActivityPlan());
         reportDetailsForm2.setInputActivityFact(detailsForm2.getInputActivityFact());
         if(detailsForm2.getIdRep()!=null){
         reportDetailsForm2.setRepId(detailsForm2.getIdRep().longValue());}
         reportDetailsForm2.setReportForm2(repForm2);
         reportDetailsForm2List.add(reportDetailsForm2);
                 }
       repForm2.setDetailsForm2(reportDetailsForm2List);
       repForm2.setDt_create(new Date());
           
           
           
           }
       
       
       /*если новый form_id*/
        if (tr3) {
        repForm2= new Report();
       repForm2.setForm(new Form(2));
       repForm2.setUsr(u);
       repForm2.setDepartment(u.getDepartment());  
       repForm2.setDt_begin(f2dop.getDt_begin());
       repForm2.getDt_begin().setDate(15);
       List<ReportDetailsForm2> reportDetailsForm2List = new ArrayList<ReportDetailsForm2>();
       
       
         for (DetailsForm2 detailsForm2 : listf2dop) {
         ReportDetailsForm2 reportDetailsForm2 = new ReportDetailsForm2();
         reportDetailsForm2.setDepartmentName(detailsForm2.getNamenp());
         reportDetailsForm2.setDepartment(new DepartmentRepository().get(detailsForm2.getId().longValue()));
         reportDetailsForm2.setFioChief(detailsForm2.getFiochief());
         reportDetailsForm2.setPhoneChief(detailsForm2.getPhonechief());
         reportDetailsForm2.setRorsChief(detailsForm2.getRorschief());
         reportDetailsForm2.setForm2Coll7(detailsForm2.getPlan_tyt().floatValue());
         reportDetailsForm2.setForm2Coll8(detailsForm2.getFact_tyt().floatValue());
         reportDetailsForm2.setForm2Coll9(detailsForm2.getPlanE().floatValue());
         reportDetailsForm2.setForm2Coll10(detailsForm2.getFactE().floatValue());
         reportDetailsForm2.setAskye(detailsForm2.getFactASKUE().floatValue());
         reportDetailsForm2.setInputActivityPlan(detailsForm2.getInputActivityPlan());
         reportDetailsForm2.setInputActivityFact(detailsForm2.getInputActivityFact());
         if(detailsForm2.getIdRep()!=null){
         reportDetailsForm2.setRepId(detailsForm2.getIdRep().longValue());}
         reportDetailsForm2.setReportForm2(repForm2);
         reportDetailsForm2List.add(reportDetailsForm2);
                 }
       repForm2.setDetailsForm2(reportDetailsForm2List);
       repForm2.setDt_create(new Date());
  }
       
         System.out.println(repForm2);
         System.out.println(repForm2.getForm());
         System.out.println(repForm2.getUsr());
         System.out.println(repForm2.getDepartment());
         System.out.println(repForm2.getDt_create());
         System.out.println(repForm2.getDt_begin());
         
        if (repForm2 != null && repForm2.getForm() != null
                && repForm2.getUsr() != null
                && repForm2.getDepartment() != null
                //&& repForm2.getDt_create() != null
                && repForm2.getDt_begin() != null) {




            new ReportRepository().save(repForm2);
            for (ReportDetailsForm2 object : repForm2.getDetailsForm2()) {
                new ReportForm2DetailsRepository().save(((ReportDetailsForm2) object));
            }
        } else {
            return "errorpageadditional";

        }
        return "mainpage";
    }
    
    
    
    /*кнопка сохранить и календарь*/
    private boolean tr;
    /*если меняем дату на другой месяц*/
    private boolean tr1;
    private boolean id_go;

    public boolean isTr1() {
        return tr1;
    }

    public void setTr1(boolean tr1) {
        this.tr1 = tr1;
    }

    
    
    
    public boolean isId_go() {
        return id_go;
    }

    public void setId_go(boolean id_go) {
        this.id_go = id_go;
    }

    public boolean isTr() {
        return tr;
    }

    public void setTr(boolean tr) {
        this.tr = tr;
    }

   /* public Report getRepForm2() {
        return repForm2;
    }

    public void setRepForm2(Report repForm2) {
        this.repForm2 = repForm2;
    }*/
    /* private String fioChief;
     private String phoneChief;
     private String rorsChief;*/
    /*   private Date mes;

     public Date getMes() {
     return mes;
     }

     public void setMes(Date mes) {
     this.mes = mes;
     }*/
    private float askye;
    private float coll7;
    private float coll8;
    private float coll9;
    private float coll10;

    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    public float getColl7() {
        return coll7;
    }

    public void setColl7(float coll7) {
        this.coll7 = coll7;
    }

    public float getColl8() {
        return coll8;
    }

    public void setColl8(float coll8) {
        this.coll8 = coll8;
    }

    public float getColl9() {
        return coll9;
    }

    public void setColl9(float coll9) {
        this.coll9 = coll9;
    }

    public float getColl10() {
        return coll10;
    }

    public void setColl10(float coll10) {
        this.coll10 = coll10;
    }

    public float getAskye() {
        return askye;
    }

    public void setAskye(float askye) {
        this.askye = askye;
    }

    /**
     * Обновление устаревших данных
     */
   /* public void Upd() {
        coll7 = 0;
        coll8 = 0;
        coll9 = 0;
        coll10 = 0;
        askye = 0;

        System.out.println("****2" + repForm2);


        List<ReportDetailsForm2> reportDetailsForm2List = new ArrayList<ReportDetailsForm2>();



        List<Integer> deplistfist = new ArrayList<Integer>();
        deplistfist.add(repForm2.getUsr().getDepartment().getIdNp());

        List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALL(deplistfist, repForm2.getDt_begin().getMonth() + 1, repForm2.getDt_begin().getYear() + 1900);
        System.out.println(depList);
        System.out.println("wwww " + repForm2.getDt_begin().getMonth() + 1);

      

        if (depList.size() > 0) {

            for (Department object : depList) {
                System.out.println("=======ww " + object.getNameNp());

                ReportDetailsForm2 reportDetailsForm2 = new ReportDetailsForm2();
                reportDetailsForm2.setDepartmentName(object.getNameNp());
                reportDetailsForm2.setDepartment(object);




                ReportRepository repRep = new ReportRepository();
                List<Report> rep = repRep.getListWithDetailsIdNpMonth(((Department) object).getIdNp(), repForm2.getDt_begin().getMonth() + 1, repForm2.getDt_begin().getYear() + 1900);
                System.out.println("wwwww " + repForm2.getDt_begin().getMonth() + 1);
                if (rep.size() > 0) {

                    for (Report objectrep : rep) {
                        for (ReportDetails objectrepDetails : objectrep.getDetails()) {
                            //<editor-fold defaultstate="collapsed" desc="проход по ReportDetails">       

                            if ((objectrepDetails.getResource().getId() == 17)
                                    || (objectrepDetails.getResource().getId() == 3)
                                    || (objectrepDetails.getResource().getId() == 4)
                                    || (objectrepDetails.getResource().getId() == 5)
                                    || (objectrepDetails.getResource().getId() == 2)
                                    || (objectrepDetails.getResource().getId() == 1)
                                    || (objectrepDetails.getResource().getId() == 7)) {
                                reportDetailsForm2.setForm2Coll7(objectrepDetails.getPlan_tut() + reportDetailsForm2.getForm2Coll7());
                                if (objectrepDetails.getDt_inputFact() != null) {
                                    reportDetailsForm2.setForm2Coll8(objectrepDetails.getFact_tut() + reportDetailsForm2.getForm2Coll8());
                                }
                                coll7 = coll7 + objectrepDetails.getPlan_tut();
                                if (objectrepDetails.getDt_inputFact() != null) {
                                    coll8 = coll8 + objectrepDetails.getFact_tut();
                                }
                            }
                            if ((objectrepDetails.getResource().getId() == 13)) {
                                reportDetailsForm2.setForm2Coll9(objectrepDetails.getPlan_col() + reportDetailsForm2.getForm2Coll9());
                                coll9 = coll9 + objectrepDetails.getPlan_col();


                                if (objectrepDetails.getDt_inputFact() != null) {
                                    reportDetailsForm2.setForm2Coll10(objectrepDetails.getFact_col() + reportDetailsForm2.getForm2Coll10());
                                    coll10 = coll10 + objectrepDetails.getFact_col();

                                    if (objectrepDetails.isAskue()) {
                                        reportDetailsForm2.setAskye(objectrepDetails.getEconomy() + reportDetailsForm2.getAskye());
                                        askye = objectrepDetails.getEconomy() + askye;
                                    }
                                }

                            }

                            // }
                            //</editor-fold>
                        }

                        reportDetailsForm2.setRepId(((Report) objectrep).getId());
                        //reportDetailsForm2.setReportId(((Report)objectrep));

                        //<editor-fold defaultstate="collapsed" desc="цвет ячееек">                             
                        Date dt_b = new Date();

                        //дата начала проведения акции
                        dt_b.setTime(((Report) objectrep).getTime_begin().getTime() + ((Report) objectrep).getDt_begin().getTime());

                        Calendar DT_NOW = Calendar.getInstance();
                        DT_NOW.setTime(dt_b);

                        Date dt_f = new Date();
                        //дата окончания проведения акции
                        dt_f.setTime(((Report) objectrep).getTime_finish().getTime() + ((Report) objectrep).getDt_begin().getTime());



                        Date dt_b_m1 = new Date();
                        //дата начала проведения акции за 1 день
                        dt_b_m1.setTime(dt_b.getTime() - 86400 * 1000 + 10800000 - session.getUser().getNum() * 60 * 1000);

                        //   Date dt_f_p1 = new Date();
                        //дата окончания проведения акции за -1 день
                        //  dt_f_p1.setTime(dt_f.getTime() + 86400 * 1000 + 10800000 - session.getUser().getNum() * 60 * 1000);

//дата окончания проведения акции за -1 день
                        //дата окончания проведения акции +3 деня 12 часов
                        Date dt_f_p1 = new Date();

                        dt_f_p1.setTime(dt_f.getTime() + 10800000 - session.getUser().getNum() * 60 * 1000);

                        Calendar ct = Calendar.getInstance();
                        System.out.println("1=============" + dt_f_p1.toString());
                        ct.setTime(dt_f_p1);
                        ct.add(Calendar.HOUR, 72);
                        ct.set(Calendar.MINUTE, 0);
                        ct.set(Calendar.HOUR_OF_DAY, 12);
                        dt_f_p1.setTime(ct.getTime().getTime());
                        System.out.println("1=============" + dt_f_p1.toString());



                        System.out.println(dt_f_p1);


                        if (!((((Report) objectrep).getDt_inputFact()) == null)) {
                            if (dt_f_p1.after(((Report) objectrep).getDt_inputFact())) {
                                reportDetailsForm2.setInputActivityFact("green");
                            } else {
                                reportDetailsForm2.setInputActivityFact("yellow");
                            }
                        } else {
                            reportDetailsForm2.setInputActivityFact("red");
                        }



                        if (!((((Report) objectrep).getDt_inputPlan()) == null)) {
                            if (dt_b_m1.after(((Report) objectrep).getDt_inputPlan())) {
                                reportDetailsForm2.setInputActivityPlan("green");
                            } else {
                                reportDetailsForm2.setInputActivityPlan("yellow");
                            }
                        } else {
                            reportDetailsForm2.setInputActivityPlan("red");
                        }
                        //    </editor-fold>                    
                        //    }
                    }
                } else {
                    reportDetailsForm2.setRepId(0);
                    reportDetailsForm2.setInputActivityPlan("red");
                    reportDetailsForm2.setInputActivityFact("red");

                }

                reportDetailsForm2.setReportForm2(repForm2);
                reportDetailsForm2List.add(reportDetailsForm2);

                //  }
            }
            repForm2.setDetailsForm2(reportDetailsForm2List);

        }






    }*/

   /* public void updatedRep() {
        
        DepartmentRepository deprep = new DepartmentRepository();
        List<Department> depList = new ArrayList<Department>();

        User u = new UserRepository().get((new User(session.getUser().getUserID())));


        List<Integer> deplistfist = new ArrayList<Integer>();
        deplistfist.add(u.getDepartment().getIdNp());

        depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALL(deplistfist, repForm2.getDt_begin().getMonth() + 1, repForm2.getDt_begin().getYear() + 1900);
        System.out.println("wwwwwwwww " + repForm2.getDt_begin().getMonth() + 1);


        if (depList.size() > 0) {
            SimpleDateFormat dateFormatter0 = new SimpleDateFormat("MM.yyyy");
            repForm2.setDt_begin(repForm2.getDt_begin());
            dt_min_cal_st_NEW = dateFormatter0.format(repForm2.getDt_begin().getTime());


//            Upd1();

            //  repForm2.setDt_begin(mes);
        }
    }
*/
   

    public void Print(Object document) {
        coll7 = 0;
        coll8 = 0;
        coll9 = 0;
        coll10 = 0;
        askye = 0;
        HSSFWorkbook wb = (HSSFWorkbook) document;

        StringImprover f = new StringImprover();

        HSSFSheet sheet = wb.createSheet("new sheet");

        HSSFPrintSetup print = sheet.getPrintSetup();
        print.setScale((short) 83);
        print.setPaperSize((short) print.A4_PAPERSIZE);



        Font font_10 = wb.createFont();
        font_10.setItalic(true);
        font_10.setFontHeightInPoints((short) 10);
        font_10.setFontName(FONT);

        Font font_14 = wb.createFont();
        font_14.setItalic(true);
        font_14.setFontHeightInPoints((short) 14);
        font_14.setFontName(FONT);

        Font font_18 = wb.createFont();
        font_18.setItalic(true);
        font_18.setFontHeightInPoints((short) 18);
        font_18.setFontName(FONT);


        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.getFontIndex();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setFont(font_18);




        CellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.getFontIndex();
        cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle1.setFont(font_14);



        CellStyle cellStyle1_r = wb.createCellStyle();
        cellStyle1_r.getFontIndex();
        cellStyle1_r.setFillBackgroundColor(IndexedColors.RED.getIndex());
        cellStyle1_r.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle1_r.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle1_r.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle1_r.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle1_r.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle1_r.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle1_r.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle1_r.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle1_r.setFont(font_14);

        CellStyle cellStyle1_y = wb.createCellStyle();
        cellStyle1_y.getFontIndex();
        cellStyle1_y.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle1_y.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle1_y.setFillPattern(CellStyle.SOLID_FOREGROUND);

        cellStyle1_y.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle1_y.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle1_y.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle1_y.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle1_y.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle1_y.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle1_y.setFont(font_14);

        CellStyle cellStyle1_g = wb.createCellStyle();
        cellStyle1_g.getFontIndex();
        cellStyle1_g.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        cellStyle1_g.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle1_g.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle1_g.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle1_g.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle1_g.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle1_g.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle1_g.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle1_g.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle1_g.setFont(font_14);


        CellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.getFontIndex();
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setFont(font_10);

        CellStyle cellStyle_L = wb.createCellStyle();
        cellStyle_L.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle_L.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle_L.getFontIndex();
        cellStyle_L.setFont(font_14);

        CellStyle cellStyle_R = wb.createCellStyle();
        cellStyle_R.setAlignment(CellStyle.ALIGN_RIGHT);
        cellStyle_R.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle_R.getFontIndex();
        cellStyle_R.setFont(font_14);

        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("HHч. mmмин.");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.");
        Row row = sheet.createRow(0);
        new createCell().createCell(wb, sheet, cellStyle_R, row, 0, 0, 0, 0, 10, "Форма 2 за " + dt_min_cal_st_NEW);

        Row row1 = sheet.createRow(1);
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 10, f2dop.getNamenp());

        Row row2 = sheet.createRow(2);
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 0, 0, 0, "Наименование структурного подразделения");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 1, 1, 1, "Начальник(Ф.И.О.)");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 2, 2, 2, "Рабочий телефон");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 3, 3, 3, "РОРС Телефон");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 4, 4, 4, "Ввод мероприятий");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 5, 5, 5, "Ввод отчета по экономии");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 6, 6, 6, "План экономии ТУТ");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 7, 7, 7, "Факт экономии ТУТ");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 8, 8, 8, "План экономии эл. энергии кВтч");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 9, 9, 9, "Факт экономии эл. энергии кВтч");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 10, 10, 10, "Факт экономии эл. энергии АСКУЭ");



        HSSFRow row3 = sheet.createRow(3);
        for (int i1 = 0; i1 < 11; i1++) {
            new createCell().createCell(wb, sheet, cellStyle1, row3, 3, 3, i1, i1, i1, String.valueOf(i1 + 1));
        }



     //   listf2dop.getDetailsForm2();
        int j = 4;
        for (int i = 0; i < listf2dop.size(); i++) {
            j = i + 4;
            HSSFRow header8 = sheet.createRow(j);
            DetailsForm2 repDetailsForm2 = listf2dop.get(i);


            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 0, 0, 0, repDetailsForm2.getNamenp());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 1, 1, 1, repDetailsForm2.getFiochief());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 2, 2, 2, repDetailsForm2.getPhonechief());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 3, 3, 3, repDetailsForm2.getRorschief());


            if (repDetailsForm2.getInputActivityPlan().equals("green")) {
                new createCell().createCell(wb, sheet, cellStyle1_g, header8, j, j, 4, 4, 4, "   ");
            }
            if (repDetailsForm2.getInputActivityPlan().equals("yellow")) {
                new createCell().createCell(wb, sheet, cellStyle1_y, header8, j, j, 4, 4, 4, "   ");
            }
            if (repDetailsForm2.getInputActivityPlan().equals("red")) {
                new createCell().createCell(wb, sheet, cellStyle1_r, header8, j, j, 4, 4, 4, "   ");
            }
            if (repDetailsForm2.getInputActivityFact().equals("green")) {
                System.out.println(repDetailsForm2.getInputActivityFact());
                new createCell().createCell(wb, sheet, cellStyle1_g, header8, j, j, 5, 5, 5, "   ");
            }
            if (repDetailsForm2.getInputActivityFact().equals("yellow")) {
                System.out.println(repDetailsForm2.getInputActivityFact());
                new createCell().createCell(wb, sheet, cellStyle1_y, header8, j, j, 5, 5, 5, "   ");
            }
            if (repDetailsForm2.getInputActivityFact().equals("red")) {
                System.out.println(repDetailsForm2.getInputActivityFact());
                new createCell().createCell(wb, sheet, cellStyle1_r, header8, j, j, 5, 5, 5, "   ");
            }




            coll7 = coll7 + repDetailsForm2.getPlan_tyt().floatValue();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, f.formatForEXCEL(repDetailsForm2.getPlan_tyt().floatValue()));
            coll8 = coll8 + repDetailsForm2.getFact_tyt().floatValue();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, f.formatForEXCEL(repDetailsForm2.getFact_tyt().floatValue()));
            coll9 = coll9 + repDetailsForm2.getPlanE().floatValue();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 8, 8, 8, f.formatForEXCEL(repDetailsForm2.getPlanE().floatValue()));
            coll10 = coll10 + repDetailsForm2.getFactE().floatValue();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 9, 9, 9, f.formatForEXCEL(repDetailsForm2.getFactE().floatValue()));
            askye = askye + repDetailsForm2.getFactASKUE().floatValue();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 10, 10, 10, f.formatForEXCEL(repDetailsForm2.getFactASKUE().floatValue()));



        }
        j++;
        HSSFRow headerj01 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_R, headerj01, j, j, 0, 0, 5, "ИТОГО: ");

        new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 6, 6, 6, f.formatForEXCEL(coll7));
        new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 7, 7, 7, f.formatForEXCEL(coll8));
        new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 8, 8, 8, f.formatForEXCEL(coll9));
        new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 9, 9, 9, f.formatForEXCEL(coll10));
        new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 10, 10, 10, f.formatForEXCEL(askye));

        j++;
        HSSFRow headerj1 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_L, headerj1, j, j, 0, 0, 10, "Дата внесения информации: " + dateFormatter2.format(f2dop.getDt_create()));

        j++;
        HSSFRow headerj2 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_L, headerj2, j, j, 0, 0, 10, "Исполнитель: " + f2dop.getFio());

        j++;
        HSSFRow headerj3 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_L, headerj3, j, j, 0, 0, 10, "Телефон: " + f2dop.getPhone());

        for (int i1 = 0; i1 < 11; i1++) {
            sheet.autoSizeColumn(i1, true);

        }


        wb.removeSheetAt(0);


    }
    /*
     public void onEdit(RowEditEvent event) {
        
       
       
        
     }

     public void onCancel(RowEditEvent event) {
     }*/
}
