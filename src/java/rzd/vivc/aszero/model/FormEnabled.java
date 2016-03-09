/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.model;

import java.io.Serializable;
import rzd.vivc.aszero.dto.form.Form;

/**
 *
 * @author vvolgina
 */
public class FormEnabled implements Serializable{
    private Form form;
    private boolean enabled;

    public FormEnabled(Form form, boolean enabled) {
        this.form = form;
        this.enabled = enabled;
    }

    public FormEnabled() {
    }
    
    

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
}
