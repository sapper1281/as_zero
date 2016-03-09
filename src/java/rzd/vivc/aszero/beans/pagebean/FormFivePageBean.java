package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.apache.poi.ss.usermodel.Row;
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.classes.excel.createCellNull;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.DepartmentGroup;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsForm2;
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
public class FormFivePageBean extends BasePage implements Serializable {

    public FormFivePageBean() {
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
      
      
    public String getMes_new() {
        return mes_new;
    }

    public void setMes_new(String mes_new) {
        this.mes_new = mes_new;
    }
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private List<ReportDetailsForm3> reportDetailsForm3List;
    //  private ReportDetailsForm3 reportDetailsForm3;
    //private Department dep;

    public List<ReportDetailsForm3> getReportDetailsForm3List() {
        return reportDetailsForm3List;
    }

    public void setReportDetailsForm3List(List<ReportDetailsForm3> reportDetailsForm3List) {
        this.reportDetailsForm3List = reportDetailsForm3List;
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

    public void Upd() {

        System.out.println(new Date());
        reportDetailsForm3List = new ArrayList<ReportDetailsForm3>();


        int rail = (int) session.getUser().getDepartmentPower();

        List<Integer> deplistfist = new ArrayList<Integer>();
        deplistfist.add(rail);

        /*первый элемент idVp=1*/
       // List<Department> depListFist = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALL(deplistfist, mes, year);
 List<Department> depListFist = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALLDate(deplistfist, dayBeginD, dayEndD);
      


        List<Integer> deplist = new ArrayList<Integer>();
        for (Department department : depListFist) {
            deplist.add(department.getIdNp());
        }
        if (deplist.size() > 0) {
        //    List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALL(deplist, mes, year);
             List<Department> depList = (new DepartmentRepository()).getActiveListDepartmentsWithDetailsALLDate(deplist, dayBeginD, dayEndD);
      
            System.out.println("depList " + depList);


            String f = "(";
            for (Department department : depListFist) {
                f = f + department.getId() + ",";
            }
            f = f + "-1)";

            //new

            ReportRepository repRep = new ReportRepository();
            List<Report> rep_all = repRep.getListWithDetailsIdNpMonthALLMyWithCostsDate(f,dayBeginD, dayEndD, rail);
            
           // List<Report> rep_all = repRep.getListWithDetailsIdNpMonthALLMyDate(f, dayBeginD, dayEndD);
          
            //List<Report> rep_all = repRep.getListWithDetailsIdNpMonthALLMy(f, mes, year);
            System.out.println("rep_all " + rep_all);
            List<Report> repForm2 = repRep.getListWithDetailsForm2IdNpMonthALLMyDate(f, dayBeginD, dayEndD);
            System.out.println("repForm2 " + repForm2);






            /*по дороге в целом*/
            ReportDetailsForm3 reportDetailsFormAll = new ReportDetailsForm3();
            Department depr = new Department();
            depr.setNameNp("ИТОГО ПОЛИГОН");
            reportDetailsFormAll.setDep(depr);
            
            
          
            
            
            /*список видов ресурсов из бд*/
            List<TypeTER> typeTERForm3All = ((new TypeTERRepository()).getAll(TypeTER.class));
            /*список видов ресурсов в форме*/
            List<TypeTERForm3> typeTERAll = new ArrayList<TypeTERForm3>();


            
               /*список ДИ*/  
        HashMap reportListDetailsForm = new HashMap();
        List<DepartmentGroup> depGroup = (new DepartmentRepository()).getActiveListDepartmentGroup(); 
            for (DepartmentGroup departmentGroup : depGroup) {
                ReportDetailsForm3 reportDetailsF3 = new ReportDetailsForm3();
                Department depr3 = new Department();
                depr3.setNameNp(departmentGroup.getNameNp());
                reportDetailsF3.setDep(depr3);
                
                List<TypeTERForm3> typeC4 = new ArrayList<TypeTERForm3>();
                
                for (TypeTER objectrepTERForm3 : typeTERForm3All) {
                TypeTERForm3 t3 = new TypeTERForm3();
                t3.setId(objectrepTERForm3.getId());
                t3.setName(objectrepTERForm3.getName());
                typeC4.add(t3);
            }
                
                reportDetailsF3.setCell4(typeC4);
                
                
                reportListDetailsForm.put(departmentGroup.getId(), reportDetailsF3);
           
            }
            
            

/*перенос ресурсов из бд в форму по дороге*/
            for (TypeTER objectrepTERForm3 : typeTERForm3All) {
                TypeTERForm3 t3 = new TypeTERForm3();
                t3.setId(objectrepTERForm3.getId());
                t3.setName(objectrepTERForm3.getName());
                typeTERAll.add(t3);
            }
            /*-------------------*/

long group = 0;
           
            
            boolean f1=true;

            for (Department department : depListFist) {


                ReportDetailsForm3 reportDetailsForm3 = new ReportDetailsForm3();

                reportDetailsForm3.setDep(department);

                List<TypeTER> typeTERForm3 = ((new TypeTERRepository()).getAll(TypeTER.class));
                List<TypeTERForm3> typeTER = new ArrayList<TypeTERForm3>();



/*перенос ресурсов из бд в форму по службе*/
                for (TypeTER objectrepTERForm3 : typeTERForm3) {
                    TypeTERForm3 t3 = new TypeTERForm3();
                    t3.setId(objectrepTERForm3.getId());
                    t3.setName(objectrepTERForm3.getName());
                    typeTER.add(t3);
                }






                for (Department object : depList) {

                    if (department.getIdNp() == object.getIdVp()) {


                        //  ReportRepository repRep = new ReportRepository();

                        // List<Report> rep = repRep.getListWithDetailsIdNpMonth(object.getIdNp(), mes, year);

                        // System.out.println(rep);
                        //   List<Report> repForm2 = repRep.getListWithDetailsForm2IdNpMonth(object.getIdNp(), mes, year);
                        //  System.out.println(repForm2);

                        for (Report repR : repForm2) {
                            if (repR.getDepartment().getId() == department.getId()) {

                                reportDetailsForm3.setRepId(repR.getId());
                            }
                        }



                        if (rep_all.size() > 0) {
                            for (Report objectrep : rep_all) {
                                if (objectrep.getDepartment().getId() == object.getId()) {

                                    for (ReportDetails objectrepDetails : objectrep.getDetails()) {



                                        for (TypeTERForm3 objectrepTER : typeTER) {



                                            if (((TypeTERForm3) objectrepTER).getId() == 1/*газ*/) {
                                                if ((objectrepDetails.getResource().getId() == 1)) {
                                                    ((TypeTERForm3) objectrepTER).setSumm_plan(objectrepDetails.getPlan_col() + ((TypeTERForm3) objectrepTER).getSumm_plan());
                                                    if (objectrepDetails.getDt_inputFact() != null) {
                                                        ((TypeTERForm3) objectrepTER).setSumm_fact(objectrepDetails.getFact_col() + ((TypeTERForm3) objectrepTER).getSumm_fact());
                                                        ((TypeTERForm3) objectrepTER).setSumm_ek(objectrepDetails.getFact_money() + ((TypeTERForm3) objectrepTER).getSumm_ek());
                                                    }
                                                }
                                            }

                                            if (((TypeTERForm3) objectrepTER).getId() == 2/*диз.топ*/) {
                                                if ((((ReportDetails) objectrepDetails).getResource().getId() == 17)) {
                                                    ((TypeTERForm3) objectrepTER).setSumm_plan(objectrepDetails.getPlan_col() + ((TypeTERForm3) objectrepTER).getSumm_plan());
                                                    if (objectrepDetails.getDt_inputFact() != null) {
                                                        ((TypeTERForm3) objectrepTER).setSumm_fact(objectrepDetails.getFact_col() + ((TypeTERForm3) objectrepTER).getSumm_fact());
                                                        ((TypeTERForm3) objectrepTER).setSumm_ek(objectrepDetails.getFact_money() + ((TypeTERForm3) objectrepTER).getSumm_ek());
                                                    }
                                                }
                                            }

                                            if (((TypeTERForm3) objectrepTER).getId() == 3/*бензин*/) {
                                                if ((((ReportDetails) objectrepDetails).getResource().getId() == 2)) {
                                                    ((TypeTERForm3) objectrepTER).setSumm_plan(objectrepDetails.getPlan_col() + ((TypeTERForm3) objectrepTER).getSumm_plan());
                                                    if (objectrepDetails.getDt_inputFact() != null) {
                                                        ((TypeTERForm3) objectrepTER).setSumm_fact(objectrepDetails.getFact_col() + ((TypeTERForm3) objectrepTER).getSumm_fact());
                                                        ((TypeTERForm3) objectrepTER).setSumm_ek(objectrepDetails.getFact_money() + ((TypeTERForm3) objectrepTER).getSumm_ek());
                                                    }
                                                }
                                            }

                                            if (((TypeTERForm3) objectrepTER).getId() == 4/*мазут*/) {
                                                if ((((ReportDetails) objectrepDetails).getResource().getId() == 3)) {
                                                    ((TypeTERForm3) objectrepTER).setSumm_plan(((ReportDetails) objectrepDetails).getPlan_col() + ((TypeTERForm3) objectrepTER).getSumm_plan());
                                                    if (objectrepDetails.getDt_inputFact() != null) {
                                                        ((TypeTERForm3) objectrepTER).setSumm_fact(((ReportDetails) objectrepDetails).getFact_col() + ((TypeTERForm3) objectrepTER).getSumm_fact());
                                                        ((TypeTERForm3) objectrepTER).setSumm_ek(((ReportDetails) objectrepDetails).getFact_money() + ((TypeTERForm3) objectrepTER).getSumm_ek());
                                                    }
                                                }
                                            }
                                            if (((TypeTERForm3) objectrepTER).getId() == 5/*уголь*/) {/*4 5*/
                                                if ((((ReportDetails) objectrepDetails).getResource().getId() == 4) || (((ReportDetails) objectrepDetails).getResource().getId() == 5)) {
                                                    ((TypeTERForm3) objectrepTER).setSumm_plan(((ReportDetails) objectrepDetails).getPlan_col() + ((TypeTERForm3) objectrepTER).getSumm_plan());
                                                    if (objectrepDetails.getDt_inputFact() != null) {
                                                        ((TypeTERForm3) objectrepTER).setSumm_fact(((ReportDetails) objectrepDetails).getFact_col() + ((TypeTERForm3) objectrepTER).getSumm_fact());
                                                        ((TypeTERForm3) objectrepTER).setSumm_ek(((ReportDetails) objectrepDetails).getFact_money() + ((TypeTERForm3) objectrepTER).getSumm_ek());
                                                    }
                                                }
                                            }
                                            if (((TypeTERForm3) objectrepTER).getId() == 6/*масла*/) {
                                                if ((((ReportDetails) objectrepDetails).getResource().getId() == 6)) {
                                                    ((TypeTERForm3) objectrepTER).setSumm_plan(((ReportDetails) objectrepDetails).getPlan_col() + ((TypeTERForm3) objectrepTER).getSumm_plan());
                                                    if (objectrepDetails.getDt_inputFact() != null) {
                                                        ((TypeTERForm3) objectrepTER).setSumm_fact(((ReportDetails) objectrepDetails).getFact_col() + ((TypeTERForm3) objectrepTER).getSumm_fact());
                                                        ((TypeTERForm3) objectrepTER).setSumm_ek(((ReportDetails) objectrepDetails).getFact_money() + ((TypeTERForm3) objectrepTER).getSumm_ek());
                                                    }
                                                }
                                            }
                                            if (((TypeTERForm3) objectrepTER).getId() == 7/*другие*/) {
                                                if ((((ReportDetails) objectrepDetails).getResource().getId() == 7)) {
                                                    ((TypeTERForm3) objectrepTER).setSumm_plan(((ReportDetails) objectrepDetails).getPlan_col() + ((TypeTERForm3) objectrepTER).getSumm_plan());
                                                    if (objectrepDetails.getDt_inputFact() != null) {
                                                        ((TypeTERForm3) objectrepTER).setSumm_fact(((ReportDetails) objectrepDetails).getFact_col() + ((TypeTERForm3) objectrepTER).getSumm_fact());
                                                        ((TypeTERForm3) objectrepTER).setSumm_ek(((ReportDetails) objectrepDetails).getFact_money() + ((TypeTERForm3) objectrepTER).getSumm_ek());
                                                    }
                                                }
                                            }



                                            /*if (objectrepTER.getId() == objectrepDetails.getResource().getId()) {
                                             objectrepTER.setSumm_plan(objectrepDetails.getPlan_col() + objectrepTER.getSumm_plan());
                                             if(objectrepDetails.getResource().getId()==13&& objectrepDetails.getDt_inputFact()!=null){
                                             objectrepTER.setSumm_fact(objectrepDetails.getFact_col() + objectrepTER.getSumm_fact());}
                                             objectrepTER.setSumm_ek(objectrepDetails.getFact_money() + objectrepTER.getSumm_ek());
                                             }*/

                                        }


                                        if ((objectrepDetails.getResource().getId() == 13)) {
                                            reportDetailsForm3.setCell1(objectrepDetails.getPlan_col() + reportDetailsForm3.getCell1());
                                            if (objectrepDetails.getDt_inputFact() != null) {
                                                reportDetailsForm3.setCell2(objectrepDetails.getFact_col() + reportDetailsForm3.getCell2());
                                                reportDetailsForm3.setCell3(objectrepDetails.getFact_money() + reportDetailsForm3.getCell3());
                                            }
                                        }
                                        if ((objectrepDetails.getResource().getId() == 12 && objectrepDetails.getDt_inputFact() != null)) {
                                            reportDetailsForm3.setCell8(objectrepDetails.getFact_col() + reportDetailsForm3.getCell8());
                                            reportDetailsForm3.setCell9(objectrepDetails.getFact_money() + reportDetailsForm3.getCell9());
                                        }
                                        if ((objectrepDetails.getResource().getId() == 16 && objectrepDetails.getDt_inputFact() != null)) {
                                            reportDetailsForm3.setCell10(objectrepDetails.getFact_col() + reportDetailsForm3.getCell10());
                                        }
                                        if ((objectrepDetails.getResource().getId() == 15 && objectrepDetails.getDt_inputFact() != null)) {
                                            reportDetailsForm3.setCell11(objectrepDetails.getFact_col() + reportDetailsForm3.getCell11());
                                        }

                                        if ((objectrepDetails.getResource().getId() == 14 && objectrepDetails.getDt_inputFact() != null)) {
                                            reportDetailsForm3.setCell12(objectrepDetails.getFact_col() + reportDetailsForm3.getCell12());
                                        }
                                        if ((objectrepDetails.getResource().getId() == 11 && objectrepDetails.getDt_inputFact() != null)) {
                                            reportDetailsForm3.setCell13(objectrepDetails.getFact_col() + reportDetailsForm3.getCell13());
                                        }
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
                       
                            ReportDetailsForm3 o = (ReportDetailsForm3) reportListDetailsForm.get(group);
                            reportDetailsForm3List.add(o);
                        

                        group = department.getDepartmentGroup_id().getId();

                       // reportListDetailsForm.clear();
                    }
                }
                if (department.getDepartmentGroup_id() == null && f1) {
                        f1=!f1; 
                            ReportDetailsForm3 o = (ReportDetailsForm3) reportListDetailsForm.get(group);
                            reportDetailsForm3List.add(o);
                    
                }
                
                
                
                
             /*заполнение ди*/   
                if(department.getDepartmentGroup_id()!=null){
                    
                    
                ReportDetailsForm3 rf=(ReportDetailsForm3)reportListDetailsForm.get(department.getDepartmentGroup_id().getId());
                rf.setCell1(reportDetailsForm3.getCell1() + rf.getCell1());
                rf.setCell2(reportDetailsForm3.getCell2() + rf.getCell2());
                rf.setCell3(reportDetailsForm3.getCell3() + rf.getCell3());
                rf.setCell8(reportDetailsForm3.getCell8() + rf.getCell8());
                rf.setCell9(reportDetailsForm3.getCell9() + rf.getCell9());
                rf.setCell10(reportDetailsForm3.getCell10() + rf.getCell10());
                rf.setCell11(reportDetailsForm3.getCell11() + rf.getCell11());
                rf.setCell12(reportDetailsForm3.getCell12() + rf.getCell12());
                rf.setCell13(reportDetailsForm3.getCell13() + rf.getCell13());
                
                List <TypeTERForm3> s= rf.getCell4() ;
                
                
                for (TypeTERForm3 objectrepTERALL1 : s) {
                    for (TypeTERForm3 objectrepTER1 : typeTER) {


                        if (objectrepTER1.getId() == objectrepTERALL1.getId()) {
                            objectrepTERALL1.setSumm_plan(objectrepTER1.getSumm_plan() + objectrepTERALL1.getSumm_plan());
                            objectrepTERALL1.setSumm_fact(objectrepTER1.getSumm_fact() + objectrepTERALL1.getSumm_fact());
                            objectrepTERALL1.setSumm_ek(objectrepTER1.getSumm_ek() + objectrepTERALL1.getSumm_ek());
                        }

                    }
                }
                
                
                 rf.setCell4(s);
                
                
                        
                reportListDetailsForm.put(department.getDepartmentGroup_id().getId(), rf);             
                        
                }
                
                /*
        HashMap reportListDetailsForm = new HashMap();
        List<DepartmentGroup> depGroup = (new DepartmentRepository()).getActiveListDepartmentGroup(); 
            for (DepartmentGroup departmentGroup : depGroup) {
                ReportDetailsForm3 reportDetailsF3 = new ReportDetailsForm3();
                Department depr3 = new Department();
                depr3.setNameNp(departmentGroup.getNameNp());
                reportDetailsF3.setDep(depr);
                reportListDetailsForm.put(departmentGroup.getId(), reportDetailsF3);
           }*/
                
                
                

                for (TypeTERForm3 objectrepTERALL1 : typeTERAll) {
                    for (TypeTERForm3 objectrepTER1 : typeTER) {


                        if (objectrepTER1.getId() == objectrepTERALL1.getId()) {
                            objectrepTERALL1.setSumm_plan(objectrepTER1.getSumm_plan() + objectrepTERALL1.getSumm_plan());
                            objectrepTERALL1.setSumm_fact(objectrepTER1.getSumm_fact() + objectrepTERALL1.getSumm_fact());
                            objectrepTERALL1.setSumm_ek(objectrepTER1.getSumm_ek() + objectrepTERALL1.getSumm_ek());
                        }

                    }
                }



                reportDetailsForm3.setCell4(typeTER);

                reportDetailsFormAll.setCell1(reportDetailsForm3.getCell1() + reportDetailsFormAll.getCell1());
                reportDetailsFormAll.setCell2(reportDetailsForm3.getCell2() + reportDetailsFormAll.getCell2());
                reportDetailsFormAll.setCell3(reportDetailsForm3.getCell3() + reportDetailsFormAll.getCell3());
                reportDetailsFormAll.setCell8(reportDetailsForm3.getCell8() + reportDetailsFormAll.getCell8());
                reportDetailsFormAll.setCell9(reportDetailsForm3.getCell9() + reportDetailsFormAll.getCell9());
                reportDetailsFormAll.setCell10(reportDetailsForm3.getCell10() + reportDetailsFormAll.getCell10());
                reportDetailsFormAll.setCell11(reportDetailsForm3.getCell11() + reportDetailsFormAll.getCell11());
                reportDetailsFormAll.setCell12(reportDetailsForm3.getCell12() + reportDetailsFormAll.getCell12());
                reportDetailsFormAll.setCell13(reportDetailsForm3.getCell13() + reportDetailsFormAll.getCell13());



                reportDetailsForm3List.add(reportDetailsForm3);
            }
            
     /*       
            Set keys = reportListDetailsForm.keySet();
Iterator keysIterator = keys.iterator();
while( keysIterator.hasNext() )
{
Object key = keysIterator.next();
ReportDetailsForm3 o = (ReportDetailsForm3)reportListDetailsForm.get(key);
 reportDetailsForm3List.add(o);
}
           */ 
            
            
            System.out.println(typeTERAll);
            reportDetailsFormAll.setCell4(typeTERAll);
            reportDetailsForm3List.add(reportDetailsFormAll);



        }

        System.out.println(new Date());
    }

    @PostConstruct
    public void Init() {
  System.out.println("=====333 ");
        //dep=(new DepartmentRepository()).get(session.getUser().getDepartmentID());
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
      
//         dep=(new DepartmentRepository()).get(session.getUser().getDepartmentID());
      /*  if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("mes")) {
            mes_new = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes") + "."
                    + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year");

            mes = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes"));
            year = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year"));
        } else {
            mes_new = String.valueOf(new Date().getMonth() + 1) + "." + String.valueOf(new Date().getYear() + 1900);
            mes = new Date().getMonth() + 1;
            year = new Date().getYear() + 1900;
        }*/

        Upd();


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
        new createCell().createCell(wb, sheet, cellStyle_R, row, 0, 0, 0, 0, 13, "Форма 5 за " + mes_new);

        Row row1 = sheet.createRow(1);
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 13, "Результат проведения акции");
        Row row2 = sheet.createRow(2);
        new createCell().createCell(wb, sheet, cellStyle, row2, 2, 2, 0, 0, 13, "Отчет");

        Row row3 = sheet.createRow(3);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 5, 0, 0, 0, "Дирекция, Служба");
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 1, 1, 3, "Сокращение потребления электоэнергии");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 2, 2, 2);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 3, 3, 3);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 4, 4, 7, "Сокращение потребления топливоэнергетических-ресурсов");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 5, 5, 5);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 6, 6, 6);
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 7, 7, 7);

        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 8, 8, 9, "Сокращение выбросов загрязняющих веществ в атмосферу");

        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 9, 9, 9);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 10, 10, 11, "Озеленение территории");
        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 11, 11, 11);
        new createCell().createCell(wb, sheet, cellStyle2, row3, 3, 3, 12, 12, 13, "Уборка территории");

        new createCellNull().createCellNull(sheet, cellStyle2, row3, 3, 3, 13, 13, 13);


        Row row4 = sheet.createRow(4);
        new createCellNull().createCellNull(sheet, cellStyle2, row4, 4, 4, 0, 0, 0);

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 1, 1, 2, "кВт");

        new createCellNull().createCellNull(sheet, cellStyle2, row4, 4, 4, 2, 2, 2);
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 3, 3, 3, "Экономичексий эффект, тыс. руб.");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 4, 4, 4, "Виды ТЭР");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 5, 5, 6, "тонн");
        new createCellNull().createCellNull(sheet, cellStyle2, row4, 4, 4, 6, 6, 6);

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 7, 7, 7, "Экономичексий эффект, тыс. руб.");


        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 8, 8, 8, "тонн");


        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 9, 9, 9, "Экономичексий эффект, тыс. руб.");


        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 10, 10, 10, "Деревья и кустарники, шт.");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 11, 11, 11, "Территория, м.кв");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 12, 12, 12, "Территории, м.кв");

        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 13, 13, 13, "Количество отходов т");



        Row row5 = sheet.createRow(5);

        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 0, 0, 0);
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 1, 1, 1, "план");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 2, 2, 2, "факт");

        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 3, 3, 3);
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 5, 5, 5, "план");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 6, 6, 6, "факт");
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 8, 8, 8);//
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 9, 9, 9);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 10, 10, 10);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 11, 11, 11);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 12, 12, 12);
        new createCellNull().createCellNull(sheet, cellStyle2, row5, 5, 5, 13, 13, 13);

        HSSFRow row6 = sheet.createRow(6);
        for (int i1 = 0; i1 < 14; i1++) {
            new createCell().createCell(wb, sheet, cellStyle1, row6, 6, 6, i1, i1, i1, String.valueOf(i1 + 1));
        }

        int jk = 7;

        /* Iterator itrtypeTER = reportDetailsForm3List.iterator();
         while (itrtypeTER.hasNext()) {
         Object objectrepTER = itrtypeTER.next();
         if (objectrepTER instanceof ReportDetailsForm3) {
                        
         ReportDetailsForm3 repDetailsForm3=(ReportDetailsForm3)objectrepTER;
         */









        for (int i = 0; i < reportDetailsForm3List.size(); i++) {
            ReportDetailsForm3 reportDetailsForm3 = reportDetailsForm3List.get(i);
            int j = jk;
            HSSFRow header8 = sheet.createRow(j);
            int j1 = j;
            
            if(reportDetailsForm3!=null){
            
            for (int i1 = 0; i1 < reportDetailsForm3.getCell4().size(); i1++) {
                j1 = i1 + j;

                TypeTERForm3 typeTERForm3Form3 = reportDetailsForm3.getCell4().get(i1);
                HSSFRow header81 = sheet.createRow(j1);
                for (int k = 0; k < 4; k++) {
                    new createCellNull().createCellNull(sheet, cellStyle2, header81, j1, j1, k, k, k);
                }

                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 4, 4, 4, String.valueOf(typeTERForm3Form3.getName()));

                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 5, 5, 5, f.formatForEXCEL(typeTERForm3Form3.getSumm_plan()));
                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 6, 6, 6, f.formatForEXCEL(typeTERForm3Form3.getSumm_fact()));
                new createCell().createCell(wb, sheet, cellStyle1, header81, j1, j1, 7, 7, 7, f.formatMoneyForEXCEL(typeTERForm3Form3.getSumm_ek()));


                for (int k = 8; k < 14; k++) {
                    new createCellNull().createCellNull(sheet, cellStyle2, header81, j1, j1, k, k, k);
                }
                //jk=j1;
            }

            


            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 0, 0, 0, String.valueOf(reportDetailsForm3.getDep().getNameNp()));

            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 1, 1, 1, f.formatForEXCEL(reportDetailsForm3.getCell1()));


            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 2, 2, 2, f.formatForEXCEL(reportDetailsForm3.getCell2()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 3, 3, 3, f.formatMoneyForEXCEL(reportDetailsForm3.getCell3()));

            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 8, 8, 8, f.formatForEXCEL(reportDetailsForm3.getCell8()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 9, 9, 9, f.formatMoneyForEXCEL(reportDetailsForm3.getCell9()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 10, 10, 10, f.formatForEXCEL(reportDetailsForm3.getCell10()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 11, 11, 11, f.formatForEXCEL(reportDetailsForm3.getCell11()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 12, 12, 12, f.formatForEXCEL(reportDetailsForm3.getCell12()));
            new createCell().createCell(wb, sheet, cellStyle1, header8, jk, jk + reportDetailsForm3.getCell4().size() - 1, 13, 13, 13, f.formatForEXCEL(reportDetailsForm3.getCell13()));



            jk = jk + reportDetailsForm3.getCell4().size();
}

        }



        /* int j = 4;
         for (int i = 0; i < repForm2.getDetailsForm2().size(); i++) {
         j = i + 4;
         HSSFRow header8 = sheet.createRow(j);
         ReportDetailsForm2 repDetailsForm2 = repForm2.getDetailsForm2().get(i);
         */







        for (int i1 = 0; i1 < 14; i1++) {
            sheet.autoSizeColumn(i1, true);

        }


        wb.removeSheetAt(0);

    }

    /**
     * Creates a new instance of FormFivePageBean
     */
    
}
