/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.util.Date;
import java.util.List;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rzd.vivc.aszero.dto.CostDetails;
import rzd.vivc.aszero.dto.Costs;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.model.CostsPageModel;
import zdislava.common.dto.configuration.SessionFactorySingleton;
import zdislava.model.dto.security.users.User;

/**
 * Работа с БД для страницы formCosts
 *
 * @author VVolgina
 */
public class CostsPageDBWork {

    //<editor-fold defaultstate="collapsed" desc="загрузка из БД">
    /**
     * Извлекает из Бд и готовит к отображению на странице цен информацию.
     * Список цен берется на все ресурсы по данной дате во все цены сразу
     * заносится текущий пользователь системы в кач-ве последнего исправившего
     *
     * @param dat год и месяц, на который нужны цены
     * @param reailwayID id жд для которой цены
     * @param userID id пользователя, осуществляющего редактирование
     * @return инфо для страницы
     */
    public CostsPageModel loadAllInfoFromDB(Date dat, long reailwayID, long userID) {
        //СОЗДАЕМ МОДЕЛЬ, В НЕЕ дату
        CostsPageModel model = new CostsPageModel();
        model.setSelectedDate(dat);
        return loadAllInfoFromDB(model, reailwayID, userID);
    }

    /**
     * Извлекает из Бд и готовит к отображению на странице цен информацию.
     * Список цен берется на все ресурсы по данной дате во все цены сразу
     * заносится текущий пользователь системы в кач-ве последнего исправившего
     *
     * @param mes номер месяца для которого цены
     * @param year 4 цифры года
     * @param reailwayID id жд для которой цены
     * @param userID id пользователя, осуществляющего редактирование
     * @return инфо для страницы
     */
    public CostsPageModel loadAllInfoFromDB(int mes, int year, long reailwayID, long userID) {
        //СОЗДАЕМ МОДЕЛЬ, В НЕЕ месяц и год для даты
        CostsPageModel model = new CostsPageModel();
        model.setDateByYearAndMonth(mes, year);
        return loadAllInfoFromDB(model, reailwayID, userID);
    }

    //общая часть публичных методов
    private CostsPageModel loadAllInfoFromDB(CostsPageModel model, long reailwayID, long userID) {
        //СОЗДАЕМ МОДЕЛЬ, В модель ИЗВЕСТНЫЕ ДАННЫЕ
        model.setRailwayID(reailwayID);

        List<Resource> resources;

        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();

        try {
            resources = new ResourceRepository().getActiveResource(sess);
            model.setRailwayName(new DepartmentRepository().get(reailwayID, sess).getNameNp());
            //забиваем цены из Бд, пользователя и ЖД
            Costs byDate = new CostsRepository().getByDate(model.getSelectedDate(), reailwayID, sess);
            if (byDate != null) {
                model.setCosts(byDate);
                model.setUser(new UserRepository().get(new User(userID), sess));
            } else {                
                model.setUser(new UserRepository().get(new User(userID), sess));
                saveList(model, sess);
            }
            
            t.commit();
            sess.close();
        } catch (JDBCException e) {
            t.rollback();
            sess.close();
            throw new JDBCException(e.getMessage(), e.getSQLException());
        }

        //Проверка, что в списке цен на ресурсы есть цена для каждого из ресурсов системы
        //Добавляет отсутствующие
        ///TODO Если в системе будет очень много ресурсов - значит надо делать эту проверку как-то по-другому
        List<CostDetails> costs = model.getCosts().getDetails();
        for (Resource resource : resources) {
            boolean flag = false;
            for (CostDetails cost : costs) {
                if (cost.getResource().getId() == resource.getId()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                model.getCosts().addDetails(new CostDetails(resource));
            }
        }

        return model;
    }
    //</editor-fold>

    /**
     * Сохраняет в БД весь список цен
     *
     * @param model модель с ценами
     */
    public void saveList(CostsPageModel model) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();
        try {
            saveList(model, sess);
            t.commit();
            sess.close();
        } catch (JDBCException e) {
            t.rollback();
            sess.close();
            throw new JDBCException(e.getMessage(), e.getSQLException());
        }
    }
    
    /**
     * Сохраняет в БД весь список цен
     *
     * @param model модель с ценами
     * @param sess сессия Hibernate
     */
    protected void saveList(CostsPageModel model, Session sess) {
            new CostsRepository().save(model.getCosts(), sess);
    }
}
