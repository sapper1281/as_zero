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
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsWithAdditionalID;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 *
 * @author VVolgina
 */
public class ReportDetailsRepository extends BaseRepository {

    public void deleteReportDetails(ReportDetails rep) {
       if (rep.getId() != 0) {
            rep.setDeleted(true);
            Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                    .openSession();
            Transaction t = null;
            try {
                t = sess.beginTransaction();
                save(rep, sess);

                t.commit();
            } catch (Exception e) {
                Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, e);
                if (t != null) {
                    try {
                        t.rollback();
                    } catch (HibernateException he) {
                        Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, he);
                    }
                }
            } finally {
                try {
                    sess.close();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        }
    }

    protected void deleteReportDetails(ReportDetails rep, Session sess) {
        if (rep.getId() != 0) {
            rep.setDeleted(true);
            save(rep, sess);
        }
    }

    public void saveAllDetails(List<ReportDetails> list) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            for (ReportDetails reportDetails : list) {
                save(reportDetails, sess);
            }

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }
    }

    
    public void saveAccurately(ReportDetails det) {
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();
        Transaction t = null;
        try {
            t = sess.beginTransaction();
            if (det instanceof ReportDetailsWithAdditionalID) {
                ReportDetails det1 = new ReportDetails();
                det1.copyFrpm(det);
                sess.saveOrUpdate(det1);
                det.setId(det1.getId());
            } else {

                sess.saveOrUpdate(det);
            }
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportDetailsRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

    }
}
