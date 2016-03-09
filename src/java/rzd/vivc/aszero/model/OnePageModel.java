/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.model;

import java.util.List;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.Resource;

/**
 * Модель с информацией из БД для formone.xhtml
 * @author VVolgina
 */
public class OnePageModel {
    //<editor-fold defaultstate="collapsed" desc="поля">
    //список доступных выидов ресерсов
    private List<Resource> list;
    //отчет по зеленой пятнице
    private Report rep;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает список доступных выидов ресерсов
     * @return список доступных выидов ресерсов
     */
    public List<Resource> getList() {
        return list;
    }
    
    /**
     * Назначает список доступных выидов ресерсов
     * @param list список доступных выидов ресерсов
     */
    public void setList(List<Resource> list) {
        this.list = list;
    }
    
    /**
     * Возвращает отчет по зеленой пятнице
     * @return отчет по зеленой пятнице
     */
    public Report getRep() {
        return rep;
    }
    
    /**
     * Назначает отчет по зеленой пятнице
     * @param rep отчет по зеленой пятнице
     */
    public void setRep(Report rep) {
        this.rep = rep;
    }
    //</editor-fold>
    
    
}
