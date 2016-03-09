package zdislava.model.dto.security.users;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.baseclass.Data_Info;
import rzd.vivc.aszero.dto.form.Form;

/**
 * Описание пользователя системы. ФИО, логин-пароль, права(тип пользователя) Для
 * БД. Анотации под хибернейт
 *
 * @author Zdislava
 *
 */
@Entity
@Table(name = "USER")
@SuppressWarnings("serial")
public class User extends Data_Info {

    //<editor-fold defaultstate="collapsed" desc="Поля">
    @Column(name = "NAME")
    private String name = "";
    @Column(name = "SURNAME")
    private String surname = "";
    @Column(name = "PATRONOMICNAME")
    private String patronomicname = "";
    @Column(name = "PHONE")
    private String phoneNumber = "";
    @ManyToOne
    @JoinColumn(name = "department", nullable = false)
    private Department department = new Department();
    @NaturalId(mutable = true)
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "PASSWORD")
    private String password;
   // private boolean 
    /* @ManyToOne
     @JoinColumn(name = "USER_TYPE_ID", nullable = false)
     private UserType userType;*/
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "USER_FORM", joinColumns = {
        @JoinColumn(name = "USER_ID", nullable = false)},
            inverseJoinColumns = {
        @JoinColumn(name = "FORM_ID",
                nullable = false)})
    private List<Form> forms = new ArrayList<Form>();
    @ManyToOne
    @JoinColumn(name = "USER_TYPE_ID", nullable = true)
    private UserType userType;

    //</editor-fold>
    public User() {
    }

    public User(long id) {
        this.setId(id);
    }

    //<editor-fold defaultstate="collapsed" desc="get-set">
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * возвращвет имя пользователя
     *
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * назначает имя пользователя
     *
     * @param name имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * возвращает фамилию пользователя
     *
     * @return фамилия
     */
    public String getSurname() {
        return surname;
    }

    /**
     * назначает фамилию пользователя
     *
     * @param surname фамилия
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * возвращает отчество пользователя
     *
     * @return отчество
     */
    public String getPatronomicname() {
        return patronomicname;
    }

    /**
     * назначает отчество пользователя
     *
     * @param patronomicname отчество
     */
    public void setPatronomicname(String patronomicname) {
        this.patronomicname = patronomicname;
    }

    /**
     * Возвращает ФИО пользователя
     *
     * @return ФИО
     */
    public String getFIO() {
        return surname + " " + name + " " + patronomicname;
    }

    /**
     * Возвращает службу, в которой работает пользователь
     *
     * @return службу, в которой работает пользователь
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * службу, в которой работает пользователь
     *
     * @param department службу, в которой работает пользователь
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * возвращает логин
     *
     * @return логин
     */
    public String getLogin() {
        return login;
    }

    /**
     * назначает логин
     *
     * @param login логин
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * возвращает пароль
     *
     * @return пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * назначает пароль
     *
     * @param password пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает тип пользователя. Тип пользователя определяет права в системе
     *
     * @return тип пользователя
     */
    /*  public UserType getUserType() {
     return userType;
     }*/
    /**
     * Назначает тип пользователя. Тип пользователя определяет права в системе
     *
     * @param userType тип пользователя
     */
    /*  public void setUserType(UserType userType) {
     this.userType = userType;
     }*/
    
    public String getFormsString(){
        String res="";
        for (Form form : forms) {
            res+=form.getShortNameForm()+", ";
        }
        return res.length()>0?res.substring(0, res.length()-2):"";
    }
    //</editor-fold>
    @Override
    public String toString() {
        return "name=" + name + ", surname=" + surname + ", patronomicname=" + patronomicname + super.toString();
    }

   /* public String getRightsInfo() {
        if (isDeleted()) {
            return "Удален";
        } else {
            StringBuilder r=new StringBuilder();
            f
        }
    }*/
}
