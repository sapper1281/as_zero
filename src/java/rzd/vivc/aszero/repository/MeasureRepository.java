/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import rzd.vivc.aszero.dto.Measure;

/**
 * Репозиторий для мер измерения
 * @author VVolgina
 */
public class MeasureRepository extends BaseRepository{
    public static void main(String[] args) {
        /**/
        // Measure mes=new Measure("т");
       // Measure mes=new Measure("кВтч");
        //Measure mes=new Measure("м<sup>3</sup>");
       // Measure mes=new Measure("ГКал");
        //Measure mes=new Measure("м<sup>2</sup>");
        Measure mes=new Measure("шт");
        (new MeasureRepository()).save(mes);
       /* Measure mes=new Measure();
        mes.setId(1);
        System.out.println((new MeasureRepository()).get(mes));*/
        System.out.println((new MeasureRepository()).getAll(Measure.class));
        System.out.println((new MeasureRepository()).getAllNonDeleted(Measure.class));
    } 
}
