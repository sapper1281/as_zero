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
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.metamodel.source.binder.Sortable;
import rzd.vivc.aszero.dto.baseclass.Data_Info;

/**
 *
 * @author apopovkin
 */
@Entity
@Table( name = "InputASKYEInfo")
@SuppressWarnings("serial")
@IdClass(value = KeyInputASKYEInfo.class)
public class InputASKYEInfo implements Serializable, Comparable<InputASKYEInfo>{
     /* ManyToOne
    @JoinColumn(name = "inputCounter_id")
    private Counter inputCounter;
    
    
    @ManyToOne
    @JoinColumn(name = "inputASKYE_id")
    private InputASKYE inputASKYEinf;
   */
    
    private float hhBefore;
    private float hhToday;
   
    @Id
    @ManyToOne(targetEntity=Counter.class)
    @JoinColumn(name = "inputCounter_id",referencedColumnName="num")
    Counter inputCounter;  
        
    @Id
    @ManyToOne(targetEntity=InputASKYE.class)
    @JoinColumn(name = "inputASKYE_id",referencedColumnName="dtBefore")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    InputASKYE inputASKYEinf;
    
    
    @Id
    int hh;
    
    
    
 
    
    
    
  /*  
    class Table_1 {
  @Id int Column_A;
}
.

class Table_2 {
  @EmbeddedId PK key;

  @Embeddable class PK {
    @OneToOne(targetEntity=Table_1.class)
    @JoinColumn(name="Column_x",referencedColumnName="Column_A")
    int Column_x;

    @OneToOne(targetEntity=Table_1.class)
    @JoinColumn(name="Column_y",referencedColumnName="Column_A")
    int Column_y;

    int Column_z;

    public boolean equals(Object O) { ... }
    public int hashCode() { ... } 
  }
}*/

    /*public KeyInputASKYEInfo getKey() {
        return key;
    }

    public void setKey(KeyInputASKYEInfo key) {
        this.key = key;
    }
    */

    // @EmbeddedId KeyInputASKYEInfo key;
    public Counter getInputCounter() {
        return inputCounter;
    }

    public void setInputCounter(Counter inputCounter) {
        this.inputCounter = inputCounter;
    }

    public InputASKYE getInputASKYEinf() {
        return inputASKYEinf;
    }

    public void setInputASKYEinf(InputASKYE inputASKYEinf) {
        this.inputASKYEinf = inputASKYEinf;
    }

    public int getHh() {
        return hh;
    }

    public void setHh(int hh) {
        this.hh = hh;
    }
    
    
    
    
    
    
    
    
    /*
    
    @IdClass(PK.class)
class Table_2 {
    @Id
    @OneToOne(targetEntity=Table_1.class)
    @JoinColumn(name="Column_x",referencedColumnName="Column_A")
    Table_1 Column_x;

    @Id
    @OneToOne(targetEntity=Table_1.class)
    @JoinColumn(name="Column_y",referencedColumnName="Column_A")
    Table_1 Column_y;

    @Id
    int Column_z;

    public boolean equals(Object O) { ... }
    public int hashCode() { ... } 
  }
}

class PK {
    int Column_x;
    int Column_y;
    int Column_z;
}
    
    --------------------------------------
    
    
    */
    
    
    
  /*  @Id
    @AttributeOverrides(value = {
        @AttributeOverride(name = "hh", column =
                @Column(name = "hh")),
        @AttributeOverride(name = "inputCounter_idinf", column =
                @Column(name = "inputCounter_idinf")),
        @AttributeOverride(name = "inputASKYEinf_idinf", column =
                @Column(name = "inputASKYEinf_idinf"))})*/
   
 /*   private long inputCounter_idinf;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date inputASKYEinf_idinf;*/

  /*  public long getInputCounter_idinf() {
        return inputCounter_idinf;
    }

    public void setInputCounter_idinf(long inputCounter_idinf) {
       // this.inputCounter_idinf = inputCounter_idinf;
         this.inputCounter_idinf=inputCounter.getNum();
        
    }*

    public Date getInputASKYEinf_idinf() {
        return inputASKYEinf_idinf;
    }

    public void setInputASKYEinf_idinf(Date inputASKYEinf_idinf) {
       // this.inputASKYEinf_idinf = inputASKYEinf_idinf;
        
         this.inputASKYEinf_idinf=inputASKYEinf.getDtBefore();
    }
    */
    
    
    
    
   /* public InputASKYE getInputASKYEinf() {
        return inputASKYEinf;
    }

    public void setInputASKYEinf(InputASKYE inputASKYEinf) {
        this.inputASKYEinf = inputASKYEinf;
       // this.inputASKYEinf_idinf=inputASKYEinf.getDtBefore();
    }

    public Counter getInputCounter() {
        return inputCounter;
    }

    public void setInputCounter(Counter inputCounter) {
        this.inputCounter = inputCounter;
        // this.inputCounter_idinf=inputCounter.getNum();
    }
    
    
   /* @ManyToOne
    @JoinColumn(name = "inputCounter_id")
    private Counter inputCounter;
    
    
    @ManyToOne
    @JoinColumn(name = "inputASKYE_id")
    private InputASKYE inputASKYEinf;
*/
    

    public float getHhBefore() {
        return hhBefore;
    }

    public void setHhBefore(float hhBefore) {
        this.hhBefore = hhBefore;
    }

    public float getHhToday() {
        return hhToday;
    }

    public void setHhToday(float hhToday) {
        this.hhToday = hhToday;
    }

    

    
 @Override
    public String toString() {
        return "InputASKYEinf{ hhBefore=" + hhBefore + "hhToday=" + hhToday + "'}'";
    }

    @Override
    public int compareTo(InputASKYEInfo o) {
        return this.hh-o.hh;
    }
     
   
     
}
