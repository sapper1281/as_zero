/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.util.Date;
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
import rzd.vivc.aszero.autorisation.AdminPage;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.repository.DepartmentRepository;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class DepartmentPageBean extends AdminPage implements Serializable {

    private TreeNode root;
    private TreeNode selectedNode;
    private Department depar;
    private Department inputDep;
    private boolean deparSelect = false;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;

    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
    }

    //private int ELEM = 1;
    public Department getInputDep() {
        return inputDep;
    }

    public void setInputDep(Department inputDep) {
        this.inputDep = inputDep;
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public boolean isDeparSelect() {
        return deparSelect;

    }

    public void setDeparSelect(boolean deparSelect) {
        this.deparSelect = deparSelect;
    }

    public Department getDepar() {
        return depar;

    }

    public void setDepar(Department depar) {
        this.depar = depar;
    }

    public void save() {
        inputDep.setDtBeginNSI(inputDep.getDtBeginNSI());
        inputDep.setDtEditNSI(new Date());
        inputDep.setIdVp(depar.getIdNp());
        System.out.println("=========" + inputDep.getId());
        if (inputDep.getIdVp() != 0) {
            (new DepartmentRepository()).save(inputDep);
            System.out.println("=========" + inputDep.getId());
            inputDep.setIdNp((int) inputDep.getId());
            if (inputDep.getIdNp() != 0) {
                (new DepartmentRepository()).save(inputDep);
                fist();
            }
        }
    }

    public void save1() {
        depar.setDtBeginNSI(depar.getDtBeginNSI());
        depar.setDt_end(depar.getDt_end());
        depar.setDtEditNSI(new Date());
        System.out.println(depar);
        if (depar.getIdVp() != 0 && inputDep.getIdNp() != 0) {
             (new DepartmentRepository()).save(depar);
        }
        fist();
    }

    public void del() {
        depar.setDeleted(true);
        DepartmentRepository depR = new DepartmentRepository();
        depR.save(depar);
        fist();
    }

    private void addNode(TreeNode parent, int element) {
        DepartmentRepository deprep = new DepartmentRepository();
        for (Object objectDepatmentNode : deprep.getElementsDepartment(element, false)) {
            TreeNode node = new DefaultTreeNode(objectDepatmentNode, parent);
        }
    }

    private void addFirst(TreeNode parent, int element) {
        DepartmentRepository deprep = new DepartmentRepository();
        for (Object objectDepatmentNode : deprep.getFirstElementsDepartment(element, false)) {
            TreeNode node = new DefaultTreeNode(objectDepatmentNode, parent);
        }
    }

    public void fist() {
        inputDep = new Department();
        depar = new Department();
        inputDep.setDtBeginNSI(new Date());
        Department departmentRoot = new Department();
        root = new DefaultTreeNode(departmentRoot, null);
        addFirst(root, (int) session.getUser().getDepartmentID());
    }

    @PostConstruct
    public void Init() {
        fist();
    }

    public DepartmentPageBean() {
        super();
    }

    public void onNodeSelect(NodeSelectEvent event) {
        Department node = new Department();
        if (event.getTreeNode().getData() instanceof Department) {
            node = (Department) event.getTreeNode().getData();
            depar = node;
            if (event.getTreeNode().getChildCount() == 0) {
                addNode(event.getTreeNode(), node.getIdNp());
                deparSelect = true;
                System.out.println(depar);
            }
            if (event.getTreeNode().getChildCount() > 0) {

                System.out.println("2");
            }

        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", node.getNameNp() + "  " + node.getNameVp());
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
