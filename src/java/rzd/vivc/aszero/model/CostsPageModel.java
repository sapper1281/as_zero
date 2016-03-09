/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.model;

import java.util.Date;
import rzd.vivc.aszero.dto.Costs;
import rzd.vivc.aszero.service.StringImprover;
import zdislava.model.dto.security.users.User;

/**
 * Вся информация, которая нужна для страницы formCosts
 *
 * @author VVolgina
 */
public class CostsPageModel {
    //<editor-fold defaultstate="collapsed" desc="поля">

    private Costs costs=new Costs();
    private Date selectedDate;
    private String userFIO;
    private String railwayName;
    private long railwayID;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * ВоЗвращает список цен по все ресурсам за месяц
     *
     * @return список цен по все ресурсам за месяц
     */
    public Costs getCosts() {
        return costs;
    }

    /**
     * Назначает список цен по все ресурсам за месяц
     *
     * @param costs список цен по все ресурсам за месяц
     */
    public void setCosts(Costs costs) {
        this.costs = costs;
    }

    /**
     * возвращает дату с месяцем и годом для которой выводятся цены
     *
     * @return дата с месяцем и годом для которой выводятся цены
     */
    public Date getSelectedDate() {
        return selectedDate;
    }

    /**
     * возвращает дату с месяцем и годом для которой выводятся цены
     *
     * @return дата с месяцем и годом для которой выводятся цены
     */
    public String getYearAndMonth() {
        return new StringImprover().getMonthAndYear(selectedDate);
    }

    /**
     * Устанавливает в модели дату по номеру месяца и 4 цифрам года
     *
     * @param month номер месяца
     * @param year 4 цифры года
     */
    public void setDateByYearAndMonth(int month, int year) {
        setSelectedDate(new StringImprover().getDateByMonthAndFullYear(month, year));
    }

    /**
     * Назначает дату с месяцем и годом для которой выводятся цены
     *
     * @param selectedDate дата с месяцем и годом для которой выводятся цены
     */
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
        StringImprover stringImprover = new StringImprover();
        costs.setMonth(stringImprover.getMonth(selectedDate));
        costs.setYear(stringImprover.getYear(selectedDate));
    }

    /**
     * Возвращает ФИО пользователя, осуществляющего редактирование цен
     *
     * @return ФИО пользователя, осуществляющего редактирование цен
     */
    public String getUserFIO() {
        return userFIO;
    }

    /**
     * Назначает пользователя, осуществляющего редактирование цен. Прописывает
     * его для всех цен и запоминает фио
     *
     * @param usr пользователь, осуществляющий редактирование цен
     */
    public void setUser(User usr) {
        this.userFIO = usr.getFIO();
        costs.setUsr(usr);
    }

    /**
     * Возвращает название дороги, для которой выводятся цены
     *
     * @return название дороги, для которой выводятся цены
     */
    public String getRailwayName() {
        return railwayName;
    }

    /**
     * Назначает название дороги, для которой выводятся цены
     *
     * @param railwayName название дороги, для которой выводятся цены
     */
    public void setRailwayName(String railwayName) {
        this.railwayName = railwayName;
    }

    /**
     * Возвращает id дороги, для которой выводятся цены
     *
     * @return id дороги, для которой выводятся цены
     */
    public long getRailwayID() {
        return railwayID;
    }

    /**
     * Назначает id дороги, для которой выводятся цены
     *
     * @param railwayID id дороги, для которой выводятся цены
     */
    public void setRailwayID(long railwayID) {
        this.railwayID = railwayID;
        costs.setRailway(railwayID);
    }

    //</editor-fold>
    public CostsPageModel() {
    }
}
