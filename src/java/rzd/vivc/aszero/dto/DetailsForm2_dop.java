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
public class DetailsForm2_dop implements Serializable{
    
 private Number  id ;
 private Date  dt_begin; 
 private Date  dt_create; 
 private String  fio; 
 private String phone;
 private Number  iddep; 
 private String namenp;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Date getDt_begin() {
        return dt_begin;
    }

    public void setDt_begin(Date dt_begin) {
        this.dt_begin = dt_begin;
    }

    public Date getDt_create() {
        return dt_create;
    }

    public void setDt_create(Date dt_create) {
        this.dt_create = dt_create;
    }
     
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

    public Number getIddep() {
        return iddep;
    }

    public void setIddep(Number iddep) {
        this.iddep = iddep;
    }

    public String getNamenp() {
        return namenp;
    }

    public void setNamenp(String namenp) {
        this.namenp = namenp;
    }
 
 
 

}
