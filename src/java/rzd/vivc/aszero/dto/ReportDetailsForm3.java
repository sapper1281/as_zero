/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.dto;

import java.io.Serializable;
import java.util.List;




/**
 *
 * @author apopovkin
 */
public class ReportDetailsForm3 implements Serializable{
    
    
    
        public ReportDetailsForm3(float cell1,float cell2,float cell3,float cell4,float cell5,float cell6,
             float cell7,float cell8,float cell9,float cell10,float cell11,float cell12,float cell13) {
                this.cell1 = cell1;
                this.cell2 = cell2;
                this.cell3 = cell3;
              //  this.cell4 = cell4;
                this.cell5 = cell5;
                this.cell6 = cell6;
                this.cell7 = cell7;
                this.cell8 = cell8;
                this.cell9 = cell9;
                this.cell10 = cell10;
                this.cell11 = cell11;
                this.cell12 = cell12;
                this.cell13 = cell13;
        }
    
    
    private Department dep;
    //private Department dep;
    private long repId;
    //private Resource getActiveResource()
    
    private float cell1;
    private float cell2;
    private float cell3;
    private List<TypeTERForm3> cell4;
    private float cell5;
    private float cell6;
    private float cell7;
    private float cell8;
    private float cell9;
    private float cell10;
    private float cell11;
    private float cell12;
    private float cell13;

    public long getRepId() {
        return repId;
    }

    public void setRepId(long repId) {
        this.repId = repId;
    }

    
    
    
    public List<TypeTERForm3> getCell4() {
        return cell4;
    }

    public void setCell4(List<TypeTERForm3> cell4) {
        this.cell4 = cell4;
    }

    

    
    

    
    
    
    
    public ReportDetailsForm3() {
    
    }

    
    
    
    public Department getDep() {
        return dep;
    }

    public void setDep(Department dep) {
        this.dep = dep;
    }

    
    
    
    
    public float getCell1() {
        return cell1;
    }

    public void setCell1(float cell1) {
        this.cell1 = cell1;
    }

    public float getCell2() {
        return cell2;
    }

    public void setCell2(float cell2) {
        this.cell2 = cell2;
    }

    public float getCell3() {
        return cell3;
    }

    public void setCell3(float cell3) {
        this.cell3 = cell3;
    }

   
    public float getCell5() {
        return cell5;
    }

    public void setCell5(float cell5) {
        this.cell5 = cell5;
    }

    public float getCell6() {
        return cell6;
    }

    public void setCell6(float cell6) {
        this.cell6 = cell6;
    }

    public float getCell7() {
        return cell7;
    }

    public void setCell7(float cell7) {
        this.cell7 = cell7;
    }

    public float getCell8() {
        return cell8;
    }

    public void setCell8(float cell8) {
        this.cell8 = cell8;
    }

    public float getCell9() {
        return cell9;
    }

    public void setCell9(float cell9) {
        this.cell9 = cell9;
    }

    public float getCell10() {
        return cell10;
    }

    public void setCell10(float cell10) {
        this.cell10 = cell10;
    }

    public float getCell11() {
        return cell11;
    }

    public void setCell11(float cell11) {
        this.cell11 = cell11;
    }

    public float getCell12() {
        return cell12;
    }

    public void setCell12(float cell12) {
        this.cell12 = cell12;
    }

    public float getCell13() {
        return cell13;
    }

    public void setCell13(float cell13) {
        this.cell13 = cell13;
    }
    
    
    
}
