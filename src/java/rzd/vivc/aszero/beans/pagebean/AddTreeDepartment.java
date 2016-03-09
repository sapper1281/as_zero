/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import rzd.vivc.aszero.service.Constants;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class AddTreeDepartment implements Serializable {
    
    private String addressTree = Constants.DEPARTMENTTREE_URL;
    private String addressForm = Constants.FORM_URL;
      
    public String getAddressTree() {
        return addressTree;
    }

    public void setAddressTree(String addressTree) {
        this.addressTree = addressTree;
    }

    public String getAddressForm() {
        return addressForm;
    }

    public void setAddressForm(String addressForm) {
        this.addressForm = addressForm;
    }
    
    
    
    
    /**
     * Creates a new instance of AddTreeDepartment
     */
    public AddTreeDepartment() {
    }


}
