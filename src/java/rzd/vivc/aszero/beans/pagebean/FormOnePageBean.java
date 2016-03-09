/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;


import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Font;
import org.primefaces.event.RowEditEvent;
import rzd.vivc.aszero.autorisation.BasePage;
import rzd.vivc.aszero.autorisation.CheckRights;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.classes.excel.createCell;
import rzd.vivc.aszero.classes.excel.createCellNull;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsWithAdditionalID;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.repository.ReportDetailsRepository;
import rzd.vivc.aszero.repository.ReportRepository;
import rzd.vivc.aszero.repository.ResourceRepository;
import rzd.vivc.aszero.repository.UserRepository;
import rzd.vivc.aszero.service.StringImprover;
import zdislava.model.dto.security.users.User;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@ViewScoped
public class FormOnePageBean extends BasePage implements Serializable {

    public FormOnePageBean() {
        super();
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="поля">
    private final String FONT = "Courier New";
    private List<Resource> list;
    private Report rep;
    private long id;
    private List<ReportDetails> mesList = new ArrayList<ReportDetails>();
    private List<ReportDetails> mesListNew = new ArrayList<ReportDetails>();
    /*возможность редактировать если принадлежит депо*/
    private boolean t_dep_form;
    /*возможность редактировать факт*/
    private boolean t_fact_form;
    /*даты доступности редактирования дат*/
    // private String dt_max_cal_st;
    // private String dt_min_cal_st;
    /*редактирование или добавление*/
    private boolean redactor;
    /* блокировка граф 3 5 при редактировании*/
    private boolean bloc_rec_fact;
    /*выбранная строка*/
    private ReportDetails selectedDetails;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private boolean sp_blok;
    /*предоставление доступана редактирования для редактирования НТЭЦ после 15часов*/
    private boolean ntec_blok;
    /*предоставление доступана редактирования НТЭЦ после 15часов(появление кнопки)*/
    private boolean button_ntec_blok;
    /* блокировка по истечению 3 суток до 12 часов после проведения акции*/
    private boolean bloc_out_time_input_all;



    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="get-set">
    
    /**
     * Возвращает блокировку предоставления доступа на редактирование
     * @return блокировка предоставления доступа на редактирование
     */
    public boolean isSp_blok() {
        return sp_blok;
    }

    /**
     * Назначает блокировку предоставления доступа на редактирование
     * @param sp_blok блокировка предоставления доступа на редактирование
     */
    public void setSp_blok(boolean sp_blok) {
        this.sp_blok = sp_blok;
    }

    public boolean isNtec_blok() {
        return ntec_blok;
    }

    public void setNtec_blok(boolean ntec_blok) {
        this.ntec_blok = ntec_blok;
    }

    public boolean isButton_ntec_blok() {
        return button_ntec_blok;
    }

    public void setButton_ntec_blok(boolean button_ntec_blok) {
        this.button_ntec_blok = button_ntec_blok;
    }
    
    
    
    /**
     * Возвращает флажок блокировки изменений через 2 дня после завершения акции
     *
     * @return флажок блокировки изменений через 2 дня после завершения акции
     */
    public boolean isBloc_out_time_input_all() {
        return bloc_out_time_input_all;
    }

    /**
     * Назначает флажок блокировки изменений через 2 дня после завершения акции
     *
     * @param bloc_out_time_input_all флажок блокировки изменений через 2 дня
     * после завершения акции
     */
    public void setBloc_out_time_input_all(boolean bloc_out_time_input_all) {
        this.bloc_out_time_input_all = bloc_out_time_input_all;
    }

    /**
     * Возвращает список ТЭР. Идут в выпадающий список при редактировании строки
     *
     * @return список ТЭР
     */
    public List<Resource> getList() {
        return list;
    }

    /**
     * Возвращает отчет, выводимый на форме
     *
     * @return отчет, выводимый на форме
     */
    public Report getRep() {
        return rep;
    }

    /**
     * Назначает отчет, выводимый на форме
     *
     * @param rep отчет, выводимый на форме
     */
    public void setRep(Report rep) {
        this.rep = rep;
    }

    /**
     * Относится ли пользователь к СП данного отчета
     *
     * @return Относится ли пользователь к СП данного отчета
     */
    public boolean isT_dep_form() {
        return t_dep_form;
    }

    /**
     * Можно ли редактировать факт
     *
     * @return права на редактирование факта
     */
    public boolean isT_fact_form() {
        return t_fact_form;
    }

    /**
     * Показывает выполняется редактирование или добавление
     *
     * @return true - если редактирование
     */
    public boolean isRedactor() {
        return redactor;
    }

    /**
     * Блокировать ли графы 3 5 при редактировании, т.е. план
     *
     * @return блокировать ли графы 3 5 при редактировании
     */
    public boolean isBloc_rec_fact() {
        return bloc_rec_fact;
    }

    /**
     * Возвращает строку выбранную в таблице. Для PrimeFaces, контескстная
     * строка
     *
     * @return строка выбранная в таблице
     */
    public ReportDetails getSelectedDetails() {
        return selectedDetails;
    }

    /**
     * Назначает строку выбранную в таблице. Для PrimeFaces, контескстная строка
     *
     * @param selectedDetails строка выбранная в таблице
     */
    public void setSelectedDetails(ReportDetails selectedDetails) {
        this.selectedDetails = selectedDetails;
    }

    /**
     * Возвращает сесионный бин
     *
     * @return сесионный бин
     */
    public AutorizationBean getSession() {
        return session;
    }

    /**
     * Назначает сесионный бин
     *
     * @param session сесионный бин
     */
    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="по кнопкам">
    public void localEdit(ReportDetails mes) {

        if (redactor && t_fact_form) {
            mes.setDt_inputFact_test(true);
            mes.setDt_inputFact(new Date());
        }
        mesListNew.add(mes);
        //указвыаем, что строока принадлежит нашему отчету
        mes.setReport(rep);

        //ТУТ рассчитывается автоматически на основании к-ва по коэфициенту
        float koef = mes.getResource().getKoef();
        //if (koef != 0) {
        mes.setFact_tut(koef * mes.getFact_col());
        mes.setPlan_tut(koef * mes.getPlan_col());
        // }

        mesList.add(mes);
    }

    /**
     * Редактирование строки отчета через карандашик и галочку
     *
     * @param event событие. содержит ReportDetails
     */
    public void onEdit(RowEditEvent event) {


        ReportDetails mes = (ReportDetails) event.getObject();

        /*for (ReportDetails repDet : rep.getDetails()) {
         if(repDet.getId()==mes.getId()){
            
         float pl=
         }}
         */
        if ((mes.getResource().getId() != 13)) {


            // mes.setFact_tut(0);
            mes.setAddres(null);
            mes.setAddressOfObject(null);
            mes.setPowerSource(null);
            mes.setResponsible(null);
            mes.setType(null);
            mes.setUsedBefore(0);
            mes.setUsedToday(0);
            mes.setNum(0);

            // mes.setFact_col(0);
            // mes.setFact_money(0);
        }
        /*  System.out.println(""+mes.getId());
         System.out.println(""+mes.getPlan_col());
         for (ReportDetails repDet : rep.getDetails()) {
         if(repDet.getId()==mes.getId()){
         System.out.println("ИзмененrepDet: "+repDet.getPlan_col());
         System.out.println("Измененmes: "+mes.getPlan_col());
         if(repDet.getPlan_col()!=mes.getPlan_col()){
                
         System.out.println("Изменен: "+mes.getId());
         };
         System.out.println(""+mes.getId());
                 
         }
         }*/

//rep.getDetails().get(index)
//        System.out.println(""+rep.getPlan_col());
        //вычиление сокращения выбросов в атмосферу
      /*  for (ReportDetails det : rep.getDetails()) {
         if (det.getResource().getKoef_polution() == -1) {
         det.setPlan_col(det.getPlan_col() + mes.getPlan_col() * mes.getResource().getKoef_polution());
         det.setFact_col(det.getFact_col() + mes.getFact_col() * mes.getResource().getKoef_polution());
         }
         }*/
        localEdit(mes);
    }

    /**
     * Отмена редактирования строки отчета через карандашик и галочку
     *
     * @param event событие. содержит ReportDetails
     */
    public void onCancel(RowEditEvent event) {
    }

    /**
     * Вставка новой строки в отчет через кнопку добавления строки
     */
    public void addRow() {
        rep.getDetails().add(new ReportDetails());
    }

    //Если в списке деталей отчета есть строка с загрязнением - подсчитывает для нее
    //новае факт и план двнные, если нет - создает новую строку с этими данными
    //возвращает созданную/найденную в списке строку
    private ReportDetails genPolutionResult() {
        //просматриваем список. суммируем данные по тем в-вам, которые
        //загрязняют окр. среду. и заполняем строку об итоговом загрязнении, если она есть в списке
        List<ReportDetails> details = rep.getDetails();
        float plan = 0;
        float fact = 0;
        ReportDetails polut = null;
        for (ReportDetails reportDetails : details) {
            if (reportDetails.getResource().getKoef_polution() > 0) {
                if (polut == null) {
                    plan += reportDetails.getResource().getKoef_polution() * reportDetails.getPlan_col();
                    fact += reportDetails.getResource().getKoef_polution() * reportDetails.getFact_col();
                } else {
                    polut.setPlan_col(polut.getPlan_col() + reportDetails.getResource().getKoef_polution() * reportDetails.getPlan_col());
                    polut.setFact_col(polut.getFact_col() + reportDetails.getResource().getKoef_polution() * reportDetails.getFact_col());
                }
            } else {
                if (reportDetails.getResource().getKoef_polution() == -1) {
                    polut = reportDetails;
                    polut.setPlan_col(plan);
                    polut.setFact_col(fact);
                }
            }
        }
        //если строки по общему загрязнению нет - создаем ее
        if (polut == null) {
            polut = new ReportDetails();
            ReportDetails templ = rep.getDetails().get(0);
            polut.setDt_create(templ.getDt_create());
            polut.setDt_end(templ.getDt_end());
            polut.setDt_inputFact(templ.getDt_inputFact());
            polut.setDt_inputFact_test(templ.isDt_inputFact_test());
            polut.setFact_col(fact);
            polut.setId(0);
            polut.setPlan_col(plan);
            polut.setReport(templ.getReport());
            polut.setResource(new ResourceRepository().getPolutionResource().get(0));
            polut.setResponsible(templ.getResponsible());
        }
        return polut;
    }

    /**
     * Сохранение всего отчета вместе со сроками в БД
     */
    public String save() {
        boolean t_plan = false;
        boolean t_fact = false;

        //проверяем, по каждому ли ресурсу введена строка план/факт отдельно
        //пробегаем по списку ТЭР
        for (Resource objectlist : list) {
            if (!t_plan || !t_fact) {
                boolean t_plan1 = false;
                boolean t_fact1 = false;
                //пробегаем по всем строкам
                for (ReportDetails repor : mesListNew) {
                    if (!t_fact1) {
                        t_fact1 = (repor.getResource().getId() == objectlist.getId()) && (!t_fact) && (repor.isDt_inputFact_test());
                    }

                    if (!t_plan1) {
                        t_plan1 = (repor.getResource().getId() == objectlist.getId()) && (!t_plan);

                    }
                }
                if (t_plan1) {//нашли
                    t_plan = false;
                }
                if (!t_plan1) {//не нашли
                    t_plan = true;
                }

                if (t_fact1) {//нашли
                    t_fact = false;
                }
                if (!t_fact1) {//не нашли
                    t_fact = true;
                }
            }
        }



        //если по всем полям отчета есть план/факт - помечаем как слеоанное
        if (t_plan) {
            rep.setDt_inputPlan(null);
        }
        if (t_fact) {
            rep.setDt_inputFact(null);
        }
        if (!t_plan) {

            if (redactor) {
                if (rep.getDt_inputPlan() == null) {
                    rep.setDt_inputPlan(new Date());
                }
                /*====================ввести условие, если открыт доступ на редактирование, есть дата ввода плана и изменили план*/
            } else {
                rep.setDt_inputPlan(new Date());
            }

        }
        if (!t_fact) {
            rep.setDt_inputFact(new Date());
        }

        //сохраняем отчет
        //   try {
        getRep().setDt_create(new Date());

        Date rt = rep.getDt_begin();
        rt.setHours(0);
        rt.setMinutes(0);
        rt.setSeconds(0);
        rep.setDt_begin(rt);
        /* if(rep.getTime_begin()==null){
         rep.setTime_begin(new Date());
         }
         if(rep.getTime_finish()==null){
         rep.setTime_finish(new Date());
         }*/

        rep.setUsr(new UserRepository().get(new User(session.getUser().getUserID())));
        if (getRep().getTime_begin() != null
                && getRep().getTime_finish() != null
                && getRep().getForm() != null
                && getRep().getUsr() != null
                && getRep().getDepartment() != null
                && getRep().getDt_create() != null
                && getRep().getDt_begin() != null) {
            (new ReportRepository()).save(getRep());
            //сохраняем строки отчета в БД
            if(mesList.size()>0){
            for (ReportDetails repor : mesList) {
                (new ReportDetailsRepository()).saveAccurately(repor);
            }
            (new ReportDetailsRepository()).saveAccurately(genPolutionResult());
            
           
            }
            else{
            
            
            }
        } else {
            return "errorpageadditional";
        }
        /*  } catch (Exception e) {
         try { замениnm на  String
         FacesContext.getCurrentInstance().getExternalContext().redirect(WHERE);
         } catch (IOException ex) {
         Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/

        /*
         * заменил на String
         * try {
         FacesContext.getCurrentInstance().getExternalContext().redirect("mainpage.xhtml");
         } catch (IOException ex) {
         Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
         }*/


        return "mainpage";

    }

    /**
     * При изменении даты акции
     */
    public void addDate() {
        rep.setTime_begin(rep.getTime_begin());
        rep.setTime_finish(rep.getTime_finish());
        // rep.setDt_begin(rep.getDt_begin());
        Upd();
    }

    /**
     * По кнопке назад
     *
     * @return адрес сраницы для перехода
     */
    public String cancel() {
        return "mainpage";
    }

    /**
     * открытие ввода плана. При установке галочки на форме
     */
   public void addMessage() {
       
        
        
        
       
        
        
        if (ntec_blok||(sp_blok&&rep.getDepartment().getIdVp()==session.getUser().getDepartmentID())) {
      

        
        //для отчета в консоль
        String summary = rep.isSolveInputPlan() ? "Checked" : "Unchecked";

        //разрешили/отменили редактирование, кто, когда
        rep.setUsrSolvePlanInput(new User(session.getUser().getUserID()));
        rep.setDt_solveInputPlan(new Date());
        rep.setSolveInputPlan(rep.isSolveInputPlan());
        //сохранение
        //  try {
        (new ReportRepository()).save(rep);
        /* } catch (Exception e) {
         try {
         FacesContext.getCurrentInstance().getExternalContext().redirect(WHERE);
         } catch (IOException ex) {
         Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/
        //для отчета в консоль
       // System.out.println(summary);
        }
    }

    /**
     * Удаление репорт.
     *
     * @param rep отчет
     */
    public void deleteReport(Report rep) {
      //  System.out.println(rep);

        //удаление отчета вместе со всеми строками
        // try {
        (new ReportRepository()).deleteReport(rep);
        /*  } catch (Exception e) {
         try {
         FacesContext.getCurrentInstance().getExternalContext().redirect(WHERE);
         } catch (IOException ex) {
         Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/

    }

    /**
     * Удаление строки отчета. Через контекстное меню
     */
    //TODO посмотреть, возможно работу с БД можно оптимизировать
    //TODO настроить лог4
    public void deleteReportDetails() {

       System.out.println("id Report элемента: " + id);
        System.out.println("id Details удаляемого элемента: " + selectedDetails);
        if (selectedDetails.getResource().getKoef_polution() != -1) {

            //если отчект еще не сохранен в БД просто убираем лишние строки
            if (selectedDetails.getId() == 0) {
                rep.getDetails().remove(selectedDetails);
                mesList.remove(selectedDetails);
            } else {
                //если уже сохранен
                //удаляем лишние строки, и занова загружем отчет
                // try {
                (new ReportDetailsRepository()).deleteReportDetails(selectedDetails);
                rep = (new ReportRepository()).getWithDetails(id);
                /*} catch (Exception e) {
                 try {
                 FacesContext.getCurrentInstance().getExternalContext().redirect(WHERE);
                 } catch (IOException ex) {
                 Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }*/

                if (rep == null) {
                    rep = (new ReportRepository()).getWithDetailsRep(id);
                    rep.setDetails(new ArrayList<ReportDetails>());
                }

                //обновляем все данные бина
                Upd();

                System.out.println("object удаляемого элемента: " + selectedDetails.getId() + "   " + rep.getId() + "  " + rep.getDetails());
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="время">
    /*дата начала акции*/
    private Date getDT_begin() {
        Date dt_b = new Date();
        dt_b.setTime(rep.getTime_begin().getTime() + rep.getDt_begin().getTime() - session.getUser().getNum() * 60 * 1000 + 3*60*60*1000);
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.  HH:mm:ss");
        System.out.println("getDT_begin "+dateFormatter2.format(dt_b));
        return dt_b;
    }
    /*дата начала акции за 1 день*/
    
    private Date getDT_begin_za_1_day() {
        Date dt_b_m1 = new Date();
        dt_b_m1.setTime(getDT_begin().getTime() - 86400 * 1000);
       SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.  HH:mm:ss");
       System.out.println("getDT_begin_za_1_day "+dateFormatter2.format(dt_b_m1));
        return dt_b_m1;
    }
    
    /*дата окончания акции*/
    private Date getDT_end() {
        Date dt_f = new Date();
        dt_f.setTime(rep.getTime_finish().getTime() + rep.getDt_begin().getTime() - session.getUser().getNum() * 60 * 1000 + 3*60*60*1000);
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.  HH:mm:ss");
       System.out.println("getDT_end "+dateFormatter2.format(dt_f));
        
        return dt_f;
    }
    
    /*дата окончания проведения акции +3 деня(блокировка корректировки в 12 часов)*/
    private Date getDT_end_12() {
        
        //третьи сутки
        Calendar ct = Calendar.getInstance();
        ct.setTime(getDT_end());
        ct.add(Calendar.HOUR, 72);
        ct.set(Calendar.MINUTE, 0);
        ct.set(Calendar.HOUR_OF_DAY, 12);
        
        Date dt_b_m2 = new Date();
        dt_b_m2.setTime(ct.getTime().getTime() - session.getUser().getNum() * 60 * 1000 );
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.  HH:mm:ss");
       System.out.println("getDT_end_12 "+dateFormatter2.format(dt_b_m2));
        return dt_b_m2;
    }
    
    /*дата окончания проведения акции +3 дня(блокировка предоставление доступа на блокировку дирекции в 15 часов)*/
    private Date getDT_end_15() {
        Calendar ct1 = Calendar.getInstance();
        ct1.setTime(getDT_end());
        ct1.add(Calendar.HOUR, 72);
        ct1.set(Calendar.MINUTE, 0);
        ct1.set(Calendar.HOUR_OF_DAY, 15);
        
        Date dt_b_m21 = new Date();
        dt_b_m21.setTime(ct1.getTime().getTime()- session.getUser().getNum() * 60 * 1000 );
         SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.  HH:mm:ss");
       System.out.println("getDT_end_15 "+dateFormatter2.format(dt_b_m21));
      
        return dt_b_m21;
    }
    
    /*получение последней пятници месяца*/
    private Date getDateFRIDAY(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyy.  HH:mm:ss");
     //   System.out.println(dateFormatter2.format(c.getTime()));
      //  System.out.println(c.getTime().getDay());
        while (c.getTime().getDay() != 5) {
            c.add(Calendar.DATE, -1);
        }
       // System.out.println(dateFormatter2.format(c.getTime()));
        return c.getTime();
    }
    //</editor-fold>
    

    
    private void Upd() {


        if (rep.getDetails() != null) {
            for (ReportDetails repRe : rep.getDetails()) {
                mesListNew.add(repRe);
            }
        }

        Date dt_b_m1 = getDT_begin_za_1_day();

        //за день до начала акции блокируется редактирование плана
        bloc_rec_fact=dt_b_m1.before(new Date());

        //дата окончания проведения акции
        Date dt_f_end = getDT_end();


        //открытие факта
        t_fact_form=dt_f_end.before(new Date());
       
        //нельза вводить факт сразу при создании нового отчета. только при редактировании старого
        t_fact_form = t_fact_form && redactor;

        /*дата окончания проведения акции +3 деня(блокировка корректировки в 12 часов)*/
        Date dt_b_m12 = getDT_end_12();
        
        
        /*блокировка ввода данных предприятиями*/
        bloc_out_time_input_all=dt_b_m12.after(new Date());
        System.out.println("new Date() "+new Date());

        /*дата окончания проведения акции +3 деня(блокировка предоставление доступа на блокировку дирекции в 15 часов)*/
        Date dt_b_m15 = getDT_end_15();

        /*предоставление доступа на разблокировку дирекции */
        sp_blok=dt_b_m15.after(new Date()) && dt_b_m12.before(new Date());
        

        
       
        /*предоставление доступана редактирования для редактирования НТЭЦ после 15 часов*/
        ntec_blok=dt_b_m15.before(new Date())&&session.getUser().getIdType()==3;
             
        /*предоставление доступана редактирования  НТЭЦ после 15 часов(появление кнопки)*/
       // System.out.println(rep.getUsrSolvePlanInput().getUserType().getId());
        
        
        if(rep.getUsrSolvePlanInput()!=null){
        button_ntec_blok=dt_b_m15.before(new Date())
                &&rep.isSolveInputPlan()
                &&rep.getUsrSolvePlanInput().getUserType().getId()==3;}
        else{
        button_ntec_blok=false;
        }
    }


    

    private void loadListOfResource(long railway, Date dat){
        //извдекаем из БД список всех видов ресурсов и убираем из него загрязнение окр. среды
        list = (new ResourceRepository()).getAllNonDeletedWithCost(railway, dat);
        Resource polut = null;
        for (Resource resource : list) {
            if (resource.getKoef_polution() == -1) {
                polut = resource;
                break;
            }
        }
        if (polut != null) {
            list.remove(polut);
        }
    }
    
    private Resource getResource(long id){
        for (Resource resource : list) {
            if(resource.getId()==id){
                return resource;
            }
        }
        return null;
    }
    
    @PostConstruct
    public void Init() {
        if((new CheckRights()).isAutorised()){
        long rail=session.getUser().getDepartmentPower();

        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id")) {
            //если был передан id отчета, значит вошли на редактирование
            String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString();
            redactor = true;
            t_dep_form = true;
            // try {
            id = new Integer(idStr);
            rep = (new ReportRepository()).getWithDetailsAndCosts(rail, id);
            /* } catch (Exception e) {
             try {
             FacesContext.getCurrentInstance().getExternalContext().redirect(WHERE);
             } catch (IOException ex) {
             Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
             }
             }*/

            //если в БД найден отчет - вычисляем доп. пар-ры
            //если в БД найден отчет - вычисляем доп. пар-ры
            if (rep == null) {
                rep = (new ReportRepository()).getWithDetailsRep(id);
                if (rep != null) {
                    rep.setDetails(new ArrayList<ReportDetails>());
                }
            }

            if (rep != null) {
                loadListOfResource(rail, rep.getDt_begin());
                //если в списке есть строка с результирующим загрязнением - она переносится в конец списка
                List<ReportDetails> details = rep.getDetails();
                ReportDetails k=null;
                for (ReportDetails reportDetails : details) {
                    if(reportDetails.getResource().getKoef_polution()==-1){
                        k=reportDetails;
                        break;
                    }
                }
                if(k!=null){
                    details.remove(k);
                    details.add(k);
                }
                
                
                if (rep.getDepartment().getId() == session.getUser().getDepartmentID()) {
                    t_dep_form = false;
                }
                Upd();
            }



        } else {
            //вычисляем флаги для редактирования
            bloc_rec_fact = false;
            t_dep_form = false;
            t_fact_form = false;
            redactor = false;
            rep = new Report();
            bloc_out_time_input_all = true;
            //заполняем для отчета всю возможную информацию: форму, пользователя, СП, время начала/окончания акции по умолчанию
            //  try {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("form_id")) {
                rep.setForm(new Form(new Long(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("form_id").toString())));

                rep.setUsr(new UserRepository().get(new User(session.getUser().getUserID())));
                rep.setDepartment(rep.getUsr().getDepartment());
                rep.setTime_begin(new Date());
                rep.setTime_finish(new Date());
                /* } catch (Exception e) {
                 try {
                 FacesContext.getCurrentInstance().getExternalContext().redirect(WHERE);
                 } catch (IOException ex) {
                 Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }*/


                //в каждом СП можно создать только 1 отчетр в месяц. Поэтому для нового отчета проверяется 
                //дата последнего отчета по этому СП, и далее можно создавать отче только на след. месяц
                Date dt_max = null;
                // try {
                dt_max = (new ReportRepository()).getDateDetails(1, rep.getDepartment().getId());
                /* } catch (Exception e) {
                 try {
                 FacesContext.getCurrentInstance().getExternalContext().redirect(WHERE);
                 } catch (IOException ex) {
                 Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }*/







                Calendar c = Calendar.getInstance();

                if (dt_max == null) {
                    /*-----------------------------------------заменить на сформерованную дату(пятницу)--------------------------------------------*/
                    Date now = new Date();
                    //c.setTime(now);
                    Date dateFRIDAY = getDateFRIDAY(now);
                    rep.setDt_begin(dateFRIDAY);
                    
                    loadListOfResource(rail, dateFRIDAY);

                    for (Resource resource : list) {

                        ReportDetails rd = new ReportDetails();
                        rd.setResource(resource);
                        rep.getDetails().add(rd);
                        localEdit(rd);
                    }


                } else {
                    /*-----------------------------------------заменить на сформерованную дату(пятницу)--------------------------------------------*/

                    c.setTime(dt_max);
                  //  System.out.println("====== " + c);
                    
                   // c.set(Calendar.DATE, 1);
                   // c.add(Calendar.MONTH, 1);
                    //rep.setDt_begin(getDateFRIDAY(c.getTime()));
                    List<Report> gg = (new ReportRepository()).getListWithDetailsIdNpMonth(rep.getUsr().getDepartment().getIdNp(), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR));
                    c.set(Calendar.DATE, 1);
                    c.add(Calendar.MONTH, 1);
                    Date dateFRIDAY = getDateFRIDAY(c.getTime());
                    rep.setDt_begin(dateFRIDAY);
                    loadListOfResource(rail, dateFRIDAY);
                  //  System.out.println("====== " + c.get(Calendar.MONTH)+1);
                  //  System.out.println("====== " + c.get(Calendar.YEAR));
                  //  System.out.println("====== " + gg);

                    for (Report report : gg) {
                        rep.setTime_begin(report.getTime_begin());
                        rep.setTime_finish(report.getTime_finish());
                        rep.setCostsBy(list);
                        System.out.println("======gg " + report);
                        int i=0;
                        for (ReportDetails details : report.getDetails()) {
                            if (details.getResource().getKoef_polution() != -1) {
                                i++;
                                ReportDetails rd = new ReportDetailsWithAdditionalID(i);

                                rd.setActivity(details.getActivity());
                                rd.setPlan_col(details.getPlan_col());
                                //rd.setPlan_money(details.getPlan_money());
                                rd.setPlan_money(0);
                                rd.setPlan_tut(details.getPlan_tut());
                                rd.setFact_col(details.getFact_col());
                                //rd.setFact_money(details.getFact_money());
                                rd.setFact_money(0);
                                rd.setFact_tut(details.getFact_tut());
                                rd.setResponsible(details.getResponsible());
                                rd.setAddressOfObject(details.getAddressOfObject());
                                rd.setPowerSource(details.getPowerSource());
                                rd.setType(details.getType());
                                rd.setNum(details.getNum());
                                rd.setAskue(details.isAskue());
                                rd.setAddres(details.getAddres());
                                rd.setUsedBefore(details.getUsedBefore());
                                rd.setUsedToday(details.getUsedToday());
                                rd.setResource(details.getResource());
                                rep.getDetails().add(rd);
                                localEdit(rd);
                                System.out.println("======details " + details);
                            }

                        }
                    }



                }

            } else {
                session.setError("Необходимо выполнить повторный вход в систему");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("errorpage");
                } catch (IOException ex) {
                    Logger.getLogger(FormOnePageBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="печать">
    public void Print(Object document) {
        StringImprover f = new StringImprover();
        //настройка докумениа
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.createSheet("new sheet");
        HSSFPrintSetup print = sheet.getPrintSetup();
        print.setScale((short) 83);
        print.setPaperSize((short) HSSFPrintSetup.A4_PAPERSIZE);

        //настойка шрифтов
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

        //настройка стилей ячеек
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

        //шапка документа
        Row row = sheet.createRow(0);
        new createCell().createCell(wb, sheet, cellStyle_R, row, 0, 0, 0, 0, 7, "Форма 1");

        Row row1 = sheet.createRow(1);
        new createCell().createCell(wb, sheet, cellStyle, row1, 1, 1, 0, 0, 7, "ОТЧЕТ");

        Row row2 = sheet.createRow(2);
        new createCell().createCell(wb, sheet, cellStyle_L, row2, 2, 2, 0, 0, 7, "по проведению акции в:   " + rep.getUsr().getDepartment().getNameNp());

        Row row3 = sheet.createRow(3);
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("HHч. mmмин.");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd.MM.yyyyг.");
        new createCell().createCell(wb, sheet, cellStyle_L, row3, 3, 3, 0, 0, 7, "Время проведения акции:   " + "C " + dateFormatter1.format(rep.getTime_begin()) + " по " + dateFormatter1.format(rep.getTime_finish()) + " " + dateFormatter2.format(rep.getDt_begin()));

        Row row4 = sheet.createRow(4);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 3, 3, 3);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 4, 4, 4);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 6, 6, 6);
        createCellNull.createCellNull(sheet, cellStyle2, row4, 4, 4, 7, 7, 7);
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 0, 0, 0, "Вид ресурса");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 5, 1, 1, 1, "Мероприятия наименование объекта");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 2, 2, 4, "План экономии ТЭР");
        new createCell().createCell(wb, sheet, cellStyle2, row4, 4, 4, 5, 5, 7, "Факт экономии");

        Row row5 = sheet.createRow(5);
        createCellNull.createCellNull(sheet, cellStyle2, row5, 5, 5, 0, 0, 0);
        createCellNull.createCellNull(sheet, cellStyle2, row5, 5, 5, 1, 1, 1);
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 2, 2, 2, "натур изм.");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 3, 3, 3, "тут");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 4, 4, 4, "тыс. руб");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 5, 5, 5, "натур изм.");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 6, 6, 6, "тут");
        new createCell().createCell(wb, sheet, cellStyle2, row5, 5, 5, 7, 7, 7, "тыс. руб");

        //цифры - нумерация столбцов
        HSSFRow row6 = sheet.createRow(6);
        for (int i1 = 0; i1 < 8; i1++) {
            new createCell().createCell(wb, sheet, cellStyle1, row6, 6, 6, i1, i1, i1, String.valueOf(i1 + 1));
        }

        //заполнение информмацией
        List<ReportDetails> details = rep.getDetails();
        int j = 7;
        int i = 0;
        //смотрим список ресурсов
        for (Resource ter : list) {
            boolean exists = false;
            for (ReportDetails repDetails : details) {
                if (repDetails.getResource().getId() == ter.getId()) {
                    //если для ресурса есть строки в отчете - они выводятся в эксель
                    exists = true;
                    j = i + 7;
                    HSSFRow header8 = sheet.createRow(j);

                    new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 0, 0, 0, repDetails.getResource().getEXELString());
                    new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 1, 1, 1, repDetails.getActivity());
                    new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 2, 2, 2, f.formatForEXCEL(repDetails.getPlan_col()));
                    new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 3, 3, 3, f.formatForEXCEL(repDetails.getPlan_tut()));
                    new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 4, 4, 4, f.formatMoneyForEXCEL(repDetails.getPlan_money()));
                    if (t_fact_form && repDetails.isDt_inputFact_test()) {
                        new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 5, 5, 5, f.formatForEXCEL(repDetails.getFact_col()));
                        new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, f.formatForEXCEL(repDetails.getFact_tut()));
                        new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, f.formatMoneyForEXCEL(repDetails.getFact_money()));
                    } else {
                        new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 5, 5, 5, "");
                        new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, "");
                        new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, "");
                    }
                    i++;
                }
            }
            if (!exists) {
                //если для ресурса нет строк в отчете - выводятся нули и пустые значения
                j = i + 7;
                HSSFRow header8 = sheet.createRow(j);

                new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 0, 0, 0, ter.getEXELString());
                new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 1, 1, 1, "");
                new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 2, 2, 2, "");
                new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 3, 3, 3, "");
                new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 4, 4, 4, "");
                new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 5, 5, 5, "");
                new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 6, 6, 6, "");
                new createCell().createCell(wb, sheet, cellStyle1, header8, j, j, 7, 7, 7, "");
                i++;
            }
        }

        //футер
        j++;
        HSSFRow headerj1 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_L, headerj1, j, j, 0, 0, 7, "Отчет составлен: " + dateFormatter2.format(rep.getDt_create()));

        j++;
        HSSFRow headerj2 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_L, headerj2, j, j, 0, 0, 7, "Исполнитель: " + rep.getUsr().getFIO());

        j++;
        HSSFRow headerj3 = sheet.createRow(j);
        new createCell().createCell(wb, sheet, cellStyle_L, headerj3, j, j, 0, 0, 7, "Телефон: " + rep.getUsr().getPhoneNumber());

        for (int i1 = 0; i1 < 8; i1++) {
            sheet.autoSizeColumn(i1, true);

        }

        //удаление лишнего листа из документа
        wb.removeSheetAt(0);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="не используемое">
    /**
     * Возвращает максимально возможную дату
     *
     * @return максимально возможная дата
     */
    /* public String getDt_max_cal_st() {
     return dt_max_cal_st;
     }*/
    /**
     * Возвращает минимально возможную дату
     *
     * @return минимально возможная дата
     */
    /* public String getDt_min_cal_st() {
     return dt_min_cal_st;
     }*/

    /*
     *
     * 
     *  public void viewCars() {
     * System.out.println("wwwwwwwwwwwwwwwwww");
     * /*     for (ReportDetails reportDetails : getRep().getDetails()) {
     * if (reportDetails.getResource().getName().equalsIgnoreCase("Электроэнергия")) {
     * six.getList().add(reportDetails);
     * }
     * }
     * 
     * 
     * 
     * // RequestContext.getCurrentInstance().openDialog("formsix");
     * }
     * public void setRedactor(boolean redactor) {
     * this.redactor = redactor;
     * }
     * public void setT_dep_form(boolean t_dep_form) {
     * this.t_dep_form = t_dep_form;
     * }
     *
     * public void setDt_max_cal_st(String dt_max_cal_st) {
     * this.dt_max_cal_st = dt_max_cal_st;
     * }
     *
     * public void setDt_min_cal_st(String dt_min_cal_st) {
     * this.dt_min_cal_st = dt_min_cal_st;
     * }
     *
     * public void setList(List<Resource> list) {
     * this.list = list;
     * }
     *
     * public void setT_fact_form(boolean t_fact_form) {
     * this.t_fact_form = t_fact_form;
     * }
     *
     * public void setBloc_rec_fact(boolean bloc_rec_fact) {
     * this.bloc_rec_fact = bloc_rec_fact;
     * }
     *  public void deleteReport(Report rep) {
     * //для отчета в консоль
     * System.out.println(rep);
     * 
     * //удаление отчета
     * (new ReportRepository()).deleteReport(rep);
     * //удаление строк из отчета
     * if (rep.getDetails() != null) {
     * for (ReportDetails repDet : rep.getDetails()) {
     * (new ReportDetailsRepository()).deleteReportDetails(repDet);
     * }
     * }
     * 
     * }
     *  public void Init() {
     * if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id")) {
     * String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString();
     * id = new Integer(idStr);
     * rep = (new ReportRepository()).getWithDetails(id);
     * 
     * 
     * if (rep == null) {
     * rep = (new ReportRepository()).getWithDetailsRep(id);
     * if (id != 0) {
     * List<ReportDetails> rr = new ArrayList<ReportDetails>();
     * rep.setDetails(rr);
     * }
     * }
     * 
     * 
     * if (rep != null) {
     * Upd();
     * 
     * Date dt_max = rep.getDt_begin();
     * Calendar dt_max_cal = Calendar.getInstance();
     * dt_max_cal.setTime(dt_max);
     * dt_max_cal.set(Calendar.DATE, 1);
     * SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
     * dt_min_cal_st = dateFormatter1.format(dt_max_cal.getTime());
     * System.out.println(dt_min_cal_st);
     * dt_max_cal.add(Calendar.MONTH, 1);
     * dt_max_cal.add(Calendar.DATE, -1);
     * dt_max_cal_st = dateFormatter1.format(dt_max_cal.getTime());
     * System.out.println(dt_max_cal_st);
     * 
     * 
     * 
     * if (rep.getDepartment().getId() == (new UserRepository().get(new User(session.getUser().getUserID()))).getDepartment().getId()) {
     * t_dep_form = false;
     * }
     * }
     * 
     * 
     * } else {
     * bloc_rec_fact = false;
     * t_dep_form = false;
     * t_fact_form = false;
     * redactor = false;
     * rep = new Report();
     * if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("form_id")) {
     * rep.setForm(new Form(new Long(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("form_id").toString())));
     * }
     * 
     * rep.setUsr(new UserRepository().get(new User(session.getUser().getUserID())));
     * rep.setDepartment(rep.getUsr().getDepartment());
     * rep.setDt_begin(new Date());
     * rep.setTime_begin(new Date());
     * rep.setTime_finish(new Date());
     * 
     * Date dt_max = (new ReportRepository()).getDateDetails(1, rep.getDepartment().getId());
     * 
     * if (dt_max == null) {
     * dt_min_cal_st = "";
     * dt_max_cal_st = "";
     * rep.setDt_begin(new Date());
     * } else {
     * 
     * Calendar dt_max_cal = Calendar.getInstance();
     * dt_max_cal.setTime(dt_max);
     * dt_max_cal.set(Calendar.DATE, 1);
     * dt_max_cal.add(Calendar.MONTH, 1);
     * SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd.MM.yyyy");
     * dt_min_cal_st = dateFormatter1.format(dt_max_cal.getTime());
     * rep.setDt_begin(dt_max_cal.getTime());
     * dt_max_cal.add(Calendar.MONTH, 1);
     * dt_max_cal.add(Calendar.DATE, -1);
     * dt_max_cal_st = dateFormatter1.format(dt_max_cal.getTime());
     * System.out.println(dt_max_cal_st);
     * 
     * }
     * 
     * 
     * System.out.println(dt_min_cal_st);
     * System.out.println(dt_max_cal_st);
     * 
     * //(new ReportRepository()).save(rep);
     * }
     * 
     * 
     * //rep.getDetails().add(new ReportDetails());
     * list = (new ResourceRepository()).getAllNonDeleted(Resource.class);
     * }
     * 
     */
    //</editor-fold>
}
