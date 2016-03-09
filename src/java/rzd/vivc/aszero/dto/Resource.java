/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import rzd.vivc.aszero.service.StringImprover;

/**
 * Ресурсы для БД. Под Хибер
 * @author VVolgina
 */
@Entity
@Table( name = "RESOURCE")
@SuppressWarnings("serial")
public class Resource extends Data_Info{
    
    //<editor-fold defaultstate="collapsed" desc="Поля">
    @Column(name = "NAME",length =100)
    private String name = "";    
    @ManyToOne( optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_TER_ID")
    private TypeTER typeTER;    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEASURE_ID", nullable = false)
    private Measure measure;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resource")
    private List<CostDetails> cost;
    private float koef;
    private float koef_polution;

    //</editor-fold>
    /**
     * Возвращает коэффициент для вычисления скращения загрязняющих выбросов в окр. среду
     * @return коэффициент для вычисления скращения загрязняющих выбросов в окр. среду
     */
    public float getKoef_polution() {
        return koef_polution;
    }

    /**
     * Назначает коэффициент для вычисления скращения загрязняющих выбросов в окр. среду
     * @param koef_polution коэффициент для вычисления скращения загрязняющих выбросов в окр. среду
     */
    public void setKoef_polution(float koef_polution) {
        this.koef_polution = koef_polution;
    }
    
    
    
     public String getEXELString(){
        return name.isEmpty()?"":name+","+measure.getValue();
    }

    /**
     * Вернуть список цен на ресурс
     * @return список цен на ресурс
     */
    public List<CostDetails> getCost() {
        return cost;
    }

    /**
     * Назначит список цен на ресурс
     * @param cost список цен на ресурс
     */
    public void setCost(List<CostDetails> cost) {
        this.cost = cost;
    }
    
    public float getKoef() {
        return koef;
    }

    public void setKoef(float koef) {
        this.koef = koef;
    }

    public Resource() {
    }

    /**
     *
     * @param name
     * @param koef_polution
     */
    public Resource(String name, float koef_polution, long id, float cost) {
        this.koef_polution = koef_polution;
        this.name=name;
        this.setId(id);
        this.cost=new ArrayList<CostDetails>();
        this.cost.add(new CostDetails(cost));
    }
    
    

    public Resource(String name) {
         this.name=(new StringImprover()).getCutedString(name, 100);
    }
    
    public Resource(long id) {
        setId(id);
    }
    
    public Resource(String name, long measureID, long typeID){
        this(name);
        measure=new Measure();
        measure.setId(measureID);
        typeTER=new TypeTER();
        typeTER.setId(typeID);     
    }
    
    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {   
         this.name=(new StringImprover()).getCutedString(name,100);
    }

    /**
     * Возвращает тип ресурса. 
     * @exception ВНИМАНИЕ! LazyInitialisation
     * @return тип ресурса
     */
    public TypeTER getTypeTER() {
        return typeTER;
    }

    /**
     * Назначает тип ресурса
     * @param typeTER тип ресурса
     */
    public void setTypeTER(TypeTER typeTER) {
        this.typeTER = typeTER;
    }
    
        /**
     * Get the value of measure
     *
     * @return the value of measure
     */
    public Measure getMeasure() {
        return measure;
    }

    /**
     * Set the value of measure
     *
     * @param measure new value of measure
     */
    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
  
    
    public String getString(){
        return measure==null?name:(name.isEmpty()?"":name+","+measure.getValueHTML());
    }

    //</editor-fold>
    @Override
    public String toString() {
        return getId()+"";
    }
    
   
    
  
}
