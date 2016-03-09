/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import rzd.vivc.aszero.dto.Counter;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.repository.CounterRepository;
import rzd.vivc.aszero.repository.DepartmentRepository;

/**
 *
 * @author apopovkin
 */
public class CounterInputExcel {
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
    /*    String PytIn = "C:" + File.separator + "counter.xls";
        File FL = new File(PytIn);
        InputStream inputStream = new FileInputStream(FL);
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wb.getSheetAt(0);

        for (Iterator is = sheet.rowIterator(); is.hasNext();) {
            HSSFRow row = (HSSFRow) is.next();
            Counter count = new Counter();

          /*  for (Iterator ir = row.cellIterator(); ir.hasNext();) {
             HSSFCell irCell = (HSSFCell) ir.next();
             System.out.print(getStringCell(row.getCell(0)));
             }*/
            //System.out.println(rowN2.getCell(0)+" "+rowN2.getCell(1)+" "+rowN2.getCell(2)+" "+rowN2.getCell(3));
            
             //System.out.print(getIntegerCell(row.getCell(0)));
       /*     count.setType(getStringCell(row.getCell(8)));
            count.setNum(new Long(getStringCell(row.getCell(9))));
            
            System.out.println(getStringCell(row.getCell(8)));
            System.out.println(getStringCell(row.getCell(9)));
            CounterRepository countRep=new CounterRepository();
            countRep.save(count);
    
    



        }
      FRIDAY*/
     

Calendar c = Calendar.getInstance();
Date now = new Date();
c.setTime(now);
c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
 SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.");
     System.out.println(dateFormatter2.format(c.getTime()));    
 System.out.println( c.getTime().getDay());
 while(c.getTime().getDay()!=5){
     c.add(Calendar.DATE, -1);
 }
 System.out.println( dateFormatter2.format(c.getTime()));    
     
     
    }
}
