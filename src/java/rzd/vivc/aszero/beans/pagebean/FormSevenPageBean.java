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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import rzd.vivc.aszero.autorisation.CheckRights;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.FormSevenNew;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.repository.ResourceRepository;
import rzd.vivc.aszero.service.StringImprover;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormSevenPageBean implements Serializable {

    private String FONT = "Courier New";
    private String dayBegin;
    private String dayEnd;
    private Date dayBeginD;
    private Date dayEndD;
   private List<FormSevenNew> formSevenList;
    private String month;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;

   public List<FormSevenNew> getFormSevenList() {
        return formSevenList;
    }

    public String getMonth() {
        return month;
    }

    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    public void updateTable() {

        if(session != null && session.getUser() != null){
        List<FormSevenNew> reps = (new ReportRepository()).getListWithDetailsForm7((int) session.getUser().getDepartmentPower(), dayBeginD, dayEndD);

        formSevenList = new ArrayList<FormSevenNew>();


        List<FormSevenNew> formSevens = new ArrayList<FormSevenNew>();
        List<FormSevenNew> formSevenProm = new ArrayList<FormSevenNew>();
        List<FormSevenNew> formSevenPromVal = new ArrayList<FormSevenNew>();
        List<FormSevenNew> formSevensEditDer = new ArrayList<FormSevenNew>();



        FormSevenNew formSevenDep = new FormSevenNew();
        FormSevenNew formSevenDer = new FormSevenNew();
        /*сумма значения показателей для сп*/
        FormSevenNew formSevenDor = new FormSevenNew();

        /*депо*/
        String s = "-";
       // String sM = "-";
        long sL = -1;
        /*ирекция*/
        String sder = "-";
       // String sderM = "-";
        long sderL = -1;
        /*ресурс*/
        String sres = "-";
        String sresM = "-";
        long sresL = -1;

        /*по ресурсу*/
        /* for (Resource elemResource : listResource) {
         FormSevenNew formSevenDor = new FormSevenNew();
         formSevenDor.setResName("ИТОГО БЛОК " + elemResource.getName());

         */



        //<editor-fold defaultstate="collapsed" desc="rep">          
        for (FormSevenNew rep1 : reps) {
            //   if (rep1.getIdRes() == elemResource.getId()/*&&rep1.getDepPerIdDep()==depDer.getId()*/) {



























if (s.equals("-")) {
                s = rep1.getDepNameNP();
                sL = rep1.getIdDep();
                sresM=rep1.getMas();
                System.out.println("СП " + s);
            }


            /*проверка меняется ли СП*/
           // if (rep1.getIdDep() != sL) {
            if ((rep1.getIdDep() != sL)/*|| (rep1.getDepPerIdDep() != sderL) || (rep1.getIdDep() != sL)*/) {
            
                if (formSevenDep.getPlan_col() != 0 || formSevenDep.getFact_col() != 0) {
                    FormSevenNew formSevenDep1 = new FormSevenNew();
                    formSevenDep1.setDepPerNameNP("ИТОГО " +s);
                    formSevenProm.add(formSevenDep1);

                    for (FormSevenNew formSevenNew : formSevenPromVal) {
                        formSevenProm.add(formSevenNew);
                    }

                    formSevenPromVal.clear();

                    formSevenDep1 = new FormSevenNew();
                    formSevenDep1.setDepPerNameNP("ИТОГО " + s);
                    System.out.println("СП " + s);
                    formSevenDep1.setDepNameNP("Экономия " + sres+" "+sresM);
                    formSevenDep1.setPlan_col(formSevenDep.getPlan_col());
                    formSevenDep1.setFact_col(formSevenDep.getFact_col());
                    formSevenDep1.setFact_plan_col_out(formSevenDep.getFact_plan_col_out());
                    formSevenDep.setPlan_col(0);
                    formSevenDep.setFact_col(0);
                    formSevenDep.setFact_plan_col_out(0);
                    formSevenProm.add(formSevenDep1);

                }
            }







            /*проверка меняется ли подразделение*/
   if (sder.equals("-")) {
                sder = rep1.getDepPerNameNP();
                sderL = rep1.getDepPerIdDep();
                sresM=rep1.getMas();
            }

           
            //if (!rep1.getDepPerNameNP().equals(sder)) {
            if ((rep1.getDepPerIdDep() != sderL) /*|| (rep1.getIdDep() != sL)*/) {
              
                if (formSevenDer.getPlan_col() != 0 || formSevenDer.getFact_col() != 0) {
                    FormSevenNew formSevenDer1 = new FormSevenNew();

                    formSevenDer1.setCol0("ИТОГО " +sder);
                    formSevensEditDer.add(formSevenDer1);
                    for (FormSevenNew formSevenNew : formSevenProm) {
                        formSevensEditDer.add(formSevenNew);
                    }
                    formSevenProm.clear();
                    System.out.println("подразделение " + sder);
                    formSevenDer1 = new FormSevenNew();
                    formSevenDer1.setCol0("ИТОГО " + sder);
                    formSevenDer1.setDepNameNP("Экономия " + sres+" "+sresM);/***************/
                    formSevenDer1.setPlan_col(formSevenDer.getPlan_col());
                    formSevenDer1.setFact_col(formSevenDer.getFact_col());
                    formSevenDer1.setFact_plan_col_out(formSevenDer.getFact_plan_col_out());
                    formSevenDer.setPlan_col(0);
                    formSevenDer.setFact_col(0);
                    formSevenDer.setFact_plan_col_out(0);
                    formSevensEditDer.add(formSevenDer1);


                }
            }







            
            
                if (sres.equals("-")) {
                sres = rep1.getResName();
                sresL = rep1.getIdRes();
                 sresM=rep1.getMas();
            }
                
                if ((rep1.getIdRes() != sresL) ) {
                //if (!rep1.getResName().equals(sres)) {
                if (formSevenDor.getPlan_col() != 0 || formSevenDor.getFact_col() != 0) {
                    FormSevenNew formSevenDor1 = new FormSevenNew();

                    System.out.println("вид рес " + sres);

                    //System.out.println(rep1.getResName()+"++++++++" + sres);
                    //System.out.println(rep1.getDepPerNameNP()+"++++++++" + sder);
                    formSevenDor1.setResName("БЛОК по " + sres+" "+sresM);/**************/
                    formSevens.add(formSevenDor1);
                    for (FormSevenNew formSevenNew : formSevensEditDer) {
                        formSevens.add(formSevenNew);
                    }
                    formSevensEditDer.clear();
                    System.out.println(sres);
                    formSevenDor1 = new FormSevenNew();
                    formSevenDor1.setResName("ИТОГО БЛОК " + sres);
                    formSevenDor1.setPlan_col(formSevenDor.getPlan_col());
                    formSevenDor1.setFact_col(formSevenDor.getFact_col());
                    formSevenDor1.setFact_plan_col_out(formSevenDor.getFact_plan_col_out());
                    formSevenDor.setPlan_col(0);
                    formSevenDor.setFact_col(0);
                    formSevenDor.setFact_plan_col_out(0);
                    formSevens.add(formSevenDor1);


                }
            }


            if (rep1.getIdDep() != sL) {
                s = rep1.getDepNameNP();
                sL = rep1.getIdDep();
                sresM = rep1.getMas();
            }


            if (rep1.getDepPerIdDep() != sderL) {
                sder = rep1.getDepPerNameNP();
                sderL = rep1.getDepPerIdDep();
                sresM=rep1.getMas();

            }

            if (rep1.getIdRes() != sresL) {
                sres = rep1.getResName();
                sresL = rep1.getIdRes();
                sresM=rep1.getMas();
            }






            FormSevenNew formSevenVal = new FormSevenNew();

            //заполнение показателей
            //<editor-fold defaultstate="collapsed" desc="заполнение показателей">      
            if ((rep1.getPlan_col() != 0 ) || (rep1.getFact_col() != 0&&rep1.getDt_inputFact() != null)) {
                System.out.println("1:"+rep1.getPlan_col() );
                System.out.println("2:"+rep1.getFact_col() );
                System.out.println("3:"+rep1.getDt_inputFact() != null );
                formSevenVal.setPlan_col(rep1.getPlan_col());
                /*if (rep1.getDt_inputFact() != null) {*/
                    formSevenVal.setFact_col(rep1.getFact_col());
                    formSevenVal.setFact_plan_col_out(rep1.getFact_col() - rep1.getPlan_col());
                //}
                //заполнение показателей
                System.out.println("Экономия " + sres + sder);
                formSevenVal.setDepNameNP("Экономия " + sres+" "+sresM);
                formSevenVal.setActivity(rep1.getActivity());
                formSevenVal.setAddres(rep1.getAddres());
                formSevenVal.setResponsible(rep1.getResponsible());
                formSevenVal.setPowerSource(rep1.getPowerSource());
                formSevenVal.setAddressOfObject(rep1.getAddressOfObject());
                formSevenVal.setType(rep1.getType());
                formSevenVal.setNum(rep1.getNum());
                formSevenVal.setAskueYesOrNot(rep1.isAskue());
                formSevenPromVal.add(formSevenVal);
                //</editor-fold>


                //по ресурсу    
                //<editor-fold defaultstate="collapsed" desc="заполнение ресурсов">
                formSevenDor.setPlan_col(formSevenDor.getPlan_col() + rep1.getPlan_col());
             //   if (rep1.getDt_inputFact() != null) {
                    formSevenDor.setFact_col(formSevenDor.getFact_col() + rep1.getFact_col());
                    if (rep1.getFact_col() - rep1.getPlan_col() > 0) {
                        formSevenDor.setFact_plan_col_out(formSevenDor.getFact_plan_col_out() + rep1.getFact_col() - rep1.getPlan_col());
                    }
             //   }


                //по службе
                formSevenDer.setPlan_col(formSevenDer.getPlan_col() + rep1.getPlan_col());
              //  if (rep1.getDt_inputFact() != null) {
                    formSevenDer.setFact_col(formSevenDer.getFact_col() + rep1.getFact_col());
                    if (rep1.getFact_col() - rep1.getPlan_col() > 0) {
                        formSevenDer.setFact_plan_col_out(formSevenDer.getFact_plan_col_out() + rep1.getFact_col() - rep1.getPlan_col());
                    }
             //   }
                //по сп
                formSevenDep.setPlan_col(formSevenDep.getPlan_col() + rep1.getPlan_col());
            //    if (rep1.getDt_inputFact() != null) {
                    formSevenDep.setFact_col(formSevenDep.getFact_col() + rep1.getFact_col());
                    if (rep1.getFact_col() - rep1.getPlan_col() > 0) {
                        formSevenDep.setFact_plan_col_out(formSevenDep.getFact_plan_col_out() + rep1.getFact_col() - rep1.getPlan_col());
             //       }
                }//</editor-fold>  
            }




            //   }
        }  //</editor-fold>




        if (formSevenDep.getPlan_col() != 0 || formSevenDep.getFact_col() != 0) {
            FormSevenNew formSevenDep1 = new FormSevenNew();
            formSevenDep1.setDepPerNameNP(s);
            formSevenProm.add(formSevenDep1);

            for (FormSevenNew formSevenNew : formSevenPromVal) {
                formSevenProm.add(formSevenNew);
            }


            formSevenPromVal.clear();


            formSevenDep1 = new FormSevenNew();
            formSevenDep1.setDepPerNameNP("ИТОГО " + s);
            formSevenDep1.setDepNameNP("Экономия " + sres+" "+sresM);
            formSevenDep1.setPlan_col(formSevenDep.getPlan_col());
            formSevenDep1.setFact_col(formSevenDep.getFact_col());
            formSevenDep1.setFact_plan_col_out(formSevenDep.getFact_plan_col_out());
            formSevenDep.setPlan_col(0);
            formSevenDep.setFact_col(0);
            formSevenDep.setFact_plan_col_out(0);
            formSevenProm.add(formSevenDep1);

        }











        if (formSevenDer.getPlan_col() != 0 || formSevenDer.getFact_col() != 0) {
            FormSevenNew formSevenDer1 = new FormSevenNew();

            formSevenDer1.setCol0(sder);
            formSevensEditDer.add(formSevenDer1);
            for (FormSevenNew formSevenNew : formSevenProm) {
                formSevensEditDer.add(formSevenNew);
            }
            formSevenProm.clear();

            formSevenDer1 = new FormSevenNew();
            formSevenDer1.setCol0("ИТОГО " + sder);
            formSevenDer1.setDepNameNP("Экономия " + sres+" "+sresM);
            formSevenDer1.setPlan_col(formSevenDer.getPlan_col());
            formSevenDer1.setFact_col(formSevenDer.getFact_col());
            formSevenDer1.setFact_plan_col_out(formSevenDer.getFact_plan_col_out());
            formSevenDer.setPlan_col(0);
            formSevenDer.setFact_col(0);
            formSevenDer.setFact_plan_col_out(0);
            formSevensEditDer.add(formSevenDer1);

        }








        if (formSevenDor.getPlan_col() != 0 || formSevenDor.getFact_col() != 0) {
            FormSevenNew formSevenDor1 = new FormSevenNew();
            formSevenDor1.setResName("БЛОК по " + sres+" "+sresM);
            formSevens.add(formSevenDor1);
            for (FormSevenNew formSevenNew : formSevensEditDer) {
                formSevens.add(formSevenNew);
            }
            formSevensEditDer.clear();

            formSevenDor1 = new FormSevenNew();
            formSevenDor1.setResName("ИТОГО БЛОК " + sres+" "+sresM);
            formSevenDor1.setPlan_col(formSevenDor.getPlan_col());
            formSevenDor1.setFact_col(formSevenDor.getFact_col());
            formSevenDor1.setFact_plan_col_out(formSevenDor.getFact_plan_col_out());
            formSevenDor.setPlan_col(0);
            formSevenDor.setFact_col(0);
            formSevenDor.setFact_plan_col_out(0);
            formSevens.add(formSevenDor1);

        }








        formSevenList = formSevens;

        formSevensEditDer.clear();
        formSevenProm.clear();


    }else{
        
        session.setError("Ошибка 7_01. Перезагрузите браузер и авторизуйтесь ");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("errorpage");
                } catch (IOException ex) {
                    Logger.getLogger(FormSevenPageBean.class.getName()).log(Level.SEVERE, null, ex);
                }

        
        
        }
    
   }

    @PostConstruct
    public void Init() {
 if((new CheckRights()).isAutorised()){
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("day_begin")) {
            String dtPar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_begin");
            month = dtPar;
            String dday_p = dtPar.substring(0, 2);
            int dday1_p = Integer.parseInt(dday_p);
            String mmonth_p = dtPar.substring(3, 5);
            int mmonth1_p = Integer.parseInt(mmonth_p) - 1;
            String yyear_p = dtPar.substring(6, 10);
            int yyear1_p = Integer.parseInt(yyear_p);
            Calendar DT_NOW = Calendar.getInstance();
            DT_NOW.set(yyear1_p, mmonth1_p, dday1_p, 0, 0, 0);
            DT_NOW.set(Calendar.DATE, 1);
            dayBeginD = DT_NOW.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayBegin = dateFormatter.format(dayBeginD);
            System.out.println("=====111dayBegin " + dayBegin);

        } else {
            SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy.");
            Calendar cdt = Calendar.getInstance();
            cdt.setTime(new Date());
            cdt.set(Calendar.DATE, 1);
            month = dateFormatter2.format(cdt.getTime());
            dayBeginD = cdt.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayBegin = dateFormatter.format(dayBeginD);
            System.out.println("=====222dayBegin " + dayBegin);

        }

        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("day_end")) {

            // mes_new =mes_new+" - "+FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_end");

            String dtPar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_end");
            month = month + " - " + dtPar;

            //mes_new=mes_new+" - "+dayEnd;
            String dday_p = dtPar.substring(0, 2);
            int dday1_p = Integer.parseInt(dday_p);
            String mmonth_p = dtPar.substring(3, 5);
            int mmonth1_p = Integer.parseInt(mmonth_p) - 1;
            String yyear_p = dtPar.substring(6, 10);
            int yyear1_p = Integer.parseInt(yyear_p);
            Calendar DT_NOW = Calendar.getInstance();
            DT_NOW.set(yyear1_p, mmonth1_p, dday1_p, 0, 0, 0);
            DT_NOW.add(Calendar.MONTH, 1);
            DT_NOW.set(Calendar.DATE, 1);
            //DT_NOW.add(Calendar.DATE, -1);
            dayEndD = DT_NOW.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayEnd = dateFormatter.format(dayEndD);
            System.out.println("=====111dayEndD " + dayEnd);
            System.out.println("=====111mes_new" + month);
        } else {
            SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy.");
            Calendar cdt = Calendar.getInstance();
            cdt.setTime(new Date());
            cdt.add(Calendar.MONTH, 1);
            cdt.set(Calendar.DATE, 1);
           // cdt.add(Calendar.DATE, -1);
            month = month + " - " + dateFormatter2.format(cdt.getTime());
            dayEndD = cdt.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayEnd = dateFormatter.format(dayEndD);
            System.out.println("=====222dayEndD " + dayEnd);
            System.out.println("=====222mes_new" + month);
        }


        /* if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("mes")) {
         month = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes") + "."
         + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year");

         mes = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes"));
         year = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year"));
         } else {
         month = String.valueOf(new Date().getMonth() + 1) + "." + String.valueOf(new Date().getYear() + 1900);
         mes = new Date().getMonth() + 1;
         year = new Date().getYear() + 1900;
         }*/
        updateTable();

        /*   formSevenList = new ArrayList<FormSeven>();
         formSevenList  = (new SevenRepository()).getActiveListFormSeven(monthAction.getMonth(),monthAction.getYear()+ 1900);
         System.out.println(formSevenList);
         System.out.println("=======");
         for (FormSeven formSeven : formSevenList) {
         System.out.println(formSeven); 
         }*/
        //Collection<A> aCol = Query.create("SELECT a FROM A a");

    }}

    /**
     * Печать Excel
     *
     * @param document
     */
    public void Print(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;

        StringImprover f = new StringImprover();

        HSSFSheet sheet = wb.createSheet(month);

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
        cellStyle1_r.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
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
        cellStyle1_r.setFillForegroundColor(IndexedColors.GREEN.getIndex());
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

        Row row = sheet.createRow(0);
        new createCell().createCell(wb, sheet, cellStyle_R, row, 0, 0, 0, 0, 15, "Форма 7 за " + month);


        Row row1 = sheet.createRow(1);
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 15, "Результат проведения акции");

        Row row2 = sheet.createRow(2);
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 0, 0, 0, "№ п/п");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 1, 1, 1, "БЛОК ТЭР");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 2, 2, 2, "Дирекция");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 3, 3, 3, "Структурное подразделение");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 4, 4, 4, "Основные мероприятия");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 5, 5, 5, "Мероприятие, наименование объекта,количество отключаемой техники");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 6, 6, 6, "Местонахождение объекта (станция, населенный пункт)");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 7, 7, 7, "Ответственный за проведение мероприятия");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 8, 8, 8, "Точка питания (ТП, КТП)");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 9, 9, 9, "Место установки прибора учета");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 10, 10, 10, "Тип прибора учета электроэнергии по которому осуществдяется расчёт за электроэнергию");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 11, 11, 11, "Номер прибора учета электроэнергии по которому осуществдяется расчёт за электроэнергию");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 12, 12, 12, "Принадлежность прибора учета к системе АСКУЭ ОАО \"РЖД\"(да/нет)");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 13, 13, 13, "Планируется сокращение потребления электроэнергии, кВт");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 14, 14, 14, "Фактическое сокращение потребления электроэнергии, кВт");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 15, 15, 15, "+/- к плану");

        HSSFRow row3 = sheet.createRow(3);
        for (int i1 = 0; i1 < 16; i1++) {
            new createCell().createCell(wb, sheet, cellStyle1, row3, 3, 3, i1, i1, i1, String.valueOf(i1 + 1));
        }

        int j = 4;
        for (int i = 0; i < formSevenList.size(); i++) {
            j = i + 4;
            HSSFRow header8 = sheet.createRow(j);
            FormSevenNew formSeven = formSevenList.get(i);


            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 0, 0, 0, String.valueOf(i + 1));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 1, 1, 1, formSeven.getResName());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 2, 2, 2, formSeven.getCol0());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 3, 3, 3, formSeven.getDepPerNameNP());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 4, 4, 4, formSeven.getDepPerNameNP());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 5, 5, 5, formSeven.getActivity());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, formSeven.getAddres());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, formSeven.getResponsible());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 8, 8, 8, formSeven.getPowerSource());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 9, 9, 9, formSeven.getAddressOfObject());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 10, 10, 10, formSeven.getType());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 11, 11, 11, String.valueOf(formSeven.getNum()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 12, 12, 12, formSeven.getAskueYesOrNot());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 13, 13, 13, f.formatForEXCEL(formSeven.getPlan_col()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 14, 14, 14, f.formatForEXCEL(formSeven.getFact_col()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 15, 15, 15, formSeven.getFact_plan_col());






        }
        for (int i1 = 0; i1 < 16; i1++) {
            sheet.autoSizeColumn(i1, true);

        }


        wb.removeSheetAt(0);



    }
}
