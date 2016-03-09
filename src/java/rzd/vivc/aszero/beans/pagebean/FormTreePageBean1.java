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
import org.apache.poi.ss.usermodel.Row;
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.classes.excel.createCellNull;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsForm3;
import rzd.vivc.aszero.dto.TypeTER;
import rzd.vivc.aszero.dto.TypeTERForm3;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.repository.TypeTERRepository;
import rzd.vivc.aszero.service.StringImprover;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormTreePageBean1 extends BasePage implements Serializable{
 public FormTreePageBean1() {
        super();
    }
    
    private String FONT = "Courier New";
    
      private String mes_new;
      private String dayBegin;
      private String dayEnd;
      private Date dayBeginD;
      private Date dayEndD;
      
      
      
   /* private int mes;
    private int year;
*/
    public String getMes_new() {
        return mes_new;
    }
    public void setMes_new(String mes_new) {
        this.mes_new = mes_new;
    }
   

    /**
     * Creates a new instance of FormTreePageBean1
     */
    
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private List<ReportDetailsForm3> reportDetailsForm3List;
    private ReportDetailsForm3 reportDetailsForm3;
    private Department dep;

    public List<ReportDetailsForm3> getReportDetailsForm3List() {
        return reportDetailsForm3List;
    }

    public void setReportDetailsForm3List(List<ReportDetailsForm3> reportDetailsForm3List) {
        this.reportDetailsForm3List = reportDetailsForm3List;
    }

    public Department getDep() {
        return dep;
    }

    public void setDep(Department dep) {
        this.dep = dep;
    }

    

    public AutorizationBean getSession() {
        return session;
    }
    
    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    public ReportDetailsForm3 getReportDetailsForm3() {
        return reportDetailsForm3;
    }

    public void setReportDetailsForm3(ReportDetailsForm3 reportDetailsForm3) {
        this.reportDetailsForm3 = reportDetailsForm3;
    }

    public void Upd() {

        reportDetailsForm3 = new ReportDetailsForm3();
        reportDetailsForm3List = new ArrayList<ReportDetailsForm3>();

        List<TypeTER> typeTERForm3 = ((new TypeTERRepository()).getAll(TypeTER.class));
        List<TypeTERForm3> typeTER = new ArrayList<TypeTERForm3>();



        for (TypeTER objectrepTERForm3 : typeTERForm3) {
            TypeTERForm3 t3 = new TypeTERForm3();
            t3.setId(objectrepTERForm3.getId());
            t3.setName(objectrepTERForm3.getName());
            typeTER.add(t3);
        }





        List<Integer> deplistfist=new ArrayList<Integer>();
        deplistfist.add(dep.getIdNp());

        List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALLDate(deplistfist, dayBeginD, dayEndD);
        System.out.println("======"+depList.size()+"==="+depList);
        

//new
      List<Report> rep_all = (new ReportRepository()).getListWithDetailsIdNpMonthALLWithCostsDate(dep.getIdNp(), dayBeginD, dayEndD, session.getUser().getDepartmentPower());
  
        
        
        
        
        
        for (Department department_object : depList) {

            //List<Report> rep = (new ReportRepository()).getListWithDetailsIdNpMonth(department_object.getIdNp(), mes, year);
//System.out.println("----"+rep);

            if (rep_all.size() > 0) {

                for (Report objectrep : rep_all) {
                    if(objectrep.getDepartment().getId()==department_object.getId()){
                    for (ReportDetails objectrepDetails : objectrep.getDetails()) {
                        for (TypeTERForm3 objectrepTER : typeTER) {


                                
                                
                                
                                
                                
                                
                              if(((TypeTERForm3)objectrepTER).getId()==1/*газ*/){
if ((objectrepDetails.getResource().getId() == 1)) {
   ((TypeTERForm3)objectrepTER).setSumm_plan( objectrepDetails.getPlan_col()+ ((TypeTERForm3)objectrepTER).getSumm_plan());
   if(objectrepDetails.getDt_inputFact()!=null){
   ((TypeTERForm3)objectrepTER).setSumm_fact( objectrepDetails.getFact_col()+ ((TypeTERForm3)objectrepTER).getSumm_fact());
   ((TypeTERForm3)objectrepTER).setSumm_ek( objectrepDetails.getFact_money()+ ((TypeTERForm3)objectrepTER).getSumm_ek());
}}}
                              
if(((TypeTERForm3)objectrepTER).getId()==2/*диз.топ*/){
if ((((ReportDetails) objectrepDetails).getResource().getId() == 17)) {
   ((TypeTERForm3)objectrepTER).setSumm_plan( objectrepDetails.getPlan_col()+ ((TypeTERForm3)objectrepTER).getSumm_plan());
   if(objectrepDetails.getDt_inputFact()!=null){
   ((TypeTERForm3)objectrepTER).setSumm_fact(objectrepDetails.getFact_col()+ ((TypeTERForm3)objectrepTER).getSumm_fact());
   ((TypeTERForm3)objectrepTER).setSumm_ek( objectrepDetails.getFact_money()+ ((TypeTERForm3)objectrepTER).getSumm_ek());
}}}

if(((TypeTERForm3)objectrepTER).getId()==3/*бензин*/){
if ((((ReportDetails) objectrepDetails).getResource().getId() == 2)) {
   ((TypeTERForm3)objectrepTER).setSumm_plan(objectrepDetails.getPlan_col()+ ((TypeTERForm3)objectrepTER).getSumm_plan());
   if(objectrepDetails.getDt_inputFact()!=null){
   ((TypeTERForm3)objectrepTER).setSumm_fact( objectrepDetails.getFact_col()+ ((TypeTERForm3)objectrepTER).getSumm_fact());
   ((TypeTERForm3)objectrepTER).setSumm_ek( objectrepDetails.getFact_money()+ ((TypeTERForm3)objectrepTER).getSumm_ek());
}}}
                             
if(((TypeTERForm3)objectrepTER).getId()==4/*мазут*/){
if ((((ReportDetails) objectrepDetails).getResource().getId() == 3)) {
   ((TypeTERForm3)objectrepTER).setSumm_plan(((ReportDetails) objectrepDetails).getPlan_col()+ ((TypeTERForm3)objectrepTER).getSumm_plan());
   if(objectrepDetails.getDt_inputFact()!=null){
   ((TypeTERForm3)objectrepTER).setSumm_fact(((ReportDetails) objectrepDetails).getFact_col()+ ((TypeTERForm3)objectrepTER).getSumm_fact());
   ((TypeTERForm3)objectrepTER).setSumm_ek(((ReportDetails) objectrepDetails).getFact_money()+ ((TypeTERForm3)objectrepTER).getSumm_ek());
}}}                             
if(((TypeTERForm3)objectrepTER).getId()==5/*уголь*/){/*4 5*/
if ((((ReportDetails) objectrepDetails).getResource().getId() == 4)||(((ReportDetails) objectrepDetails).getResource().getId() == 5)) {
   ((TypeTERForm3)objectrepTER).setSumm_plan(((ReportDetails) objectrepDetails).getPlan_col()+ ((TypeTERForm3)objectrepTER).getSumm_plan());
   if(objectrepDetails.getDt_inputFact()!=null){
   ((TypeTERForm3)objectrepTER).setSumm_fact(((ReportDetails) objectrepDetails).getFact_col()+ ((TypeTERForm3)objectrepTER).getSumm_fact());
   ((TypeTERForm3)objectrepTER).setSumm_ek(((ReportDetails) objectrepDetails).getFact_money()+ ((TypeTERForm3)objectrepTER).getSumm_ek());
}}} 
if(((TypeTERForm3)objectrepTER).getId()==6/*масла*/){
if ((((ReportDetails) objectrepDetails).getResource().getId() == 6)) {
   ((TypeTERForm3)objectrepTER).setSumm_plan(((ReportDetails) objectrepDetails).getPlan_col()+ ((TypeTERForm3)objectrepTER).getSumm_plan());
   if(objectrepDetails.getDt_inputFact()!=null){
   ((TypeTERForm3)objectrepTER).setSumm_fact(((ReportDetails) objectrepDetails).getFact_col()+ ((TypeTERForm3)objectrepTER).getSumm_fact());
   ((TypeTERForm3)objectrepTER).setSumm_ek(((ReportDetails) objectrepDetails).getFact_money()+ ((TypeTERForm3)objectrepTER).getSumm_ek());
}}}
if(((TypeTERForm3)objectrepTER).getId()==7/*другие*/){
if ((((ReportDetails) objectrepDetails).getResource().getId() == 7)) {
   ((TypeTERForm3)objectrepTER).setSumm_plan(((ReportDetails) objectrepDetails).getPlan_col()+ ((TypeTERForm3)objectrepTER).getSumm_plan());
   if(objectrepDetails.getDt_inputFact()!=null){
   ((TypeTERForm3)objectrepTER).setSumm_fact(((ReportDetails) objectrepDetails).getFact_col()+ ((TypeTERForm3)objectrepTER).getSumm_fact());
   ((TypeTERForm3)objectrepTER).setSumm_ek(((ReportDetails) objectrepDetails).getFact_money()+ ((TypeTERForm3)objectrepTER).getSumm_ek());
}}}  
                                
                                
                                
                                
                                
                                
                                

      /*                 //  if (objectrepTER.getId() == objectrepDetails.getResource().getId()) {
                               if(((TypeTERForm3)objectrepTER).getId()==2){
                            
                                System.out.println(objectrepDetails.getPlan_col());
                                DecimalFormat myFo = new DecimalFormat("######.###");
                                myFo.format(objectrepDetails.getPlan_col());
                            }   
                                System.out.println(objectrepDetails.getPlan_col());
                            objectrepTER.setSumm_plan(objectrepDetails.getPlan_col() + objectrepTER.getSumm_plan());
                            
                           // if(objectrepDetails.getResource().getId()==13&& objectrepDetails.getDt_inputFact()!=null){
                            objectrepTER.setSumm_fact(objectrepDetails.getFact_col() + objectrepTER.getSumm_fact());//}
                            objectrepTER.setSumm_ek(objectrepDetails.getFact_money() + objectrepTER.getSumm_ek());
*/
                    //        }
}


                        if ((objectrepDetails.getResource().getId() == 13)) {
                            reportDetailsForm3.setCell1( objectrepDetails.getPlan_col() + reportDetailsForm3.getCell1());
                            if(objectrepDetails.getDt_inputFact()!=null){
                            reportDetailsForm3.setCell2( objectrepDetails.getFact_col() + reportDetailsForm3.getCell2());
                            reportDetailsForm3.setCell3( objectrepDetails.getFact_money() + reportDetailsForm3.getCell3());}
                        }
                        if ((objectrepDetails.getResource().getId() == 12&& objectrepDetails.getDt_inputFact()!=null)) {
                            reportDetailsForm3.setCell8( objectrepDetails.getFact_col() + reportDetailsForm3.getCell8());
                            reportDetailsForm3.setCell9( objectrepDetails.getFact_money() + reportDetailsForm3.getCell9());
                        }
                        if ((objectrepDetails.getResource().getId() == 16&& objectrepDetails.getDt_inputFact()!=null)) {
                            reportDetailsForm3.setCell10( objectrepDetails.getFact_col() + reportDetailsForm3.getCell10());
                        }
                        if ((objectrepDetails.getResource().getId() == 15&& objectrepDetails.getDt_inputFact()!=null)) {
                            reportDetailsForm3.setCell11( objectrepDetails.getFact_col() + reportDetailsForm3.getCell11());
                        }

                        if ((objectrepDetails.getResource().getId() == 14&& objectrepDetails.getDt_inputFact()!=null)) {
                            reportDetailsForm3.setCell12( objectrepDetails.getFact_col() + reportDetailsForm3.getCell12());
                        }
                        if ((objectrepDetails.getResource().getId() == 11&& objectrepDetails.getDt_inputFact()!=null)) {
                            reportDetailsForm3.setCell13( objectrepDetails.getFact_col() + reportDetailsForm3.getCell13());
                        }

                    }
                }
                }
            }
        }

        reportDetailsForm3.setCell4(typeTER);
        reportDetailsForm3List.add(reportDetailsForm3);

    }

     @PostConstruct
    public void Init() {
       System.out.println("=====333 ");
        dep=(new DepartmentRepository()).get(session.getUser().getDepartmentID());
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("day_begin")) {
        String dtPar=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_begin");
        mes_new=dtPar;
        String dday_p = dtPar.substring(0,2); 
        int dday1_p=Integer.parseInt(dday_p);
        String mmonth_p = dtPar.substring(3,5);
        int mmonth1_p=Integer.parseInt(mmonth_p)-1;
        String yyear_p = dtPar.substring(6,10);
        int yyear1_p=Integer.parseInt(yyear_p);
        Calendar DT_NOW=Calendar.getInstance(); 
        DT_NOW.set(yyear1_p,mmonth1_p,dday1_p,0,0,0);
        DT_NOW.set(Calendar.DATE, 1);
        dayBeginD=DT_NOW.getTime();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dayBegin = dateFormatter.format(dayBeginD);
        System.out.println("=====111dayBegin "+dayBegin);
        
        }else{
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy.");
        Calendar cdt= Calendar.getInstance();
        cdt.setTime(new Date());
        cdt.set(Calendar.DATE, 1);
        mes_new = dateFormatter2.format(cdt.getTime());
        dayBeginD = cdt.getTime();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dayBegin = dateFormatter.format(dayBeginD);
        System.out.println("=====222dayBegin "+dayBegin);
        
        }
        
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("day_end")) {
            
       // mes_new =mes_new+" - "+FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_end");
        
        String dtPar=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day_end");
        mes_new=mes_new+" - "+dtPar;
       
        //mes_new=mes_new+" - "+dayEnd;
        String dday_p = dtPar.substring(0,2); 
        int dday1_p=Integer.parseInt(dday_p);
        String mmonth_p = dtPar.substring(3,5);
        int mmonth1_p=Integer.parseInt(mmonth_p)-1;
        String yyear_p = dtPar.substring(6,10);
        int yyear1_p=Integer.parseInt(yyear_p);
        Calendar DT_NOW=Calendar.getInstance(); 
        DT_NOW.set(yyear1_p,mmonth1_p,dday1_p,0,0,0);
        DT_NOW.add(Calendar.MONTH, 1);
        DT_NOW.set(Calendar.DATE, 1);
        DT_NOW.add(Calendar.DATE, -1);
        dayEndD=DT_NOW.getTime();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dayEnd = dateFormatter.format(dayEndD);
         System.out.println("=====111dayEndD "+dayEnd);
        System.out.println("=====111mes_new"+mes_new);
        }else{
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy.");
        Calendar cdt= Calendar.getInstance();
        cdt.setTime(new Date());
        cdt.add(Calendar.MONTH, 1);
        cdt.set(Calendar.DATE, 1);
        cdt.add(Calendar.DATE, -1);
        mes_new =mes_new+" - "+ dateFormatter2.format(cdt.getTime());
        dayEndD = cdt.getTime();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dayEnd = dateFormatter.format(dayEndD);
         System.out.println("=====222dayEndD "+dayEnd);
         System.out.println("=====222mes_new"+mes_new);
        }
      
        
        
      /*  if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("mes")) {
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

    public void Print(Object document) {
        StringImprover f=new StringImprover();

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
        new createCell().createCell(wb, sheet, cellStyle_R, row, 0, 0, 0, 0, 12, "Форма 3 за "+ mes_new);

        Row row1 = sheet.createRow(1);
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 12, "Результат проведения акции");
        Row row2 = sheet.createRow(2);
        new createCell().createCell(wb, sheet, cellStyle, row2, 2, 2, 0, 0, 12, dep.getNameNp());

        Row row3 = sheet.createRow(3);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 0, 0, 2, "Сокращение потребления электоэнергии");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 1, 1, 1);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 2, 2, 2);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 3, 3, 6, "Сокращение потребления топливоэнергетических-ресурсов");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 4, 4, 4);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 5, 5, 5);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 6, 6, 6);

        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 7, 7, 8, "Сокращение выбросов загрязняющих веществ в атмосферу");

        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 8, 8, 8);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 9, 9, 10, "Озеленение территории");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 10, 10, 10);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 11, 11, 12, "Уборка территории");

        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 12, 12, 12);


        Row row4 = sheet.createRow(4);

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 0, 0, 1, "кВт");

        new createCellNull().createCellNull(sheet, cellStyle2, row4, 4, 4, 1, 1, 1);
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 2, 2, 2, "Экономичексий эффект, тыс. руб.");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 3, 3, 3, "Виды ТЭР");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 4, 4, 5, "тонн");
        new createCellNull().createCellNull(sheet, cellStyle2, row4, 4, 4, 5, 5, 5);

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 6, 6, 6, "Экономичексий эффект, тыс. руб.");
        //new createCellNull().createCellNull(sheet, cellStyle2, row4, 5, 5, 6, 6, 6);

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 7, 7, 7, "тонн");


        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 8, 8, 8, "Экономичексий эффект, тыс. руб.");


        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 9, 9, 9, "Деревья и кустарники, шт.");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 10, 10, 10, "Территория, м.кв");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 11, 11, 11, "Территории, м.кв");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 12, 12, 12, "Количество отходов т");



        Row row5 = sheet.createRow(5);
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 0, 0, 0, "план");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 1, 1, 1, "факт");

        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 2, 2, 2);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 3, 3, 3);
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 4, 4, 4, "план");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 5, 5, 5, "факт");
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 6, 6, 6);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 7, 7, 7);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 8, 8, 8);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 9, 9, 9);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 10, 10, 10);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 11, 11, 11);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 12, 12, 12);


        HSSFRow row6 = sheet.createRow(6);
        for (int i1 = 0; i1 < 13; i1++) {
            new createCell().createCell(wb, sheet, cellStyle1, row6, 6, 6, i1, i1, i1, String.valueOf(i1 + 1));
        }

        int j = 7;

        /* Iterator itrtypeTER = reportDetailsForm3List.iterator();
         while (itrtypeTER.hasNext()) {
         Object objectrepTER = itrtypeTER.next();
         if (objectrepTER instanceof ReportDetailsForm3) {
                        
         ReportDetailsForm3 repDetailsForm3=(ReportDetailsForm3)objectrepTER;
         */
        for (int i = 0; i < reportDetailsForm3List.size(); i++) {
            ReportDetailsForm3 reportDetailsForm3 = reportDetailsForm3List.get(i);
            j = i + 7;
            HSSFRow header8 = sheet.createRow(j);
            int j1 = j;
            for (int i1 = 0; i1 < reportDetailsForm3.getCell4().size(); i1++) {
                j1 = i1 + j;

                TypeTERForm3 typeTERForm3Form3 = reportDetailsForm3.getCell4().get(i1);
                HSSFRow header81 = sheet.createRow(j1);
                for (int k = 0; k < 3; k++) {
                    new createCellNull().createCellNull(sheet, cellStyle2, header81, j1, j1, k, k, k);
                }

                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 3, 3, 3, String.valueOf(typeTERForm3Form3.getName()));
                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 4, 4, 4, f.formatForEXCEL(typeTERForm3Form3.getSumm_plan()));
                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 5, 5, 5,  f.formatForEXCEL(typeTERForm3Form3.getSumm_fact()));
                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 6, 6, 6,  f.formatMoneyForEXCEL(typeTERForm3Form3.getSumm_ek()));
                for (int k = 7; k < 13; k++) {
                    new createCellNull().createCellNull(sheet, cellStyle2, header81, j1, j1, k, k, k);
                }

            }





            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 0, 0, 0,  f.formatForEXCEL(reportDetailsForm3.getCell1()));


            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 1, 1, 1,  f.formatForEXCEL(reportDetailsForm3.getCell2()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 2, 2, 2,  f.formatMoneyForEXCEL(reportDetailsForm3.getCell3()));

            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 7, 7, 7,  f.formatForEXCEL(reportDetailsForm3.getCell8()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 8, 8, 8, f.formatMoneyForEXCEL(reportDetailsForm3.getCell9()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 9, 9, 9,  f.formatForEXCEL(reportDetailsForm3.getCell10()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 10, 10, 10,  f.formatForEXCEL(reportDetailsForm3.getCell11()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 11, 11, 11,  f.formatForEXCEL(reportDetailsForm3.getCell12()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j + reportDetailsForm3.getCell4().size() - 1, 12, 12, 12,  f.formatForEXCEL(reportDetailsForm3.getCell13()));



        }



        for (int i1 = 0; i1 < 13; i1++) {
            sheet.autoSizeColumn(i1, true);

        }


        wb.removeSheetAt(0);

    }
}
