/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
//import org.primefaces.model.chart.Axis;
//import org.primefaces.model.chart.AxisType;
//import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import rzd.vivc.aszero.dto.FormGraf;
import rzd.vivc.aszero.repository.ReportRepository;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class GrafPageBean {
  
    
    //private BarChartModel animatedModel2;
    private Number dd=0;

    public Number getDd() {
        return dd;
    }

    public void setDd(Number dd) {
        this.dd = dd;
    }
    
    
   /* public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }
    
    
     
    private void createAnimatedModels() {
        
         
        animatedModel2 = initBarModel();
        animatedModel2.setTitle("2014");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        animatedModel2.setBarMargin(20);
        Axis yAxis = animatedModel2.getAxis(AxisType.Y);
        //yAxis.setMin(0);
       // yAxis.setMax(2);
        yAxis.setLabel("Дней сэкономлено");
        Axis xAxis = animatedModel2.getAxis(AxisType.X);
        xAxis.setLabel("Номер месяца");
    }
     
    
    
    
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Воздух дни");
        
        for (FormGraf formGraf : reportFormGraf) {
            boys.set(formGraf.getMonth1(),formGraf.getD() );
            dd=dd.floatValue()+formGraf.getD().floatValue();
        }
        
        
       
 
        
 
        model.addSeries(boys);
       
         
        return model;
    }
    
    
    
    
    
    
    */
    
    
  private String mes_new;
   // private int mes;
   // private int year;
 private String dayBegin;
      private String dayEnd;
      private Date dayBeginD;
      private Date dayEndD;
private List<FormGraf> reportFormGraf;

    public List<FormGraf> getReportFormGraf() {
        return reportFormGraf;
    }

    public void setReportFormGraf(List<FormGraf> reportFormGraf) {
        this.reportFormGraf = reportFormGraf;
    }





    public String getMes_new() {
        return mes_new;
    }

    public void setMes_new(String mes_new) {
        this.mes_new = mes_new;
    }
     
    
       public void Upd() {
       
       reportFormGraf = new ArrayList<FormGraf>();
      
       reportFormGraf=(new ReportRepository()).getGraf(94, dayBeginD, dayEndD);
       
      // createAnimatedModels();
       }
       
    
    
    /**
     * Creates a new instance of GrafPageBean
     */
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
}
