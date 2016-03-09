/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto.baseclass;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import rzd.vivc.aszero.service.StringImprover;

/**
 * Базовый класс для классов под хибернейт
 *
 * @author apopovkin
 */
@MappedSuperclass
public class Data_Info implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Поля">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "dt_end")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_end;
    @Column(name = "dt_create", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt_create = new Date();
    @Column(name = "del_fl")
    private boolean deleted=false;
    //</editor-fold>
    
    
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
     * Извлекает дату окончания актуальности записи
     *
     * @return Извлекает дату окончания актуальности записи
     */
    public Date getDt_end() {
        return dt_end;
    }
    
    /**
     * Добавляет дату окончания актуальности записи
     *
     * @return Добавляет дату окончания актуальности записи
     */
    public void setDt_end(Date dt_end) {
        this.dt_end = dt_end;
    }
    
    /**
     * Извлекает дату создания. Назначить ее нельзя. Устанавливается при создании объекта текущим временем.
     * При клонировании объета для БД не переносится, т.е. сохраняется та, что была у объета из БД
     *
     * @return Извлекает дату создания
     */
    public Date getDt_create() {
        return dt_create;
    }
    
    /**
     * Добавить дату создания
     *
     * @return Добавить дату создания
     */
    public void setDt_create(Date dt_create) {
        this.dt_create = dt_create;
    }
    
    public String getDt_createString(){
        return (new StringImprover()).getDateString(dt_create);
    }
    
    /**
     * Возвращает индикатор удаления объекта
     * @return индикатор удаления объекта
     */
    public boolean isDeleted() {
        return deleted;
    }
    
    /**
     * Назначает индикатор удаления объекта
     * @param deleted индикатор удаления объекта
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    //</editor-fold>
    /**
     * Клонирует инфо из параметра
     * @param dat отсюда копеируем инфо
     */
    public Data_Info(Data_Info dat) {
        this.id = dat.id;
        this.dt_end = (Date) dat.dt_end.clone();
        this.dt_create=(Date) dat.dt_create.clone();
        this.deleted=dat.deleted;
    }

    public Data_Info() {
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Data_Info other = (Data_Info) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    /**
     * Переносит информацию из объекта параметра в данный. Всю, кроме id и даты создания
     *
     * @param obj объект-параметр
     */
    public void cloneForDB(Data_Info obj) {
        dt_end=obj.dt_end;
        deleted=obj.deleted;      
    }

    @Override
    public String toString() {
        return ", dt_end=" + dt_end + ", dt_create=" + dt_create + ", deleted=" + deleted +"id=" + id;
    }
}
