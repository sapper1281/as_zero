/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.classes;

import rzd.vivc.aszero.dto.Department;

/**
 *
 * @author apopovkin
 */
public class DepartmentCoR {
    
    
    private DepartmentCoR parent;
    private Department nodeName;

    /**
     * Creates a new instance of DepartmentPageBean
     */
    public DepartmentCoR(DepartmentCoR parent, Department nodeName) {
        this.parent = parent;
        this.nodeName = nodeName;
    }

    public Department getRootNodeName() {
        if (parent == null) {
            return this.nodeName;
        } else {
            return this.parent.getRootNodeName();
        }
    }
    
}
