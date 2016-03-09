/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import rzd.vivc.aszero.dto.Counter;
import rzd.vivc.aszero.dto.InputASKYE;
import rzd.vivc.aszero.dto.InputASKYEInfo;
import rzd.vivc.aszero.dto.InputExcelError;
import rzd.vivc.aszero.repository.CounterRepository;
import org.primefaces.event.FileUploadEvent;



 

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class InputASKYEPageBean implements Serializable {

    private List<InputExcelError> errList;

    public List<InputExcelError> getErrList() {
        return errList;
    }

    public void setErrList(List<InputExcelError> errList) {
        this.errList = errList;
    }
    
    
    
    
    
    /*число или нет*/
    public  boolean checkString(String string) {
        try {

            Long.parseLong(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /*выбор номера счетчика long*/

    public  long longExel(String stExel) {
        long back = 0;
        int min = stExel.indexOf("(");
        int max = stExel.indexOf(")");
        if (min > max) {
            stExel = stExel.substring(max + 1, stExel.length());
            min = stExel.indexOf("(");
            max = stExel.indexOf(")");
        }

        String st = "";
        if (min != max) {
            st = stExel.substring(min + 1, max);
            if (checkString(st)&&st.length()>4) {
                back = Long.parseLong(st);
            } else {
                st = stExel.substring(min, max + 1);
                stExel = stExel.replace(st, "");
                back = longExel(stExel);
            }
            return back;

        } else {

            return back;

        }

    }

    public  double cellExel(HSSFCell cExel) throws Exception {

        try {
            switch (cExel.getCellType()) {
                case 0:
                    return cExel.getNumericCellValue();
                case 1:
                    String st = cExel.getStringCellValue().replace(",", ".");
                    return Double.parseDouble(st);
                default:
                    return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    //парсер
    public  void parser(InputStream inputStream) throws IOException, Exception {


        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(0);

 errList=new ArrayList<InputExcelError>();
        int j = 0;
        while (j + 3 != sheet.getLastRowNum()) {

            while (!sheet.getRow(j).getCell(0).getStringCellValue().equals("ТУ:")) {
                j++;
            }
            String stExel = sheet.getRow(j).getCell(3).getStringCellValue();
            long l = longExel(stExel);
            if (l != 0) {
                List<Counter> counterList = new ArrayList<Counter>();
                Counter counter = new CounterRepository().get(l);

                if (counter == null) {
                    InputExcelError err=new InputExcelError();
                    err.setInf1("№ "+l);
                    err.setInf2("нет в бд");
                    err.setInf3(stExel);
                    errList.add(err);
                    j++;
                } else {
                    counterList.add(counter);

                    while (!sheet.getRow(j).getCell(1).getStringCellValue().equals("Дата")) {
                        j++;
                    }
                    List<InputASKYE> inASList = new ArrayList<InputASKYE>();
                    InputASKYE inASFirst = new InputASKYE();
                    inASList.add(inASFirst);
                    inASFirst.setDtBefore(sheet.getRow(j + 1).getCell(1).getDateCellValue());


                    if (sheet.getRow(j + 2).getCell(1).getDateCellValue() != null) {
                     

                           
                      /*  for (int i = 2; i < 26; i++) {
                            InputASKYEInfo inputASKYEInfo=new InputASKYEInfo();
                            System.out.println(cellExel(sheet.getRow(j + 1).getCell(i)));
                        }*/

int iFist=j;
                    if (inASFirst.getDtBefore().equals(sheet.getRow(j + 2).getCell(1).getDateCellValue())) {
                            j++;
                    }
                    inASFirst.setDtToday(sheet.getRow(j + 2).getCell(1).getDateCellValue());

List<InputASKYEInfo> inputASKYEInfoList=new ArrayList<InputASKYEInfo>();       

                        for (int i = 2; i < 26; i++) {
                            InputASKYEInfo inputASKYEInfo=new InputASKYEInfo();
                            inputASKYEInfo.setInputASKYEinf(inASFirst);
                            inputASKYEInfo.setInputCounter(counter);
                            inputASKYEInfo.setHh(Integer.parseInt(sheet.getRow(iFist).getCell(i).getStringCellValue().substring(0, 2)));
                            inputASKYEInfo.setHhBefore((float)cellExel(sheet.getRow(iFist+1).getCell(i)));
                            inputASKYEInfo.setHhToday((float)cellExel(sheet.getRow(j + 2).getCell(i)));
                          //  new InputASKYEInfoRepository().save(inputASKYEInfo);
                            inputASKYEInfoList.add(inputASKYEInfo);
                        }

                        


                        inASFirst.setInputASKYEinputASKYEinf(inputASKYEInfoList);
                        inASFirst.setInputCounter(counterList);
                        
                       


                        counter.setСounterInputASKYEInfo(inputASKYEInfoList);
                        counter.setInputASKYE(inASList);
                        
                        
                        new CounterRepository().saveAll(inASFirst,counter,inputASKYEInfoList);
                        
                        
                      /*  new InputASKYERepository().save(inASFirst);
                        new CounterRepository().save(counter);
                        
                        
                        for (InputASKYEInfo inputASKYEInfo : inputASKYEInfoList) {
                           new InputASKYEInfoRepository().save(inputASKYEInfo);
                        }*/




                    } else {
                        
                        InputExcelError err=new InputExcelError();
                    err.setInf1("№ "+l);
                    err.setInf2("не полные данные");
                    err.setInf3(stExel);
                    errList.add(err);
                     
                        j++;
                    }
                }
            } else {
                j++;
                  InputExcelError err=new InputExcelError();
                    err.setInf1("№ ");
                    err.setInf2("нет номера счетчика");
                    err.setInf3(stExel);
                    errList.add(err);
        
            }

        }
        
        
        System.out.println(errList); 
        
        
     

    }
    
    
    
    
    
    
    
    
     private  String getStringCell(HSSFCell Cell) throws Exception {
        try {

          
            switch (Cell.getCellType()) {
                default:
                    return "не определен";
                case 0:
                    return String.valueOf(Cell.getNumericCellValue());
                case 1:
                    return Cell.getRichStringCellValue().getString();
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    public  long cellExelLong(HSSFCell cExel) throws Exception {
         try {
         switch (cExel.getCellType()) {
            case 0:
                 return (long)cExel.getNumericCellValue();
            case 1:
                String st=cExel.getStringCellValue().replace(",", ".");
                if(checkString(st)){
                
                return Long.parseLong(st);
                }else{return 0;}
                
                 
            default:
               return 0;
        }
         } catch (Exception e) {
            return 0;
        }
    }
    
    
    
    
    
    
    
    
    
    
    

     public  void parserCounter(InputStream inputStream) throws IOException, Exception {


        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
           HSSFSheet sheet = wb.getSheetAt(0);

        for (Iterator is = sheet.rowIterator(); is.hasNext();) {
            HSSFRow row = (HSSFRow) is.next();
            Counter count = new Counter();
          
          long k=cellExelLong(row.getCell(2));
            System.out.println("ff "+row.getCell(2));
            System.out.println("ff1 "+row.getCell(1));
            System.out.println("ff0 "+row.getCell(0));
            if(k>0){
             count.setNum(k);
             count.setType(getStringCell(row.getCell(1)));
            
            
            
            
            CounterRepository countRep = new CounterRepository();
            countRep.save(count);
            }





        }
     }
    
    
    
    //добавление файла
    public String handleFileUpload(FileUploadEvent event) throws IOException, Exception {
        try {
            parser(event.getFile().getInputstream());



        } catch (IOException e) {
            e.printStackTrace();
        }
        return "mainpage";
    }

    
    public String handleFileUploadCounter(FileUploadEvent event) throws IOException, Exception {
        try {
            parserCounter(event.getFile().getInputstream());



        } catch (IOException e) {
            e.printStackTrace();
        }
        return "mainpage";
    }
    
    
     
     
 
     
     
     
     
    /**
     * Creates a new instance of inputASKYEPageBean
     */
    public InputASKYEPageBean() {
    }

 /*   public static void main(String[] args) throws Exception {
        String PytIn = "C:" + File.separator + "profili.xls";
        File FL = new File(PytIn);
        InputStream inputStream = new FileInputStream(FL);
        parser(inputStream);

    }*/
}
