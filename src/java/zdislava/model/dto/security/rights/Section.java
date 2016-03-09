package zdislava.model.dto.security.rights;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;

/**
 * Информация о секциях, на которые разбит сайт. К разным секциям разные группы
 * пользователей имеют разные права доступа Для хранения информаци в БД анотации
 * под Hibernate
 *
 * @author Zdislava
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SECTION")
public class Section extends Data_Info {

    @Column(name = "NAME")
    private String name;
    @Column(name = "VIEWNAME")
    private String nameForMenu;
    @Column(name = "DESCR")
    private String description;
    @OneToMany(mappedBy = "section", cascade = CascadeType.REMOVE)
    private List<RigtsToSection> rights = new ArrayList<RigtsToSection>();

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает название секции, т.е. адрес страгицы без .xhtml
     *
     * @return название секции, т.е. адрес страгицы без .xhtml
     */
    public String getName() {
        return name;
    }

    /**
     * Назначает название секции, т.е. адрес страгицы без .xhtml
     *
     * @param name название секции, т.е. адрес страгицы без .xhtml
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает название секции в том виде, в котором оно должно появляться в
     * меню
     *
     * @return название секции в том виде, в котором оно должно появляться в
     * меню
     */
    public String getNameForMenu() {
        return nameForMenu;
    }

    /**
     * Назначает название секции в том виде, в котором оно должно появляться в
     * меню
     *
     * @param nameForMenu название секции в том виде, в котором оно должно
     * появляться в меню
     */
    public void setNameForMenu(String nameForMenu) {
        this.nameForMenu = nameForMenu;
    }

    /**
     * Возвращает описание секции
     *
     * @return описание секции
     */
    public String getDescription() {
        return description;
    }

    /**
     * Назначает описание секции
     *
     * @param description описание секции
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает список прав на обращение к секции
     *
     * @return список прав на обращение к секции
     */
    public List<RigtsToSection> getRights() {
        return rights;
    }

    /**
     * Назначает список прав на обращение к секции
     *
     * @param rights список прав на обращение к секции
     */
    public void setRights(List<RigtsToSection> rights) {
        this.rights = rights;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "Section [id=" + super.getId() + ", name=" + name + ", nameForMenu="
                + nameForMenu + ", description=" + description + "]";
    }
}
