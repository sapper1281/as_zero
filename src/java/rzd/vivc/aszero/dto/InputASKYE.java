/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import rzd.vivc.aszero.dto.baseclass.Data_Info;

/**
 *
 * @author apopovkin
 */
@Entity
@Table( name = "InputASKYE")
@SuppressWarnings("serial")
public class InputASKYE implements Serializable{

    
    /*@ManyToOne
    @JoinColumn(name = "Counter_id")
    private Counter inputCounter;*/
     
   
    
     
      @Id
     @Temporal(javax.persistence.TemporalType.TIMESTAMP)
     private Date dtBefore;
     @Temporal(javax.persistence.TemporalType.TIMESTAMP)
     private Date dtToday;
     
      @ManyToMany(mappedBy="inputASKYE")
    private List<Counter> inputCounter;

     
    @Column(name = "dt_end")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_end;
    @Column(name = "dt_create", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_create = new Date();
    @Column(name = "del_fl")
    private boolean deleted=false;
     
     
     
     @OneToMany(mappedBy = "inputASKYEinf")
     private List<InputASKYEInfo> inputASKYEinputASKYEinf = new ArrayList<InputASKYEInfo>();

    public Date getDt_end() {
        return dt_end;
    }

    public void setDt_end(Date dt_end) {
        this.dt_end = dt_end;
    }

    public Date getDt_create() {
        return dt_create;
    }

    public void setDt_create(Date dt_create) {
        this.dt_create = dt_create;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    
     
     
     
    public List<Counter> getInputCounter() {
        return inputCounter;
    }

    public void setInputCounter(List<Counter> inputCounter) {
        this.inputCounter = inputCounter;
    }

   

    

    public Date getDtBefore() {
        return dtBefore;
    }

    public void setDtBefore(Date dtBefore) {
        this.dtBefore = dtBefore;
    }

    public Date getDtToday() {
        return dtToday;
    }

    public void setDtToday(Date dtToday) {
        this.dtToday = dtToday;
    }

    public List<InputASKYEInfo> getInputASKYEinputASKYEinf() {
        return inputASKYEinputASKYEinf;
    }

    public void setInputASKYEinputASKYEinf(List<InputASKYEInfo> inputASKYEinputASKYEinf) {
        this.inputASKYEinputASKYEinf = inputASKYEinputASKYEinf;
    }


   
      @Override
    public String toString() {
        return "InputASKYE{ dtBefore=" + dtBefore + "'}'";
    }
     
     
    
}
