/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.Temporal;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.repository.DepartmentRepository;

/**
 *
 * @author apopovkin
 */
public class DepartmentExcelInput {

    private static SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private static String getStringCell(HSSFCell Cell) throws Exception {
        try {

            String st = "";
            switch (Cell.getCellType()) {
                default:
                    st = "не определен переменная типа строка";
                    break;
                case 0:
                    st = dateFormatter1.format(Cell.getDateCellValue()).toString();
                    break;
                case 1:
                    st = Cell.getRichStringCellValue().getString();
                    break;


            }
            return st;
        } catch (Exception e) {
            return e.toString();
        }
    }

    private static int getIntegerCell(HSSFCell Cell) throws Exception {
        try {

            int st = -1;
            switch (Cell.getCellType()) {
                default:
                    break;
                case 0:
                    st =(int)Cell.getNumericCellValue();
                    break;

            }
            return st;
        } catch (Exception e) {
            return -10;
        }
    }

    public static void main(String[] args) throws Exception {
        String PytIn = "C:" + File.separator + "nsi.xls";
        File FL = new File(PytIn);
        InputStream inputStream = new FileInputStream(FL);
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wb.getSheetAt(0);

        for (Iterator is = sheet.rowIterator(); is.hasNext();) {
            HSSFRow row = (HSSFRow) is.next();
            Department dep = new Department();

          /*  for (Iterator ir = row.cellIterator(); ir.hasNext();) {
             HSSFCell irCell = (HSSFCell) ir.next();
             System.out.print(getStringCell(row.getCell(0)));
             }*/
            //System.out.println(rowN2.getCell(0)+" "+rowN2.getCell(1)+" "+rowN2.getCell(2)+" "+rowN2.getCell(3));
            
             //System.out.print(getIntegerCell(row.getCell(0)));
            
            dep.setIdVp(getIntegerCell(row.getCell(0)));
            dep.setNameVp(getStringCell(row.getCell(1)));
            dep.setIdGroupVp(getIntegerCell(row.getCell(2)));
            dep.setIdActionVp(getIntegerCell(row.getCell(3)));
            dep.setIdNp(getIntegerCell(row.getCell(4)));
            dep.setNameNp(getStringCell(row.getCell(5)));
            dep.setIdGroupNp(getIntegerCell(row.getCell(6)));
            dep.setIdActionNp(getIntegerCell(row.getCell(7)));
            dep.setNum(getIntegerCell(row.getCell(8)));
            dep.setViewSubmission(getStringCell(row.getCell(9)));
            
            dep.setIdViewSubmission(1);
            dep.setTypeCommunication(getStringCell(row.getCell(10)));
            dep.setIdTypeCommunication(1);
            //GOTO привести к дате
            dep.setDtEditNSI(row.getCell(11).getDateCellValue());
            dep.setDtBeginNSI(row.getCell(12).getDateCellValue());
         
         
            DepartmentRepository depRep=new DepartmentRepository();
            depRep.save(dep);
    
    




        }
    }
}
