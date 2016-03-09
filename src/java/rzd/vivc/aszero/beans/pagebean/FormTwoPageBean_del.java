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
import java.util.Iterator;
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
import org.primefaces.event.RowEditEvent;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.classes.excel.createCellNull;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsForm2;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.ReportDetailsRepository;
import rzd.vivc.aszero.repository.ReportForm2DetailsRepository;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.repository.ResourceRepository;
import rzd.vivc.aszero.repository.UserRepository;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormTwoPageBean_del implements Serializable{
/*private String FONT="Courier New";
    
    private Report repForm2 ;
    
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    
      public Report getRepForm2() {
        return repForm2;
    }

    public void setRepForm2(Report repForm2) {
        this.repForm2 = repForm2;
    }
    
    
  
     private Date mes;

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }
     
     
     
     
     
    
    
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
 
    
    
    
    
    
    public void Upd1(){
        coll7=0;
        coll8=0;
        coll9=0;
        coll10=0;
    List<ReportDetailsForm2> reportDetailsForm2List=repForm2.getDetailsForm2();
    repForm2.setDt_create(new Date());
    DepartmentRepository deprep=new DepartmentRepository();
            List <Department> depList=new ArrayList<Department>();
            depList =deprep.getActiveListDepartmentsWithDetails(repForm2.getUsr().getDepartment().getIdNp());
            Iterator itr =  depList.iterator();
         while (itr.hasNext()) {
        Object object = itr.next();
            if (object instanceof Department) {
             
                
        Iterator itrDetailsForm2 =  reportDetailsForm2List.iterator();
        while (itrDetailsForm2.hasNext()) {
             Object objectDetailsForm2 = itrDetailsForm2.next();
            if (objectDetailsForm2 instanceof ReportDetailsForm2) {
            ReportDetailsForm2 reportDetailsForm2=(ReportDetailsForm2)objectDetailsForm2; 
            
            
            
            
            if(reportDetailsForm2.getDepartmentName().equals(((Department)object).getNameNp())){
            reportDetailsForm2.setForm2Coll7(0);
            reportDetailsForm2.setForm2Coll8(0);
            reportDetailsForm2.setForm2Coll9(0);
            reportDetailsForm2.setForm2Coll10(0);
            
            
            ReportRepository repRep=new ReportRepository();
            // List<Report> rep=repRep.getListWithDetailsIdNp(((Department)object).getIdNp());
              List<Report> rep=repRep.getListWithDetailsIdNpMonth(((Department)object).getIdNp(),mes.getMonth()+1,mes.getYear()+1900);
             
             if(rep.size()>0){
                 
                 
                 
              Iterator itrrep =  rep.iterator();
         while (itrrep.hasNext()) {
        Object objectrep = itrrep.next();
            if (objectrep instanceof Report) {
                
              Iterator itrrepDetails =  ((Report)objectrep).getDetails().iterator();
         while (itrrepDetails.hasNext()) {
        Object objectrepDetails = itrrepDetails.next();
            if (objectrepDetails instanceof ReportDetails) {  
                
             if((((ReportDetails)objectrepDetails).getResource().getId()==17)||
                (((ReportDetails)objectrepDetails).getResource().getId()==3)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==4)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==5)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==2)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==1)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==7)
                     )   
             {
                 reportDetailsForm2.setForm2Coll7(((ReportDetails)objectrepDetails).getPlan_tut()+reportDetailsForm2.getForm2Coll7());
                 reportDetailsForm2.setForm2Coll8(((ReportDetails)objectrepDetails).getFact_tut()+reportDetailsForm2.getForm2Coll8());
                 coll7=coll7+reportDetailsForm2.getForm2Coll7();
        coll8=coll8+reportDetailsForm2.getForm2Coll8();
        
                 
             } 
             if((((ReportDetails)objectrepDetails).getResource().getId()==13))
                
             {
                 reportDetailsForm2.setForm2Coll9(((ReportDetails)objectrepDetails).getPlan_tut()+reportDetailsForm2.getForm2Coll9());
                 reportDetailsForm2.setForm2Coll10(((ReportDetails)objectrepDetails).getFact_tut()+reportDetailsForm2.getForm2Coll10());
                 coll9=coll9+reportDetailsForm2.getForm2Coll9();
        coll10=coll10+reportDetailsForm2.getForm2Coll10();
             } 
             
            }}
                
               
             reportDetailsForm2.setRepId(((Report)objectrep).getId());
            // reportDetailsForm2.setReportId(((Report)objectrep));
             
            Date dt_b=new Date() ;
             
           //dt_b.setTime(((Report)objectrep).getDt_begin().getTime());
                //дата начала проведения акции
           dt_b.setTime(((Report)objectrep).getTime_begin().getTime()+((Report)objectrep).getDt_begin().getTime());
           System.out.println(((Report)objectrep).getId()+"=="+dt_b+"==="+((Report)objectrep).getTime_begin().getTime());
          
           Calendar DT_NOW=Calendar.getInstance(); 
           DT_NOW.setTime(dt_b);
           System.out.println(DT_NOW);
           
           Date dt_f=new Date() ;
                //дата окончания проведения акции
           dt_f.setTime(((Report)objectrep).getTime_finish().getTime()+((Report)objectrep).getDt_begin().getTime());
           System.out.println(((Report)objectrep).getId()+"=="+dt_f+"==="+((Report)objectrep).getTime_finish().getTime());
          
           
           
           Date dt_b_m1=new Date() ;
                //дата начала проведения акции за 1 день
           dt_b_m1.setTime(dt_b.getTime()-86400*1000 +10800000);
           System.out.println(dt_b_m1);
           
           
           
           
           Date dt_f_p1=new Date() ;
            //дата окончания проведения акции за -1 день
           dt_f_p1.setTime(dt_f.getTime()+86400*1000 +10800000);
           
           System.out.println(dt_f_p1);
           
            
            
            System.out.println(((Report)objectrep).getDt_inputFact());
           System.out.println(((Report)objectrep).getDt_inputPlan());
           
           if(!((((Report)objectrep).getDt_inputFact())==null)){
                     if(dt_f_p1.after(((Report)objectrep).getDt_inputFact()))  {
                reportDetailsForm2.setInputActivityFact("green");
               }else{
               reportDetailsForm2.setInputActivityFact("yellow");
               }
             }
             else
             {
             reportDetailsForm2.setInputActivityFact("red");
             }
           
           
             
             if(!((((Report)objectrep).getDt_inputPlan())==null)){
                     if(dt_b_m1.after(((Report)objectrep).getDt_inputPlan()))  {
                reportDetailsForm2.setInputActivityPlan("green");
               }else{
               reportDetailsForm2.setInputActivityPlan("yellow");
               }
             }
             else
             {
             reportDetailsForm2.setInputActivityPlan("red");
             }
             }}
             }
             
             else{
                 reportDetailsForm2.setRepId(0);
             reportDetailsForm2.setInputActivityPlan("red");
             reportDetailsForm2.setInputActivityFact("red");
             
             }
            
            }
            
            
            }} 
            }}
    
    
    
    
    
    }
    
    
    public void Upd(){
    coll7=0;
        coll8=0;
        coll9=0;
        coll10=0;
       List<ReportDetailsForm2> reportDetailsForm2List=new ArrayList<ReportDetailsForm2>();
            
            DepartmentRepository deprep=new DepartmentRepository();
            List <Department> depList=new ArrayList<Department>();
            depList =deprep.getActiveListDepartmentsWithDetails(repForm2.getUsr().getDepartment().getIdNp());
            Iterator itr =  depList.iterator();
         while (itr.hasNext()) {
        Object object = itr.next();
            if (object instanceof Department) {
              ReportDetailsForm2 reportDetailsForm2=new ReportDetailsForm2(); 
              reportDetailsForm2.setDepartmentName(((Department)object).getNameNp());
              
             ReportRepository repRep=new ReportRepository();
             
             System.out.println(mes.getMonth());
             System.out.println(((Department)object).getIdNp());
             List<Report> rep=repRep.getListWithDetailsIdNpMonth(((Department)object).getIdNp(),mes.getMonth()+1,mes.getYear()+1900);
             
                System.out.println(rep);
             
             
             if(rep.size()>0){
              Iterator itrrep =  rep.iterator();
         while (itrrep.hasNext()) {
        Object objectrep = itrrep.next();
            if (objectrep instanceof Report) {
                
              Iterator itrrepDetails =  ((Report)objectrep).getDetails().iterator();
         while (itrrepDetails.hasNext()) {
        Object objectrepDetails = itrrepDetails.next();
            if (objectrepDetails instanceof ReportDetails) {  
                
             if((((ReportDetails)objectrepDetails).getResource().getId()==17)||
                (((ReportDetails)objectrepDetails).getResource().getId()==3)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==4)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==5)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==2)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==1)||
                     (((ReportDetails)objectrepDetails).getResource().getId()==7)
                     )   
             {
                 reportDetailsForm2.setForm2Coll7(((ReportDetails)objectrepDetails).getPlan_tut()+reportDetailsForm2.getForm2Coll7());
                 reportDetailsForm2.setForm2Coll8(((ReportDetails)objectrepDetails).getFact_tut()+reportDetailsForm2.getForm2Coll8());
                 coll7=coll7+reportDetailsForm2.getForm2Coll9();
        coll8=coll8+reportDetailsForm2.getForm2Coll10();
             } 
             if((((ReportDetails)objectrepDetails).getResource().getId()==13))
                
             {
                 reportDetailsForm2.setForm2Coll9(((ReportDetails)objectrepDetails).getPlan_tut()+reportDetailsForm2.getForm2Coll9());
                 reportDetailsForm2.setForm2Coll10(((ReportDetails)objectrepDetails).getFact_tut()+reportDetailsForm2.getForm2Coll10());
                 coll9=coll9+reportDetailsForm2.getForm2Coll9();
        coll10=coll10+reportDetailsForm2.getForm2Coll10();
             } 
             
            }}
                
               
             reportDetailsForm2.setRepId(((Report)objectrep).getId());
           //  reportDetailsForm2.setReportId(((Report)objectrep));
             
            Date dt_b=new Date() ;
             
           //dt_b.setTime(((Report)objectrep).getDt_begin().getTime());
                //дата начала проведения акции
           dt_b.setTime(((Report)objectrep).getTime_begin().getTime()+((Report)objectrep).getDt_begin().getTime());
           System.out.println(((Report)objectrep).getId()+"=="+dt_b+"==="+((Report)objectrep).getTime_begin().getTime());
          
           Calendar DT_NOW=Calendar.getInstance(); 
           DT_NOW.setTime(dt_b);
           System.out.println(DT_NOW);
           
           Date dt_f=new Date() ;
                //дата окончания проведения акции
           dt_f.setTime(((Report)objectrep).getTime_finish().getTime()+((Report)objectrep).getDt_begin().getTime());
           System.out.println(((Report)objectrep).getId()+"=="+dt_f+"==="+((Report)objectrep).getTime_finish().getTime());
          
           
           
           Date dt_b_m1=new Date() ;
                //дата начала проведения акции за 1 день
           dt_b_m1.setTime(dt_b.getTime()-86400*1000 +10800000);
           System.out.println(dt_b_m1);
           
           
           
           
           Date dt_f_p1=new Date() ;
            //дата окончания проведения акции за -1 день
           dt_f_p1.setTime(dt_f.getTime()+86400*1000 +10800000);
           
           System.out.println(dt_f_p1);
           
            
            
            System.out.println(((Report)objectrep).getDt_inputFact());
           System.out.println(((Report)objectrep).getDt_inputPlan());
           
           if(!((((Report)objectrep).getDt_inputFact())==null)){
                     if(dt_f_p1.after(((Report)objectrep).getDt_inputFact()))  {
                reportDetailsForm2.setInputActivityFact("green");
               }else{
               reportDetailsForm2.setInputActivityFact("yellow");
               }
             }
             else
             {
             reportDetailsForm2.setInputActivityFact("red");
             }
           
           
             
             if(!((((Report)objectrep).getDt_inputPlan())==null)){
                     if(dt_b_m1.after(((Report)objectrep).getDt_inputPlan()))  {
                reportDetailsForm2.setInputActivityPlan("green");
               }else{
               reportDetailsForm2.setInputActivityPlan("yellow");
               }
             }
             else
             {
             reportDetailsForm2.setInputActivityPlan("red");
             }
             }}
             }else{
                 reportDetailsForm2.setRepId(0);
             reportDetailsForm2.setInputActivityPlan("red");
             reportDetailsForm2.setInputActivityFact("red");
             
             }
             
              reportDetailsForm2.setReportForm2(repForm2);
             // reportDetailsForm2.setAskye(repForm2.getDetailsForm2().);
              
              
              
              
              reportDetailsForm2List.add(reportDetailsForm2);
            
            }}
            repForm2.setDetailsForm2(reportDetailsForm2List);
    
    }
    
    
    
    
    
    
    
    @PostConstruct
    public void Init() {
        System.out.println("==========");
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id")) {

            String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString();
            long id = new Integer(idStr);
            repForm2 = (new ReportRepository()).getWithDetailsForm2(id);
            
            
            mes=repForm2.getDt_begin();
        } else {
            repForm2 = new Report();
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("form_id")) {
                repForm2.setForm(new Form(new Long(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("form_id").toString())));
            }
            
            
            
            mes=new Date();
            
            repForm2.setUsr(new UserRepository().get(new User(session.getUser().getUserID())));
            repForm2.setDepartment(repForm2.getUsr().getDepartment());
            
            Upd();
            repForm2.setDt_begin(mes);
            
            
            
            
        }
        
        
        

    }
    
    
     public void updatedRep() {
     Upd1();
     repForm2.setDt_begin(mes);
     }
     
    
    
    public void save() {
    
    new ReportRepository().save(getRepForm2());
   
            
            Iterator itr =  (repForm2.getDetailsForm2()).iterator();
         while (itr.hasNext()) {
        Object object = itr.next();
            if (object instanceof ReportDetailsForm2) {
    new ReportForm2DetailsRepository().save(((ReportDetailsForm2)object));}}
    }
    
    
    
     public void Print(Object document) {
coll7=0;
        coll8=0;
        coll9=0;
        coll10=0;
        askye=0;
        HSSFWorkbook wb = (HSSFWorkbook) document;



        HSSFSheet sheet = wb.createSheet("new sheet");

        HSSFPrintSetup print = sheet.getPrintSetup();
        print.setScale((short) 83);
        print.setPaperSize((short) print.A4_PAPERSIZE);
       


        Font font_10 = wb.createFont();
        font_10.setItalic(true);
        font_10.setFontHeightInPoints((short)10);
        font_10.setFontName(FONT);
    
        Font font_14 = wb.createFont();
        font_14.setItalic(true);
        font_14.setFontHeightInPoints((short)14);
        font_14.setFontName(FONT);
        
        Font font_18 = wb.createFont();
        font_18.setItalic(true);
        font_18.setFontHeightInPoints((short)18);
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
new createCell().createCell(wb,sheet,cellStyle_R,row,0,0,0,0,10,"Форма 2");

Row row1 = sheet.createRow(1);
new createCell().createCell(wb,sheet,cellStyle,row1,1,1,0,0,10,repForm2.getUsr().getDepartment().getNameNp());

Row row2 = sheet.createRow(2);
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,0,0,0,"Наименование структурного подразделения");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,1,1,1,"Начальник(Ф.И.О.)");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,2,2,2,"Рабочий телефон");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,3,3,3,"РОРС Телефон");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,4,4,4,"Ввод мероприятий");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,5,5,5,"Ввод отчета по экономии");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,6,6,6,"План экономии ТУТ");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,7,7,7,"Факт экономии ТУТ");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,8,8,8,"План экономии эл. энергии кВтч");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,9,9,9,"Факт экономии эл. энергии кВтч");
new createCell().createCell(wb,sheet,cellStyle2,row2,2,2,10,10,10,"Факт экономии эл. энергии АСКУЭ");
     
      

        HSSFRow row3 = sheet.createRow(3);
        for (int i1 = 0; i1 < 11; i1++) {
        new createCell().createCell(wb,sheet,cellStyle1,row3,3,3,i1,i1,i1,String.valueOf(i1 + 1));    
        }
        
        
        
        repForm2.getDetailsForm2();
        int j = 4;
        for (int i = 0; i < repForm2.getDetailsForm2().size(); i++) {
            j = i + 4;
            HSSFRow header8 = sheet.createRow(j);
            ReportDetailsForm2 repDetailsForm2 = repForm2.getDetailsForm2().get(i);


            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,0,0,0,repDetailsForm2.getDepartmentName());
            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,1,1,1,repDetailsForm2.getFioChief());
            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,2,2,2, repDetailsForm2.getPhoneChief());
            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,3,3,3, repDetailsForm2.getRorsChief());
            
            
            if(repDetailsForm2.getInputActivityPlan().equals("green")){
             new createCell().createCell(wb,sheet,cellStyle1_g,header8,j,j,4,4,4, "   ");
            } 
            if(repDetailsForm2.getInputActivityPlan().equals("yellow")){
                 new createCell().createCell(wb,sheet,cellStyle1_y,header8,j,j,4,4,4, "   ");
            }    
            if(repDetailsForm2.getInputActivityPlan().equals("red")){
                 new createCell().createCell(wb,sheet,cellStyle1_r,header8,j,j,4,4,4, "   ");
            }    
             if(repDetailsForm2.getInputActivityFact().equals("green")){
                 System.out.println(repDetailsForm2.getInputActivityFact());
             new createCell().createCell(wb,sheet,cellStyle1_g,header8,j,j,5,5,5, "   ");
            } 
            if(repDetailsForm2.getInputActivityFact().equals("yellow")){
                 System.out.println(repDetailsForm2.getInputActivityFact());
                 new createCell().createCell(wb,sheet,cellStyle1_y,header8,j,j,5,5,5, "   ");
            }    
            if(repDetailsForm2.getInputActivityFact().equals("red")){
                 System.out.println(repDetailsForm2.getInputActivityFact());
                 new createCell().createCell(wb,sheet,cellStyle1_r,header8,j,j,5,5,5, "   ");
            }  
            
            
            
           
            coll7=coll7+repDetailsForm2.getForm2Coll7();
            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,6,6,6, String.valueOf(repDetailsForm2.getForm2Coll7()));
            coll8=coll8+repDetailsForm2.getForm2Coll8();
            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,7,7,7, String.valueOf(repDetailsForm2.getForm2Coll8()));
            coll9=coll9+repDetailsForm2.getForm2Coll9();
            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,8,8,8, String.valueOf(repDetailsForm2.getForm2Coll9()));
            coll10=coll10+repDetailsForm2.getForm2Coll10();
            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,9,9,9, String.valueOf(repDetailsForm2.getForm2Coll10()));
            askye=askye+repDetailsForm2.getAskye();
            new createCell().createCell(wb,sheet,cellStyle1,header8,j,j,10,10,10, String.valueOf(repDetailsForm2.getAskye()));
            
 
       
        }
        j++;
         HSSFRow headerj01 = sheet.createRow(j);
        new createCell().createCell(wb,sheet,cellStyle_R,headerj01,j,j,0,0,5,"ИТОГО: ");
        
        new createCell().createCell(wb,sheet,cellStyle1,headerj01,j,j,6,6,6,String.valueOf(coll7));
        new createCell().createCell(wb,sheet,cellStyle1,headerj01,j,j,7,7,7,String.valueOf(coll8));
        new createCell().createCell(wb,sheet,cellStyle1,headerj01,j,j,8,8,8,String.valueOf(coll9));
        new createCell().createCell(wb,sheet,cellStyle1,headerj01,j,j,9,9,9,String.valueOf(coll10));
        new createCell().createCell(wb,sheet,cellStyle1,headerj01,j,j,10,10,10,String.valueOf(askye));
     
        j++;
        HSSFRow headerj1 = sheet.createRow(j);
        new createCell().createCell(wb,sheet,cellStyle_L,headerj1,j,j,0,0,10,"Дата внесения информации: " + dateFormatter2.format(repForm2.getDt_create()));
        
        j++;
        HSSFRow headerj2 = sheet.createRow(j);
        new createCell().createCell(wb,sheet,cellStyle_L,headerj2,j,j,0,0,10,"Исполнитель: " + repForm2.getUsr().getFIO());
        
        j++;
        HSSFRow headerj3 = sheet.createRow(j);
        new createCell().createCell(wb,sheet,cellStyle_L,headerj3,j,j,0,0,10,"Телефон: " + repForm2.getUsr().getPhoneNumber());
        
        for (int i1 = 0; i1 < 11; i1++) {
            sheet.autoSizeColumn(i1,true);

        }


        wb.removeSheetAt(0);

      
    }
    
   */
    
}
