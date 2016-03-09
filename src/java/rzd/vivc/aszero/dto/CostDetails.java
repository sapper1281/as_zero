/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;

/**
 * Модель данных для цен на ресурсы. Анотации под Hibernate
 * @author VVolgina
 */
@Entity
@Table( name = "COSTS_DETAILS")
@SuppressWarnings("serial")
public class CostDetails{
    //<editor-fold defaultstate="collapsed" desc="поля">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "common_info_id")
    private Costs common;
    private float cost;
    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;
    //</editor-fold>
    
    public CostDetails() {
    }

    public CostDetails(long id) {
        this.id=id;
    }

    public CostDetails(Resource resource) {
        this.resource = resource;
    } 

    public CostDetails(float cost) {
        this.cost = cost;
    }
    
    
    
    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает id. Генерируется автоматически
     *
     * @return id
     */
    public long getId() {
        return id;
    }
    
    /**
     * Назначает id. Генерируется автоматически
     *
     * @param id id
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Возвращает общую для всех цен по данной дороге за месяц инфу
     * @return общая для всех цен по данной дороге за месяц инфа
     */
    public Costs getCommon() {
        return common;
    }

    /**
     * Назначает общую для всех цен по данной дороге за месяц инфу
     * @param common общая для всех цен по данной дороге за месяц инфа
     */
    public void setCommon(Costs common) {
        this.common = common;
    }
        
    /**
     * Возвращает цену на ресурс
     * @return цена на ресурс
     */
    public float getCost() {
        return cost;
    }
    
    /**
     * Назначает цену на ресурс
     * @param cost цена на ресурс
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * Возвращает вид ресурса
     * @return вид ресурса
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Назначает вид ресурса
     * @param resource вид ресурса
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    //</editor-fold>
    
    @Override
    public String toString() {
        return "Costs{ id=" +id+ ", cost=" + cost + ", resource=" + resource.getName() +" "+resource.getId() + '}';
    }
    
    
}
