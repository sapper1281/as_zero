package rzd.vivc.aszero.beans.pagebean;

import java.io.IOException;
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
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.autorisation.CheckRights;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.DepartmentGroup;
import rzd.vivc.aszero.dto.DetailsForm4;
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
public class FormFourPageBeanNew extends BasePage implements Serializable {

    private String FONT = "Courier New";
    private boolean t_mes1;

    public boolean isT_mes1() {
        return t_mes1;
    }

    public void setT_mes1(boolean t_mes1) {
        this.t_mes1 = t_mes1;
    }
    
    
    public FormFourPageBeanNew() {
        super();
    }
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    
    
    
    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
    }
     private List<DetailsForm4> detailsForm4List = new ArrayList<DetailsForm4>();

    public List<DetailsForm4> getDetailsForm4List() {
        return detailsForm4List;
    }

    public void setDetailsForm4List(List<DetailsForm4> detailsForm4List) {
        this.detailsForm4List = detailsForm4List;
    }
    
    
    
    
   
    
  
    private String mes_new;
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
   

   
    

    /**
     * Обновление
     */
   public void Upd() {
    if(session != null && session.getUser() != null){
       
      
        int idDepUser=(int) session.getUser().getDepartmentPower();
        
         t_mes1=true;
         System.out.println(dayBeginD.getMonth());
         System.out.println(dayEndD.getMonth());
        t_mes1=dayBeginD.getMonth()==dayEndD.getMonth();
        
      //  if(dayBeginD.getMonth()!=dayEndD.getMonth()){};
        List<DetailsForm4> detailsForm4ListSQL=new ArrayList<DetailsForm4> ();
        if(t_mes1){
        detailsForm4ListSQL=(new ReportRepository()).getListWithDetailsFormFour((int) session.getUser().getDepartmentPower(), dayBeginD, dayEndD);
        }
         if(!t_mes1){
        detailsForm4ListSQL=(new ReportRepository()).getListWithDetailsFormFourPER((int) session.getUser().getDepartmentPower(), dayBeginD, dayEndD);
        }

        int k=-1;
        
        DetailsForm4 sumDi=new DetailsForm4();  
        DetailsForm4 sumDor=new DetailsForm4();
        sumDor.setNamenp("ИТОГО");
             sumDor.setPlanE(0);
            sumDor.setFactE(0);
            sumDor.setFactASKUE(0);
            sumDor.setPlan_tyt(0);
            sumDor.setFact_tyt(0);
            sumDor.setIdF2(-1);
            sumDor.setFactProc(0);
            sumDor.setPlanProc(0);
        for (DetailsForm4 detailsForm4 : detailsForm4ListSQL) {
            
            if(detailsForm4.getAllPred().intValue() != 0){
                detailsForm4.setFactProc(100*detailsForm4.getFactProc().intValue()/detailsForm4.getAllPred().intValue());
               
            } else {
                detailsForm4.setFactProc(0);
            }
           
            if(detailsForm4.getAllPred().intValue() != 0){
                detailsForm4.setPlanProc(100*detailsForm4.getPlanProc().intValue()/detailsForm4.getAllPred().intValue());
                 
            } else {
                detailsForm4.setPlanProc(0);
                
            }
            
            
            int g=0;
            if(detailsForm4.getIdGroup()!=null){g=detailsForm4.getIdGroup().intValue();}
            
            
            if(g!=k){
                if(k!=-1){
            detailsForm4List.add(sumDi);}
            sumDi=new DetailsForm4();  
            k=g; 
            sumDi.setNamenp(detailsForm4.getNamenpGroup());
             sumDi.setPlanE(0);
            sumDi.setFactE(0);
            sumDi.setFactASKUE(0);
            sumDi.setPlan_tyt(0);
            sumDi.setFact_tyt(0);
            sumDi.setIdF2(-1);
            sumDi.setFactProc(0);
            sumDi.setPlanProc(0);
            } 
            sumDi.setPlanE(sumDi.getPlanE().floatValue()+detailsForm4.getPlanE().floatValue());
            sumDi.setFactE(sumDi.getFactE().floatValue()+detailsForm4.getFactE().floatValue());
            sumDi.setFactASKUE(sumDi.getFactASKUE().floatValue()+detailsForm4.getFactASKUE().floatValue());
            sumDi.setPlan_tyt(sumDi.getPlan_tyt().floatValue()+detailsForm4.getPlan_tyt().floatValue());
            sumDi.setFact_tyt(sumDi.getFact_tyt().floatValue()+detailsForm4.getFact_tyt().floatValue());
            
             sumDor.setPlanE(sumDor.getPlanE().floatValue()+detailsForm4.getPlanE().floatValue());
            sumDor.setFactE(sumDor.getFactE().floatValue()+detailsForm4.getFactE().floatValue());
            sumDor.setFactASKUE(sumDor.getFactASKUE().floatValue()+detailsForm4.getFactASKUE().floatValue());
            sumDor.setPlan_tyt(sumDor.getPlan_tyt().floatValue()+detailsForm4.getPlan_tyt().floatValue());
            sumDor.setFact_tyt(sumDor.getFact_tyt().floatValue()+detailsForm4.getFact_tyt().floatValue());
              
            
            
            
            
            detailsForm4List.add(detailsForm4);
        }
detailsForm4List.add(sumDor);
            





         
 }else{
        
        session.setError("Ошибка 4_01. Перезагрузите браузер и авторизуйтесь ");
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

           
            String dtPar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_end");
            mes_new = mes_new + " - " + dtPar;

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
            DT_NOW.set(Calendar.MILLISECOND, -1);
           // DT_NOW.add(Calendar.DATE, -1);
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
            cdt.set(Calendar.MILLISECOND, -1);
           // cdt.add(Calendar.DATE, -1);
            mes_new = mes_new + " - " + dateFormatter2.format(cdt.getTime());
            dayEndD = cdt.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayEnd = dateFormatter.format(dayEndD);
            System.out.println("=====222dayEndD " + dayEnd);
            System.out.println("=====222mes_new" + mes_new);
        }


        Upd();

    }}

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



        for (int i = 0; i < detailsForm4List.size(); i++) {
            j = i + 4;
            HSSFRow header8 = sheet.createRow(j);
            DetailsForm4 repDetailsForm2 = detailsForm4List.get(i);

if(repDetailsForm2!=null){
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 0, 0, 0, repDetailsForm2.getNamenp());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 1, 1, 1, repDetailsForm2.getPlanProc().toString());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 2, 2, 2, repDetailsForm2.getFactProc().toString());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 3, 3, 3, f.formatForEXCEL(repDetailsForm2.getPlan_tyt().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 4, 4, 4, f.formatForEXCEL(repDetailsForm2.getFact_tyt().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 5, 5, 5, f.formatForEXCEL(repDetailsForm2.getPlanE().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, f.formatForEXCEL(repDetailsForm2.getFactE().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, f.formatForEXCEL(repDetailsForm2.getFactASKUE().floatValue()));

}





        }
       


        for (int i1 = 0; i1 < 8; i1++) {
            sheet.autoSizeColumn(i1, true);

        }


        wb.removeSheetAt(0);


    }
}
