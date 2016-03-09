/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean.pageinfo;

import java.io.Serializable;
import java.util.Date;

/**
 * id и название объекта
 * @author VVolgina
 */
public class ShortInfoReport implements Serializable{
    //id
    private long id;
    // фио пользователя
    private String FIO;
    //дата начала
    private Date dt_begin;
    //id формы отчета
    private long formID;

    /**
     *id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     *id
     * @param id id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *название отчета
     * @return название
     */
    public String getName() {
        return FIO + " за " + (getDt_begin().getMonth() + 1) + " месяц " + (getDt_begin().getYear() + 1900) + " года";
    }

    /**
     *фио пользователя
     * @return фио пользователя
     */
    public String getFIO() {
        return FIO;
    }

    /**
     *фио пользователя
     * @param FIO фио пользователя
     */
    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    /**
     *дата начала
     * @return дата начала
     */
    public Date getDt_begin() {
        return dt_begin;
    }

    /**
     *дата начала
     * @param dt_begin дата начала
     */
    public void setDt_begin(Date dt_begin) {
        this.dt_begin = dt_begin;
    }

    /**
     *id формы отчета
     * @return id формы отчета
     */
    public long getFormID() {
        return formID;
    }

    /**
     *id формы отчета
     * @param formID id формы отчета
     */
    public void setFormID(long formID) {
        this.formID = formID;
    }
    
    

    /**
     *
     * @param id id
     * @param FIO фио пользователя
     * @param dt_begin дмта акции
     * @param formID id формы отчета
     */
    public ShortInfoReport(long id, String FIO, Date dt_begin, long formID) {
        this.id = id;
        this.FIO = FIO;
        this.dt_begin = dt_begin;
        this.formID = formID;
    }

    @Override
    public String toString() {
        return "ShortInfoReport{" + "id=" + id + ", FIO=" + FIO + ", dt_begin=" + dt_begin + ", formID=" + formID + '}';
    }
    
    
}
