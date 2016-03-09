/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

/**
 *
 * @author apopovkin
 */
public class FormGraf {
     private int year1,month1;
       private Number       d;
      private Number       idDep;
    
     private String depNameNP ;
     private Number      depPerIdDep;
     private String depPerNameNP ;

    public Number getD() {
        return d;
    }

    public void setD(Number d) {
        this.d = d;
    }

     
     
     
    public int getYear1() {
        return year1;
    }

    public void setYear1(int year1) {
        this.year1 = year1;
    }

    public int getMonth1() {
        return month1;
    }

    public void setMonth1(int month1) {
        this.month1 = month1;
    }

    public Number getIdDep() {
        return idDep;
    }

    public void setIdDep(Number idDep) {
        this.idDep = idDep;
    }

    public Number getDepPerIdDep() {
        return depPerIdDep;
    }

    public void setDepPerIdDep(Number depPerIdDep) {
        this.depPerIdDep = depPerIdDep;
    }


    public String getDepNameNP() {
        return depNameNP;
    }

    public void setDepNameNP(String depNameNP) {
        this.depNameNP = depNameNP;
    }

    public String getDepPerNameNP() {
        return depPerNameNP;
    }

    public void setDepPerNameNP(String depPerNameNP) {
        this.depPerNameNP = depPerNameNP;
    }
     
}
