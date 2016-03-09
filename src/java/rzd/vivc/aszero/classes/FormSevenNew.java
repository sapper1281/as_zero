
package rzd.vivc.aszero.classes;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author apopovkin
 */
public class FormSevenNew implements Serializable{
    String col0;
    Number   idRep;
    String   activity; 
  String   addres; 
  String   responsible; 
  String   powerSource; 
  String   addressOfObject;
  String   type;
  Number     num;
  Number  askue;
  
 String askueYesOrNot;
  Number   idDep;  
  String   depNameNP; 
  
  Number   depPerIdDep;  
  String   depPerNameNP;
  
  
  Number   idRes;  
  String   resName;
  Number    plan_col;
  Number    fact_col;
Object  dt_inputFact;
 float    fact_plan_col_out;
String  fact_plan_col;

String mas;

    public String getMas() {
        return mas;
    }

    public void setMas(String mas) {
        this.mas = mas;
    }



    public String getCol0() {
        return col0;
    }

    public void setCol0(String col0) {
        this.col0 = col0;
    }

 
 
    public long getIdRep() {
        return idRep.longValue() ;
    }

    public void setIdRep(Number idRep) {
        this.idRep = idRep;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(String powerSource) {
        this.powerSource = powerSource;
    }

    public String getAddressOfObject() {
        return addressOfObject;
    }

    public void setAddressOfObject(String addressOfObject) {
        this.addressOfObject = addressOfObject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getNum() {
        if(num!=null)
        return num.longValue();
        else
        return 0;    
    }

    public void setNum(Number num) {
        this.num = num;
    }

    public boolean isAskue() {
        if(askue!=null)
        return askue.shortValue()==1;
        else
        return false;    
    }

    public void setAskue(Number askue) {
        this.askue = askue;
    }

    public String getAskueYesOrNot() {
       
        return askueYesOrNot;
      
    }

    public void setAskueYesOrNot(boolean askueYesOrNot) {
        if(askueYesOrNot)
        this.askueYesOrNot = "да";
        else
             this.askueYesOrNot = "нет";
    }
    
    
    
    

    public long getIdDep() {
        
        return idDep.longValue();
    }

    public void setIdDep(Number idDep) {
        this.idDep = idDep;
    }

    public String getDepNameNP() {
        return depNameNP;
    }

    public void setDepNameNP(String depNameNP) {
        this.depNameNP = depNameNP;
    }

    public long getDepPerIdDep() {
        return depPerIdDep.longValue();
    }

    public void setDepPerIdDep(Number depPerIdDep) {
        this.depPerIdDep = depPerIdDep;
    }

    public String getDepPerNameNP() {
        return depPerNameNP;
    }

    public void setDepPerNameNP(String depPerNameNP) {
        this.depPerNameNP = depPerNameNP;
    }

    
    
    
    public long getIdRes() {
        return idRes.longValue();
    }

    public void setIdRes(Number idRes) {
        this.idRes = idRes;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public float getPlan_col() {
        
        if(plan_col!=null)
        return plan_col.floatValue();
        else
         return   0;
        
       
    }

    public void setPlan_col(Number plan_col) {
        this.plan_col = plan_col;
    }

    public float getFact_col() {
        if(fact_col!=null)
        return fact_col.floatValue();
        else
         return   0;
    }

    public void setFact_col(Number fact_col) {
        this.fact_col = fact_col;
    }

    public Object getDt_inputFact() {
        return dt_inputFact;
    }

    public void setDt_inputFact(Object dt_inputFact) {
        this.dt_inputFact = dt_inputFact;
    }

    public float getFact_plan_col_out() {
        return fact_plan_col_out;
    }

    public void setFact_plan_col_out(float fact_plan_col_out) {
        this.fact_plan_col_out = fact_plan_col_out;
    }

   

    public String getFact_plan_col() {
       DecimalFormat decimalFormat = new DecimalFormat("######.##");
       
        return decimalFormat.format(fact_plan_col_out);
    }

    public void setFact_plan_col(String fact_plan_col) {
        this.fact_plan_col = fact_plan_col;
    }

   
  
    
}
