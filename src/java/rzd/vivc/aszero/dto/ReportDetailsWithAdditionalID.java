/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

/**
 *
 * @author vvolgina
 */
public class ReportDetailsWithAdditionalID extends ReportDetails{
    @Transient
    private long addID;

    @Override
    public long getAddID() {
        return addID;
    }

    public void setAddID(long addID) {
        this.addID = addID;
    }

    public ReportDetailsWithAdditionalID(long addID) {
        this.addID = addID;
    }

    
}
