/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import rzd.vivc.aszero.autorisation.AdminPage;
import rzd.vivc.aszero.beans.session.AutorizationBean;
import rzd.vivc.aszero.dto.Department;
import rzd.vivc.aszero.dto.DepartmentGroup;
import rzd.vivc.aszero.repository.DepartmentRepository;
import rzd.vivc.aszero.service.Constants;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@ViewScoped
public class DepartmentPageBeanNew extends AdminPage implements Serializable {

    public DepartmentPageBeanNew() {
        super();
    }

    
    
    
    
    private TreeNode root;
    private TreeNode selectedNode;
    private Department depar;
    private Department deparNode;
    private boolean deparSelect = false;
    @ManagedProperty(value = "#{autorizationBean}")
    private AutorizationBean session;
    private String address = Constants.DEPARTMENT_URL;
    int tab = 0;
    private String message;
    DepartmentGroup depGroupSelected;
    List<DepartmentGroup> depGroup;
    long test=123;

    public DepartmentGroup getDepGroupSelected() {
        return depGroupSelected;
    }

    public void setDepGroupSelected(DepartmentGroup depGroupSelected) {
        this.depGroupSelected = depGroupSelected;
    }

    
    
    
    public long getTest() {
        return test;
    }

    public void setTest(long test) {
        this.test = test;
    }
    
    
    
    

    public List<DepartmentGroup> getDepGroup() {
        return depGroup;
    }

    public void setDepGroup(List<DepartmentGroup> depGroup) {
        this.depGroup = depGroup;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public AutorizationBean getSession() {
        return session;
    }

    public void setSession(AutorizationBean session) {
        this.session = session;
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

    public Department getDeparNode() {
        return deparNode;
    }

    public void setDeparNode(Department deparNode) {
        this.deparNode = deparNode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    /*удаление ветки*/

    public void del_child(Department depNode) {
        DepartmentRepository deprep = new DepartmentRepository();
        List<Department> deprepList = deprep.getElementsDepartment(depNode.getIdNp(), false);
        if (deprepList.size() > 0) {
            for (Department objectDepatment : deprepList) {
                objectDepatment.setDeleted(true);
                new DepartmentRepository().save(objectDepatment);
                del_child(objectDepatment);
            }
        }

    }
    
     public void del() {
        if (selectedNode.isSelected()) {
            selectedNode.setSelected(false);
            selectedNode.setExpanded(false);
        }
        deparNode.setDeleted(true);
        DepartmentRepository depR = new DepartmentRepository();
        depR.save(deparNode);


        if (selectedNode.getChildCount() > 0) {
            del_child((Department) selectedNode.getData());
        }



        selectedNode.getParent().getChildren().clear();
        addNode(selectedNode.getParent(), ((Department) selectedNode.getParent().getData()).getIdNp());
        selectedNode.getParent().setSelected(true);
        selectedNode.getParent().setExpanded(true);
        deparNode = (Department) selectedNode.getParent().getData();


        depar.setId(((Department) selectedNode.getParent().getData()).getId());
        cloneDep(depar, (Department) selectedNode.getParent().getData());
        selectedNode = selectedNode.getParent();
        
        Logger.getLogger(DepartmentPageBeanNew.class.getName()).log(Level.ERROR, "Удалено СП: "+"("+deparNode.getId()+")"
                +deparNode.getNameNp()+" адм. "+session.getUser().getUserID());

        
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


        depGroup = (new DepartmentRepository()).getActiveListDepartmentGroup();
        
     
       
       fist();
    }
    
    
      public void onNodeSelect(NodeSelectEvent event) {
        deparNode = new Department();
        depar = new Department();
        if (event.getTreeNode().getData() instanceof Department) {
            deparNode = (Department) event.getTreeNode().getData();
            System.out.println("deparNode " + deparNode);


            depar.setId(deparNode.getId());
            cloneDep(depar, deparNode);
            System.out.println("depar " + depar);


            if (event.getTreeNode().getChildCount() == 0) {
                addNode(event.getTreeNode(), ((Department) event.getTreeNode().getData()).getIdNp());
            }
            if (event.getTreeNode().getParent().isSelected()) {
                event.getTreeNode().getParent().setSelected(false);
            }
            event.getTreeNode().setExpanded(true);
            deparSelect = true;

           

        }
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    

    /*копия department*/
    private void cloneDep(Department fistDepar, Department secondDepar) {
        fistDepar.setNameNp(secondDepar.getNameNp());
        fistDepar.setNum(secondDepar.getNum());
        fistDepar.setDtBeginNSI(secondDepar.getDtBeginNSI());
        fistDepar.setDt_end(secondDepar.getDt_end());
        fistDepar.setDepartmentGroup_id(secondDepar.getDepartmentGroup_id());


    }

    public void save() {
        Department inputDep = new Department();
        inputDep.setDtBeginNSI(depar.getDtBeginNSI());
        inputDep.setDtEditNSI(new Date());
        inputDep.setNameNp(depar.getNameNp());
        inputDep.setDt_end(depar.getDt_end());
        inputDep.setIdVp(deparNode.getIdNp());
        inputDep.setNum(depar.getNum());
        inputDep.setDepartmentGroup_id(depar.getDepartmentGroup_id());
        if(inputDep.getDtBeginNSI()==null){
            Calendar dt_max_cal = Calendar.getInstance();
           // dt_max_cal.setTime(new Date());
            dt_max_cal.set(Calendar.DATE, 1);
            //dt_max_cal.set(Calendar.MONTH, 1);
            dt_max_cal.set(Calendar.AM_PM, 0);
            dt_max_cal.set(Calendar.MINUTE, 0);
            dt_max_cal.set(Calendar.SECOND, 0);
            dt_max_cal.set(Calendar.MILLISECOND, 0);
        inputDep.setDtBeginNSI(dt_max_cal.getTime());
        }
        (new DepartmentRepository()).save(inputDep);
        inputDep.setIdNp((int) inputDep.getId());
        (new DepartmentRepository()).save(inputDep);
        selectedNode.getChildren().clear();
        addNode(selectedNode, ((Department) selectedNode.getData()).getIdNp());
        selectedNode.setExpanded(true);
        cloneDep(depar, (Department) selectedNode.getData());
        Logger.getLogger(DepartmentPageBeanNew.class.getName()).log(Level.ERROR, "Добавлено СП: "+"("+inputDep.getId()+")"
                +deparNode.getNameNp()+" адм. "+session.getUser().getUserID()+" Dt_end "+inputDep.getDt_end());
    }

    public void update() {
        deparNode.setDtEditNSI(new Date());
        deparNode.setNameNp(depar.getNameNp());
        deparNode.setDtBeginNSI(depar.getDtBeginNSI());
        deparNode.setDt_end(depar.getDt_end());
        deparNode.setNum(depar.getNum());
        deparNode.setDepartmentGroup_id(depar.getDepartmentGroup_id());
        if(deparNode.getDtBeginNSI()==null){
            Calendar dt_max_cal = Calendar.getInstance();
           // dt_max_cal.setTime(new Date());
            dt_max_cal.set(Calendar.DATE, 1);
           // dt_max_cal.set(Calendar.MONTH, 1);
            dt_max_cal.set(Calendar.AM_PM, 0);
            dt_max_cal.set(Calendar.MINUTE, 0);
            dt_max_cal.set(Calendar.SECOND, 0);
            dt_max_cal.set(Calendar.MILLISECOND, 0);
        deparNode.setDtBeginNSI(dt_max_cal.getTime());
        }
        (new DepartmentRepository()).save(deparNode);
  Logger.getLogger(DepartmentPageBeanNew.class.getName()).log(Level.ERROR, "Обнавлено СП: "+"("+deparNode.getId()+")"
                +deparNode.getNameNp()+" адм. "+session.getUser().getUserID()+" Dt_end "+deparNode.getDt_end());
    }

 /*  public void  onChecked( )
   {
       if(depar.getDepartmentGroup_id()!=null)
       System.out.println(depar.getDepartmentGroup_id().getId()+ " fgfgfgfg   "+depar.getDepartmentGroup_id().getNameNp());
   }
    
    
   public void  onChecked(long idDepGroup )
   {
       System.out.println(idDepGroup);
       test=idDepGroup;
       if(depar.getDepartmentGroup_id()!=null&&idDepGroup==depar.getDepartmentGroup_id().getId()){
       test=99;
       depar.setDepartmentGroup_id(null);
       }else{
       
           for (DepartmentGroup depG : depGroup) {
           if(idDepGroup==depG.getId())
           depar.setDepartmentGroup_id(depG);
           }
       
       }
       
       
   }*/
   
   
   

  

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
