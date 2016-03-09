/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import rzd.vivc.aszero.service.StringImprover;

/**
 * Величины змерения для ресурсов для БД. Под Хибер
 *
 * @author VVolgina
 */
@Entity
@Table( name = "MEASURE")
@SuppressWarnings("serial")
public class Measure extends Data_Info {

    //<editor-fold defaultstate="collapsed" desc="Поля">
    @Column(name = "VALUE", length = 20)
    private String value = "";
    @Column(name = "VALUE_HTML", length = 20)
    private String valueHTML = "";
    //</editor-fold>
    public Measure() {
    }

    public Measure(String value) {
         this.value=(new StringImprover()).getCutedString(value, 20);
    }

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(String value) {
       this.value=(new StringImprover()).getCutedString(value, 20);
    }

    public String getValueHTML() {
        return valueHTML;
    }

    public void setValueHTML(String valueHTML) {
        this.valueHTML =(new StringImprover()).getCutedString(valueHTML, 20);
    }
    
    

    //</editor-fold>
    @Override
    public String toString() {
        return "Measure{" + "value=" + value + '}';
    }
}
