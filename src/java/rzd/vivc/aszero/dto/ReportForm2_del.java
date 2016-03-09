/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import rzd.vivc.aszero.dto.form.Form;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author apopovkin
 */
//@Entity
//@Table(name = "REPORT_FORM2")
//@SuppressWarnings("serial")
public class ReportForm2_del extends Data_Info  {
    
  /*  
        @ManyToOne
    private Form form;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User usr;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @OneToMany(mappedBy = "reportForm2")
    private List<ReportDetailsForm2> details= new ArrayList<ReportDetailsForm2>();
    
    
    
    
    
    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<ReportDetailsForm2> getDetails() {
        return details;
    }

    public void setDetails(List<ReportDetailsForm2> details) {
        this.details = details;
    }

   */
    
}
