/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import rzd.vivc.aszero.dto.baseclass.Data_Info;

/**
 *
 * @author apopovkin
 */
@Entity
@Table(   name = "DepartmentGroup")
public class DepartmentGroup extends Data_Info implements Serializable, Cloneable  {
 
    private String nameNp; 
    
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dtEditNSI;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dtBeginNSI;

    public String getNameNp() {
        return nameNp;
    }

    public void setNameNp(String nameNp) {
        this.nameNp = nameNp;
    }

    public Date getDtEditNSI() {
        return dtEditNSI;
    }

    public void setDtEditNSI(Date dtEditNSI) {
        this.dtEditNSI = dtEditNSI;
    }

    public Date getDtBeginNSI() {
        return dtBeginNSI;
    }

    public void setDtBeginNSI(Date dtBeginNSI) {
        this.dtBeginNSI = dtBeginNSI;
    }
    
    
    
    
    
    
}
