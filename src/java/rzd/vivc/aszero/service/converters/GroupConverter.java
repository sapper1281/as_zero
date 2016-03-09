/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.service.converters;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.model.DualListModel;
import rzd.vivc.aszero.dto.DepartmentGroup;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.repository.ResourceRepository;
/**
 *
 * @author apopovkin
 */

 @FacesConverter(forClass=DepartmentGroup.class,value = "groupConverter")
public class GroupConverter implements Converter {


    @Override
   public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if(submittedValue.equals("")){
        System.out.println("fgfgfgfg1 null");
        return  null;
        
        }else{
        System.out.println("fgfgfgfg1  "+(new DepartmentRepository()).getDepartmentGroup(Long.parseLong(submittedValue)));
        return (new DepartmentRepository()).getDepartmentGroup(Long.parseLong(submittedValue));}
}
 @Override
public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
     
     if (!(value instanceof DepartmentGroup)) {
            return "";
        } else {
         System.out.println("fgfgfgfg2  "+((DepartmentGroup) value).getId() + "");
            return ((DepartmentGroup) value).getId()+ "";
        }
        
   
}
}       




