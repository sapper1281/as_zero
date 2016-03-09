/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rzd.vivc.aszero.dto.Costs;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.Resource;
import rzd.vivc.aszero.dto.form.Form;
import rzd.vivc.aszero.model.OnePageModel;
import zdislava.common.dto.configuration.SessionFactorySingleton;
import zdislava.model.dto.security.users.User;

/**
 * Работа с БД для страницы formone.xhtml
 *
 * @author VVolgina
 */
public class OnePageDbWork {

    public OnePageModel loadNew(long railwayID, long userID, long formID) {
        OnePageModel model = new OnePageModel();

        //объявляем переменные для удобства работы
        Report rep = new Report();
        //список ресурсов, по которым можно будет создавать строки в отчете
 //отсюда
        List<Resource> list;
        Costs costs;
        Date dateOfReport;
        User usr;
        

        //лезем в БД
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = sess.beginTransaction();

        try {
            //извдекаем из БД список всех видов ресурсов 
            list = (new ResourceRepository()).getAllNonDeleted(Resource.class, sess);
            //достаем пользователя из БД
            usr = new UserRepository().get(new User(userID), sess);
            //в каждом СП можно создать только 1 отчетр в месяц. Поэтому для нового отчета проверяется 
            //дата последнего отчета по этому СП, и далее можно создавать отче только на след. месяц
            dateOfReport = (new ReportRepository()).getDateDetails(1, usr.getDepartment().getId());
//отсюда
            t.commit();
            sess.close();
        } catch (JDBCException e) {
            t.rollback();
            sess.close();
            throw new JDBCException(e.getMessage(), e.getSQLException());
        }

        rep.setForm(new Form(formID));
        rep.setUsr(usr);
        rep.setDepartment(rep.getUsr().getDepartment());
        rep.setTime_begin(new Date());
        rep.setTime_finish(new Date());

        //убираем из ресурсов загрящнение окр. среды и добавляем в него цены на текущий период
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

        model.setRep(rep);
        model.setList(list);

        return model;

    }
}
