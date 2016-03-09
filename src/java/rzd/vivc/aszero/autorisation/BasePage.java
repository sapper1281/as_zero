/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rzd.vivc.aszero.autorisation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import rzd.vivc.aszero.autorisation.CheckRights;
import rzd.vivc.aszero.beans.pagebean.FormOnePageBean;


/**
 *
 * @author vvolgina
 */
public class BasePage {

    public BasePage() {
        if(!(new CheckRights()).isAutorised()){
         try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("errorpageaccess.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
}
