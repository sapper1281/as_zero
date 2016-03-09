/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.session;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import rzd.vivc.aszero.model.UserModel;

/**
 * Сессионный бин. Хранится информация о пользователе и его правах на доступ к
 * страницам
 *
 * @author VVolgina
 */
@ManagedBean
@SessionScoped
public class AutorizationBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="поля">
    private UserModel user;
    private String error = "";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает сообщение о произошедшей ошибке
     *
     * @return сообщение о произошедшей ошибке
     */
    public String getError() {
        return error;
    }

    /**
     * Назначает сообщение о произошедшей ошибке
     *
     * @param error сообщение о произошедшей ошибке
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Возвращает информацию о зарегистрировавшемся о пользователе
     *
     * @return информация о зарегистрировавшемся о пользователе
     */
    public UserModel getUser() {
        return user;
    }

    /**
     * Назначает информацию о зарегистрировавшемся о пользователе
     *
     * @param user информация о зарегистрировавшемся о пользователе
     */
    public void setUser(UserModel user) {
        this.user = user;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="по кнопкам">
    /**
     * Выход пользователя из системы и переход на страницу авторизации
     *
     */
    public void logOut() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        error = "";
        user = null;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index");
        } catch (IOException ex) {
        }
    }
    //</editor-fold>
}
