/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.service.converters;

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
@FacesConverter(value = "shortInfoResourceConverter")
public class ShortInfoResourceConverter implements Converter{
    /**
     * Создает объект ресурса на основе краткой информации о нем
     * @param old краткая информация о ресурсе
     * @return
     */
    public Resource toLong(ShortInfoResource old){
        return new Resource(old.getName(), old.getKoef_polution(), old.getId(), old.getCost());
    }
    
    /**
     * Создает краткую информацию о ресурсе
     * @param old ресурс
     * @return
     */
    public ShortInfoResource toShort(Resource old){
        return new ShortInfoResource(old.getId(), old.getName(), old.getKoef_polution());
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (!(entity instanceof ShortInfoResource)) {
            return "";
        } else {
            return ((ShortInfoResource) entity).getId() + "";
        } 
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        try {
            ShortInfoResource entity = (new ResourceRepository()).getShort(new Long(uuid));
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
}
