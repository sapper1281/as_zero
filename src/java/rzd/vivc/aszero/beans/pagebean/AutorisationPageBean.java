/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.model.UserModel;
import rzd.vivc.aszero.repository.UserRepository;
import zdislava.model.dto.security.users.ExistingUserExeption;
import zdislava.model.dto.security.users.User;

/**
 * Бин для страницы index.xhtml
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class AutorisationPageBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="поля">
    private String login = "";
    private String password = "";
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean bean;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает введенный логин
     *
     * @return введенный логин
     */
    public String getLogin() {
        return login;
    }

    /**
     * Назначает введенный логин
     *
     * @param login введенный логин
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Возвращает введенный пароль
     *
     * @return введенный пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Назначает введенный пароль
     *
     * @param password введенный пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает сессию
     *
     * @return сессия
     */
    public AutorizationBean getBean() {
        return bean;
    }

    /**
     * Назначает сессию
     *
     * @param bean сессия
     */
    public void setBean(AutorizationBean bean) {
        this.bean = bean;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="авто">
    /**
     * Creates a new instance of AutorisationPageBean
     */
    public AutorisationPageBean() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="по кнопкам">
    /**
     * Осуществляет вход пользователя в систему
     *
     * @return error, при ошибке addDepartment или mainpage, при успешном входе
     * администратора и пользователя соответственно
     */
   /* public String logIn() {
        User userUser;
        UserModel modelUser = ((AutorizationBean) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("autorizationBean")).getUser();
        if (modelUser != null) {
            try {
                //извлекаем информацию о пользователе из БД
                userUser = (new UserRepository()).getUserWithoutRights(login, password);
                if (userUser.getId() != modelUser.getUserID()) {
                    bean.setError("Вы авторизованы в системе под другим пользователем. Необходимо сохранить данные и нажать кнопку Выход");
                    return "error";
                }
            } catch (ExistingUserExeption e) {
                //неверные логин/пароль
                bean.setError(e.getMessage());
                return "error";
            } catch (Exception e) {
                //ошибка связи с БД
                bean.setError("Ошибка соединения с БД");
                return "error";
            }
        } else {
            //if(FacesContext.getCurrentInstance().getExternalContext().getSession(false)!=nu)

            try {
                //извлекаем информацию о пользователе из БД
                userUser = (new UserRepository()).getUserWithoutRights(login, password);
            } catch (ExistingUserExeption e) {
                //неверные логин/пароль
                bean.setError(e.getMessage());
                bean.setUser(null);
                return "error";
            } catch (Exception e) {
                //ошибка связи с БД
                System.out.println("-------" + e);
                bean.setError("Ошибка соединения с БД");
                bean.setUser(null);
                return "error";
            }

            //добавляем информацию о пользователе в сессию
            bean.setUser(new UserModel(userUser.getId()));
            bean.getUser().setDepartmentID(userUser.getDepartment().getId());
            bean.getUser().getDepartmentPower();
            bean.getUser().setNum(userUser.getDepartment().getNum());
            bean.getUser().setIdType(userUser.getUserType().getId());
        }
        //посылаем на нужную страницу
        if (userUser.getUserType() != null && userUser.getUserType().getName().equalsIgnoreCase("Администратор")) {
            return "addDepartmentNew_1";
        }
        return "mainpage";

    }*/
    
    
    public String logIn() {
        User userUser;
        UserModel modelUser = ((AutorizationBean) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("autorizationBean")).getUser();
        if (modelUser != null) {
            try {
                //извлекаем информацию о пользователе из БД
                userUser = (new UserRepository()).getUserWithoutRights(login, password);
                if (userUser.getId() != modelUser.getUserID()) {
                    bean.setError("Вы авторизованы в системе под другим пользователем. Необходимо сохранить данные и нажать кнопку Выход");
                    return "error";
                }
            } catch (ExistingUserExeption e) {
                //неверные логин/пароль
                bean.setError(e.getMessage());
                return "error";
            } catch (Exception e) {
                //ошибка связи с БД
                bean.setError("Ошибка: " + e.getMessage());
                Logger.getLogger(AutorisationPageBean.class.getName()).log(Level.SEVERE, null, e);
                return "error";
            }
        } else {
            //if(FacesContext.getCurrentInstance().getExternalContext().getSession(false)!=nu)

            try {
                //извлекаем информацию о пользователе из БД
                userUser = (new UserRepository()).getUserWithoutRights(login, password);
            } catch (ExistingUserExeption e) {
                //неверные логин/пароль
                bean.setError(e.getMessage());
                bean.setUser(null);
                return "error";
            } catch (Exception e) {
                //ошибка связи с БД
                System.out.println("-------" + e);
                bean.setError("Ошибка: " + e.getMessage());
                bean.setUser(null);
                Logger.getLogger(AutorisationPageBean.class.getName()).log(Level.SEVERE, null, e);
                return "error";
            }

            //добавляем информацию о пользователе в сессию
            bean.setUser(new UserModel(userUser.getId()));
            bean.getUser().setDepartmentID(userUser.getDepartment().getId());
            bean.getUser().getDepartmentPower();
            bean.getUser().setNum(userUser.getDepartment().getNum());
            bean.getUser().setIdType(userUser.getUserType().getId());
        }
        //посылаем на нужную страницу
        if (userUser.getUserType() != null && userUser.getUserType().getName().equalsIgnoreCase("Администратор")) {
            return "addDepartmentNew_1";
        }
         if (userUser.getUserType() != null && userUser.getUserType().getName().equalsIgnoreCase("Технолог")) {
            return "addTreeDepartment";
                   // "addDepartmentNew_1";
        }
        return "mainpage";

    }

    //</editor-fold>
}
