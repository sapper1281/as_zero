/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rzd.vivc.aszero.classes.FormSevenNew;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 *
 * @author apopovkin
 */
public class SevenRepository {

    public List<FormSevenNew> getActiveListFormSeven(int month, int year) {

        List<FormSevenNew> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getActiveListFormSeven(month, year, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(SevenRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(SevenRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(SevenRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<FormSevenNew> getActiveListFormSeven(int month, int year, Session sess) {



        return (List<FormSevenNew>) sess.createQuery(
                "select "
                + "depDer.nameNp as col1, "
                + "dep.nameNp as col2,  "
                + "'экономия электроэнергии' as col3, "
                + "detail.activity as activity,  "
                + "detail.addres as addres,  "
                + "detail.responsible as responsible,  "
                + "detail.powerSource as powerSource,  "
                + "detail.addressOfObject as addressOfObject,  "
                + "detail.type as type,  "
                + "detail.num as num,  "
                + "detail.askue as askue,  "
                + "detail.plan_col as plan_col,  "
                + "detail.fact_col as fact_col  "
                + " from resource_details detail "
                + " join report rep on rep.id=detail.report_id "
                + " join department dep on rep.department_id=dep.id "
                + " join department depDer on depDer.id=dep.idVp "
                + "where rep.form_id=1 and month(rep.dt_begin)=:month and year(rep.dt_begin)=:year "
                + "order by 1,2")
                .setInteger("month", month).setInteger("year", year).list();

    }
}
