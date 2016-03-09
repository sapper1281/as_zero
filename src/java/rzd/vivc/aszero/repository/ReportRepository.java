/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.type.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoReport;
import rzd.vivc.aszero.classes.FormSevenNew;
import rzd.vivc.aszero.dto.Costs;
import rzd.vivc.aszero.dto.DetailsForm2;
import rzd.vivc.aszero.dto.DetailsForm2_dop;
import rzd.vivc.aszero.dto.DetailsForm4;
import rzd.vivc.aszero.dto.DetailsForm5;
import rzd.vivc.aszero.dto.FormGraf;
import rzd.vivc.aszero.dto.Report;
import rzd.vivc.aszero.dto.ReportDetails;
import rzd.vivc.aszero.dto.ReportDetailsForm2;
import rzd.vivc.aszero.service.StringImprover;
import zdislava.common.dto.configuration.SessionFactorySingleton;

/**
 *
 * @author VVolgina
 */
public class ReportRepository extends BaseRepository implements Serializable{

    /*Форма 4 период*/
    public List<DetailsForm4> getListWithDetailsFormFourPER(int idPower, Date dayBegin, Date dayEnd) {


        List<DetailsForm4> bd = new ArrayList();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsFormFourPER(idPower, dayBegin, dayEnd, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<DetailsForm4> getListWithDetailsFormFourPER(int idPower, Date dayBegin, Date dayEnd, Session sess) {


        Query query = sess.createSQLQuery(
                " select   "
                + " sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_col*koef ELSE 0   "
                + " end) as plan_tyt,  "
                + "  sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_col*koef ELSE 0   "
                + " end) as fact_tyt,  "
                + " sum(case when details.resource_id in(13) then plan_col ELSE 0   "
                + " end) as planE,  "
                + " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0   "
                + " end) as factE,  "
                + " sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0   "
                + " end) as factASKUE,  "
                + " count( distinct   dep.id) as allPred,  "
                + " count( distinct case when rep.dt_inputplan is not null then dep.id end) as planProc,  "
                + " count( distinct case when rep.dt_inputfact is not null then dep.id end) as factProc,  "
                + " depGroup.id as idGroup,  "
                + " depGroup.namenp as namenpGroup,  "
                + "  depPer.id ,  "
                + "  depPer.namenp "
                // + " repF2.id as idF2   "
                + "   from  "
                + "                          ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=" + idPower + " and   "
                + "                          dtbeginNSI<=:dayEnd  and   "
                + "                          (dt_end>=:dayBegin or dt_end is null  )) as depPer     "
                + "  inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and   "
                + "                          dtbeginNSI<=:dayEnd and   "
                + "                          (dt_end>=:dayBegin or dt_end is null  )  "
                + "                          ) as dep on depPer.id =dep.idVp  "
                + "  left join (select * from DB2ADMIN.REPORT  "
                + "   where dt_begin>=:dayBegin and dt_begin<=:dayEnd  and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id "
                + "    left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID  "
                // + "                      left join (select * from DB2ADMIN.REPORT "
                // + "                where dt_begin>=:dayBegin and dt_begin<=:dayEnd and form_id=2 and DEL_FL=0) as repF2 "
                // + "                      on repF2.department_id=depPer.id  "
                + "                          left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and   "
                + "                          dtbeginNSI<=:dayEnd  and   "
                + "                          (dt_end>=:dayBegin or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup   "
                + " left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   "
                + "  left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id  "
                + "                         group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  "
                + "                         order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id   with ur" /* " select   "+

                 " sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_tut ELSE 0   "+
                 " end) as plan_tyt,  "+
                 "  sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_tut ELSE 0   "+
                 " end) as fact_tyt,  "+
                 " sum(case when details.resource_id in(13) then plan_col ELSE 0   "+
                 " end) as planE,  "+
                 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0   "+
                 " end) as factE,  "+
                 " sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0   "+
                 " end) as factASKUE,  "+

                 " count( distinct   dep.id) as allPred,  "+
                 " count( distinct case when rep.dt_inputplan is not null then dep.id end) as planProc,  "+
                 " count( distinct case when rep.dt_inputfact is not null then dep.id end) as factProc,  "+

                 " depGroup.id as idGroup,  "+
                 " depGroup.namenp as namenpGroup,  "+
                 "  depPer.id ,  "+
                 "  depPer.namenp ," +
                 " repF2.id as idF2   "+

               
                 "   from  "+
                 "   (select * from DB2ADMIN.REPORT  "+
                 "   where dt_begin>=:dayBegin and dt_begin<=:dayEnd  and form_id=1 and DEL_FL=0) as rep  "+
                
                 "    inner join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID  "+
                 "   inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and   "+
                 "                             dtbeginNSI<=:dayEnd and   "+
                 "                             (dt_end>=:dayBegin or dt_end is null  )  "+
                 "                             ) as dep on rep.DEPARTMENT_ID=dep.id  "+
                 
                                                                
                 "                             inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP="+idPower+" and   "+
                 "                             dtbeginNSI<=:dayEnd  and   "+
                 "                             (dt_end>=:dayBegin or dt_end is null  )) as depPer on depPer.id =dep.idVp   "+
                
                 "                             left join (select * from DB2ADMIN.REPORT "+
                 "                where dt_begin>=:dayBegin and dt_begin<=:dayEnd and form_id=2 and DEL_FL=0) as repF2 "+
                 "                             on repF2.department_id=depPer.id  "+
        
                                                                
                 "                             left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and   "+
                 "                             dtbeginNSI<=:dayEnd  and   "+
                 "                             (dt_end>=:dayBegin or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup   "+
                                                               
                 " inner join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   "+
               
                 "  left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id  "+
            
                 "                            group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id"+
                 "                            order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id  "
                 */).addScalar("plan_tyt").addScalar("fact_tyt").addScalar("planE").addScalar("factE").addScalar("factASKUE")
                .addScalar("allPred").addScalar("planProc").addScalar("factProc")
                .addScalar("idGroup")
                .addScalar("namenpGroup").addScalar("id").addScalar("namenp")
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(DetailsForm4.class));



        /*select   

         sum(case when details.resource_id in(1,2,3,4,5,7,17) then plan_col ELSE 0 
         end) as plan_tyt,
         sum(case when details.resource_id in(1,2,3,4,5,7,17) and details.dt_inputfact is not null then fact_col ELSE 0 
         end) as fact_tyt,
         sum(case when details.resource_id in(13) then plan_col ELSE 0 
         end) as planE,
         sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0 
         end) as factE,
         sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then fact_col ELSE 0 
         end) as factASKUE,

         count( distinct  depPer.id) as allPred,
         count( distinct case when rep.dt_inputplan is not null then dep.id end) as planProc,
         count( distinct case when rep.dt_inputfact is not null then dep.id end) as factProc,

         depGroup.id as idGroup,
         depGroup.namenp as namenpGroup,
         depPer.id ,
         depPer.namenp,
         repF2.id as idF2 


         -- rep.id as idRep,
         --  details.activity,details.addres,details.responsible,details.powerSource,details.addressOfObject,details.type,details.num,details.askue, 
         --  details.plan_col  ,details.fact_col,details.dt_inputFact,
         --  dep.id as idDep,dep.nameNP as depNameNP, 
         --  depPer.id as depPerIdDep,depPer.nameNP as depPerNameNP, 
         -- depGroup.id as depGroupIdDep,depGroup.nameNP as depGroupNameNP--, 
                
         -- res.id as idRes,res.name as resName, mas.value as mas 
         from
         (select * from DB2ADMIN.REPORT
         where dt_begin>='2014-06-01 00:00:00.000000' and dt_begin<='2014-06-30 00:00:00.000000' and form_id=1 and DEL_FL=0) as rep
                
         inner join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID
         inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and 
         dtbeginNSI<='2014-06-30 00:00:00.000000' and 
         (dt_end>='2014-06-01 00:00:00.000000' or dt_end is null  )
         ) as dep on rep.DEPARTMENT_ID=dep.id
                 
                                                                
         inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=95 and 
         dtbeginNSI<='2014-06-30 00:00:00.000000' and 
         (dt_end>='2014-06-01 00:00:00.000000' or dt_end is null  )) as depPer on depPer.id =dep.idVp 
                                                                
                                                                
                                                                
         left join (select * from DB2ADMIN.REPORT
         where dt_begin>='2014-06-01 00:00:00.000000' and dt_begin<='2014-06-30 00:00:00.000000' and form_id=2 and DEL_FL=0) as repF2
         on repF2.department_id=depPer.id 
                                                                
                                                                
         left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and 
         dtbeginNSI<='2014-06-30 00:00:00.000000' and 
         (dt_end>='2014-06-01 00:00:00.000000' or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup 
                                                                
         inner join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id 
               
         left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id
            
         group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id, repF2.id
         order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id, repF2.id  with ur*/
        List<DetailsForm4> pusList = query.list();
        return pusList;
    }

    /*Форма 2 дата на 1 месяц меньше*/
    public List<DetailsForm2> getListWithDetailsForm2_1(/*long idRes,int idNp,*/long idPower, Date dayBegin, Date dayEnd) {
        List<DetailsForm2> bd = new ArrayList();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm2_1(/*idRes,idNp,*/idPower, dayBegin, dayEnd, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<DetailsForm2> getListWithDetailsForm2_1(/*long idRes,int idNp, */long idPower, Date dayBegin, Date dayEnd, Session sess) {


        Query query = sess.createSQLQuery(
                " select    "
                + " sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_tut ELSE 0 "
                + " end) as plan_tyt, "
                + " sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_tut ELSE 0  "
                + " end) as fact_tyt, "
                + " sum(case when details.resource_id in(13) then plan_col ELSE 0  "
                + " end) as planE, "
                + " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0  "
                + " end) as factE, "
                + " sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0 "
                + " end) as factASKUE, "
                + " rep.id as idRep, "
                + " rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,rep.time_begin,"
                + "		 rep.time_finish, "
                + "  dep.id , dep.namenp "
                + "      from "
                + " 	 ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0  and  idVP=" + idPower + "  and  "
                + " 	 dtbeginNSI<=:dayEnd and  "
                + " 	 (dt_end>=:dayBegin or dt_end is null  ))as dep  "
                + "  left join (select * from DB2ADMIN.REPORT "
                + "  where dt_begin>=:dayBegin and dt_begin<=:dayEnd and form_id=1 and DEL_FL=0) as rep "
                + "  on rep.DEPARTMENT_ID=dep.id "
                + "  left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID "
                + "  left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id  "
                + " 	group by dep.namenp,  dep.id,rep.id ,rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,rep.time_begin,"
                + "		 rep.time_finish  "
                + " order by  dep.namenp,  dep.id,rep.id ,rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,rep.time_begin,"
                + "		 rep.time_finish  ")
                .addScalar("plan_tyt").addScalar("fact_tyt").addScalar("planE").addScalar("factE").addScalar("factASKUE")
                .addScalar("idRep")
                .addScalar("dt_inputfact").addScalar("dt_inputplan").addScalar("dt_begin")
                .addScalar("time_begin")
                .addScalar("time_finish")
                .addScalar("id").addScalar("namenp")
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(DetailsForm2.class));


        List<DetailsForm2> pusList = query.list();
        /*
         select   

         sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_tut ELSE 0 
         end) as plan_tyt,
         sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_tut ELSE 0 
         end) as fact_tyt,
         sum(case when details.resource_id in(13) then plan_col ELSE 0 
         end) as planE,
         sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0 
         end) as factE,
         sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0 
         end) as factASKUE,
         rep.id as idRep,
         rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,
         dep.id ,
         dep.namenp,
         repF2.id as idrepF2,
         detailsF2.id as iddetailsF2,
         detailsF2.fiochief,
         detailsF2.phonechief,
         detailsF2.rorschief
 
 
         from
				 
         ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=6 and 
         dtbeginNSI<='2014-06-30 00:00:00.000000' and 
         (dt_end>='2014-06-01 00:00:00.000000' or dt_end is null  ))as dep 
				 
         left join (select * from DB2ADMIN.REPORT
         where dt_begin>='2014-06-01 00:00:00.000000' and dt_begin<='2014-06-30 00:00:00.000000' and form_id=1 and DEL_FL=0) as rep
         on rep.DEPARTMENT_ID=dep.id
				
				
         left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID
                 
         left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id 
				 
         left join (select * from DB2ADMIN.REPORT
         where dt_begin>='2014-06-01 00:00:00.000000' and dt_begin<='2014-06-30 00:00:00.000000' and form_id=2 and DEL_FL=0) as repF2
         on repF2.department_id=dep.idVP 
				 
         inner join (select * from DB2ADMIN.REPORT_DETAILS_FORM2 where DEL_FL=0) as detailsF2
         on detailsF2.reportform2_id=repF2.id and detailsF2.department_id=dep.id
				
				 
               
         group by dep.namenp,  dep.id,rep.id ,rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,repF2.id,detailsF2.id,  detailsF2.fiochief,
         detailsF2.phonechief,
         detailsF2.rorschief 
 
         order by  dep.namenp,  dep.id,rep.id ,rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,repF2.id  ,detailsF2.id,  detailsF2.fiochief,
         detailsF2.phonechief,
         detailsF2.rorschief  */


        return pusList;
    }

    public DetailsForm2_dop getListWithDetailsForm2_dol_1(/*long idRes,int idNp,*/long idPower) {
        DetailsForm2_dop bd = new DetailsForm2_dop();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm2_dol_1(/*idRes,idNp,*/idPower, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected DetailsForm2_dop getListWithDetailsForm2_dol_1(/*long idRes,int idNp, */long idPower, Session sess) {


        Query query = sess.createSQLQuery(
                " select "
                + " userF2.surname||' '||userF2.name||' '||userF2.patronomicname as fio,userF2.phone, "
                + "  dep.id  as iddep,dep.namenp "
                + " from  (select * from DB2ADMIN.USER where DEL_FL=0  and id=" + idPower + " ) as userF2   "
                + " inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 "
                + "  ) as dep on userF2.DEPARTMENT=dep.id ")
                .addScalar("fio").addScalar("phone").addScalar("iddep").addScalar("namenp")
                .setResultTransformer(Transformers.aliasToBean(DetailsForm2_dop.class));


        DetailsForm2_dop pusList = (DetailsForm2_dop) query.uniqueResult();
        /*
         select userF2.surname||' '||userF2.name||' '||userF2.patronomicname as fio,
         dep.id as iddep,dep.namenp
         from 
         (select * from DB2ADMIN.USER where DEL_FL=0 and id=35) as userF2 
         inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and 
         dtbeginNSI<='2014-06-30 00:00:00.000000' and 
         (dt_end>='2014-06-01 00:00:00.000000' or dt_end is null  )
         ) as dep on userF2.DEPARTMENT=dep.id
         */


        return pusList;
    }

    public DetailsForm2_dop getListWithDetailsForm2_dol(/*long idRes,int idNp,*/int idPower) {
        DetailsForm2_dop bd = new DetailsForm2_dop();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm2_dol(/*idRes,idNp,*/idPower, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected DetailsForm2_dop getListWithDetailsForm2_dol(/*long idRes,int idNp, */int idPower, Session sess) {


        Query query = sess.createSQLQuery(
                " select repF2.id,repF2.dt_begin,repF2.dt_create, "
                + " userF2.surname||' '||userF2.name||' '||userF2.patronomicname as fio,userF2.phone, "
                + "  dep.id  as iddep,dep.namenp "
                + "  from (select * from DB2ADMIN.REPORT where "
                // + " dt_begin>=:dayBegin  and dt_begin<=:dayEnd   and "
                + " form_id=2 and DEL_FL=0 and id=" + idPower + "  "
                + "  ) as repF2 "
                + "  left join  (select * from DB2ADMIN.USER where DEL_FL=0) as userF2 on repF2.user_id=userF2.id  "
                + " inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0   "
                // + " and dtbeginNSI<=:dayEnd and  "
                // + "  (dt_end>=:dayBegin  or dt_end is null  ) "
                + "  ) as dep on repF2.DEPARTMENT_ID=dep.id "
                + " order by repF2.id ")
                .addScalar("id").addScalar("dt_begin").addScalar("dt_create").addScalar("fio")
                .addScalar("phone").addScalar("iddep").addScalar("namenp")
                .setResultTransformer(Transformers.aliasToBean(DetailsForm2_dop.class));


        DetailsForm2_dop pusList = (DetailsForm2_dop) query.uniqueResult();
        /*
         select repF2.id,repF2.dt_begin,repF2.dt_create,
         userF2.surname||' '||userF2.name||' '||userF2.patronomicname as fio,
         dep.id as iddep,dep.namenp
         from (select * from DB2ADMIN.REPORT where dt_begin>='2014-06-01 00:00:00.000000' and dt_begin<='2014-06-30 00:00:00.000000' and form_id=2 and DEL_FL=0 and id=3416
         ) as repF2
         left join  (select * from DB2ADMIN.USER where DEL_FL=0) as userF2 on repF2.user_id=userF2.id 
         inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and 
         dtbeginNSI<='2014-06-30 00:00:00.000000' and 
         (dt_end>='2014-06-01 00:00:00.000000' or dt_end is null  )
         ) as dep on repF2.DEPARTMENT_ID=dep.id

         order by repF2.id  */


        return pusList;
    }

    /*Форма 2 дата на 1 месяц меньше*/
    public List<DetailsForm2> getListWithDetailsForm2(/*long idRes,int idNp,*/int idPower, Date dayBegin, Date dayEnd) {
        List<DetailsForm2> bd = new ArrayList();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm2(/*idRes,idNp,*/idPower, dayBegin, dayEnd, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<DetailsForm2> getListWithDetailsForm2(/*long idRes,int idNp, */int idPower, Date dayBegin, Date dayEnd, Session sess) {


        Query query = sess.createSQLQuery(
                " select    "
                + " sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_col*koef ELSE 0 "
                + " end) as plan_tyt, "
                + " sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_col*koef ELSE 0  "
                + " end) as fact_tyt, "
                + " sum(case when details.resource_id in(13) then plan_col ELSE 0  "
                + " end) as planE, "
                + " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0  "
                + " end) as factE, "
                + " sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0 "
                + " end) as factASKUE, "
                + " rep.id as idRep, "
                + " rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,rep.time_begin,"
                + "		 rep.time_finish, "
                + "  dep.id , dep.namenp, "
                + " repF2.id as idrepF2, "
                + " detailsF2.id as iddetailsF2, "
                + " detailsF2.fiochief, "
                + " detailsF2.phonechief, "
                + " detailsF2.rorschief "
                + "      from "
                + " 	 ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and  "
                + " 	 dtbeginNSI<=:dayEnd and  "
                + " 	 (dt_end>=:dayBegin or dt_end is null  ))as dep  "
                + "  left join (select * from DB2ADMIN.REPORT "
                + "  where dt_begin>=:dayBegin and dt_begin<=:dayEnd and form_id=1 and DEL_FL=0) as rep "
                + "  on rep.DEPARTMENT_ID=dep.id "
                + "  left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID "
                + "  left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id  "
                + "  inner join (select * from DB2ADMIN.REPORT "
                + "   where dt_begin>=:dayBegin and dt_begin<=:dayEnd and form_id=2 and DEL_FL=0 and  id=" + idPower + " ) as repF2 "
                + " 	 on repF2.department_id=dep.idVP  "
                + " 	 left join (select * from DB2ADMIN.REPORT_DETAILS_FORM2 where DEL_FL=0) as detailsF2 "
                + " 	 on detailsF2.reportform2_id=repF2.id and detailsF2.department_id=dep.id "
                + " 	group by dep.namenp,  dep.id,rep.id ,rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,rep.time_begin,"
                + "		 rep.time_finish,repF2.id,detailsF2.id,  detailsF2.fiochief, "
                + "  detailsF2.phonechief, detailsF2.rorschief  "
                + " order by  dep.namenp,  dep.id,rep.id ,rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,rep.time_begin,"
                + "		 rep.time_finish,repF2.id  ,detailsF2.id,  detailsF2.fiochief, "
                + "  detailsF2.phonechief, detailsF2.rorschief   ")
                .addScalar("plan_tyt").addScalar("fact_tyt").addScalar("planE").addScalar("factE").addScalar("factASKUE")
                .addScalar("idRep")
                .addScalar("dt_inputfact").addScalar("dt_inputplan").addScalar("dt_begin")
                .addScalar("time_begin")
                .addScalar("time_finish")
                .addScalar("id").addScalar("namenp")
                .addScalar("idrepF2").addScalar("iddetailsF2")
                .addScalar("fiochief")
                .addScalar("phonechief")
                .addScalar("rorschief")
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(DetailsForm2.class));


        List<DetailsForm2> pusList = query.list();
        /*
         select   

         sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_tut ELSE 0 
         end) as plan_tyt,
         sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_tut ELSE 0 
         end) as fact_tyt,
         sum(case when details.resource_id in(13) then plan_col ELSE 0 
         end) as planE,
         sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0 
         end) as factE,
         sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0 
         end) as factASKUE,
         rep.id as idRep,
         rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,
         dep.id ,
         dep.namenp,
         repF2.id as idrepF2,
         detailsF2.id as iddetailsF2,
         detailsF2.fiochief,
         detailsF2.phonechief,
         detailsF2.rorschief
 
 
         from
				 
         ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=6 and 
         dtbeginNSI<='2014-06-30 00:00:00.000000' and 
         (dt_end>='2014-06-01 00:00:00.000000' or dt_end is null  ))as dep 
				 
         left join (select * from DB2ADMIN.REPORT
         where dt_begin>='2014-06-01 00:00:00.000000' and dt_begin<='2014-06-30 00:00:00.000000' and form_id=1 and DEL_FL=0) as rep
         on rep.DEPARTMENT_ID=dep.id
				
				
         left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID
                 
         left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id 
				 
         left join (select * from DB2ADMIN.REPORT
         where dt_begin>='2014-06-01 00:00:00.000000' and dt_begin<='2014-06-30 00:00:00.000000' and form_id=2 and DEL_FL=0) as repF2
         on repF2.department_id=dep.idVP 
				 
         inner join (select * from DB2ADMIN.REPORT_DETAILS_FORM2 where DEL_FL=0) as detailsF2
         on detailsF2.reportform2_id=repF2.id and detailsF2.department_id=dep.id
				
				 
               
         group by dep.namenp,  dep.id,rep.id ,rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,repF2.id,detailsF2.id,  detailsF2.fiochief,
         detailsF2.phonechief,
         detailsF2.rorschief 
 
         order by  dep.namenp,  dep.id,rep.id ,rep.dt_inputfact,rep.dt_inputplan,rep.dt_begin,repF2.id  ,detailsF2.id,  detailsF2.fiochief,
         detailsF2.phonechief,
         detailsF2.rorschief  */


        return pusList;
    }

    /*Форма 7*/
    public List<FormSevenNew> getListWithDetailsForm7(/*long idRes,int idNp,*/int idPower, Date dayBegin, Date dayEnd) {
        List<FormSevenNew> bd = new ArrayList();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();



            bd = getListWithDetailsForm7(/*idRes,idNp,*/idPower, dayBegin, dayEnd, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<FormSevenNew> getListWithDetailsForm7(/*long idRes,int idNp, */int idPower, Date dayBegin, Date dayEnd, Session sess) {


        Query query = sess.createSQLQuery(
                " select   "
                + " rep.id as idRep,"
                + " details.activity,details.addres,details.responsible,details.powerSource,details.addressOfObject,details.type,details.num,details.askue, "
                + " details.plan_col  ,details.fact_col,details.dt_inputFact,"
                + " dep.id as idDep,dep.nameNP as depNameNP, "
                + " depPer.id as depPerIdDep,depPer.nameNP as depPerNameNP, "
                + " res.id as idRes,res.name as resName, mas.value as mas "
                + " from "
                //+ " (select * from DB2ADMIN.REPORT where MONTH(dt_begin)=:mes and YEAR(dt_begin)=:year and DEL_FL=0) as rep "
                + " (select * from DB2ADMIN.REPORT "
                //  + " where MONTH(dt_begin)=:mes and YEAR(dt_begin)=:year  and DEL_FL=0) as rep "
                + " where dt_begin>=:dayBegin and dt_begin<=:dayEnd and DEL_FL=0 and form_id=1 ) as rep "
                + " inner join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID "
                + " inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0  and "
                + "				 dtbeginNSI<=:dayEnd and "
                + "				 (dt_end>=:dayBegin or dt_end is null  )"
                + ") as dep on rep.DEPARTMENT_ID=dep.id "
                + " inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=" + idPower + " and "
                + "				 dtbeginNSI<=:dayEnd and "
                + "				 (dt_end>=:dayBegin or dt_end is null  )"
                + " ) as depPer on depPer.id =dep.idVp "
                + " inner join (  select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id "
                + " left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id"
                // + " where details.plan_col>0 or details.fact_col>0 "
                + " where (details.plan_col>0 or details.fact_col>0) "
                + " order by res.id,dep.id"//,dep.id "       
                ).addScalar("idRep").addScalar("activity").addScalar("addres").addScalar("responsible").addScalar("powerSource").addScalar("addressOfObject")
                .addScalar("type").addScalar("num").addScalar("askue")
                .addScalar("plan_col").addScalar("fact_col").addScalar("dt_inputFact")
                .addScalar("idDep").addScalar("depNameNP").addScalar("depPerIdDep").addScalar("depPerNameNP")
                .addScalar("idRes").addScalar("resName").addScalar("mas")
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(FormSevenNew.class));

        List<FormSevenNew> pusList = query.list();



        return pusList;
    }

    /*Форма 4*/
    public List<DetailsForm4> getListWithDetailsFormFour(int idPower, Date dayBegin, Date dayEnd) {


        List<DetailsForm4> bd = new ArrayList();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsFormFour(idPower, dayBegin, dayEnd, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<DetailsForm4> getListWithDetailsFormFour(int idPower, Date dayBegin, Date dayEnd, Session sess) {


        Query query = sess.createSQLQuery(
                " select   "
                + " sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_col*koef ELSE 0   "
                + " end) as plan_tyt,  "
                + "  sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_col*koef ELSE 0   "
                + " end) as fact_tyt,  "
                + " sum(case when details.resource_id in(13) then plan_col ELSE 0   "
                + " end) as planE,  "
                + " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0   "
                + " end) as factE,  "
                + " sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0   "
                + " end) as factASKUE,  "
                + " count( distinct   dep.id) as allPred,  "
                + " count( distinct case when rep.dt_inputplan is not null then dep.id end) as planProc,  "
                + " count( distinct case when rep.dt_inputfact is not null then dep.id end) as factProc,  "
                + " depGroup.id as idGroup,  "
                + " depGroup.namenp as namenpGroup,  "
                + "  depPer.id ,  "
                + "  depPer.namenp ,"
                + " repF2.id as idF2   "
                + "   from  "
                + " 		 ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=" + idPower + " and   "
                + " 		 dtbeginNSI<=:dayEnd  and   "
                + " 		 (dt_end>=:dayBegin or dt_end is null  )) as depPer     "
                + "  inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and   "
                + " 		 dtbeginNSI<=:dayEnd and   "
                + "  		 (dt_end>=:dayBegin or dt_end is null  )  "
                + " 		 ) as dep on depPer.id =dep.idVp  "
                + "  left join (select * from DB2ADMIN.REPORT  "
                + "   where dt_begin>=:dayBegin and dt_begin<=:dayEnd  and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id "
                + "    left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID  "
                + " 		 left join (select * from DB2ADMIN.REPORT "
                + "                where dt_begin>=:dayBegin and dt_begin<=:dayEnd and form_id=2 and DEL_FL=0) as repF2 "
                + " 		 on repF2.department_id=depPer.id  "
                + " 		 left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and   "
                + " 		 dtbeginNSI<=:dayEnd  and   "
                + " 		 (dt_end>=:dayBegin or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup   "
                + " left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   "
                + "  left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id  "
                + " 		group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id"
                + " 		order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id   with ur" /* " select   "+

                 " sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_tut ELSE 0   "+
                 " end) as plan_tyt,  "+
                 "  sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_tut ELSE 0   "+
                 " end) as fact_tyt,  "+
                 " sum(case when details.resource_id in(13) then plan_col ELSE 0   "+
                 " end) as planE,  "+
                 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0   "+
                 " end) as factE,  "+
                 " sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0   "+
                 " end) as factASKUE,  "+

                 " count( distinct   dep.id) as allPred,  "+
                 " count( distinct case when rep.dt_inputplan is not null then dep.id end) as planProc,  "+
                 " count( distinct case when rep.dt_inputfact is not null then dep.id end) as factProc,  "+

                 " depGroup.id as idGroup,  "+
                 " depGroup.namenp as namenpGroup,  "+
                 "  depPer.id ,  "+
                 "  depPer.namenp ," +
                 " repF2.id as idF2   "+

               
                 "   from  "+
                 "   (select * from DB2ADMIN.REPORT  "+
                 "   where dt_begin>=:dayBegin and dt_begin<=:dayEnd  and form_id=1 and DEL_FL=0) as rep  "+
                
                 "    inner join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID  "+
                 "   inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and   "+
                 " 		 dtbeginNSI<=:dayEnd and   "+
                 "  		 (dt_end>=:dayBegin or dt_end is null  )  "+
                 " 		 ) as dep on rep.DEPARTMENT_ID=dep.id  "+
                 
				 
                 " 		 inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP="+idPower+" and   "+
                 " 		 dtbeginNSI<=:dayEnd  and   "+
                 " 		 (dt_end>=:dayBegin or dt_end is null  )) as depPer on depPer.id =dep.idVp   "+
                
                 " 		 left join (select * from DB2ADMIN.REPORT "+
                 "                where dt_begin>=:dayBegin and dt_begin<=:dayEnd and form_id=2 and DEL_FL=0) as repF2 "+
                 " 		 on repF2.department_id=depPer.id  "+
        
				 
                 " 		 left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and   "+
                 " 		 dtbeginNSI<=:dayEnd  and   "+
                 " 		 (dt_end>=:dayBegin or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup   "+
				 
                 " inner join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   "+
               
                 "  left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id  "+
            
                 " 		group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id"+
                 " 		order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id  "
                 */).addScalar("plan_tyt").addScalar("fact_tyt").addScalar("planE").addScalar("factE").addScalar("factASKUE")
                .addScalar("allPred").addScalar("planProc").addScalar("factProc")
                .addScalar("idGroup")
                .addScalar("namenpGroup").addScalar("id").addScalar("namenp").addScalar("idF2")
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(DetailsForm4.class));



        /* select   
                 sum(case when details.resource_id in(1,2,3,4,5,6,7,17) then plan_col*koef ELSE 0   
                 end) as plan_tyt,  
                  sum(case when details.resource_id in(1,2,3,4,5,6,7,17) and details.dt_inputfact is not null then fact_col*koef ELSE 0   
                 end) as fact_tyt,  
                 sum(case when details.resource_id in(13) then plan_col ELSE 0   
                end) as planE,  
                 sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0   
                 end) as factE,  
                 sum(case when details.resource_id in(13) and details.askue=1 and details.dt_inputfact is not null  then usedbefore-usedtoday ELSE 0   
                 end) as factASKUE,  
                 count( distinct   dep.id) as allPred,  
                 count( distinct case when rep.dt_inputplan is not null then dep.id end) as planProc,  
                 count( distinct case when rep.dt_inputfact is not null then dep.id end) as factProc,  
                 depGroup.id as idGroup,  
                 depGroup.namenp as namenpGroup,  
                  depPer.id ,  
                  depPer.namenp ,
                 repF2.id as idF2   
                   from  
                 		 ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=95 and   
                 		 dtbeginNSI<='2014-12-31 00:13:51.000000'  and   
                 		 (dt_end>='2014-12-01 00:13:51.000000' or dt_end is null  )) as depPer     
                  inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and   
                 		 dtbeginNSI<='2014-12-31 00:13:51.000000' and   
                  		 (dt_end>='2014-12-01 00:13:51.000000' or dt_end is null  )  
                 		 ) as dep on depPer.id =dep.idVp  
                  left join (select * from DB2ADMIN.REPORT  
                   where dt_begin>='2014-12-01 00:13:51.000000' and dt_begin<='2014-12-31 00:13:51.000000'  and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id 
                    left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID  
                 		 left join (select * from DB2ADMIN.REPORT 
                                where dt_begin>='2014-12-01 00:13:51.000000' and dt_begin<='2014-12-31 00:13:51.000000' and form_id=2 and DEL_FL=0) as repF2 
                 		 on repF2.department_id=depPer.id  
                 		 left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and   
                 		 dtbeginNSI<='2014-12-31 00:13:51.000000'  and   
                 		 (dt_end>='2014-12-01 00:13:51.000000' or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup   
                 left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   
                  left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id  
                 		group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id
                 		order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id   with ur*/
        List<DetailsForm4> pusList = query.list();
        return pusList;
    }

    public List<Report> getListWithDetailsForm2IdNpMonthALL(List<Integer> depList, int mes, int year) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.YEAR, year);
        c.add(Calendar.DATE, -1);
        List<Report> bd = null;

        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();



            bd = getListWithDetailsForm2IdNpMonthALL(depList, mes, year, c.getTime(), sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected List<Report> getListWithDetailsForm2IdNpMonthALL(List<Integer> depList, int mes, int year, Date day, Session sess) {


        Criterion criteria0 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(day.getTime())), Restrictions.ge("dt_end", new Timestamp(day.getTime())));
        Criterion criteria1 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(day.getTime())), Restrictions.isNull("dt_end"));
        Criterion criteria2 = Restrictions.or(criteria0, criteria1);
        Criterion criteria3 = Restrictions.and(Restrictions.in("idVp", depList), Restrictions.eq("deleted", false));
        Criterion criteriaDep = Restrictions.and(criteria2, criteria3);


        Criteria criteria;
        criteria = sess.createCriteria(Report.class)
                .add(Restrictions.eq("deleted", false))
                .add(Restrictions.sqlRestriction("MONTH({alias}.dt_begin)=?", mes, IntegerType.INSTANCE))
                .add(Restrictions.sqlRestriction("YEAR({alias}.dt_begin)=?", year, IntegerType.INSTANCE))
                .createAlias("department", "dep", JoinType.INNER_JOIN, criteriaDep)
                .createAlias("detailsForm2", "detailsForm2", JoinType.INNER_JOIN, Restrictions.eq("deleted", false))
                // .setFetchMode("detailsForm2", FetchMode.JOIN)
                .addOrder(Order.asc("id"));

        List<Report> rep = (List<Report>) criteria.list();
        for (Report r : rep) {

            for (ReportDetailsForm2 rd : r.getDetailsForm2()) {
            }
        }


        return rep;

        /* return (List<Report>) sess.createQuery(
         "select distinct r from Report r left join fetch r.department dep left join fetch r.detailsForm2 d"
         + " where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and   d.department.idVp in "+My+" "
         + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
         .setParameter("mes", mes)
         .setParameter("year", year)
         .list();*/

    }

    public List<Report> getListWithDetailsForm2IdNpMonthALLMyDate(String My, Date dayBegin, Date dayEnd) {


        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsForm2IdNpMonthALLMyDate(My, dayBegin, dayEnd, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected List<Report> getListWithDetailsForm2IdNpMonthALLMyDate(String My, Date dayBegin, Date dayEnd, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r left join fetch r.department dep left join fetch r.detailsForm2 d"
                + " where r.dt_begin>=:dayBegin and r.dt_begin<=:dayEnd"
                //  + " where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year "
                + " and   d.department.idVp in " + My + " "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                //.setLong("idVp", idVp)
                /*.setParameter("mes", mes)
                 .setParameter("year", year)*/
                .list();
    }

    /*  " select distinct r from Report r "
     + " left join fetch r.department dep "
     + " left join fetch r.details d "
     + " where r.dt_begin>=:dayBegin and r.dt_begin<=:dayEnd"
     + ""
     // + "where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year "
     + " and  dep.idVp =:idVp "
     + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
     .setLong("idVp", idVp)
     .setParameter("dayBegin", dayBegin)
     .setParameter("dayEnd", dayEnd)
     .list();*/
    public List<Report> getListWithDetailsForm2IdNpMonthALLMy(String My, int mes, int year) {

        List<Report> bd = null;

        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm2IdNpMonthALLMy(My, mes, year, sess);
            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsForm2IdNpMonthALLMy(String My, int mes, int year, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r left join fetch r.department dep left join fetch r.detailsForm2 d"
                + " where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and   d.department.idVp in " + My + " "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                //.setLong("idVp", idVp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();
    }

    public List<Report> getListWithDetailsIdNpMonthALL(List<Integer> depList, int mes, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.YEAR, year);
        c.add(Calendar.DATE, -1);



        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonthALL(depList, mes, year, c.getTime(), sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthALL(List<Integer> depList, int mes, int year, Date day, Session sess) {



        Criterion criteria0 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(day.getTime())), Restrictions.ge("dt_end", new Timestamp(day.getTime())));
        Criterion criteria1 = Restrictions.and(Restrictions.le("dtBeginNSI", new Timestamp(day.getTime())), Restrictions.isNull("dt_end"));
        Criterion criteria2 = Restrictions.or(criteria0, criteria1);
        Criterion criteria3 = Restrictions.and(Restrictions.in("idVp", depList), Restrictions.eq("deleted", false));
        Criterion criteriaDep = Restrictions.and(criteria2, criteria3);


        Criteria criteria;
        criteria = sess.createCriteria(Report.class)
                .add(Restrictions.eq("deleted", false))
                .add(Restrictions.sqlRestriction("MONTH({alias}.dt_begin)=?", mes, IntegerType.INSTANCE))
                .add(Restrictions.sqlRestriction("YEAR({alias}.dt_begin)=?", year, IntegerType.INSTANCE))
                .createAlias("department", "dep", JoinType.INNER_JOIN, criteriaDep)
                .createAlias("details", "details", JoinType.INNER_JOIN, Restrictions.eq("deleted", false))
                // .setFetchMode("details", FetchMode.JOIN)
                .addOrder(Order.asc("id"));

        List<Report> rep = (List<Report>) criteria.list();
        for (Report r : rep) {

            for (ReportDetails rd : r.getDetails()) {
            }
        }


        return rep;
    }

//период
    public List<Report> getListWithDetailsIdNpMonthALLMyDate(String My, Date dayBegin, Date dayEnd) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsIdNpMonthALLMyDate(My, dayBegin, dayEnd, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthALLMyDate(String My, Date dayBegin, Date dayEnd, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d "
                + " "
                + " where r.dt_begin>=:dayBegin and r.dt_begin<=:dayEnd"
                // + " where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year "
                + " and  dep.idVp in " + My + " "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                //.setLong("idVp", idVp)
                //.setParameter("mes", mes)
                //.setParameter("year", year)
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .list();

    }

    /*  " select distinct r from Report r "
     + " left join fetch r.department dep "
     + " left join fetch r.details d "
     + " where r.dt_begin>=:dayBegin and r.dt_begin<=:dayEnd"
     + ""
     // + "where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year "
     + " and  dep.idVp =:idVp "
     + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
     .setLong("idVp", idVp)
     .setParameter("dayBegin", dayBegin)
     .setParameter("dayEnd", dayEnd)
     .list();*/
    public List<Report> getListWithDetailsIdNpMonthALLMy(String My, int mes, int year) {
        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonthALLMy(My, mes, year, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthALLMy(String My, int mes, int year, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and  dep.idVp in " + My + " "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                //.setLong("idVp", idVp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();

    }

    public List<Report> getListWithDetailsIdNpMonthALLMyWithCosts(String My, int mes, int year, long rail) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonthALLMyWithCosts(My, mes, year, rail, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthALLMyWithCosts(String My, int mes, int year, long rail, Session sess) {
        List<Report> res = (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and  dep.idVp in " + My + " "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                //.setLong("idVp", idVp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();
        addCostsToListOfReports(res, rail, sess);
        return res;
    }

    public List<Report> getListWithDetailsIdNpMonthALLMyWithCostsDate(String My, Date dayBegin, Date dayEnd, long rail) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonthALLMyWithCostsDate(My, dayBegin, dayEnd, rail, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthALLMyWithCostsDate(String My, Date dayBegin, Date dayEnd, long rail, Session sess) {
        List<Report> res = (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d "
                + ""
                + ""
                // + " where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year "
                + " where r.dt_begin>=:dayBegin and r.dt_begin<=:dayEnd"
                + " and  dep.idVp in " + My + " "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                //.setLong("idVp", idVp)
                /* .setParameter("mes", mes)
                 .setParameter("year", year)*/
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .list();
        addCostsToListOfReports(res, rail, sess);
        return res;
    }

    /*  " select distinct r from Report r "
     + " left join fetch r.department dep "
     + " left join fetch r.details d "
     + " where r.dt_begin>=:dayBegin and r.dt_begin<=:dayEnd"
     + ""
     // + "where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year "
     + " and  dep.idVp =:idVp "
     + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
     .setLong("idVp", idVp)
     .setParameter("dayBegin", dayBegin)
     .setParameter("dayEnd", dayEnd)
     .list();*/
    public void deleteReport(Report rep) {
        if (rep.getId() != 0) {
            rep.setDeleted(true);
            ReportDetailsRepository repDetRepository = new ReportDetailsRepository();
            Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                    .openSession();

            Transaction t = null;
            try {
                t = sess.beginTransaction();


                if (rep.getDetails() != null) {
                    for (ReportDetails repDet : rep.getDetails()) {
                        repDetRepository.deleteReportDetails(repDet, sess);
                    }
                }

                save(rep, sess);

                t.commit();
            } catch (Exception e) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
                if (t != null) {
                    try {
                        t.rollback();
                    } catch (HibernateException he) {
                        Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                    }
                }
            } finally {
                try {
                    sess.close();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }


        }
    }

    public List<Report> getReportListByFormAndDep(long idForm, long idDep) {
        List<Report> dbList = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            //Извлекаем список из БД
            dbList = (ArrayList<Report>) sess.createQuery(
                    "select r from Report r where r.form.id=:idForm and r.deleted=0 and (r.department.id=:idDep or r.department.idVp =(select d.idNp from Department d where d.id=:idDep1))").setLong("idForm", idForm).setLong("idDep", idDep).setLong("idDep1", idDep).list();

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return dbList;
    }

    /*инфо о репорт с детеил*/
    public Report getWithDetails(long id) {

        Report bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getWithDetails(id, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected Report getWithDetails(long id, Session sess) {
        return (Report) sess.createQuery(
                "select distinct r from Report r left join fetch r.details d where r.id=:id and r.deleted=0 and (not exists elements(r.details) or d.deleted=0) ").setLong("id", id).uniqueResult();

    }

    public Report getWithDetailsAndCosts(long railway, long id) {

        Report bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getWithDetailsAndCosts(railway, id, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }


    /* старая версияprotected Report getWithDetailsAndCosts(long railway, long id, Session sess) {
        
     Report rep;
     rep=getWithDetails(id, sess);
     StringImprover stringImprover = new StringImprover();
     byte mon=stringImprover.getMonth(rep.getDt_begin());
     byte year=stringImprover.getYear(rep.getDt_begin());
     long costCount = (Long) sess.createQuery("SELECT count(c) FROM Costs as c WHERE c.railway=:rail AND c.month=:mon AND c.year=:ye  ")
     .setFloat("rail", railway)
     .setByte("mon", mon)
     .setByte("ye", year).uniqueResult();
     if (costCount >= 1) {
     rep = (Report) sess.createQuery(
     "select distinct r from Report r "
     + "left join fetch r.details d "
     + "left join fetch d.resource.cost "
     + "as c where r.id=:id and r.deleted=0 and (not exists elements(r.details) or d.deleted=0) "
     + "and c.common.railway=:rail AND c.common.month=:mon AND c.common.year=:ye")
     .setLong("rail", railway)
     .setByte("mon", mon)
     .setByte("ye", year)
     .setLong("id", id)
     .uniqueResult();
     }
     return rep;
     }*/
    protected Report getWithDetailsAndCosts(long railway, long id, Session sess) {
        sess.createQuery("from Measure").list();
        sess.createQuery("from Resource").list();
        Report rep;
        rep = getWithDetails(id, sess);
        if (rep != null) {
            StringImprover stringImprover = new StringImprover();
            byte mon = stringImprover.getMonth(rep.getDt_begin());
            byte year = stringImprover.getYear(rep.getDt_begin());
            Costs cost = getCost(railway, mon, year, sess);
            if (cost != null) {
                rep.setCosts(cost.getDetails());
            }
        }
        return rep;
    }

    protected Costs getCost(long railway, byte mon, byte year, Session sess) {
        return (Costs) sess.createQuery("SELECT c FROM Costs as c WHERE c.railway=:rail AND c.month=:mon AND c.year=:ye")
                .setFloat("rail", railway)
                .setByte("mon", mon)
                .setByte("ye", year).uniqueResult();
    }

    /*инфо о репорт*/
    public Report getWithDetailsRep(long id) {

        Report bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getWithDetailsRep(id, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected Report getWithDetailsRep(long id, Session sess) {
        return (Report) sess.createQuery(
                "from Report r  where r.id=:id and r.deleted=0").setLong("id", id).uniqueResult();

    }

    /*максимальная дата*/
    public Date getDateDetails(long form_id, long department_id) {


        Date bd = null;

        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getDateDetails(form_id, department_id, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }






        /* Session sess = SessionFactorySingleton.getSessionFactoryInstance()
         .openSession();
         Transaction t = sess.beginTransaction();

         Date bd = getDateDetails(form_id, department_id, sess);

         t.commit();
         sess.close();*/

        return bd;
    }

    public Date getDateDetails(long form_id, long department_id, Session sess) {
        //SELECT CONCAT(month(max(dt_begin)),"/","1","/",SUBSTRING(year(max(dt_begin)),3,4)) FROM `report` where del_fl=0 and form_id=1 and department_id=1;
        return (Date) sess.createSQLQuery("SELECT max(dt_begin) FROM report where del_fl=0 and form_id=:form_id and department_id=:department_id")
                .setLong("form_id", form_id)
                .setLong("department_id", department_id).uniqueResult();
    }

    public Report getWithDetailsForm2(long id) {

        Report bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getWithDetailsForm2(id, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected Report getWithDetailsForm2(long id, Session sess) {
        return (Report) sess.createQuery(
                "from Report r left join fetch r.detailsForm2 d where r.id=:id and (d.deleted=0 or d.deleted is null)  and r.deleted=0").setLong("id", id).uniqueResult();

    }


    /*инфо по принадлежности*/
    public List<Report> getListWithDetails(int idVp) {


        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetails(idVp, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetails(int idVp, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d where dep.idVp=:idVp and d.deleted=0 and dep.deleted=0 and r.deleted=0")
                .setLong("idVp", idVp).list();

    }

    public List<Report> getListWithDetailsIdNp(int idNp) {
        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNp(idNp, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsIdNp(int idNp, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d where dep.idNp=:idNp and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                .setLong("idNp", idNp).list();

    }

    public List<Report> getListWithDetailsIdNpMonth(int idNp, int mes, int year) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonth(idNp, mes, year, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonth(int idNp, int mes, int year, Session sess) {
        sess.createQuery("from Measure").list();
        sess.createQuery("from Resource").list();
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and   dep.idNp=:idNp "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                .setLong("idNp", idNp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();

    }

    public List<Report> getListWithDetailsIdNpMonthALL(int idVp, int mes, int year) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonthALL(idVp, mes, year, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthALL(int idVp, int mes, int year, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and  dep.idVp =:idVp "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                .setLong("idVp", idVp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();

    }

    public List<Report> getListWithDetailsIdNpMonthALLWithCosts(int idVp, int mes, int year, long rail) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonthALLWithCosts(idVp, mes, year, rail, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthALLWithCosts(int idVp, int mes, int year, long rail, Session sess) {

        List<Report> res = (List<Report>) sess.createQuery(
                "select distinct r from Report r "
                + "left join fetch r.department dep "
                + "left join fetch r.details d where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and  dep.idVp =:idVp "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                .setLong("idVp", idVp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();
        addCostsToListOfReports(res, rail, sess);

        return res;
    }

    public List<Report> getListWithDetailsIdNpMonthALLWithCostsDate(int idVp, Date dayBegin, Date dayEnd, long rail) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonthALLWithCostsDate(idVp, dayBegin, dayEnd, rail, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthALLWithCostsDate(int idVp, Date dayBegin, Date dayEnd, long rail, Session sess) {

        List<Report> res = (List<Report>) sess.createQuery(
                " select distinct r from Report r "
                + " left join fetch r.department dep "
                + " left join fetch r.details d "
                + " where r.dt_begin>=:dayBegin and r.dt_begin<=:dayEnd"
                + ""
                // + "where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year "
                + " and  dep.idVp =:idVp "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                .setLong("idVp", idVp)
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .list();
        addCostsToListOfReports(res, rail, sess);

        return res;
    }

    private void addCostsToListOfReports(List<Report> res, long rail, Session sess) {
        if (res != null && !res.isEmpty()) {

            StringImprover stringImprover = new StringImprover();
            byte mon1 = 0;
            byte ye1 = 0;
            Costs cost = new Costs();
            for (int i = 0; i < res.size(); i++) {
                Report report = res.get(i);
                byte mon = stringImprover.getMonth(report.getDt_begin());
                byte ye = stringImprover.getYear(report.getDt_begin());
                if (mon != mon1 || ye != ye1) {
                    mon1 = mon;
                    ye1 = ye;
                    cost = getCost(rail, mon, ye, sess);
                }
                if (cost != null) {
                    report.setCosts(cost.getDetails());
                }


            }
            /* 
             StringImprover stringImprover = new StringImprover();
             byte mon = stringImprover.getMonth(res.get(0).getDt_begin());
             byte ye = stringImprover.getYear(res.get(0).getDt_begin());
             Costs cost = getCost(rail, mon, ye, sess);
             if (cost != null) {
             res.get(0).setCosts(cost.getDetails());
             }
             for (int i = 1; i < res.size(); i++) {
             Report report = res.get(i);
             report.setCosts(res.get(0));
             }*/
        }
    }

    public List<Report> getListWithDetailsForm2IdNpMonthALL(int idVp, int mes, int year) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsForm2IdNpMonthALL(idVp, mes, year, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsForm2IdNpMonthALL(int idVp, int mes, int year, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r left join fetch r.department dep left join fetch r.detailsForm2 d"
                + " where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and   d.department.idVp=:idVp "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                .setLong("idVp", idVp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();

    }

    public List<FormSevenNew> getListWithDetailsIdNpMonthEl(/*long idRes,int idNp,*/int idPower, int mes, int year) {

        List<FormSevenNew> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsIdNpMonthEl(/*idRes,idNp,*/idPower, mes, year, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<FormSevenNew> getListWithDetailsIdNpMonthEl(/*long idRes,int idNp, */int idPower, int mes, int year, Session sess) {


        Query query = sess.createSQLQuery(
                " select   "
                + " rep.id as idRep,"
                + " details.activity,details.addres,details.responsible,details.powerSource,details.addressOfObject,details.type,details.num,details.askue, "
                + " details.plan_col  ,details.fact_col,details.dt_inputFact,"
                + " dep.id as idDep,dep.nameNP as depNameNP, "
                + " depPer.id as depPerIdDep,depPer.nameNP as depPerNameNP, "
                + " res.id as idRes,res.name as resName "
                + " from "
                + " (select * from DB2ADMIN.REPORT where MONTH(dt_begin)=:mes and YEAR(dt_begin)=:year and DEL_FL=0) as rep "
                + " inner join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID "
                + " inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0) as dep on rep.DEPARTMENT_ID=dep.id "
                + " inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=" + idPower + ") as depPer on depPer.id =dep.idVp "
                + " inner join (  select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id "
                + " where details.plan_col>0 or details.fact_col>0 "
                + " order by res.id,dep.id"//,dep.id "       
                ).addScalar("idRep").addScalar("activity").addScalar("addres").addScalar("responsible").addScalar("powerSource").addScalar("addressOfObject")
                .addScalar("type").addScalar("num").addScalar("askue")
                .addScalar("plan_col").addScalar("fact_col").addScalar("dt_inputFact")
                .addScalar("idDep").addScalar("depNameNP").addScalar("depPerIdDep").addScalar("depPerNameNP")
                .addScalar("idRes").addScalar("resName")
                .setParameter("mes", mes)
                .setParameter("year", year)
                .setResultTransformer(Transformers.aliasToBean(FormSevenNew.class));

        List<FormSevenNew> pusList = query.list();



        return pusList;
    }

    public List<FormGraf> getGraf(long idPower, Date dayBegin, Date dayEnd) {


        List<FormGraf> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getGraf(idPower, dayBegin, dayEnd, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<FormGraf> getGraf(long idPower, Date dayBegin, Date dayEnd, Session sess) {
        /*  select year1,month1, sum(sumb) as d, idDep, depNameNP,  
         depPerIdDep, depPerNameNP 
         from( select   
         year(rep.dt_begin)as year1,month(rep.dt_begin) as month1,   
         case  
         when res.id=2 then 
         sum(details.fact_col*25.7/24) else  
         case 
         when res.id=13 then 
         sum(details.fact_col*1.4/24)else 0 
         end 
         end as sumb, 
			  
         dep.id as idDep,dep.nameNP as depNameNP,  
         depPer.id as depPerIdDep,depPer.nameNP as depPerNameNP,  
         res.id as idRes,res.name as resName  
         from  
         (select * from DB2ADMIN.REPORT  
         where  dt_begin>='2014-05-30' and dt_begin<='2014-06-30' and DEL_FL=0) as rep  
                 
         inner join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 and dt_inputFact is not null) as details on rep.ID=details.REPORT_ID  
         inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0  and id=94) as dep on rep.DEPARTMENT_ID=dep.id  
         inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 ) as depPer on depPer.id =dep.idVp  
         inner join (  select * from DB2ADMIN.RESOURCE where  DEL_FL=0 and id in (2,13)) as res on details.RESOURCE_ID=res.id  
         where (details.plan_col>0 or details.fact_col>0)   
         group by rep.dt_begin,dep.id ,dep.nameNP ,  
         depPer.id ,depPer.nameNP ,  
         res.id ,res.name  
         )as a 
         group by  year1,month1,  idDep, depNameNP,  
         depPerIdDep, depPerNameNP 
         order by 1,2,6,4*/

        Query query = sess.createSQLQuery("  select year1,month1, sum(sumb) as d, idDep, depNameNP,  "
                + "    depPerIdDep, depPerNameNP "
                + " 		  from( select   "
                + "  year(rep.dt_begin)as year1,month(rep.dt_begin) as month1,   "
                + " 	   case  "
                + " 	   when res.id=2 then "
                + " 	   sum(details.fact_col*25.7/24) else  "
                + " 	   case "
                + " 	   when res.id=13 then "
                + " 	   sum(details.fact_col*1.4/24)else 0 "
                + " 	   end "
                + " 	   end as sumb, "
                + " 		   dep.id as idDep,dep.nameNP as depNameNP,  "
                + "  depPer.id as depPerIdDep,depPer.nameNP as depPerNameNP,  "
                + "   res.id as idRes,res.name as resName  "
                + "   from  "
                + "   (select * from DB2ADMIN.REPORT  "
                + "   where  dt_begin>=:dayBegin and dt_begin<=:dayEnd and DEL_FL=0) as rep  "
                + "    inner join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 and dt_inputFact is not null) as details on rep.ID=details.REPORT_ID  "
                + "   inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0  and id=" + idPower + ") as dep on rep.DEPARTMENT_ID=dep.id  "
                + "    inner join ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 ) as depPer on depPer.id =dep.idVp  "
                + "  inner join (  select * from DB2ADMIN.RESOURCE where  DEL_FL=0 and id in (2,13)) as res on details.RESOURCE_ID=res.id  "
                + "   where (details.plan_col>0 or details.fact_col>0)   "
                + " 		group by rep.dt_begin,dep.id ,dep.nameNP ,  "
                + "    depPer.id ,depPer.nameNP ,  "
                + "    res.id ,res.name  "
                + " 		 )as a "
                + "  group by  year1,month1,  idDep, depNameNP,  "
                + "     depPerIdDep, depPerNameNP "
                + " 		order by 1,2,7,5")
                .addScalar("year1").addScalar("month1").addScalar("d").addScalar("idDep").addScalar("depNameNP")
                .addScalar("depPerIdDep").addScalar("depPerNameNP")
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(FormGraf.class));

        List<FormGraf> pusList = query.list();



        return pusList;
    }

    public List<Report> getListWithDetailsIdNpMonthElMy(String My, int mes, int year) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsIdNpMonthElMy(My, mes, year, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }

    protected List<Report> getListWithDetailsIdNpMonthElMy(String My, int mes, int year, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r  left join fetch r.department dep "
                + " left join fetch r.details d LEFT JOIN FETCH d.resource res "
                + " where res.name='Электроэнергия' and MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and   dep.idVp in " + My + " "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0   order by r.id")
                //.setLong("idNp", idNp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();

    }

    /* public List<Report> getListWithDetailsForm2IdNpMonth(int idNp,int mes,int year){
     Session sess = SessionFactorySingleton.getSessionFactoryInstance()
     .openSession();
     Transaction t = sess.beginTransaction();
        
     List<Report> bd=getListWithDetailsForm2IdNpMonth(idNp,mes,year, sess);
            
     t.commit();
     sess.close();
        
     return bd;
     }
    
     protected List<Report> getListWithDetailsForm2IdNpMonth(int idNp,int mes,int year, Session sess){
     return  (List<Report>) sess.createQuery(
     "select distinct r from Report r "
     + "left join fetch r.department dep "
     + "left join fetch r.detailsForm2 d where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and   dep.idNp=:idNp "
     + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
     .setLong("idNp", idNp)
     .setParameter("mes", mes)
     .setParameter("year", year)
     .list();
        
     }
     */
    public List<Report> getListWithDetailsForm2IdNpMonth(int idNp, int mes, int year) {

        List<Report> bd = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();

            bd = getListWithDetailsForm2IdNpMonth(idNp, mes, year, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }

        return bd;
    }

    protected List<Report> getListWithDetailsForm2IdNpMonth(int idNp, int mes, int year, Session sess) {
        return (List<Report>) sess.createQuery(
                "select distinct r from Report r left join fetch r.department dep left join fetch r.detailsForm2 d where MONTH(r.dt_begin)=:mes and YEAR(r.dt_begin)=:year and   d.department.idNp=:idNp "
                + " and d.deleted=0 and dep.deleted=0 and r.deleted=0 order by r.id")
                .setLong("idNp", idNp)
                .setParameter("mes", mes)
                .setParameter("year", year)
                .list();

    }

    /*
     *  public void deleteReport(Report rep) {
     if (rep.getId() != 0) {
     rep.setDeleted(true);
     Session sess = SessionFactorySingleton.getSessionFactoryInstance()
     .openSession();
     Transaction t = sess.beginTransaction();

     save(rep, sess);

     t.commit();
     sess.close();
            
     }
     }
     * protected Report getWithDetails(long id, Session sess){
     return  (Report) sess.createQuery(
     "from Report r left join fetch r.details d where r.id=:id and d.deleted=0 and  r.deleted=0").setLong("id", id).uniqueResult();
        
     }
     */
    /**
     * Извлекает из БД все отчеты данного подразделения по формам 1 и 2
     *
     * @param idDep id Подразделения
     * @param sess открытая сессия хибернейт
     * @return
     */
    public List<ShortInfoReport> getShortInfoReportListByDep(long idDep, Session sess) {
        List<ShortInfoReport> dbList = (ArrayList<ShortInfoReport>) sess.createQuery(
                "select new rzd.vivc.aszero.beans.pagebean.pageinfo.ShortInfoReport(r.id, u.surname||' '||u.name||' '||u.patronomicname, r.dt_begin, r.form.id) from Report r JOIN r.usr u where r.deleted=0 and "
                + " r.department.id=:idDep")
                .setLong("idDep", idDep).list();

        return dbList;
    }

    public List<Report> getReportListByDep(long idDep) {
        List<Report> dbList = null;
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            //Извлекаем список из БД
            dbList = (ArrayList<Report>) sess.createQuery(
                    "select r from Report r where r.deleted=0 and "
                    + " r.department.id=:idDep")
                    .setLong("idDep", idDep).list();

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }





        return dbList;
    }

    public static void main(String[] args) {
        /*   FormRepository formRep=new FormRepository();
         Form form=new Form();
         form.setSerialNumForm(3);
         form.setShortNameForm("Ф3");
         form.setFullNameForm("Форма 3");
         formRep.save(form);*/
        /*  Calendar DT_NOW = Calendar.getInstance();
         DT_NOW.set(2014, 5, 1, 0, 0, 0);
         //  DT_NOW.set(Calendar.DATE, 1);
         Calendar DT_NOW1 = Calendar.getInstance();
         DT_NOW1.set(2014, 5, 30, 0, 0, 0);
         //   DT_NOW1.set(Calendar.DATE, 1);
         System.out.println(DT_NOW);
         System.out.println(DT_NOW1);
         System.out.println((new ReportRepository()).getWithDetailsForm2(5052));
         // List<DetailsForm2> f = (new ReportRepository()).getListWithDetailsForm2_1(6, DT_NOW.getTime(), DT_NOW1.getTime());
         //System.out.println(f.size());
         /* for (DetailsForm2 detailsForm4 : f) {
         System.out.println(detailsForm4.getNamenp() + " " + detailsForm4.getId());
         }*/
        /* ReportRepository rep = new ReportRepository();
         List<FormGraf> fd=(new ReportRepository()).getGraf(94, DT_NOW.getTime(), DT_NOW1.getTime());
         System.out.println("--"+ fd.size());
         for (FormGraf formGraf : fd) {
         System.out.println("--"+ formGraf.getIdDep());
         }
         //System.out.println(rep.getWithDetailsForm2(6).getDetailsForm2());
         // repRep.getListWithDetailsIdNpMonth(((Department) object).getIdNp(), mes.getMonth() + 1)
         */
        /*   Iterator itr =  rep.getListWithDetailsIdNpMonth(7378,03,2014).iterator();
         while (itr.hasNext()) {
         Object object = itr.next();//
         if (object instanceof Report) {
         System.out.println(((Report)object).getId()+"======"+((Report)object).getDepartment().getNameNp()+"========="+((Report)object).getDetails());
         }}*/
        /*   List<Integer> deplist = new ArrayList<Integer>();
         deplist.add(1);
         deplist.add(12);
         deplist.add(13);
         deplist.add(14);
         deplist.add(15);
         List<Report> rr = rep.getListWithDetailsIdNpMonthALL(deplist, 1, 2014);
         for (Report r : rr) {
         System.out.println(r.getDt_begin());
         System.out.println(r.getDepartment().getId());
         System.out.println(r.getDepartment().getNameNp());
         for (ReportDetails rd : r.getDetails()) {
         System.out.println(rd.getId());
         }
         }*/
        //getGraf(int idPower,Date dayBegin, Date dayEnd);
        // rep.getWithDetails(60);
        ReportRepository reportRepository = new ReportRepository();
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        t = sess.beginTransaction();
        List<ShortInfoReport> shortInfoReportListByDep = reportRepository.getShortInfoReportListByDep(3l, sess);

        t.commit();
        sess.close();

        for (ShortInfoReport shortInfoReport : shortInfoReportListByDep) {
            System.out.println(shortInfoReport);
        }
    }
    
    /*Форма 5 период*/
    public List<DetailsForm5> getListWithDetailsForm5Per(long idPower, Date dayBegin, Date dayEnd) {
        List<DetailsForm5> bd = new ArrayList();
        byte m=(new StringImprover()).getMonth(dayBegin);
        byte y=(new StringImprover()).getYear(dayBegin);
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm5Per(idPower, dayBegin, dayEnd,m,y, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }
    
    
    
     protected List<DetailsForm5> getListWithDetailsForm5Per(/*long idRes,int idNp, */long idPower, Date dayBegin, Date dayEnd,byte m,byte y, Session sess) {


        Query query = sess.createSQLQuery(
            " select   "+  
 " sum(case when details.resource_id in(13) then plan_col ELSE 0    end) as planE,   "+  
 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factE, "+  
 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factEM, "+  
  
 " sum(case when details.resource_id in(1) then plan_col ELSE 0    end) as planG,   "+  
 " sum(case when details.resource_id in(1) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factG, "+  
 " sum(case when details.resource_id in(1) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factGM, "+  
 " sum(case when details.resource_id in(17) then plan_col ELSE 0    end) as planD,   "+  
 " sum(case when details.resource_id in(17) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD, "+  
 " sum(case when details.resource_id in(17) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDM, "+  
 " sum(case when details.resource_id in(2) then plan_col ELSE 0    end) as planB,   "+  
 " sum(case when details.resource_id in(2) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factB, "+  
 " sum(case when details.resource_id in(2) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factBM, "+  
 " sum(case when details.resource_id in(3) then plan_col ELSE 0    end) as planM,  "+   
 " sum(case when details.resource_id in(3) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factM, "+  
 " sum(case when details.resource_id in(3) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMM, "+  
 " sum(case when details.resource_id in(4,5) then plan_col ELSE 0    end) as planY,   "+  
 " sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY, "+  
 " sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factYM, "+  
 " sum(case when details.resource_id in(6) then plan_col ELSE 0    end) as planMA,   "+  
 " sum(case when details.resource_id in(6) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factMA, "+  
 " sum(case when details.resource_id in(6) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMAM, "+  
 " sum(case when details.resource_id in(7) then plan_col ELSE 0    end) as planDA,   "+  
 " sum(case when details.resource_id in(7) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factDA, "+  
 " sum(case when details.resource_id in(7) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDAM, "+  
 " sum(case when details.resource_id in(12) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factS, "+  
 " sum(case when details.resource_id in(12) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factSM, "+  
 " sum(case when details.resource_id in(16) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO,   "+  
 " sum(case when details.resource_id in(15) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO2,   "+  
 " sum(case when details.resource_id in(14) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY0,   "+  
 " sum(case when details.resource_id in(11) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY2,   "+  
       
"                 depGroup.id as idGroup,   "+  
"                  depGroup.namenp as namenpGroup,   "+    
"                             depPer.id ,     "+  
"                             depPer.namenp   "+  

                               
                                 
"                  from                                  ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=" + idPower + "  and                    dtbeginNSI<=:dayEnd  and    "+  
"                             (dt_end>=:dayBegin or dt_end is null  )) as depPer      "+  
                               
"                               inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and                      dtbeginNSI<=:dayEnd and     "+  
"                                                             (dt_end>=:dayBegin or dt_end is null  )                              ) as dep on depPer.id =dep.idVp    "+  
                                                                
"                                left join (select * from DB2ADMIN.REPORT     where dt_begin>=:dayBegin  and dt_begin<=:dayEnd  "+  
"                                 and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id     "+  
                                               
"                                 left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID "+                    
                                               
                               
"                                             left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and                            dtbeginNSI<=:dayEnd  and    "+  
        "                     (dt_end>=:dayBegin  or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup  "+  
"                                                 left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   "+  
                                                               
"                                                             left join (select resource_id,cost from "+  
" (select * from DB2ADMIN.COST where  DEL_FL=0 and railway=" + idPower + "  and month="+m+" and year="+y+") as cost "+  
" left join  DB2ADMIN.COSTS_DETAILS  as cost_details on cost_details.common_info_id=cost.id ) as cost on cost.resource_id=res.id "+  
"                                                             left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id    "+                   
"                                                             group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id                             "+  
"                                                             order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id   with ur "
                
                
                /*  select    

  sum(case when details.resource_id in(13) then plan_col ELSE 0    end) as planE,  
  sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factE,
  sum(case when details.resource_id in(13) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factEM,
  
  sum(case when details.resource_id in(1) then plan_col ELSE 0    end) as planG,  
  sum(case when details.resource_id in(1) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factG,
  sum(case when details.resource_id in(1) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factGM,
  sum(case when details.resource_id in(17) then plan_col ELSE 0    end) as planD,  
  sum(case when details.resource_id in(17) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD,
  sum(case when details.resource_id in(17) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDM,
  sum(case when details.resource_id in(2) then plan_col ELSE 0    end) as planB,  
  sum(case when details.resource_id in(2) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factB,
  sum(case when details.resource_id in(2) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factBM,
  sum(case when details.resource_id in(3) then plan_col ELSE 0    end) as planM,  
  sum(case when details.resource_id in(3) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factM,
  sum(case when details.resource_id in(3) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMM,
  sum(case when details.resource_id in(4,5) then plan_col ELSE 0    end) as planY,  
  sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY,
  sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factYM,
  sum(case when details.resource_id in(6) then plan_col ELSE 0    end) as planMA,  
  sum(case when details.resource_id in(6) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factMA,
  sum(case when details.resource_id in(6) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMAM,
  sum(case when details.resource_id in(7) then plan_col ELSE 0    end) as planD,  
  sum(case when details.resource_id in(7) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD,
  sum(case when details.resource_id in(7) and details.dt_inputfact is not null then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as factDM,
  sum(case when details.resource_id in(12) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factS,
  
  sum(case when details.resource_id in(12) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as factSM,
   
  sum(case when details.resource_id in(16) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO,  
  sum(case when details.resource_id in(15) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO2,  
  sum(case when details.resource_id in(14) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY0,  
  sum(case when details.resource_id in(11) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY2,  
       
  
                
                   depGroup.id as idGroup,  
                    depGroup.namenp as namenpGroup,    
                               depPer.id ,    
                               depPer.namenp , 
                               repF2.id as idF2   
                               
                                 
                    from                                  ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=95 and                   dtbeginNSI<='2014-02-01 00:00:00.000000'  and   
                                (dt_end>='2014-01-01 00:00:00.000000' or dt_end is null  )) as depPer     
                               
                                 inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and                      dtbeginNSI<='2014-02-01 00:00:00.000000' and    
                                                               (dt_end>='2014-01-01 00:00:00.000000' or dt_end is null  )                         ) as dep on depPer.id =dep.idVp   
                                                                
                                  left join (select * from DB2ADMIN.REPORT     where dt_begin>='2014-01-01 00:00:00.000000'  and dt_begin<='2014-02-01 00:00:00.000000' 
                                   and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id    
                                               
                                   left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID          
                                               
                                               
                                               
                                               left join (select * from DB2ADMIN.REPORT                 where dt_begin>='2014-01-01 00:00:00.000000'  and dt_begin<='2014-02-01 00:00:00.000000' and form_id=2 and DEL_FL=0) as repF2                   
                                                on repF2.department_id=depPer.id                    
                                                left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and                            dtbeginNSI<='2014-02-01 00:00:00.000000'  and                                (dt_end>='2014-01-01 00:00:00.000000'  or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup 
                                                   left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id  
                                                               
                                                               left join (select resource_id,cost from
(select * from DB2ADMIN.COST where  DEL_FL=0 and railway=95 and month=1 and year=14) as cost
left join  DB2ADMIN.COSTS_DETAILS  as cost_details on cost_details.common_info_id=cost.id ) as cost on cost.resource_id=res.id
                                                               
                                               
                                                               
                                                                  
                                                               left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id                           
                                                               group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id                       
                                                               order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id  

*/
)
                .addScalar("planE").addScalar("factE").addScalar("factEM")
                .addScalar("planG").addScalar("factG").addScalar("factGM")
                .addScalar("planD").addScalar("factD").addScalar("factDM")
                .addScalar("planB").addScalar("factB").addScalar("factBM")
                .addScalar("planM").addScalar("factM").addScalar("factMM")
                .addScalar("planY").addScalar("factY").addScalar("factYM")
                .addScalar("planMA").addScalar("factMA").addScalar("factMAM")
                .addScalar("planDA").addScalar("factDA").addScalar("factDAM")
                .addScalar("factS").addScalar("factSM").addScalar("factO")
                .addScalar("factO2").addScalar("factY0").addScalar("factY2")
                
                .addScalar("idGroup").addScalar("namenpGroup").addScalar("id").addScalar("namenp")//.addScalar("idF2")
                
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(DetailsForm5.class));


        List<DetailsForm5> pusList = query.list();
        return pusList;
    }
     /*Форма 5*/
    public List<DetailsForm5> getListWithDetailsForm5(long idPower, Date dayBegin, Date dayEnd) {
        List<DetailsForm5> bd = new ArrayList();
        byte m=(new StringImprover()).getMonth(dayBegin);
        byte y=(new StringImprover()).getYear(dayBegin);
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm5(idPower, dayBegin, dayEnd,m,y, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
               try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }
    
    
    
     protected List<DetailsForm5> getListWithDetailsForm5(/*long idRes,int idNp, */long idPower, Date dayBegin, Date dayEnd,byte m,byte y, Session sess) {


        Query query = sess.createSQLQuery(
            " select   "+  
 " sum(case when details.resource_id in(13) then plan_col ELSE 0    end) as planE,   "+  
 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factE, "+  
 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factEM, "+  
  
 " sum(case when details.resource_id in(1) then plan_col ELSE 0    end) as planG,   "+  
 " sum(case when details.resource_id in(1) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factG, "+  
 " sum(case when details.resource_id in(1) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factGM, "+  
 " sum(case when details.resource_id in(17) then plan_col ELSE 0    end) as planD,   "+  
 " sum(case when details.resource_id in(17) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD, "+  
 " sum(case when details.resource_id in(17) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDM, "+  
 " sum(case when details.resource_id in(2) then plan_col ELSE 0    end) as planB,   "+  
 " sum(case when details.resource_id in(2) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factB, "+  
 " sum(case when details.resource_id in(2) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factBM, "+  
 " sum(case when details.resource_id in(3) then plan_col ELSE 0    end) as planM,  "+   
 " sum(case when details.resource_id in(3) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factM, "+  
 " sum(case when details.resource_id in(3) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMM, "+  
 " sum(case when details.resource_id in(4,5) then plan_col ELSE 0    end) as planY,   "+  
 " sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY, "+  
 " sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factYM, "+  
 " sum(case when details.resource_id in(6) then plan_col ELSE 0    end) as planMA,   "+  
 " sum(case when details.resource_id in(6) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factMA, "+  
 " sum(case when details.resource_id in(6) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMAM, "+  
 " sum(case when details.resource_id in(7) then plan_col ELSE 0    end) as planDA,   "+  
 " sum(case when details.resource_id in(7) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factDA, "+  
 " sum(case when details.resource_id in(7) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDAM, "+  
 " sum(case when details.resource_id in(12) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factS, "+  
 " sum(case when details.resource_id in(12) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factSM, "+  
 " sum(case when details.resource_id in(16) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO,   "+  
 " sum(case when details.resource_id in(15) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO2,   "+  
 " sum(case when details.resource_id in(14) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY0,   "+  
 " sum(case when details.resource_id in(11) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY2,   "+  
       
"                 depGroup.id as idGroup,   "+  
"                  depGroup.namenp as namenpGroup,   "+    
"                             depPer.id ,     "+  
"                             depPer.namenp ,  "+  
"                             repF2.id as idF2    "+  
                               
                                 
"                  from                                  ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=" + idPower + "  and                    dtbeginNSI<=:dayEnd  and    "+  
"                             (dt_end>=:dayBegin or dt_end is null  )) as depPer      "+  
                               
"                               inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and                      dtbeginNSI<=:dayEnd and     "+  
"                                                             (dt_end>=:dayBegin or dt_end is null  )                              ) as dep on depPer.id =dep.idVp    "+  
                                                                
"                                left join (select * from DB2ADMIN.REPORT     where dt_begin>=:dayBegin  and dt_begin<=:dayEnd  "+  
"                                 and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id     "+  
                                               
"                                 left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID "+                    
                                               
"                                             left join (select * from DB2ADMIN.REPORT                 where dt_begin>=:dayBegin  and dt_begin<=:dayEnd and form_id=2 and DEL_FL=0) as repF2   "+                             
"                                             on repF2.department_id=depPer.id    "+                           
"                                             left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and                            dtbeginNSI<=:dayEnd  and    "+  
        "                     (dt_end>=:dayBegin  or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup  "+  
"                                                 left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   "+  
                                                               
"                                                             left join (select resource_id,cost from "+  
" (select * from DB2ADMIN.COST where  DEL_FL=0 and railway=" + idPower + "  and month="+m+" and year="+y+") as cost "+  
" left join  DB2ADMIN.COSTS_DETAILS  as cost_details on cost_details.common_info_id=cost.id ) as cost on cost.resource_id=res.id "+  
"                                                             left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id    "+                   
"                                                             group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id                        "+  
"                                                             order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id  with ur  "
                
                
                /*  select    

  sum(case when details.resource_id in(13) then plan_col ELSE 0    end) as planE,  
  sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factE,
  sum(case when details.resource_id in(13) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factEM,
  
  sum(case when details.resource_id in(1) then plan_col ELSE 0    end) as planG,  
  sum(case when details.resource_id in(1) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factG,
  sum(case when details.resource_id in(1) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factGM,
  sum(case when details.resource_id in(17) then plan_col ELSE 0    end) as planD,  
  sum(case when details.resource_id in(17) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD,
  sum(case when details.resource_id in(17) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDM,
  sum(case when details.resource_id in(2) then plan_col ELSE 0    end) as planB,  
  sum(case when details.resource_id in(2) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factB,
  sum(case when details.resource_id in(2) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factBM,
  sum(case when details.resource_id in(3) then plan_col ELSE 0    end) as planM,  
  sum(case when details.resource_id in(3) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factM,
  sum(case when details.resource_id in(3) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMM,
  sum(case when details.resource_id in(4,5) then plan_col ELSE 0    end) as planY,  
  sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY,
  sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factYM,
  sum(case when details.resource_id in(6) then plan_col ELSE 0    end) as planMA,  
  sum(case when details.resource_id in(6) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factMA,
  sum(case when details.resource_id in(6) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMAM,
  sum(case when details.resource_id in(7) then plan_col ELSE 0    end) as planD,  
  sum(case when details.resource_id in(7) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD,
  sum(case when details.resource_id in(7) and details.dt_inputfact is not null then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as factDM,
  sum(case when details.resource_id in(12) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factS,
  
  sum(case when details.resource_id in(12) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as factSM,
   
  sum(case when details.resource_id in(16) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO,  
  sum(case when details.resource_id in(15) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO2,  
  sum(case when details.resource_id in(14) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY0,  
  sum(case when details.resource_id in(11) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY2,  
       
  
                
                   depGroup.id as idGroup,  
                    depGroup.namenp as namenpGroup,    
                               depPer.id ,    
                               depPer.namenp , 
                               repF2.id as idF2   
                               
                                 
                    from                                  ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=95 and                   dtbeginNSI<='2014-02-01 00:00:00.000000'  and   
                                (dt_end>='2014-01-01 00:00:00.000000' or dt_end is null  )) as depPer     
                               
                                 inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and                      dtbeginNSI<='2014-02-01 00:00:00.000000' and    
                                                               (dt_end>='2014-01-01 00:00:00.000000' or dt_end is null  )                         ) as dep on depPer.id =dep.idVp   
                                                                
                                  left join (select * from DB2ADMIN.REPORT     where dt_begin>='2014-01-01 00:00:00.000000'  and dt_begin<='2014-02-01 00:00:00.000000' 
                                   and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id    
                                               
                                   left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID          
                                               
                                               
                                               
                                               left join (select * from DB2ADMIN.REPORT                 where dt_begin>='2014-01-01 00:00:00.000000'  and dt_begin<='2014-02-01 00:00:00.000000' and form_id=2 and DEL_FL=0) as repF2                   
                                                on repF2.department_id=depPer.id                    
                                                left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and                            dtbeginNSI<='2014-02-01 00:00:00.000000'  and                                (dt_end>='2014-01-01 00:00:00.000000'  or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup 
                                                   left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id  
                                                               
                                                               left join (select resource_id,cost from
(select * from DB2ADMIN.COST where  DEL_FL=0 and railway=95 and month=1 and year=14) as cost
left join  DB2ADMIN.COSTS_DETAILS  as cost_details on cost_details.common_info_id=cost.id ) as cost on cost.resource_id=res.id
                                                               
                                               
                                                               
                                                                  
                                                               left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id                           
                                                               group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id                       
                                                               order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id  

*/
)
                .addScalar("planE").addScalar("factE").addScalar("factEM")
                .addScalar("planG").addScalar("factG").addScalar("factGM")
                .addScalar("planD").addScalar("factD").addScalar("factDM")
                .addScalar("planB").addScalar("factB").addScalar("factBM")
                .addScalar("planM").addScalar("factM").addScalar("factMM")
                .addScalar("planY").addScalar("factY").addScalar("factYM")
                .addScalar("planMA").addScalar("factMA").addScalar("factMAM")
                .addScalar("planDA").addScalar("factDA").addScalar("factDAM")
                .addScalar("factS").addScalar("factSM").addScalar("factO")
                .addScalar("factO2").addScalar("factY0").addScalar("factY2")
                
                .addScalar("idGroup").addScalar("namenpGroup").addScalar("id").addScalar("namenp").addScalar("idF2")
                
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(DetailsForm5.class));


        List<DetailsForm5> pusList = query.list();
        return pusList;
    }

     
     public List<DetailsForm5> getListWithDetailsForm3(long  idPowerUser,long idPower, Date dayBegin, Date dayEnd) {
        List<DetailsForm5> bd = new ArrayList();
        byte m=(new StringImprover()).getMonth(dayBegin);
        byte y=(new StringImprover()).getYear(dayBegin);
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm3(idPowerUser,idPower, dayBegin, dayEnd,m,y, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
               try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }
    
    
    
     protected List<DetailsForm5> getListWithDetailsForm3(/*long idRes,int idNp, */long idPowerUser,long idPower, Date dayBegin, Date dayEnd,byte m,byte y, Session sess) {


        Query query = sess.createSQLQuery(
            " select   "+  
 " sum(case when details.resource_id in(13) then plan_col ELSE 0    end) as planE,   "+  
 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factE, "+  
 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factEM, "+  
  
 " sum(case when details.resource_id in(1) then plan_col ELSE 0    end) as planG,   "+  
 " sum(case when details.resource_id in(1) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factG, "+  
 " sum(case when details.resource_id in(1) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factGM, "+  
 " sum(case when details.resource_id in(17) then plan_col ELSE 0    end) as planD,   "+  
 " sum(case when details.resource_id in(17) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD, "+  
 " sum(case when details.resource_id in(17) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDM, "+  
 " sum(case when details.resource_id in(2) then plan_col ELSE 0    end) as planB,   "+  
 " sum(case when details.resource_id in(2) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factB, "+  
 " sum(case when details.resource_id in(2) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factBM, "+  
 " sum(case when details.resource_id in(3) then plan_col ELSE 0    end) as planM,  "+   
 " sum(case when details.resource_id in(3) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factM, "+  
 " sum(case when details.resource_id in(3) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMM, "+  
 " sum(case when details.resource_id in(4,5) then plan_col ELSE 0    end) as planY,   "+  
 " sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY, "+  
 " sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factYM, "+  
 " sum(case when details.resource_id in(6) then plan_col ELSE 0    end) as planMA,   "+  
 " sum(case when details.resource_id in(6) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factMA, "+  
 " sum(case when details.resource_id in(6) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMAM, "+  
 " sum(case when details.resource_id in(7) then plan_col ELSE 0    end) as planDA,   "+  
 " sum(case when details.resource_id in(7) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factDA, "+  
 " sum(case when details.resource_id in(7) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDAM, "+  
 " sum(case when details.resource_id in(12) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factS, "+  
 " sum(case when details.resource_id in(12) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factSM, "+  
 " sum(case when details.resource_id in(16) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO,   "+  
 " sum(case when details.resource_id in(15) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO2,   "+  
 " sum(case when details.resource_id in(14) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY0,   "+  
 " sum(case when details.resource_id in(11) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY2   "+  
       
//"                 depGroup.id as idGroup,   "+  
//"                  depGroup.namenp as namenpGroup,   "+    
//"                             depPer.id ,     "+  
//"                             depPer.namenp ,  "+  
//"                             repF2.id as idF2    "+  
                               
                                 
"                  from                                  ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=" + idPowerUser + "  and                    dtbeginNSI<=:dayEnd  and    "+  
"                             (dt_end>=:dayBegin or dt_end is null  )) as depPer      "+  
                               
//"                               inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and       id=" + idPower + "  and             dtbeginNSI<=:dayEnd and     "+  
//"                                                             (dt_end>=:dayBegin or dt_end is null  )                              ) as dep on depPer.id =dep.idVp    "+  
                                                                
"                                left join (select * from DB2ADMIN.REPORT     where dt_begin>=:dayBegin  and dt_begin<=:dayEnd  "+  
"                                 and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=depPer.id     "+  
                                               
"                                 left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID "+                    
                                               
//"                                             left join (select * from DB2ADMIN.REPORT                 where dt_begin>=:dayBegin  and dt_begin<=:dayEnd and form_id=2 and DEL_FL=0) as repF2   "+                             
//"                                             on repF2.department_id=depPer.id    "+                           
//"                                             left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and                            dtbeginNSI<=:dayEnd  and    "+  
//        "                     (dt_end>=:dayBegin  or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup  "+  
"                                                 left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   "+  
                                                               
"                                                             left join (select resource_id,cost from "+  
" (select * from DB2ADMIN.COST where  DEL_FL=0 and railway=" + idPower + "  and month="+m+" and year="+y+") as cost "+  
" left join  DB2ADMIN.COSTS_DETAILS  as cost_details on cost_details.common_info_id=cost.id ) as cost on cost.resource_id=res.id "+  
"                                                             left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id    "+                   
//"                                                             group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id                        "+  
//"                                                             order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id 
                " with ur  "
                
                
                /*  select    

  sum(case when details.resource_id in(13) then plan_col ELSE 0    end) as planE,  
  sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factE,
  sum(case when details.resource_id in(13) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factEM,
  
  sum(case when details.resource_id in(1) then plan_col ELSE 0    end) as planG,  
  sum(case when details.resource_id in(1) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factG,
  sum(case when details.resource_id in(1) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factGM,
  sum(case when details.resource_id in(17) then plan_col ELSE 0    end) as planD,  
  sum(case when details.resource_id in(17) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD,
  sum(case when details.resource_id in(17) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDM,
  sum(case when details.resource_id in(2) then plan_col ELSE 0    end) as planB,  
  sum(case when details.resource_id in(2) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factB,
  sum(case when details.resource_id in(2) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factBM,
  sum(case when details.resource_id in(3) then plan_col ELSE 0    end) as planM,  
  sum(case when details.resource_id in(3) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factM,
  sum(case when details.resource_id in(3) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMM,
  sum(case when details.resource_id in(4,5) then plan_col ELSE 0    end) as planY,  
  sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY,
  sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factYM,
  sum(case when details.resource_id in(6) then plan_col ELSE 0    end) as planMA,  
  sum(case when details.resource_id in(6) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factMA,
  sum(case when details.resource_id in(6) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMAM,
  sum(case when details.resource_id in(7) then plan_col ELSE 0    end) as planD,  
  sum(case when details.resource_id in(7) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD,
  sum(case when details.resource_id in(7) and details.dt_inputfact is not null then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as factDM,
  sum(case when details.resource_id in(12) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factS,
  
  sum(case when details.resource_id in(12) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as factSM,
   
  sum(case when details.resource_id in(16) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO,  
  sum(case when details.resource_id in(15) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO2,  
  sum(case when details.resource_id in(14) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY0,  
  sum(case when details.resource_id in(11) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY2,  
       
  
                
                   depGroup.id as idGroup,  
                    depGroup.namenp as namenpGroup,    
                               depPer.id ,    
                               depPer.namenp , 
                               repF2.id as idF2   
                               
                                 
                    from                                  ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=95 and                   dtbeginNSI<='2014-02-01 00:00:00.000000'  and   
                                (dt_end>='2014-01-01 00:00:00.000000' or dt_end is null  )) as depPer     
                               
                                 inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and                      dtbeginNSI<='2014-02-01 00:00:00.000000' and    
                                                               (dt_end>='2014-01-01 00:00:00.000000' or dt_end is null  )                         ) as dep on depPer.id =dep.idVp   
                                                                
                                  left join (select * from DB2ADMIN.REPORT     where dt_begin>='2014-01-01 00:00:00.000000'  and dt_begin<='2014-02-01 00:00:00.000000' 
                                   and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id    
                                               
                                   left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID          
                                               
                                               
                                               
                                               left join (select * from DB2ADMIN.REPORT                 where dt_begin>='2014-01-01 00:00:00.000000'  and dt_begin<='2014-02-01 00:00:00.000000' and form_id=2 and DEL_FL=0) as repF2                   
                                                on repF2.department_id=depPer.id                    
                                                left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and                            dtbeginNSI<='2014-02-01 00:00:00.000000'  and                                (dt_end>='2014-01-01 00:00:00.000000'  or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup 
                                                   left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id  
                                                               
                                                               left join (select resource_id,cost from
(select * from DB2ADMIN.COST where  DEL_FL=0 and railway=95 and month=1 and year=14) as cost
left join  DB2ADMIN.COSTS_DETAILS  as cost_details on cost_details.common_info_id=cost.id ) as cost on cost.resource_id=res.id
                                                               
                                               
                                                               
                                                                  
                                                               left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id                           
                                                               group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id                       
                                                               order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id  

*/
)
                .addScalar("planE").addScalar("factE").addScalar("factEM")
                .addScalar("planG").addScalar("factG").addScalar("factGM")
                .addScalar("planD").addScalar("factD").addScalar("factDM")
                .addScalar("planB").addScalar("factB").addScalar("factBM")
                .addScalar("planM").addScalar("factM").addScalar("factMM")
                .addScalar("planY").addScalar("factY").addScalar("factYM")
                .addScalar("planMA").addScalar("factMA").addScalar("factMAM")
                .addScalar("planDA").addScalar("factDA").addScalar("factDAM")
                .addScalar("factS").addScalar("factSM").addScalar("factO")
                .addScalar("factO2").addScalar("factY0").addScalar("factY2")
                
               // .addScalar("idGroup").addScalar("namenpGroup").addScalar("id").addScalar("namenp").addScalar("idF2")
                
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(DetailsForm5.class));


        List<DetailsForm5> pusList = query.list();
        return pusList;
    }
/*Форма 5 период*/
    public List<DetailsForm5> getListWithDetailsForm3Per(long idPowerUser,long idPower, Date dayBegin, Date dayEnd) {
        List<DetailsForm5> bd = new ArrayList();
        byte m=(new StringImprover()).getMonth(dayBegin);
        byte y=(new StringImprover()).getYear(dayBegin);
        Session sess = SessionFactorySingleton.getSessionFactoryInstance()
                .openSession();

        Transaction t = null;
        try {
            t = sess.beginTransaction();


            bd = getListWithDetailsForm3Per(idPowerUser,idPower, dayBegin, dayEnd,m,y, sess);

            t.commit();
        } catch (Exception e) {
            Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, e);
            if (t != null) {
                try {
                    t.rollback();
                } catch (HibernateException he) {
                    Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
                }
            }
        } finally {
            try {
                sess.close();
            } catch (HibernateException he) {
                Logger.getLogger(ReportRepository.class.getName()).log(Level.ERROR, null, he);
            }
        }


        return bd;
    }
    
    
    
     protected List<DetailsForm5> getListWithDetailsForm3Per(/*long idRes,int idNp, */long idPowerUser,long idPower, Date dayBegin, Date dayEnd,byte m,byte y, Session sess) {


        Query query = sess.createSQLQuery(
            " select   "+  
 " sum(case when details.resource_id in(13) then plan_col ELSE 0    end) as planE,   "+  
 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factE, "+  
 " sum(case when details.resource_id in(13) and details.dt_inputfact is not null "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factEM, "+  
  
 " sum(case when details.resource_id in(1) then plan_col ELSE 0    end) as planG,   "+  
 " sum(case when details.resource_id in(1) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factG, "+  
 " sum(case when details.resource_id in(1) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factGM, "+  
 " sum(case when details.resource_id in(17) then plan_col ELSE 0    end) as planD,   "+  
 " sum(case when details.resource_id in(17) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD, "+  
 " sum(case when details.resource_id in(17) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDM, "+  
 " sum(case when details.resource_id in(2) then plan_col ELSE 0    end) as planB,   "+  
 " sum(case when details.resource_id in(2) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factB, "+  
 " sum(case when details.resource_id in(2) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factBM, "+  
 " sum(case when details.resource_id in(3) then plan_col ELSE 0    end) as planM,  "+   
 " sum(case when details.resource_id in(3) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factM, "+  
 " sum(case when details.resource_id in(3) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMM, "+  
 " sum(case when details.resource_id in(4,5) then plan_col ELSE 0    end) as planY,   "+  
 " sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY, "+  
 " sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factYM, "+  
 " sum(case when details.resource_id in(6) then plan_col ELSE 0    end) as planMA,   "+  
 " sum(case when details.resource_id in(6) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factMA, "+  
 " sum(case when details.resource_id in(6) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMAM, "+  
 " sum(case when details.resource_id in(7) then plan_col ELSE 0    end) as planDA,   "+  
 " sum(case when details.resource_id in(7) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factDA, "+  
 " sum(case when details.resource_id in(7) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDAM, "+  
 " sum(case when details.resource_id in(12) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factS, "+  
 " sum(case when details.resource_id in(12) and details.dt_inputfact is not null  "+
                " then "+ 
    "  (case when (cost=0 or cost is null) then "+ 
  "  fact_money  ELSE fact_col*cost   end ) else 0 end) as  factSM, "+  
 " sum(case when details.resource_id in(16) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO,   "+  
 " sum(case when details.resource_id in(15) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO2,   "+  
 " sum(case when details.resource_id in(14) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY0,   "+  
 " sum(case when details.resource_id in(11) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY2   "+  
       
//"                 depGroup.id as idGroup,   "+  
//"                  depGroup.namenp as namenpGroup,   "+    
//"                             depPer.id ,     "+  
//"                             depPer.namenp   "+  

                               
                                 
"                  from                                  ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=" + idPowerUser + "  and                    dtbeginNSI<=:dayEnd  and    "+  
"                             (dt_end>=:dayBegin or dt_end is null  )) as depPer      "+  
                               
//"                               inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and                      dtbeginNSI<=:dayEnd and     "+  
//"                                                             (dt_end>=:dayBegin or dt_end is null  )                              ) as dep on depPer.id =dep.idVp    "+  
                                                                
"                                left join (select * from DB2ADMIN.REPORT     where dt_begin>=:dayBegin  and dt_begin<=:dayEnd  "+  
"                                 and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=depPer.id     "+  
                                               
"                                 left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID "+                    
                                               
                               
//"                                             left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and                            dtbeginNSI<=:dayEnd  and    "+  
 //       "                     (dt_end>=:dayBegin  or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup  "+  
"                                                 left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id   "+  
                                                               
"                                                             left join (select resource_id,cost from "+  
" (select * from DB2ADMIN.COST where  DEL_FL=0 and railway=" + idPower + "  and month="+m+" and year="+y+") as cost "+  
" left join  DB2ADMIN.COSTS_DETAILS  as cost_details on cost_details.common_info_id=cost.id ) as cost on cost.resource_id=res.id "+  
"                                                             left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id    with ur "                 
//"                                                             group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id                             "+  
//"                                                             order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id   with ur "
                
                
                /*  select    

  sum(case when details.resource_id in(13) then plan_col ELSE 0    end) as planE,  
  sum(case when details.resource_id in(13) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factE,
  sum(case when details.resource_id in(13) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factEM,
  
  sum(case when details.resource_id in(1) then plan_col ELSE 0    end) as planG,  
  sum(case when details.resource_id in(1) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factG,
  sum(case when details.resource_id in(1) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factGM,
  sum(case when details.resource_id in(17) then plan_col ELSE 0    end) as planD,  
  sum(case when details.resource_id in(17) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD,
  sum(case when details.resource_id in(17) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factDM,
  sum(case when details.resource_id in(2) then plan_col ELSE 0    end) as planB,  
  sum(case when details.resource_id in(2) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factB,
  sum(case when details.resource_id in(2) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factBM,
  sum(case when details.resource_id in(3) then plan_col ELSE 0    end) as planM,  
  sum(case when details.resource_id in(3) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factM,
  sum(case when details.resource_id in(3) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMM,
  sum(case when details.resource_id in(4,5) then plan_col ELSE 0    end) as planY,  
  sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY,
  sum(case when details.resource_id in(4,5) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factYM,
  sum(case when details.resource_id in(6) then plan_col ELSE 0    end) as planMA,  
  sum(case when details.resource_id in(6) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factMA,
  sum(case when details.resource_id in(6) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as  factMAM,
  sum(case when details.resource_id in(7) then plan_col ELSE 0    end) as planD,  
  sum(case when details.resource_id in(7) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factD,
  sum(case when details.resource_id in(7) and details.dt_inputfact is not null then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as factDM,
  sum(case when details.resource_id in(12) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factS,
  
  sum(case when details.resource_id in(12) and details.dt_inputfact is not null  then
     (case when (cost=0 or cost is null) then
   fact_money  ELSE fact_col*cost   end ) else 0 end) as factSM,
   
  sum(case when details.resource_id in(16) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO,  
  sum(case when details.resource_id in(15) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factO2,  
  sum(case when details.resource_id in(14) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY0,  
  sum(case when details.resource_id in(11) and details.dt_inputfact is not null then fact_col ELSE 0    end) as factY2,  
       
  
                
                   depGroup.id as idGroup,  
                    depGroup.namenp as namenpGroup,    
                               depPer.id ,    
                               depPer.namenp , 
                               repF2.id as idF2   
                               
                                 
                    from                                  ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and idVP=95 and                   dtbeginNSI<='2014-02-01 00:00:00.000000'  and   
                                (dt_end>='2014-01-01 00:00:00.000000' or dt_end is null  )) as depPer     
                               
                                 inner join   ( select * from DB2ADMIN.DEPARTMENT where  DEL_FL=0 and                      dtbeginNSI<='2014-02-01 00:00:00.000000' and    
                                                               (dt_end>='2014-01-01 00:00:00.000000' or dt_end is null  )                         ) as dep on depPer.id =dep.idVp   
                                                                
                                  left join (select * from DB2ADMIN.REPORT     where dt_begin>='2014-01-01 00:00:00.000000'  and dt_begin<='2014-02-01 00:00:00.000000' 
                                   and form_id=1 and DEL_FL=0) as rep  on rep.DEPARTMENT_ID=dep.id    
                                               
                                   left join ( select * from DB2ADMIN.RESOURCE_DETAILS where  DEL_FL=0 ) as details on rep.ID=details.REPORT_ID          
                                               
                                               
                                               
                                               left join (select * from DB2ADMIN.REPORT                 where dt_begin>='2014-01-01 00:00:00.000000'  and dt_begin<='2014-02-01 00:00:00.000000' and form_id=2 and DEL_FL=0) as repF2                   
                                                on repF2.department_id=depPer.id                    
                                                left join ( select * from DB2ADMIN.DEPARTMENTGROUP where  DEL_FL=0  and                            dtbeginNSI<='2014-02-01 00:00:00.000000'  and                                (dt_end>='2014-01-01 00:00:00.000000'  or dt_end is null  )) as depGroup on depGroup.id =depPer.departmentgroup 
                                                   left join ( select * from DB2ADMIN.RESOURCE where  DEL_FL=0) as res on details.RESOURCE_ID=res.id  
                                                               
                                                               left join (select resource_id,cost from
(select * from DB2ADMIN.COST where  DEL_FL=0 and railway=95 and month=1 and year=14) as cost
left join  DB2ADMIN.COSTS_DETAILS  as cost_details on cost_details.common_info_id=cost.id ) as cost on cost.resource_id=res.id
                                                               
                                               
                                                               
                                                                  
                                                               left join (select *   from DB2ADMIN.MEASURE  where  DEL_FL=0) as mas on res.MEASURE_ID=mas.id                           
                                                               group by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id  , repF2.id                       
                                                               order by depGroup.id,depGroup.namenp, depPer.namenp,  depPer.id , repF2.id  

*/
)
                .addScalar("planE").addScalar("factE").addScalar("factEM")
                .addScalar("planG").addScalar("factG").addScalar("factGM")
                .addScalar("planD").addScalar("factD").addScalar("factDM")
                .addScalar("planB").addScalar("factB").addScalar("factBM")
                .addScalar("planM").addScalar("factM").addScalar("factMM")
                .addScalar("planY").addScalar("factY").addScalar("factYM")
                .addScalar("planMA").addScalar("factMA").addScalar("factMAM")
                .addScalar("planDA").addScalar("factDA").addScalar("factDAM")
                .addScalar("factS").addScalar("factSM").addScalar("factO")
                .addScalar("factO2").addScalar("factY0").addScalar("factY2")
                
               // .addScalar("idGroup").addScalar("namenpGroup").addScalar("id").addScalar("namenp")//.addScalar("idF2")
                
                .setParameter("dayBegin", dayBegin)
                .setParameter("dayEnd", dayEnd)
                .setResultTransformer(Transformers.aliasToBean(DetailsForm5.class));


        List<DetailsForm5> pusList = query.list();
        return pusList;
    }
}
     

