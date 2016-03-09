/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import rzd.vivc.aszero.dto.Measure;
import rzd.vivc.aszero.repository.MeasureRepository;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class MeasuresPageBean implements Serializable{
    
    private List<Measure> list;

    /**
     * Get the value of list
     *
     * @return the value of list
     */
    public List<Measure> getList() {
        return list;
    }


    /**
     * Creates a new instance of MeasuresPageBean
     */
    public MeasuresPageBean() {
        list=(new MeasureRepository()).getAllNonDeleted(Measure.class);
    }
    
    public void onEdit(RowEditEvent event) { 
        Measure mes=(Measure)event.getObject();
        String str;
    }  
      
    public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Car Cancelled");  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
}
