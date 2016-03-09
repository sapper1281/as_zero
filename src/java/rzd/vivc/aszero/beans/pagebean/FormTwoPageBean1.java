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
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.dto.Department;
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
public class FormTwoPageBean1 extends BasePage implements Serializable {
  public FormTwoPageBean1() {
        super();
    }
    
    private String FONT = "Courier New";
    private Report repForm2;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private boolean tr;
    private boolean id_go;
    /*даты доступности редактирования дат*/
    private String dt_max_cal_st;
    private String dt_min_cal_st;
    private String dt_min_cal_st_NEW;

    public boolean isId_go() {
        return id_go;
    }

    public void setId_go(boolean id_go) {
        this.id_go = id_go;
    }

    public String getDt_min_cal_st_NEW() {
        return dt_min_cal_st_NEW;
    }

    public void setDt_min_cal_st_NEW(String dt_min_cal_st_NEW) {
        this.dt_min_cal_st_NEW = dt_min_cal_st_NEW;
    }

    public String getDt_max_cal_st() {
        return dt_max_cal_st;
    }

    public boolean isTr() {
        return tr;
    }

    public void setTr(boolean tr) {
        this.tr = tr;
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

    public Report getRepForm2() {
        return repForm2;
    }

    public void setRepForm2(Report repForm2) {
        this.repForm2 = repForm2;
    }
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
    public void Upd1() {
        coll7 = 0;
        coll8 = 0;
        coll9 = 0;
        coll10 = 0;
        askye = 0;
        System.out.println("****1" + repForm2 + "===" + repForm2.getDetailsForm2());
        List<ReportDetailsForm2> reportDetailsForm2List = repForm2.getDetailsForm2();



        List<Integer> deplistfist = new ArrayList<Integer>();
        deplistfist.add(repForm2.getUsr().getDepartment().getIdNp());

        List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALL(deplistfist, repForm2.getDt_begin().getMonth() + 1, repForm2.getDt_begin().getYear() + 1900);

        
        
        System.out.println("ww " + repForm2.getDt_begin().getMonth() + 1);



        // List<Department> depList = deprep.getActiveListDepartmentsWithDetails(repForm2.getUsr().getDepartment().getIdNp());
        for (Department objectD : depList) {

            boolean t = true;
            for (ReportDetailsForm2 reportDetailsForm2 : reportDetailsForm2List) {



                if (reportDetailsForm2.getDepartment().getIdNp() == (objectD.getIdNp())) {
                    t = false;
                    /*заменить на id dep*/
                    reportDetailsForm2.setForm2Coll7(0);
                    reportDetailsForm2.setForm2Coll8(0);
                    reportDetailsForm2.setForm2Coll9(0);
                    reportDetailsForm2.setForm2Coll10(0);
                    reportDetailsForm2.setAskye(0);


                    ReportRepository repRep = new ReportRepository();
                    List<Report> rep = repRep.getListWithDetailsIdNpMonth(objectD.getIdNp(), repForm2.getDt_begin().getMonth() + 1, repForm2.getDt_begin().getYear() + 1900);

                    System.out.println("www " + repForm2.getDt_begin().getMonth() + 1);

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
                                //</editor-fold>
                            }


                            reportDetailsForm2.setRepId(objectrep.getId());
                            // reportDetailsForm2.setReportId(((Report)objectrep));
//<editor-fold defaultstate="collapsed" desc="цвет ячееек">  
                            Date dt_b = new Date();

                            //dt_b.setTime(((Report)objectrep).getDt_begin().getTime());
                            //дата начала проведения акции
                            dt_b.setTime(((Report) objectrep).getTime_begin().getTime() + ((Report) objectrep).getDt_begin().getTime());
                            System.out.println(((Report) objectrep).getId() + "==" + dt_b + "===" + ((Report) objectrep).getTime_begin().getTime());

                            Calendar DT_NOW = Calendar.getInstance();
                            DT_NOW.setTime(dt_b);
                            System.out.println(DT_NOW);

                            Date dt_f = new Date();
                            //дата окончания проведения акции
                            dt_f.setTime(((Report) objectrep).getTime_finish().getTime() + ((Report) objectrep).getDt_begin().getTime());
                            System.out.println(((Report) objectrep).getId() + "==" + dt_f + "===" + ((Report) objectrep).getTime_finish().getTime());



                            Date dt_b_m1 = new Date();
                            //дата начала проведения акции за 1 день
                            dt_b_m1.setTime(dt_b.getTime() - 86400 * 1000 + 10800000 - session.getUser().getNum() * 60 * 1000);
                            System.out.println(dt_b_m1);




                            // Date dt_f_p1 = new Date();
                            //дата окончания проведения акции за -1 день
                            // dt_f_p1.setTime(dt_f.getTime() + 86400 * 1000 + 10800000-session.getUser().getNum()*60*1000);
//дата окончания проведения акции за -1 день
                            //дата окончания проведения акции +3 деня 12 часов
                            Date dt_f_p1 = new Date();

                            dt_f_p1.setTime(dt_f.getTime()/* + 86400 * 1000 */ + 10800000 - session.getUser().getNum() * 60 * 1000);

                            Calendar ct = Calendar.getInstance();
                            System.out.println("1=============" + dt_f_p1.toString());
                            ct.setTime(dt_f_p1);
                            ct.add(Calendar.HOUR, 72);
                            ct.set(Calendar.MINUTE, 0);
                            ct.set(Calendar.HOUR_OF_DAY, 12);
                            dt_f_p1.setTime(ct.getTime().getTime());
                            System.out.println("1=============" + dt_f_p1.toString());



                            System.out.println(dt_f_p1);

                            System.out.println(dt_f_p1);



                            System.out.println(((Report) objectrep).getDt_inputFact());
                            System.out.println(((Report) objectrep).getDt_inputPlan());

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

                            //</editor-fold>
                            //  }
                        }
                    } else {
                        reportDetailsForm2.setRepId(0);
                        reportDetailsForm2.setInputActivityPlan("red");
                        reportDetailsForm2.setInputActivityFact("red");

                    }

                }


            }

            if (t) {







                ReportDetailsForm2 reportDetailsForm2 = new ReportDetailsForm2();
                reportDetailsForm2.setDepartmentName(objectD.getNameNp());
                reportDetailsForm2.setDepartment(objectD);
                ReportRepository repRep = new ReportRepository();
                List<Report> rep = repRep.getListWithDetailsIdNpMonth(((Department) objectD).getIdNp(), repForm2.getDt_begin().getMonth() + 1, repForm2.getDt_begin().getYear() + 1900);
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

                        //  Date dt_f_p1 = new Date();
                        //дата окончания проведения акции за -1 день
                        //   dt_f_p1.setTime(dt_f.getTime() + 86400 * 1000 + 10800000 - session.getUser().getNum() * 60 * 1000);

//дата окончания проведения акции за -1 день
                        //дата окончания проведения акции +3 деня 12 часов
                        Date dt_f_p1 = new Date();

                        dt_f_p1.setTime(dt_f.getTime()/* + 86400 * 1000 */ + 10800000 - session.getUser().getNum() * 60 * 1000);

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



            }









            // }
        }

        repForm2.setDetailsForm2(reportDetailsForm2List);
        //  }





    }

    public void Upd() {
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

        /* DepartmentRepository deprep = new DepartmentRepository();
         List<Department> depList = deprep.getActiveListDepartmentsWithDetails(repForm2.getUsr().getDepartment().getIdNp());*/

        if (depList.size() > 0) {

            for (Department object : depList) {
System.out.println("=======ww "+object.getNameNp() );

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

                        dt_f_p1.setTime(dt_f.getTime()/* + 86400 * 1000 */ + 10800000 - session.getUser().getNum() * 60 * 1000);

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






    }

    @PostConstruct
    public void Init() {


        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id")) {
            String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString();
            long id = new Integer(idStr);
            repForm2 = (new ReportRepository()).getWithDetailsForm2(id);
            //  mes = repForm2.getDt_begin();
            Date dt_max = repForm2.getDt_begin();
            tr = false;
            Calendar dt_max_cal = Calendar.getInstance();
            dt_max_cal.setTime(dt_max);
            dt_max_cal.set(Calendar.DATE, 1);
            SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat dateFormatter0 = new SimpleDateFormat("MM.yyyy");
            dt_min_cal_st_NEW = dateFormatter0.format(dt_max_cal.getTime());
            dt_min_cal_st = dateFormatter1.format(dt_max_cal.getTime());
            System.out.println(dt_min_cal_st);
            dt_max_cal.add(Calendar.MONTH, 1);
            dt_max_cal.add(Calendar.DATE, -1);
            dt_max_cal_st = dateFormatter1.format(dt_max_cal.getTime());
            System.out.println(dt_max_cal_st);
            Upd1();



        } else {
            User u = new UserRepository().get((new User(session.getUser().getUserID())));
            /*все структурные подразделения*/







            repForm2 = new Report();
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("form_id")) {
                repForm2.setForm(new Form(new Long(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("form_id").toString())));

                // repForm2.setForm(new Form(2));
                repForm2.setUsr(u);
                repForm2.setDepartment(u.getDepartment());

                //<editor-fold defaultstate="collapsed" desc="период отчета если New">             
                Date dt_max = (new ReportRepository()).getDateDetails(2, repForm2.getDepartment().getId());
                boolean t = false;
                if (dt_max == null) {
                    dt_max = new Date();
                    t = true;
                }

                SimpleDateFormat dateFormatter0 = new SimpleDateFormat("MM.yyyy");
                SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
                if (t) {
                    /*если нет данных то месяц любой*/
                    repForm2.setDt_begin(new Date());
                    Calendar dt_max_cal = Calendar.getInstance();
                    dt_max_cal.setTime(dt_max);
                    dt_min_cal_st_NEW = dateFormatter0.format(dt_max_cal.getTime());
                    System.out.println("=="+ dateFormatter1.format(dt_max_cal.getTime()));
                    dt_min_cal_st = "";
                    dt_max_cal_st = "";
                } else {
                    /*если есть данные то месяц следующий за максимальным*/
                    Calendar dt_max_cal = Calendar.getInstance();
                    dt_max_cal.setTime(dt_max);
                    dt_max_cal.set(Calendar.DATE, 1);
                    dt_max_cal.add(Calendar.MONTH, 1);
                    dt_min_cal_st_NEW = dateFormatter0.format(dt_max_cal.getTime());
                    dt_min_cal_st = dateFormatter1.format(dt_max_cal.getTime());
                    dt_max_cal.add(Calendar.MONTH, 1);
                    dt_max_cal.add(Calendar.DATE, -1);
                    dt_max_cal_st = dateFormatter1.format(dt_max_cal.getTime());
                    repForm2.setDt_begin(dt_max_cal.getTime());
                }



                List<Integer> deplistfist = new ArrayList<Integer>();
                deplistfist.add(u.getDepartment().getIdNp());

                List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALL(deplistfist, repForm2.getDt_begin().getMonth() + 1, repForm2.getDt_begin().getYear() + 1900);
                System.out.println("wwwwww " + repForm2.getDt_begin().getMonth() + 1);
//------------






                //  List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetails(u.getDepartment().getIdNp());
                tr = true;


                if (depList.size() > 0) {
                    tr = false;



                    //</editor-fold >        

                    Upd();
                }


            }
        }



    }

    public void updatedRep() {
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


            Upd1();

            //  repForm2.setDt_begin(mes);
        }
    }

    public String save() {


        if (repForm2 != null && repForm2.getForm() != null
                && repForm2.getUsr() != null
                && repForm2.getDepartment() != null
                && repForm2.getDt_create() != null
                && repForm2.getDt_begin() != null) {




            new ReportRepository().save(getRepForm2());
            for (ReportDetailsForm2 object : repForm2.getDetailsForm2()) {
                new ReportForm2DetailsRepository().save(((ReportDetailsForm2) object));
            }
        } else {
            return "errorpageadditional";

        }
        return "mainpage";
    }

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
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 10, repForm2.getUsr().getDepartment().getNameNp());

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



        repForm2.getDetailsForm2();
        int j = 4;
        for (int i = 0; i < repForm2.getDetailsForm2().size(); i++) {
            j = i + 4;
            HSSFRow header8 = sheet.createRow(j);
            ReportDetailsForm2 repDetailsForm2 = repForm2.getDetailsForm2().get(i);


            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 0, 0, 0, repDetailsForm2.getDepartmentName());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 1, 1, 1, repDetailsForm2.getFioChief());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 2, 2, 2, repDetailsForm2.getPhoneChief());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 3, 3, 3, repDetailsForm2.getRorsChief());


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




            coll7 = coll7 + repDetailsForm2.getForm2Coll7();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, f.formatForEXCEL(repDetailsForm2.getForm2Coll7()));
            coll8 = coll8 + repDetailsForm2.getForm2Coll8();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, f.formatForEXCEL(repDetailsForm2.getForm2Coll8()));
            coll9 = coll9 + repDetailsForm2.getForm2Coll9();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 8, 8, 8, f.formatForEXCEL(repDetailsForm2.getForm2Coll9()));
            coll10 = coll10 + repDetailsForm2.getForm2Coll10();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 9, 9, 9, f.formatForEXCEL(repDetailsForm2.getForm2Coll10()));
            askye = askye + repDetailsForm2.getAskye();
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 10, 10, 10, f.formatForEXCEL(repDetailsForm2.getAskye()));



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
        new createCell().createCell(wb, sheet, cellStyle_L, headerj1, j, j, 0, 0, 10, "Дата внесения информации: " + dateFormatter2.format(repForm2.getDt_create()));

        j++;
        HSSFRow headerj2 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_L, headerj2, j, j, 0, 0, 10, "Исполнитель: " + repForm2.getUsr().getFIO());

        j++;
        HSSFRow headerj3 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_L, headerj3, j, j, 0, 0, 10, "Телефон: " + repForm2.getUsr().getPhoneNumber());

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
