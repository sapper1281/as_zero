/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.autorisation;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import rzd.vivc.aszero.beans.session.AutorizationBean;

/**
 *
 * @author vvolgina
 */
public class CheckRights {

    public boolean isAutorised() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session==null){
            return false;
        }
        AutorizationBean aut=(AutorizationBean) (session).getAttribute("autorizationBean");
        if(aut==null){
            return false;
        }
        if(aut.getUser()==null){
            return false;
        }
        return true;
    }
    
    public boolean isAdmin(){
        return isAutorised()&&((AutorizationBean)((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("autorizationBean")).getUser().getIdType()==2;
    }
}
