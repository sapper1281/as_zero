/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.model;

import rzd.vivc.aszero.dto.Department;

/**
 * НЕ ИМПОЛЬЗУЕТСЯ! ВОЗМОЖНО ПОТОМ УДАЛЮ. Групировка подразделения и инфо Id его дороги
 * @author VVolgina
 */
public class DepartmentParentModel {
    //<editor-fold defaultstate="collapsed" desc="поля">
    //подразделение
    Department dep;
    //id его дороги
    long id;
    //</editor-fold>

    /**
     * Создает модель
     * @param dep подразделение
     * @param id id дороги
     */
    public DepartmentParentModel(Department dep, long id) {
        this.dep = dep;
        this.id = id;
    }

    //<editor-fold defaultstate="collapsed" desc="Get-set">
    /**
     * Возвращает подразделение
     * @return подразделение
     */
    public Department getDep() {
        return dep;
    }
    
    /**
     * Возвращает id дороги
     * @return id дороги
     */
    public long getId() {
        return id;
    }
    //</editor-fold>
}
