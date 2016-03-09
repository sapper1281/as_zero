package rzd.vivc.aszero.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoResource;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import rzd.vivc.aszero.service.converters.ShortInfoResourceConverter;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author VVolgina
 */
@Entity
@Table(name = "RESOURCE_DETAILS")
@SuppressWarnings("serial")
public class ReportDetails extends Data_Info {

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;
    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource = new Resource();
    @Column(name = "dt_inputFact")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_inputFact;
    /*проверка введен или нет факт*/
    private boolean dt_inputFact_test;

    public boolean isDt_inputFact_test() {
        return dt_inputFact_test;
    }

    public void setDt_inputFact_test(boolean dt_inputFact_test) {
        this.dt_inputFact_test = dt_inputFact_test;
    }

    public Date getDt_inputFact() {
        return dt_inputFact;
    }

    public void setDt_inputFact(Date dt_inputFact) {
        this.dt_inputFact = dt_inputFact;
    }
    /**
     * 1форма
     */
    @Column(name = "activity", length = 1000)
    private String activity;
    private float plan_col;
    private float plan_tut;
    private float plan_money;
    private float fact_col;
    private float fact_tut;
    private float fact_money;
    //<editor-fold defaultstate="collapsed" desc="электроэнергия">
    private String responsible;
    private String addressOfObject;
    private String powerSource;
    private String type;
    private long num;
    private boolean askue;
    private String addres;
    private float usedBefore;
    private float usedToday;

    public float getEconomy() {
        /*  if(usedBefore-usedToday>0)*/ return usedBefore - usedToday;
        // return 0;
    }

    public float getEconomyNull() {
        if (getEconomy() > 0) {
            return getEconomy();
        }
        return 0;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getAddressOfObject() {
        return addressOfObject;
    }

    public void setAddressOfObject(String addressOfObject) {
        this.addressOfObject = addressOfObject;
    }

    public String getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(String powerSource) {
        this.powerSource = powerSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public boolean isAskue() {
        return askue;
    }

    public void setAskue(boolean askue) {
        this.askue = askue;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public float getUsedBefore() {
        return usedBefore;
    }

    public void setUsedBefore(float usedBefore) {
        this.usedBefore = usedBefore;

        if (resource.getId() == 13) {
            fact_col = getEconomyNull();
        }
    }

    public float getUsedToday() {
        return usedToday;
    }

    public void setUsedToday(float usedToday) {
        this.usedToday = usedToday;
        if (resource.getId() == 13) {
            fact_col = getEconomyNull();
        }
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Transient
    private ShortInfoResource shortRes=new ShortInfoResource();
    
    public ShortInfoResource getShortResource() {
        return shortRes;
    }

    public void setShortResource(ShortInfoResource resource) {
        this.shortRes=resource;
        this.resource = new ShortInfoResourceConverter().toLong(resource);
    }
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public float getPlan_col() {
        return plan_col;
    }

    public void setPlan_col(float plan_col) {
        this.plan_col = plan_col;
    }

    public float getPlan_tut() {
        return plan_tut;
    }

    public void setPlan_tut(float plan_tut) {
        this.plan_tut = plan_tut;
    }

    public float getPlan_money() {
        if (report == null) {
            return 0;
        }

        if (report.getCostsShort() != null) {
            List<ShortInfoResource> costs = report.getCostsShort();
            int costIndex = (int) resource.getId();
            if (costs != null && costs.size() > costIndex && costs.get(costIndex) != null) {
                return plan_col * costs.get(costIndex).getCost();
            } else {
                return plan_money;
            }
        } else {
            //проверяет, есть ли в списке цен цена на данный ресурс
            //если есть-рассчитывает автоматически, если нет - возвращает цену из БД
            List<CostDetails> costs = report.getCosts();
            int costIndex = (int) resource.getId();
            if (costs != null && costs.size() > costIndex && costs.get(costIndex) != null) {
                return plan_col * costs.get(costIndex).getCost();
            } else {
                return plan_money;
            }
        }
    }

    public void setPlan_money(float plan_money) {
        this.plan_money = plan_money;
    }

    public float getFact_col() {
        return fact_col;
    }

    public long getAddID() {
        return getId();
    }

    public void setFact_col(float fact_col) {
        this.fact_col = fact_col;
    }

    public float getFact_tut() {
        return fact_tut;
    }

    public void setFact_tut(float fact_tut) {
        this.fact_tut = fact_tut;
    }

    public float getFact_money() {
        if (report == null) {
            return 0;
        }
        if (report.getCostsShort() != null) {
            List<ShortInfoResource> costs = report.getCostsShort();
            int costIndex = (int) resource.getId();
            if (costs != null && costs.size() > costIndex && costs.get(costIndex) != null) {
                return fact_col * costs.get(costIndex).getCost();
            } else {
                return fact_money;
            }
        } else {
            List<CostDetails> costs = report.getCosts();
            int costIndex = (int) resource.getId();
            if (costs != null && costs.size() > costIndex && costs.get(costIndex) != null) {
                return fact_col * costs.get(costIndex).getCost();
            } else {
                return fact_money;
            }
        }
    }

    public void setFact_money(float fact_money) {
        this.fact_money = fact_money;
    }

    public boolean equalsForFormSix(ReportDetails other) {
        if (this.getId() != other.getId()) {
            return false;
        }

        if (this.resource.getId() != other.resource.getId() && (this.resource == null)) {
            return false;
        }
        if ((this.activity == null) ? (other.activity != null) : !this.activity.equals(other.activity)) {
            return false;
        }
        return true;
    }

    public void copyFrpm(ReportDetails det) {
        this.report = det.report;
        this.dt_inputFact = det.dt_inputFact;
        this.dt_inputFact_test = det.dt_inputFact_test;
        this.activity = det.activity;
        this.plan_col = det.plan_col;
        this.plan_tut = det.plan_tut;
        this.plan_money = det.plan_money;
        this.fact_col = det.fact_col;
        this.fact_tut = det.fact_tut;
        this.fact_money = det.fact_money;
        this.responsible = det.responsible;
        this.addressOfObject = det.addressOfObject;
        this.powerSource = det.powerSource;
        this.type = det.type;
        this.num = det.num;
        this.askue = det.askue;
        this.addres = det.addres;
        this.usedBefore = det.usedBefore;
        this.usedToday = det.usedToday;
        this.resource = det.resource;
        this.setId(det.getId());
        this.setDeleted(det.isDeleted());
        this.setDt_create(det.getDt_create());
        this.setDt_end(det.getDt_end());
    }
}
