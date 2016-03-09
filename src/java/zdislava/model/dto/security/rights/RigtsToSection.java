package zdislava.model.dto.security.rights;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import zdislava.model.dto.security.users.UserType;

/**
 * Информация о Правах для доступа разных групп пользователей к секциям, на
 * которые разбит сайт. Для хранения информаци в БД анотации под Hibernate
 *
 * @author Zdislava
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "RIGHTS")
public class RigtsToSection extends Data_Info {

    //<editor-fold defaultstate="collapsed" desc="поля">
    @Column(name = "VISIBLE")
    private boolean visible = false;
    @Column(name = "ENABLED")
    private boolean enabled = false;
    @ManyToOne
    @JoinColumn(name = "SECTION_ID")
    private Section section;
    @ManyToOne
    @JoinColumn(name = "USER_TYPE_ID")
    private UserType userType;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Проверяет доступность секции на просмотр.
     *
     * @return true-если секцию можно промсмотреть, false - иначе
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Назначает доступность секции на просмотр.
     *
     * @param visible доступность секции на просмотр
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Проверяет доступность секции на редактирование.
     *
     * @return true-если секцию можно редактировать, false - иначе
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Назначает доступность секции на редактирование.
     *
     * @param enabled доступность секции на редактирование
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Возвращает секцию, к кторой относятся права
     *
     * @return секция, к кторой относятся права
     */
    public Section getSection() {
        return section;
    }

    /**
     * Назначает секцию, к кторой относятся права
     *
     * @param section секция, к кторой относятся права
     */
    public void setSection(Section section) {
        this.section = section;
    }

    /**
     * Возвращает группу пользователей, обладающую данными правами
     *
     * @return группа пользователей, обладающая данными правами
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Назначает группу пользователей, обладающую данными правами
     *
     * @param userType группа пользователей, обладающая данными правами
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "RigtsToSection [id=" + super.getId() + ", visible=" + visible
                + ", enabled=" + enabled + ", section=" + section
                + ", userType=" + userType.getName() + "]";
    }
}
