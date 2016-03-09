/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.model.FormEnabled;
import rzd.vivc.aszero.repository.FormRepository;
import rzd.vivc.aszero.repository.UserRepository;
import rzd.vivc.aszero.service.Constants;

/**
 * К странице includesaveuser.xhtml Сохранение информации оправах пользователя
 *
 * @author VVolgina
 */
@ManagedBean
@ViewScoped
public class SaveRightsPageBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="поля">
    //информация о пользователе
    @ManagedProperty(value = "#{saveUserPageBean1}")
    private SaveUserPageBean1 userBean;
    //список имеющихся форм и наличия прав на них
    private List<FormEnabled> list;
    private long id;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="get-set">

    /**
     * Возвращает бин с информацией о пользователе
     *
     * @return бин с информацией о пользователе
     */
    public SaveUserPageBean1 getUserBean() {
        return userBean;
    }

    /**
     * Назначает бин с информацией о пользователе
     *
     * @param userBean бин с информацией о пользователе
     */
    public void setUserBean(SaveUserPageBean1 userBean) {
        this.userBean = userBean;
    }

    /**
     * Возвращает список форм с правами на них
     *
     * @return список форм с правами на них
     */
    public List<FormEnabled> getList() {
        if (userBean.getItem().getId() != 0 && userBean.getItem().getId()!=id) {
            //получаем из БД уже имеющиеся права
            userBean.setItem((new UserRepository()).getUserWithForms(userBean.getItem().getId()));

            //составляем список доступных форм и прав на них
            //для каждой формы добавляем прва, если они есть в БД 
            //иначе добавляем форму с отсутствующими правами
            list = new ArrayList<FormEnabled>();
            List<Form> forms = (new FormRepository()).getActiveForm();
            for (Form form : forms) {
                List<Form> forms1 = userBean.getItem().getForms();
                boolean found = false;
                for (Form form1 : forms1) {
                    if (form1.getId() == form.getId()) {
                        list.add(new FormEnabled(form, found = true));
                        break;
                    }
                }
                if (!found) {
                    list.add(new FormEnabled(form, false));
                }
            }
        }
        return list;
    }

    /**
     * Назначает список форм с правами на них
     *
     * @param list список форм с правами на них
     */
    public void setList(List<FormEnabled> list) {
        this.list = list;
    }
    //</editor-fold>

    /**
     * Creates a new instance of SavingBean
     */
    public SaveRightsPageBean() {
    }

    @PostConstruct
    public void Init() {
        if (userBean.getItem().getId() != 0) {
            //получаем из БД уже имеющиеся права
            userBean.setItem((new UserRepository()).getUserWithForms(userBean.getItem().getId()));
        }
        id=userBean.getItem().getId();
        //составляем список доступных форм и прав на них
        //для каждой формы добавляем прва, если они есть в БД 
        //иначе добавляем форму с отсутствующими правами
        list = new ArrayList<FormEnabled>();
        List<Form> forms = (new FormRepository()).getActiveForm();
        for (Form form : forms) {
            List<Form> forms1 = userBean.getItem().getForms();
            boolean found = false;
            for (Form form1 : forms1) {
                if (form1.getId() == form.getId()) {
                    list.add(new FormEnabled(form, found = true));
                    break;
                }
            }
            if (!found) {
                list.add(new FormEnabled(form, false));
            }
        }

    }

    /**
     * Сохраняет информацию о правах возвращаемся на основной include
     *
     */
    public void saveRights() {
        //оставляем в списке форм для пользователя только те, на которые у него есть права
        for (FormEnabled formEnabled : list) {
            List<Form> forms1 = userBean.getItem().getForms();
            boolean found = false;
            for (Form form1 : forms1) {
                if (form1.getId() == formEnabled.getForm().getId()) {
                    found = true;
                    if (!formEnabled.isEnabled()) {
                        forms1.remove(form1);
                    }
                    break;
                }
            }
            if (!found && formEnabled.isEnabled()) {
                forms1.add(formEnabled.getForm());
            }
        }
        //сохраняем в БД
        (new UserRepository()).save(userBean.getItem());
        userBean.setTab(0);
        userBean.getDep().setAddress(Constants.DEPARTMENT_URL);
    }
}
