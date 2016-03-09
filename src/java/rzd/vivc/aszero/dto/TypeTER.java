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
 * Типы ресурсов для БД. Под Хибер
 *
 * @author VVolgina
 */
@Entity
@Table( name = "TYPE_TER")
@SuppressWarnings("serial")
public class TypeTER extends Data_Info {

    //<editor-fold defaultstate="collapsed" desc="Поля">
    @Column(name = "NAME", length = 50)
    private String name = "";
    @OneToMany(mappedBy = "typeTER", cascade = CascadeType.REMOVE)
    private List<Resource> resources;

    //</editor-fold>
    public TypeTER() {
    }

    public TypeTER(String name) {
         this.name=(new StringImprover()).getCutedString(name, 50);
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
       this.name=(new StringImprover()).getCutedString(name, 50);
    }

    /**
     * Возвращает список ресурсов данного типа.
     * @exception ВНИМАНИЕ! LazyInitialisation
     * @return список ресурсов данного типа.
     */
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * Назначает список ресурсов данного типа.
     * @param resources список ресурсов данного типа.
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
    //</editor-fold>
    
    @Override
    public String toString() {
        return "TypeTER{" + "name=" + name + '}';
    }   
}
