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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
import rzd.vivc.aszero.autorisation.CheckRights;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.classes.excel.createCellNull;
import rzd.vivc.aszero.dto.DetailsForm5;
import rzd.vivc.aszero.dto.TypeTER;
import rzd.vivc.aszero.dto.TypeTERForm3;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.repository.TypeTERRepository;
import rzd.vivc.aszero.service.StringImprover;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormTreePageBean1New extends BasePage implements Serializable {

    public FormTreePageBean1New() {
        super();
    }

    private String FONT = "Courier New";
    private String mes_new;
   // private int mes;
   // private int year;
      private String dayBegin;
      private String dayEnd;
      private Date dayBeginD;
      private Date dayEndD;
       private boolean t_mes1;

    public boolean isT_mes1() {
        return t_mes1;
    }

    public void setT_mes1(boolean t_mes1) {
        this.t_mes1 = t_mes1;
    }
      
    public String getMes_new() {
        return mes_new;
    }

    public void setMes_new(String mes_new) {
        this.mes_new = mes_new;
    }
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private List<DetailsForm5> reportDetailsForm5List;

    //  private ReportDetailsForm3 reportDetailsForm3;
    //private Department dep;
    public List<DetailsForm5> getReportDetailsForm5List() {
        return reportDetailsForm5List;
    }

    public void setReportDetailsForm5List(List<DetailsForm5> reportDetailsForm5List) {
        this.reportDetailsForm5List = reportDetailsForm5List;
    }

    

    /* public Department getDep() {
     return dep;
     }

     public void setDep(Department dep) {
     this.dep = dep;
     }*/
    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    private void getTypeC (Map<Long, TypeTERForm3> typeC, List<TypeTER> typeTERForm3All) {
        
       /* for ( int i=typeTERForm3All.size()-1; i>=0;i--) {
                TypeTER objectrepTERForm3=typeTERForm3All.get(i);
                TypeTERForm3 t3 = new TypeTERForm3();
                t3.setName(objectrepTERForm3.getName());
                typeC.put(objectrepTERForm3.getId(),t3);
        }*/
         for (TypeTER objectrepTERForm3 : typeTERForm3All) {
                TypeTERForm3 t3 = new TypeTERForm3();
                t3.setName(objectrepTERForm3.getName());
                typeC.put(objectrepTERForm3.getId(),t3);
            } 
        typeC.get(new Long(1)).setSumm_plan(0);
              typeC.get(new Long(1)).setSumm_fact(0); 
              typeC.get(new Long(1)).setSumm_ek(0);
              typeC.get(new Long(2)).setSumm_plan(0);
              typeC.get(new Long(2)).setSumm_fact(0); 
              typeC.get(new Long(2)).setSumm_ek(0);
              typeC.get(new Long(3)).setSumm_plan(0);
              typeC.get(new Long(3)).setSumm_fact(0); 
              typeC.get(new Long(3)).setSumm_ek(0);
              typeC.get(new Long(4)).setSumm_plan(0);
              typeC.get(new Long(4)).setSumm_fact(0); 
              typeC.get(new Long(4)).setSumm_ek(0);
              typeC.get(new Long(5)).setSumm_plan(0);
              typeC.get(new Long(5)).setSumm_fact(0); 
              typeC.get(new Long(5)).setSumm_ek(0);
              typeC.get(new Long(6)).setSumm_plan(0);
              typeC.get(new Long(6)).setSumm_fact(0); 
              typeC.get(new Long(6)).setSumm_ek(0);
              typeC.get(new Long(7)).setSumm_plan(0);
              typeC.get(new Long(7)).setSumm_fact(0); 
              typeC.get(new Long(7)).setSumm_ek(0);
    }
    
    
    public void Upd() {
 if(session != null && session.getUser() != null){
      t_mes1=true;
     System.out.println(dayBeginD.getMonth());
     
     
         System.out.println(dayBeginD.getMonth());
         System.out.println(dayEndD.getMonth());
        t_mes1=dayBeginD.getMonth()==dayEndD.getMonth()-1;
        
      //  if(dayBeginD.getMonth()!=dayEndD.getMonth()){};
        List<DetailsForm5> detailsForm5ListSQL=new ArrayList<DetailsForm5> ();
        if(t_mes1){
        detailsForm5ListSQL=(new ReportRepository()).getListWithDetailsForm3((int) session.getUser().getDepartmentID(),(int) session.getUser().getDepartmentPower(), dayBeginD, dayEndD);
        }
         if(!t_mes1){
        detailsForm5ListSQL=(new ReportRepository()).getListWithDetailsForm3Per((int) session.getUser().getDepartmentID(),(int) session.getUser().getDepartmentPower(), dayBeginD, dayEndD);
        }
         int k=-1;
         
         reportDetailsForm5List=new ArrayList<DetailsForm5>();
          /*список видов ресурсов из бд*/
         List<TypeTER> typeTERForm3All = ((new TypeTERRepository()).getAll(TypeTER.class));
         
       /*   Map<Long, TypeTERForm3> typeCDor = new Hashtable<Long, TypeTERForm3>();
         // getTypeC(typeCDor,typeTERForm3All);
           for (TypeTER objectrepTERForm3 : typeTERForm3All) {
                TypeTERForm3 t3 = new TypeTERForm3();
                t3.setName(objectrepTERForm3.getName());
                typeCDor.put(objectrepTERForm3.getId(),t3);
            } 
        typeCDor.get(new Long(1)).setSumm_plan(0);
              typeCDor.get(new Long(1)).setSumm_fact(0); 
              typeCDor.get(new Long(1)).setSumm_ek(0);
              typeCDor.get(new Long(2)).setSumm_plan(0);
              typeCDor.get(new Long(2)).setSumm_fact(0); 
              typeCDor.get(new Long(2)).setSumm_ek(0);
              typeCDor.get(new Long(3)).setSumm_plan(0);
              typeCDor.get(new Long(3)).setSumm_fact(0); 
              typeCDor.get(new Long(3)).setSumm_ek(0);
              typeCDor.get(new Long(4)).setSumm_plan(0);
              typeCDor.get(new Long(4)).setSumm_fact(0); 
              typeCDor.get(new Long(4)).setSumm_ek(0);
              typeCDor.get(new Long(5)).setSumm_plan(0);
              typeCDor.get(new Long(5)).setSumm_fact(0); 
              typeCDor.get(new Long(5)).setSumm_ek(0);
              typeCDor.get(new Long(6)).setSumm_plan(0);
              typeCDor.get(new Long(6)).setSumm_fact(0); 
              typeCDor.get(new Long(6)).setSumm_ek(0);
              typeCDor.get(new Long(7)).setSumm_plan(0);
              typeCDor.get(new Long(7)).setSumm_fact(0); 
              typeCDor.get(new Long(7)).setSumm_ek(0);*/
          
       /*    Map<Long, TypeTERForm3> typeCDi = new Hashtable<Long, TypeTERForm3>();
          //getTypeC(typeCDi,typeTERForm3All);
         for (TypeTER objectrepTERForm3 : typeTERForm3All) {
                TypeTERForm3 t3 = new TypeTERForm3();
                t3.setName(objectrepTERForm3.getName());
                typeCDi.put(objectrepTERForm3.getId(),t3);
            } 
        typeCDi.get(new Long(1)).setSumm_plan(0);
              typeCDi.get(new Long(1)).setSumm_fact(0); 
              typeCDi.get(new Long(1)).setSumm_ek(0);
              typeCDi.get(new Long(2)).setSumm_plan(0);
              typeCDi.get(new Long(2)).setSumm_fact(0); 
              typeCDi.get(new Long(2)).setSumm_ek(0);
              typeCDi.get(new Long(3)).setSumm_plan(0);
              typeCDi.get(new Long(3)).setSumm_fact(0); 
              typeCDi.get(new Long(3)).setSumm_ek(0);
              typeCDi.get(new Long(4)).setSumm_plan(0);
              typeCDi.get(new Long(4)).setSumm_fact(0); 
              typeCDi.get(new Long(4)).setSumm_ek(0);
              typeCDi.get(new Long(5)).setSumm_plan(0);
              typeCDi.get(new Long(5)).setSumm_fact(0); 
              typeCDi.get(new Long(5)).setSumm_ek(0);
              typeCDi.get(new Long(6)).setSumm_plan(0);
              typeCDi.get(new Long(6)).setSumm_fact(0); 
              typeCDi.get(new Long(6)).setSumm_ek(0);
              typeCDi.get(new Long(7)).setSumm_plan(0);
              typeCDi.get(new Long(7)).setSumm_fact(0); 
              typeCDi.get(new Long(7)).setSumm_ek(0);*/
          
                
         
    /*     DetailsForm5 sumDi=new DetailsForm5();  
           sumDi.setPlanE(0);
            sumDi.setFactE(0);
            sumDi.setFactEM(0);
            sumDi.setFactS(0);
            sumDi.setFactSM(0);
            sumDi.setFactO(0);
            sumDi.setFactO2(0);
            sumDi.setFactY0(0);
            sumDi.setFactY2(0);
            sumDi.setIdF2(-1);
         DetailsForm5 sumDor=new DetailsForm5();
         sumDor.setNamenp("ИТОГО");
            sumDor.setPlanE(0);
            sumDor.setFactE(0);
            sumDor.setFactEM(0);
            sumDor.setFactS(0);
            sumDor.setFactSM(0);
            sumDor.setFactO(0);
            sumDor.setFactO2(0);
            sumDor.setFactY0(0);
            sumDor.setFactY2(0);
            sumDor.setIdF2(-1);
         */
          for (DetailsForm5 detailsForm5 : detailsForm5ListSQL) {
              
            Map<Long, TypeTERForm3> typeC4 = new Hashtable<Long, TypeTERForm3>();
                for (TypeTER objectrepTERForm3 : typeTERForm3All) {
                TypeTERForm3 t3 = new TypeTERForm3();
                t3.setName(objectrepTERForm3.getName());
                typeC4.put(objectrepTERForm3.getId(),t3);
            }  
              typeC4.get(new Long(1)).setSumm_plan(detailsForm5.getPlanG().floatValue());
              typeC4.get(new Long(1)).setSumm_fact(detailsForm5.getFactG().floatValue()); 
              typeC4.get(new Long(1)).setSumm_ek(detailsForm5.getFactGM().floatValue());
              typeC4.get(new Long(2)).setSumm_plan(detailsForm5.getPlanD().floatValue());
              typeC4.get(new Long(2)).setSumm_fact(detailsForm5.getFactD().floatValue()); 
              typeC4.get(new Long(2)).setSumm_ek(detailsForm5.getFactDM().floatValue());
              typeC4.get(new Long(3)).setSumm_plan(detailsForm5.getPlanB().floatValue());
              typeC4.get(new Long(3)).setSumm_fact(detailsForm5.getFactB().floatValue()); 
              typeC4.get(new Long(3)).setSumm_ek(detailsForm5.getFactBM().floatValue());
              typeC4.get(new Long(4)).setSumm_plan(detailsForm5.getPlanM().floatValue());
              typeC4.get(new Long(4)).setSumm_fact(detailsForm5.getFactM().floatValue()); 
              typeC4.get(new Long(4)).setSumm_ek(detailsForm5.getFactMM().floatValue());
              typeC4.get(new Long(5)).setSumm_plan(detailsForm5.getPlanY().floatValue());
              typeC4.get(new Long(5)).setSumm_fact(detailsForm5.getFactY().floatValue()); 
              typeC4.get(new Long(5)).setSumm_ek(detailsForm5.getFactYM().floatValue());
              typeC4.get(new Long(6)).setSumm_plan(detailsForm5.getPlanMA().floatValue());
              typeC4.get(new Long(6)).setSumm_fact(detailsForm5.getFactMA().floatValue()); 
              typeC4.get(new Long(6)).setSumm_ek(detailsForm5.getFactMAM().floatValue());
              typeC4.get(new Long(7)).setSumm_plan(detailsForm5.getPlanDA().floatValue());
              typeC4.get(new Long(7)).setSumm_fact(detailsForm5.getFactDA().floatValue()); 
              typeC4.get(new Long(7)).setSumm_ek(detailsForm5.getFactDAM().floatValue());
              
               
               
          
              
              detailsForm5.setCell4(new ArrayList(typeC4.values()));
              
              
              int g=0;
            if(detailsForm5.getIdGroup()!=null){g=detailsForm5.getIdGroup().intValue();}
            
               
            /*  if(g!=k){
                if(k!=-1){
            sumDi.setCell4(new ArrayList(typeCDi.values()));       
            reportDetailsForm5List.add(sumDi);}
           
                
                
                typeCDi = new Hashtable<Long, TypeTERForm3>();
              //  getTypeC(typeCDi,typeTERForm3All);
            for (TypeTER objectrepTERForm3 : typeTERForm3All) {
                TypeTERForm3 t3 = new TypeTERForm3();
                t3.setName(objectrepTERForm3.getName());
                typeCDi.put(objectrepTERForm3.getId(),t3);
            } 
        typeCDi.get(new Long(1)).setSumm_plan(0);
              typeCDi.get(new Long(1)).setSumm_fact(0); 
              typeCDi.get(new Long(1)).setSumm_ek(0);
              typeCDi.get(new Long(2)).setSumm_plan(0);
              typeCDi.get(new Long(2)).setSumm_fact(0); 
              typeCDi.get(new Long(2)).setSumm_ek(0);
              typeCDi.get(new Long(3)).setSumm_plan(0);
              typeCDi.get(new Long(3)).setSumm_fact(0); 
              typeCDi.get(new Long(3)).setSumm_ek(0);
              typeCDi.get(new Long(4)).setSumm_plan(0);
              typeCDi.get(new Long(4)).setSumm_fact(0); 
              typeCDi.get(new Long(4)).setSumm_ek(0);
              typeCDi.get(new Long(5)).setSumm_plan(0);
              typeCDi.get(new Long(5)).setSumm_fact(0); 
              typeCDi.get(new Long(5)).setSumm_ek(0);
              typeCDi.get(new Long(6)).setSumm_plan(0);
              typeCDi.get(new Long(6)).setSumm_fact(0); 
              typeCDi.get(new Long(6)).setSumm_ek(0);
              typeCDi.get(new Long(7)).setSumm_plan(0);
              typeCDi.get(new Long(7)).setSumm_fact(0); 
              typeCDi.get(new Long(7)).setSumm_ek(0);
          
          
            sumDi=new DetailsForm5();  
            k=g; 
            sumDi.setNamenp(detailsForm5.getNamenpGroup());
            sumDi.setPlanE(0);
            sumDi.setFactE(0);
            sumDi.setFactEM(0);
            sumDi.setFactS(0);
            sumDi.setFactSM(0);
            sumDi.setFactO(0);
            sumDi.setFactO2(0);
            sumDi.setFactY0(0);
            sumDi.setFactY2(0);
            sumDi.setIdF2(-1);
             
            } */
         /*   sumDi.setPlanE(sumDi.getPlanE().floatValue()+detailsForm5.getPlanE().floatValue());
            sumDi.setFactE(sumDi.getFactE().floatValue()+detailsForm5.getFactE().floatValue());
            sumDi.setFactEM(sumDi.getFactEM().floatValue()+detailsForm5.getFactEM().floatValue());
            sumDi.setFactS(sumDi.getFactS().floatValue()+detailsForm5.getFactS().floatValue());
            sumDi.setFactSM(sumDi.getFactSM().floatValue()+detailsForm5.getFactSM().floatValue());
            sumDi.setFactO(sumDi.getFactO().floatValue()+detailsForm5.getFactO().floatValue());
            sumDi.setFactO2(sumDi.getFactO2().floatValue()+detailsForm5.getFactO2().floatValue());
            sumDi.setFactY0(sumDi.getFactY0().floatValue()+detailsForm5.getFactY0().floatValue());
            sumDi.setFactY2(sumDi.getFactY2().floatValue()+detailsForm5.getFactY2().floatValue());  
              
              typeCDi.get(new Long(1)).setSumm_plan(typeCDi.get(new Long(1)).getSumm_plan()+typeC4.get(new Long(1)).getSumm_plan());
              typeCDi.get(new Long(1)).setSumm_fact(typeCDi.get(new Long(1)).getSumm_fact()+typeC4.get(new Long(1)).getSumm_fact()); 
              typeCDi.get(new Long(1)).setSumm_ek(typeCDi.get(new Long(1)).getSumm_ek()+typeC4.get(new Long(1)).getSumm_ek());
            typeCDi.get(new Long(2)).setSumm_plan(typeCDi.get(new Long(2)).getSumm_plan()+typeC4.get(new Long(2)).getSumm_plan());
              typeCDi.get(new Long(2)).setSumm_fact(typeCDi.get(new Long(2)).getSumm_fact()+typeC4.get(new Long(2)).getSumm_fact()); 
              typeCDi.get(new Long(2)).setSumm_ek(typeCDi.get(new Long(2)).getSumm_ek()+typeC4.get(new Long(2)).getSumm_ek());
            typeCDi.get(new Long(3)).setSumm_plan(typeCDi.get(new Long(3)).getSumm_plan()+typeC4.get(new Long(3)).getSumm_plan());
              typeCDi.get(new Long(3)).setSumm_fact(typeCDi.get(new Long(3)).getSumm_fact()+typeC4.get(new Long(3)).getSumm_fact()); 
              typeCDi.get(new Long(3)).setSumm_ek(typeCDi.get(new Long(3)).getSumm_ek()+typeC4.get(new Long(3)).getSumm_ek());
            typeCDi.get(new Long(4)).setSumm_plan(typeCDi.get(new Long(4)).getSumm_plan()+typeC4.get(new Long(4)).getSumm_plan());
              typeCDi.get(new Long(4)).setSumm_fact(typeCDi.get(new Long(4)).getSumm_fact()+typeC4.get(new Long(4)).getSumm_fact()); 
              typeCDi.get(new Long(4)).setSumm_ek(typeCDi.get(new Long(4)).getSumm_ek()+typeC4.get(new Long(4)).getSumm_ek());
            typeCDi.get(new Long(5)).setSumm_plan(typeCDi.get(new Long(5)).getSumm_plan()+typeC4.get(new Long(5)).getSumm_plan());
              typeCDi.get(new Long(5)).setSumm_fact(typeCDi.get(new Long(5)).getSumm_fact()+typeC4.get(new Long(5)).getSumm_fact()); 
              typeCDi.get(new Long(5)).setSumm_ek(typeCDi.get(new Long(5)).getSumm_ek()+typeC4.get(new Long(5)).getSumm_ek());
            typeCDi.get(new Long(6)).setSumm_plan(typeCDi.get(new Long(6)).getSumm_plan()+typeC4.get(new Long(6)).getSumm_plan());
              typeCDi.get(new Long(6)).setSumm_fact(typeCDi.get(new Long(6)).getSumm_fact()+typeC4.get(new Long(6)).getSumm_fact()); 
              typeCDi.get(new Long(6)).setSumm_ek(typeCDi.get(new Long(6)).getSumm_ek()+typeC4.get(new Long(6)).getSumm_ek());
            typeCDi.get(new Long(7)).setSumm_plan(typeCDi.get(new Long(7)).getSumm_plan()+typeC4.get(new Long(7)).getSumm_plan());
              typeCDi.get(new Long(7)).setSumm_fact(typeCDi.get(new Long(7)).getSumm_fact()+typeC4.get(new Long(7)).getSumm_fact()); 
              typeCDi.get(new Long(7)).setSumm_ek(typeCDi.get(new Long(7)).getSumm_ek()+typeC4.get(new Long(7)).getSumm_ek());
            
            sumDor.setPlanE(sumDor.getPlanE().floatValue()+detailsForm5.getPlanE().floatValue());
            sumDor.setFactE(sumDor.getFactE().floatValue()+detailsForm5.getFactE().floatValue());
            sumDor.setFactEM(sumDor.getFactEM().floatValue()+detailsForm5.getFactEM().floatValue());
            sumDor.setFactS(sumDor.getFactS().floatValue()+detailsForm5.getFactS().floatValue());
            sumDor.setFactSM(sumDor.getFactSM().floatValue()+detailsForm5.getFactSM().floatValue());
            sumDor.setFactO(sumDor.getFactO().floatValue()+detailsForm5.getFactO().floatValue());
            sumDor.setFactO2(sumDor.getFactO2().floatValue()+detailsForm5.getFactO2().floatValue());
            sumDor.setFactY0(sumDor.getFactY0().floatValue()+detailsForm5.getFactY0().floatValue());
            sumDor.setFactY2(sumDor.getFactY2().floatValue()+detailsForm5.getFactY2().floatValue());  
            
            
            typeCDor.get(new Long(1)).setSumm_plan(typeCDor.get(new Long(1)).getSumm_plan()+typeC4.get(new Long(1)).getSumm_plan());
              typeCDor.get(new Long(1)).setSumm_fact(typeCDor.get(new Long(1)).getSumm_fact()+typeC4.get(new Long(1)).getSumm_fact()); 
              typeCDor.get(new Long(1)).setSumm_ek(typeCDor.get(new Long(1)).getSumm_ek()+typeC4.get(new Long(1)).getSumm_ek());
            typeCDor.get(new Long(2)).setSumm_plan(typeCDor.get(new Long(2)).getSumm_plan()+typeC4.get(new Long(2)).getSumm_plan());
              typeCDor.get(new Long(2)).setSumm_fact(typeCDor.get(new Long(2)).getSumm_fact()+typeC4.get(new Long(2)).getSumm_fact()); 
              typeCDor.get(new Long(2)).setSumm_ek(typeCDor.get(new Long(2)).getSumm_ek()+typeC4.get(new Long(2)).getSumm_ek());
            typeCDor.get(new Long(3)).setSumm_plan(typeCDor.get(new Long(3)).getSumm_plan()+typeC4.get(new Long(3)).getSumm_plan());
              typeCDor.get(new Long(3)).setSumm_fact(typeCDor.get(new Long(3)).getSumm_fact()+typeC4.get(new Long(3)).getSumm_fact()); 
              typeCDor.get(new Long(3)).setSumm_ek(typeCDor.get(new Long(3)).getSumm_ek()+typeC4.get(new Long(3)).getSumm_ek());
            typeCDor.get(new Long(4)).setSumm_plan(typeCDor.get(new Long(4)).getSumm_plan()+typeC4.get(new Long(4)).getSumm_plan());
              typeCDor.get(new Long(4)).setSumm_fact(typeCDor.get(new Long(4)).getSumm_fact()+typeC4.get(new Long(4)).getSumm_fact()); 
              typeCDor.get(new Long(4)).setSumm_ek(typeCDor.get(new Long(4)).getSumm_ek()+typeC4.get(new Long(4)).getSumm_ek());
            typeCDor.get(new Long(5)).setSumm_plan(typeCDor.get(new Long(5)).getSumm_plan()+typeC4.get(new Long(5)).getSumm_plan());
              typeCDor.get(new Long(5)).setSumm_fact(typeCDor.get(new Long(5)).getSumm_fact()+typeC4.get(new Long(5)).getSumm_fact()); 
              typeCDor.get(new Long(5)).setSumm_ek(typeCDor.get(new Long(5)).getSumm_ek()+typeC4.get(new Long(5)).getSumm_ek());
            typeCDor.get(new Long(6)).setSumm_plan(typeCDor.get(new Long(6)).getSumm_plan()+typeC4.get(new Long(6)).getSumm_plan());
              typeCDor.get(new Long(6)).setSumm_fact(typeCDor.get(new Long(6)).getSumm_fact()+typeC4.get(new Long(6)).getSumm_fact()); 
              typeCDor.get(new Long(6)).setSumm_ek(typeCDor.get(new Long(6)).getSumm_ek()+typeC4.get(new Long(6)).getSumm_ek());
            typeCDor.get(new Long(7)).setSumm_plan(typeCDor.get(new Long(7)).getSumm_plan()+typeC4.get(new Long(7)).getSumm_plan());
              typeCDor.get(new Long(7)).setSumm_fact(typeCDor.get(new Long(7)).getSumm_fact()+typeC4.get(new Long(7)).getSumm_fact()); 
              typeCDor.get(new Long(7)).setSumm_ek(typeCDor.get(new Long(7)).getSumm_ek()+typeC4.get(new Long(7)).getSumm_ek());
            */
              
          /*  for ( int i=1; i<7;i++) {
              typeCDi.get(new Long(i)).setSumm_plan(typeCDi.get(new Long(i)).getSumm_plan()+typeC4.get(new Long(i)).getSumm_plan());
              typeCDi.get(new Long(i)).setSumm_fact(typeCDi.get(new Long(i)).getSumm_fact()+typeC4.get(new Long(i)).getSumm_fact()); 
              typeCDi.get(new Long(i)).setSumm_ek(typeCDi.get(new Long(i)).getSumm_ek()+typeC4.get(new Long(i)).getSumm_ek());
            }
            sumDor.setPlanE(sumDor.getPlanE().floatValue()+detailsForm5.getPlanE().floatValue());
            sumDor.setFactE(sumDor.getFactE().floatValue()+detailsForm5.getFactE().floatValue());
            sumDor.setFactEM(sumDor.getFactEM().floatValue()+detailsForm5.getFactEM().floatValue());
            sumDor.setFactS(sumDor.getFactS().floatValue()+detailsForm5.getFactS().floatValue());
            sumDor.setFactSM(sumDor.getFactSM().floatValue()+detailsForm5.getFactSM().floatValue());
            sumDor.setFactO(sumDor.getFactO().floatValue()+detailsForm5.getFactO().floatValue());
            sumDor.setFactO2(sumDor.getFactO2().floatValue()+detailsForm5.getFactO2().floatValue());
            sumDor.setFactY0(sumDor.getFactY0().floatValue()+detailsForm5.getFactY0().floatValue());
            sumDor.setFactY2(sumDor.getFactY2().floatValue()+detailsForm5.getFactY2().floatValue());  
            
             for ( int i=1; i<7;i++) {
            typeCDor.get(new Long(i)).setSumm_plan(typeCDor.get(new Long(i)).getSumm_plan()+typeC4.get(new Long(i)).getSumm_plan());
              typeCDor.get(new Long(i)).setSumm_fact(typeCDor.get(new Long(i)).getSumm_fact()+typeC4.get(new Long(i)).getSumm_fact()); 
              typeCDor.get(new Long(i)).setSumm_ek(typeCDor.get(new Long(i)).getSumm_ek()+typeC4.get(new Long(i)).getSumm_ek());
             }*/
              
              
              reportDetailsForm5List.add(detailsForm5);
              
          }
  //sumDor.setCell4(new ArrayList(typeCDor.values()));    
  //reportDetailsForm5List.add(sumDor);    
       
     
     
 
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
           // cdt.add(Calendar.DATE, -1);
            mes_new = mes_new + " - " + dateFormatter2.format(cdt.getTime());
            dayEndD = cdt.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dayEnd = dateFormatter.format(dayEndD);
            System.out.println("=====222dayEndD " + dayEnd);
            System.out.println("=====222mes_new" + mes_new);
        }

        Upd();

         }
    }

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
        SimpleDateFormat dateFormatter3 = new SimpleDateFormat("MM.yyyyг.");
        Row row = sheet.createRow(0);
        new createCell().createCell(wb, sheet, cellStyle_R, row, 0, 0, 0, 0, 12, "Форма 3 за " + mes_new);

        Row row1 = sheet.createRow(1);
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 12, "Результат проведения акции");
        Row row2 = sheet.createRow(2);
        new createCell().createCell(wb, sheet, cellStyle, row2, 2, 2, 0, 0, 12, "Отчет");

        Row row3 = sheet.createRow(3);
      //  new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 5, 0, 0, 0, "Дирекция, Служба");
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 0, 0, 2, "Сокращение потребления электоэнергии");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 1, 1, 1);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 2, 2, 2);
        //new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 3, 3, 3);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 3, 3, 6, "Сокращение потребления топливоэнергетических-ресурсов");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 4, 4, 4);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 5, 5, 5);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 6, 6, 6);
        //new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 7, 7, 7);

        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 7, 7, 8, "Сокращение выбросов загрязняющих веществ в атмосферу");

        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 8, 8, 8);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 9, 9, 10, "Озеленение территории");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 10, 10, 10);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 11, 11, 12, "Уборка территории");

        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 12, 12, 12);


        Row row4 = sheet.createRow(4);
      //  new createCellNull().createCellNull(sheet, cellStyle2, row4, 4, 4, 0, 0, 0);

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 0, 0, 1, "кВт");

        new createCellNull().createCellNull(sheet, cellStyle2, row4, 4, 4, 1, 1, 1);
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 2, 2, 2, "Экономичексий эффект, тыс. руб.");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 3, 3, 3, "Виды ТЭР");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 4, 4, 5, "тонн");
        new createCellNull().createCellNull(sheet, cellStyle2, row4, 4, 4, 5, 5, 5);

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 6, 6, 6, "Экономичексий эффект, тыс. руб.");


        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 7, 7, 7, "тонн");


        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 8, 8,8, "Экономичексий эффект, тыс. руб.");


        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 9, 9, 9, "Деревья и кустарники, шт.");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 10, 10, 10, "Территория, м.кв");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 11, 11, 11, "Территории, м.кв");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 12, 12, 12, "Количество отходов т");



        Row row5 = sheet.createRow(5);

       // new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 0, 0, 0);
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 0, 0, 0, "план");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 1, 1, 1, "факт");

        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 2, 2, 2);
        //new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 3, 3, 3);
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 4, 4, 4, "план");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 5, 5, 5, "факт");
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 7, 7, 7);//
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 8, 8, 8);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 9, 9, 9);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 10, 10, 10);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 11, 11, 11);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 12, 12, 12);
        
        HSSFRow row6 = sheet.createRow(6);
        for (int i1 = 0; i1 < 13; i1++) {
            new createCell().createCell(wb, sheet, cellStyle1, row6, 6, 6, i1, i1, i1, String.valueOf(i1 + 1));
        }

        int jk = 6;

       







        for (int i = 0; i < reportDetailsForm5List.size(); i++) {
            DetailsForm5 reportDetailsForm3 = reportDetailsForm5List.get(i);
            int j = jk;
            HSSFRow header8 = sheet.createRow(j);
            int j1 = j;
            
            if(reportDetailsForm3!=null){
            
            for (int i1 = 0; i1 < reportDetailsForm3.getCell4().size(); i1++) {
                j1 = i1 + j;

                TypeTERForm3 typeTERForm3Form3 = reportDetailsForm3.getCell4().get(i1);
                HSSFRow header81 = sheet.createRow(j1);
                for (int k = 0; k < 3; k++) {
                    new createCellNull().createCellNull(sheet, cellStyle2, header81, j1, j1, k, k, k);
                }

                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 3, 3, 3, String.valueOf(typeTERForm3Form3.getName()));

                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 4, 4, 4, f.formatForEXCEL(typeTERForm3Form3.getSumm_plan()));
                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 5, 5, 5, f.formatForEXCEL(typeTERForm3Form3.getSumm_fact()));
                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 6, 6, 6, f.formatMoneyForEXCEL(typeTERForm3Form3.getSumm_ek()));


                for (int k = 7; k < 13; k++) {
                    new createCellNull().createCellNull(sheet, cellStyle2, header81, j1, j1, k, k, k);
                }
                //jk=j1;
            }

            


            //new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 0, 0, 0, String.valueOf(reportDetailsForm3.getNamenp()));

            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 0, 0, 0, f.formatForEXCEL(reportDetailsForm3.getPlanE().floatValue()));


            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 1, 1, 1, f.formatForEXCEL(reportDetailsForm3.getFactE().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 2, 2, 2, f.formatMoneyForEXCEL(reportDetailsForm3.getFactEM().floatValue()));

            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 7, 7, 7, f.formatForEXCEL(reportDetailsForm3.getFactS().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 8, 8, 8, f.formatMoneyForEXCEL(reportDetailsForm3.getFactSM().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 9, 9, 9, f.formatForEXCEL(reportDetailsForm3.getFactO().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 10, 10, 10, f.formatForEXCEL(reportDetailsForm3.getFactO2().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 11, 11, 11, f.formatForEXCEL(reportDetailsForm3.getFactY0().floatValue()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 12, 12, 12, f.formatForEXCEL(reportDetailsForm3.getFactY2().floatValue()));



            jk = jk + reportDetailsForm3.getCell4().size();
}

        }



       







        for (int i1 = 0; i1 < 13; i1++) {
            sheet.autoSizeColumn(i1, true);

        }


        wb.removeSheetAt(0);

    }

    /**
     * Creates a new instance of FormFivePageBean
     */
    
}

