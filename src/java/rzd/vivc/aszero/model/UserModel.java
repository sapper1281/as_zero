/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.model;

import java.io.Serializable;
import java.util.List;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.repository.DepartmentRepository;

/**
 *
 * @author VVolgina
 */
public class UserModel implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="поля">
    private long userID = 0;
    private long departmentID = 0;
    private long departmentPower = 0;
    private int num = 0;
    private long idType = 0;

    //</editor-fold>
    public UserModel() {
    }

    //<editor-fold defaultstate="collapsed" desc="get-set"> 
    /**
     * Возвращает id дороги, к которой принадлежит пользователь
     *
     * @return id дороги, к которой принадлежит пользователь
     */
    public long getDepartmentPower() {
        if (departmentPower == 0) {

            departmentPower = new DepartmentRepository().getDepartmentPower(departmentID);
        }
        return departmentPower;
    }

    public void setDepartmentPower(long departmentPower) {
        this.departmentPower = departmentPower;
    }

    public UserModel(long userID) {
        this.userID = userID;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Get the value of departmentID
     *
     * @return the value of departmentID
     */
    public long getDepartmentID() {
        return departmentID;
    }

    /**
     * Set the value of departmentID
     *
     * @param departmentID new value of departmentID
     */
    public void setDepartmentID(long departmentID) {
        this.departmentID = departmentID;
    }

    /**
     * Get the value of userID
     *
     * @return the value of userID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Set the value of userID
     *
     * @param userID new value of userID
     */
    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getIdType() {
        return idType;
    }

    public void setIdType(long idType) {
        this.idType = idType;
    }
    //</editor-fold>
}
