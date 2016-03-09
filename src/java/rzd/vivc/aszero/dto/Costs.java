/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import rzd.vivc.aszero.service.StringImprover;
import zdislava.model.dto.security.users.User;

/**
 * Модель данных для цен на ресурсы. Анотации под Hibernateю общин данные для всех ресурсов за месяц
 * @author VVolgina
 */
@Entity
@Table( name = "COST")
@SuppressWarnings("serial")
public class Costs extends Data_Info{
    //<editor-fold defaultstate="collapsed" desc="поля">
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "common")
    private List<CostDetails> details=new ArrayList<CostDetails>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User usr;
    private long railway;
    private byte year;
    private byte month;

    //</editor-fold>
    
    public Costs() {
    }

    /**
     * лучше пользоваться этим. тогда не забудешь проставить юзера
     * @param usr пользователь, последним создавший запись
     */
    public Costs(User usr) {
        this.usr = usr;
    }

    public Costs(long id) {
        setId(id);
    }
    
    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает список цен по всем ресурсам
     * @return список цен по всем ресурсам
     */
    public List<CostDetails> getDetails() {
        return details;
    }

    /**
     * Назначает список цен по всем ресурсам
     * @param details список цен по всем ресурсам
     */
    public void setDetails(List<CostDetails> details) {
        this.details = details;
    }
    
    /**
     * Добаляет в список цену на ресурс
     * @param details цена на ресурс
     */
    public void addDetails(CostDetails details) {
        details.setCommon(this);
        this.details.add(details);
    }
    
     /**
     * Находит в списке цену для данного ресурса
     * @param res ресурс
     * @return цена
     */
    public CostDetails getCostForResource(Resource res){
        return getCostForResource(res.getId());
    }
    
    /**
     * Находит в списке цену для данного ресурса
     * @param resID ID ресурса
     * @return цена
     */
    public CostDetails getCostForResource(long resID){
        for (CostDetails costDetails : details) {
            if(costDetails.getResource().getId()==resID){
                return costDetails;
            }
        }
        return null;
    }
    
    /**
     * Возвращает пользователя, последним изменившего запись
     * @return пользователь, последним изменивший запись
     */
    public User getUsr() {
        return usr;
    }
    
    /**
     * Назначает пользователя, последним изменившего запись
     * @param usr пользователь, последним изменивший запись
     */
    public void setUsr(User usr) {
        this.usr = usr;
    }
    
    /**
     * Возвращает Id дороги, к которой относится запись
     * @return Id дороги, к которой относится запись
     */
    public long getRailway() {
        return railway;
    }
    
    /**
     * Назначает Id дороги, к которой относится запись
     * @param railway Id дороги, к которой относится запись
     */
    public void setRailway(long railway) {
        this.railway = railway;
    }
    
    /**
     * Возвращает месяц, для которого актуальна эта цена 
     * @return месяц, для которого актуальна эта цена 
     */
    public byte getMonth() {
        return month;
    }
    
    /**
     * Назначает месяц, для которого актуальна эта цена 
     * @param month месяц, для которого актуальна эта цена 
     */
    public void setMonth(byte month) {
        this.month = month;
    }
    
     /**
     * Возвращает год, для которого актуальна эта цена 
     * @return год, для которого актуальна эта цена 
     */
    public byte getYear() {
        return year;
    }

    /**
     * Назначает год, для которого актуальна эта цена 
     * @param year год, для которого актуальна эта цена 
     */
    public void setYear(byte year) {
        this.year = year;
    }
    
    /**
     * Возвращает дату актуальности
     * @return дата актуальности
     */
    public Date getDate(){
        return new StringImprover().getDateByMonthAndYear(month, year);
    }

    //</editor-fold>
    
    @Override
    public String toString() {
        return "Costs{ id=" +getId()+  ", railway=" + railway + ", dat=" + getDate()+'}';
    }
    
    
}
