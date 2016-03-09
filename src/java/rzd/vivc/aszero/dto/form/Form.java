/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto.form;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author apopovkin
 */
@Entity
@Table( name = "Form")
public class Form extends Data_Info implements Serializable {
    private static final long serialVersionUID = 1L;
    private int serialNumForm;
    private String fullNameForm;
    private String shortNameForm;
    private String pageAddress;
  /*  @OneToMany(fetch = FetchType.LAZY, mappedBy = "form", cascade = CascadeType.ALL)
    private List<FormHeader> formHeader = new ArrayList<FormHeader>();*/
    @OneToMany(mappedBy = "form")
    private List<Report> report;
   /* @OneToMany(mappedBy = "form")
    
    
    private List<ReportForm2> reportForm2;
public List<ReportForm2> getReportForm2() {
        return reportForm2;
    }
public void setReportForm2(List<ReportForm2> reportForm2) {
        this.reportForm2 = reportForm2;
    }*/

    
    @ManyToMany(mappedBy = "forms")
    private List<User> users;
    
     public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public Form() {
    }

    public Form(long id) {
        setId(id);
    }
    
    
   

    /*@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

   @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Form)) {
            return false;
        }
        Form other = (Form) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
*/
    @Override
    public String toString() {
        return "rzd.vivc.aszero.dto.Form[ id=" + fullNameForm + " ]";
    }

    public int getSerialNumForm() {
        return serialNumForm;
    }

    public void setSerialNumForm(int serialNumForm) {
        this.serialNumForm = serialNumForm;
    }

    public String getFullNameForm() {
        return fullNameForm;
    }

    public void setFullNameForm(String fullNameForm) {
        this.fullNameForm = fullNameForm;
    }

    public String getShortNameForm() {
        return shortNameForm;
    }

    public void setShortNameForm(String shortNameForm) {
        this.shortNameForm = shortNameForm;
    }

    /*    public List<FormHeader> getFormHeader() {
    return formHeader;
    }
    public void setFormHeader(List<FormHeader> formHeader) {
    this.formHeader = formHeader;
    }*/
    public List<Report> getReport() {
        return report;
    }

    public void setReport(List<Report> report) {
        this.report = report;
    }

    public String getPageAddress() {
        return pageAddress;
    }

    public void setPageAddress(String pageAddress) {
        this.pageAddress = pageAddress;
    }

   
    
}
