/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.service.Constants;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class DepartmentTreePageBean implements Serializable{

     private TreeNode root;
     private TreeNode selectedNode;

     private Department deparNode;
     @ManagedProperty(value = "#{autorizationBean}")
     private AutorizationBean session;
     
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Department getDeparNode() {
        return deparNode;
    }

    public void setDeparNode(Department deparNode) {
        this.deparNode = deparNode;
    }

    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
    }
     
     
     
     
     
    /**
     * Creates a new instance of DepartmentTreePageBean
     */
    public DepartmentTreePageBean() {
    }
    
    
    
    
     /*первый элемент по idVp*/

    private void addNode(TreeNode parent, int element) {
        DepartmentRepository deprep = new DepartmentRepository();
        for (Object objectDepatmentNode : deprep.getElementsDepartment(element, false)) {
            TreeNode node = new DefaultTreeNode(objectDepatmentNode, parent);

        }
    }
    
    /*первый элемент по idNp*/
    
    private void addFirst(TreeNode parent, int element) {
        DepartmentRepository deprep = new DepartmentRepository();
        for (Object objectDepatmentNode : deprep.getFirstElementsDepartment(element, false)) {
            TreeNode node = new DefaultTreeNode(objectDepatmentNode, parent);
        }
    }
    
    
    public void fist() {
        Department departmentRoot = new Department();
        root = new DefaultTreeNode(departmentRoot, null);
        addFirst(root, (int) session.getUser().getDepartmentID());
    }
    
    
    @PostConstruct
    public void Init() {
        fist();
    }
    
    
    
    
     public void onNodeSelect(NodeSelectEvent event) {
        deparNode = new Department();
        
        if (event.getTreeNode().getData() instanceof Department) {
            deparNode = (Department) event.getTreeNode().getData();
            if (event.getTreeNode().getChildCount() == 0) {
                addNode(event.getTreeNode(), ((Department) event.getTreeNode().getData()).getIdNp());
            }
            if (event.getTreeNode().getParent().isSelected()) {
                event.getTreeNode().getParent().setSelected(false);
            }
            event.getTreeNode().setExpanded(true);
           
        }
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
     public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeExpand(NodeExpandEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
