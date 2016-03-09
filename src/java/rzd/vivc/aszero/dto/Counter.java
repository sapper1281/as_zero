/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import rzd.vivc.aszero.dto.baseclass.Data_Info;

/**
 *
 * @author vvolgina
 */
@Entity
@Table( name = "COUNTER")
@SuppressWarnings("serial")
public class Counter implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="поля">
    
   
    @Id
    private long num;
    private String type;
    private boolean askue;
    
    
  
    
    @OneToMany(mappedBy = "inputCounter")
    private List<InputASKYEInfo> сounterInputASKYEInfo = new ArrayList<InputASKYEInfo>();

    
    
    
    
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="counter_inputASKYE",
               joinColumns={@JoinColumn(name="id_counter")},
               inverseJoinColumns={@JoinColumn(name="id_InputASKYE")})
    private List<InputASKYE> inputASKYE;
    
    
    
    
    
    @Column(name = "dt_end")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_end;
    @Column(name = "dt_create", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_create = new Date();
    @Column(name = "del_fl")
    private boolean deleted=false;

    public List<InputASKYE> getInputASKYE() {
        return inputASKYE;
    }

    public void setInputASKYE(List<InputASKYE> inputASKYE) {
        this.inputASKYE = inputASKYE;
    }

    
    
    
    
    
    
    
    
    
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
    
    
    
    
    
    //</editor-fold>
    public Counter() {
    }

    public Counter(long id) {
        setNum(id);
    }
    
    

    //<editor-fold defaultstate="collapsed" desc="get-set">
      
  /*  public List<InputASKYE> getDayInputCounter() {
        return dayInputCounter;
    }

    public void setDayInputCounter(List<InputASKYE> dayInputCounter) {
        this.dayInputCounter = dayInputCounter;
    }*/
      
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
    
    public boolean isAskue() {
        return askue;
    }
    
    public void setAskue(boolean askue) {
        this.askue = askue;
    }

    public List<InputASKYEInfo> getСounterInputASKYEInfo() {
        return сounterInputASKYEInfo;
    }

    public void setСounterInputASKYEInfo(List<InputASKYEInfo> сounterInputASKYEInfo) {
        this.сounterInputASKYEInfo = сounterInputASKYEInfo;
    }

       
    //</editor-fold>

    @Override
    public String toString() {
        return "Counter{ type=" + type + ", num=" + num + ", askue=" + askue + '}';
    }
    
    
}
