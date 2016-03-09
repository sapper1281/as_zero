/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import com.rits.cloning.Cloner;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import rzd.vivc.aszero.dto.baseclass.Data_Info;

/**
 *
 * @author apopovkin
 */
@Entity
@Table(   name = "Department")
public class Department extends Data_Info implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private int idVp;
    private String nameVp ;
    private int idGroupVp;
    private int idActionVp;
    private int idNp;
    private String nameNp; 
    private int idGroupNp;
    private int idActionNp;
    private int num;
    private String viewSubmission ;
    private int idViewSubmission;
    private String typeCommunication;
    private int idTypeCommunication;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dtEditNSI;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dtBeginNSI;

    @ManyToOne
    @JoinColumn(name = "departmentGroup")
    private DepartmentGroup departmentGroup_id;
   
    
    
    
    
    
    public Department() {
    }

    @Override
    public Department clone() {
         Cloner cloner = new Cloner();
          return cloner.deepClone(this);
    }
    
    

    
   /* @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
*/
   @Override
    public String toString() {
        return  nameNp ;
    }


    public int getIdVp() {
        return idVp;
    }

    public void setIdVp(int idVp) {
        this.idVp = idVp;
    }

    public String getNameVp() {
        return nameVp;
    }

    public void setNameVp(String nameVp) {
        this.nameVp = nameVp;
    }

    public int getIdGroupVp() {
        return idGroupVp;
    }

    public void setIdGroupVp(int idGroupVp) {
        this.idGroupVp = idGroupVp;
    }

    public int getIdActionVp() {
        return idActionVp;
    }

    public void setIdActionVp(int idActionVp) {
        this.idActionVp = idActionVp;
    }

    public int getIdNp() {
        return idNp;
    }

    public void setIdNp(int idNp) {
        this.idNp = idNp;
    }

    public String getNameNp() {
        return nameNp;
    }

    public void setNameNp(String nameNp) {
        this.nameNp = nameNp;
    }

    public int getIdGroupNp() {
        return idGroupNp;
    }

    public void setIdGroupNp(int idGroupNp) {
        this.idGroupNp = idGroupNp;
    }

    public int getIdActionNp() {
        return idActionNp;
    }

    public void setIdActionNp(int idActionNp) {
        this.idActionNp = idActionNp;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getViewSubmission() {
        return viewSubmission;
    }

    public void setViewSubmission(String viewSubmission) {
        this.viewSubmission = viewSubmission;
    }

    public int getIdViewSubmission() {
        return idViewSubmission;
    }

    public void setIdViewSubmission(int idViewSubmission) {
        this.idViewSubmission = idViewSubmission;
    }

    public String getTypeCommunication() {
        return typeCommunication;
    }

    public void setTypeCommunication(String typeCommunication) {
        this.typeCommunication = typeCommunication;
    }

    public int getIdTypeCommunication() {
        return idTypeCommunication;
    }

    public void setIdTypeCommunication(int idTypeCommunication) {
        this.idTypeCommunication = idTypeCommunication;
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

   public DepartmentGroup getDepartmentGroup_id() {
        return departmentGroup_id;
    }

    public void setDepartmentGroup_id(DepartmentGroup departmentGroup_id) {
        this.departmentGroup_id = departmentGroup_id;
    }
    
}
