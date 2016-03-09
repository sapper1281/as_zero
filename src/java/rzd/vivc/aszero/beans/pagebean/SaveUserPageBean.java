/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import rzd.vivc.aszero.beans.pagebean.DepartmentPageBean;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.UserRepository;
import rzd.vivc.aszero.repository.UserTypeRepository;
import zdislava.model.dto.security.users.ExistingUserExeption;
import zdislava.model.dto.security.users.User;
import zdislava.model.dto.security.users.UserType;

/**
 * Для страницы saveuser.xhtml
 *
 * @author VVolgina
 */
@ManagedBean
@ViewScoped
public class SaveUserPageBean implements Serializable{

    //<editor-fold defaultstate="collapsed" desc="поля">
    private User item;
    private String oldLogin;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private List<UserType> types=new ArrayList<UserType>();

    //<editor-fold defaultstate="collapsed" desc="get-set">
    public List<UserType> getTypes() {
        return types;
    }

    public void setTypes(List<UserType> types) {
        this.types = types;
    }

    
            
    /**
     * Возвращает объект пользователя, редактирование которой произодится на
     * странице
     *
     * @return объект пользователя, редактирование которой произодится на
     * странице
     */
    public User getItem() {
        return item;
    }

    /**
     * Назначаеи объект пользователя, редактирование которой произодится на
     * странице
     *
     * @param item объект пользователя, редактирование которой произодится на
     * странице
     */
    public void setItem(User item) {
        this.item = item;
    }

    /**
     * Возвращает логин пользователя, кторый был до начала редактирования
     *
     * @return логин пользователя, кторый был до начала редактирования
     */
    public String getOldLogin() {
        return oldLogin;
    }

    /**
     * Назначает логин пользователя, кторый был до начала редактирования
     *
     * @param oldLogin логин пользователя, кторый был до начала редактирования
     */
    public void setOldLogin(String oldLogin) {
        this.oldLogin = oldLogin;
    }

    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    //</editor-fold>
    /**
     * Creates a new instance of SavingBean
     */
    public SaveUserPageBean() {
        types=(new UserTypeRepository()).getAllNonDeleted(UserType.class);
        long id = 0;
        long dep = 0;
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id")) {
            try {
                id = new Long(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString());
            } catch (Exception e) {
                id = 0;
            }
        }
         if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("dep")) {
            try {
                dep = new Long(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dep").toString());
            } catch (Exception e) {
                dep = 0;
            }
        }
        loadInfo(id, dep);
    }

    private void loadInfo(long id, long dep) {
        if (id != 0) {
            item=new User(id);
            item = (new UserRepository()).get(item);
            oldLogin=item.getLogin();
        } else {
            item = new User();
            Department depart=(new DepartmentRepository()).get(dep);
            item.setDepartment(depart);
            oldLogin="";
        }
    }

    /**
     * Сохраняет информацию о пользователе
     *
     * @return "error", если при сохранении произошла ошибка, "list" если
     * сохранение произошло успешно
     */
    public String saveItem() {
        try {
            //если мы зашли на страницу для создания нового пользователя
            if (oldLogin.isEmpty()) {
                //создаем
                createUser(item);
            } else {
                //иначе сохраняем изменения в старом
                if (oldLogin.equals(item.getLogin())) {
                    saveUser(item);
                } else {
                    saveUserWhithLoginCheck(item);
                }
            }
            oldLogin = "";
        } catch (ExistingUserExeption ex) {
            session.setError(ex.getMessage());
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("errorpage.xhtml");
            } catch (IOException ex1) {
                Logger.getLogger(SaveUserPageBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return "errorpage.xhtml";
        } catch (Exception ex) {
            session.setError("Ошибка соединения с БД");
             try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("errorpage.xhtml");
            } catch (IOException ex1) {
                Logger.getLogger(SaveUserPageBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return "errorpage.xhtml";
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("addDepartment.xhtml?tab=2");
        } catch (IOException ex) {
            Logger.getLogger(SaveUserPageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "users.xhtml";

    }

    /**
     * Удаляет информацию о секции
     *
     * @return "error", если при удалении произошла ошибка, "list" если удалении
     * произошло успешно
     */
    public String deleteItem() {
        try {
            deleteUser(item);
        }  catch (ExistingUserExeption ex) {
            session.setError(ex.getMessage());
            return "errorpage.xhtml";
        } catch (Exception ex) {
            session.setError("Ошибка соединения с БД");
            return "errorpage.xhtml";
        }
        return "users.xhtml";

    }
    
    public String repareItem() {
        try {
            repareUser(item);
        }  catch (ExistingUserExeption ex) {
            session.setError(ex.getMessage());
            return "errorpage.xhtml";
        } catch (Exception ex) {
            session.setError("Ошибка соединения с БД");
            return "errorpage.xhtml";
        }
        return "users.xhtml";

    }

    /**
     * Обновляет в БД информацию опользователя
     *
     * @throws ExistingUserExeption если в БД отсутствует пользователь с таким
     * логинм
     */
    private void saveUser(User itm) throws ExistingUserExeption {
        UserRepository rep = new UserRepository();
        rep.changeExistingUser(itm);
    }
    
    
    /**
     * Обновляет в БД информацию опользователе вместе с его логином
     *
     * @throws ExistingUserExeption если в БД уже есть пользователь с логином, на который хотим поменять
     */
    private void saveUserWhithLoginCheck(User itm) throws ExistingUserExeption {
        UserRepository rep = new UserRepository();
        rep.changeExistingUserWhithLoginCheck(itm);
    }


    /**
     * Сохраняет в БД нового пользователя
     *
     * @throws ExistingUserExeption если в БД уже существует пользователь с
     * таким логинм
     */
    private void createUser(User itm) throws ExistingUserExeption {
        UserRepository rep = new UserRepository();
        rep.addNewUser(itm);
    }

    /**
     * Помечаеи пользователя в БД как удаленного
     *
     * @throws ExistingUserExeption если в БД не найден пользователь
     */
    private void deleteUser(User itm) throws ExistingUserExeption {
        UserRepository rep = new UserRepository();
        item.setDeleted(true);
        rep.changeExistingUser(itm);
    }
    
    private void repareUser(User itm) throws ExistingUserExeption {
        UserRepository rep = new UserRepository();
        item.setDeleted(false);
        rep.changeExistingUser(itm);
    }
}
