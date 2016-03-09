/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.classes;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author apopovkin
 */
public class FormSeven_del implements Serializable{
  String   col1;
  String   col2;
  String   col3;
  String   activity; 
  String   addres; 
  String   responsible; 
  String   powerSource; 
  String   addressOfObject;
  String   type;
  long     num;
  boolean  askue;
  String  askueYesOrNot;
  float    plan_col;
  float    fact_col;
  float    fact_plan_col_out;
  String    fact_plan_col;

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
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
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getAskueYesOrNot() {
        if(askue)
        return "да";
        else
        return "нет";    
    }

    public void setAskueYesOrNot(String askueYesOrNot) {
        this.askueYesOrNot = askueYesOrNot;
    }

    
    
    public boolean isAskue() {
        
        return askue;
    }
    

    public void setAskue(boolean askue) {
        this.askue = askue;
    }

    public float getPlan_col() {
        return plan_col;
    }

    public void setPlan_col(float plan_col) {
        this.plan_col = plan_col;
    }

    public float getFact_col() {
        return fact_col;
    }

    public void setFact_col(float fact_col) {
        this.fact_col = fact_col;
    }

    public String getFact_plan_col() {
       DecimalFormat decimalFormat = new DecimalFormat("######.##");
       
        return decimalFormat.format(fact_plan_col_out);
    }

    public void setFact_plan_col(String fact_plan_col) {
        this.fact_plan_col = fact_plan_col;
    }

    public float getFact_plan_col_out() {
        return fact_plan_col_out;
    }

    public void setFact_plan_col_out(float fact_plan_col_out) {
        this.fact_plan_col_out = fact_plan_col_out;
    }

  
    
}
