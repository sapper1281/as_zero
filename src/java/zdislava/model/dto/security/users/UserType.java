





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
public class UserType extends Data_Info {

    //<editor-fold defaultstate="collapsed" desc="поля">
    @Column(name = "NAME")
    private String name;

    public UserType() {
    }

    //</editor-fold>

    public UserType(long id) {
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







/*package zdislava.model.dto.security.users;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import zdislava.model.dto.security.rights.RigtsToSection;

@SuppressWarnings("serial")
@Entity
@Table(name = "USER_TYPE")
public class UserType extends Data_Info {

    //<editor-fold defaultstate="collapsed" desc="поля">
    @Column(name = "NAME")
    private String name;
    // При удалении типа пользователя также удалятся все его права и пользователи данного типа
    @OneToMany(mappedBy = "userType", cascade = CascadeType.REMOVE)
    private List<RigtsToSection> rights = new ArrayList<RigtsToSection>();
    @OneToMany(mappedBy = "userType", cascade = CascadeType.REMOVE)
    private List<User> users = new ArrayList<User>();
    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="get-set">
   
    public String getName() {
        return name;
    }
    
   
    public void setName(String name) {
        this.name = name;
    }
    
    
    public List<RigtsToSection> getRights() {
        return rights;
    }
    
    
    public void setRights(List<RigtsToSection> rights) {
        this.rights = rights;
    }
    
    
    public List<User> getUsers() {
        return users;
    }
    
   
    public void setUsers(List<User> users) {
        this.users = users;
    }
    //</editor-fold>
   

    @Override
    public String toString() {
        return "UserType [id=" + super.getId() + ", name=" + name + "]";
    }
}
*/