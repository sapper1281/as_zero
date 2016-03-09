/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.event.RowEditEvent;
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.classes.excel.createCellNull;
import rzd.vivc.aszero.dto.Counter;
import rzd.vivc.aszero.dto.InputASKYEInfo;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.repository.CounterRepository;
import rzd.vivc.aszero.repository.ReportDetailsRepository;
import rzd.vivc.aszero.service.StringImprover;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class FormSixPageBean extends BasePage implements Serializable {

    private final String WHERE = "errorpageadditional.xhtml";
    private final String FONT = "Courier New";
    @ManagedProperty(value = "#{formOnePageBean}")
    private FormOnePageBean one;
    private List<ReportDetails> list = new ArrayList<ReportDetails>();
    //</editor-fold>
public FormSixPageBean() {
        super();
    }
    //<editor-fold defaultstate="collapsed" desc="ger-set">
    /**
     * Возвращает бин формы 1
     *
     * @return бин формы 1
     */
    public FormOnePageBean getOne() {
        return one;
    }

    /**
     * Назначает бин формы 1
     *
     * @param one бин формы 1
     */
    public void setOne(FormOnePageBean one) {
        try {
            this.one = one;
        } catch (Exception e) {
            this.one = null;
        }
    }

    /**
     * Возвращает список строк по электроэнергии
     *
     * @return список строк по электроэнергии
     */
    public List<ReportDetails> getList() {
        return list;
    }

    /**
     * Назначает список строк по электроэнергии
     *
     * @param list список строк по электроэнергии
     */
    public void setList(List<ReportDetails> list) {
        this.list = list;
    }

    //</editor-fold>
    /**
     * Загрузка информации выполняется после открытия диалогового окна
     */
    @PostConstruct
    public void Init() {
        loadInfo();
    }

    //запись в список тех строк отчета, у которых ТЭР электроэнергия
    private void loadInfo() {
        list.clear();
        if (one != null && one.getRep() != null && one.getRep().getDetails() != null) {
            List<ReportDetails> details = one.getRep().getDetails();
            for (ReportDetails reportDetails : details) {
                if (reportDetails.getResource().getName().equalsIgnoreCase("Электроэнергия")) {
                    list.add(reportDetails);
                }
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="кнопки">
    /**
     * Галочка Обратить внимание! могут быть некие глюки со временем акции
     *
     * @param event содержит строку отчета, которую меняем
     */
    public void onEdit(RowEditEvent event) {
        ReportDetails cur = (ReportDetails) event.getObject();
        if (cur.getNum() != 0) {
            //извлекаем из БД счетчик с данным номером и информацией из АСКУЭ на дату акции
            Counter byNum = null;
            try {
                byNum = (new CounterRepository()).getWithAskueInfo(cur.getNum(), cur.getReport().getDt_begin());
            } catch (Exception e) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(WHERE);
                } catch (IOException ex) {
                    Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //если есть такой счетчик
            if (byNum != null) {
                //устанавливаем аскуе и тип
                cur.setType(byNum.getType());
                cur.setAskue(true);
                //если есть к нему инфа по дате
                //если ее нет, попадем в катч из-за lasyInitialuzation
                /*try {
                 if (byNum.getСounterInputASKYEInfo() != null && !byNum.getСounterInputASKYEInfo().isEmpty()) {
                 StringImprover imp = new StringImprover();
                 int hb = imp.getHour(cur.getReport().getTime_begin()) - 1;
                 int he = imp.getHour(cur.getReport().getTime_begin());
                 if (hb > 0) {
                 InputASKYEInfo infB = byNum.getСounterInputASKYEInfo().get(hb);
                 InputASKYEInfo infE = byNum.getСounterInputASKYEInfo().get(he);
                 cur.setUsedToday(infB.getHhToday() + infE.getHhToday());
                 cur.setUsedBefore(infB.getHhBefore() + infE.getHhBefore());
                 }
                 }
                 } catch (Exception e) {
                 }*/
            } else {
               // cur.setType(null);
                cur.setAskue(false);

            }

        } else {
           // cur.setType(null);
            cur.setAskue(false);
        }
        one.onEdit(event);
    }

    /**
     * Крестик
     *
     * @param event содержит строку отчета, которую меняем
     */
    public void onCancel(RowEditEvent event) {
    }

    /**
     * Сохранение строк из формы 6
     */
    public String save() {
        try {
            new ReportDetailsRepository().saveAllDetails(list);
        } catch (Exception e) {
            return "errorpageadditional";
        }
        return "mainpage";
    }
    //</editor-fold>

    public void Print(Object document) {

        //<editor-fold defaultstate="collapsed" desc="формируем докумерт">
        HSSFWorkbook wb = (HSSFWorkbook) document;



        HSSFSheet sheet = wb.createSheet("new sheet");

        HSSFPrintSetup print = sheet.getPrintSetup();
        print.setScale((short) 83);
        print.setPaperSize((short) HSSFPrintSetup.A4_PAPERSIZE);



        Font font_10 = wb.createFont();
        font_10.setItalic(true);
        font_10.setFontHeightInPoints((short) 10);
        font_10.setFontName(FONT);

        Font font_14 = wb.createFont();
        font_14.setItalic(true);
        font_14.setFontHeightInPoints((short) 14);
        font_14.setFontName(FONT);

        Font font_18 = wb.createFont();
        font_18.setItalic(true);
        font_18.setFontHeightInPoints((short) 18);
        font_18.setFontName(FONT);


        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.getFontIndex();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setFont(font_18);


        CellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.getFontIndex();
        cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle1.setFont(font_14);




        CellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.getFontIndex();
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setFont(font_10);

        CellStyle cellStyle_L = wb.createCellStyle();
        cellStyle_L.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle_L.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle_L.getFontIndex();
        cellStyle_L.setFont(font_14);

        CellStyle cellStyle_R = wb.createCellStyle();
        cellStyle_R.setAlignment(CellStyle.ALIGN_RIGHT);
        cellStyle_R.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle_R.getFontIndex();
        cellStyle_R.setFont(font_14);
        //</editor-fold>


        //<editor-fold defaultstate="collapsed" desc="заголовок">
        Row row = sheet.createRow(0);
        new createCell().createCell(wb, sheet, cellStyle_R, row, 0, 0, 0, 0, 11, "Форма 6");

        Row row1 = sheet.createRow(1);
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 11, "ОТЧЕТ");

        Row row4 = sheet.createRow(4);

        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 2, 2, 2);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 3, 3, 3);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 4, 4, 4);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 5, 5, 5);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 6, 6, 6);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 7, 7, 7);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 8, 8, 8);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 10, 10, 10);
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 0, 0, 0, "№ п/п");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 1, 1, 8, "Информация об объекте на которм планируется проведение мероприятий по экономии электроэнергии");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 9, 9, 10, "Потребление электроенергии");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 11, 11, 11, "Экономия электроэнергии, кВт*ч");


        Row row5 = sheet.createRow(5);
        createCellNull.createCellNull(sheet, cellStyle2, row5, 5, 5, 11, 11, 11);
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 1, 1, 1, "Мероприятие, наименование объекта");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 2, 2, 2, "Ответственный за проведение мероприятия");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 3, 3, 3, "Местонахождение объекта (станиция, населенный пункт)");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 4, 4, 4, "Источник питания (ТП, КТП)");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 5, 5, 5, "Место установки прибора учета");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 6, 6, 6, "Тип прибора учета электроэнергии по которому осуществляется рачсет за электроэнергию");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 7, 7, 7, "Номер прибора учета электроэнергии по которому осуществляется рачсет за электроэнергию");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 8, 8, 8, "Принадлежность прибора учета к системе АСКУЭ ОАО \"РЖД\" (да/нет)");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 9, 9, 9, "Потребление электроэнергии за 2 аса работы, за день до проведения акции кВт*ч");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 10, 10, 10, "Потребление электроэнергии за 2 аса работы, в день проведения акции кВт*ч");


        HSSFRow row6 = sheet.createRow(6);
        for (int i1 = 0; i1 < 12; i1++) {
            new createCell().createCell(wb, sheet, cellStyle1, row6, 6, 6, i1, i1, i1, String.valueOf(i1 + 1));
        }
        //</editor-fold>

        List<ReportDetails> details = list;
        int j;
        int i = 0;
        for (ReportDetails repDetails : details) {
            j = i + 7;
            HSSFRow header8 = sheet.createRow(j);

            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 0, 0, 0, (details.indexOf(repDetails) + 1) + "");
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 1, 1, 1, repDetails.getActivity());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 2, 2, 2, repDetails.getResponsible());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 3, 3, 3, repDetails.getAddressOfObject());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 4, 4, 4, repDetails.getPowerSource());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 5, 5, 5, repDetails.getAddres());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, repDetails.getType());
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, repDetails.getNum() + "");
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 8, 8, 8, repDetails.isAskue() ? "Да" : "Нет");
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 9, 9, 9, repDetails.getUsedBefore() + "");
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 10, 10, 10, repDetails.getUsedToday() + "");
            new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 11, 11, 11, repDetails.getEconomy() + "");

            i++;
        }

        for (int i1 = 0; i1 < 12; i1++) {
            sheet.autoSizeColumn(i1, true);
        }
        wb.removeSheetAt(0);
    }
}
