/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class PathBean {
    int f;

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
    
   String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
   

    /**
     * Creates a new instance of PathBean
     */
    public PathBean() {
         Properties pr = new Properties();

            try {
         
                pr.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/classes/news/news.properties"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            str = pr.getProperty("developers");
    }
}
