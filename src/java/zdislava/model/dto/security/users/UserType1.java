package zdislava.model.dto.security.users;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import zdislava.model.dto.security.rights.RigtsToSection;

/**
 * Описывает возможные типы пользователей в системе. Пользователи разных типов
 * могут обладать разными правами при доступе к элементам системы Например
 * пользователь, только просмотр, администратор. Для БД. Анотации под хибернейт
 *
 * @author Zdislava
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USER_TYPE")
public class UserType1 extends Data_Info {

    //<editor-fold defaultstate="collapsed" desc="поля">
    @Column(name = "NAME")
    private String name;

    public UserType1() {
    }

    //</editor-fold>

    public UserType1(long id) {
       super();
       setId(id);
    }


    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * возвращает название типа пользователя
     *
     * @return название типа пользователя
     */
    public String getName() {
        return name;
    }
    
    /**
     * назначает название типа пользователя
     *
     * @param name название типа пользователя
     */
    public void setName(String name) {
        this.name = name;
    }
    
   
    //</editor-fold>
   

    @Override
    public String toString() {
        return "UserType [id=" + super.getId() + ", name=" + name + "]";
    }
}
