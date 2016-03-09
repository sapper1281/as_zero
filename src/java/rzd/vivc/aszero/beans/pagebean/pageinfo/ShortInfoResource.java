/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean.pageinfo;

/**
 * id, Назване ресурса и цена за него в выводимомп месяце
 *
 * @author vvolgina
 */
public class ShortInfoResource {
    //Id

    private long id;
    //название ресурса
    private String name;
    //цена на ресурс
    private float cost;
    //коэффициент загрязнения окр.среды
    private float koef_polution;

    /**
     * Id
     *
     * @return Id
     */
    public long getId() {
        return id;
    }

    /**
     * Id
     *
     * @param id Id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * название ресурса
     *
     * @return название ресурса
     */
    public String getName() {
        return name;
    }

    /**
     * название ресурса
     *
     * @param name название ресурса
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * цена на ресурс
     *
     * @return цена на ресурс
     */
    public float getCost() {
        return cost;
    }

    /**
     * цена на ресурс
     *
     * @param cost цена на ресурс
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     *коэффициент загрязнения окр.среды
     * @return коэффициент загрязнения окр.среды
     */
    public float getKoef_polution() {
        return koef_polution;
    }

    /**
     * коэффициент загрязнения окр.среды
     * @param koef_polution коэффициент загрязнения окр.среды
     */
    public void setKoef_polution(float koef_polution) {
        this.koef_polution = koef_polution;
    }
    
    

    /**
     *
     * @param id
     * @param name название ресурса
     * @param cost цена на ресурс
     */
    public ShortInfoResource(long id, String name, float cost, float koef_polution) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.koef_polution = koef_polution;
    }

    /**
     *
     * @param id
     * @param name название ресурса
     */
    public ShortInfoResource(long id, String name, float koef_polution) {
        this.id = id;
        this.name = name;
        this.cost = 0;
        this.koef_polution = koef_polution;
    }

    /**
     *
     */
    public ShortInfoResource() {
    }
    
    /**
     * Название ресурса для exel
     * @return
     */
    public String getEXELString(){
        return name.replaceAll("<sup>", "").replaceAll("</sup>", "");
    }

    @Override
    public String toString() {
        return "ShortInfoResource{" + "id=" + id + ", name=" + name + ", cost=" + cost + '}';
    }
}
