/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.service.converters;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.WeakHashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoResource;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.repository.ResourceRepository;

/**
 *
 * @author VVolgina
 */
@FacesConverter(value = "resourceConverter")
public class ResourceConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (!(entity instanceof Resource)) {
            return "";
        } else {
            return ((Resource) entity).getId() + "";
        } 
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        try {
            Resource entity = (new ResourceRepository()).get(new Resource(new Long(uuid)));
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
}
