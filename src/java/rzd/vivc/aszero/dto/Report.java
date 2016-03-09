/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoResource;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.service.StringImprover;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author VVolgina
 */
@Entity
@Table(name = "REPORT")
@SuppressWarnings("serial")
public class Report extends Data_Info {

    /**
     * 1форма
     */
    @Transient
    private List<CostDetails> costs;

    public List<CostDetails> getCosts() {
        return costs;
    }

    public void setCosts(List<CostDetails> costs) {
        this.costs = new ArrayList<CostDetails>();
        for (CostDetails costDetails : costs) {
            int id = (int) costDetails.getResource().getId();
            while (id >= this.costs.size()) {
                this.costs.add(null);
            }
            this.costs.set(id, costDetails);
        }
    }
    
     @Transient
    private List<ShortInfoResource> costsShort=null;

    public List<ShortInfoResource> getCostsShort() {
        return costsShort;
    }

    public void setCostsShort(List<ShortInfoResource> costsShort) {
        this.costsShort = new ArrayList<ShortInfoResource>();
         try {
            for (ShortInfoResource costDetails : costsShort) {
                int id = (int) costDetails.getId();
                while (id >= this.costsShort.size()) {
                    this.costsShort.add(null);
                }
                this.costsShort.set(id, costDetails);
            }
        } catch (Exception e) {
            this.costsShort=null;
        }
    }
    
    
    
    public void setCostsBy(List<Resource> costs) {
        this.costs = new ArrayList<CostDetails>();
         try {
            for (Resource costDetails : costs) {
                int id = (int) costDetails.getId();
                while (id >= this.costs.size()) {
                    this.costs.add(null);
                }
                this.costs.set(id, costDetails.getCost().get(0));
            }
        } catch (Exception e) {
            this.costs=null;
        }
    }

    public void setCosts(Report rep) {
        this.costs = rep.getCosts();
    }
    @Column(name = "dt_inputPlan")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_inputPlan;
    @Column(name = "dt_inputFact")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_inputFact;
    @Column(name = "dt_begin")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_begin;
    @Column(name = "time_begin")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date time_begin;
    @Column(name = "time_finish")
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date time_finish;
    @ManyToOne
    private Form form;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User usr;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(mappedBy = "report")
    private List<ReportDetails> details = new ArrayList<ReportDetails>();
    @OneToMany(mappedBy = "reportForm2")
    private List<ReportDetailsForm2> detailsForm2 = new ArrayList<ReportDetailsForm2>();
    @OneToOne(mappedBy = "reportId")
    private ReportDetailsForm2 reportDetailsForm2;
    @Column(name = "dt_inputPlanInput")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_solveInputPlan;
    private boolean solveInputPlan;
    @ManyToOne/*(fetch = FetchType.LAZY)*/
    @JoinColumn(name = "usrSolvePlanInput")
    private User usrSolvePlanInput;

    public Date getDt_solveInputPlan() {
        return dt_solveInputPlan;
    }

    public void setDt_solveInputPlan(Date dt_solveInputPlan) {
        this.dt_solveInputPlan = dt_solveInputPlan;
    }

    public boolean isSolveInputPlan() {
        return solveInputPlan;
    }

    public void setSolveInputPlan(boolean solveInputPlan) {
        this.solveInputPlan = solveInputPlan;
    }

    public User getUsrSolvePlanInput() {
        return usrSolvePlanInput;
    }

    public void setUsrSolvePlanInput(User usrSolvePlanInput) {
        this.usrSolvePlanInput = usrSolvePlanInput;
    }

    /*   @ManyToOne
     @JoinColumn(name = "reportDetailsForm2_id")
     private ReportDetailsForm2 reportDetailsForm2;

     public ReportDetailsForm2 getReportDetailsForm2() {
     return reportDetailsForm2;
     }

     public void setReportDetailsForm2(ReportDetailsForm2 reportDetailsForm2) {
     this.reportDetailsForm2 = reportDetailsForm2;
     }*/
    public List<ReportDetailsForm2> getDetailsForm2() {
        return detailsForm2;
    }

    public void setDetailsForm2(List<ReportDetailsForm2> detailsForm2) {
        this.detailsForm2 = detailsForm2;
    }

    public Report() {
    }

    public Report(long id) {
        setId(id);
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Date getDt_begin() {
        return dt_begin;



    }

    public void setDt_begin(Date dt_begin) {
        this.dt_begin = dt_begin;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public List<ReportDetails> getDetails() {
        return details;
    }

    public void setDetails(List<ReportDetails> details) {
        this.details = details;
    }

    public Date getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(Date time_begin) {
        this.time_begin = time_begin;
    }

    public Date getTime_finish() {
        return time_finish;
    }

    public void setTime_finish(Date time_finish) {
        this.time_finish = time_finish;
    }

    public String getTitle() {
        return usr.getFIO() + " за " + (getDt_begin().getMonth() + 1) + " месяц " + (getDt_begin().getYear() + 1900) + " года";
    }

    public Date getDt_inputPlan() {
        return dt_inputPlan;
    }

    public void setDt_inputPlan(Date dt_inputPlan) {
        this.dt_inputPlan = dt_inputPlan;
    }

    public Date getDt_inputFact() {
        return dt_inputFact;
    }

    public void setDt_inputFact(Date dt_inputFact) {
        this.dt_inputFact = dt_inputFact;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDt_dt_inputPlanString() {
        return (new StringImprover()).getDateString(dt_inputPlan);
    }

    public String getDt_dt_inputFactString() {
        return (new StringImprover()).getDateString(dt_inputFact);
    }

    @Override
    public String toString() {
        return "Report{" + "department=" + department + '}';
    }
}
