/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author apopovkin
 */
@Embeddable
public class KeyInputASKYEInfo implements Serializable{
    Counter inputCounter;  
    InputASKYE inputASKYEinf;
    int hh;
    
    
    
    
      /*
        
    @ManyToOne(targetEntity=Counter.class)
    @JoinColumn(name = "inputCounter_id",referencedColumnName="num")
    private int num;   
        
    @ManyToOne(targetEntity=InputASKYE.class)
    @JoinColumn(name = "inputASKYE_id",referencedColumnName="dtBefore")
     @Temporal(javax.persistence.TemporalType.TIMESTAMP)
     private Date dtBefore;  
   
     private int hh;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + this.num;
            hash = 37 * hash + (this.dtBefore != null ? this.dtBefore.hashCode() : 0);
            hash = 37 * hash + this.hh;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final KeyInputASKYEInfo other = (KeyInputASKYEInfo) obj;
            if (this.num != other.num) {
                return false;
            }
            if (this.dtBefore != other.dtBefore && (this.dtBefore == null || !this.dtBefore.equals(other.dtBefore))) {
                return false;
            }
            if (this.hh != other.hh) {
                return false;
            }
            return true;
        }

   
  
    
    
    
    
    
    
    
    
    
    
    
    
   /* @Column(name = "inputCounter_idinf")
    private long inputCounter;
    
    @Column(name = "inputASKYEinf_idinf")
     @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date inputASKYEinf;
    
     @Column(name = "hh")
    private int hh;

    public int getHh() {
        return hh;
    }

    public void setHh(int hh) {
        this.hh = hh;
    }

     
     
     
    public long getInputCounter() {
        return inputCounter;
    }

    public void setInputCounter(long inputCounter) {
        this.inputCounter = inputCounter;
    }

    public Date getInputASKYEinf() {
        return inputASKYEinf;
    }

    public void setInputASKYEinf(Date inputASKYEinf) {
        this.inputASKYEinf = inputASKYEinf;
    }

    public KeyInputASKYEInfo() {
    }

    public KeyInputASKYEInfo(long inputCounter, Date inputASKYEinf, int hh) {
        this.inputCounter = inputCounter;
        this.inputASKYEinf = inputASKYEinf;
        this.hh = hh;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.inputCounter ^ (this.inputCounter >>> 32));
        hash = 97 * hash + (this.inputASKYEinf != null ? this.inputASKYEinf.hashCode() : 0);
        hash = 97 * hash + this.hh;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KeyInputASKYEInfo other = (KeyInputASKYEInfo) obj;
        if (this.inputCounter != other.inputCounter) {
            return false;
        }
        if (this.inputASKYEinf != other.inputASKYEinf && (this.inputASKYEinf == null || !this.inputASKYEinf.equals(other.inputASKYEinf))) {
            return false;
        }
        if (this.hh != other.hh) {
            return false;
        }
        return true;
    }

    */

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.inputCounter != null ? this.inputCounter.hashCode() : 0);
        hash = 89 * hash + (this.inputASKYEinf != null ? this.inputASKYEinf.hashCode() : 0);
        hash = 89 * hash + this.hh;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KeyInputASKYEInfo other = (KeyInputASKYEInfo) obj;
        if (this.inputCounter != other.inputCounter && (this.inputCounter == null || !this.inputCounter.equals(other.inputCounter))) {
            return false;
        }
        if (this.inputASKYEinf != other.inputASKYEinf && (this.inputASKYEinf == null || !this.inputASKYEinf.equals(other.inputASKYEinf))) {
            return false;
        }
        if (this.hh != other.hh) {
            return false;
        }
        return true;
    }
     
     
}
