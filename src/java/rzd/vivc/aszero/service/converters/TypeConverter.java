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
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.repository.ResourceRepository;
import rzd.vivc.aszero.repository.UserTypeRepository;
import zdislava.model.dto.security.users.UserType;

/**
 *
 * @author VVolgina
 */
@FacesConverter(value = "typeConverter")
public class TypeConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (!(entity instanceof UserType)) {
            return "";
        } else {
            return ((UserType) entity).getId() + "";
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        try {
            UserType entity = (new UserTypeRepository()).get(new UserType(new Long(uuid)));
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
}
