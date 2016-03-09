/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.model.CostsPageModel;
import rzd.vivc.aszero.repository.CostsPageDBWork;

/**
 * Бин для страницы inputCounter
 *
 * @author VVolgina
 */
@ManagedBean
@ViewScoped
public class FormCosts extends BasePage{
    //<editor-fold defaultstate="collapsed" desc="Поля">

    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private CostsPageModel model = new CostsPageModel();

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает сессионные данные
     *
     * @return сессионные данные
     */
    public AutorizationBean getSession() {
        return session;
    }

    /**
     * Назначает сессионные данные
     *
     * @param session сессионные данные
     */
    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    /**
     * Возвращает модель со всей информацией для страницы
     *
     * @return модель со всей информацией для страницы
     */
    public CostsPageModel getModel() {
        return model;
    }
    //</editor-fold>

    /**
     * Creates a new instance of FormCosts
     */
    public FormCosts() { 
        super();
            
    }

    @PostConstruct
    public void Init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("mes") && FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("year")) {
            int mes = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes"));
            int year = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year"));
            model = new CostsPageDBWork().loadAllInfoFromDB(mes, year, session.getUser().getDepartmentPower(), session.getUser().getUserID());
        } else {
            Date dat=new Date();
            model = new CostsPageDBWork().loadAllInfoFromDB(dat, session.getUser().getDepartmentPower(), session.getUser().getUserID());
        }
    }
    
    /**
     * Сохраняет информацию о ценах
     * В качестае пользователя будет проставлен текущий во ВСЕХ строках цен
     */
    public void save() {
        new CostsPageDBWork().saveList(model);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("mainpage.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FormCosts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
