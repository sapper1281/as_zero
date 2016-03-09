/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsForm3;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.repository.UserRepository;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class FormTreePageBean_del implements Serializable{

  /*  private Date dt_otchet;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private List<ReportDetailsForm3> reportDetailsForm3List;
    private ReportDetailsForm3 reportDetailsForm3;
    private Department dep;

    public List<ReportDetailsForm3> getReportDetailsForm3List() {
        return reportDetailsForm3List;
    }

    public void setReportDetailsForm3List(List<ReportDetailsForm3> reportDetailsForm3List) {
        this.reportDetailsForm3List = reportDetailsForm3List;
    }

    public Department getDep() {
        return dep;
    }

    public void setDep(Department dep) {
        this.dep = dep;
    }

    public Date getDt_otchet() {
        return dt_otchet;
    }

    public void setDt_otchet(Date dt_otchet) {
        this.dt_otchet = dt_otchet;
    }

    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    public ReportDetailsForm3 getReportDetailsForm3() {
        return reportDetailsForm3;
    }

    public void setReportDetailsForm3(ReportDetailsForm3 reportDetailsForm3) {
        this.reportDetailsForm3 = reportDetailsForm3;
    }

    @PostConstruct
    //System.out.println("==========");
    public void Init() {
        reportDetailsForm3.setCell1(0);
        reportDetailsForm3.setCell2(0);
        reportDetailsForm3.setCell3(0);
        //reportDetailsForm3.setCell4(0);
        reportDetailsForm3.setCell5(0);
        reportDetailsForm3.setCell6(0);
        reportDetailsForm3.setCell7(0);
        reportDetailsForm3.setCell8(0);
        reportDetailsForm3.setCell9(0);
        reportDetailsForm3.setCell10(0);
        reportDetailsForm3.setCell11(0);
        reportDetailsForm3.setCell12(0);
        reportDetailsForm3.setCell13(0);
        
        dep = (new UserRepository().get(new User(session.getUser().getUserID()))).getDepartment();


        DepartmentRepository deprep = new DepartmentRepository();
        
        List<Department>  depList = deprep.getActiveListDepartmentsWithDetails(dep.getIdNp());
        System.err.println("============"+depList);
        Iterator itr = depList.iterator();
        while (itr.hasNext()) {
            Object object = itr.next();
            if (object instanceof Department) {
                ReportRepository repRep = new ReportRepository();
                List<Report> rep = repRep.getListWithDetailsIdNp(((Department) object).getIdNp());
         System.err.println("============"+repRep);
                
                if (rep.size() > 0) {
                    Iterator itrrep = rep.iterator();
                    while (itrrep.hasNext()) {
                        Object objectrep = itrrep.next();
                        if (objectrep instanceof Report) {
                            Iterator itrrepDetails = ((Report) objectrep).getDetails().iterator();
                            while (itrrepDetails.hasNext()) {
                                Object objectrepDetails = itrrepDetails.next();
                                if (objectrepDetails instanceof ReportDetails) {
System.err.println("============"+(ReportDetails) objectrepDetails);
         

                                    if ((((ReportDetails) objectrepDetails).getResource().getId() == 13)) {
                                        reportDetailsForm3.setCell1(((ReportDetails) objectrepDetails).getPlan_col() + reportDetailsForm3.getCell1());
                                        reportDetailsForm3.setCell2(((ReportDetails) objectrepDetails).getFact_col() + reportDetailsForm3.getCell2());
                                        reportDetailsForm3.setCell3(((ReportDetails) objectrepDetails).getFact_money() + reportDetailsForm3.getCell3());
                                    }
                                    if ((((ReportDetails) objectrepDetails).getResource().getId() == 12)) {
                                        reportDetailsForm3.setCell8(((ReportDetails) objectrepDetails).getFact_col() + reportDetailsForm3.getCell8());
                                        reportDetailsForm3.setCell9(((ReportDetails) objectrepDetails).getFact_money() + reportDetailsForm3.getCell9());
                                    }
                                    if ((((ReportDetails) objectrepDetails).getResource().getId() == 16)) {
                                        reportDetailsForm3.setCell10(((ReportDetails) objectrepDetails).getFact_col() + reportDetailsForm3.getCell10());
                                    }
                                    if ((((ReportDetails) objectrepDetails).getResource().getId() == 15)) {
                                        reportDetailsForm3.setCell11(((ReportDetails) objectrepDetails).getFact_col() + reportDetailsForm3.getCell11());
                                        reportDetailsForm3.setCell12(((ReportDetails) objectrepDetails).getFact_col() + reportDetailsForm3.getCell12());
                                    }
                                    if ((((ReportDetails) objectrepDetails).getResource().getId() == 11)) {
                                        reportDetailsForm3.setCell13(((ReportDetails) objectrepDetails).getFact_col() + reportDetailsForm3.getCell13());
                                    }



                                }
                            }



                        }
                    }
                }
            }

            reportDetailsForm3List.add(reportDetailsForm3);
        }






    }*/
}
