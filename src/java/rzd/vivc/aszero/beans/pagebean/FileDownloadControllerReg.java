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
public class FileDownloadControllerReg {  
  
    private StreamedContent file;  
      
    public FileDownloadControllerReg() {          
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/doc/reg.doc");  
        file = new DefaultStreamedContent(stream, "image/doc", "reg.doc");  
    }  
  
    public StreamedContent getFile() {  
        return file;  
    }    
}  
   
