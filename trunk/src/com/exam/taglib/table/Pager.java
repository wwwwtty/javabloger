package com.exam.taglib.table;      
     
import org.apache.struts2.components.UIBean;      
import org.apache.struts2.views.annotations.StrutsTag;      
import org.apache.struts2.views.annotations.StrutsTagAttribute;      
import com.opensymphony.xwork2.util.ValueStack;      
     
import javax.servlet.http.HttpServletRequest;      
import javax.servlet.http.HttpServletResponse;      
     
/**    
 * struts2版的分页标签    
 *     
 */     
     
@StrutsTag(name = "pager", tldTagClass = "org.icim.pager.struts2.PagerTag", description = "struts2 pager by ithink")      
public class Pager extends UIBean {      
     
    final public static String TEMPLATE = "pager";      
     
    protected String totalRecord;      
    protected String totalPage;      
    protected String curPage;      
    protected String pageLimit;      
    protected String url;      
    protected String curCssClass;      
    protected String showTotalPage;      
    protected String showTotalRecord;      
    protected String directJumpType;      
     
    public Pager(ValueStack stack, HttpServletRequest request,      
            HttpServletResponse response) {      
        super(stack, request, response);      
    }      
     
    /**    
     * 用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件。    
     */     
    @Override     
    protected String getDefaultTemplate() {      
        return TEMPLATE;      
    }      
     
    /**    
     * 设置UIBean的属性，一般Tag中有几个这样的属性，这里就有几个 StrutsTagAttribute注解，说明该属性是int类型，这一步很重要    
     *     
     * @param totalPage    
     */     
     
    @StrutsTagAttribute(description = "total records", type = "Long")      
    public void setTotalRecord(String totalRecord) {      
        this.totalRecord = totalRecord;      
    }      
     
    @StrutsTagAttribute(description = "total pages", type = "Integer")      
    public void setTotalPage(String totalPage) {      
        this.totalPage = totalPage;      
    }      
     
    @StrutsTagAttribute(description = "current page", type = "Integer")      
    public void setCurPage(String curPage) {      
        this.curPage = curPage;      
    }      
     
    @StrutsTagAttribute(description = "how many pages in a panel once", type = "Integer")      
    public void setPageLimit(String pageLimit) {      
        this.pageLimit = pageLimit;      
    }      
     
    @StrutsTagAttribute(description = "url to be linked", type = "String")      
    public void setUrl(String url) {      
        this.url = url;      
    }      
     
    @StrutsTagAttribute(description = "css style of current page", type = "String")      
    public void setCurCssClass(String curCssClass) {      
        this.curCssClass = curCssClass;      
    }      
     
    @StrutsTagAttribute(description = "whether to show totalPage", type = "Boolean", defaultValue = "true")      
    public void setShowTotalPage(String showTotalPage) {      
        this.showTotalPage = showTotalPage;      
    }      
     
    @StrutsTagAttribute(description = "whether to show currentPage", type = "Boolean", defaultValue = "false")      
    public void setShowTotalRecord(String showTotalRecord) {      
        this.showTotalRecord = showTotalRecord;      
    }      
     
    // TODO 直接页面跳转      
    // 这里的directJumpType默认值为none， 可选值为 'select', 'goto'      
    @StrutsTagAttribute(description = "show type of direct jump type. such as select,textbox which can lead going to a page directly", type = "String", defaultValue = "none")      
    public void setDirectJumpType(String directJumpType) {      
        this.directJumpType = directJumpType;      
    }      
     
    /**    
     * 重写evaluateExtraParams（）方法，在UIBean初始化后会调用这个方法来初始化设定参数，如addParameter方法，会在freemarker里的parameters里加入一个key    
     * value。这里要注意findString，还有相关的findxxxx方法，它们是已经封装好了的解释ognl语法的工具，具体是怎么样的，大家可以查看一下UIBean的api    
     * doc    
     */     
    @Override     
    protected void evaluateExtraParams() {      
        super.evaluateExtraParams();      
        // findValue()方法本身已对OGNL进行了处理      
     
        if (totalRecord != null) {      
            addParameter("totalRecord", findValue(totalRecord));      
        }      
     
        if (totalPage != null) {      
            addParameter("totalPage", findValue(totalPage));      
        }      
     
        if (curPage != null) {      
            addParameter("curPage", findValue(curPage));      
        }      
     
        if (pageLimit != null) {      
            addParameter("pageLimit", findValue(pageLimit));      
        }      
     
        if (url != null) {      
            addParameter("url", findValue(url, String.class));      
        }      
     
        if (curCssClass != null) {      
            addParameter("curCssClass", findValue(curCssClass,String.class));      
        }      
     
        if (showTotalPage != null) {      
            addParameter("showTotalPage", findValue(showTotalPage,      
                    Boolean.class));      
        }      
     
        if (showTotalRecord != null) {      
            addParameter("showTotalRecord", findValue(showTotalRecord,Boolean.class));      
        }      
     
        if (directJumpType != null) {      
            addParameter("directJumpType", findValue(directJumpType));      
        }      
     
    }      
}    