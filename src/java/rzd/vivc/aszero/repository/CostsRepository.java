/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.util.Date;
import org.hibernate.Session;
import rzd.vivc.aszero.dto.Costs;
import rzd.vivc.aszero.service.StringImprover;

/**
 * Репозиторий для цен на ресурсы
 * @author VVolgina
 */
public class CostsRepository extends BaseRepository {

    /**
     * Извлекает из БД цены на выбранный месяц для данной ЖД
     * @param dat месяц и год
     * @param railwayID Id ЖД
     * @param sess сессия Hibernate
     * @return цены 
     */
    protected Costs getByDate(Date dat, long railwayID, Session sess){
        StringImprover imp=new StringImprover();
        byte month = imp.getMonth(dat);
        byte year = imp.getYear(dat);
        Costs db;
        db = (Costs) sess.createQuery("SELECT c from Costs as c WHERE c.month=:mon AND c.year=:year AND c.railway=:rail")
                .setByte("mon", month)
                .setByte("year", year)
                .setLong("rail", railwayID)
                .setMaxResults(1)
                .uniqueResult();
        return db;
    }
    
    public static void main(String[] args) {
        //создание и инициализация цены для сохранения в БД
       /* User usr = new UserRepository().get(new User(7));
        Costs cost=new Costs(usr);
        CostsRepository costsRepository = new CostsRepository();
        cost.setCost((float) 0.0581);
        cost.setMonth((byte)12);
        cost.setYear((byte)14);
        cost.setRailway(35);
        cost.setResource(new Resource(1));
        System.out.println(cost);
        costsRepository.save(cost);
        System.out.println(cost);
        Costs get = costsRepository.get(new Costs(1));
        System.out.println(get);*/
    }
    
    
}
