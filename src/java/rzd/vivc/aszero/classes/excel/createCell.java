/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.classes.excel;


import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.RowEditEvent;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.dto.Measure;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.repository.ReportDetailsRepository;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.repository.ResourceRepository;
import rzd.vivc.aszero.repository.UserRepository;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author apopovkin
 */
public class createCell {
    
    
    public void createCell(Workbook wb,HSSFSheet sheet,CellStyle cellStyle, Row row,
            int rowfirst,int rowlast, int column,int columnfirst,int columnlast,String value 
            ) {
        
        row.setHeightInPoints(20);
        Cell cell = row.createCell(column);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(
                rowfirst, //first row (0-based)
                rowlast, //last row  (0-based)
                columnfirst, //first column (0-based)
                columnlast //last column  (0-based)
                ));
    }
    
    
}
