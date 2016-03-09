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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;

/**
 *
 * @author apopovkin
 */
@Entity
@Table(  name = "REPORT_DETAILS_FORM2")
@SuppressWarnings("serial")
public class ReportDetailsForm2 extends Data_Info {
   
    
    
    
    @OneToOne
    @JoinColumn(name = "reportId")
    private Report reportId;
    
    
    @ManyToOne
    @JoinColumn(name = "reportForm2_id")
    private Report reportForm2;
    
    /*@JoinColumn(name = "department_id")*/
    /*вспомогательное надо подумать как без них!!!*/
    private String departmentName;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    
    private long repId;
    private String inputActivityPlan ;
    private String inputActivityFact ;
    private float form2Coll7 ;
    private float form2Coll8 ;
    private float form2Coll9 ;
    private float form2Coll10 ;

    public Report getReportId() {
        return reportId;
    }

    public void setReportId(Report reportId) {
        this.reportId = reportId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    
    
    
    
    /*@OneToMany(mappedBy = "reportDetailsForm2")
    private List<Report> detailsReport = new ArrayList<Report>();
    public List<Report> getDetailsReport() {
    return detailsReport;
    }
    public void setDetailsReport(List<Report> detailsReport) {
    this.detailsReport = detailsReport;
    }
     */
    public float getForm2Coll7() {
        return form2Coll7;
    }

    public void setForm2Coll7(float form2Coll7) {
        this.form2Coll7 = form2Coll7;
    }

    public float getForm2Coll8() {
        return form2Coll8;
    }

    public void setForm2Coll8(float form2Coll8) {
        this.form2Coll8 = form2Coll8;
    }

    public float getForm2Coll9() {
        return form2Coll9;
    }

    public void setForm2Coll9(float form2Coll9) {
        this.form2Coll9 = form2Coll9;
    }

    public float getForm2Coll10() {
        return form2Coll10;
    }

    public void setForm2Coll10(float form2Coll10) {
        this.form2Coll10 = form2Coll10;
    }
    
    
    
    
    
    private String fioChief;
    private String phoneChief;
    private String rorsChief;
    private float askye;   

    public Report getReportForm2() {
        return reportForm2;
    }

    public void setReportForm2(Report reportForm2) {
        this.reportForm2 = reportForm2;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public long getRepId() {
        return repId;
    }

    public void setRepId(long repId) {
        this.repId = repId;
    }

    public String getInputActivityPlan() {
        return inputActivityPlan;
    }

    public void setInputActivityPlan(String inputActivityPlan) {
        this.inputActivityPlan = inputActivityPlan;
    }

    public String getInputActivityFact() {
        return inputActivityFact;
    }

    public void setInputActivityFact(String inputActivityFact) {
        this.inputActivityFact = inputActivityFact;
    }

    

    
    
    
    
    
    
    public String getFioChief() {
        return fioChief;
    }

    public void setFioChief(String fioChief) {
        this.fioChief = fioChief;
    }

    public String getPhoneChief() {
        return phoneChief;
    }

    public void setPhoneChief(String phoneChief) {
        this.phoneChief = phoneChief;
    }

    public String getRorsChief() {
        return rorsChief;
    }

    public void setRorsChief(String rorsChief) {
        this.rorsChief = rorsChief;
    }

    public float getAskye() {
        return askye;
    }

    public void setAskye(float askye) {
        this.askye = askye;
    }
    
    
    
}
