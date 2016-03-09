/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.repository;

import java.io.Serializable;
import rzd.vivc.aszero.dto.TypeTER;

/**
 *
 * @author VVolgina
 */
public class TypeTERRepository extends BaseRepository implements Serializable{
    
    
    
    
    
    
    public static void main(String[] args) {
        //TypeTER ter=new TypeTER("газ");
       // TypeTER ter=new TypeTER("бензин");
       // TypeTER ter=new TypeTER("мазут");
        //TypeTER ter=new TypeTER("уголь");
        //TypeTER ter=new TypeTER("масла");
        //TypeTER ter=new TypeTER("другие");
        //TypeTER ter=new TypeTER("Уборка территории");
        //TypeTER ter=new TypeTER("Сокращение выбросов загрязняющих веществ в атмосферу");
       // ter.setDeleted(true);
    //    TypeTER ter=new TypeTER("Не известно1");
     //   ter.setDeleted(true);
    //   (new TypeTERRepository()).save(ter);
        System.out.println((new TypeTERRepository()).getAll(TypeTER.class));
        System.out.println((new TypeTERRepository()).getAllNonDeleted(TypeTER.class));
    } 
}
