/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.io.Serializable;

/**
 *
 * @author apopovkin
 */
public class InputExcelError implements Serializable{
    private String inf1;
    private String inf2;
    private String inf3;

    public String getInf1() {
        return inf1;
    }

    public void setInf1(String inf1) {
        this.inf1 = inf1;
    }

    public String getInf2() {
        return inf2;
    }

    public void setInf2(String inf2) {
        this.inf2 = inf2;
    }

    public String getInf3() {
        return inf3;
    }

    public void setInf3(String inf3) {
        this.inf3 = inf3;
    }
    
     
    
    
    
      @Override
    public String toString() {
        return "inf1{ inf1=" + inf1 + ", inf2=" + inf2 + ", inf3=" + inf3 + "'}'";
    }
    
}
