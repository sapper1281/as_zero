/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.io.Serializable;

/**
 *
 * @author apopovkin
 */
public class TypeTERForm3 extends TypeTER implements Serializable{
    
    float summ_plan;
    float summ_fact;
    float summ_ek;

    public float getSumm_plan() {
        return summ_plan;
    }

    public void setSumm_plan(float summ_plan) {
        this.summ_plan = summ_plan;
    }

    public float getSumm_fact() {
        return summ_fact;
    }

    public void setSumm_fact(float summ_fact) {
        this.summ_fact = summ_fact;
    }

    public float getSumm_ek() {
        return summ_ek;
    }

    public void setSumm_ek(float summ_ek) {
        this.summ_ek = summ_ek;
    }
    
    
    
}
