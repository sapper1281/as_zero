/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author apopovkin
 */
public class DetailsForm2 implements Serializable{
  private Number plan_tyt;
  private Number fact_tyt;
  private Number planE;
  private Number factE;
  private Number factASKUE;

 private Number idRep;
 private Date dt_inputfact;
 private Date dt_inputplan;
 private Date  dt_begin;
private Date time_begin;
private Date time_finish;
 private Number  id ;
 private String  namenp; 
 private Number  idrepF2; 
 private Number  iddetailsF2; 
 private String fiochief;
 private String phonechief;
 private String rorschief;
 private String inputActivityPlan ;
    private String inputActivityFact ;

    public Number getPlan_tyt() {
        return plan_tyt;
    }

    public void setPlan_tyt(Number plan_tyt) {
        this.plan_tyt = plan_tyt;
    }

    public Number getFact_tyt() {
        return fact_tyt;
    }

    public void setFact_tyt(Number fact_tyt) {
        this.fact_tyt = fact_tyt;
    }

    public Number getPlanE() {
        return planE;
    }

    public void setPlanE(Number planE) {
        this.planE = planE;
    }

    public Number getFactE() {
        return factE;
    }

    public void setFactE(Number factE) {
        this.factE = factE;
    }

    public Number getFactASKUE() {
        return factASKUE;
    }

    public void setFactASKUE(Number factASKUE) {
        this.factASKUE = factASKUE;
    }

    public Number getIdRep() {
        return idRep;
    }

    public void setIdRep(Number idRep) {
        this.idRep = idRep;
    }

    public Date getDt_inputfact() {
        return dt_inputfact;
    }

    public void setDt_inputfact(Date dt_inputfact) {
        this.dt_inputfact = dt_inputfact;
    }

    public Date getDt_inputplan() {
        return dt_inputplan;
    }

    public void setDt_inputplan(Date dt_inputplan) {
        this.dt_inputplan = dt_inputplan;
    }

    public Date getDt_begin() {
        return dt_begin;
    }

    public void setDt_begin(Date dt_begin) {
        this.dt_begin = dt_begin;
    }

    public Date getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(Date time_begin) {
        this.time_begin = time_begin;
    }

    public Date getTime_finish() {
        return time_finish;
    }

    public void setTime_finish(Date time_finish) {
        this.time_finish = time_finish;
    }
    
    

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getNamenp() {
        return namenp;
    }

    public void setNamenp(String namenp) {
        this.namenp = namenp;
    }

    public Number getIdrepF2() {
        return idrepF2;
    }

    public void setIdrepF2(Number idrepF2) {
        this.idrepF2 = idrepF2;
    }

    public Number getIddetailsF2() {
        return iddetailsF2;
    }

    public void setIddetailsF2(Number iddetailsF2) {
        this.iddetailsF2 = iddetailsF2;
    }

    public String getFiochief() {
        return fiochief;
    }

    public void setFiochief(String fiochief) {
        this.fiochief = fiochief;
    }

    public String getPhonechief() {
        return phonechief;
    }

    public void setPhonechief(String phonechief) {
        this.phonechief = phonechief;
    }

    public String getRorschief() {
        return rorschief;
    }

    public void setRorschief(String rorschief) {
        this.rorschief = rorschief;
    }

    public String getInputActivityPlan() {
        return inputActivityPlan;
    }

    public void setInputActivityPlan(String inputActivityPlan) {
        this.inputActivityPlan = inputActivityPlan;
    }

    public String getInputActivityFact() {
        return inputActivityFact;
    }

    public void setInputActivityFact(String inputActivityFact) {
        this.inputActivityFact = inputActivityFact;
    }
 
 
 
 
 

}
