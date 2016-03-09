package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.DepartmentGroup;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsForm2;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.service.StringImprover;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormFourPageBean_del implements Serializable {

    private String FONT = "Courier New";
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    // private int mes;
    //private int year;
    private float askye;
    private float coll7;
    private float coll8;
    private float coll9;
    private float coll10;
    private String mes_new;
    //private String mes_new;
    private String dayBegin;
    private String dayEnd;
    private Date dayBeginD;
    private Date dayEndD;

    public String getMes_new() {
        return mes_new;
    }

    public void setMes_new(String mes_new) {
        this.mes_new = mes_new;
    }
    private List<ReportDetailsForm2> reportDetailsForm2List = new ArrayList<ReportDetailsForm2>();

    public List<ReportDetailsForm2> getReportDetailsForm2List() {
        return reportDetailsForm2List;
    }

    public void setReportDetailsForm2List(List<ReportDetailsForm2> reportDetailsForm2List) {
        this.reportDetailsForm2List = reportDetailsForm2List;
    }

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
     * Обновление
     */
    public void Upd() {
        coll7 = 0;
        coll8 = 0;
        coll9 = 0;
        coll10 = 0;
        askye = 0;


        List<Integer> deplistfist = new ArrayList<Integer>();
        deplistfist.add((int) session.getUser().getDepartmentPower());
        /*первый элемент idVp=1*/
        /* Выбоп подчененных предприятий по списку  службы*/
        //   List<Department> depListFist = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALL(deplistfist, mes, year);
        List<Department> depListFist = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALLDate(deplistfist, dayBeginD, dayEndD);


        List<Integer> deplist = new ArrayList<Integer>();
        for (Department department : depListFist) {
            deplist.add(department.getIdNp());
        }
        if (deplist.size() > 0) {
            /* Выбоп подчененных предприятий по списку структурные подразделения*/
            // List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALL(deplist, mes, year);
            List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALLDate(deplist, dayBeginD, dayEndD);

            System.out.println("depList " + depList);




            String f = "(";
            for (Department department : depListFist) {
                f = f + department.getId() + ",";
                System.out.println("ffff==" + department.getDtBeginNSI());
            }
            f = f + "-1)";



            //new

            ReportRepository repRep = new ReportRepository();
            List<Report> rep_all = repRep.getListWithDetailsIdNpMonthALLMyDate(f, dayBeginD, dayEndD);
            System.out.println("rep_all " + rep_all);
            List<Report> repForm2 = repRep.getListWithDetailsForm2IdNpMonthALLMyDate(f, dayBeginD, dayEndD);
            System.out.println("repForm2 " + repForm2);



            reportDetailsForm2List = new ArrayList<ReportDetailsForm2>();

            ReportDetailsForm2 reportDetailsForm2ALL = new ReportDetailsForm2();
            reportDetailsForm2ALL.setDepartmentName("ИТОГО");

            /*список ДИ*/
            HashMap reportListDetailsForm = new HashMap();
            List<DepartmentGroup> depGroup = (new DepartmentRepository()).getActiveListDepartmentGroup();
            for (DepartmentGroup departmentGroup : depGroup) {
                ReportDetailsForm2 reportDetailsForm2 = new ReportDetailsForm2();
                reportDetailsForm2.setDepartmentName(departmentGroup.getNameNp());
                reportListDetailsForm.put(departmentGroup.getId(), reportDetailsForm2);

            }


            System.out.println("=============" + reportListDetailsForm.size());



            //depList.size();-всего предприятий в службе
            long group = 0;
           
            
            boolean f1=true;

            for (Department department : depListFist) {
                int proc_fact = 0;
                int proc_plan = 0;
                ReportDetailsForm2 reportDetailsForm2 = new ReportDetailsForm2();
                reportDetailsForm2.setDepartmentName(department.getNameNp());




                int fdep = 0;

                for (Department dep : depList) {

                    if (department.getIdNp() == dep.getIdVp()) {
                        fdep++;


                        /*для ссылки на 1 форму id report*/
                        for (Report repR : repForm2) {

                            System.out.println("repR 1" + repR);
                            if (repR.getDepartment().getId() == department.getId()) {

                                System.out.println("repR 2" + repR);
                                reportDetailsForm2.setRepId(repR.getId());
                            }
                        }




                        for (Report repR : rep_all) {

                            if (repR.getDepartment().getId() == dep.getId()) {

                                if (repR.getDt_inputPlan() != null) {
                                    proc_plan = proc_plan + 1;
                                }
                                if (repR.getDt_inputFact() != null) {
                                    proc_fact = proc_fact + 1;
                                }


                                for (ReportDetails repD : repR.getDetails()) {


                                    if ((repD.getResource().getId() == 17)
                                            || (repD.getResource().getId() == 3)
                                            || (repD.getResource().getId() == 4)
                                            || (repD.getResource().getId() == 5)
                                            || (repD.getResource().getId() == 2)
                                            || (repD.getResource().getId() == 1)
                                            || (repD.getResource().getId() == 7)) {


                                        reportDetailsForm2.setForm2Coll7(repD.getPlan_tut() + reportDetailsForm2.getForm2Coll7());
                                        if (repD.getDt_inputFact() != null) {
                                            reportDetailsForm2.setForm2Coll8(repD.getFact_tut() + reportDetailsForm2.getForm2Coll8());
                                        }
                                        coll7 = coll7 + repD.getPlan_tut();
                                        if (repD.getDt_inputFact() != null) {
                                            coll8 = coll8 + repD.getFact_tut();
                                        }

                                        /*ДИ*/
                                        if (department.getDepartmentGroup_id() != null) {
                                            ReportDetailsForm2 rf = (ReportDetailsForm2) reportListDetailsForm.get(department.getDepartmentGroup_id().getId());

                                            System.out.println("--------------" + rf.getDepartmentName());
                                            System.out.println("--------------" + dep.getNameNp());
                                            rf.setForm2Coll7(repD.getPlan_tut() + rf.getForm2Coll7());
                                            if (repD.getDt_inputFact() != null) {
                                                rf.setForm2Coll8(repD.getFact_tut() + rf.getForm2Coll8());
                                            }
                                            reportListDetailsForm.put(department.getDepartmentGroup_id().getId(), rf);
                                        }


                                    }
                                    if ((repD.getResource().getId() == 13)) {
                                        reportDetailsForm2.setForm2Coll9(repD.getPlan_col() + reportDetailsForm2.getForm2Coll9());
                                        coll9 = coll9 + repD.getPlan_col();
                                        if (repD.getDt_inputFact() != null) {
                                            reportDetailsForm2.setForm2Coll10(repD.getFact_col() + reportDetailsForm2.getForm2Coll10());
                                            if (repD.isAskue()) {
                                                reportDetailsForm2.setAskye(repD.getEconomy() + reportDetailsForm2.getAskye());
                                                askye = repD.getEconomy() + askye;
                                            }
                                            coll10 = coll10 + repD.getFact_col();
                                        }

                                        /*ДИ*/
                                        /*   if(department.getDepartmentGroup_id()!=null){
                                         ReportDetailsForm2 rf=(ReportDetailsForm2)reportListDetailsForm.get(department.getDepartmentGroup_id().getId());
                            
                                         System.out.println("--------------"+rf.getDepartmentName());
                                         System.out.println("--------------"+dep.getNameNp());
                                         rf.setForm2Coll9(repD.getPlan_col() + rf.getForm2Coll9());
                                         if (repD.getDt_inputFact() != null) {
                                         rf.setForm2Coll10(repD.getFact_col() + rf.getForm2Coll10());
                                         if(repD.isAskue()){
                                         rf.setAskye(repD.getEconomy()+rf.getAskye());
                                         }
                                         }
                                         reportListDetailsForm.put(department.getDepartmentGroup_id().getId(), rf);     
                                         }*/




                                    }


                                }
                            }
                        }
                    }
                }

                if (department.getDepartmentGroup_id() != null) {
                    if (group == 0) {
                        group = department.getDepartmentGroup_id().getId();
                    }
                }
                

                if (department.getDepartmentGroup_id() != null) {
                    if (group != department.getDepartmentGroup_id().getId()) {
                       
                            ReportDetailsForm2 o = (ReportDetailsForm2) reportListDetailsForm.get(group);
                            reportDetailsForm2List.add(o);
                        

                        group = department.getDepartmentGroup_id().getId();

                       // reportListDetailsForm.clear();
                    }
                }
                if (department.getDepartmentGroup_id() == null && f1) {
                        f1=!f1; 
                            ReportDetailsForm2 o = (ReportDetailsForm2) reportListDetailsForm.get(group);
                            reportDetailsForm2List.add(o);
                    
                }


                if (department.getDepartmentGroup_id() != null) {

                    ReportDetailsForm2 rf = (ReportDetailsForm2) reportListDetailsForm.get(department.getDepartmentGroup_id().getId());

                    rf.setForm2Coll9(rf.getForm2Coll9() + reportDetailsForm2.getForm2Coll9());
                    rf.setForm2Coll10(rf.getForm2Coll10() + reportDetailsForm2.getForm2Coll10());
                    rf.setAskye(rf.getAskye() + reportDetailsForm2.getAskye());
                    reportListDetailsForm.put(department.getDepartmentGroup_id().getId(), rf);
                }
                /*ДИ end*/


                if (fdep > 0) {
                    int del = dayEndD.getMonth() - dayBeginD.getMonth() + 1;

                    if (del > 0) {
                        reportDetailsForm2.setFioChief(String.valueOf(100 * proc_plan / (del * fdep)));
                        reportDetailsForm2.setPhoneChief(String.valueOf(100 * proc_fact / (del * fdep)));
                    }
                }

                reportDetailsForm2List.add(reportDetailsForm2);
            }

            reportDetailsForm2ALL.setAskye(askye);
            reportDetailsForm2ALL.setForm2Coll7(coll7);
            reportDetailsForm2ALL.setForm2Coll8(coll8);
            reportDetailsForm2ALL.setForm2Coll9(coll9);
            reportDetailsForm2ALL.setForm2Coll10(coll10);

            /*добавление ди*/
       /*     Set keys = reportListDetailsForm.keySet();
            Iterator keysIterator = keys.iterator();
            while (keysIterator.hasNext()) {
                Object key = keysIterator.next();
                ReportDetailsForm2 o = (ReportDetailsForm2) reportListDetailsForm.get(key);
                reportDetailsForm2List.add(o);
            }*/


            reportDetailsForm2List.add(reportDetailsForm2ALL);





        }

    }

    @PostConstruct
    public void Init() {
        System.out.println("=====333 ");
        //dep=(new DepartmentRepository()).get(session.getUser().getDepartmentID());
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("day_begin")) {
            String dtPar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_begin");
            mes_new = dtPar;
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
            mes_new = dateFormatter2.format(cdt.getTime());
            dayBeginD = cdt.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayBegin = dateFormatter.format(dayBeginD);
            System.out.println("=====222dayBegin " + dayBegin);

        }

        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("day_end")) {

            // mes_new =mes_new+" - "+FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_end");

            String dtPar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_end");
            mes_new = mes_new + " - " + dtPar;

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
            DT_NOW.add(Calendar.DATE, -1);
            dayEndD = DT_NOW.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayEnd = dateFormatter.format(dayEndD);
            System.out.println("=====111dayEndD " + dayEnd);
            System.out.println("=====111mes_new" + mes_new);
        } else {
            SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy.");
            Calendar cdt = Calendar.getInstance();
            cdt.setTime(new Date());
            cdt.add(Calendar.MONTH, 1);
            cdt.set(Calendar.DATE, 1);
            cdt.add(Calendar.DATE, -1);
            mes_new = mes_new + " - " + dateFormatter2.format(cdt.getTime());
            dayEndD = cdt.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayEnd = dateFormatter.format(dayEndD);
            System.out.println("=====222dayEndD " + dayEnd);
            System.out.println("=====222mes_new" + mes_new);
        }



        /*if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("mes")) {
         mes_new=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes")+"."+
         FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year");
         
         mes= Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes"));
         year= Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year"));
         }
         else{
         mes_new = String.valueOf(new Date().getMonth()+1)+"."+ String.valueOf(new Date().getYear()+1900);
         mes=new Date().getMonth()+1;
         year= new Date().getYear()+1900;
         }*/
        Upd();

    }

    /**
     * Печать Excel
     *
     * @param document
     */
    public void Print(Object document) {
        StringImprover f = new StringImprover();

        HSSFWorkbook wb = (HSSFWorkbook) document;



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

        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("HHч. mmмин.");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.");
        Row row = sheet.createRow(0);
        new createCell().createCell(wb, sheet, cellStyle_R, row, 0, 0, 0, 0, 10, "Форма 4 за " + mes_new);

        Row row1 = sheet.createRow(1);
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 10, "Результат проведения акции");

        Row row2 = sheet.createRow(2);
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 0, 0, 0, "Наименование дирекции, службы");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 1, 1, 1, "Ввод мероприятий");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 2, 2, 2, "Ввод отчета по экономии");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 3, 3, 3, "План экономии ТУТ");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 4, 4, 4, "Факт экономии ТУТ");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 5, 5, 5, "План экономии эл. энергии кВтч");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 6, 6, 6, "Факт экономии эл. энергии кВтч");
        new createCell().createCell(wb, sheet, cellStyle2, row2, 2, 2, 7, 7, 7, "Факт экономии эл. энергии АСКУЭ");



        HSSFRow row3 = sheet.createRow(3);
        for (int i1 = 0; i1 < 8; i1++) {
            new createCell().createCell(wb, sheet, cellStyle1, row3, 3, 3, i1, i1, i1, String.valueOf(i1 + 1));
        }


        int j = 4;



        for (int i = 0; i < reportDetailsForm2List.size(); i++) {
            j = i + 4;
            HSSFRow header8 = sheet.createRow(j);
            ReportDetailsForm2 repDetailsForm2 = reportDetailsForm2List.get(i);

if(repDetailsForm2!=null){
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 0, 0, 0, repDetailsForm2.getDepartmentName());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 1, 1, 1, repDetailsForm2.getFioChief());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 2, 2, 2, repDetailsForm2.getPhoneChief());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 3, 3, 3, f.formatForEXCEL(repDetailsForm2.getForm2Coll7()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 4, 4, 4, f.formatForEXCEL(repDetailsForm2.getForm2Coll8()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 5, 5, 5, f.formatForEXCEL(repDetailsForm2.getForm2Coll9()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, f.formatForEXCEL(repDetailsForm2.getForm2Coll10()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, f.formatForEXCEL(repDetailsForm2.getAskye()));

}





        }
        /*   j++;
         HSSFRow headerj01 = sheet.createRow(j);
         new createCell().createCell(wb, sheet, cellStyle_R, headerj01, j, j, 0, 0, 3, "ИТОГО: ");

         new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 3, 3, 3, String.valueOf(coll7));
         new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 4, 4, 4, String.valueOf(coll8));
         new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 5, 5, 5, String.valueOf(coll9));
         new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 6, 6, 6, String.valueOf(coll10));
         new createCell().createCell(wb, sheet, cellStyle1, headerj01, j, j, 7, 7, 7, String.valueOf(askye));
         */


        for (int i1 = 0; i1 < 8; i1++) {
            sheet.autoSizeColumn(i1, true);

        }


        wb.removeSheetAt(0);


    }
}
