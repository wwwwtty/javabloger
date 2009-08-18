package com.exam.taglib.table;      
     
import org.apache.struts2.views.jsp.ui.AbstractUITag;      
import org.apache.struts2.components.Component;      
import com.opensymphony.xwork2.util.ValueStack;      
     
import javax.servlet.http.HttpServletRequest;      
import javax.servlet.http.HttpServletResponse;      
     
/**    
 * struts2 版分页标签    
 */     
public class PagerTag extends AbstractUITag {      
          
    private static final long serialVersionUID = 719669934024053141L;      
          
    protected String totalRecord;      
    protected String totalPage;      
    protected String curPage;      
    protected String pageLimit;      
    protected String url;      
    protected String curCssClass;      
    protected String showTotalPage;      
    protected String showTotalRecord;      
    protected String directJumpType;      
     
    protected void populateParams() {      
     
        super.populateParams();      
     
        Pager pager = (Pager) component;      
              
        pager.setTotalRecord(totalRecord);      
        pager.setTotalPage(totalPage);      
        pager.setCurPage(curPage);      
        pager.setPageLimit(pageLimit);      
        pager.setUrl(url);      
        pager.setCurCssClass(curCssClass);      
        pager.setShowTotalPage(showTotalPage);      
        pager.setShowTotalRecord(showTotalRecord);      
        pager.setDirectJumpType(directJumpType);      
     
    }      
     
    @Override     
    public Component getBean(ValueStack stack, HttpServletRequest request,      
            HttpServletResponse response) {      
        return new Pager(stack, request, response);      
    }      
     
    public void setTotalRecord(String totalRecord){      
        this.totalRecord = totalRecord;      
    }      
          
    public void setTotalPage(String totalPage) {      
        this.totalPage = totalPage;      
    }      
     
    public void setCurPage(String curPage) {      
        this.curPage = curPage;      
    }      
     
    public void setPageLimit(String pageLimit) {      
        this.pageLimit = pageLimit;      
    }      
     
    public void setUrl(String url) {      
        this.url = url;      
    }      
     
    public void setCurCssClass(String curCssClass) {      
        this.curCssClass = curCssClass;      
    }      
     
    public void setShowTotalPage(String showTotalPage) {      
        this.showTotalPage = showTotalPage;      
    }      
     
    public void setShowTotalRecord(String showTotalRecord) {      
        this.showTotalRecord = showTotalRecord;      
    }      
     
    public void setDirectJumpType(String directJumpType) {      
        this.directJumpType = directJumpType;      
    }      
          
          
}   