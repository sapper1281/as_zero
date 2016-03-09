/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import com.ibm.db2.tools.common.uamanager.UAHtmlFilter;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import rzd.vivc.aszero.repository.UserRepository;
import rzd.vivc.aszero.service.Constants;
import zdislava.model.dto.security.users.User;

/**
 * К странице users.xhtml
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class UsersPageBeanNew1 implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="поля">
    private List<User> beanList;
    private List<User> filteredBeanList;
    @ManagedProperty(value = "#{departmentPageBeanNew}")
    private DepartmentPageBeanNew dep;
    
    
    
    
            

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает список пользователей
     *
     * @return список секций пользователей
     */
    //</editor-fold>
    public DepartmentPageBeanNew getDep() {
        return dep;
    }

    public void setDep(DepartmentPageBeanNew dep) {
        this.dep = dep;
    }

    /**
     * Возвращает список пользователей
     *
     * @return список секций пользователей
     */
    public List<User> getBeanList() {
        return beanList;
    }

    /**
     * Назначает список пользователей
     *
     * @param beanList список пользователей
     */
    public void setBeanList(List<User> beanList) {
        this.beanList = beanList;
    }

    /**
     * Возвращает отфильтрованный список пользователей
     *
     * @return отфильтрованный список пользователей
     */
    public List<User> getFilteredBeanList() {
        return filteredBeanList;
    }

    /**
     * Назначает отфильтрованный список пользователей
     *
     * @param filteredBeanList отфильтрованный список пользователей
     */
    public void setFilteredBeanList(List<User> filteredBeanList) {
        this.filteredBeanList = filteredBeanList;
    }

    //</editor-fold>
    /**
     * Creates a new instance of SectionsPageBean
     */
    public UsersPageBeanNew1() {
    }

    @PostConstruct
    public void Init() {
        beanList = (new UserRepository()).getAllUsersByDepWithRights(dep.getDepar().getId());
    }

    /**
     * Переход на страницу редактирования
     *
     * @return "save"
     */
    public void editItem() {
        dep.setTab(0);
      /*  ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");*/

        dep.setAddress(/*userAgent.indexOf("Chrome")>=0?*/Constants.USER_URL/*:Constants.USER_URL*/);
        dep.setMessage("");
    }
}