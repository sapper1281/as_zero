/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.InputStream;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;  
import javax.servlet.ServletContext;  
  
import org.primefaces.model.DefaultStreamedContent;  
import org.primefaces.model.StreamedContent;  
@ManagedBean
@RequestScoped  
public class FileDownloadControllerGoogle {  
  
    private StreamedContent file;  
      
    public FileDownloadControllerGoogle() {          
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/doc/GoogleChromeStandaloneEnterprise.msi");  
        file = new DefaultStreamedContent(stream, "image/doc", "GoogleChromeStandaloneEnterprise.msi");  
    }  
  
    public StreamedContent getFile() {  
        return file;  
    }    
}  
   
